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

class ClawContraptionV2 {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ClawContraptionV2 [filename]");
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

            long numTokens = 0;

            while (s.hasNextLine()) {
                long xA = 0; long xB = 0; long xPrize = 0;
                long yA = 0; long yB = 0; long yPrize = 0;

                String buttonA = s.nextLine();
                Matcher aXMatcher = xPl.matcher(buttonA);
                if (aXMatcher.find()) {
                    xA = Long.parseLong(aXMatcher.group(1));
                }
                Matcher aYMatcher = yPl.matcher(buttonA);
                if (aYMatcher.find()) {
                    yA = Long.parseLong(aYMatcher.group(1));
                }

                String buttonB = s.nextLine();
                Matcher bXMatcher = xPl.matcher(buttonB);
                if (bXMatcher.find()) {
                    xB = Long.parseLong(bXMatcher.group(1));
                }
                Matcher bYMatcher = yPl.matcher(buttonB);
                if (bYMatcher.find()) {
                    yB = Long.parseLong(bYMatcher.group(1));
                }

                String prize = s.nextLine();
                Matcher prizeXMatcher = xEq.matcher(prize);
                if (prizeXMatcher.find()) {
                    xPrize = Long.parseLong(prizeXMatcher.group(1));
                }
                Matcher prizeYMatcher = yEq.matcher(prize);
                if (prizeYMatcher.find()) {
                    yPrize = Long.parseLong(prizeYMatcher.group(1));
                }

                xPrize += 10000000000000L; yPrize += 10000000000000L;

                long determinent = xA * yB - xB * yA;
                
                if (determinent != 0) {
                    double xSol = (double)yB / determinent * xPrize - (double)xB / determinent * yPrize;
                    double ySol = (double)xA / determinent * yPrize - (double)yA / determinent * xPrize;

                    double xSolRounded = Math.round(xSol * 100) / 100.00d;
                    double ySolRounded = Math.round(ySol * 100) / 100.00d;

                    if (xSolRounded % 1 == 0 && ySolRounded % 1 == 0) {
                        numTokens += (long)xSolRounded * 3 + (long)ySolRounded;
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