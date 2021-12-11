import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

class Day09 {

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

    public boolean isLowPoint(int x, int y, ArrayList<String> rows) {
        int height = Integer.valueOf(rows.get(y).charAt(x) + "").intValue();

        int topHeight = Integer.MAX_VALUE;
        if (y > 0)
            topHeight = Integer.valueOf(rows.get(y - 1).charAt(x) + "").intValue();

        int bottomHeight = Integer.MAX_VALUE;
        if (y < rows.size() - 1)
            bottomHeight = Integer.valueOf(rows.get(y + 1).charAt(x) + "").intValue();

        int leftHeight = Integer.MAX_VALUE;
        if (x > 0)
            leftHeight = Integer.valueOf(rows.get(y).charAt(x - 1) + "").intValue();

        int rightHeight = Integer.MAX_VALUE;
        if (x < rows.get(y).length() - 1)
            rightHeight = Integer.valueOf(rows.get(y).charAt(x + 1) + "").intValue();

        // System.out.println( "topHeight="+topHeight );
        // System.out.println( "bottomHeight="+bottomHeight );
        // System.out.println( "leftHeight="+leftHeight );
        // System.out.println( "rightHeight="+rightHeight );

        return height < topHeight && height < bottomHeight && height < leftHeight && height < rightHeight;
    }

    public void run() {
        //ArrayList<String> rows = readInput("input_short.txt");
        ArrayList<String> rows = readInput("input.txt");
        System.out.println("rows="+rows);

        int sum = 0;
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(0).length(); x++) {
                if (isLowPoint(x, y, rows)) {
                    int height = Integer.valueOf(rows.get(y).charAt(x) + "").intValue();
                    int riskLevel = 1 + height;
                    sum += riskLevel;
                }
            }
        }
        System.out.println("sum=" + sum);
    }

    public static void main(String[] args) {
        Day09 d = new Day09();
        d.run();
    }
}
