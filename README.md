# MIDI datasets
Allows you to build midi datasets.

### buildDataset
The code creates MIDI files with unique chords (one chord in a file; any instrument) with random duration and velocity. Names of such files are notes' midi-numbers splited by ','. For e.g. "40,45,51.mid".
## WARNING!
This code creates 3000 MIDI! Rewrite it to get less files.

### splitMidi
Splits big MIDI file into little MIDI files by notes minimum duration. Names of such files are files' indexes and notes' midi-numbers, all splited by ','. For e.g. "1,40,45,51.mid".
