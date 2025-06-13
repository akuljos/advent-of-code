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

class HoofIt {

    static int hoofItDfs(List<List<Integer>> topologicalMap, int row, int col) {
        List<List<Integer>> tracker = new ArrayList<List<Integer>>();
        for (int i = 0; i < topologicalMap.size(); i++) {
            List<Integer> trackerRow = new ArrayList<Integer>();
            for (int j = 0; j < topologicalMap.get(i).size(); j++) {
                trackerRow.add(0);
            }
            tracker.add(trackerRow);
        }

        Stack<int[]> gridStack = new Stack<int[]>();
        gridStack.push(new int[] { row, col });

        int numTrails = 0;
        while (gridStack.size() > 0) {
            int[] point = gridStack.pop();
            int row_ = point[0]; int col_ = point[1]; int height = topologicalMap.get(row_).get(col_);

            tracker.get(row_).set(col_, 1);
            if (height == 9) {
                numTrails += 1;
                continue;
            }
                  
            if (row_ != 0 && tracker.get(row_-1).get(col_) != 1 && topologicalMap.get(row_-1).get(col_) == (height + 1)) {
                gridStack.push(new int[] { row_ - 1, col_ });
            }
            if (row_ != topologicalMap.size()-1 && tracker.get(row_+1).get(col_) != 1 && topologicalMap.get(row_+1).get(col_) == (height + 1)) {
                gridStack.push(new int[] { row_ + 1, col_ });
            }
            if (col_ != 0 && tracker.get(row_).get(col_-1) != 1 && topologicalMap.get(row_).get(col_-1) == (height + 1)) {
                gridStack.push(new int[] { row_, col_ - 1 });
            }
            if (col_ != topologicalMap.get(row_).size()-1 && tracker.get(row_).get(col_+1) != 1 && topologicalMap.get(row_).get(col_+1) == (height + 1)) {
                gridStack.push(new int[] { row_ , col_ + 1 });
            }
        }

        return numTrails;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HoofIt [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<int []> trailheads = new ArrayList<int []>();

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
                        int[] trailhead = new int[] { rowNum, colNum };
                        trailheads.add(trailhead);
                    }
                    colNum += 1;
                }
                topologicalMap.add(topoMapRow);
                rowNum += 1;
            }

            s.close();

            int numTrails = 0;

            for (int[] trailhead : trailheads) {
                numTrails += hoofItDfs(topologicalMap, trailhead[0], trailhead[1]);
            }

            System.out.println("Num trails is " + numTrails);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}