import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Lagoon {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Lagoon [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<Integer> xPos = new ArrayList<Integer>(); xPos.add(0);
            List<Integer> yPos = new ArrayList<Integer>(); yPos.add(0);

            int currX = 0; int currY = 0;
            int numBoundary = 0;

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" ");
                String dir = line[0]; int steps = Integer.parseInt(line[1]);

                switch (dir) {
                    case "U":
                        currX -= steps;
                        break;
                    case "D":
                        currX += steps;
                        break;
                    case "L":
                        currY -= steps;
                        break;
                    case "R":
                        currY += steps;
                        break;
                    default:
                        break;
                }

                xPos.add(currX);
                yPos.add(currY);

                numBoundary += steps;
            }

            int numPoints = xPos.size();
            int area = 0;

            for (int i = 0; i < numPoints; i++) {
                area += (xPos.get(i) * yPos.get((i+1) % numPoints) - yPos.get(i) * xPos.get((i+1) % numPoints));
            }

            area = Math.abs(area);
            area /= 2;

            int numInterior = (2 * (area + 1) - numBoundary) / 2;
            int totalDug = numBoundary + numInterior;

            System.out.println("Total dug is " + totalDug);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}