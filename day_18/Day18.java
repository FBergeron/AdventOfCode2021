import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day18 {

    class SNumber {

        public SNumber(String str) throws NumberFormatException {
            if (str.charAt(0) != '[')
                throw new NumberFormatException();

            int pairIndex = 0;
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);

                if (c == '[') {
                    SNumber childNumber = new SNumber(str.substring(i));
                    pair[pairIndex] = childNumber;
                    pairIndex++;
                    i += childNumber.length() - 1;
                }
                else if (c == ']') {
                    if (pairIndex == 2)
                        return;
                    else
                        throw new NumberFormatException();
                }
                else if (c == ',') {
                }
                else {
                    pair[pairIndex] = Integer.valueOf(c + "");
                    pairIndex++;
                }
            }
        }

        public SNumber add(SNumber number) {
            StringBuilder str = new StringBuilder();
            str.append("[").append(toString()).append(",").append(number.toString()).append("]");
            return new SNumber(str.toString());
        }

        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("[").append(pair[0]).append(",").append(pair[1]).append("]");
            return str.toString();
        }

        public int length() {
            return toString().length();
        }

        public long getMagnitude() {
            long left = 3 * (pair[0] instanceof SNumber ? ((SNumber)pair[0]).getMagnitude() : ((Integer)pair[0]).intValue());
            long right = 2 * (pair[1] instanceof SNumber ? ((SNumber)pair[1]).getMagnitude() : ((Integer)pair[1]).intValue());
            return left + right;
        }

        public void reduce() {
        }

        public boolean isReducable() {
        }

        private boolean isReducableRec() {
        }

        Object[] pair = new Object[2];

    }

    public Day18() {
    }

    private ArrayList<SNumber> readInput(String filename) {
        ArrayList<SNumber> numbers = new ArrayList<SNumber>();
        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                line = line.trim();
                SNumber number = new SNumber(line);
                numbers.add(number);
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
        return numbers;
    }

    public void run() {
        ArrayList<SNumber> input = readInput("input_test_1.txt");
        //String input = readInput("input.txt");

        System.out.println("input=" + input);

        SNumber sumTest = input.get(0).add(input.get(1));
        System.out.println("sumTest="+sumTest);

        for (SNumber number : input)
            System.out.println(number + " -> m=" + number.getMagnitude());
    }

    public static void main(String[] args) {
        Day18 d = new Day18();
        d.run();
    }

}
