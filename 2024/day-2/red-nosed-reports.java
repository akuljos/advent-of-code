import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RedNosedReports {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java RedNosedReports [filename]");
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

                boolean inc = (numbers.get(0) < numbers.get(numbers.size() - 1)) ? true : false;
                boolean safe = true;

                for (int i = 0; i < numbers.size() - 1; i++) {
                    int diff = numbers.get(i+1) - numbers.get(i);
                    if (inc) {
                        if (diff < 1 || diff > 3) {
                            safe = false;
                            break;
                        }
                    } else {
                        if (diff > -1 || diff < -3) {
                            safe = false;
                            break;
                        }
                    }
                }

                safeCount += ((safe) ? 1 : 0);
            }
            s.close();

            System.out.println("Red-nosed reports count is " + safeCount);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}