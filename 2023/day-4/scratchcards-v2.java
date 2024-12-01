import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ScratchCardsV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ScratchCardsV2 [filename]");
            return;
        }    

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            List<Integer> winCount = new ArrayList<Integer>();
            List<Integer> numTickets = new ArrayList<Integer>();

            while (s.hasNextLine()) {
                int counter = 0;

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

                winCount.add(counter);
                numTickets.add(1);
            }

            int ticketCount = numTickets.size();

            for (int i = 0; i < ticketCount; i++) {
                int currWinCount = winCount.get(i);
                int currNumTickets = numTickets.get(i);

                sum += currNumTickets;

                for (int j = 1; j <= currWinCount; j++) {
                    if ((i+j) >= ticketCount) {
                        break;
                    }

                    numTickets.set(i+j, numTickets.get(i+j) + currNumTickets);
                }
            }

            System.out.println("Scratchcards total is " + sum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }


}