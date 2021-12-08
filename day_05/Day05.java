import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class Day05 {

    public static final int height = 1000;
    public static final int width = 1000;

    class Matrix {

        Matrix() {
            data = new ArrayList<ArrayList<Integer>>();
            for (int r = 0; r < height; r++) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                for (int c = 0; c < width; c++) {
                    row.add(Integer.valueOf(0));
                }
                data.add(row);
            }
        }

        public void addLine(Line line) {
            System.out.println("addLine line="+line);

            int x1 = line.x1;
            int y1 = line.y1;
            int x2 = line.x2;
            int y2 = line.y2;
            int incrX, incrY;

            if (line.x2 > line.x1)
                incrX = 1;
            else if (line.x2 < line.x1)
                incrX = -1;
            else
                incrX = 0;

            if (line.y2 > line.y1)
                incrY = 1;
            else if (line.y2 < line.y1)
                incrY = -1;
            else
                incrY = 0;

            int x = x1;
            int y = y1;
            for (;;) {
                System.out.println("Start loop x="+x+" y="+y);

                ArrayList<Integer> row = data.get(y);
                Integer cell = row.get(x);
                System.out.println("cell="+cell);
                row.remove(x);
                Integer newCell = Integer.valueOf(cell.intValue() + 1);
                if (cell.intValue() == 1)
                    peakHigherThan2Count++;
                if (newCell.intValue() > maxHeight)
                    maxHeight = newCell.intValue();
                row.add(x, newCell);

                x += incrX;
                y += incrY;

                System.out.println("End loop x="+x+" y="+y);

                if (line.x2 == line.x1) {
                    if ((line.y2 > line.y1 && y > line.y2) || (line.y2 < line.y1 && y < line.y2))
                        break;
                }
                else if (line.y2 == line.y1) {
                    if ((line.x2 > line.x1 && x > line.x2) || (line.x2 < line.x1 && x < line.x2))
                        break;
                }
                else if ((line.x2 > line.x1 && x > line.x2) ||
                         (line.x2 < line.x1 && x < line.x2) ||
                         (line.y2 > line.y1 && y > line.y2) ||
                         (line.y2 < line.y1 && y < line.y2))
                    break;
            }
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        public int getPeakHigherThan2Count() {
            return peakHigherThan2Count;
        }

        public String toString() {
            StringBuffer str = new StringBuffer();
            for (int r = 0; r < height; r++) {
                ArrayList<Integer> row = data.get(r);
                for (int c = 0; c < width; c++) {
                    Integer cell = row.get(c);
                    if (cell.intValue() == 0)
                        str.append(".");
                    else
                        str.append(cell + "");
                }
                str.append("\n");
            }
            return str.toString();
        }

        private ArrayList<ArrayList<Integer>> data;
        private int maxHeight = 0;
        private int peakHigherThan2Count =0;

    }

    class Line {

        Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public int x1;
        public int y1;
        public int x2;
        public int y2;

        public String toString() {
            return x1 + "," + y1 + " -> " + x2 + "," + y2;
        }

    }

    private ArrayList<Line> readInput(String filename) {
        ArrayList<Line> lines = new ArrayList<Line>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                String coordDelim = " -> ";
                int indexOfCoordDelim = line.indexOf(coordDelim);
                String coord1 = line.substring(0, indexOfCoordDelim);
                String coord2 = line.substring(indexOfCoordDelim + coordDelim.length());

                int indexOfComma = coord1.indexOf(",");
                String x1 = coord1.substring(0, indexOfComma);
                String y1 = coord1.substring(indexOfComma + 1);

                indexOfComma = coord2.indexOf(",");
                String x2 = coord2.substring(0, indexOfComma);
                String y2 = coord2.substring(indexOfComma + 1);

                Line l = new Line(Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(x2), Integer.valueOf(y2));
                lines.add(l);
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
        return lines;
    }

    public void run() {
        //ArrayList<Line> lines = readInput("input_short.txt");
        ArrayList<Line> lines = readInput("input.txt");
        System.out.println("lines="+lines);
        Matrix m = new Matrix();
        System.out.println("Before m=");
        System.out.println(m);

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            //if (line.x1 == line.x2 || line.y1 == line.y2) {
                System.out.println("Processing line="+line);
                m.addLine(line);
                System.out.println("After m=");
                System.out.println(m);
            //}
        }

        System.out.println("maxHeight=" + m.getMaxHeight());
        System.out.println("peakHigherThan2Count=" + m.getPeakHigherThan2Count());
    }

    public static void main(String[] args) {
        Day05 d = new Day05();
        d.run();
    }
}
