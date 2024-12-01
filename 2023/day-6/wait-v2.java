import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class WaitV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java WaitV2 [filename]");
            return;
        }   

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String timeInput = s.nextLine();
            String [] times = timeInput.split("\\s+");
            String timeString = "";
            
            for (int i = 1; i < times.length; i++) {
                timeString += times[i];
            }

            long time = Long.parseLong(timeString);
            
            String distanceInput = s.nextLine();
            String [] distances = distanceInput.split("\\s+");
            String distString = "";

            for (int i = 1; i < distances.length; i++) {
                distString += distances[i];
            }

            long distance = Long.parseLong(distString);

            Long prod = (long)1;

            for (int i = 0; i <= time; i++) {
                Long millimeters = i * (time - i);

                if (millimeters > distance) {
                    prod = time - 2 * i + ((time % 2 == 0) ? 1 : 0);
                    break;
                } 
            }

            System.out.println("Total number of ways is " + prod);            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}