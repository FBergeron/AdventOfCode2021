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

class Day17 {

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

    public Day17() {
    }

    private String readInput(String filename) {
        String input = null;
        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line = bufReader.readLine();

            int indexOfX = line.indexOf("x=");
            int indexOfY = line.indexOf(", y=");
            int indexOfXDelim = line.indexOf("..");
            int indexOfYDelim = line.indexOf("..", indexOfXDelim + 2);

            String strX1 = line.substring(indexOfX + 2, indexOfXDelim);
            targetAreaX1 = Integer.valueOf(strX1);

            String strX2 = line.substring(indexOfXDelim + 2, indexOfY);
            targetAreaX2 = Integer.valueOf(strX2);

            String strY1 = line.substring(indexOfY + 4, indexOfYDelim);
            targetAreaY1 = Integer.valueOf(strY1);

            String strY2 = line.substring(indexOfYDelim + 2);
            targetAreaY2 = Integer.valueOf(strY2);
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
        return input;
    }

    public boolean launchProbe(Point velocity) {
        int maxY = Integer.MIN_VALUE;
        Point pos = new Point(0, 0);
        for (int step = 0; ; step++) {
            pos = new Point(pos.x + velocity.x, pos.y + velocity.y);
            velocity = new Point(velocity.x == 0 ? 0 : velocity.x + (velocity.x > 0 ? -1 : 1), velocity.y - 1);
System.out.println( "After step " + (step + 1) + ", pos=" + pos + " velocity=" + velocity);

            if (pos.y > maxY)
                maxY = pos.y;

            if (pos.x >= targetAreaX1 && pos.x <= targetAreaX2 && pos.y >= targetAreaY1 && pos.y <= targetAreaY2) {
                System.out.println("In target! maxY=" + maxY);
                return true;
            }

            if (pos.y < targetAreaY1) {
                System.out.println("Too far!");
                return false;
            }
        }
    }

    public void run() {
        //String input = readInput("input_test.txt");
        String input = readInput("input.txt");

        System.out.println("target area: x=" + targetAreaX1 + ".." + targetAreaX2 + ", y=" + targetAreaY1 + " .." + targetAreaY2);

        //Point velocity = new Point(7, 2);
        //launchProbe(velocity);

        //Point velocity = new Point(6, 3);
        //launchProbe(velocity);

        //Point velocity = new Point(9, 0);
        //launchProbe(velocity);

        //Point velocity = new Point(17, -4);
        //launchProbe(velocity);

        //Point velocity = new Point(6, 9);
        //launchProbe(velocity);

        // Point velocity = new Point(1, 10);
        // launchProbe(velocity);

        HashSet<Point> rightVelocities = new HashSet<Point>();
        for (int x = 1; x <= 300; x++) {
            for (int y = 300; y >= -300; y--) {
                System.out.println("v=(" + x + ", " + y + ")");
                Point velocity = new Point(x, y);
                boolean isRight = launchProbe(velocity);
                if (isRight)
                    rightVelocities.add(velocity);
            }
        }
        System.out.println("rightVelocities.size: " + rightVelocities.size());

    }

    public static void main(String[] args) {
        Day17 d = new Day17();
        d.run();
    }

    private int targetAreaX1;
    private int targetAreaX2;
    private int targetAreaY1;
    private int targetAreaY2;

}
