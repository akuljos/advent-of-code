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

class GardenGroups {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java GardenGroups [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<char[]> garden = new ArrayList<char[]>();
            List<int[]> groups = new ArrayList<int[]>();

            while (s.hasNextLine()) {
                String gardenRow = s.nextLine();

                garden.add(gardenRow.toCharArray());
                groups.add(new int[gardenRow.length()]);
            }

            s.close();    
            
            int numRows = garden.size(); 
            int numCols = garden.get(0).length;

            int groupCounter = 1;

            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (groups.get(row)[col] == 0) {
                        Character currLetter = garden.get(row)[col];
                        List<int[]> gardenQueue = new ArrayList<int[]>();
                        gardenQueue.add(new int[] { row, col });

                        while (gardenQueue.size() > 0) {
                            int[] currGarden = gardenQueue.remove(0);
                            int currRow = currGarden[0]; int currCol = currGarden[1];

                            if (currRow < 0 || currCol < 0 || currRow >= numRows || currCol >= numCols || garden.get(currRow)[currCol] != currLetter || groups.get(currRow)[currCol] != 0) {
                                continue;
                            }

                            groups.get(currRow)[currCol] = groupCounter;
                            gardenQueue.add(new int[] { currRow - 1, currCol });
                            gardenQueue.add(new int[] { currRow + 1, currCol });
                            gardenQueue.add(new int[] { currRow, currCol - 1 });
                            gardenQueue.add(new int[] { currRow, currCol + 1 });
                        }
                        groupCounter += 1;
                    }
                }
            }

            int[] areaMap = new int[groupCounter];
            int[] perimeterMap = new int[groupCounter];

            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    int groupNumber = groups.get(row)[col];

                    areaMap[groupNumber]++;
                    if (row == 0 || groups.get(row-1)[col] != groupNumber) {
                        perimeterMap[groupNumber]++;
                    } 
                    if (row == (numRows - 1) || groups.get(row+1)[col] != groupNumber) {
                        perimeterMap[groupNumber]++;
                    } 
                    if (col == 0 || groups.get(row)[col-1] != groupNumber) {
                        perimeterMap[groupNumber]++;
                    } 
                    if (col == (numCols - 1) || groups.get(row)[col+1] != groupNumber) {
                        perimeterMap[groupNumber]++;
                    } 
                }
            }

            int fencingCost = 0;
            for (int i = 1; i < groupCounter; i++) {
                fencingCost += (areaMap[i] * perimeterMap[i]);
            }

            System.out.println("Cost of fencing is " + fencingCost);
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}