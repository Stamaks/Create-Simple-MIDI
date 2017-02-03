import com.sun.org.apache.xpath.internal.functions.FuncRound;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.sound.midi.*;

import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;


/**
 * Created by alena on 28.01.17.
 */
public class SplitMidi {
    int min;
    String directoryName;

    public SplitMidi(int min, String directoryName) {
        this.min = min;
        this.directoryName = directoryName;
    }

    public void split(String fileName){

        try {
            Sequence sequence = MidiSystem.getSequence(new File(fileName));

            boolean[] isNoteOnTrack = new boolean[sequence.getTracks().length];
            int trackNumber = 0;
            for (Track track : sequence.getTracks()){
                for (int i = 0; i < track.size(); i++) {
                    if (track.get(i).getMessage() instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) track.get(i).getMessage();
                        if (sm.getCommand() == NOTE_ON) {
                            isNoteOnTrack[trackNumber] = true;
                            break;
                        }
                    }
                }
                trackNumber++;
            }

            String[] notes = getArrayOfFutureMidiTracks(isNoteOnTrack, sequence.getTracks());
            for (int i = 0; i < notes.length; i++){
                System.out.println(notes[i]);
            }

            writeNewMidi(notes);

        } catch (InvalidMidiDataException e) {
            System.err.println("Invalid midi data!");
        } catch (IOException e) {
            System.err.println("Couldn't load midi file!");
        }

    }

    private String[] getArrayOfFutureMidiTracks(boolean[] isNoteOnTrack, Track[] tracks) {
        long maxTicksNumber = 0;
        for (int i = 0; i < tracks.length; i++) {
            if (!isNoteOnTrack[i]) continue;

            if (maxTicksNumber < tracks[i].ticks()){
                for (int j = tracks[i].size() - 1; j >= 0; j--) {
                    if (tracks[i].get(j).getMessage() instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) tracks[i].get(j).getMessage();
                        if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {
                            if (tracks[i].get(j).getTick() > maxTicksNumber) {
                                maxTicksNumber = tracks[i].get(j).getTick();
                                break;
                            }
                        }
                    }
                }
            }
        }

        while (maxTicksNumber % min > 0) {
            maxTicksNumber += 1;
        }

        String[] notes = new String[(int) maxTicksNumber/min];

        for (int i = 0; i < notes.length; i++){
            notes[i] = "";
        }

        int trackNumber = -1;

        for (Track track : tracks) {
            trackNumber++;
            if (!isNoteOnTrack[trackNumber]) continue;

            long tick = 0, lastTick = 0;
            String current = "";
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int velocity = sm.getData2();

                        if (tick != event.getTick()) {
                            for (int j = 0; j < (event.getTick() - tick)/min; j++){
                                notes[(int) tick/min + j] += current;
                            }
                            tick = event.getTick();
                        }

                        if (velocity == 0 || sm.getCommand() == NOTE_OFF) { //Sometimes NOTE_ON has velocity == 0 as equivalent of NOTE_OFF
                            //Delete ",key" from string
                            if (current.contains(key + "")) { //It certainly does but who the hell knows...
                                current = current.substring(0, current.indexOf(key + "") - 1) +
                                        current.substring(current.indexOf(key + "") + (key + "").length());
                            }
                        } else {
                            current += "," + key;
                        }
                        lastTick = event.getTick();
                    }
                }
            }

            if (tick != lastTick) {
                for (int j = 0; j < (lastTick - tick)/min; j++){
                    notes[(int) tick/min + j] += current;
                }
            }

        }

        return notes;
    }

    private void writeNewMidi(String[] notes) {
        ComplexChordMidi ccm = new ComplexChordMidi(0, "piano");

        int j;
        for (int i = 0; i < notes.length; i++) {
            String[] currentNotes = notes[i].split(",");
            if (currentNotes[0].equals("")) j = 1;
            else j = 0;

            int[] currentNotesInt = new int[currentNotes.length - j];
            for (int h = 0; h < currentNotesInt.length; h++) {
                currentNotesInt[h] = Integer.parseInt(currentNotes[h + j]);
            }

            ccm.buildComplexChordMidi(currentNotesInt, 80, 60, 144, directoryName);
        }
    }
}
