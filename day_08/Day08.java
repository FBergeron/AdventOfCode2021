import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

class Day08 {

    public class Entry {

        public Entry(String[] patterns, String[] digits) {
            this.patterns = patterns;
            this.digits = digits;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            String delim = "";
            for (int i = 0; i < patterns.length; i++) {
                builder.append(delim);
                builder.append(patterns[i]);
                delim = " ";
            }
            builder.append(" | ");
            delim = "";
            for (int i = 0; i < digits.length; i++) {
                builder.append(delim);
                builder.append(digits[i]);
                delim = " ";
            }
            return builder.toString();
        }

        public String[] patterns;
        public String[] digits;

    }

    private ArrayList<Entry> readInput(String filename) {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                Entry entry = new Entry(parts[0].split(" "), parts[1].split(" "));
                entries.add(entry);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return entries;
    }

    public void run() {
        //ArrayList<Entry> entries = readInput("input_short.txt");
        ArrayList<Entry> entries = readInput("input.txt");
        System.out.println("entries="+entries);

        int count = 0;
        for (Entry entry : entries) {
            for (int i = 0; i < entry.digits.length; i++) {
                if (entry.digits[i].length() == 2 ||
                    entry.digits[i].length() == 4 ||
                    entry.digits[i].length() == 3 ||
                    entry.digits[i].length() == 7)
                    count++;
            }
        }
        System.out.println("count="+count);
    }

    public static void main(String[] args) {
        Day08 d = new Day08();
        d.run();
    }
}
