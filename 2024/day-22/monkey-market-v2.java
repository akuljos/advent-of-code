import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class MonkeyMarketV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java MonkeyMarketV2 [filename]");
            return;
        }
        
        String filename = args[0];

        Map<String, Long> patternBananaMap = new HashMap<String, Long>();
        Map<String, Integer> patternIterMap = new HashMap<String, Integer>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int iter = 1;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                Long secretNumber = Long.parseLong(line);

                long prevDigit = secretNumber % 10;
                List<Long> difference = new ArrayList<Long>();
                List<Long> bananas = new ArrayList<Long>();

                bananas.add(prevDigit);

                for (int i = 0; i < 2000; i++) {
                    secretNumber ^= (secretNumber * 64);
                    secretNumber %= 16777216;

                    secretNumber ^= (secretNumber / 32);
                    secretNumber %= 16777216;

                    secretNumber ^= (secretNumber * 2048);
                    secretNumber %= 16777216;

                    long tmp = secretNumber % 10;
                    long diff = tmp - prevDigit;
                    difference.add(diff);
                    prevDigit = tmp;
                    bananas.add(prevDigit);
                }

                for (int i = 4; i < bananas.size(); i++) {
                    String pattern = difference.subList(i-4, i).toString();
                    
                    if (!patternIterMap.containsKey(pattern)) {
                        patternIterMap.put(pattern, 0);
                        patternBananaMap.put(pattern, 0L);
                    }
                    if (patternIterMap.get(pattern) != iter) {
                        patternBananaMap.put(pattern, patternBananaMap.get(pattern) + bananas.get(i));
                        patternIterMap.put(pattern, iter);
                    }
                }
                iter++;
            }

            s.close();

            String maxPattern = "";
            long maxBananas = 0;
            for (String key : patternBananaMap.keySet()) {
                if (patternBananaMap.get(key) > maxBananas) {
                    maxPattern = key;
                    maxBananas = patternBananaMap.get(key);
                }
            }

            System.out.println("The combined secret number (pattern " + maxPattern + ") is " + maxBananas);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}