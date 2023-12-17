import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Fertilizer {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Fertilizer [filename]");
            return;
        }   

        String filename = args[0];

        List<Long> seeds = new ArrayList<Long>();

        List<Long> destStart = new ArrayList<Long>();
        List<Long> srcStart = new ArrayList<Long>();
        List<Long> rngLen = new ArrayList<Long>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String [] seedStrs = s.nextLine().split(": ")[1].split(" ");
            for (String str : seedStrs) {
                seeds.add(Long.parseLong(str));
            }

            s.nextLine();
            s.nextLine();

            while (s.hasNextLine()) {
                String line = s.nextLine();

                if (line.length() == 0) {

                    for (int idx = 0; idx < seeds.size(); idx++) {
                        long seed = seeds.get(idx);

                        for (int i = 0; i < destStart.size(); i++) {
                            if (seed >= srcStart.get(i) && seed < (srcStart.get(i) + rngLen.get(i))) {
                                seeds.set(idx, (destStart.get(i) - srcStart.get(i) + seed));
                                break;
                            }
                        }
                    }

                    destStart = new ArrayList<Long>();
                    srcStart = new ArrayList<Long>();
                    rngLen = new ArrayList<Long>();

                    s.nextLine();

                } else {
                    String [] mapVals = line.split(" ");
                    destStart.add(Long.parseLong(mapVals[0]));
                    srcStart.add(Long.parseLong(mapVals[1]));
                    rngLen.add(Long.parseLong(mapVals[2]));
                }
            }

            for (int idx = 0; idx < seeds.size(); idx++) {
                long seed = seeds.get(idx);

                for (int i = 0; i < destStart.size(); i++) {
                    if (seed >= srcStart.get(i) && seed < (srcStart.get(i) + rngLen.get(i))) {
                        seeds.set(idx, (destStart.get(i) - srcStart.get(i) + seed));
                        break;
                    }
                }
            }

            long min = (long)Math.pow(10,10);
            for (int i = 0; i < seeds.size(); i++) {
                min = Math.min(min, seeds.get(i));
            }

            System.out.println("The minimum location number is " + min);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

}