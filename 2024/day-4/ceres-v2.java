import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CeresSearchV2 {

    static int crossCheck(List<char[]> grid, int row, int col) {
        boolean backSlash = (grid.get(row-1)[col-1] == 'M' && grid.get(row+1)[col+1] == 'S')
                        ||  (grid.get(row-1)[col-1] == 'S' && grid.get(row+1)[col+1] == 'M');
        boolean forwardSlash = (grid.get(row-1)[col+1] == 'M' && grid.get(row+1)[col-1] == 'S')
                           ||  (grid.get(row-1)[col+1] == 'S' && grid.get(row+1)[col-1] == 'M');

        return ((backSlash && forwardSlash) ? 1 : 0);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CeresSearchV2 [filename]");
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

            for (int row = 1; row < (grid.size() - 1); row++) {
                for (int col = 1; col < (grid.get(row).length - 1); col++) {
                    if (grid.get(row)[col] == 'A') {
                        sum += crossCheck(grid, row, col);
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