import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

class ReindeerMazeV2 {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ReindeerMazeV2 [filename]");
            return;
        }

        String filename = args[0];

        List<List<Integer>> reindeerMaze = new ArrayList<List<Integer>>();
        int startX = -1; int startY = -1;
        int endX = -1; int endY = -1;
        int currRow = 0; int currCol = 0;

        int[] xStep = new int[] { -1, 0, 1, 0 };
        int[] yStep = new int[] { 0, 1, 0, -1 };

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            currRow = 0;

            while (s.hasNextLine()) {
                String gridRow = s.nextLine();

                List<Integer> reindeerMazeRow = new ArrayList<Integer>();
                currCol = 0;

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

            List<List<List<Integer>>> scores = new ArrayList<List<List<Integer>>>();
            for (int i = 0; i < 4; i++) {
                List<List<Integer>> scoresGrid = new ArrayList<List<Integer>>();
                for (int j = 0; j < reindeerMaze.size(); j++) {
                    List<Integer> scoresRow = new ArrayList<Integer>();
                    for (int k = 0; k < reindeerMaze.get(j).size(); k++) {
                        scoresRow.add((int)Math.pow(2, 31));
                    }
                    scoresGrid.add(scoresRow);
                }
                scores.add(scoresGrid);
            }

            Map<String, Set<String>> predecessorMap = new HashMap<String, Set<String>>();

            Comparator<Integer[]> priorityComp = new Comparator<Integer[]>() {
                public int compare(Integer[] t1, Integer[] t2) {
                    return t1[0].compareTo(t2[0]);
                }
            };

            PriorityQueue<Integer[]> pathExplorer = new PriorityQueue<Integer[]>(priorityComp);
            pathExplorer.add(new Integer[] { 0, startX, startY, 1, -1, -1, -1 });

            while (!pathExplorer.isEmpty()) {
                Integer[] currentPath = pathExplorer.poll();

                int score = currentPath[0];
                int currX = currentPath[1]; int currY = currentPath[2]; int currDirection = currentPath[3];
                int prevX = currentPath[4]; int prevY = currentPath[5]; int prevDirection = currentPath[6];
                
                if (scores.get(currDirection).get(currX).get(currY) < score) {
                    if (currX == endX && currY == endY) {
                        break;
                    }
                    continue;
                }
                scores.get(currDirection).get(currX).set(currY, score);

                String currentKey = Integer.toString(currDirection) + "," + Integer.toString(currX) + "," + Integer.toString(currY);
                String predecessorKey = Integer.toString(prevDirection) + "," + Integer.toString(prevX) + "," + Integer.toString(prevY);
                if (!predecessorMap.containsKey(currentKey)) {
                    predecessorMap.put(currentKey, new HashSet<String>());
                }
                predecessorMap.get(currentKey).add(predecessorKey);

                if (reindeerMaze.get(currX + xStep[currDirection]).get(currY + yStep[currDirection]) == 0) {
                    pathExplorer.add(new Integer[] { score + 1, currX + xStep[currDirection], currY + yStep[currDirection], currDirection, currX, currY, currDirection });
                }
                pathExplorer.add(new Integer[] { score + 1000, currX, currY, (currDirection + 1) - Math.floorDiv(currDirection + 1, 4) * 4, currX, currY, currDirection });
                pathExplorer.add(new Integer[] { score + 1000, currX, currY, (currDirection - 1) - Math.floorDiv(currDirection - 1, 4) * 4, currX, currY, currDirection });
            }

            int minDis = (int)Math.pow(2, 31);
            for (int i = 0; i < 4; i++) {
                minDis = Math.min(minDis, scores.get(i).get(endX).get(endY));
            }

            Set<String> tracker = new HashSet<String>();
            List<String> backtracking = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                if (scores.get(i).get(endX).get(endY) == minDis) {
                    backtracking.add(i + "," + endX + "," + endY);
                }
            }

            while (!backtracking.isEmpty()) {
                String currKey = backtracking.remove(0);
                if (currKey == "-1,-1,-1" || tracker.contains(currKey) || !predecessorMap.containsKey(currKey)) {
                    continue;
                }

                tracker.add(currKey);
                Set<String> predKeys = predecessorMap.get(currKey);

                for (String predKey : predKeys) {
                    backtracking.add(predKey);
                }
            }

            List<List<Integer>> marker = new ArrayList<List<Integer>>();
            for (int i = 0; i < currRow; i++) {
                List<Integer> markerRow = new ArrayList<Integer>();
                for (int j = 0; j < currCol; j++) {
                    markerRow.add(0);
                }
                marker.add(markerRow);
            }

            for (String point : tracker) {
                String[] portions = point.split(",");
                marker.get(Integer.parseInt(portions[1])).set(Integer.parseInt(portions[2]),1);
            }

            int sum = 0;
            for (int i = 0; i < currRow; i++) {
                for (int j = 0; j < currCol; j++) {
                    if (marker.get(i).get(j) == 1) { sum++; }
                }
            }

            System.out.println("Reindeer sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}