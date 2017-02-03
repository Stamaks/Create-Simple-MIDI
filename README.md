# Create-Simple-MIDI
Created 

###buildDataset
The code creates MIDI files with any instrument chords (one chord in a file) with random duration and velocity. Good for building music datasets, if you want lots of little MIDI files be there. Names of such files are midi note numbers splited by ','. For instance "40,45,51.mid".
##WARNING!
This code creates 3000 MIDI! Rewrite it to get less files.

###splitMidi
Splits big MIDI file into little MIDI files by note minimum duration.
