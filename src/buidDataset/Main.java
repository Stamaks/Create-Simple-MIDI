package buidDataset;

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

        CreateChords cc = new CreateChords();
        cc.create(instrument, instrumentName, A0pitch, C8pitch, notesQuantity, chordsQuantity, directoryName);

    }
}
