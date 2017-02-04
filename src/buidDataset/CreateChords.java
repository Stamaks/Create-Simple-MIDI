package buidDataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by alena on 03.02.17.
 */
public class CreateChords {

    int minBPM = 60;
    int maxBPM = 240;
    int minVelocity = 20;
    int maxVelocity = 100;
    int minDuration = 30;
    int maxLoopsNumber = 100;

    public void create(int instrument, String instrumentName, int firstNote, int lastNote,
                       int notesQuantity, int chordsQuantity, String directoryName) {
        ComplexChordMidi ccm = new ComplexChordMidi(instrument, instrumentName);

        Random random = new Random();
        int bpm, velocity, duration;
        List<int[]> usedChords = new ArrayList<>(chordsQuantity);
        int countLoops = 0;
        for (int i = 0; i < chordsQuantity; i++) {
            countLoops++;
            int[] notes = new int[notesQuantity];
            boolean contains;
            int count, note = 0;
            for (int j = 0; j < notes.length; j++) {
                contains = true;
                while (contains) {
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
                if (countLoops == maxLoopsNumber) {
                    System.err.println("Too many loops done! Unable to build any more chords!");
                    break;
                }
            } else {
                usedChords.add(notes);

                bpm = minBPM + random.nextInt(maxBPM - minBPM + 1);
                velocity = minVelocity + random.nextInt(maxVelocity - minVelocity + 1);
                duration = minDuration + minDuration * random.nextInt(16);

                int[] enumerateFiles = {}; //don't need enumeration
                ccm.buildComplexChordMidi(notes, velocity, duration, bpm, directoryName, enumerateFiles);

                countLoops = 0;
            }
        }
    }
}
