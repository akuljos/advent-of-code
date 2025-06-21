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

class RestroomRedoubt {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RestroomRedoubt [filename]");
            return;
        }

        String filename = args[0];

        Pattern posVeloPattern = Pattern.compile("p=([0-9]*),([0-9]*) v=([0-9[\\-]]*),([0-9[\\-]]*)");

        int gridLength = 101;
        int gridHeight = 103;
        int numberOfSteps = 100;

        int midPointLength = gridLength / 2;
        int midPointHeight = gridHeight / 2;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int q1 = 0; int q2 = 0; int q3 = 0; int q4 = 0;

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

                int finalX = posX + numberOfSteps * velX;
                int finalY = posY + numberOfSteps * velY;

                finalX = Math.floorMod(finalX, gridLength);
                finalY = Math.floorMod(finalY, gridHeight);

                if (finalX < midPointLength && finalY < midPointHeight) {
                    q1++;
                }
                if (finalX > midPointLength && finalY < midPointHeight) {
                    q2++;
                }
                if (finalX < midPointLength && finalY > midPointHeight) {
                    q3++;
                }
                if (finalX > midPointLength && finalY > midPointHeight) {
                    q4++;
                }
            }

            s.close();  

            System.out.println("Safety factor of floor is " + (q1 * q2 * q3 * q4));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}