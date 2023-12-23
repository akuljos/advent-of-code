import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Idea! Subtract the difference from the second iteration seen and the first iteration seen
// for each key, and then LCD!

class HauntedWastelandV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HauntedWastelandV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            char [] moveOrder = s.nextLine().toCharArray();
            int idx = 0;
            int n = moveOrder.length;

            s.nextLine();

            List<String> currLocs = new ArrayList<String>();
            List<Long> differences = new ArrayList<Long>();

            Map<String, String []> moveMap = new HashMap<String, String []>();
            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" = ");
                String[] leftRight = line[1].substring(1, line[1].length() - 1).split(", ");

                moveMap.put(line[0], leftRight);

                if (line[0].substring(2,3).equals("A")) {
                    currLocs.add(line[0]);
                }
            }

            for (int i = 0; i < currLocs.size(); i++) {
                String currLoc = currLocs.get(i);
                long steps = 0;

                while (!currLoc.substring(2,3).equals("Z")) {
                    char dir = moveOrder[idx];
                    String [] spotMoves = moveMap.get(currLoc);

                    if (dir == 'L') {
                        currLoc = spotMoves[0];
                    } else {
                        currLoc = spotMoves[1];
                    }

                    steps += ((long)1);
                    idx = (idx + 1) % n;
                }

                differences.add(steps);
            }

            while (differences.size() != 1) {
                long numOne = differences.remove(0);
                long numTwo = differences.remove(0);

                long a = numOne;
                long b = numTwo;

                while (b != 0) {
                    long tmp = b;
                    b = a % b;
                    a = tmp;
                }

                differences.add((numOne * numTwo)/a);
            }

            System.out.println("Minimum steps is " + differences.get(0));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


    }
}