import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

class PrintQueue {

    static int orderingValue(Map<Integer, Set<Integer>> prerequisites, String[] ordering) {
        for (int i = ordering.length - 1; i >= 0; i--) {
            int after = Integer.parseInt(ordering[i]);
            for (int j = i - 1; j >= 0; j--) {
                int before = Integer.parseInt(ordering[j]);
                if (prerequisites.containsKey(before) && prerequisites.get(before).contains(after)) {
                    return 0;
                }
            }
        }

        return Integer.parseInt(ordering[ordering.length / 2]);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PrintQueue [filename]");
            return;
        }

        String filename = args[0];
        Map<Integer, Set<Integer>> prerequisites = new HashMap<Integer, Set<Integer>>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line == "") break;

                String[] nums = line.split("\\|");

                int from = Integer.parseInt(nums[0]);
                int to = Integer.parseInt(nums[1]);

                if (prerequisites.containsKey(to)) {
                    prerequisites.get(to).add(from);
                } else {
                    Set<Integer> requirements = new HashSet<Integer>();
                    requirements.add(from);
                    prerequisites.put(to, requirements);
                }
            }

            while (s.hasNextLine()) {
                String line = s.nextLine();
                sum += orderingValue(prerequisites, line.split(","));
            }

            s.close();

            System.out.println("Print queue sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}