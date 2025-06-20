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

class PlutonianPebblesV2 {

    static long plutonianPebblesRec(Map<Long, Map<Integer, Long>> rockResults, long rock, int turn) {
        if (turn == 75) {
            return 1;
        }

        if (rockResults.containsKey(rock) && rockResults.get(rock).containsKey(turn)) {
            return rockResults.get(rock).get(turn);
        }

        long rockResult = 0;
        if (rock == 0) {
            rockResult += plutonianPebblesRec(rockResults, 1, turn + 1);
        } else {
            String strRock = Long.toString(rock);
            if (strRock.length() % 2 == 0) {
                String newRockLeft = strRock.substring(0, strRock.length() / 2);
                String newRockRight = strRock.substring(strRock.length() / 2);

                rockResult += plutonianPebblesRec(rockResults, Long.parseLong(newRockLeft), turn + 1);
                rockResult += plutonianPebblesRec(rockResults, Long.parseLong(newRockRight), turn + 1);
            } else {
                rockResult = plutonianPebblesRec(rockResults, rock * 2024, turn + 1);
            }
        }

        if (!rockResults.containsKey(rock)) {
            rockResults.put(rock, new HashMap<Integer, Long>());
        }
        rockResults.get(rock).put(turn, rockResult);

        return rockResult;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PlutonianPebblesV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String rocks = s.nextLine();

            s.close();

            Map<Long, Map<Integer, Long>> rockResults = new HashMap<Long, Map<Integer, Long>>();

            long numRocks = 0;
            for (String rock : rocks.split(" ")) {
                numRocks += plutonianPebblesRec(rockResults, Long.parseLong(rock), 0);
            }

            System.out.println("Number of rocks is " + numRocks);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}