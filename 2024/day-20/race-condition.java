import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RaceCondition {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RaceCondition [filename]");
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
            int distance = 1;

            while (currRow != endRow || currCol != endCol) {
                course.get(currRow).set(currCol, distance);

                if (course.get(currRow).get(currCol-1) == 0) {
                    currCol = currCol - 1;
                } else if (course.get(currRow).get(currCol+1) == 0) {
                    currCol = currCol + 1;
                } else if (course.get(currRow-1).get(currCol) == 0) {
                    currRow = currRow - 1;
                } else if (course.get(currRow+1).get(currCol) == 0) {
                    currRow = currRow + 1;
                }

                distance++;
            }
            
            int sum = 0;
            for (int i = 1; i < course.size() - 1; i++) {
                for (int j = 1; j < course.get(i).size() - 1; j++) {
                    if (course.get(i).get(j) == -1) {
                        int up = course.get(i-1).get(j); int down = course.get(i+1).get(j);
                        int left = course.get(i).get(j-1); int right = course.get(i).get(j+1);

                        if (up != -1 && down != -1) {
                            if ((Math.abs(up - down) - 2) >= 100) {
                                sum++;
                            }
                        }

                        if (left != -1 && right != -1) {
                            if ((Math.abs(left - right) - 2) >= 100) {
                                sum++;
                            }
                        }
                    }
                }
            }

            System.out.println("Number of shortcuts more than 100 is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}