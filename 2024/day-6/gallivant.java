import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GuardGallivant {

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

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Gallivant [filename]");
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

            currRow = startRow; currCol = startCol;
            int delRow = -1; int delCol = 0;

            while (isInbound(laboratory, currRow, currCol)) {
                laboratory.get(currRow).set(currCol, 'X');

                if (isInbound(laboratory, currRow + delRow, currCol + delCol) && isObstacle(laboratory, currRow + delRow, currCol + delCol)) {
                    int tmp = delRow;
                    delRow = delCol;
                    delCol = -tmp;
                }

                currRow += delRow; currCol += delCol;
            }

            int sum = 0;
            for (List<Character> row : laboratory) {
                for (char c : row) {
                    if (c == 'X') {
                        sum++;
                    }
                }
            }

            s.close();

            System.out.println("Gallivant sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}