import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class ReindeerMaze {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ReindeerMaze [filename]");
            return;
        }

        String filename = args[0];

        List<List<Integer>> reindeerMaze = new ArrayList<List<Integer>>();
        int startX = -1; int startY = -1;
        int endX = -1; int endY = -1;

        int[] xStep = new int[] { -1, 0, 1, 0 };
        int[] yStep = new int[] { 0, 1, 0, -1 };

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int currRow = 0;

            while (s.hasNextLine()) {
                String gridRow = s.nextLine();

                List<Integer> reindeerMazeRow = new ArrayList<Integer>();
                int currCol = 0;

                for (char space : gridRow.toCharArray()) {
                    switch (space) {
                        case '#':
                            reindeerMazeRow.add(1);
                            break;
                        case 'S':
                            reindeerMazeRow.add(0);
                            startX = currRow;
                            startY = currCol;
                            break;
                        case 'E':
                            reindeerMazeRow.add(0);
                            endX = currRow;
                            endY = currCol;
                            break;
                        default:
                            reindeerMazeRow.add(0);
                    }
                    currCol++;
                }
                reindeerMaze.add(reindeerMazeRow);
                currRow++;
            }
            s.close(); 

            List<List<List<Integer>>> visited = new ArrayList<List<List<Integer>>>();
            for (int i = 0; i < 4; i++) {
                List<List<Integer>> visitedGrid = new ArrayList<List<Integer>>();
                for (int j = 0; j < reindeerMaze.size(); j++) {
                    List<Integer> visitedRow = new ArrayList<Integer>();
                    for (int k = 0; k < reindeerMaze.get(j).size(); k++) {
                        visitedRow.add(0);
                    }
                    visitedGrid.add(visitedRow);
                }
                visited.add(visitedGrid);
            }

            Comparator<Integer[]> priorityComp = new Comparator<Integer[]>() {
                public int compare(Integer[] t1, Integer[] t2) {
                    return t1[0].compareTo(t2[0]);
                }
            };

            PriorityQueue<Integer[]> pathExplorer = new PriorityQueue<Integer[]>(priorityComp);
            pathExplorer.add(new Integer[] { 0, startX, startY, 1 });

            while (!pathExplorer.isEmpty()) {
                Integer[] currentPath = pathExplorer.poll();

                int score = currentPath[0];
                int currX = currentPath[1]; int currY = currentPath[2]; 
                int direction = currentPath[3];

                if (visited.get(direction).get(currX).get(currY) == 1) {
                    continue;
                }
                visited.get(direction).get(currX).set(currY, 1);

                if (currX == endX && currY == endY) {
                    System.out.println("Score of reindeer maze is " + score);
                    break;
                }

                if (reindeerMaze.get(currX + xStep[direction]).get(currY + yStep[direction]) == 0) {
                    pathExplorer.add(new Integer[] { score + 1, currX + xStep[direction], currY + yStep[direction], direction });
                }
                pathExplorer.add(new Integer[] { score + 1000, currX, currY, (direction + 1) - Math.floorDiv(direction + 1, 4) * 4 });
                pathExplorer.add(new Integer[] { score + 1000, currX, currY, (direction - 1) - Math.floorDiv(direction - 1, 4) * 4 });
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}