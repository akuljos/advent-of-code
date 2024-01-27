import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LagoonV2 {

    static long charHex (char c) {
        switch (c) {
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
            default:
                return c - '0';
        }
    }

    static long hexValue (String hex) {
        char [] hexChars = hex.toCharArray();

        long hexVal = 0;

        for (int i = 0; i < 5; i++) {
            hexVal += (charHex(hexChars[i]) * Math.pow(16, 5 - i - 1));
        }

        return hexVal;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LagoonV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<Long> xPos = new ArrayList<Long>(); xPos.add((long)0);
            List<Long> yPos = new ArrayList<Long>(); yPos.add((long)0);

            long currX = 0; long currY = 0;
            long numBoundary = 0;

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" ");

                long steps = hexValue(line[2].substring(2,7));
                int dir = Integer.parseInt(line[2].substring(7,8));

                switch (dir) {
                    case 0:
                        currY += steps;
                        break;
                    case 1:
                        currX += steps;
                        break;
                    case 2:
                        currY -= steps;
                        break;
                    case 3:
                        currX -= steps;
                        break;
                    default:
                        break;
                }

                xPos.add(currX);
                yPos.add(currY);

                numBoundary += steps;
            }

            int numPoints = xPos.size();
            long area = 0;

            for (int i = 0; i < numPoints; i++) {
                area += (xPos.get(i) * yPos.get((i+1) % numPoints) - yPos.get(i) * xPos.get((i+1) % numPoints));
            }

            area = Math.abs(area);
            area /= 2;

            long numInterior = (2 * (area + 1) - numBoundary) / 2;
            long totalDug = numBoundary + numInterior;

            System.out.println("Total dug is " + totalDug);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}