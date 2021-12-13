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

class Day13 {

    class Point {

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point point = (Point)obj;
            return point.x == this.x && point.y == this.y;
        }

        public int hashCode() {
            return (x + "," + y).hashCode();
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        public int x;
        public int y;

    }

    public Day13() {
    }

    private int rowsToCount() {
        int count = 0;
        for (int r = 0; r < rows.size(); r++) {
            ArrayList<Character> row = rows.get(r);
            for (int c = 0; c < row.size(); c++) {
                if (row.get(c).charValue() == '#')
                    count++;
            }
        }
        return count;
    }

    private String rowsToString() {
        StringBuilder bld = new StringBuilder();
        for (int r = 0; r < rows.size(); r++) {
            ArrayList<Character> row = rows.get(r);
            for (int c = 0; c < row.size(); c++) {
                bld.append(row.get(c));
            }
            bld.append("\n");
        }
        return bld.toString();
    }

    private void readInput(String filename) {
        rows = new ArrayList<ArrayList<Character>>();
        foldInstructions = new ArrayList<String>();

        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        ArrayList<Point> points = new ArrayList<Point>();
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

                if (!isFirstSectionOver) {
                    int indexOfComma = line.indexOf(",");
                    String strX = line.substring(0, indexOfComma);
                    String strY = line.substring(indexOfComma + 1);
                    int x = Integer.valueOf(strX);
                    int y = Integer.valueOf(strY);
                    if (x > maxX)
                        maxX = x;
                    if (y > maxY)
                        maxY = y;
                    points.add(new Point(x, y));
                }
                else {
                    foldInstructions.add(line);
                }
            }

            for (int r = 0; r <= maxY; r++) {
                ArrayList<Character> row = new ArrayList<Character>();
                for (int c = 0; c <= maxX; c++)
                    row.add(Character.valueOf('.'));
                rows.add(row);
            }
            for (Point p : points) {
                ArrayList<Character> row = rows.get(p.y);
                row.remove(p.x);
                row.add(p.x, Character.valueOf('#'));
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

    private void foldHorizontally(int foldY) {
        System.out.println("foldHorizontally y=" + foldY);

        ArrayList<ArrayList<Character>> newRows = new ArrayList<ArrayList<Character>>();
        for (int y = 0; y < rows.size() / 2; y++) {
            ArrayList<Character> newRow = new ArrayList<Character>();
            ArrayList<Character> topHalfRow = rows.get(y);
            ArrayList<Character> bottomHalkRow = rows.get(rows.size() - 1 - y);
            for (int x = 0; x < topHalfRow.size(); x++) {
                Character topHalfChar = topHalfRow.get(x);
                Character bottomHalfChar = bottomHalkRow.get(x);
                if (topHalfChar.charValue() == '#' || bottomHalfChar.charValue() == '#')
                    newRow.add(Character.valueOf('#'));
                else
                    newRow.add(Character.valueOf('.'));
            }
            newRows.add(newRow);
        }
        rows = newRows;
    }

    private void foldVertically(int foldX) {
        System.out.println("foldVertically x=" + foldX);

        ArrayList<ArrayList<Character>> newRows = new ArrayList<ArrayList<Character>>();
        for (int y = 0; y < rows.size(); y++) {
            ArrayList<Character> row = rows.get(y);
            ArrayList<Character> newRow = new ArrayList<Character>();
            for (int x = 0; x < row.size() / 2; x++) {
                Character leftHalfChar = row.get(x);
                Character rightHalfChar = row.get(row.size() - 1 - x);
                if (leftHalfChar.charValue() == '#' || rightHalfChar.charValue() == '#')
                    newRow.add(Character.valueOf('#'));
                else
                    newRow.add(Character.valueOf('.'));
            }
            newRows.add(newRow);
        }
        rows = newRows;
    }

    public void run() {
        //readInput("input_test.txt");
        readInput("input.txt");
        System.out.println("rows:");
        System.out.println(rowsToString());
        System.out.println("count=" + rowsToCount());

        for (String foldInstruction : foldInstructions) {
            String[] parts = foldInstruction.split(" ");
            String[] subparts = parts[2].split("=");
            if ("x".equals(subparts[0]))
                foldVertically(Integer.valueOf(subparts[1]).intValue());
            else if ("y".equals(subparts[0]))
                foldHorizontally(Integer.valueOf(subparts[1]).intValue());
            System.out.println("rows:");
            System.out.println(rowsToString());
            System.out.println("count=" + rowsToCount());
        }
    }


    private ArrayList<ArrayList<Character>> rows;
    private ArrayList<String> foldInstructions;

    public static void main(String[] args) {
        Day13 d = new Day13();
        d.run();
    }
}
