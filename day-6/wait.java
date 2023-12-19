import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Wait {

    static List<Long> getProdArray(long number) {
        List<Long> prodArray = new ArrayList<Long>();

        for (int i = 0; i < number; i++) {
            prodArray.add(i * (number - i));
        }

        return prodArray;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Wait [filename]");
            return;
        }   

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<Long> times = new ArrayList<Long>();

            String timeStr = s.nextLine();
            String [] timeStrings = timeStr.split("\\s+");

            for (int i = 1; i < timeStrings.length; i++) {
                times.add(Long.parseLong(timeStrings[i]));
            }

            List<Long> distances = new ArrayList<Long>();

            String distanceStr = s.nextLine();
            String [] distStrings = distanceStr.split("\\s+");

            for (int i = 1; i < distStrings.length; i++) {
                distances.add(Long.parseLong(distStrings[i]));
            }

            Long prod = (long)1;

            for (int i = 0; i < times.size(); i++) {
                Long currTime = times.get(i);
                Long currDistance = distances.get(i);

                int sum = 0;

                List<Long> prodArray = getProdArray(currTime);

                for (int j = 1; j < prodArray.size(); j++) {
                    Long milliseconds = prodArray.get(j);

                    if (milliseconds > currDistance) {
                        sum += 1;
                    }
                }

                prod *= sum;
            }

            System.out.println("Total number of ways is " + prod);            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}