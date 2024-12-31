import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BridgeRepair {

    static boolean add(String[] stones, long expectedTotal, long currentTotal, int idx) {
        if (idx == stones.length) {
            return (expectedTotal == currentTotal);
        }

        long currentValue = Long.parseLong(stones[idx]);
        return (add(stones, expectedTotal, currentTotal + currentValue, idx + 1) || multiply(stones, expectedTotal, currentTotal + currentValue, idx + 1));
    }

    static boolean multiply(String[] stones, long expectedTotal, long currentTotal, int idx) {
        if (idx == stones.length) {
            return (expectedTotal == currentTotal);
        }

        long currentValue = Long.parseLong(stones[idx]);
        return (add(stones, expectedTotal, currentTotal * currentValue, idx + 1) || multiply(stones, expectedTotal, currentTotal * currentValue, idx + 1));
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java BridgeRepair [filename]");
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
                if (add(stones, total, firstValue, 1) || multiply(stones, total, firstValue, 1)) {
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