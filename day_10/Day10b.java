import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

class Day10b {

    public Day10b() {
        scores.put(')', Integer.valueOf(1));
        scores.put(']', Integer.valueOf(2));
        scores.put('}', Integer.valueOf(3));
        scores.put('>', Integer.valueOf(4));

        openingMarks.put(Character.valueOf(')'), Character.valueOf('('));
        openingMarks.put(Character.valueOf(']'), Character.valueOf('['));
        openingMarks.put(Character.valueOf('}'), Character.valueOf('{'));
        openingMarks.put(Character.valueOf('>'), Character.valueOf('<'));

        closingMarks.put(Character.valueOf('('), Character.valueOf(')'));
        closingMarks.put(Character.valueOf('['), Character.valueOf(']'));
        closingMarks.put(Character.valueOf('{'), Character.valueOf('}'));
        closingMarks.put(Character.valueOf('<'), Character.valueOf('>'));
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

        ArrayList<Long> allScores = new ArrayList<Long>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            System.out.println("line="+line);

            Stack<Character> levels = new Stack<Character>();

            boolean isCorrupted = false;
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Character cv = Character.valueOf(c);
                if (c == '(' || c == '[' || c == '{' || c == '<')
                    levels.push(cv);
                else if (c == ')' || c == ']' || c == '}' || c == '>') {
                    Character lastOpeningMark = levels.peek();
                    if (openingMarks.get(cv) != lastOpeningMark) {
                        System.out.println("Expecting " + lastOpeningMark + " but got " + c);
                        isCorrupted = true;
                        break;
                    }
                    else
                        levels.pop();
                }
            }
            if (!isCorrupted) {
                long score = 0;
                System.out.println("levels="+levels);
                while (!levels.empty()) {
                    Character topCharacter = closingMarks.get(levels.pop());
                    //System.out.println("topCharacter="+topCharacter);
                    score = score * 5 + scores.get(topCharacter).intValue();
                }
                System.out.println("score="+score);
                allScores.add(Long.valueOf(score));
            }
        }
        Collections.sort(allScores);
        System.out.println("allScores="+allScores);
        long middleScore = allScores.get(allScores.size() / 2).longValue();
        System.out.println("middleScore="+middleScore);
    }

    private HashMap<Character, Integer> scores = new HashMap<Character, Integer>();
    private HashMap<Character, Character> openingMarks = new HashMap<Character, Character>();
    private HashMap<Character, Character> closingMarks = new HashMap<Character, Character>();

    public static void main(String[] args) {
        Day10b d = new Day10b();
        d.run();
    }
}
