import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

class PrintQueueV2 {

    static int orderingValue(Map<Integer, Set<Integer>> prerequisites, String[] line) {
        Set<Integer> nums = new HashSet<Integer>();
        for (String n : line) {
            nums.add(Integer.parseInt(n));
        }

        List<Integer> ordering = new ArrayList<Integer>();
        while (nums.size() > 0) {
            for (int i = 0; i < line.length; i++) {
                int num = Integer.parseInt(line[i]);

                Set<Integer> intersection = new HashSet<Integer>();
                if (prerequisites.get(num) != null) {
                    intersection = new HashSet<Integer>(nums);
                    intersection.retainAll(prerequisites.get(num));
                }

                if (nums.contains(num) && intersection.isEmpty()) {
                    ordering.add(num);
                    nums.remove(num);
                }
            }
        }

        boolean changed = false;
        for (int i = 0; i < ordering.size(); i++) {
            if (ordering.get(i) != Integer.parseInt(line[i])) {
                changed = true;
            }
        }

        return ((changed) ? (ordering.get(ordering.size() / 2)) : 0);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PrintQueueV2 [filename]");
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