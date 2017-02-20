/**
 * you can put a one sentence description of your library here.
 *
 * (c) 2012
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 * 
 * @author Andreas Schlegel http://www.sojamo.de
 * @modified 02/02/2012
 * @version 0.1.0
 */

package sojamo.animatedgif;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.imageio.stream.FileImageOutputStream;

import processing.core.PApplet;
import processing.event.KeyEvent;


@SuppressWarnings( { "rawtypes" , "unchecked" } ) public class GifRecorder {

	private PApplet papplet;
	public final static String VERSION = "0.1.1";
	private List< int[] > frames;
	private GifSequenceWriter writer;
	private FileImageOutputStream output;

	private int width;
	private int height;
	private String outFile;
	private String timeStampPattern = "yyMMdd-HHmmss";

	private boolean isAdding = false;
	private boolean isRecording = false;
	private boolean isKeyControl = true;
	private boolean isLoop = true;
	private boolean isTimeStamp = true;
	private int duration = -1;
	private int millisBetweenFrames = 200;
	private String createBy = "animatedgif";
	private float scale = 1.0f;

	/* timer fields */

	private int n;
	private long t;
	private int numberOfFrames = -1;

	public GifRecorder( PApplet theParent ) {
		papplet = theParent;
		papplet.registerMethod( "draw" , this );
		papplet.registerMethod( "keyEvent" , this );
		width = papplet.width;
		height = papplet.height;
		frames = new ArrayList< int[] >( );
		setFileName( papplet.sketchPath( "tmp.gif" ) );
		welcome( );
	}

	public Map settings( ) {
		HashMap m = new HashMap( );
		m.put( "isAdding" , isAdding );
		m.put( "isRecording" , isRecording );
		m.put( "isKeyControl" , isKeyControl );
		m.put( "isLoop" , isLoop );
		m.put( "isTimeStamp" , isTimeStamp );
		m.put( "duration" , duration );
		m.put( "scale" , scale );
		m.put( "numberOfFrames" , numberOfFrames );
		m.put( "millisBetweenFrames" , millisBetweenFrames );
		m.put( "timeStampPattern" , timeStampPattern );
		m.put( "outputFileName" , timeStampPattern );
		return Collections.unmodifiableMap( m );
	}

	public void draw( ) {
		if ( isAdding ) {
			if ( isRecording ) {
				n += ( papplet.millis( ) - t );
				t = papplet.millis( );
				if ( n >= millisBetweenFrames ) {
					n = 0;
					papplet.loadPixels( );
					addFrame( papplet.pixels );
					if ( frames.size( ) == numberOfFrames ) {
						save( );
						clear( );
						isAdding = false;
					}
				}
			} else {
				papplet.loadPixels( );
				addFrame( papplet.pixels );
				isAdding = false;
				if ( frames.size( ) == numberOfFrames ) {
					save( );
				}
			}
		}
	}

	public void keyEvent( processing.event.KeyEvent k ) {
		if ( isKeyControl ) {
			if ( k.getAction( ) == KeyEvent.PRESS ) {
				char key = k.getKey( );
				switch ( key ) {
				case ( ' ' ):
					add( );
					break;
				case ( 's' ):
				case ( 'S' ):
					save( );
					stop( );
					break;
				case ( 0x08 ):
					clear( );
					break;
				}
			}
		}
	}

	public GifRecorder add( ) {
		isAdding = true;
		return this;
	}

	public GifRecorder record( ) {
		clear( );
		isRecording = true;
		isAdding = true;
		n = 0;
		return this;
	}

	public boolean isRecording( ) {
		return isRecording;
	}

	public GifRecorder useTimeStamp( boolean theValue ) {
		isTimeStamp = theValue;
		return this;
	}

	public boolean isTimeStamp( ) {
		return isTimeStamp;
	}

	public GifRecorder stop( ) {
		isRecording = false;
		isAdding = false;
		return this;
	}

	public GifRecorder pause( ) {
		isRecording = false;
		return this;
	}

	public GifRecorder resume( ) {
		isRecording = true;
		return this;
	}

	public GifRecorder clear( ) {
		frames = new ArrayList< int[] >( );
		return this;
	}

	public GifRecorder setKeyControl( boolean theValue ) {
		isKeyControl = theValue;
		return this;
	}

	public boolean isKeyControl( ) {
		return isKeyControl;
	}

