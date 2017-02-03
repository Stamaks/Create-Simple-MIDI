package buidDataset;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.InstrumentName;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by alena on 28.01.17.
 */
public class ComplexChordMidi {
    int instrument;
    String instrumentName;

    public ComplexChordMidi(int instrument, String instrumentName) {
        this.instrument = instrument;
        this.instrumentName = instrumentName;
    }

    public void buildComplexChordMidi(int[] notes, int velocity, int duration, float bpm, String directoryName){

        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(bpm);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);

        int channel = 0;
        int tick = 0;
        for (int note : notes) {
            noteTrack.insertNote(channel, note, velocity, tick, duration);
        }

        ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        InstrumentName instName = new InstrumentName(0, 0, instrumentName);
        noteTrack.insertEvent(instName);

        //Oh no. No-no-no. Do not touch this magic!
        TreeSet<MidiEvent> mEventsTemp = (TreeSet<MidiEvent>) tracks.get(1).getEvents().clone();
        tracks.get(1).getEvents().clear();
        tracks.get(1).insertEvent(
                new ProgramChange(0, channel, instrument));
        tracks.get(1).getEvents().addAll(mEventsTemp);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

        String fileName = "";
        Arrays.sort(notes);
        for (Integer note : notes) {
            fileName += note + ",";
        }

        if (!fileName.equals("")) {

            fileName = fileName.substring(0, fileName.length()-1);

            File directory = new File(directoryName);
            if (!directory.exists()) {
                if (directory.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            File output = new File(directoryName + "/" + fileName + ".mid");
            try {
                midi.writeToFile(output);
                System.err.println(fileName + ".mid");
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        else {
            System.err.println("No notes were found!");
        }
    }

}
