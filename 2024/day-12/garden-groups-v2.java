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

class GardenGroupsV2 {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java GardenGroupsV2 [filename]");
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
                }
            }

            // added perimeter checking for the top walls
            for (int row = 0; row < numRows; row++) {
                int previousPlot = 0;
                boolean prevMatch = false;
                for (int col = 0; col < numCols; col++) {
                    int currentPlot = groups.get(row)[col];
                    if (previousPlot != currentPlot) {
                        prevMatch = false;
                        perimeterMap[previousPlot]++;
                        if (row != 0 && currentPlot == groups.get(row-1)[col]) {
                            previousPlot = 0;
                        } else {
                            previousPlot = currentPlot;
                        }
                    } else if (row != 0 && currentPlot == groups.get(row-1)[col]) {
                        prevMatch = true;
                    } else if (row != 0 && prevMatch) {
                        perimeterMap[previousPlot]++;
                        prevMatch = false;
                    }
                }
                perimeterMap[previousPlot]++;
            }

            for (int row = numRows - 1; row >= 0; row--) {
                int previousPlot = 0;
                boolean prevMatch = false;
                for (int col = 0; col < numCols; col++) {
                    int currentPlot = groups.get(row)[col];
                    if (previousPlot != currentPlot) {
                        prevMatch = false;
                        perimeterMap[previousPlot]++;
                        if (row != (numRows-1) && currentPlot == groups.get(row+1)[col]) {
                            previousPlot = 0;
                        } else {
                            previousPlot = currentPlot;
                        }
                    }  else if (row != (numRows-1) && currentPlot == groups.get(row+1)[col]) {
                        prevMatch = true;
                    } else if (row != (numRows-1) && prevMatch) {
                        perimeterMap[previousPlot]++;
                        prevMatch = false;
                    }
                }
                perimeterMap[previousPlot]++;
            }

            for (int col = 0; col < numCols; col++) {
                int previousPlot = 0;
                boolean prevMatch = false;
                for (int row = 0; row < numRows; row++) {
                    int currentPlot = groups.get(row)[col];
                    if (previousPlot != currentPlot) {
                        prevMatch = false;
                        perimeterMap[previousPlot]++;
                        if (col != 0 && currentPlot == groups.get(row)[col-1]) {
                            previousPlot = 0;
                        } else {
                            previousPlot = currentPlot;
                        }
                    }  else if (col != 0 && currentPlot == groups.get(row)[col-1]) {
                        prevMatch = true;
                    } else if (col != 0 && prevMatch) {
                        perimeterMap[previousPlot]++;
                        prevMatch = false;
                    }
                }
                perimeterMap[previousPlot]++;
            }

            for (int col = numCols - 1; col >= 0; col--) {
                int previousPlot = 0;
                boolean prevMatch = false;
                for (int row = 0; row < numRows; row++) {
                    int currentPlot = groups.get(row)[col];
                    if (previousPlot != currentPlot) {
                        prevMatch = false;
                        perimeterMap[previousPlot]++;
                        if (col != (numCols - 1) && currentPlot == groups.get(row)[col+1]) {
                            previousPlot = 0;
                        } else {
                            previousPlot = currentPlot;
                        }
                    }  else if (col != (numCols-1) && currentPlot == groups.get(row)[col+1]) {
                        prevMatch = true;
                    } else if (row != 0 && prevMatch) {
                        perimeterMap[previousPlot]++;
                        prevMatch = false;
                    }
                }
                perimeterMap[previousPlot]++;
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