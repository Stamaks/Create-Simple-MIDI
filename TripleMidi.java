import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by Alena on 06.01.2017.
 */
public class TripleMidi {
    public void create(int instrument, String instrumentName) {

        String fileName = "";
        final int A0pitch = 21, C8pitch = 108;

        Random randomBpm = new Random(60);
        Random randomVelocity = new Random(50);
        Random randomDuration = new Random(1);

        for (int i = A0pitch; i < C8pitch - 1; i++){
            for (int j = i + 1; j < C8pitch; j += 3){
                for (int k = j + 1; k <= C8pitch; k +=2){
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

                    int pitchM = j;
                    int pitchU = k;

                    noteTrack.insertNote(channel, pitchD, velocity, tick, duration);
                    noteTrack.insertNote(channel, pitchM, velocity, tick, duration);
                    noteTrack.insertNote(channel, pitchU, velocity, tick, duration);

                    ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
                    tracks.add(tempoTrack);
                    tracks.add(noteTrack);

                    TreeSet<MidiEvent> mEventsTemp = (TreeSet<MidiEvent>) tracks.get(1).getEvents().clone();
                    tracks.get(1).getEvents().clear();
                    tracks.get(1).insertEvent(
                            new ProgramChange(0, 1 - 1, instrument));
                    tracks.get(1).getEvents().addAll(mEventsTemp);

                    MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

                    fileName = 3 + "," + pitchD + "," + pitchM + "," + pitchU + "," + duration;

                    File output = new File("midiT_"+ instrumentName + "/" + fileName + ".mid");
                    try {
                        midi.writeToFile(output);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        }
    }
}