	public GifRecorder setFileName( String theOutFile ) {
		outFile = theOutFile;
		return this;
	}

	public String getFileName( ) {
		return outFile;
	}

	public GifRecorder setMillis( int theMillis ) {
		return setMillisBetweenFrames( theMillis );
	}

	public int getMillis( ) {
		return getMillisBetweenFrames( );
	}

	public GifRecorder setMillisBetweenFrames( int theMillis ) {
		millisBetweenFrames = theMillis;
		return this;
	}

	public int getMillisBetweenFrames( ) {
		return millisBetweenFrames;
	}

	public GifRecorder setNumberOfFrames( int theValue ) {
		numberOfFrames = theValue;
		duration = getMillisBetweenFrames( ) * getNumberOfFrames( );
		return this;
	}

	public int getNumberOfFrames( ) {
		return numberOfFrames;
	}

	public GifRecorder setFramesPerSecond( int theValue ) {
		setMillisBetweenFrames( 1000 / theValue );
		return this;
	}

	public int getFramesPerSecond( ) {
		return 1000 / getMillisBetweenFrames( );
	}

	public GifRecorder setDuration( int theValue ) {
		setNumberOfFrames( ( 1000 / getMillisBetweenFrames( ) ) * ( theValue / 1000 ) );
		return this;
	}

	public int getDuration( ) {
		return duration;
	}

	public GifRecorder setLoop( boolean theValue ) {
		isLoop = theValue;
		return this;
	}

	public boolean isLoop( ) {
		return isLoop;
	}

	public GifRecorder setCreatedBy( String theValue ) {
		createBy = theValue;
		return this;
	}

	public String getCreatedBy( ) {
		return createBy;
	}

	public GifRecorder save( ) {
		if ( outFile.endsWith( ".gif" ) && isTimeStamp ) {
			String s = outFile.substring( 0 , outFile.length( ) - 4 );
			s += "_" + getTimeStamp( ) + ".gif";
			return saveAs( s );
		}
		// TODO fixme
		return saveAs( outFile );
	}

	public GifRecorder setScale( float theValue ) {
		scale = theValue;
		return this;
	}

	public float getScale( ) {
		return scale;
	}

	public GifRecorder saveAs( String theFileName ) {
		System.out.println( "saving frames to " + theFileName );
		try {
			output = new FileImageOutputStream( new File( theFileName ) );
		} catch ( IOException ex ) {
			throw new RuntimeException( ex );
		}
		try {
			writer = new GifSequenceWriter( output , BufferedImage.TYPE_INT_RGB , millisBetweenFrames , isLoop , createBy );
		} catch ( IOException ex ) {
			throw new RuntimeException( ex );
		}

		for ( int[] frame : frames ) {
			BufferedImage img = new BufferedImage( width , height , BufferedImage.TYPE_INT_RGB );
			img.setRGB( 0 , 0 , width , height , frame , 0 , width );
			BufferedImage after;
			if ( scale != 1.0f ) {
				after = new BufferedImage( ( int ) ( width * scale ) , ( int ) ( height * scale ) , BufferedImage.TYPE_INT_RGB );
				AffineTransform at = new AffineTransform( );
				at.scale( scale , scale );
				AffineTransformOp scaleOp = new AffineTransformOp( at , AffineTransformOp.TYPE_BILINEAR );
				after = scaleOp.filter( img , after );
			} else {
				after = img;
			}
			try {
				writer.writeToSequence( after );
			} catch ( IOException e ) {
				e.printStackTrace( );
			}
		}

		try {
			writer.close( );
			output.close( );
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return this;
	}

	private String getTimeStamp( ) {
		SimpleDateFormat format = new SimpleDateFormat( timeStampPattern );
		return format.format( new Date( ) );
	}

	public GifRecorder addFrame( int[] theFrame ) {
		int[] t = new int[ theFrame.length ];
		System.arraycopy( theFrame , 0 , t , 0 , theFrame.length );
		frames.add( t );
		System.out.println( "adding frame " + frames.size( ) + "/" + numberOfFrames );
		return this;
	}

	private void welcome( ) {
		System.out.println( String.format( "animatedgif %s infos, comments, questions at http://www.sojamo.de/libraries/animatedgif" , version( ) ) );
	}

	/**
	 * return the version of the library.
	 * 
	 * @return String
	 */
	public static String version( ) {
		return VERSION;
	}

}
