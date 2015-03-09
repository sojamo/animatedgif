# AnimatedGif


A library for the programming environment [processing](http://processing.org) to record and save animated gifs.

## Contents 

  * [Download](#download)
  * [Installation](#installation)
  * [Initialization](#initialization)
  * [API](#api)
    * [Constructor](#constructor)
    * [Setter and Getter](#setter-and-getter)
    * [Control](#control)    
    * [Others](#others)
  * [Example](#example)
  * [GifSequenceWriter](#gifsequencewriter)


## <a name"download"></a>Download
Download the animatedgif library from the [releases](https://github.com/sojamo/animatedgif/releases) section of this repository.

## <a name"installation"></a>Installation
unzip the zip file that you downloaded from above, then place the animatedgif folder into the libraries folder inside Processing's sketchbook folder. Where is the sketchbook folder? For osx users this folder by default is located at ~/Documents/Processing/libraries. For windows users this folder is probably located at c:/My Documents/Processing/libraries also see this [tutorial](http://www.learningprocessing.com/tutorials/libraries/). After putting the library into the libraries folder, restart processing to activate the library.

## <a name"initialization"></a>Initialization
Initialize a GifRecorder by creating a new instance of GifRecorder, the recorder automatically attaches itself to the tail of your draw procedure so that it can record the screen when instructed to do so. 


```java
	import sojamo.animatedgif.*;
	
	GifRecorder gif;
	
	void setup() {
		gif = new GifRecorder(this);
	}
	
	void draw() {}
	
```


## <a name"api"></a>API

### <a name"constructor"></a>Constructor
Create a new GifRecorder instance

```java

	GifRecorder gif = new GifRecorder(this); // this is of type PApplet
```


### <a name"setter-and-getter"></a>Setter and Getter
Use the following setter and getter functions to customize the seetings of a gif recording session.

#### Duration
Set the duration of a recording session in milliseconds. When `gif.record()` is called, the length of the recording will correspond to the duration set with `gif.setDuration(int)`

```java

	gif.setDuration(5000); // sets the duration to 5 seconds
	gif.getDuration(); // returns an int representing milliseconds
```

#### File Name
Set the filename under which an animated gif will be saved, by default gifs will be saved into the sketch folder if no other folder is specified. The GifRecorder does add a timestamp to the filename so that more than one gifs can be saved under the same name, differentiated by the timestamp suffix.

```java

	gif.setFileName("myGif.gif"); // myGif will be saved to the sketchfolder
	gif.getFileName(); // returns a String
```


#### Frames per Second
Set the frames per second that will be used during record.

```java

	gif.setFramesPerSecond(10); // sets the update rate to 100ms
	gif.getFramesPerSecond(); // returns an int
```


#### Millis between Frames
Set the interval between frames in milliseconds during record.

```java

	gif.setMillisBetweenFrames(100); // sets the update rate to 100ms
	gif.getMillisBetweenFrames(); // returns an int
```

#### Number of Frames
Set the number of frames that will be recorded and calculates the duration based on the millis between frames.

```java

	gif.setNumberOfFrames(20); // limits the frames to be recorded to 20
	gif.getMillisBetweenFrames(); // returns an int
```

#### Scale
Scales the animated gif when saved by default the scale is set to 1.0 to double the size, use 2.0, to half the size use 0.5.

```java

	gif.setScale(0.5); // shrinks the original size to half
	gif.getScale(); // returns a float
```

#### Loop
Set the loop status of the currently recorded animated gif

```java

	gif.setLoop(true); // gif will loop
	gif.isLoop(); // returns a boolean
```

### <a name"control"></a>Control
Use the following commands to control the and save gif recordings.

#### add
Add a frame to the current array of frames, use `gif.clear()` to reset the frame-array.

```java

	gif.add();
```


#### clear
Clear the frame array of recorded frames

```java

	gif.clear(); // deletes all recorded frames
```

#### record
Start recording frames over time based on the frames-per-second and duration settings. After recording has finished, the animated gif will be saved to the file defined by `setFileName(String)`.

```java

	gif.record(); // starts the recording procedure
	gif.isRecording(); // returns a boolean
```

#### pause
Pause the current active recording

```java

	gif.pause();
```

#### resume 
Resume the currently paused recording session

```java

	gif.resume();
```

#### stop
Stop the currenlty active recording session. To start over again, use `gif.record`

```java

	gif.stop();
```

#### save
Save the current gif recording under the name set by `gif.setFileName()` with timestamp-prefix

```java

	gif.save();
```

#### saveAs
Save the current gif recording

```java

	gif.saveAs("newGif.gif");
```



### <a name"others"></a>Others

#### Settings
Request the state of current settings

```java

	gif.settings(); // returns a HashMap including current settings
```

#### Key Control
De/activates the pre-defined key controls for GifRecorder, by default the key controls are deactivated. When active, use

  - the space bar to add a frame
  - s to save the current frame-array
  - backspace to clear the frame-array

```java

	gif.setKeyControl(true); // activates the pre-defined key control
	gif.isKeyControl(); // returns a boolean
```

#### Use TimeStamp
Use a Timestamp as a prefix for recorded and saved gifs to avoid overriding, this feature is active by default

```java

	gif.useTimeStamp(false); // deactivates the timestamp prefix
	gif.isTimeStamp(); // returns a boolean
```



## <a name"example"></a>Example
And some example code which you can also find inside the examples folder that comes with the library.

```java
	import sojamo.animatedgif.*;
	
	GifRecorder gif;

	void setup() {
	  size(500,200);
	  // create a new GifRecorder to record animated gifs 
	  gif = new GifRecorder(this);
	  // set the update time per second, here 100ms
	  gif.setMillisBetweenFrames(100);
	  // set the duration of the gif
	  gif.setDuration(5000);
	  // loop the gif 
	  gif.setLoop(true);
	}

	void draw() {
	  background(frameCount%255);
	}


	void keyPressed() {
	  switch(key) {
		// press key 'r' to start recording 
		// after the recording has finished, the gif
		// will be saved to disc automatically
	    case('r'):gif.record();break;
	  }
	}
```


## <a name"gifsequencewriter"></a>GifSequenceWriter.java

The GifSequenceWriter.java code was created by Elliot Kroo [link](http://elliot.kroo.net/software/java/GifSequenceWriter/)


_9 March 2015_
