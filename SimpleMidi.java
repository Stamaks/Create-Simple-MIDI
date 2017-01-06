import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alena on 05.01.2017.
 */
public class SimpleMidi {

    public void create() {

        String fileName = "";
        final int A0pitch = 21, C8pitch = 108;

        Random randomBpm = new Random(60);
        Random randomVelocity = new Random(50);
        Random randomDuration = new Random(1);
        for (int i = A0pitch; i < C8pitch; i++){
            for (int j = i + 1; j <= C8pitch; j += 2){
                MidiTrack tempoTrack = new MidiTrack();
                MidiTrack noteTrack = new MidiTrack();

                TimeSignature ts = new TimeSignature();
                ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

                Tempo tempo = new Tempo();
                tempo.setBpm(randomBpm.nextInt(230));

                tempoTrack.insertEvent(ts);
                tempoTrack.insertEvent(tempo);

                int channel = 0;
                int pitchD = i;
                int velocity = randomVelocity.nextInt(100);
                while (velocity == 0) {
                    velocity = randomVelocity.nextInt(100);
                }
                long tick = 0;
                long duration = 120*randomDuration.nextInt(5);
                while (duration == 0) {
                    duration = 120*randomDuration.nextInt(5);
                }

                int pitchU = j;

                noteTrack.insertNote(channel, pitchD, velocity, tick, duration);
                noteTrack.insertNote(channel, pitchU, velocity, tick, duration);

                ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
                tracks.add(tempoTrack);
                tracks.add(noteTrack);

                MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

                fileName = 2 + "," + pitchD + "," + pitchU + "," + duration;

                File output = new File("midi/" + fileName + ".mid");
                try {
                    midi.writeToFile(output);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
}
