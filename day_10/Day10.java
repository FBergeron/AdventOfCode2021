import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class Day10 {

    public Day10() {
        scores.put(')', Integer.valueOf(3));
        scores.put(']', Integer.valueOf(57));
        scores.put('}', Integer.valueOf(1197));
        scores.put('>', Integer.valueOf(25137));

        openingMarks.put(Character.valueOf(')'), Character.valueOf('('));
        openingMarks.put(Character.valueOf(']'), Character.valueOf('['));
        openingMarks.put(Character.valueOf('}'), Character.valueOf('{'));
        openingMarks.put(Character.valueOf('>'), Character.valueOf('<'));
    }

    private ArrayList<String> readInput(String filename) {
        ArrayList<String> lines = new ArrayList<String>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                lines.add(line);
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
        //ArrayList<String> lines = readInput("input_short.txt");
        ArrayList<String> lines = readInput("input.txt");
        System.out.println("lines="+lines);

        int score = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            System.out.println("line="+line);

            Stack<Character> levels = new Stack<Character>();

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Character cv = Character.valueOf(c);
                if (c == '(' || c == '[' || c == '{' || c == '<')
                    levels.push(cv);
                else if (c == ')' || c == ']' || c == '}' || c == '>') {
                    Character lastOpeningMark = levels.peek();
                    if (openingMarks.get(cv) != lastOpeningMark) {
                        System.out.println("Expecting " + lastOpeningMark + " but got " + c);
                        score += scores.get(cv);
                        break;
                    }
                    else
                        levels.pop();
                }
            }
        }
        System.out.println("score="+score);
    }

    private HashMap<Character, Integer> scores = new HashMap<Character, Integer>();
    private HashMap<Character, Character> openingMarks = new HashMap<Character, Character>();

    public static void main(String[] args) {
        Day10 d = new Day10();
        d.run();
    }
}
