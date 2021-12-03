import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class Day03 {

    private ArrayList<String> readBinaryNumbers(String filename) {
        ArrayList<String> numbers = new ArrayList<String>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                try {
                    numbers.add(line);
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
        return numbers;
    }

    public void run() {
        ArrayList<String> numbers = readBinaryNumbers("input_short.txt");
        System.out.print(numbers);

        int[] positiveBits = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] negativeBits = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i);
            for (int d = 0; d < 12; d++) {
                char bit = number.charAt(d);
                if ('1' == bit)
                    positiveBits[d] += 1;
                else
                    negativeBits[d] += 1;
            }
        }
        StringBuffer strGammaRate = new StringBuffer();
        StringBuffer strEpsilonRate = new StringBuffer();
        ListArray<String> oxigenGeneratorRate = new ListArray<String>();
        ListArray<String> co2ScrubbingRate = new ListArray<String>();
        for (int d = 0; d < 12; d++) {
            if (positiveBits[d] > negativeBits[d]) {
                strGammaRate.append("1");
                strEpsilonRate.append("0");
            }
            else {
                strGammaRate.append("0");
                strEpsilonRate.append("1");
            }
        }
        System.out.println("strGammaRate="+strGammaRate);
        System.out.println("strEpsilonRate="+strEpsilonRate);
        int gammaRate = Integer.parseInt(strGammaRate.toString(), 2);
        int epsilonRate = Integer.parseInt(strEpsilonRate.toString(), 2);
        System.out.println(gammaRate);
        System.out.println(epsilonRate);
        int powerConsumption = gammaRate * epsilonRate;
        System.out.println("powerConsumption="+powerConsumptiong);

    }

    public static void main(String[] args) {
        Day03 d = new Day03();
        d.run();
    }
}



