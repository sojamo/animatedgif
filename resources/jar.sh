cd $HOME/Documents/workspace/animatedgif/target/classes
jar cf ../animatedgif.jar .
cp ../animatedgif.jar $HOME/Documents/Processing/libraries/animatedgif/library
echo "animatedgif compiled on $(date)"