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

    public int[] getPositiveBits(ArrayList<String> numbers) {
        int[] positiveBits = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i);
            for (int d = 0; d < 12; d++) {
                char bit = number.charAt(d);
                if ('1' == bit)
                    positiveBits[d] += 1;
            }
        }
        return positiveBits;
    }

    public int[] getNegativeBits(ArrayList<String> numbers) {
        int[] negativeBits = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i);
            for (int d = 0; d < 12; d++) {
                char bit = number.charAt(d);
                if ('0' == bit)
                    negativeBits[d] += 1;
            }
        }
        return negativeBits;
    }


    public void run() {
        ArrayList<String> numbers = readBinaryNumbers("input.txt");
        System.out.print(numbers);

        int[] positiveBits = getPositiveBits(numbers);
        int[] negativeBits = getNegativeBits(numbers);
        StringBuffer strGammaRate = new StringBuffer();
        StringBuffer strEpsilonRate = new StringBuffer();
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
        System.out.println("positiveBits");
        for (int d = 0; d < 12; d++)
            System.out.println(positiveBits[d]);
        System.out.println("negativeBits");
        for (int d = 0; d < 12; d++)
            System.out.println(negativeBits[d]);
        System.out.println("negativeBits="+negativeBits);
        System.out.println("strGammaRate="+strGammaRate);
        System.out.println("strEpsilonRate="+strEpsilonRate);
        int gammaRate = Integer.parseInt(strGammaRate.toString(), 2);
        int epsilonRate = Integer.parseInt(strEpsilonRate.toString(), 2);
        System.out.println(gammaRate);
        System.out.println(epsilonRate);
        int powerConsumption = gammaRate * epsilonRate;
        System.out.println("powerConsumption="+powerConsumption);

        ArrayList<String> oxigenGeneratorRate = new ArrayList<String>();
        oxigenGeneratorRate.addAll(numbers);
        ArrayList<String> co2ScrubbingRate = new ArrayList<String>();
        co2ScrubbingRate.addAll(numbers);
        System.out.println("oxigenGeneratorRate="+oxigenGeneratorRate);
        System.out.println("co2ScrubbingRate="+co2ScrubbingRate);
        for (int d = 0; d < 12; d++) {
            int[] positiveBitsOxigen = getPositiveBits(oxigenGeneratorRate);
            int[] negativeBitsOxigen = getNegativeBits(oxigenGeneratorRate);

            int[] positiveBitsCo2 = getPositiveBits(co2ScrubbingRate);
            int[] negativeBitsCo2 = getNegativeBits(co2ScrubbingRate);

            char oxigenGeneratorBitCriteria = positiveBitsOxigen[d] >= negativeBitsOxigen[d] ? '1' : '0';
            char co2ScrubbingBitCriteria = negativeBitsCo2[d] <= positiveBitsCo2[d] ? '0' : '1';
            System.out.println("d="+d);
            System.out.println("oxigenGeneratorBitCriteria="+oxigenGeneratorBitCriteria);
            System.out.println("co2ScrubbingBitCriteria="+co2ScrubbingBitCriteria);
            if (oxigenGeneratorRate.size() > 1) {
                ArrayList<String> numbersToRemove = new ArrayList<String>();
                for (int i = 0; i < oxigenGeneratorRate.size(); i++) {
                    String number = oxigenGeneratorRate.get(i);
                    char bit = number.charAt(d);
                    if (bit != oxigenGeneratorBitCriteria) {
                        numbersToRemove.add(number);
                        System.out.println("remove " + number + " for oxigenGeneratorRate");
                    }
                }
                for (int i = 0; i < numbersToRemove.size(); i++) {
                    oxigenGeneratorRate.remove(numbersToRemove.get(i));
                }
            }
            if (co2ScrubbingRate.size() > 1) {
                ArrayList<String> numbersToRemove = new ArrayList<String>();
                for (int i = 0; i < co2ScrubbingRate.size(); i++) {
                    String number = co2ScrubbingRate.get(i);
                    char bit = number.charAt(d);
                    if (bit != co2ScrubbingBitCriteria) {
                        numbersToRemove.add(number);
                        System.out.println("remove " + number + " for co2ScrubbingRate");
                    }
                }
                for (int i = 0; i < numbersToRemove.size(); i++) {
                    co2ScrubbingRate.remove(numbersToRemove.get(i));
                }
            }
        }
        System.out.println("oxigenGeneratorRate="+oxigenGeneratorRate);
        System.out.println("co2ScrubbingRate="+co2ScrubbingRate);
        int oxigenGenRate = Integer.parseInt(oxigenGeneratorRate.get(0), 2);
        int co2ScrubRate = Integer.parseInt(co2ScrubbingRate.get(0), 2);
        System.out.println("oxigenGenRate="+oxigenGenRate);
        System.out.println("co2ScrubRate="+co2ScrubRate);
        int lifeSupportRate = oxigenGenRate * co2ScrubRate;
        System.out.println("lifeSupportRate="+lifeSupportRate);
    }

    public static void main(String[] args) {
        Day03 d = new Day03();
        d.run();
    }
}



