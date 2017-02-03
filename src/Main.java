import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.event.meta.TrackName;

import java.io.File;
import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Alena on 04.01.2017.
 */
public class Main {
    public static void main(String args[]) {

        //see the table: http://soundprogramming.net/file-formats/general-midi-instrument-list/
        //WARNING! Number-- (should be from 0 to 127, NOT FROM 1 TO 128!)
        int instrument = 0; //Acoustic Grand Piano
        String instrumentName = "Acoustic Grand Piano";
        int A0pitch = 21, C8pitch = 108; //Piano keyboard range
        int notesQuantity = 3, chordsQuantity = 3000;
        String directoryName = "midiChords";

        createManyChords(instrument, instrumentName, A0pitch, C8pitch, notesQuantity, chordsQuantity, directoryName);

    }

    private static void createManyChords(int instrument, String instrumentName, int firstNote, int lastNote,
                                         int notesQuantity, int chordsQuantity, String directoryName){

        ComplexChordMidi ccm = new ComplexChordMidi(instrument, instrumentName);

        Random random = new Random();
        int minBpm = 60, bpm, minVelocity = 20, velocity, minDuration = 30, duration;
        List<int[]> usedChords = new ArrayList<>(chordsQuantity);
        int countLoops = 0, maxLoopsNumber = 100;
        for (int i = 0; i < chordsQuantity; i++) {
            countLoops++;
            int[] notes = new int[notesQuantity];
            boolean contains;
            int count, note = 0;
            for (int j = 0; j <notes.length; j++) {
                contains = true;
                while (contains){
                    count = 0;
                    note = firstNote + random.nextInt(lastNote - firstNote + 1);
                    for (int k = 0; k < notes.length; k++) {
                        if (notes[k] != note) {
                            count++;
                        }
                    }
                    if (count == notes.length)
                        contains = false;
                }
                notes[j] = note;
            }

            Arrays.sort(notes);

            if (usedChords.contains(notes)) {
                i--;
                if (countLoops == maxLoopsNumber){
                    System.err.println("Too many loops done! Unable to build any more chords!");
                    break;
                }
            }
            else {
                usedChords.add(notes);

                bpm = minBpm + random.nextInt(180);
                velocity = minVelocity + random.nextInt(80);
                duration = minDuration + minDuration * random.nextInt(16);

                ccm.buildComplexChordMidi(notes, velocity, duration, bpm, directoryName);

                countLoops = 0;
            }
        }



    }
}
