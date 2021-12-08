import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class Day06 {

    private ArrayList<Integer> readInput(String filename) {
        ArrayList<Integer> timers = new ArrayList<Integer>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line = bufReader.readLine();
            String[] strTimers = line.split(",");
            for (int i = 0; i < strTimers.length; i++) {
                timers.add(Integer.valueOf(strTimers[i]));
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
        return timers;
    }

    public void run() {
        ArrayList<Integer> timers = readInput("input_short.txt");
        //ArrayList<Integer> timers = readInput("input.txt");
        System.out.println("timers="+timers);

        for (int d = 0; d < 256; d++) {
            int newLanternFish = 0;
            for (int i = 0; i < timers.size(); i++) {
                Integer timer = timers.get(i);
                Integer newTimer;
                if (timer.intValue() == 0) {
                    newTimer = Integer.valueOf(6);
                    newLanternFish++;
                }
                else {
                    newTimer = Integer.valueOf(timer.intValue() - 1);
                }
                timers.remove(i);
                timers.add(i, newTimer);
            }
            for (int i = 0; i < newLanternFish; i++) {
                timers.add(Integer.valueOf(8));
            }
            System.out.println("After " + (d+1) + " days.");
            //System.out.println("After " + (d+1) + " days: " + timers);
        }

        System.out.println("timers.size=" + timers.size());
    }

    public static void main(String[] args) {
        Day06 d = new Day06();
        d.run();
    }
}
