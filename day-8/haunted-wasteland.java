import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class HauntedWasteland {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HauntedWasteland [filename]");
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

            Map<String, String []> moveMap = new HashMap<String, String []>();
            while (s.hasNextLine()) {
                String lineHa = s.nextLine();
                String [] line = lineHa.split(" = ");
                String[] leftRight = line[1].substring(1, line[1].length() - 1).split(", ");

                moveMap.put(line[0], leftRight);
            }

            String currLoc = "AAA";
            int steps = 0;

            while (!currLoc.equals("ZZZ")) {
                char dir = moveOrder[idx];
                String [] spotMoves = moveMap.get(currLoc);

                if (dir == 'L') {
                    currLoc = spotMoves[0];
                } else {
                    currLoc = spotMoves[1];
                }

                steps += 1;
                idx = (idx + 1) % n;
            }

            System.out.println("Total step count is " + steps);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


    }
}