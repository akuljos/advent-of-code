import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CeresSearch {

    static int xmasSearch(List<char[]> grid, int row, int col, int delRow, int delCol, int pos) {
        if (row < 0 || row >= grid.size() || col < 0 || col >= grid.get(row).length || (delRow == 0 && delCol == 0)) {
            return 0;
        }

        char c = grid.get(row)[col];

        if (c == 'S' && pos == 3) {
            return 1;
        }

        if ((c == 'X' && pos == 0) || (c == 'M' && pos == 1) || (c == 'A' && pos == 2)) {
            return xmasSearch(grid, row + delRow, col + delCol, delRow, delCol, pos + 1);
        }

        return 0;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CeresSearch [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<char[]> grid = new ArrayList<char[]>();
            int sum = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                grid.add(line.toCharArray());
            }

            for (int row = 0; row < grid.size(); row++) {
                for (int col = 0; col < grid.get(row).length; col++) {
                    for (int delRow = -1; delRow <= 1; delRow++) {
                        for (int delCol = -1; delCol <= 1; delCol++) {
                            sum += xmasSearch(grid, row, col, delRow, delCol, 0);
                        }
                    }
                }
            }

            s.close();

            System.out.println("Ceres sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}