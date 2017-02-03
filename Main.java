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

        SimpleMidi smP = new SimpleMidi();
        smP.create(0, "piano");
        SimpleMidi smG = new SimpleMidi();
        smG.create(24, "guitar");

        TripleMidi tmP = new TripleMidi();
        tmP.create(0, "piano");
        TripleMidi tmG = new TripleMidi();
        tmG.create(24, "guitar");
    }
}
