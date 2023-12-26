import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PipeMazeV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PipeMazeV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Character>> pipeArr = new ArrayList<List<Character>>();
            List<List<Integer>> distArr = new ArrayList<List<Integer>>();

            int startCol = -1;
            int startRow = -1;

            int currSetupRow = 0;

            while (s.hasNextLine()) {
                char [] line = s.nextLine().toCharArray();
                int currCol = 0;

                List<Character> pipeRow = new ArrayList<Character>();
                List<Integer> distRow = new ArrayList<Integer>();

                for (char pipe : line) {
                    pipeRow.add(pipe);

                    if (pipe == 'S') {
                        startCol = currCol;
                        startRow = currSetupRow;
                    }

                    distRow.add(0);
                    currCol += 1;
                }

                pipeArr.add(pipeRow);
                distArr.add(distRow);

                currSetupRow += 1;
            }

            int numRows = pipeArr.size();
            int numCols = pipeArr.get(0).size();

            List<int []> shoelace = new ArrayList<int []>();

            boolean startDetermined = false;

            if (!startDetermined && startCol != (numCols - 1)) {
                char pipeChar = pipeArr.get(startRow).get(startCol+1);
                if (pipeChar == '-' || pipeChar == 'J' || pipeChar == '7') {

                    int [] thing = { startRow, startCol };
                    shoelace.add(thing);

                    distArr.get(startRow).set(startCol, 1);
                    startCol += 1;
                    startDetermined = true;
                }
            }

            if (!startDetermined && startRow != (numRows - 1)) {
                char pipeChar = pipeArr.get(startRow+1).get(startCol);
                if (pipeChar == '|' || pipeChar == 'J' || pipeChar == 'L') {

                    int [] thing = { startRow, startCol };
                    shoelace.add(thing);

                    distArr.get(startRow).set(startCol, 1);
                    startRow += 1;
                    startDetermined = true;
                }
            }

            if (!startDetermined && startCol != 0) {
                char pipeChar = pipeArr.get(startRow).get(startCol-1);
                if (pipeChar == '-' || pipeChar == 'L' || pipeChar == 'F') {

                    int [] thing = { startRow, startCol };
                    shoelace.add(thing);

                    distArr.get(startRow).set(startCol, 1);
                    startCol -= 1;
                    startDetermined = true;
                }
            }

            if (!startDetermined && startRow != 0) {
                char pipeChar = pipeArr.get(startRow-1).get(startCol);
                if (pipeChar == '|' || pipeChar == 'F' || pipeChar == '7') {

                    int [] thing = { startRow, startCol };
                    shoelace.add(thing);

                    distArr.get(startRow).set(startCol, 1);
                    startRow -= 1;
                    startDetermined = true;
                }
            }    

            while (distArr.get(startRow).get(startCol) == 0) {
                boolean startEligible;
                switch (pipeArr.get(startRow).get(startCol)) {
                    case '-':
                        startEligible = (startCol != 0 && startCol != (numCols-1) && distArr.get(startRow).get(startCol-1) != 0 && distArr.get(startRow).get(startCol+1) != 0);

                        if (startCol != 0 && (distArr.get(startRow).get(startCol-1) == 0 || (startEligible && pipeArr.get(startRow).get(startCol-1) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startCol -= 1;
                            break;
                        }

                        if (startCol != (numCols - 1) && (distArr.get(startRow).get(startCol+1) == 0 || (startEligible && pipeArr.get(startRow).get(startCol+1) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startCol += 1;
                            break;
                        }
                    
                    case '|':
                        startEligible = (startRow != 0 && startRow != (numRows-1) && distArr.get(startRow-1).get(startCol) != 0 && distArr.get(startRow+1).get(startCol) != 0);

                        if (startRow != 0 && (distArr.get(startRow-1).get(startCol) == 0 || (startEligible && pipeArr.get(startRow-1).get(startCol) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startRow -= 1;
                            break;
                        }

                        if (startRow != (numRows - 1) && (distArr.get(startRow+1).get(startCol) == 0 || (startEligible && pipeArr.get(startRow+1).get(startCol) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startRow += 1;
                            break;
                        }

                    case 'L':
                        startEligible = (startRow != 0 && startCol != (numCols - 1) && distArr.get(startRow-1).get(startCol) != 0 && distArr.get(startRow).get(startCol+1) != 0);

                        if (startRow != 0 && (distArr.get(startRow-1).get(startCol) == 0 || (startEligible && pipeArr.get(startRow-1).get(startCol) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startRow -= 1;
                            break;
                        }

                        if (startCol != (numCols - 1) && (distArr.get(startRow).get(startCol+1) == 0 || (startEligible && pipeArr.get(startRow).get(startCol+1) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startCol += 1;
                            break;
                        }

                    case 'J':
                        startEligible = (startRow != 0 && startCol != 0 && distArr.get(startRow-1).get(startCol) != 0 && distArr.get(startRow).get(startCol-1) != 0);

                        if (startRow != 0 && (distArr.get(startRow-1).get(startCol) == 0 || (startEligible && pipeArr.get(startRow-1).get(startCol) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startRow -= 1;
                            break;
                        }

                        if (startCol != 0 && (distArr.get(startRow).get(startCol-1) == 0 || (startEligible && pipeArr.get(startRow).get(startCol-1) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startCol -= 1;
                            break;
                        }

                    case '7':
                        startEligible = (startRow != (numRows - 1) && startCol != 0 && distArr.get(startRow+1).get(startCol) != 0 && distArr.get(startRow).get(startCol-1) != 0);

                        if (startRow != (numRows - 1) && (distArr.get(startRow+1).get(startCol) == 0 || (startEligible && pipeArr.get(startRow+1).get(startCol) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startRow += 1;
                            break;
                        }

                        if (startCol != 0 && (distArr.get(startRow).get(startCol-1) == 0 || (startEligible && pipeArr.get(startRow).get(startCol-1) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startCol -= 1;
                            break;
                        }

                    case 'F':
                        startEligible = (startRow != (numRows - 1) && startCol != (numCols - 1) && distArr.get(startRow+1).get(startCol) != 0 && distArr.get(startRow).get(startCol+1) != 0);

                        if (startRow != (numRows - 1) && (distArr.get(startRow+1).get(startCol) == 0 || (startEligible && pipeArr.get(startRow+1).get(startCol) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startRow += 1;
                            break;
                        }

                        if (startCol != (numCols - 1) && (distArr.get(startRow).get(startCol+1) == 0 || (startEligible && pipeArr.get(startRow).get(startCol+1) == 'S'))) {
                            int [] thing = { startRow, startCol };
                            shoelace.add(thing);

                            distArr.get(startRow).set(startCol, 1);
                            startCol += 1;
                            break;
                        }

                    default:
                        continue;
                }
            }

            int area = 0;

            for (int i = 0; i < shoelace.size(); i++) {
                int [] first = shoelace.get(i);
                int [] second = shoelace.get((i+1) % shoelace.size());

                area += (second[0] * first[1] - first[0] * second[1]);
            }

            area /= 2;
            if (area < 0) {
                area = area * -1;
            }

            int sum = ((area + 1) * 2 - shoelace.size()) / 2;

            System.out.println("Total area is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}