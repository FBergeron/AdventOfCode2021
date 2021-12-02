import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class Day02b {

    private ArrayList<String> readCommands(String filename) {
        ArrayList<String> commands = new ArrayList<String>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                try {
                    commands.add(line);
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
        return commands;
    }

    public void run() {
        ArrayList<String> commands = readCommands("input.txt");

        int depth = 0;
        int horizPos = 0;
        int aim = 0;
        for(String command : commands) {
            int indexOfSpace = command.indexOf(" ");
            String direction = command.substring(0, indexOfSpace);
            int value = Integer.valueOf(command.substring(indexOfSpace + 1));
            System.out.println("direction="+direction+" value="+value);

            if ("forward".equals(direction)) {
                horizPos += value;
                depth += value * aim;
            }
            else if ("up".equals(direction))
                aim -= value;
            else if ("down".equals(direction))
                aim += value;
        }

        System.out.println("depth=" + depth + " horizPos=" + horizPos);
        System.out.println("Product=" + (depth * horizPos));
    }

    public static void main(String[] args) {
        Day02b d = new Day02b();
        d.run();
    }
}



