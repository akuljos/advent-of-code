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

class WarehouseWoesV2 {

    static boolean moveUp(List<List<Integer>> markedToMove, List<List<Integer>> warehouseFloor, int currX, int currY) {
        if (warehouseFloor.get(currX-1).get(currY) == 0) {
            markedToMove.get(currX).set(currY, 1);
            return true;
        }

        if (warehouseFloor.get(currX-1).get(currY) == 4) {
            return false;
        }

        if (warehouseFloor.get(currX-1).get(currY) == 1) {
            if (moveUp(markedToMove, warehouseFloor, currX-1, currY) && moveUp(markedToMove, warehouseFloor, currX-1, currY+1)) {
                markedToMove.get(currX).set(currY, 1);
                return true;
            }
            return false;
        }

        if (warehouseFloor.get(currX-1).get(currY) == 2) {
            if (moveUp(markedToMove, warehouseFloor, currX-1, currY) && moveUp(markedToMove, warehouseFloor, currX-1, currY-1)) {
                markedToMove.get(currX).set(currY, 1);
                return true;
            }
            return false;
        }

        return false;
    }

    static boolean moveDown(List<List<Integer>> markedToMove, List<List<Integer>> warehouseFloor, int currX, int currY) {
        if (warehouseFloor.get(currX+1).get(currY) == 0) {
            markedToMove.get(currX).set(currY, 1);
            return true;
        }

        if (warehouseFloor.get(currX+1).get(currY) == 4) {
            return false;
        }

        if (warehouseFloor.get(currX+1).get(currY) == 1) {
            if (moveDown(markedToMove, warehouseFloor, currX+1, currY) && moveDown(markedToMove, warehouseFloor, currX+1, currY+1)) {
                markedToMove.get(currX).set(currY, 1);
                return true;
            }
            return false;
        }

        if (warehouseFloor.get(currX+1).get(currY) == 2) {
            if (moveDown(markedToMove, warehouseFloor, currX+1, currY) && moveDown(markedToMove, warehouseFloor, currX+1, currY-1)) {
                markedToMove.get(currX).set(currY, 1);
                return true;
            }
            return false;
        }

        return false;
    }

    static boolean handleDirection(List<List<Integer>> warehouseFloor, int robotX, int robotY, int direction) {
        int currY;
        boolean moveBoxes;
        List<List<Integer>> markedToMove;
        
        switch (direction) {
            case 0:
                markedToMove = new ArrayList<List<Integer>>();
                for (int row = 0; row < warehouseFloor.size(); row++) {
                    List<Integer> markedToMoveRow = new ArrayList<Integer>();
                    for (int col = 0; col < warehouseFloor.get(row).size(); col++) {
                        markedToMoveRow.add(0);
                    }
                    markedToMove.add(markedToMoveRow);
                }
                moveBoxes = moveUp(markedToMove, warehouseFloor, robotX, robotY);

                if (moveBoxes) {
                    for (int row = 0; row < warehouseFloor.size(); row++) {
                        for (int col = 0; col < warehouseFloor.get(row).size(); col++) {
                            if (markedToMove.get(row).get(col) == 1) {
                                if (warehouseFloor.get(row).get(col) == 3 || (warehouseFloor.get(row).get(col) == 1 && markedToMove.get(row).get(col+1) == 1) || (warehouseFloor.get(row).get(col) == 2 && markedToMove.get(row).get(col-1) == 1)) {
                                    warehouseFloor.get(row-1).set(col, warehouseFloor.get(row).get(col));
                                    warehouseFloor.get(row).set(col,0);
                                }
                            }
                        }
                    }
                }
                
                break;
            case 1:
                currY = robotY;
                moveBoxes = true;

                while (warehouseFloor.get(robotX).get(currY+1) != 0) {
                    if (warehouseFloor.get(robotX).get(currY+1) == 4) {
                        moveBoxes = false;
                        break;
                    }
                    currY++;
                }

                if (moveBoxes) {
                    while (currY != (robotY - 1)) {
                        warehouseFloor.get(robotX).set(currY+1, warehouseFloor.get(robotX).get(currY));
                        warehouseFloor.get(robotX).set(currY, 0);
                        currY--;
                    }
                }

                break;
            case 2:
                markedToMove = new ArrayList<List<Integer>>();
                for (int row = 0; row < warehouseFloor.size(); row++) {
                    List<Integer> markedToMoveRow = new ArrayList<Integer>();
                    for (int col = 0; col < warehouseFloor.get(row).size(); col++) {
                        markedToMoveRow.add(0);
                    }
                    markedToMove.add(markedToMoveRow);
                }
                moveBoxes = moveDown(markedToMove, warehouseFloor, robotX, robotY);

                if (moveBoxes) {
                    for (int row = warehouseFloor.size()-1; row >= 0; row--) {
                        for (int col = 0; col < warehouseFloor.get(row).size(); col++) {
                            if (markedToMove.get(row).get(col) == 1) {
                                if (warehouseFloor.get(row).get(col) == 3 || (warehouseFloor.get(row).get(col) == 1 && markedToMove.get(row).get(col+1) == 1) || (warehouseFloor.get(row).get(col) == 2 && markedToMove.get(row).get(col-1) == 1)) {
                                    warehouseFloor.get(row+1).set(col, warehouseFloor.get(row).get(col));
                                    warehouseFloor.get(row).set(col,0);
                                }
                            }
                        }
                    }
                }
                
                break;
            case 3:
                currY = robotY;
                moveBoxes = true;

                while (warehouseFloor.get(robotX).get(currY-1) != 0) {
                    if (warehouseFloor.get(robotX).get(currY-1) == 4) {
                        moveBoxes = false;
                        break;
                    }
                    currY--;
                }

                if (moveBoxes) {
                    while (currY != (robotY + 1)) {
                        warehouseFloor.get(robotX).set(currY-1, warehouseFloor.get(robotX).get(currY));
                        warehouseFloor.get(robotX).set(currY, 0);
                        currY++;
                    }
                }

                break;
            default:
                moveBoxes = false;
        }

        return moveBoxes;
    }

