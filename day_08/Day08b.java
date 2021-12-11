import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Day08b {

    public class Entry {

        public Entry(String[] patterns, String[] digits) {
            this.patterns = new String[patterns.length];
            ArrayList<String> tempPatterns = new ArrayList<String>();
            for (int i = 0; i < patterns.length; i++) {
                String[] chars = patterns[i].split("");
                Arrays.sort(chars);
                this.patterns[i] = String.join("", chars);
            }

            this.digits = new String[digits.length];
            ArrayList<String> tempdigits = new ArrayList<String>();
            for (int i = 0; i < digits.length; i++) {
                String[] chars = digits[i].split("");
                Arrays.sort(chars);
                this.digits[i] = String.join("", chars);
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            String delim = "";
            for (int i = 0; i < patterns.length; i++) {
                builder.append(delim);
                builder.append(patterns[i]);
                delim = " ";
            }
            builder.append(" | ");
            delim = "";
            for (int i = 0; i < digits.length; i++) {
                builder.append(delim);
                builder.append(digits[i]);
                delim = " ";
            }
            return builder.toString();
        }

        public String[] patterns;
        public String[] digits;

    }

    /**
     * Leds are identified by numbers like this:
     *
     *           -- 0 --
     *          |       |
     *          1       2
     *          |       |
     *           -- 3 --
     *          |       |
     *          4       5
     *          |       |
     *           -- 6 --
     *
     */
    public class SolutionValidator {

        public SolutionValidator(HashMap<Character, Integer> assocCharLed, String[] digitPatterns) {
            this.assocCharLed = assocCharLed;
            this.digitPatterns = digitPatterns;

            HashSet<Integer> digits0 = new HashSet<Integer>();
            digits0.add(Integer.valueOf(0));
            digits0.add(Integer.valueOf(2));
            digits0.add(Integer.valueOf(5));
            digits0.add(Integer.valueOf(6));
            digits0.add(Integer.valueOf(4));
            digits0.add(Integer.valueOf(1));
            digits.add(digits0);

            HashSet<Integer> digits1 = new HashSet<Integer>();
            digits1.add(Integer.valueOf(2));
            digits1.add(Integer.valueOf(5));
            digits.add(digits1);

            HashSet<Integer> digits2 = new HashSet<Integer>();
            digits2.add(Integer.valueOf(0));
            digits2.add(Integer.valueOf(2));
            digits2.add(Integer.valueOf(3));
            digits2.add(Integer.valueOf(4));
            digits2.add(Integer.valueOf(6));
            digits.add(digits2);

            HashSet<Integer> digits3 = new HashSet<Integer>();
            digits3.add(Integer.valueOf(0));
            digits3.add(Integer.valueOf(2));
            digits3.add(Integer.valueOf(3));
            digits3.add(Integer.valueOf(5));
            digits3.add(Integer.valueOf(6));
            digits.add(digits3);

            HashSet<Integer> digits4 = new HashSet<Integer>();
            digits4.add(Integer.valueOf(1));
            digits4.add(Integer.valueOf(2));
            digits4.add(Integer.valueOf(3));
            digits4.add(Integer.valueOf(5));
            digits.add(digits4);

            HashSet<Integer> digits5 = new HashSet<Integer>();
            digits5.add(Integer.valueOf(0));
            digits5.add(Integer.valueOf(1));
            digits5.add(Integer.valueOf(3));
            digits5.add(Integer.valueOf(5));
            digits5.add(Integer.valueOf(6));
            digits.add(digits5);

            HashSet<Integer> digits6 = new HashSet<Integer>();
            digits6.add(Integer.valueOf(0));
            digits6.add(Integer.valueOf(1));
            digits6.add(Integer.valueOf(3));
            digits6.add(Integer.valueOf(4));
            digits6.add(Integer.valueOf(5));
            digits6.add(Integer.valueOf(6));
            digits.add(digits6);

            HashSet<Integer> digits7 = new HashSet<Integer>();
            digits7.add(Integer.valueOf(0));
            digits7.add(Integer.valueOf(2));
            digits7.add(Integer.valueOf(5));
            digits.add(digits7);

            HashSet<Integer> digits8 = new HashSet<Integer>();
            digits8.add(Integer.valueOf(0));
            digits8.add(Integer.valueOf(1));
            digits8.add(Integer.valueOf(2));
            digits8.add(Integer.valueOf(3));
            digits8.add(Integer.valueOf(4));
            digits8.add(Integer.valueOf(5));
            digits8.add(Integer.valueOf(6));
            digits.add(digits8);

            HashSet<Integer> digits9 = new HashSet<Integer>();
            digits9.add(Integer.valueOf(0));
            digits9.add(Integer.valueOf(1));
            digits9.add(Integer.valueOf(2));
            digits9.add(Integer.valueOf(3));
            digits9.add(Integer.valueOf(5));
            digits9.add(Integer.valueOf(6));
            digits.add(digits9);
        }

        public boolean isValid() {
            for (int i = 0; i < digitPatterns.length; i++) {
                if (!isDigitPatternValid(digitPatterns[i], i))
                    return false;
            }
            return true;
        }

        private boolean isDigitPatternValid(String pattern, int digit) {
            Set<Integer> activatedLeds = new HashSet<Integer>();
            for (int i = 0; i < pattern.length(); i++)
                activatedLeds.add(assocCharLed.get(Character.valueOf(pattern.charAt(i))));
            return (activatedLeds.equals(digits.get(Integer.valueOf(digit))));
        }

        private String[] digitPatterns;
        private HashMap<Character, Integer> assocCharLed;
        private ArrayList<HashSet<Integer>> digits = new ArrayList<HashSet<Integer>>();

    }

    private ArrayList<Entry> readInput(String filename) {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                Entry entry = new Entry(parts[0].split(" "), parts[1].split(" "));
                entries.add(entry);
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
        return entries;
    }

    public void run() {
        //ArrayList<Entry> entries = readInput("input_very_short.txt");
        //ArrayList<Entry> entries = readInput("input_short.txt");
        ArrayList<Entry> entries = readInput("input.txt");
        System.out.println("entries="+entries);

        // HashMap<Character, Integer> solution = new HashMap<Character, Integer>();
        // solution.put(Character.valueOf('d'), Integer.valueOf(0));
        // solution.put(Character.valueOf('e'), Integer.valueOf(1));
        // solution.put(Character.valueOf('a'), Integer.valueOf(2));
        // solution.put(Character.valueOf('f'), Integer.valueOf(3));
        // solution.put(Character.valueOf('g'), Integer.valueOf(4));
        // solution.put(Character.valueOf('b'), Integer.valueOf(5));
        // solution.put(Character.valueOf('c'), Integer.valueOf(6));

        // String[] digitPatterns = new String[] {
        //     "cagedb",
        //     "ab",
        //     "gcdfa",
        //     "fbcad",
        //     "eafb",
        //     "cdfbe",
        //     "cdfgeb",
        //     "dab",
        //     "acedgfb",
        //     "cefabd"
        // };

        // SolutionValidator validator = new SolutionValidator(solution, digitPatterns);
        // System.out.println("isValid=" + validator.isValid());

        int sum = 0;
        for (Entry entry : entries) {
            HashMap<Character, Integer> assocCharLed = new HashMap<Character, Integer>();

            ArrayList<String> digitsWith5Leds = new ArrayList<String>();
            ArrayList<String> digitsWith6Leds = new ArrayList<String>();

            HashMap<Integer, String> assocDigitPattern = new HashMap<Integer, String>();
            for (int i = 0; i < entry.patterns.length; i++) {
                if (entry.patterns[i].length() == 2)
                    assocDigitPattern.put(Integer.valueOf(1), entry.patterns[i]);
                else if (entry.patterns[i].length() == 3)
                    assocDigitPattern.put(Integer.valueOf(7), entry.patterns[i]);
                else if (entry.patterns[i].length() == 4)
                    assocDigitPattern.put(Integer.valueOf(4), entry.patterns[i]);
                else if (entry.patterns[i].length() == 7)
                    assocDigitPattern.put(Integer.valueOf(8), entry.patterns[i]);
                else if (entry.patterns[i].length() == 5)
                    digitsWith5Leds.add(entry.patterns[i]);
                else if (entry.patterns[i].length() == 6)
                    digitsWith6Leds.add(entry.patterns[i]);
            }

            // Find led 0.
            String pattern7 = assocDigitPattern.get(Integer.valueOf(7));
            String pattern1 = assocDigitPattern.get(Integer.valueOf(1));
            for (int i = 0; i < pattern7.length(); i++) {
                if (pattern1.indexOf(pattern7.charAt(i)) == -1)
                    assocCharLed.put(Character.valueOf(pattern7.charAt(i)), Integer.valueOf(0));
            }

            // Find digit 6.
            String patternForDigit1 = assocDigitPattern.get(Integer.valueOf(1));
            boolean isDigit6Found = false;
            for (int i = 0; !isDigit6Found && i < digitsWith6Leds.size(); i++) {
                String digit = digitsWith6Leds.get(i);
                for (int j = 0; j < patternForDigit1.length(); j++) {
                    if (digit.indexOf(patternForDigit1.charAt(j)) == -1) {
                        assocDigitPattern.put(Integer.valueOf(6), digit);
                        isDigit6Found = true;
                        break;
                    }
                }
            }
            digitsWith6Leds.remove(assocDigitPattern.get(Integer.valueOf(6)));

            // Find leds 2 and 5.
            String patternForDigit6 = assocDigitPattern.get(Integer.valueOf(6));
            for (int i = 0; i < patternForDigit1.length(); i++) {
                char c = patternForDigit1.charAt(i);
                if (patternForDigit6.indexOf(c) == -1)
                    assocCharLed.put(Character.valueOf(c), Integer.valueOf(2));
                else
                    assocCharLed.put(Character.valueOf(c), Integer.valueOf(5));
            }

            // Find led 3 and digits 0 and 9.
            String patternForDigit4 = assocDigitPattern.get(Integer.valueOf(4));
            for (int i = 0; i < digitsWith6Leds.size(); i++) {
                boolean isDigit3Found = false;
                String digit = digitsWith6Leds.get(i);
                for (int j = 0; !isDigit3Found && j < patternForDigit4.length(); j++) {
                    char c = patternForDigit4.charAt(j);
                    if (digit.indexOf(c) == -1) {
                        assocCharLed.put(Character.valueOf(c), Integer.valueOf(3));
                        assocDigitPattern.put(Integer.valueOf(0), digit);
                        System.out.println("Found 3");
                        isDigit3Found = true;
                        break;
                    }
                }
                if (!isDigit3Found) {
                    assocDigitPattern.put(Integer.valueOf(9), digit);
                        System.out.println("Found 9");
                }
            }

            // Find led 4 and digit 2.
            String patternForDigit9 = assocDigitPattern.get(Integer.valueOf(9));
            boolean isDigit2Found = false;
            for (int i = 0; !isDigit2Found && i < digitsWith5Leds.size(); i++) {
                String digit = digitsWith5Leds.get(i);
                for (int j = 0; j < patternForDigit9.length(); j++) {
                    ArrayList<Character> chars = new ArrayList<Character>();
                    for (int k = 0; k < digit.length(); k++)
                        chars.add(Character.valueOf(digit.charAt(k)));

                    for (Iterator<Character> iter = chars.listIterator(); iter.hasNext(); ) {
                        Character c = iter.next();
                        if (patternForDigit9.indexOf(c) != -1)
                            iter.remove();
                    }
                    if (chars.size() == 1) {
                        assocDigitPattern.put(Integer.valueOf(2), digit);
                        assocCharLed.put(chars.get(0), Integer.valueOf(4));
                        isDigit2Found = true;
                        break;
                    }
                }
            }
            digitsWith5Leds.remove(assocDigitPattern.get(Integer.valueOf(2)));

            // Find digits 3 and 5 and leds 1.
            String patternForDigit2 = assocDigitPattern.get(Integer.valueOf(2));
            for (int i = 0; i < digitsWith5Leds.size(); i++) {
                String digit = digitsWith5Leds.get(i);
                for (int j = 0; j < patternForDigit2.length(); j++) {
                    ArrayList<Character> chars = new ArrayList<Character>();
                    for (int k = 0; k < digit.length(); k++)
                        chars.add(Character.valueOf(digit.charAt(k)));

                    for (Iterator<Character> iter = chars.listIterator(); iter.hasNext(); ) {
                        Character c = iter.next();
                        if (patternForDigit2.indexOf(c) != -1)
                            iter.remove();
                    }
                    if (chars.size() == 2) {
                        assocDigitPattern.put(Integer.valueOf(5), digit);
                        for (Iterator<Character> iter = chars.listIterator(); iter.hasNext();) {
                            Character c = iter.next();
                            if (!assocCharLed.containsKey(c))
                                assocCharLed.put(c, Integer.valueOf(1));
                        }
                    }
                    else if (chars.size() == 1)
                        assocDigitPattern.put(Integer.valueOf(3), digit);
                }
            }

            // Find led 6.
            String patternForDigit8 = assocDigitPattern.get(Integer.valueOf(8));
            for (int i = 0; i < patternForDigit8.length(); i++) {
                Character c = Character.valueOf(patternForDigit8.charAt(i));
                if (!assocCharLed.containsKey(c)) {
                    assocCharLed.put(c, Integer.valueOf(6));
                    break;
                }
            }

            HashMap<String, Integer> assocPatternDigit = new HashMap<String, Integer>();
            for (Integer k : assocDigitPattern.keySet())
                assocPatternDigit.put(assocDigitPattern.get(k), k);

            // System.out.println("assocDigitPattern=" + assocDigitPattern);
            // System.out.println("assocCharLed=" + assocCharLed);
            // System.out.println("digitsWith5Leds=" + digitsWith5Leds);
            // System.out.println("digitsWith6Leds=" + digitsWith6Leds);
            // System.out.println("assocPatternDigit=" + assocPatternDigit);
            // System.out.println("");
            // System.out.println("");
            // System.out.println("");

            StringBuilder strValue = new StringBuilder();
            for (int i = 0; i < entry.digits.length; i++) {
                Integer digit = assocPatternDigit.get(entry.digits[i]);
                strValue.append(digit + "");
            }
            // System.out.println("strValue=" + strValue);

            sum += Integer.valueOf(strValue.toString());
        }
        System.out.println("sum="+sum);
    }

    public static void main(String[] args) {
        Day08b d = new Day08b();
        d.run();
    }
}

