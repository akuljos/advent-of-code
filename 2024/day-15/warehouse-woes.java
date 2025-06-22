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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class WarehouseWoes {

    static boolean handleDirection(List<List<Integer>> warehouseFloor, int robotX, int robotY, int direction) {
        int currX;
        int currY;
        boolean moveBoxes;
        
        switch (direction) {
            case 0:
                currX = robotX;
                moveBoxes = true;

                while (warehouseFloor.get(currX-1).get(robotY) != 0) {
                    if (warehouseFloor.get(currX-1).get(robotY) == 3) {
                        moveBoxes = false;
                        break;
                    }
                    currX--;
                }

                if (moveBoxes) {
                    while (currX != (robotX + 1)) {
                        warehouseFloor.get(currX-1).set(robotY, warehouseFloor.get(currX).get(robotY));
                        warehouseFloor.get(currX).set(robotY, 0);
                        currX++;
                    }
                }

                break;
            case 1:
                currY = robotY;
                moveBoxes = true;

                while (warehouseFloor.get(robotX).get(currY+1) != 0) {
                    if (warehouseFloor.get(robotX).get(currY+1) == 3) {
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
                currX = robotX;
                moveBoxes = true;

                while (warehouseFloor.get(currX+1).get(robotY) != 0) {
                    if (warehouseFloor.get(currX+1).get(robotY) == 3) {
                        moveBoxes = false;
                        break;
                    }
                    currX++;
                }

                if (moveBoxes) {
                    while (currX != (robotX - 1)) {
                        warehouseFloor.get(currX+1).set(robotY, warehouseFloor.get(currX).get(robotY));
                        warehouseFloor.get(currX).set(robotY, 0);
                        currX--;
                    }
                }

                break;
            case 3:
                currY = robotY;
                moveBoxes = true;

                while (warehouseFloor.get(robotX).get(currY-1) != 0) {
                    if (warehouseFloor.get(robotX).get(currY-1) == 3) {
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

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java WarehouseWoes [filename]");
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
                            warehouseRow.add(3);
                            break;
                        case '@':
                            warehouseRow.add(2);
                            robotX = currRow;
                            robotY = currCol;
                            break;
                        case 'O':
                            warehouseRow.add(1);
                            break;
                        default:
                            warehouseRow.add(0);
                    }
                    currCol++;
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