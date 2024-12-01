import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LensLibrary {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LensLibrary [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String [] initializers = s.nextLine().split(",");
            int sum = 0;

            for (String init : initializers) {
                int currVal = 0;

                for (char point : init.toCharArray()) {
                    currVal += (int)point;
                    currVal *= 17;
                    currVal %= 256;
                }

                sum += currVal;
            }

            System.out.println("Lens sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}