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
        TripleMidi tm = new TripleMidi();

        tm.create();
    }
}
