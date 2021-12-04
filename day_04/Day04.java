import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class Day04 {

    private int[] numbers = null;
    private ArrayList<int[][]> boards = null;

    private int computeScore(int[][] board, ArrayList<Integer> numbers) {
        int sumOfUnmarkedNumbers = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c =0; c < board.length; c++) {
                if (!numbers.contains(Integer.valueOf(board[r][c])))
                    sumOfUnmarkedNumbers += board[r][c];
            }
        }
        return numbers.get(numbers.size() - 1) * sumOfUnmarkedNumbers;
    }

    private boolean isWinningBoard(int[][] board, ArrayList<Integer> numbers) {
        System.out.println("isWinningBoard numbers="+numbers);
        // Check rows.
        for (int r = 0; r < board.length; r++) {
            System.out.println("Checking r="+r);
            boolean isWinning = true;
            for (int c = 0; c < board.length; c++) {
                System.out.println("Checking " + board[r][c]);
                if (!numbers.contains(Integer.valueOf(board[r][c]))) {
                    isWinning = false;
                    break;
                }
            }
            if (isWinning) {
                System.out.println("Winning row!");
                System.out.println("Numbers were " + numbers);
                String delim = "";
                for (int c = 0; c < board.length; c++) {
                    System.out.print(delim + board[r][c]);
                    delim = ",";
                }
                System.out.println("");
                return true;
            }
        }
        // Check cols.
        for (int c = 0; c < board.length; c++) {
            System.out.println("Checking c="+c);
            boolean isWinning = true;
            for (int r = 0; r < board.length; r++) {
                System.out.println("Checking " + board[r][c]);
                if (!numbers.contains(Integer.valueOf(board[r][c]))) {
                    isWinning = false;
                    break;
                }
            }
            if (isWinning) {
                System.out.println("Winning col!");
                String delim = "";
                for (int r = 0; r < board.length; r++) {
                    System.out.print(delim + board[r][c]);
                    delim = ",";
                }
                System.out.println("");
                return true;
            }
        }
        return false;
    }

    private void readInput(String filename) {
        ArrayList<Integer> tempNumbers = new ArrayList<Integer>();

        ArrayList<ArrayList<Integer>> tempBoardNumbers = new ArrayList<ArrayList<Integer>>();

        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                if (tempNumbers.size() == 0) {
                    String[] strNumbers = line.split(",");
                    for (int i = 0; i < strNumbers.length; i++)
                        tempNumbers.add(Integer.valueOf(strNumbers[i]));
                }
                else if (line.equals("")) {
                    System.out.println("empty line");
                    if (tempBoardNumbers.size() > 0) {

                        int[][] tempBoard = new int[tempBoardNumbers.size()][tempBoardNumbers.get(0).size()];
                        for (int i = 0; i < tempBoardNumbers.size(); i++) {
                            ArrayList<Integer> tempBoardLine = tempBoardNumbers.get(i);
                            for (int j = 0; j < tempBoardLine.size(); j++) {
                                tempBoard[i][j] = tempBoardLine.get(j).intValue();
                            }
                        }
                        if (boards == null)
                            boards = new ArrayList<int[][]>();
                        boards.add(tempBoard);

                        tempBoardNumbers.clear();
                    }
                }
                else {
                    ArrayList<Integer> tempBoardLine = new ArrayList<Integer>();
                    System.out.println("line="+line);
                    String[] strNumbers = line.trim().split("\\s+");
                    for (int i = 0; i < strNumbers.length; i++)
                        tempBoardLine.add(Integer.valueOf(strNumbers[i]));
                    tempBoardNumbers.add(tempBoardLine);
                }
            }
            if (tempBoardNumbers.size() > 0) {

                int[][] tempBoard = new int[tempBoardNumbers.size()][tempBoardNumbers.get(0).size()];
                for (int i = 0; i < tempBoardNumbers.size(); i++) {
                    ArrayList<Integer> tempBoardLine = tempBoardNumbers.get(i);
                    for (int j = 0; j < tempBoardLine.size(); j++) {
                        tempBoard[i][j] = tempBoardLine.get(j).intValue();
                    }
                }
                if (boards == null)
                    boards = new ArrayList<int[][]>();
                boards.add(tempBoard);

                tempBoardNumbers.clear();
            }

            numbers = new int[tempNumbers.size()];
            for (int i = 0; i < tempNumbers.size(); i++)
                numbers[i] = tempNumbers.get(i).intValue();
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
    }

    public void run() {
        readInput("input.txt");
        System.out.println("board count="+boards.size());

        ArrayList<Integer> tempNumbers = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++) {

            tempNumbers.add(Integer.valueOf(numbers[i]));

            // Check if there is a winning board if we have enough numbers.
            if (i >= 5) {
                for (int j = 0; j < boards.size(); j++) {
                    int[][] board = boards.get(j);
                    if (isWinningBoard(board, tempNumbers)) {
                        System.out.println("Winning board="+j+" after " + numbers[i]);
                        int score = computeScore(board, tempNumbers);
                        System.out.println("Score="+score);
                        return;
                    }

                }
            }

        }
    }

    public static void main(String[] args) {
        Day04 d = new Day04();
        d.run();
    }
}
