import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class HistorianHysteriaV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HistorianHysteriaV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();
            List<Integer> queries = new ArrayList<Integer>();

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String [] numbers = line.split("   ");

                int groundTruth = Integer.parseInt(numbers[0]); int found = Integer.parseInt(numbers[1]);

                if (countMap.containsKey(found)) {
                    countMap.put(found, countMap.get(found) + 1);
                } else {
                    countMap.put(found, 1);
                }
                queries.add(groundTruth);
            }
            s.close();

            int sum = 0;

            for (int query : queries) {
                if (countMap.containsKey(query)) {
                    sum += (query * countMap.get(query));
                }
            }


            System.out.println("Historian hysteria sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}