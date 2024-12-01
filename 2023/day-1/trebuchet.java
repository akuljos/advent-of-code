import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.lang.Character;

class Trebuchet {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Trebuchet [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                int previousNum = -1;

                for (char c : line.toCharArray()) {
                    if (Character.isDigit(c)) {
                        int digVal = c - '0';

                        if (previousNum == -1) {
                            sum += (10 * digVal);
                        }

                        previousNum = digVal;
                    }
                }

                sum += previousNum;
            }

            System.out.println("Trebuchet sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}