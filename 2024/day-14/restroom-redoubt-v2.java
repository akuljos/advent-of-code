import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class RestroomRedoubtV2 {

    static void printGrid(List<int[]> grid, int gridLength, int gridHeight, FileWriter outputFile) throws IOException {
        int[][] gridDisplay = new int[gridLength][gridHeight];

        for (int[] robot : grid) {
            gridDisplay[robot[0]][robot[1]] = 1;
        }

        for (int row = 0; row < gridLength; row++) {
            for (int col = 0; col < gridHeight; col++) {
                outputFile.write((gridDisplay[row][col] == 1) ? "X" : ".");
            }
            outputFile.write("\n");
        }

        outputFile.write("\n\n\n\n\n");
    }

    public static void main (String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("usage: java RestroomRedoubtV2 [filename]");
            return;
        }

        String filename = args[0];

        Pattern posVeloPattern = Pattern.compile("p=([0-9]*),([0-9]*) v=([0-9[\\-]]*),([0-9[\\-]]*)");

        int gridLength = 101;
        int gridHeight = 103;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int q1 = 0; int q2 = 0; int q3 = 0; int q4 = 0;

            List<int[]> robotPositions = new ArrayList<int[]>();
            List<int[]> robotVelocities = new ArrayList<int[]>();

            while (s.hasNextLine()) {
                int posX = 0; int posY = 0;
                int velX = 0; int velY = 0;

                String posVelo = s.nextLine();
                Matcher posVeloMatcher = posVeloPattern.matcher(posVelo);
                if (posVeloMatcher.find()) {
                    posX = Integer.parseInt(posVeloMatcher.group(1));
                    posY = Integer.parseInt(posVeloMatcher.group(2));
                    velX = Integer.parseInt(posVeloMatcher.group(3));
                    velY = Integer.parseInt(posVeloMatcher.group(4));
                }

                robotPositions.add(new int[] { posX, posY });
                robotVelocities.add(new int[] { velX, velY });
            }

            s.close();  

            FileWriter outputFile = new FileWriter("restroom_redoubt_output.txt");

            int epoch = 1;
            while (epoch <= 10000) {
                for (int i = 0; i < robotPositions.size(); i++) {
                    robotPositions.get(i)[0] += robotVelocities.get(i)[0];
                    robotPositions.get(i)[0] = Math.floorMod(robotPositions.get(i)[0], gridLength);

                    robotPositions.get(i)[1] += robotVelocities.get(i)[1];
                    robotPositions.get(i)[1] = Math.floorMod(robotPositions.get(i)[1], gridHeight);
                }

                outputFile.write("Epoch " + epoch +"\n---------------\n");
                printGrid(robotPositions, gridLength, gridHeight, outputFile);
                epoch++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}