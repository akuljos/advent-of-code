import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// Idea! 
// Keep track of pairs (seedStart, seedRange)
// At each processing point, sort lists based on srcStart and apply to each pair of (seedStart, seedRange), breaking up into multiple pairs if needed

class FertilizerV2 {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java FertilizerV2 [filename]");
            return;
        }   

        String filename = args[0];

        List<List<Long>> seeds = new ArrayList<List<Long>>();

        Comparator<List<Long>> rangeComp = new Comparator<List<Long>>() {
            public int compare(List<Long> t1, List<Long> t2) {
                return t1.get(0).compareTo(t2.get(0));
            }
        };

        List<List<Long>> rangeTracker = new ArrayList<List<Long>>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String [] seedStrs = s.nextLine().split(": ")[1].split(" ");
            for (int i = 0; i < seedStrs.length; i += 2) {
                List<Long> seedRange = new ArrayList<Long>();

                seedRange.add(Long.parseLong(seedStrs[i]));
                seedRange.add(Long.parseLong(seedStrs[i+1]));

                seeds.add(seedRange);
            }

            s.nextLine();
            s.nextLine();

            while (s.hasNextLine()) {
                String line = s.nextLine();

                if (line.length() == 0) {

                    rangeTracker.sort(rangeComp);

                    List<List<Long>> newSeeds = new ArrayList<List<Long>>();

                    for (int idx = 0; idx < seeds.size(); idx++) {
                        List<Long> seed = seeds.get(idx);
                        Long seedStart = seed.get(0); 
                        Long seedRange = seed.get(1);

                        boolean flag = false;

                        for (int i = 0; i < rangeTracker.size(); i++) {
                            List<Long> range = rangeTracker.get(i);
                            Long srcStart = range.get(0); Long destStart = range.get(1); Long rngLen = range.get(2);

                            if (seedStart >= srcStart && seedStart < (srcStart + rngLen)) {
                                if ((seedStart + seedRange) >= (srcStart + rngLen)) {
                                    Long numSeeds = srcStart + rngLen - seedStart;
                                    seedRange -= numSeeds;

                                    List<Long> newSeed = new ArrayList<Long>();
                                    newSeed.add(destStart - srcStart + seedStart);
                                    newSeed.add(numSeeds);

                                    newSeeds.add(newSeed);

                                    seedStart += numSeeds;
                                } else {
                                    List<Long> newSeed = new ArrayList<Long>();
                                    newSeed.add(destStart - srcStart + seedStart);
                                    newSeed.add(seedRange);

                                    newSeeds.add(newSeed);

                                    seedRange = (long)0;
                                }

                                flag = true;
                            }

                            if (seedRange == 0) {
                                break;
                            }
                        }

                        if (!flag) {
                            newSeeds.add(seed);
                        }
                    }

                    seeds = newSeeds;

                    rangeTracker = new ArrayList<List<Long>>();

                    s.nextLine();

                } else {
                    String [] mapVals = line.split(" ");
                    List<Long> listMapVals = new ArrayList<Long>();

                    listMapVals.add(Long.parseLong(mapVals[1]));
                    listMapVals.add(Long.parseLong(mapVals[0]));
                    listMapVals.add(Long.parseLong(mapVals[2]));

                    rangeTracker.add(listMapVals);
                }
            }

            List<List<Long>> newSeeds = new ArrayList<List<Long>>();

            for (int idx = 0; idx < seeds.size(); idx++) {
                List<Long> seed = seeds.get(idx);
                Long seedStart = seed.get(0); 
                Long seedRange = seed.get(1);

                boolean flag = false;

                for (int i = 0; i < rangeTracker.size(); i++) {
                    List<Long> range = rangeTracker.get(i);
                    Long srcStart = range.get(0); Long destStart = range.get(1); Long rngLen = range.get(2);

                    if (seedStart >= srcStart && seedStart < (srcStart + rngLen)) {
                        if ((seedStart + seedRange) >= (srcStart + rngLen)) {
                            Long numSeeds = srcStart + rngLen - seedStart;
                            seedRange -= numSeeds;

                            List<Long> newSeed = new ArrayList<Long>();
                            newSeed.add(destStart - srcStart + seedStart);
                            newSeed.add(numSeeds);

                            newSeeds.add(newSeed);

                            seedStart += numSeeds;
                        } else {
                            List<Long> newSeed = new ArrayList<Long>();
                            newSeed.add(destStart - srcStart + seedStart);
                            newSeed.add(seedRange);

                            newSeeds.add(newSeed);

                            seedRange = (long)0;
                        }

                        flag = true;
                    }

                    if (seedRange == 0) {
                        break;
                    }
                }

                if (!flag) {
                    newSeeds.add(seed);
                }
            }

            seeds = newSeeds;

            long min = (long)Math.pow(10,15);
            for (int i = 0; i < seeds.size(); i++) {
                min = Math.min(min, seeds.get(i).get(0));
            }

            System.out.println("The minimum location number is " + min);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }

}