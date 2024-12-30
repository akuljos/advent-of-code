import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class GuardGallivantV2 {

    static boolean isInbound(List<List<Character>> laboratory, int row, int col) {
        if (row < 0 || row >= laboratory.size() || col < 0 || col >= laboratory.get(row).size()) {
            return false;
        }
        return true;
    }

    static boolean isObstacle(List<List<Character>> laboratory, int row, int col) {
        if (laboratory.get(row).get(col) == '#') {
            return true;
        }
        return false;
    }

    static int checkInfiniteLoop(List<List<Character>> laboratory, int startRow, int startCol, int maxSteps) {
        int currRow = startRow; int currCol = startCol;
        int delRow = -1; int delCol = 0;

        int numSteps = 0;

        while (numSteps < maxSteps) {
            if (!isInbound(laboratory, currRow, currCol)) {
                return 0;
            }

            while (isInbound(laboratory, currRow + delRow, currCol + delCol) && isObstacle(laboratory, currRow + delRow, currCol + delCol)) {
                int tmp = delRow;
                delRow = delCol;
                delCol = -tmp;
            }

            currRow += delRow; currCol += delCol;
            numSteps += 1;
        }
        
        return 1;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java GallivantV2 [filename]");
            return;
        }

        String filename = args[0];

        List<List<Character>> laboratory = new ArrayList<List<Character>>();
        int startRow = -1; int startCol = -1;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int currRow = 0; int currCol = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();

                List<Character> row = new ArrayList<Character>();
                currCol = 0;

                for (char c : line.toCharArray()) {
                    row.add(c);
                    if (c == '^') {
                        startRow = currRow;
                        startCol = currCol;
                    }
                    currCol++;
                }
                laboratory.add(row);
                currRow++;
            }

            s.close();

            int numRows = currRow; int numCols = currCol;
            int sum = 0;

            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    // checkInfiniteLoop
                    if (laboratory.get(row).get(col) == '.') {
                        laboratory.get(row).set(col, '#');
                        sum += checkInfiniteLoop(laboratory, startRow, startCol, numRows * numCols);
                        laboratory.get(row).set(col, '.');
                    }
                }
            }

            System.out.println("Gallivant sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}