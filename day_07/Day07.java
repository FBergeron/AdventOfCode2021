import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

class Day07 {

    private ArrayList<Integer> readInput(String filename) {
        ArrayList<Integer> crabPositions = new ArrayList<Integer>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line = bufReader.readLine();
            String[] strTimers = line.split(",");
            for (int i = 0; i < strTimers.length; i++) {
                crabPositions.add(Integer.valueOf(strTimers[i]));
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
        return crabPositions;
    }

    class FuelCalculator {

        public FuelCalculator() {
            fuelCostPerMove.put(Integer.valueOf(0), Integer.valueOf(0));
            maxMoveKey = 0;
        }

        int computeFuelCost(int move) {
            Integer moveKey = Integer.valueOf(move);
            if (fuelCostPerMove.containsKey(moveKey))
                return fuelCostPerMove.get(moveKey).intValue();

            int i = maxMoveKey + 1;
            for (; i <= move; i++) {
                System.out.println("i="+i);
                Integer prevMoveCost = fuelCostPerMove.get(i - 1);
                Integer moveCost = Integer.valueOf(prevMoveCost.intValue() + i);
                fuelCostPerMove.put(Integer.valueOf(i), moveCost);
            }
            maxMoveKey = move;

            return fuelCostPerMove.get(move);
        }

        public HashMap<Integer, Integer> fuelCostPerMove = new HashMap<Integer, Integer>();
        private int maxMoveKey;

    }

    public void run() {
        //ArrayList<Integer> crabPositions = readInput("input_short.txt");
        ArrayList<Integer> crabPositions = readInput("input.txt");
        System.out.println("crabPositions="+crabPositions);

        FuelCalculator calc = new FuelCalculator();

        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        for (int p = 0; p < crabPositions.size(); p++) {
            Integer crabPos = crabPositions.get(p);
            if (crabPos < minPos)
                minPos = crabPos.intValue();
            if (crabPos > maxPos)
                maxPos = crabPos.intValue();
        }

        int cheapestPos = -666;
        int minFuelCost = Integer.MAX_VALUE;
        for (int i = minPos; i <= maxPos; i++) {
            int fuelCost = 0;
            for (int p = 0; p < crabPositions.size(); p++) {
                Integer crabPos = crabPositions.get(p);

                // Part 1
                //fuelCost += Math.abs(crabPos.intValue() - i);

                // Part 2
                fuelCost += calc.computeFuelCost(Math.abs(crabPos.intValue() - i));
            }
            if (fuelCost < minFuelCost) {
                minFuelCost = fuelCost;
                cheapestPos = i;
            }
        }

        System.out.println("The cheapest pos was " + cheapestPos + " with a cost of " + minFuelCost + " fuel units.");
        //System.out.println("calc="+calc.fuelCostPerMove);
    }

    public static void main(String[] args) {
        Day07 d = new Day07();
        d.run();
    }
}
