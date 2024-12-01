import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LavaFloorV2 {

    static List<char []> extractTable (Scanner s) {
        List<char []> table = new ArrayList<char []>();

        while (s.hasNextLine()) {
            table.add(s.nextLine().toCharArray());
        }

        return table;
    }

    static int calculateEnergySum (List<char []> table, int x, int y, int dir) {
        int numRows = table.size();
        int numCols = table.get(0).length;

        List<Integer> queueX = new ArrayList<Integer>(); queueX.add(x);
        List<Integer> queueY = new ArrayList<Integer>(); queueY.add(y);
        List<Integer> queueDir = new ArrayList<Integer>(); queueDir.add(dir);

        List<List<List<Integer>>> visited = new ArrayList<List<List<Integer>>>();
        
        for (int i = 0; i < 4; i++) {
            List<List<Integer>> currTable = new ArrayList<List<Integer>>();

            for (int row = 0; row < numRows; row++) {
                List<Integer> currRow = new ArrayList<Integer>();
                for (int col = 0; col < numCols; col++) {
                    currRow.add(0);
                }

                currTable.add(currRow);
            }

            visited.add(currTable);
        }

        List<List<Integer>> energized = new ArrayList<List<Integer>>();
        for (int row = 0; row < numRows; row++) {
            List<Integer> currRow = new ArrayList<Integer>();
            
            for (int col = 0; col < numCols; col++) {
                currRow.add(0);
            }

            energized.add(currRow);
        }

        while (queueX.size() > 0) {
            int currX = queueX.remove(0); int currY = queueY.remove(0); int currDir = queueDir.remove(0);

            if (currX < 0 || currY < 0 || currX >= numRows || currY >= numCols || visited.get(currDir).get(currX).get(currY) == 1) {
                continue;
            }
            visited.get(currDir).get(currX).set(currY, 1);
            energized.get(currX).set(currY, 1);

            char currPoint = table.get(currX)[currY];
            int nextX, nextY, nextDir;

            switch (currPoint) {
                case '\\':
                    nextX = currX + ((currDir == 0) ? 1 : 0) - ((currDir == 2) ? 1 : 0);
                    nextY = currY + ((currDir == 1) ? 1 : 0) - ((currDir == 3) ? 1 : 0);

                    nextDir = ((currDir % 2 == 0) ? (currDir + 1) : (currDir - 1));

                    queueX.add(nextX); queueY.add(nextY); queueDir.add(nextDir);
                    break;

                case '/':
                    nextX = currX + ((currDir == 2) ? 1 : 0) - ((currDir == 0) ? 1 : 0);
                    nextY = currY + ((currDir == 3) ? 1 : 0) - ((currDir == 1) ? 1 : 0);

                    nextDir = ((currDir % 2 == 1) ? ((currDir + 1) % 4) : ((currDir + 3) % 4)); 

                    queueX.add(nextX); queueY.add(nextY); queueDir.add(nextDir);
                    break;

                case '|':
                    if ((currDir % 2) == 0) {
                        nextX = currX + 1;
                        queueX.add(nextX); queueY.add(currY); queueDir.add(1);

                        nextX = currX - 1;
                        queueX.add(nextX); queueY.add(currY); queueDir.add(3);
                    } else {
                        nextX = currX + ((currDir == 1) ? 1 : 0) - ((currDir == 3) ? 1 : 0);
                        nextY = currY + ((currDir == 0) ? 1 : 0) - ((currDir == 2) ? 1 : 0);

                        nextDir = currDir;

                        queueX.add(nextX); queueY.add(nextY); queueDir.add(nextDir);
                    }
                    break;

                case '-':
                    if ((currDir % 2) == 1) {
                        nextY = currY + 1;
                        queueX.add(currX); queueY.add(nextY); queueDir.add(0);
                        
                        nextY = currY - 1;
                        queueX.add(currX); queueY.add(nextY); queueDir.add(2);
                    } else {
                        nextX = currX + ((currDir == 1) ? 1 : 0) - ((currDir == 3) ? 1 : 0);
                        nextY = currY + ((currDir == 0) ? 1 : 0) - ((currDir == 2) ? 1 : 0);

                        nextDir = currDir;

                        queueX.add(nextX); queueY.add(nextY); queueDir.add(nextDir);
                    }
                    break;

                default:
                    nextX = currX + ((currDir == 1) ? 1 : 0) - ((currDir == 3) ? 1 : 0);
                    nextY = currY + ((currDir == 0) ? 1 : 0) - ((currDir == 2) ? 1 : 0);

                    nextDir = currDir;

                    queueX.add(nextX); queueY.add(nextY); queueDir.add(nextDir);
            }
        }

        int sum = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (energized.get(row).get(col) == 1) {
                    sum += 1;
                }
            }
        }

        return sum;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LavaFloorV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<char []> table = extractTable(s);
            int maxSum = 0;

            int numRows = table.size();
            int numCols = table.get(0).length;

            for (int row = 0; row < numRows; row++) {
                maxSum = Math.max(maxSum, calculateEnergySum(table, row, 0, 0));
                maxSum = Math.max(maxSum, calculateEnergySum(table, row, numCols - 1, 2));
            }

            for (int col = 0; col < numCols; col++) {
                maxSum = Math.max(maxSum, calculateEnergySum(table, 0, col, 1));
                maxSum = Math.max(maxSum, calculateEnergySum(table, numRows - 1, col, 1));
            }

            System.out.println("Energized sum is " + maxSum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}