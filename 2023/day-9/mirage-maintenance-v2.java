import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class MirageMaintenanceV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java MirageMaintenanceV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" ");

                List<Integer> differences = new ArrayList<Integer>();
                for (String num : line) {
                    differences.add(Integer.parseInt(num));
                }
                boolean seeNonzero = true;
                boolean isNegative = false;

                while (seeNonzero) {
                    seeNonzero = false;

                    List<Integer> newDifferences = new ArrayList<Integer>();
                    sum += (differences.get(0) * ((isNegative) ? (-1) : 1));

                    isNegative = !isNegative;

                    for (int i = 0; i < differences.size() - 1; i++) {
                        int newDiff = differences.get(i+1) - differences.get(i);
                        if (newDiff != 0) {
                            seeNonzero = true;
                        }
                        newDifferences.add(newDiff);
                    }

                    differences = newDifferences;
                }
            }

            System.out.println("Total prediction is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}