import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

class Day06b {

    private HashMap<Integer, Long> readInput(String filename) {
        HashMap<Integer, Long> timers = new HashMap<Integer, Long>();
        for (int i = 0; i < 9; i++)
            timers.put(Integer.valueOf(i), Long.valueOf(0));

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line = bufReader.readLine();
            String[] strTimers = line.split(",");
            for (int i = 0; i < strTimers.length; i++) {
                Integer timer = Integer.valueOf(strTimers[i]);
                //if (!timers.containsKey(timer))
                //    timers.put(timer, Integer.valueOf(1));
                //else {
                    Long prevValue = timers.get(timer);
                    timers.put(timer, Long.valueOf(prevValue.longValue() + 1));
                //}
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
        //HashMap<Integer, Long> timers = readInput("input_short.txt");
        HashMap<Integer, Long> timers = readInput("input.txt");
        System.out.println("timers="+timers);

        for (int d = 0; d < 256; d++) {
            long newLanternFish = 0;
            for (int i = 0; i < 9; i++) {
                Integer key = Integer.valueOf(i);
                Long value = timers.get(key);
                if (key.intValue() == 0)
                    newLanternFish = value.longValue();
                else {
                    Integer lowerKey = Integer.valueOf(key.intValue() - 1);
                    timers.put(lowerKey, value);
                }
                timers.put(key, Long.valueOf(0));
            }
            if (newLanternFish > 0) {
                timers.put(Integer.valueOf(6), Long.valueOf(timers.get(Integer.valueOf(6)).longValue() + newLanternFish));
                timers.put(Integer.valueOf(8), Long.valueOf(timers.get(Integer.valueOf(8)).longValue() + newLanternFish));
            }
            System.out.println("timers after day " + (d+1) +": "+timers);
        }

        long fishCount = 0;
        for (int i = 0; i < 9; i++) {
            fishCount += timers.get(Integer.valueOf(i));
        }
        System.out.println("Number of fish: " + fishCount);
    }

    public static void main(String[] args) {
        Day06b d = new Day06b();
        d.run();
    }
}

