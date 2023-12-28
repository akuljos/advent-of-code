import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HotSprings {

    static boolean checkCombination (char [] record, List<Long> springs) {
        long hashCount = 0;
        int springIndex = 0;

        for (int i = 0; i < record.length; i++) {
            int recordChar = record[i];
            if (recordChar == '#') {
                hashCount += 1;
            } else {
                if (hashCount != 0) {
                    if (hashCount != springs.get(springIndex)) {
                        return false;
                    }

                    hashCount = 0;
                    springIndex += 1;

                    if (springIndex == springs.size()) {
                        return true;
                    }
                }
            }
        }

        if (springIndex != (springs.size() - 1)) {
            return false;
        } else {
            return (hashCount == springs.get(springIndex));
        }
    }

    static int numCombinations (char [] record, List<Long> springs, int index, long leftHashes) {
        if (index >= record.length) {
            if (checkCombination(record, springs)) {
                return 1;
            } else {
                return 0;
            }
        }

        char currSpace = record[index];
        if (currSpace == '.' || currSpace == '#') {
            return numCombinations(record, springs, index+1, leftHashes);
        } else if (leftHashes == 0) {
            int sum = 0;

            record[index] = '.';
            sum += numCombinations(record, springs, index+1, leftHashes);

            record[index] = '?';
            return sum;
        } else {
            int sum = 0;

            record[index] = '.';
            sum += numCombinations(record, springs, index+1, leftHashes);

            record[index] = '#';
            sum += numCombinations(record, springs, index+1, leftHashes-1);

            record[index] = '?';
            return sum;
        }
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HotSprings [filename]");
            return;
        }

        String filename = args[0];

        try {

            File f = new File(filename);
            Scanner s = new Scanner(f);
            
            long combos = 0;

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" ");
                String record = line[0]; 

                List<Long> springs = new ArrayList<Long>();
                long sum = 0;
                long numHashes = 0;
                
                for (String spring : line[1].split(",")) {
                    springs.add(Long.parseLong(spring));
                    sum += Long.parseLong(spring);
                }

                for (char space : record.toCharArray()) {
                    if (space == '#') {
                        numHashes += 1;
                    }
                }

                combos += numCombinations(record.toCharArray(), springs, 0, sum - numHashes);
            }

            System.out.println("Num combos is " + combos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}