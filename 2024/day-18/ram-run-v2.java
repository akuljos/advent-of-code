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

class RamRunV2 {

    static boolean hasPath(int[][] grid, int gridSize) {
        int[][] visited = new int[gridSize][gridSize];

        Comparator<Integer[]> visitComp = new Comparator<Integer[]>() {
            public int compare(Integer[] t1, Integer[] t2) {
                return t1[0].compareTo(t2[0]);
            }
        };

        PriorityQueue<Integer[]> visitQueue = new PriorityQueue<>(visitComp);
        visitQueue.add(new Integer[]{0, 0, 0});

        while (visitQueue.size() > 0) {
            Integer[] currVisit = visitQueue.poll();
            int distance = currVisit[0]; int row = currVisit[1]; int col = currVisit[2];

            if (row < 0 || col < 0 || row >= gridSize || col >= gridSize || grid[row][col] == 1 || visited[row][col] == 1) {
                continue;
            }
            visited[row][col] = 1;

            if (row == gridSize - 1 && col == gridSize - 1) {
                return true;
            }

            visitQueue.add(new Integer[] { distance+1, row-1, col });
            visitQueue.add(new Integer[] { distance+1, row+1, col });
            visitQueue.add(new Integer[] { distance+1, row, col-1 });
            visitQueue.add(new Integer[] { distance+1, row, col+1 });
        }
        return false;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RamRunV2 [filename]");
            return;
        }
        
        String filename = args[0];

        int GRID_SIZE = 71;

        List<Integer[]> positions = new ArrayList<Integer[]>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String[] line = s.nextLine().split(",");
                positions.add(new Integer[]{ Integer.parseInt(line[1]), Integer.parseInt(line[0])});
            }

            s.close();

            int start = 0;
            int end = positions.size() - 1;

            while (start <= end) {
                int mid = start + (end - start) / 2;

                int[][] grid = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i <= mid; i++) {
                    grid[positions.get(i)[0]][positions.get(i)[1]] = 1;
                }

                if (hasPath(grid, GRID_SIZE)) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }

            System.out.println("First grid position that fails path is " + positions.get(start)[1] + "," + positions.get(start)[0]);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}