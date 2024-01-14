import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Parabolic {

    static List<List<Integer>> extractTable (Scanner s) {
        List<List<Integer>> table = new ArrayList<List<Integer>>();

        while (s.hasNextLine()) {
            char [] line = s.nextLine().toCharArray();
            List<Integer> row = new ArrayList<Integer>();

            for (char c : line) {
                switch (c) {
                    case '.':
                        row.add(0);
                        break;
                    case 'O':
                        row.add(1);
                        break;
                    case '#':
                        row.add(2);
                        break;
                    default:
                        row.add(3);
                }
            }

            table.add(row);
        }

        return table;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Parabolic [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Integer>> table = extractTable(s);
            
            int numRows = table.size();
            int numCols = table.get(0).size();

            for (int col = 0; col < numCols; col++) {
                List<Integer> emptyRows = new ArrayList<Integer>();

                for (int row = 0; row < numRows; row++) {
                    int pos = table.get(row).get(col);

                    switch (pos) {
                        case 0:
                            emptyRows.add(row);
                            break;
                        case 1:
                            if (emptyRows.size() != 0) {
                                int newRow = emptyRows.remove(0);

                                table.get(newRow).set(col, 1);
                                table.get(row).set(col, 0);

                                emptyRows.add(row);
                            }
                            break;
                        case 2:
                            emptyRows = new ArrayList<Integer>();
                        default:
                            break;
                    }
                }
            }

            int sum = 0;

            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (table.get(row).get(col) == 1) {
                        sum += (numRows - row);
                    }
                }
            }
            
            System.out.println("Parabolic sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}