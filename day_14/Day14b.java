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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Day14b {

    public Day14b() {
    }

    private void readInput(String filename) {
        pairInsertions = new HashMap<String, String>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            boolean isFirstSectionOver = false;
            String line;
            while ((line = bufReader.readLine()) != null) {
                if (line.length() == 0) {
                    isFirstSectionOver = true;
                    continue;
                }

                if (!isFirstSectionOver)
                    initState = line;
                else {
                    String[] parts = line.split(" -> ");
                    pairInsertions.put(parts[0], parts[1]);
                }
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
    }

    private void computeResult(String pair, int stepCount) {
        StringBuilder value = new StringBuilder(pair);
        for (int i = 0; i < stepCount; i++) {
            StringBuilder newValue =  new StringBuilder();
            String tempPair = null;
            for (int c = 0; c < value.length() - 1; c++) {
                tempPair = value.substring(c, c + 2);
                String insertion = pairInsertions.get(tempPair);

                newValue.append(tempPair.substring(0, 1) + insertion);
            }
            newValue.append(tempPair.substring(1, 2));
            value = newValue;
        }

        precomputedResults.put(pair, value.toString());

        HashMap<Character, Long> counts = new HashMap<Character, Long>();
        for (int i = 0; i < value.length(); i++) {
            Character c = Character.valueOf(value.charAt(i));
            if (!counts.containsKey(c))
                counts.put(c, Long.valueOf(1));
            else
                counts.put(c, Long.valueOf(counts.get(c).longValue() + 1));
        }
        precomputedCounts.put(pair, counts);
    }

    public void run() {
        //readInput("input_test.txt");
        readInput("input.txt");

        System.out.println("initState=" + initState);
        System.out.println("pairInsertions=" + pairInsertions);

        int stepCount = 40;

        for (String pair : pairInsertions.keySet())
            computeResult(pair, stepCount / 2);

        //System.out.println("precomputedResults");
        //System.out.println(precomputedResults);
        //System.out.println("\n\nprecomputedCounts");
        //System.out.println(precomputedCounts);

        HashMap<Character, Long> counts = new HashMap<Character, Long>();

        for (int p1 = 0; p1 < initState.length() - 1; p1++) {
            String tempPair1 = initState.substring(p1, p1 + 2);
            String tempResult1 = precomputedResults.get(tempPair1);
            HashMap<Character, Long> tempCounts1 = precomputedCounts.get(tempPair1);

            //System.out.println("Level 1: Process pair: " + tempPair1);

            for (int p2 = 0; p2 < tempResult1.length() - 1; p2++) {
                String tempPair2 = tempResult1.substring(p2, p2 + 2);
                String tempResult2 = precomputedResults.get(tempPair2);
                HashMap<Character, Long> tempCounts2 = precomputedCounts.get(tempPair2);

                //System.out.println("Level 2: Process pair: " + tempPair2);

                for (Character c : tempCounts2.keySet()) {
                    if (!counts.containsKey(c))
                        counts.put(c, tempCounts2.get(c));
                    else
                        counts.put(c, counts.get(c) + tempCounts2.get(c));
                }
                Character lastChar = Character.valueOf(tempResult2.charAt(tempResult2.length() - 1));
                counts.put(lastChar, Long.valueOf(counts.get(lastChar) - 1));
            }
        }
        Character lastChar = Character.valueOf(initState.charAt(initState.length() - 1));
        counts.put(lastChar, Long.valueOf(counts.get(lastChar) + 1));

        //System.out.println("counts="+counts);

        Character mostCommonElement = null;
        Character leastCommonElement = null;
        for (Character c : counts.keySet()) {
            if (mostCommonElement == null || counts.get(c) > counts.get(mostCommonElement))
                mostCommonElement = c;
            if (leastCommonElement == null || counts.get(c) < counts.get(leastCommonElement))
                leastCommonElement = c;
        }
        System.out.println("Least common element: " + leastCommonElement + " quantity: " + counts.get(leastCommonElement));
        System.out.println("Most common element: " + mostCommonElement + " quantity: " + counts.get(mostCommonElement));
        System.out.println("Difference: " + (counts.get(mostCommonElement) - counts.get(leastCommonElement)));
    }


    private HashMap<String, String> pairInsertions;
    private String initState;

    private HashMap<String, HashMap<Character, Long>> precomputedCounts = new HashMap<String, HashMap<Character, Long>>();
    private HashMap<String, String> precomputedResults = new HashMap<String, String>();

    public static void main(String[] args) {
        Day14b d = new Day14b();
        d.run();
    }
}

