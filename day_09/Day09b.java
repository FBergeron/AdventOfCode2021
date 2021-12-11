import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

class Day09b {

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

        public int x;
        public int y;

    }

    private ArrayList<String> readInput(String filename) {
        ArrayList<String> rows = new ArrayList<String>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                rows.add(line);
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

    public boolean isInsideNewBasin(int x, int y, ArrayList<String> rows, HashSet<Point> processedPoints) {
        int height = Integer.valueOf(rows.get(y).charAt(x) + "").intValue();

        return (height < 9 && !processedPoints.contains(new Point(x ,y)));
    }

    public void processBasin(int x, int y, ArrayList<String> rows, HashSet<Point> processedPoints, HashSet<Point> basinPoints) {
        Point p = new Point(x, y);
        processedPoints.add(new Point(x, y));
    }

    public void checkBasin(int x, int y, ArrayList<String> rows, HashSet<Point> processedPoints, HashSet<Point> basinPoints) {
        //System.out.println("checkBasin x="+x+" y="+y);
        int height = Integer.valueOf(rows.get(y).charAt(x) + "").intValue();

        Point p = new Point(x, y);

        if (height == 9 || processedPoints.contains(p)) {
            processedPoints.add(p);
            return;
        }

        processedPoints.add(p);
        basinPoints.add(p);

        int topHeight = Integer.MAX_VALUE;
        Point topPoint = null;
        if (y > 0) {
            topHeight = Integer.valueOf(rows.get(y - 1).charAt(x) + "").intValue();
            topPoint = new Point(x, y - 1);
        }

        int leftHeight = Integer.MAX_VALUE;
        Point leftPoint = null;
        if (x > 0) {
            leftHeight = Integer.valueOf(rows.get(y).charAt(x - 1) + "").intValue();
            leftPoint = new Point(x -1, y);
        }

        int rightHeight = Integer.MAX_VALUE;
        Point rightPoint = null;
        if (x < rows.get(y).length() - 1) {
            rightHeight = Integer.valueOf(rows.get(y).charAt(x + 1) + "").intValue();
            rightPoint = new Point(x + 1, y);
        }

        int bottomHeight = Integer.MAX_VALUE;
        Point bottomPoint = null;
        if (y < rows.size() - 1) {
            bottomHeight = Integer.valueOf(rows.get(y + 1).charAt(x) + "").intValue();
            bottomPoint = new Point(x, y + 1);
        }

        if (topPoint != null) {
            checkBasin(topPoint.x, topPoint.y, rows, processedPoints, basinPoints);
        }
        if (leftPoint != null) {
            checkBasin(leftPoint.x, leftPoint.y, rows, processedPoints, basinPoints);
        }
        if (rightPoint != null) {
            checkBasin(rightPoint.x, rightPoint.y, rows, processedPoints, basinPoints);
        }
        if (bottomPoint != null) {
            checkBasin(bottomPoint.x, bottomPoint.y, rows, processedPoints, basinPoints);
        }
    }

    public void run() {
        //ArrayList<String> rows = readInput("input_short.txt");
        ArrayList<String> rows = readInput("input.txt");
        System.out.println("rows="+rows);

        ArrayList<Integer> basinSizes = new ArrayList<Integer>();
        HashSet<Point> processedPoints = new HashSet<Point>();

        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(0).length(); x++) {
                HashSet<Point> basinPoints = new HashSet<Point>();
                checkBasin(x, y, rows, processedPoints, basinPoints);
                if (basinPoints.size() > 0) {
                    System.out.println("Found a basin of size: " + basinPoints.size());
                    basinSizes.add(Integer.valueOf(basinPoints.size()));
                }
            }
        }
        Collections.sort(basinSizes, Collections.reverseOrder());
        //System.out.println("basinSizes=" + basinSizes);

        int product = 1;
        for (int i = 0; i < 3; i++) {
            product *= basinSizes.get(i).intValue();
        }
        System.out.println("product="+product);

    }

    public static void main(String[] args) {
        Day09b d = new Day09b();
        d.run();
    }
}

