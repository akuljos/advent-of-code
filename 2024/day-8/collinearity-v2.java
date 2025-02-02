import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class CollinearityV2 {

    static boolean isValidAntinode(int[] antinode, int numRows, int numCols) {
        return (antinode[0] >= 0 && antinode[0] < numRows && antinode[1] >= 0 && antinode[1] < numCols);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CollinearityV2 [filename]");
            return;
        }

        String filename = args[0];

        Map<Character, List<int[]>> antennaMap = new HashMap<Character, List<int[]>>();
        int currRow = 0; int currCol = 0;

        List<boolean[]> markerMap = new ArrayList<boolean[]>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();

                markerMap.add(new boolean[line.length()]);

                currCol = 0;

                for (char c : line.toCharArray()) {
                    if (c != '.') {
                        if (!antennaMap.containsKey(c)) {
                            antennaMap.put(c, new ArrayList<int[]>());
                        }
                        antennaMap.get(c).add(new int[]{currRow, currCol});
                    }
                    currCol++;
                }
                currRow++;                
            }

            s.close();

            int numRows = markerMap.size();
            int numCols = markerMap.get(0).length;

            for (Character key : antennaMap.keySet()) {
                List<int[]> keypoints = antennaMap.get(key);

                for (int i = 0; i < keypoints.size(); i++) {
                    int[] pointOne = keypoints.get(i);
                    for (int j = i + 1; j < keypoints.size(); j++) {
                        int[] pointTwo = keypoints.get(j);

                        float slope = (float)(pointTwo[0] - pointOne[0]) / (pointTwo[1] - pointOne[1]);
                        for (int k = 0; k < numCols; k++) {
                            float row = slope * ((float)k - pointOne[1]) + pointOne[0];
                            if (row % 1 == 0 && isValidAntinode(new int[]{(int)row, k}, numRows, numCols)) {
                                markerMap.get((int)row)[k] = true;
                            }
                        }
                    }
                }
            }

            int sum = 0;
            for (boolean[] row : markerMap) {
                for (boolean col : row) { 
                    if (col) {
                        sum++;
                    }
                }
            }

            System.out.println("Collinearity sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}