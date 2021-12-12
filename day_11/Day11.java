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
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Day11 {

    class EnergyLevelBoard extends JPanel {

        public EnergyLevelBoard(ArrayList<ArrayList<Integer>> rows) {
            this.rows = rows;
        }

        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int y = 0; y < rows.size(); y++) {
                ArrayList<Integer> row = rows.get(y);

                for (int x = 0; x < row.size(); x++) {
                    Integer energyLevel = row.get(x);
                    g.setColor(new Color(energyLevel.intValue() * 25, energyLevel.intValue() * 25, energyLevel.intValue() * 25));
                    g.fillRect(x * 40, y * 40, 40, 40);
                }
            }
        }

        public void setRows(ArrayList<ArrayList<Integer>> rows) {
            this.rows = rows;
            repaint();
        }

        private ArrayList<ArrayList<Integer>> rows;
    }

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

    public Day11() {
    }

    private ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                ArrayList<Integer> energyLevels = new ArrayList<Integer>();
                for (int c = 0; c < line.length(); c++)
                    energyLevels.add(Integer.valueOf(line.charAt(c) + ""));
                rows.add(energyLevels);
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
        return rows;
    }


    private void increaseEnergyLevel(Point p, ArrayList<Point> flashedOctopus, ArrayList<Point> flashingOctopus) {
        ArrayList<Integer> row = rows.get(p.y);
        Integer energyLevel = row.get(p.x);
        if (energyLevel < 10 && !flashedOctopus.contains(p)) {
            Integer newEnergyLevel = Integer.valueOf(energyLevel.intValue() + 1);
            row.remove(p.x);
            row.add(p.x, newEnergyLevel);
            if (newEnergyLevel.intValue() == 10) {
                flashingOctopus.add(p);
                totalFlashes++;
            }
        }
    }

    public void run() {
        //rows = readInput("input_short.txt");
        rows = readInput("input.txt");
        System.out.println("rows="+rows);

        JFrame frame = new JFrame("Octopus Energy Level");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelMain = (JPanel)frame.getContentPane();

        EnergyLevelBoard board = new EnergyLevelBoard(rows);
        //JButton buttonNextStep = new JButton("Next Step");
        //buttonNextStep.addActionListener(
        //    new ActionListener() {
        //        public void actionPerformed(ActionEvent evt) {
        //            board.setRows(rows);
        //        }
        //    }
        //);

        panelMain.add(board, BorderLayout.CENTER);
        //panelMain.add(buttonNextStep, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        totalFlashes = 0;
        for (int step = 0; step < 195; step++) {
            ArrayList<Point> flashedOctopus = new ArrayList<Point>();
            ArrayList<Point> flashingOctopus = new ArrayList<Point>();
            for (int y = 0; y < rows.size(); y++) {
                ArrayList<Integer> row = rows.get(y);
                for (int x = 0; x < row.size(); x++) {
                    Point p = new Point(x, y);
                    increaseEnergyLevel(p, flashedOctopus, flashingOctopus);
                }
            }

            while(flashingOctopus.size() > 0) {

                for (int i = 0; i < flashingOctopus.size(); i++) {

                    Point p = flashingOctopus.get(i);

                    // Top row.
                    if (p.y > 0) {
                        ArrayList<Integer> topRow = rows.get(p.y - 1);

                        // Top left point.
                        if (p.x > 0) {
                            Point topLeftPoint = new Point(p.x - 1, p.y - 1);
                            increaseEnergyLevel(topLeftPoint, flashedOctopus, flashingOctopus);
                        }

                        // Top point.
                        Point topPoint = new Point(p.x, p.y - 1);
                        increaseEnergyLevel(topPoint, flashedOctopus, flashingOctopus);

                        // Top right point.
                        if (p.x < topRow.size() - 1) {
                            Point topRightPoint = new Point(p.x + 1, p.y - 1);
                            increaseEnergyLevel(topRightPoint, flashedOctopus, flashingOctopus);
                        }
                    }

                    // Middle row.
                    ArrayList<Integer> middleRow = rows.get(p.y);

                    // Left point.
                    if (p.x > 0) {
                        Point leftPoint = new Point(p.x - 1, p.y);
                        increaseEnergyLevel(leftPoint, flashedOctopus, flashingOctopus);
                    }

                    // Center point.
                    middleRow.remove(p.x);
                    middleRow.add(p.x, Integer.valueOf(0));

                    // Right point.
                    if (p.x < middleRow.size() - 1) {
                        Point rightPoint = new Point(p.x + 1, p.y);
                        increaseEnergyLevel(rightPoint, flashedOctopus, flashingOctopus);
                    }

                    // Bottom row.
                    if (p.y < rows.size() - 1) {
                        ArrayList<Integer> bottomRow = rows.get(p.y + 1);

                        // Bottom left point.
                        if (p.x > 0) {
                            Point bottomLeftPoint = new Point(p.x - 1, p.y + 1);
                            increaseEnergyLevel(bottomLeftPoint, flashedOctopus, flashingOctopus);
                        }

                        // Bottom point.
                        Point bottomPoint = new Point(p.x, p.y + 1);
                        increaseEnergyLevel(bottomPoint, flashedOctopus, flashingOctopus);

                        // Bottom right point.
                        if (p.x < bottomRow.size() - 1) {
                            Point bottomRightPoint = new Point(p.x + 1, p.y + 1);
                            increaseEnergyLevel(bottomRightPoint, flashedOctopus, flashingOctopus);
                        }
                    }

                    flashingOctopus.remove(p);
                    flashedOctopus.add(p);
                }

            }

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch(InterruptedException ignore) {
            }

            board.setRows(rows);
        }
        System.out.println("rows="+rows);
        System.out.println("totalFlashes="+totalFlashes);

    }

    private int totalFlashes;
    private ArrayList<ArrayList<Integer>> rows;

    public static void main(String[] args) {
        Day11 d = new Day11();
        d.run();
    }
}
