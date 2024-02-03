// find distance to each garden plot
// if distance is even, reachable; if distance is odd, unreachable

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class StepCounter {

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

    static List<List<Integer>> generateVisited (int numRows, int numCols) {
        List<List<Integer>> visited = new ArrayList<List<Integer>>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < numCols; j++) {
                row.add(0);
            }
            visited.add(row);
        }

        return visited;
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

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LensLibrary [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Integer>> table = extractTable(s);

            int numRows = table.size(); int numCols = table.get(0).size();
            List<List<Integer>> visited = generateVisited(numRows, numCols);

            List<Integer> start = findStart(table);

            List<Integer> xQueue = new ArrayList<Integer>(Arrays.asList(new Integer[] { start.get(0) }));
            List<Integer> yQueue = new ArrayList<Integer>(Arrays.asList(new Integer[] { start.get(1) }));
            List<Integer> distQueue = new ArrayList<Integer>(Arrays.asList(new Integer[] { 0 }));

            int reachable = 0;

            while (xQueue.size() > 0) {
                int xPos = xQueue.remove(0); int yPos = yQueue.remove(0); int dist = distQueue.remove(0);

                if (xPos < 0 || yPos < 0 || xPos >= numRows || yPos >= numCols || dist > 64 || visited.get(xPos).get(yPos) == 1 || table.get(xPos).get(yPos) == 0) {
                    continue;
                }

                visited.get(xPos).set(yPos,1);
                reachable += ((dist % 2 == 0) ? 1 : 0);

                xQueue.add(xPos-1); yQueue.add(yPos); distQueue.add(dist+1);
                xQueue.add(xPos+1); yQueue.add(yPos); distQueue.add(dist+1);
                xQueue.add(xPos); yQueue.add(yPos-1); distQueue.add(dist+1);
                xQueue.add(xPos); yQueue.add(yPos+1); distQueue.add(dist+1);
            }

            System.out.println("Number reachable is " + reachable);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}