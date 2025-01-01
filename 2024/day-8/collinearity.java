import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Collinearity {

    static boolean isValidAntinode(int[] antinode, int numRows, int numCols) {
        return (antinode[0] >= 0 && antinode[0] < numRows && antinode[1] >= 0 && antinode[1] < numCols);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Collinearity [filename]");
            return;
        }

        String filename = args[0];

        Map<Character, List<int[]>> antennaMap = new HashMap<Character, List<int[]>>();
        int currRow = 0; int currCol = 0;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();

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

            int numRows = currRow; int numCols = currCol;

            Set<String> antinodeSet = new HashSet<String>();
            for (char c : antennaMap.keySet()) {
                List<int[]> antennas = antennaMap.get(c);
                for (int i = 0; i < antennas.size(); i++) {
                    for (int j = i+1; j < antennas.size(); j++) {
                        int[] antennaOne = antennas.get(i);
                        int[] antennaTwo = antennas.get(j);

                        int[] antinodeOne = {2 * antennaOne[0] - antennaTwo[0], 2 * antennaOne[1] - antennaTwo[1]};
                        if (isValidAntinode(antinodeOne, numRows, numCols)) antinodeSet.add(antinodeOne[0] + "," + antinodeOne[1]);

                        int[] antinodeTwo = {2 * antennaTwo[0] - antennaOne[0], 2 * antennaTwo[1] - antennaOne[1]};
                        if (isValidAntinode(antinodeTwo, numRows, numCols)) antinodeSet.add(antinodeTwo[0] + "," + antinodeTwo[1]);
                    }
                }
            }

            s.close();

            System.out.println("Collinearity sum is " + antinodeSet.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}