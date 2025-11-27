import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class RaceConditionV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RaceConditionV2 [filename]");
            return;
        }
        
        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Integer>> course = new ArrayList<List<Integer>>();

            int startRow = 0; int startCol = 0;
            int endRow = 0; int endCol = 0;

            int currRow = 0;
            while (s.hasNextLine()) {
                int currCol = 0;
                String line = s.nextLine();
                List<Integer> courseRow = new ArrayList<Integer>();
                for (char spot : line.toCharArray()) {
                    if (spot == '#') {
                        courseRow.add(-1);
                    } else {
                        courseRow.add(0);
                    }

                    if (spot == 'S') {
                        startRow = currRow; startCol = currCol;
                    }

                    if (spot == 'E') {
                        endRow = currRow; endCol = currCol;
                    }

                    currCol += 1;
                }
                course.add(courseRow);
                currRow += 1;
            }

            s.close();

            currRow = startRow; int currCol = startCol;
            List<int[]> path = new ArrayList<int[]>();
            path.add(new int[] {startRow, startCol});
    
            while (currRow != endRow || currCol != endCol) {
                course.get(currRow).set(currCol,1);

                if (course.get(currRow).get(currCol-1) == 0) {
                    currCol = currCol - 1;
                } else if (course.get(currRow).get(currCol+1) == 0) {
                    currCol = currCol + 1;
                } else if (course.get(currRow-1).get(currCol) == 0) {
                    currRow = currRow - 1;
                } else if (course.get(currRow+1).get(currCol) == 0) {
                    currRow = currRow + 1;
                }

                path.add(new int[] {currRow, currCol});
            }

            int shortcuts = 0;
            Map<Integer, Integer> timeSaved = new HashMap<Integer, Integer>();
            for (int i = 0; i < path.size(); i++) {
                int[] point1 = path.get(i);
                for (int j = i + 100; j < path.size(); j++) {
                    int[] point2 = path.get(j);

                    int timeCheat = Math.abs(point1[0] - point2[0]) + Math.abs(point1[1] - point2[1]); 
                    if (timeCheat <= 20 && (j - i - timeCheat) > 0 && (j - i - timeCheat) >= 100) {
                        if (!timeSaved.containsKey(j - i - timeCheat)) {
                            timeSaved.put(j - i - timeCheat, 0);
                        }
                        timeSaved.put(j - i - timeCheat, timeSaved.get(j - i - timeCheat) + 1);
                        shortcuts += 1;
                    }
                }
            }

            System.out.println("Number of shortcuts more than 100 is " + shortcuts);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}