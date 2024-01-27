import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// Idea: in track table, memoization for each direction and number of consecutive steps

class CrucibleV2 {

    static List<List<Integer>> extractTable (Scanner s) {
        List<List<Integer>> table = new ArrayList<List<Integer>>();

        while (s.hasNextLine()) {
            char [] line = s.nextLine().toCharArray();
            List<Integer> row = new ArrayList<Integer>();

            for (char c : line) {
                row.add(c - '0');
            }

            table.add(row);
        }

        return table;
    }

    static List<List<List<Double>>> createTrackTable(int numRows, int numCols) {
        List<List<List<Double>>> trackTable = new ArrayList<List<List<Double>>>();

        for (int i = 0; i < numRows; i++) {
            List<List<Double>> trackRow = new ArrayList<List<Double>>();

            for (int j = 0; j < numCols; j++) {
                List<Double> tracker = new ArrayList<Double>();
                for (int k = 0; k < 40; k++) {
                    tracker.add(Math.pow(2, 32) - 1);
                }

                trackRow.add(tracker);
            }

            trackTable.add(trackRow);
        }

        return trackTable;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CrucibleV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Integer>> table = extractTable(s);
            int numRows = table.size(); int numCols = table.get(0).size();

            List<List<List<Double>>> trackTable = createTrackTable(numRows, numCols);

            Comparator<List<Integer>> priorityComp = new Comparator<List<Integer>>() {
                public int compare(List<Integer> t1, List<Integer> t2) {
                    return t1.get(0).compareTo(t2.get(0));
                }
            };

            PriorityQueue<List<Integer>> queue = new PriorityQueue<List<Integer>>(priorityComp);
            // < distance, x position, y position, direction, consecutive steps >
            // direction: 0 (right), 1 (down), 2 (left), 3 (up)

            queue.add(Arrays.asList(new Integer[] { -table.get(0).get(0), 0, 0, 0, 0 }));
            queue.add(Arrays.asList(new Integer[] { -table.get(0).get(0), 0, 0, 1, 0 }));

            int minDistance = (int)Math.pow(2,32) - 1;

            while (queue.size() != 0) {
                List<Integer> item = queue.poll();
                int distance = item.get(0); int xPos = item.get(1); int yPos = item.get(2); int dir = item.get(3); int consec = item.get(4);

                if (xPos < 0 || yPos < 0 || xPos >= numRows || yPos >= numCols) {
                    continue;
                }

                distance += table.get(xPos).get(yPos);
                if (consec != 0 && distance >= trackTable.get(xPos).get(yPos).get(10 * dir + (consec - 1))) {
                    continue;
                }

                if (consec != 0) {
                    trackTable.get(xPos).get(yPos).set(10 * dir + (consec - 1), (double)distance);
                }

                if (xPos == (numRows - 1) && yPos == (numCols - 1) && consec >= 4) {
                    minDistance = Math.min(minDistance, distance);
                    continue;
                }

                switch (dir) {
                    case 0:
                        if (consec >= 4) {
                            queue.add(Arrays.asList( new Integer[] { distance, xPos - 1, yPos, 3, 1 } ));
                            queue.add(Arrays.asList( new Integer[] { distance, xPos + 1, yPos, 1, 1 } ));
                        }
                        
                        if (consec < 10) { queue.add(Arrays.asList( new Integer[] { distance, xPos, yPos + 1, 0, consec + 1 } )); }
                        break;

                    case 1:
                        if (consec >= 4) {
                            queue.add(Arrays.asList( new Integer[] { distance, xPos, yPos - 1, 2, 1 } ));
                            queue.add(Arrays.asList( new Integer[] { distance, xPos, yPos + 1, 0, 1 } ));
                        }

                        if (consec < 10) { queue.add(Arrays.asList( new Integer[] { distance, xPos + 1, yPos, 1, consec + 1 } )); }
                        break;
                    
                    case 2:
                        if (consec >= 4) {
                            queue.add(Arrays.asList( new Integer[] { distance, xPos - 1, yPos, 3, 1 } ));
                            queue.add(Arrays.asList( new Integer[] { distance, xPos + 1, yPos, 1, 1 } ));
                        }

                        if (consec < 10) { queue.add(Arrays.asList( new Integer[] { distance, xPos, yPos - 1, 2, consec + 1 } )); }
                        break;
                    
                    case 3:
                        if (consec >= 4) {
                            queue.add(Arrays.asList( new Integer[] { distance, xPos, yPos - 1, 2, 1 } ));
                            queue.add(Arrays.asList( new Integer[] { distance, xPos, yPos + 1, 0, 1 } ));
                        }

                        if (consec < 10) { queue.add(Arrays.asList( new Integer[] { distance, xPos - 1, yPos, 3, consec + 1 } )); }
                        break;
                    
                    default:
                        break;
                }
            }

            System.out.println("Min distance is " + minDistance);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}