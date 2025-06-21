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

class ClawContraption {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ClawContraption [filename]");
            return;
        }

        String filename = args[0];

        Pattern xEq = Pattern.compile("X=([0-9]*)");
        Pattern xPl = Pattern.compile("X\\+([0-9]*)");
        Pattern yEq = Pattern.compile("Y=([0-9]*)");
        Pattern yPl = Pattern.compile("Y\\+([0-9]*)");

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int numTokens = 0;

            while (s.hasNextLine()) {
                int xA = 0; int xB = 0; int xPrize = 0;
                int yA = 0; int yB = 0; int yPrize = 0;

                String buttonA = s.nextLine();
                Matcher aXMatcher = xPl.matcher(buttonA);
                if (aXMatcher.find()) {
                    xA = Integer.parseInt(aXMatcher.group(1));
                }
                Matcher aYMatcher = yPl.matcher(buttonA);
                if (aYMatcher.find()) {
                    yA = Integer.parseInt(aYMatcher.group(1));
                }

                String buttonB = s.nextLine();
                Matcher bXMatcher = xPl.matcher(buttonB);
                if (bXMatcher.find()) {
                    xB = Integer.parseInt(bXMatcher.group(1));
                }
                Matcher bYMatcher = yPl.matcher(buttonB);
                if (bYMatcher.find()) {
                    yB = Integer.parseInt(bYMatcher.group(1));
                }

                String prize = s.nextLine();
                Matcher prizeXMatcher = xEq.matcher(prize);
                if (prizeXMatcher.find()) {
                    xPrize = Integer.parseInt(prizeXMatcher.group(1));
                }
                Matcher prizeYMatcher = yEq.matcher(prize);
                if (prizeYMatcher.find()) {
                    yPrize = Integer.parseInt(prizeYMatcher.group(1));
                }

                int determinent = xA * yB - xB * yA;
                
                if (determinent != 0) {
                    float xSol = (float)yB / determinent * xPrize - (float)xB / determinent * yPrize;
                    float ySol = (float)xA / determinent * yPrize - (float)yA / determinent * xPrize;

                    float xSolRounded = Math.round(xSol * 100) / 100.00f;
                    float ySolRounded = Math.round(ySol * 100) / 100.00f;

                    if (xSolRounded % 1 == 0 && ySolRounded % 1 == 0) {
                        numTokens += (int)xSolRounded * 3 + (int)ySolRounded;
                    }
                }

                if (s.hasNextLine()) {
                    s.nextLine();
                }
            }

            s.close();  
            
            System.out.println("Number of tokens is " + numTokens);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}