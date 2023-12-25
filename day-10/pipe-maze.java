import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PipeMaze {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PipeMaze [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Character>> pipeArr = new ArrayList<List<Character>>();
            List<List<Integer>> distArr = new ArrayList<List<Integer>>();

            List<int []> checkQueue = new ArrayList<int []>();

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
                        distRow.add(0);

                        startCol = currCol;
                        startRow = currSetupRow;
                    } else {
                        distRow.add(-1);
                    }

                    currCol += 1;
                }

                pipeArr.add(pipeRow);
                distArr.add(distRow);
                currSetupRow += 1;
            }

            int numRows = pipeArr.size();
            int numCols = pipeArr.get(0).size();

            if (startRow != 0) {
                char pipeChar = pipeArr.get(startRow-1).get(startCol);
                if (pipeChar == '|' || pipeChar == 'F' || pipeChar == '7') {
                    int [] newSq = { startRow - 1, startCol, 1 };
                    checkQueue.add(newSq);
                }
            }

            if (startRow != (numRows - 1)) {
                char pipeChar = pipeArr.get(startRow+1).get(startCol);
                if (pipeChar == '|' || pipeChar == 'J' || pipeChar == 'L') {
                    int [] newSq = { startRow + 1, startCol, 1 };
                    checkQueue.add(newSq);
                }
            }

            if (startCol != 0) {
                char pipeChar = pipeArr.get(startRow).get(startCol-1);
                if (pipeChar == '-' || pipeChar == 'L' || pipeChar == 'F') {
                    int [] newSq = { startRow, startCol - 1, 1 };
                    checkQueue.add(newSq);
                }
            }

            if (startCol != (numCols - 1)) {
                char pipeChar = pipeArr.get(startRow).get(startCol+1);
                if (pipeChar == '-' || pipeChar == 'J' || pipeChar == '7') {
                    int [] newSq = { startRow, startCol + 1, 1 };
                    checkQueue.add(newSq);
                }
            }

            int maxDist = 0;

            while (checkQueue.size() > 0) {
                int [] currPos = checkQueue.remove(0);
                int currRow = currPos[0];
                int currCol = currPos[1];
                int currDist = currPos[2];

                if (currRow < 0 || currCol < 0 || currRow >= numRows || currCol >= numCols || distArr.get(currRow).get(currCol) != -1) {
                    continue;
                }

                char currPipe = pipeArr.get(currRow).get(currCol);
                
                distArr.get(currRow).set(currCol, currDist);

                int [] newSq = new int[3];
                int [] newSq2 = new int[3];

                maxDist = Math.max(maxDist, currDist);

                switch (currPipe) {
                    case '|':
                        newSq[0] = currRow - 1; newSq[1] = currCol; newSq[2] = currDist + 1;
                        newSq2[0] = currRow + 1; newSq2[1] = currCol; newSq2[2] = currDist + 1;
                        break;
                    case '-':
                        newSq[0] = currRow; newSq[1] = currCol - 1; newSq[2] = currDist + 1;
                        newSq2[0] = currRow; newSq2[1] = currCol + 1; newSq2[2] = currDist + 1;
                        break;
                    case 'L':
                        newSq[0] = currRow - 1; newSq[1] = currCol; newSq[2] = currDist + 1;
                        newSq2[0] = currRow; newSq2[1] = currCol + 1; newSq2[2] = currDist + 1;
                        break;
                    case 'J':
                        newSq[0] = currRow - 1; newSq[1] = currCol; newSq[2] = currDist + 1;
                        newSq2[0] = currRow; newSq2[1] = currCol - 1; newSq2[2] = currDist + 1;
                        break;
                    case '7':
                        newSq[0] = currRow + 1; newSq[1] = currCol; newSq[2] = currDist + 1;
                        newSq2[0] = currRow; newSq2[1] = currCol - 1; newSq2[2] = currDist + 1;
                        break;
                    case 'F':
                        newSq[0] = currRow + 1; newSq[1] = currCol; newSq[2] = currDist + 1;
                        newSq2[0] = currRow; newSq2[1] = currCol + 1; newSq2[2] = currDist + 1;
                        break;
                    default:
                        continue;
                }

                checkQueue.add(newSq); checkQueue.add(newSq2);
            }

            System.out.println("Max distance is " + maxDist);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}