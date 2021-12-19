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
            StringBuilder regularNumber = null;

            if (str.charAt(0) != '[')
                throw new NumberFormatException();

            int pairIndex = 0;
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);

                if (c == '[') {
                    SNumber childNumber = new SNumber(str.substring(i));
                    childNumber.parent = this;
                    pair[pairIndex] = childNumber;
                    pairIndex++;
                    i += childNumber.length() - 1;
                }
                else if (c == ']' || c == ',') {
                    if (regularNumber != null) {
                        pair[pairIndex] = Integer.valueOf(regularNumber.toString());
                        pairIndex++;
                        regularNumber = null;
                    }
                    if (pairIndex == 2)
                        return;
                }
                else {
                    if (regularNumber == null)
                        regularNumber = new StringBuilder();
                    regularNumber.append(c + "");
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
            reduceRec(0);
        }

        public void showParent() {
            showParentRec(0);
        }

        public void showParentRec(int depth) {
System.out.println( "showParentRec depth="+depth+" parent="+parent);
            for (int i = 0; i < pair.length; i++) {
                if (pair[i] instanceof SNumber) {
                    SNumber child = (SNumber)pair[i];
                    child.showParentRec(depth + 1);
                }
            }
        }


        public void reduceRec(int depth) {
            boolean mustReduceFromTopAgain = false;

            if (depth == 4) {
                System.out.println("Exploding " + this + "...");

                // Search first left regular number and add it the first child.
                SNumber tempSNumber = parent;
                while (tempSNumber != null && tempSNumber.pair[0] != null && tempSNumber.pair[0] != this) {
                    tempSNumber = parent;
                }




                SNumber rightMost = tempSNumber.pair[1];
                    while (rightMost != null && rightMost.pair[1] != null && rightMost.pair[1] instanceof SNumber) {
System.out.println( "Go down!" );
                        rightMost = rightMost.pair[1];
                    }
                    if (rightMost != null) {
System.out.println( "Found rightMost!" );
                        rightMost.pair[1] =
                        rightMost.pair[1] = Integer.valueOf(
                            ((Integer)tempSNumber.pair[0]).intValue() + ((Integer)pair[0]).intValue());

                    }
                    tempSNumber.pair[0] = Integer.valueOf(
                        ((Integer)tempSNumber.pair[0]).intValue() + ((Integer)pair[0]).intValue());
                }

//                // Search the first right regular number and add it the second child.
//                tempSNumber = parent;
//System.out.println( "tempSNumber="+tempSNumber );
//                while (tempSNumber != null && tempSNumber.pair[1] instanceof SNumber) {
//System.out.println( "Go up!" );
//                    tempSNumber = tempSNumber.parent;
//                }
//                if (tempSNumber != null) {
//System.out.println( "Found right parent" );
//                    tempSNumber.pair[1] = Integer.valueOf(
//                        ((Integer)tempSNumber.pair[1]).intValue() + ((Integer)pair[1]).intValue());
//                }

//                // Replace the current pair with a regular number 0.
//System.out.println( "parent="+parent+" depth="+depth );
//System.out.println( "parent.pair="+parent.pair );
//                for (int i = 0; i < parent.pair.length; i++) {
//                    if (parent.pair[i] == this)
//                        parent.pair[i] = 0;
//                }

                mustReduceFromTopAgain = false;
            }

            for (int i = 0; i < pair.length; i++) {
                Object child = pair[i];
                if (child instanceof Integer) {
                    Integer childRegularNumber = (Integer)child;
                    if (childRegularNumber.intValue() > 9) {
                        System.out.println("Splitting " + childRegularNumber + "...");
                        SNumber newPair = new SNumber("[" +
                            childRegularNumber.intValue() / 2 + "," +
                            Math.round((float)childRegularNumber.intValue() / 2) + "]");
                        newPair.parent = this;
                        pair[i] = newPair;

                        mustReduceFromTopAgain = true;
                        break;
                    }
                }
                else {
                    SNumber childSNumber = (SNumber)child;
                    childSNumber.reduceRec(depth + 1);
                }
            }

            if (mustReduceFromTopAgain) {
                SNumber topParent = this;
                while(topParent.parent != null)
                    topParent = topParent.parent;
                topParent.reduce();
            }
        }

        public boolean isReducable() {
            return false;
        }

        private boolean isReducableRec() {
            return false;
        }

        Object[] pair = new Object[2];
        SNumber parent = null;

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
        //ArrayList<SNumber> input = readInput("input_test_1.txt");
        ArrayList<SNumber> input = readInput("input_test_reduce.txt");
        //String input = readInput("input.txt");

        System.out.println("input=" + input);

        //for (SNumber number : input)
        //    //number.showParent();
        //    number.reduce();

        //SNumber sumTest = input.get(0).add(input.get(1));
        //System.out.println("sumTest="+sumTest);

        //for (SNumber number : input)
        //    System.out.println(number + " -> m=" + number.getMagnitude());

        //System.out.println("13/2="+Math.floor((float)13/2));
        //System.out.println("13/2="+Math.round((float)13/2));

        for (SNumber number : input) {
            StringBuilder str = new StringBuilder();
            str.append(number + " -> reduced=");
            number.reduce();
            str.append(number);
            System.out.println(str.toString());
        }

//System.out.println( "parent="+((SNumber)input.get(4).pair[0]).parent );

    }

    public static void main(String[] args) {
        Day18 d = new Day18();
        d.run();
    }

}
