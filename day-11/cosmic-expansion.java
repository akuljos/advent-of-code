import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CosmicExpansion {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CosmicExpansion [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Character>> space = new ArrayList<List<Character>>();
            List<int []> galaxies = new ArrayList<int []>();

            int setRow = 0;

            while (s.hasNextLine()) {
                char [] line = s.nextLine().toCharArray();
                List<Character> spaceRow = new ArrayList<Character>();

                for (int setCol = 0; setCol < line.length; setCol++) {
                    spaceRow.add(line[setCol]);
                    
                    if (line[setCol] == '#') {
                        int [] galaxy = { setRow, setCol };
                        galaxies.add(galaxy);
                    }
                }

                space.add(spaceRow);
                setRow++;
            }

            int numRows = space.size(); int numCols = space.get(0).size();

            List<Boolean> emptyRows = new ArrayList<Boolean>();
            List<Boolean> emptyCols = new ArrayList<Boolean>();

            for (int i = 0; i < numRows; i++) {
                emptyRows.add(true);
            }

            for (int i = 0; i < numCols; i++) {
                emptyCols.add(true);
            }

            for (int [] galaxy : galaxies) {
                emptyRows.set(galaxy[0], false);
                emptyCols.set(galaxy[1], false);
            }

            int dist = 0;
            for (int i = 0; i < galaxies.size(); i++) {
                int [] startGalaxy = galaxies.get(i);

                for (int j = i+1; j < galaxies.size(); j++) {
                    int [] endGalaxy = galaxies.get(j);
                    int currDist = Math.abs(startGalaxy[0] - endGalaxy[0]) + Math.abs(startGalaxy[1] - endGalaxy[1]);

                    for (int k = Math.min(startGalaxy[0],endGalaxy[0]) + 1; k < Math.max(startGalaxy[0],endGalaxy[0]); k++) {
                        if (emptyRows.get(k)) { currDist += 1; }
                    }

                    for (int k = Math.min(startGalaxy[1],endGalaxy[1]) + 1; k < Math.max(startGalaxy[1],endGalaxy[1]); k++) {
                        if (emptyCols.get(k)) { currDist += 1; }
                    }

                    dist += currDist;
                }
            }

            System.out.println("Minimum distance is " + dist);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}