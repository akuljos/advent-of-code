import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

class MonkeyMarket {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java MonkeyMarket [filename]");
            return;
        }
        
        String filename = args[0];

        long totalSecretNumber = 0;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                Long secretNumber = Long.parseLong(line);

                for (int i = 0; i < 2000; i++) {
                    secretNumber ^= (secretNumber * 64);
                    secretNumber %= 16777216;

                    secretNumber ^= (secretNumber / 32);
                    secretNumber %= 16777216;

                    secretNumber ^= (secretNumber * 2048);
                    secretNumber %= 16777216;
                }

                totalSecretNumber += secretNumber;
            }

            s.close();

            System.out.println("The combined secret number is " + totalSecretNumber);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}