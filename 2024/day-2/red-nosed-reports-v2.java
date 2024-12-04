import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RedNosedReportsV2 {

    static boolean safeCheckInc (List<Integer> numbers, int idx, boolean skip) {
        if (idx == (numbers.size() - 1) || (idx == (numbers.size() - 2) && skip)) return true;

        int singleDiff = (numbers.get(idx+1) - numbers.get(idx));
        if (idx == (numbers.size() - 2)) {
            return singleDiff >= 1 && singleDiff <= 3 && safeCheckInc(numbers, idx + 1, skip);
        }

        int doubleDiff = (numbers.get(idx+2) - numbers.get(idx));
        return (singleDiff >= 1 && singleDiff <= 3 && safeCheckInc(numbers, idx + 1, skip)) || (skip && doubleDiff >= 1 && doubleDiff <= 3 && safeCheckInc(numbers, idx + 2, false));
    }

    static boolean safeCheckDec (List<Integer> numbers, int idx, boolean skip) {
        if (idx == (numbers.size() - 1) || (idx == (numbers.size() - 2) && skip)) return true;

        int singleDiff = (numbers.get(idx+1) - numbers.get(idx));
        if (idx == (numbers.size() - 2)) {
            return singleDiff >= -3 && singleDiff <= -1 && safeCheckDec(numbers, idx + 1, skip);
        }

        int doubleDiff = (numbers.get(idx+2) - numbers.get(idx));
        return (singleDiff >= -3 && singleDiff <= -1 && safeCheckDec(numbers, idx + 1, skip)) || (skip && doubleDiff >= -3 && doubleDiff <= -1 && safeCheckDec(numbers, idx + 2, false));
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RedNosedReportsV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int safeCount = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                List<Integer> numbers = new ArrayList<Integer>();
                java.util.Arrays.stream(line.split(" ")).map(i -> Integer.parseInt(i)).forEach(i -> numbers.add(i));


                boolean safe = safeCheckInc(numbers, 0, true) || 
                safeCheckInc(numbers, 1, false) ||
                safeCheckDec(numbers, 0, true)  ||
                safeCheckDec(numbers, 1, false);
                
                if (safe) {
                    safeCount += 1;
                }
            }
            s.close();

            System.out.println("Red-nosed reports count is " + safeCount);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}