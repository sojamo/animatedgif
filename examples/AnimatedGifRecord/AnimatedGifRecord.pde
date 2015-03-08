import sojamo.animatedgif.*;
  
  GifRecorder gif;

  void setup() {
    size(500,200);
    // create a new GifAnimator to record animated gifs 
    gif = new GifRecorder(this);
    // set the update time per second, here 100ms
    gif.setMillisBetweenFrames(100);
    // set the duration of the gif
    gif.setDuration(1000);
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
