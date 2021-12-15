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

class Day14 {

    public Day14() {
    }

    private void readInput(String filename) {
        steps = new ArrayList<String>();
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
                    steps.add(line);
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

    public void run() {
        readInput("input_test.txt");
        //readInput("input.txt");

        System.out.println("steps=" + steps);
        System.out.println("pairInsertions=" + pairInsertions);

        int stepCount = 10;
        //int stepCount = 24;
        for (int i = 0; i < stepCount; i++) {
            String value = steps.get(i);
            StringBuilder newValue =  new StringBuilder();
            String pair = null;
            for (int c = 0; c < value.length() - 1; c++) {
                pair = value.substring(c, c + 2);
                String insertion = pairInsertions.get(pair);

                newValue.append(pair.substring(0, 1) + insertion);
            }
            newValue.append(pair.substring(1, 2));
            steps.add(newValue.toString());
            System.out.println("newValue.l=" + newValue.toString().length());
        }
        for (int i = 0; i < steps.size(); i++)
            System.out.println("step i=" + i + " -> " + steps.get(i));
        System.out.println("len=" + steps.size());

        String result = steps.get(stepCount);
        HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
        for (int i = 0; i < result.length(); i++) {
            Character c = Character.valueOf(result.charAt(i));
            if (!counts.containsKey(c))
                counts.put(c, Integer.valueOf(1));
            else
                counts.put(c, Integer.valueOf(counts.get(c).intValue() + 1));
        }

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
    private ArrayList<String> steps;

    public static void main(String[] args) {
        Day14 d = new Day14();
        d.run();
    }
}
