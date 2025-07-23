import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class LinenLayout {

    static boolean patternHelper(String pattern, String[] stripes) {

        boolean[] tracker = new boolean[pattern.length() + 1];
        tracker[pattern.length()] = true;

        for (int idx = pattern.length() - 1; idx >= 0; idx--) {
            for (String stripe : stripes) {
                if ((idx + stripe.length()) <= (pattern.length()) && tracker[idx + stripe.length()] && pattern.substring(idx, idx+stripe.length()).equals(stripe)) {
                    tracker[idx] = true;
                }
            }
        }

        return tracker[0];
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LinenLayout [filename]");
            return;
        }
        
        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String line = s.nextLine();
            String[] stripes = line.split(", ");

            List<String> patterns = new ArrayList<String>();

            s.nextLine();
            while (s.hasNextLine()) {
                patterns.add(s.nextLine());
            }

            s.close();

            int feasible = 0;
            for (String pattern : patterns) {
                if(patternHelper(pattern, stripes)) {
                    feasible++;
                }
            }

            System.out.println("Number of feasible patterns is " + feasible);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}