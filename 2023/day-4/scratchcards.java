import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

class ScratchCards {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ScratchCards [filename]");
            return;
        }    

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                int counter = -1;

                String [] lines = s.nextLine().split(":  |: ")[1].split(" \\|  | \\| ");
                String [] winningNumbers = lines[0].split("  | ");
                String [] ticketNumbers = lines[1].split("  | ");

                for (int i = 0; i < winningNumbers.length; i++) {
                    for (int j = 0; j < ticketNumbers.length; j++) {
                        
                        if (winningNumbers[i].equals(ticketNumbers[j])) {
                            counter += 1;
                            break;
                        }

                    }
                }

                sum += Math.pow(2, counter);
            }

            System.out.println("Scratchcards total is " + sum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }


}