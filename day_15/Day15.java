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
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Day15 {

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

    public Day15() {
    }

    private void readInput(String filename) {
        rows = new ArrayList<ArrayList<Integer>>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            boolean isFirstSectionOver = false;
            String line;
            while ((line = bufReader.readLine()) != null) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                for (int i = 0; i < line.length(); i++) {
                    row.add(Integer.valueOf(line.charAt(i) + ""));
                }
                rows.add(row);
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

    private void showPath(ArrayList<Point> path, int riskCost, boolean isKept) {
        for (int y = 0; y < rows.size(); y++) {
            ArrayList<Integer> row = rows.get(y);

            for (int x = 0; x < row.size(); x++) {
                Point p = new Point(x, y);
                if (path.contains(p) || (p.x == 0 && p.y == 0))
                    System.out.print(row.get(x));
                else
                    System.out.print(" ");
            }
            if (y == 0)
                System.out.println("  Cost: " + riskCost + " " + (isKept ? "Kept" : "Discarded"));
            else
                System.out.println("");
        }
        System.out.println("");
    }

    //public void travelRec(Point p, ArrayList<Point> path, int riskCumulCost) {
    //    path.add(p);
    //    riskCumulCost += rows.get(p.y).get(p.x).intValue();

    //    if (p.equals(end)) {
    //        boolean isBetter = riskCumulCost < lowestRiskCost;
    //        if (isBetter)
    //            showPath(path, riskCumulCost, isBetter);
    //            System.out.println("lowestCost="+riskCumulCost);

    //        if (isBetter) {
    //            lowestRiskCost = riskCumulCost;
    //            bestPath = path;
    //        }
    //        return;
    //    }

    //    if (riskCumulCost + (rows.size() - p.x) + (rows.size() - p.y) > lowestRiskCost) {
    //        showPath(path, riskCumulCost, false);
    //        //System.out.println("Discarded path with len=" + path.size());
    //        return;
    //    }

    //    Point right = null;
    //    if (p.x < rows.size() - 1) {
    //        right = new Point(p.x + 1, p.y);
    //        if (!path.contains(right)) {
    //            ArrayList<Point> recPath = new ArrayList<Point>();
    //            recPath.addAll(path);
    //            travelRec(right, recPath, riskCumulCost);
    //        }
    //    }

    //    Point bottom = null;
    //    if (p.y < rows.size() - 1) {
    //        bottom = new Point(p.x, p.y + 1);
    //        if (!path.contains(bottom)) {
    //            ArrayList<Point> recPath = new ArrayList<Point>();
    //            recPath.addAll(path);
    //            travelRec(bottom, recPath, riskCumulCost);
    //        }
    //    }

    //    Point top = null;
    //    if (p.y > 0) {
    //        top = new Point(p.x, p.y - 1);
    //        if (!path.contains(top)) {
    //            ArrayList<Point> recPath = new ArrayList<Point>();
    //            recPath.addAll(path);
    //            travelRec(top, recPath, riskCumulCost);
    //        }
    //    }

    //    Point left = null;
    //    if (p.x > 0) {
    //        left = new Point(p.x - 1, p.y);
    //        if (!path.contains(left)) {
    //            ArrayList<Point> recPath = new ArrayList<Point>();
    //            recPath.addAll(path);
    //            travelRec(left, recPath, riskCumulCost);
    //        }
    //    }
    //}

    public void travelRec(Point p, ArrayList<Point> path, int riskCumulCost, Point end) {
        System.out.println("travelRec p="+p + " path="+path);
        path.add(p);
        riskCumulCost += rows.get(p.y).get(p.x).intValue();

        if (p.equals(end)) {
            boolean isBetter = riskCumulCost < lowestRiskCost;
            if (isBetter)
                showPath(path, riskCumulCost, isBetter);
                System.out.println("lowestCost="+riskCumulCost);

            if (isBetter) {
                lowestRiskCost = riskCumulCost;
                bestPath = path;
            }
            return;
        }

        //if (discardedPoints.contains(p))
        //    return;

        if (riskCumulCost /*+ (end.x - p.x) + (end.y - p.y)*/ > lowestRiskCost) {
            showPath(path, riskCumulCost, false);
            System.out.println("Discarded path with len=" + path.size());
            //discardedPoints.add(p);
            return;
        }

        ArrayList<Point> points = new ArrayList<Point>();

        Point right = null;
        if (p.x < end.x) {
            right = new Point(p.x + 1, p.y);
//System.out.println( "right="+right );
            if (!path.contains(right))
                points.add(right);
        }

        Point bottom = null;
        if (p.y < end.y) {
            bottom = new Point(p.x, p.y + 1);
//System.out.println( "bottom="+bottom );
            if (!path.contains(bottom))
                points.add(bottom);
        }

        Point top = null;
        if (p.y > 0) {
            top = new Point(p.x, p.y - 1);
//System.out.println( "top="+top );
            if (!path.contains(top))
                points.add(top);
        }

        Point left = null;
        if (p.x > 0) {
            left = new Point(p.x - 1, p.y);
//System.out.println( "left="+left );
            if (!path.contains(left))
                points.add(left);
        }

        if (points.size() > 0) {
            //System.out.println("Before");
            //for (int i = 0; i < points.size(); i++) {
            //    Point point = points.get(i);
            //    System.out.println("p=" + point + " score=" + getScore(point));
            //}

            //Collections.sort(points, new PointComparator());

            //System.out.println("After");
            //for (int i = 0; i < points.size(); i++) {
            //    Point point = points.get(i);
            //    System.out.println("p=" + point + " score=" + getScore(point));
            //}
            //for (Point point : points) {
            for (int i = 0; i < points.size()/* && i < 4*/; i++) {
                Point point = points.get(i);
                ArrayList<Point> recPath = new ArrayList<Point>();
                recPath.addAll(path);
                travelRec(point, recPath, riskCumulCost, end);
            }
        }
    }

    class PointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            //return rows.get(p2.y).get(p2.x).intValue() - rows.get(p1.y).get(p1.x).intValue();
            //return rows.get(p1.y).get(p1.x).intValue() - rows.get(p2.y).get(p2.x).intValue();
            int score1 = getScore(p1);
            int score2 = getScore(p2);
System.out.println( "compare score1="+score1+ " score2="+score2);
            return score1 - score2;
        }
    }

    public int getScore(Point p) {
        int score = rows.get(p.y).get(p.x).intValue();
        if (p.x > 0)
            score += rows.get(p.y).get(p.x - 1).intValue();
        if (p.x < rows.size() - 1)
            score += rows.get(p.y).get(p.x + 1).intValue();
        if (p.y > 0)
            score += rows.get(p.y - 1).get(p.x).intValue();
        if (p.y < rows.size() - 1)
            score += rows.get(p.y + 1).get(p.x).intValue();
        return score;
    }


    public void travel(Point start, Point end) {
        lowestRiskCost = Integer.MAX_VALUE;
        ArrayList<Point> bestPath = null;
        Point right = new Point(start.x + 1, start.y);
        Point bottom = new Point(start.x, start.y + 1);
        if (rows.get(right.y).get(right.x).intValue() < rows.get(bottom.y).get(bottom.x).intValue()) {
            travelRec(right, new ArrayList<Point>(), 0, end);
            travelRec(bottom, new ArrayList<Point>(), 0, end);
        }
        else {
            travelRec(bottom, new ArrayList<Point>(), 0, end);
            travelRec(right, new ArrayList<Point>(), 0, end);
        }
    }

    public void run() {
        readInput("input_test.txt");
        //readInput("input.txt");

        Point start = new Point(0, 0);
        //end = new Point(rows.size() - 1, rows.size() - 1);
        Point end = new Point(rows.size() - 1, rows.size() - 1);
        //Point end = new Point(4, 4);
        //Point end = new Point(rows.size() - 1, rows.size() - 1);

        travel(start, end);
        System.out.println("bestPath=" + bestPath);
        System.out.println("lowestRiskCost=" + lowestRiskCost);
    }

    //public void run() {
    //    readInput("input_test.txt");
    //    //readInput("input.txt");

    //    bestCosts = new ArrayList<ArrayList<Integer>>();
    //    for (int y = 0; y < rows.size(); y++) {
    //        ArrayList<Integer> bestCostsRow = new ArrayList<Integer>();
    //        for (int x = 0; x < rows.size(); x++) {
    //            Point start = new Point(0, 0);
    //            if (x == 0 && y == 0)
    //                bestCostsRow.add(Integer.valueOf(0));
    //            else {
    //                Point end = new Point(x, y);
    //                travel(start, end);
    //                System.out.println("x=" + x + " y=" + y + " -> " + lowestRiskCost);
    //                bestCostsRow.add(lowestRiskCost);
    //            }
    //        }
    //        bestCosts.add(bestCostsRow);
    //    }
    //    for (int y = 0; y < bestCosts.size(); y++) {
    //        ArrayList<Integer> bestCostsRow = bestCosts.get(y);
    //        String delim = "";
    //        for (int x = 0; x < bestCostsRow.size(); x++) {
    //            System.out.print(delim + bestCostsRow.get(x));
    //            delim = " ";
    //        }
    //        System.out.println("");
    //    }
    //}


    private ArrayList<ArrayList<Integer>> rows;
    private ArrayList<ArrayList<Integer>> bestCosts;
    private ArrayList<Point> bestPath = null;
    private Integer lowestRiskCost = Integer.MAX_VALUE;
    //private Integer lowestRiskCost = 300;
    //private HashSet<Point> discardedPoints = new HashSet<Point>();

    public static void main(String[] args) {
        Day15 d = new Day15();
        d.run();
    }
}
