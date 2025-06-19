import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

class PlutonianPebbles {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PlutonianPebbles [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String rocks = s.nextLine();

            s.close();

            List<Long> oldRocks = new ArrayList<Long>();
            for (String rock : rocks.split(" ")) {
                oldRocks.add(Long.parseLong(rock));
            }
            List<Long> newRocks = new ArrayList<Long>();

            for (int itr = 0; itr < 25; itr++) {
                for (Long rock : oldRocks) {
                    if (rock == 0) {
                        newRocks.add(1L);
                    } else {
                        String strRock = Long.toString(rock);
                        if (strRock.length() % 2 == 0) {
                            String newRockLeft = strRock.substring(0, strRock.length() / 2);
                            String newRockRight = strRock.substring(strRock.length() / 2);

                            newRocks.add(Long.parseLong(newRockLeft));
                            newRocks.add(Long.parseLong(newRockRight));
                        } else {
                            newRocks.add(rock * 2024);
                        }
                    }
                }
                oldRocks = newRocks;
                newRocks = new ArrayList<Long>();
            }

            System.out.println("Number of rocks is " + oldRocks.size());
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}