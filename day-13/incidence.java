import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Incidence {

    static List<List<Integer>> extractTable (Scanner s) {
        List<List<Integer>> table = new ArrayList<List<Integer>>();

        while (s.hasNextLine()) {
            char [] line = s.nextLine().toCharArray();
            if (line.length <= 1) {
                break;
            }

            List<Integer> row = new ArrayList<Integer>();

            for (char c : line) {
                row.add(((c == '#') ? 1 : 0));
            }

            table.add(row);
        }

        return table;
    }

    static int findVerticalMirror(List<List<Integer>> table) {
        int rowCount = table.size(); 
        int colCount = table.get(0).size();

        for (int m = 1; m < colCount; m++) {
            boolean mirror = true;

            for (int row = 0; row < rowCount; row += 1) {
                List<Integer> currRow = table.get(row);

                int left = m - 1;
                int right = m;

                while (left >= 0 && right < colCount) {
                    if (currRow.get(left) != currRow.get(right)) {
                        mirror = false;
                        break;
                    }

                    left -= 1;
                    right += 1;
                }

                if (!mirror) {
                    break;
                }
            }

            if (mirror) {
                return m;
            }
        }

        return -1;
    }

    static int findHorizontalMirror(List<List<Integer>> table) {
        int rowCount = table.size(); 
        int colCount = table.get(0).size();

        for (int m = 1; m < rowCount; m++) {
            boolean mirror = true;

            for (int col = 0; col < colCount; col += 1) {
                int top = m - 1;
                int bottom = m;

                while (top >= 0 && bottom < rowCount) {
                    if (table.get(top).get(col) != table.get(bottom).get(col)) {
                        mirror = false;
                        break;
                    }

                    top -= 1;
                    bottom += 1;
                }

                if (!mirror) {
                    break;
                }
            }

            if (mirror) {
                return m;
            }
        }

        return -1;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Incidence [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                List<List<Integer>> table = extractTable(s);

                int horizontalMirror = -1;
                int verticalMirror = findVerticalMirror(table);

                if (verticalMirror == -1) {
                    horizontalMirror = findHorizontalMirror(table);
                }

                sum += ((horizontalMirror != -1) ? (100 * horizontalMirror) : 0);
                sum += ((verticalMirror != -1) ? verticalMirror : 0);
            }

            System.out.println("Mirror sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}