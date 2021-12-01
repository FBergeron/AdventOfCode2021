import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class Test {

    private ArrayList<Integer> readHeights(String filename) {
        ArrayList<Integer> heights = new ArrayList<Integer>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                try {
                    heights.add(Integer.valueOf(line));
                }
                catch(NumberFormatException e) {
                    e.printStackTrace();
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
        return heights;
    }

    public void run() {
        ArrayList<Integer> heights = readHeights("input.txt");
        System.out.println(heights);

        int increase = 0;
        Integer prevHeight = null;
        for (int i = 0; i < heights.size(); i++) {
            Integer height = heights.get(i);

            if (prevHeight != null && height > prevHeight)
                increase++;

            prevHeight = height;
        }
        System.out.println(increase);
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.run();
    }
}

