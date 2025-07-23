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

class LinenLayoutV2 {

    static long patternHelper(String pattern, String[] stripes) {

        long[] tracker = new long[pattern.length() + 1];
        tracker[pattern.length()] = 1;

        for (int idx = pattern.length() - 1; idx >= 0; idx--) {
            for (String stripe : stripes) {
                if ((idx + stripe.length()) <= (pattern.length()) && pattern.substring(idx, idx+stripe.length()).equals(stripe)) {
                    tracker[idx] += tracker[idx + stripe.length()];
                }
            }
        }

        return tracker[0];
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LinenLayoutV2 [filename]");
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

            long feasible = 0;
            for (String pattern : patterns) {
                feasible += patternHelper(pattern, stripes);
            }

            System.out.println("Number of feasible patterns is " + feasible);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}