    public static void main (String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("usage: java WarehouseWoesV2 [filename]");
            return;
        }

        String filename = args[0];

        List<List<Integer>> warehouseFloor = new ArrayList<List<Integer>>();
        List<Integer> directions = new ArrayList<Integer>();
        int robotX = -1; int robotY = -1;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int currRow = 0;

            while (s.hasNextLine()) {
                String gridRow = s.nextLine();
                if (gridRow.equals("")) { break; }

                List<Integer> warehouseRow = new ArrayList<Integer>();
                int currCol = 0;

                for (char space : gridRow.toCharArray()) {
                    switch (space) {
                        case '#':
                            warehouseRow.add(4);
                            warehouseRow.add(4);
                            break;
                        case '@':
                            warehouseRow.add(3);
                            warehouseRow.add(0);
                            robotX = currRow;
                            robotY = currCol;
                            break;
                        case 'O':
                            warehouseRow.add(1);
                            warehouseRow.add(2);
                            break;
                        default:
                            warehouseRow.add(0);
                            warehouseRow.add(0);
                    }
                    currCol += 2;
                }
                warehouseFloor.add(warehouseRow);
                currRow++;
            }

            while (s.hasNextLine()) {
                String directionString = s.nextLine();
                for (char dir : directionString.toCharArray()) {
                    switch (dir) {
                        case '^':
                            directions.add(0);
                            break;
                        case '>':
                            directions.add(1);
                            break;
                        case 'v':
                            directions.add(2);
                            break;
                        default:
                            directions.add(3);
                    }
                }
            }

            s.close();  

            int step = 0;
            for (int direction : directions) {
                boolean moveBoxes = handleDirection(warehouseFloor, robotX, robotY, direction);

                if (moveBoxes) {
                    switch (direction) {
                        case 0:
                            robotX--;
                            break;
                        case 1:
                            robotY++;
                            break;
                        case 2:
                            robotX++;
                            break;
                        default:
                            robotY--;
                    }
                }

                FileWriter output = new FileWriter("samples/step_" + step + ".txt");

                for (int row = 0; row < warehouseFloor.size(); row++) {
                    for (int col = 0; col < warehouseFloor.get(row).size(); col++) {
                        switch (warehouseFloor.get(row).get(col)) {
                            case 4:
                                output.write('#');
                                break;
                            case 3:
                                output.write('@');
                                break;
                            case 2:
                                output.write(']');
                                break;
                            case 1:
                                output.write('[');
                                break;
                            default:
                                output.write('.');
                        }
                    }
                    output.write("\n");
                }
                output.write("(" + robotX + "," + robotY + ")\n");

                output.close();
                step++;
            }

            int gpsScore = 0;
            for (int row = 0; row < warehouseFloor.size(); row++) {
                for (int col = 0; col < warehouseFloor.get(row).size(); col++) {
                    if (warehouseFloor.get(row).get(col) == 1) {
                        gpsScore += (row * 100 + col);
                    }
                }
            }
            
            System.out.println("GPS score of floor is " + gpsScore);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}