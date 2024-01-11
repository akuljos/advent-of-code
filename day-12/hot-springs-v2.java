import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class HotSpringsV2 {

    static Map<String, Long> memoTable;

    static long numCombinations (char [] record, List<Integer> springs, int recordIndex, int springIndex) {
        String indexStr = recordIndex + "," + springIndex;

        if (memoTable.containsKey(indexStr)) {
            return memoTable.get(indexStr);
        }

        if (springIndex == springs.size()) {
            for (int i = recordIndex; i < record.length; i++) {
                if (record[i] == '#') {
                    return (long)0;
                }
            }
            return (long)1;
        }

        if (recordIndex >= record.length) {
            if (springIndex == springs.size()) {
                return (long)1;
            }
            return (long)0;
        }

        long combos = 0;

        char currRecord = record[recordIndex];
        int currSpring = springs.get(springIndex);

        if (currRecord == '.' || currRecord == '?') {
            combos += numCombinations(record, springs, recordIndex + 1, springIndex);
        }

        if (currRecord == '#' || currRecord == '?') {
            if (currSpring <= (record.length - recordIndex)) {
                boolean noDotsFlag = true;

                for (int i = recordIndex; i < recordIndex + currSpring; i++) {
                    if (record[i] == '.') {
                        noDotsFlag = false;
                    }
                }

                if (noDotsFlag && (currSpring == (record.length - recordIndex) || record[recordIndex+currSpring] != '#')) {
                    combos += numCombinations(record, springs, recordIndex + currSpring + 1, springIndex + 1);
                }
            }
        }

        memoTable.put(indexStr, combos);

        return combos;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HotSpringsV2 [filename]");
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

                memoTable = new HashMap<String, Long>();

                List<Integer> springs = new ArrayList<Integer>();
                
                for (int i = 0; i < 5; i++) {
                    for (String spring : line[1].split(",")) {
                        springs.add(Integer.parseInt(spring));
                    }
                }

                char [] realRecord = new char[record.length() * 5 + 4];
                int idx = 0;

                for (int i = 0; i < 5; i++) {
                    for (char space : record.toCharArray()) {
                        realRecord[idx] = space;
                        idx += 1;
                    }

                    if (i != 4) {
                        realRecord[idx] = '?';
                        idx += 1;
                    }
                }

                combos += numCombinations(realRecord, springs, 0, 0);
            }

            System.out.println("Num combos is " + combos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

}