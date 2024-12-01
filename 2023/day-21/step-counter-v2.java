// find distance to each garden plot
// if distance is even, reachable; if distance is odd, unreachable

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class StepCounterV2 {

    static List<List<Integer>> extractTable (Scanner s) {
        List<List<Integer>> table = new ArrayList<List<Integer>>();

        while (s.hasNextLine()) {
            char [] line = s.nextLine().toCharArray();
            List<Integer> row = new ArrayList<Integer>();

            for (char c : line) {
                switch (c) {
                    case '#':
                        row.add(0);
                        break;
                    case '.':
                        row.add(1);
                        break;
                    case 'S':
                        row.add(2);
                        break;
                    default:
                        break;
                }
            }

            table.add(row);
        }

        return table;
    }

    static List<Integer> findStart (List<List<Integer>> table) {
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) == 2) {
                    List<Integer> start = Arrays.asList(new Integer[] { i, j });
                    table.get(i).set(j,1);
                    return start;
                }
            }
        }

        return null;
    }

    static int modulate (int number, int mod) {
        return ((number % mod) + mod) % mod;
    }

    static long reachableTest (List<List<Integer>> table, List<Integer> start, int numSteps) {
        int parity = numSteps % 2;

        List<Integer> xQueue = new ArrayList<Integer>(Arrays.asList(new Integer[] { start.get(0) }));
        List<Integer> yQueue = new ArrayList<Integer>(Arrays.asList(new Integer[] { start.get(1) }));
        List<Integer> distQueue = new ArrayList<Integer>(Arrays.asList(new Integer[] { 0 }));

        int numRows = table.size(); int numCols = table.get(0).size();
        Set<String> visited = new HashSet<String>();

        long reachable = 0;

        while (xQueue.size() > 0) {
            int xPos = xQueue.remove(0); int yPos = yQueue.remove(0); int dist = distQueue.remove(0);

            if (dist > numSteps || visited.contains(String.format("%d,%d", xPos, yPos)) || table.get(modulate(xPos, numRows)).get(modulate(yPos, numCols)) == 0) {
                continue;
            }

            visited.add(String.format("%d,%d", xPos, yPos));
            reachable += ((dist % 2 == parity) ? 1 : 0);

            xQueue.add(xPos-1); yQueue.add(yPos); distQueue.add(dist+1);
            xQueue.add(xPos+1); yQueue.add(yPos); distQueue.add(dist+1);
            xQueue.add(xPos); yQueue.add(yPos-1); distQueue.add(dist+1);
            xQueue.add(xPos); yQueue.add(yPos+1); distQueue.add(dist+1);
        }

        return reachable;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java StepCounterV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Integer>> table = extractTable(s);

            List<Integer> start = findStart(table);

            // Find number reachable in this cycle and plug into quadratic solver in Python program

            long reachable = reachableTest(table, start, 65);
            System.out.println("Number reachable is " + reachable);

            reachable = reachableTest(table, start, 65+131*4);
            System.out.println("Number reachable is " + reachable);

            reachable = reachableTest(table, start, 65+131*8);
            System.out.println("Number reachable is " + reachable);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}