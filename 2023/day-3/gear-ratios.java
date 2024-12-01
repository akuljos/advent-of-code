import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class GearRatios {

    static List<ArrayList<Character>> gearboard = new ArrayList<ArrayList<Character>>();
    static List<ArrayList<Integer>> gearboardTracker = new ArrayList<ArrayList<Integer>>();

    static int numRows = 0;
    static int numCols = 0;

    private static void dfs(int x, int y) {

        if ((x < 0) || (x >= numRows) || (y < 0) 
            || (y >= numCols) || (!Character.isDigit(gearboard.get(x).get(y))) || (gearboardTracker.get(x).get(y) == 1)) {
                return;
        }

        gearboardTracker.get(x).set(y, 1);
        dfs(x,y-1);
        dfs(x,y+1);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java GearRatios [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                ArrayList<Character> gearboardRow = new ArrayList<Character>();
                ArrayList<Integer> gearboardTrackerRow = new ArrayList<Integer>();

                for (char c : s.nextLine().toCharArray()) {
                    gearboardRow.add(c);
                    gearboardTrackerRow.add(0);
                }

                numCols = gearboardRow.size();
                numRows += 1;

                gearboard.add(gearboardRow);
                gearboardTracker.add(gearboardTrackerRow);           
            }

            for (int x = 0; x < numRows; x++) {
                for (int y = 0; y < numCols; y++) {
                    if (!Character.isDigit(gearboard.get(x).get(y)) && gearboard.get(x).get(y) != '.') {
                        dfs(x-1, y-1);
                        dfs(x-1, y);
                        dfs(x-1, y+1);
                        dfs(x, y-1);
                        dfs(x, y+1);
                        dfs(x+1, y-1);
                        dfs(x+1, y);
                        dfs(x+1, y+1);
                    }
                }
            }     

            int sum = 0;

            for (int x = 0; x < numRows; x++) {
                int currentVal = 0;

                for (int y = 0; y < numCols; y++) {
                    if (gearboardTracker.get(x).get(y) == 1) {
                        currentVal = 10 * currentVal + (gearboard.get(x).get(y) - '0');
                    } else {
                        sum += currentVal;
                        currentVal = 0;
                    }
                }

                sum += currentVal;
            }

            System.out.println("The gear ratio sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }
}