import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

class HoofItV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HoofItV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            Stack<int []> trailheads = new Stack<int []>();

            List<char[]> grid = new ArrayList<char[]>();
            while (s.hasNextLine()) {
                grid.add(s.nextLine().toCharArray());
            }

            List<List<Integer>> topologicalMap = new ArrayList<List<Integer>>();
            int rowNum = 0;
            for (char[] row : grid) {
                List<Integer> topoMapRow = new ArrayList<Integer>();
                int colNum = 0;
                for (char c : row) {
                    topoMapRow.add(c - '0');
                    if (c == '0') {
                        int[] trailhead = new int[] { rowNum, colNum, 0 };
                        trailheads.add(trailhead);
                    }
                    colNum += 1;
                }
                topologicalMap.add(topoMapRow);
                rowNum += 1;
            }

            s.close();

            int numTrails = 0;
            while (trailheads.size() > 0) {
                int[] trailhead = trailheads.pop();
                int row = trailhead[0]; int col = trailhead[1]; int height = trailhead[2];

                if (height == 9) {
                    numTrails += 1;
                    continue;
                }

                if (row > 0 && topologicalMap.get(row-1).get(col) == (height + 1)) {
                    trailheads.add(new int[] { row-1, col, height+1 });
                }
                if (row < topologicalMap.size()-1 && topologicalMap.get(row+1).get(col) == (height + 1)) {
                    trailheads.add(new int[] { row+1, col, height+1 });
                }
                if (col > 0 && topologicalMap.get(row).get(col-1) == (height + 1)) {
                    trailheads.add(new int[] { row, col-1, height+1 });
                }
                if (col < topologicalMap.get(row).size()-1 && topologicalMap.get(row).get(col+1) == (height + 1)) {
                    trailheads.add(new int[] { row, col+1, height+1 });
                }
            }

            System.out.println("Num trails is " + numTrails);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}