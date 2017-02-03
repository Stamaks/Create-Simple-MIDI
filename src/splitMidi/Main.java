package splitMidi;

/**
 * Created by alena on 03.02.17.
 */
public class Main {
    public static void main(String args[]){
        int min = 30; //Warning! You must choose min % 15 == 0!!!
        String directoryName = "littleMIDIs";

        MidiSplitter ms = new MidiSplitter(min, directoryName);
        ms.split("test.mid");
    }
}
