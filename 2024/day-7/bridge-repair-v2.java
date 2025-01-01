import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

class BridgeRepairV2 {

    static boolean add(String[] stones, long expectedTotal, long currentTotal, int idx) {
        if (idx == stones.length) {
            return (expectedTotal == currentTotal);
        }

        long currentValue = Long.parseLong(stones[idx]);
        return (add(stones, expectedTotal, currentTotal + currentValue, idx + 1) 
             || multiply(stones, expectedTotal, currentTotal + currentValue, idx + 1)
             || concat(stones, expectedTotal, currentTotal + currentValue, idx + 1));
    }

    static boolean multiply(String[] stones, long expectedTotal, long currentTotal, int idx) {
        if (idx == stones.length) {
            return (expectedTotal == currentTotal);
        }

        long currentValue = Long.parseLong(stones[idx]);
        return (add(stones, expectedTotal, currentTotal * currentValue, idx + 1) 
             || multiply(stones, expectedTotal, currentTotal * currentValue, idx + 1)
             || concat(stones, expectedTotal, currentTotal * currentValue, idx + 1));
    }

    static boolean concat(String[] stones, long expectedTotal, long currentTotal, int idx) {
        if (idx == stones.length) {
            return (expectedTotal == currentTotal);
        }

        long currentValue = Long.parseLong(stones[idx]);
        int currentValueLen = stones[idx].length();
        return (add(stones, expectedTotal, currentTotal * (long)(Math.pow(10, currentValueLen)) + currentValue, idx + 1)
             || multiply(stones, expectedTotal, currentTotal * (long)(Math.pow(10, currentValueLen)) + currentValue, idx + 1)
             || concat(stones, expectedTotal, currentTotal * (long)(Math.pow(10, currentValueLen)) + currentValue, idx + 1));
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java BridgeRepairV2 [filename]");
            return;
        }

        String filename = args[0];
        long sum = 0;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();

                String[] values = line.split(": ");
                long total = Long.parseLong(values[0]); 
                
                String bridge = values[1]; String[] stones = bridge.split(" ");
                
                long firstValue = Long.parseLong(stones[0]);
                if (add(stones, total, firstValue, 1) || multiply(stones, total, firstValue, 1) || concat(stones, total, firstValue, 1)) {
                    sum += total;
                }
            }

            s.close();

            System.out.println("Bridge repair sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}