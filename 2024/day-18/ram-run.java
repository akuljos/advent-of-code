import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class RamRun {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RamRun [filename]");
            return;
        }
        
        String filename = args[0];

        int GRID_SIZE = 71;
        int NUM_BYTES = 1024;
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int byteCounter = 0;
            while (s.hasNextLine()) {
                String[] line = s.nextLine().split(",");
                grid[Integer.parseInt(line[1])][Integer.parseInt(line[0])] = 1;

                byteCounter += 1;
                if (byteCounter == NUM_BYTES) {
                    break;
                }
            }

            s.close();

            int[][] visited = new int[GRID_SIZE][GRID_SIZE];

            Comparator<Integer[]> visitComp = new Comparator<Integer[]>() {
                public int compare(Integer[] t1, Integer[] t2) {
                    return t1[0].compareTo(t2[0]);
                }
            };

            PriorityQueue<Integer[]> visitQueue = new PriorityQueue<>(visitComp);
            visitQueue.add(new Integer[]{0, 0, 0});

            int finalDistance = 0;
            while (visitQueue.size() > 0) {
                Integer[] currVisit = visitQueue.poll();
                int distance = currVisit[0]; int row = currVisit[1]; int col = currVisit[2];

                if (row < 0 || col < 0 || row >= GRID_SIZE || col >= GRID_SIZE || grid[row][col] == 1 || visited[row][col] == 1) {
                    continue;
                }
                visited[row][col] = 1;

                if (row == GRID_SIZE - 1 && col == GRID_SIZE - 1) {
                    finalDistance = distance;
                    break;
                }

                visitQueue.add(new Integer[] { distance+1, row-1, col });
                visitQueue.add(new Integer[] { distance+1, row+1, col });
                visitQueue.add(new Integer[] { distance+1, row, col-1 });
                visitQueue.add(new Integer[] { distance+1, row, col+1 });
            }

            System.out.println("Final distance is " + finalDistance);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}