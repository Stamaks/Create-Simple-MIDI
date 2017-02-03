import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.event.meta.TrackName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alena on 04.01.2017.
 */
public class Main {
    public static void main(String args[]) {

        int[] notes = new int[4];
        notes[0] = 40;
        notes[1] = 46;
        notes[2] = 52;
        notes[3] = 60;

        ComplexChordMidi ccm = new ComplexChordMidi(0, "piano");
        ccm.buildComplexChordMidi(notes, 70, 60, 144, "chordMidi");

    }
}
