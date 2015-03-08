# AnimatedGif


A library for the programming environment [processing](http://processing.org) to record and save animated gifs.


``` Processing

	import sojamo.animatedgif.*;
	
	GifAnimator gif;

	void setup() {
	  size(500,200);
	  // create a new GifAnimator to record animated gifs 
	  gif = new GifAnimator(this);
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

