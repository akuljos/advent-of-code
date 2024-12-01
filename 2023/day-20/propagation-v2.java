import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class PropagationV2 {

    static long lcmOfGroup (List<Long> lcmNumbers) {
        while (lcmNumbers.size() > 1) {
            long num2 = lcmNumbers.remove(1);
            long num1 = lcmNumbers.remove(0);

            long a = Math.max(num1,num2);
            long b = Math.min(num1,num2);

            while (b > 0) {
                long rem = a % b;
                a = b;
                b = rem;
            }

            lcmNumbers.add((num1 * num2) / a);
        }

        return lcmNumbers.get(0);
    }


    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java PropagationV2 [filename]");
            return;
        }

        String filename = args[0];

        Map<String,String[]> destMap = new HashMap<String,String[]>();
        Map<String,Integer> typeMap = new HashMap<String,Integer>(); // 0 flip-flop, 1 conjunction

        Map<String,Integer> onMap = new HashMap<String,Integer>(); // 0 off, 1 on
        Map<String,Map<String,Integer>> conjMap = new HashMap<String,Map<String,Integer>>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" -> ");
                
                if (line[0].equals("broadcaster")) {
                    destMap.put(line[0], line[1].split(", "));
                    continue;
                }

                String[] dests = line[1].split(", ");
                
                String name = line[0].substring(1);
                int type = (line[0].substring(0,1).equals("&") ? 1 : 0);

                destMap.put(name, dests);
                typeMap.put(name, type);

                for (String dest : dests) {
                    if (!conjMap.containsKey(dest)) {
                        conjMap.put(dest, new HashMap<String,Integer>());
                    }

                    conjMap.get(dest).put(name,0);
                }

                if (type == 0) {
                    onMap.put(name, 0);
                }
            }

            List<String> conjKeys = new ArrayList<String>(conjMap.keySet());

            for (int i = 0; i < conjKeys.size(); i++) {
                String module = conjKeys.get(i);

                if (!typeMap.containsKey(module) || typeMap.get(module) != 1) {
                    conjMap.remove(module);
                }
            }

            Map<String,Long> numPresses = new HashMap<String,Long>();
            Set<String> prevModules = new HashSet<String>(conjMap.get("rs").keySet());

            long buttonPresses = 1;

            while (true) {
                List<String> moduleQueue = new ArrayList<String>();
                List<String> prevQueue = new ArrayList<String>();
                List<Integer> pulseQueue = new ArrayList<Integer>();

                String [] broadcastDests = destMap.get("broadcaster");

                for (int i = 0; i < broadcastDests.length; i++) {
                    moduleQueue.add(broadcastDests[i]);
                    prevQueue.add("broadcaster");
                    pulseQueue.add(0);
                }

                while (moduleQueue.size() > 0) {
                    String module = moduleQueue.remove(0);
                    String prev = prevQueue.remove(0);
                    int pulse = pulseQueue.remove(0);

                    if (module.equals("rs") && pulse == 1) {
                        if (prevModules.contains(prev)) {
                            numPresses.put(prev,buttonPresses);
                            prevModules.remove(prev);
                        }
                    }

                    if (!typeMap.containsKey(module)) {
                        continue;
                    }

                    String [] dests = destMap.get(module);
                    int type = typeMap.get(module);

                    if (type == 0 && pulse == 0) {
                        int on = onMap.get(module);
                        onMap.put(module, 1 - onMap.get(module));

                        for (String dest : dests) {
                            moduleQueue.add(dest);
                            prevQueue.add(module);
                            pulseQueue.add(1 - on);
                        }
                    }

                    if (type == 1) {
                        Map<String,Integer> conj = conjMap.get(module);
                        conj.put(prev, pulse);

                        conjKeys = new ArrayList<String>(conj.keySet());
                        boolean flag = true;

                        for (int i = 0; i < conjKeys.size(); i++) {
                            if (conj.get(conjKeys.get(i)) == 0) {
                                for (String dest : dests) {
                                    moduleQueue.add(dest);
                                    prevQueue.add(module);
                                    pulseQueue.add(1);
                                    
                                    flag = false;
                                }
                            }

                            if (!flag) {
                                break;
                            }
                        }

                        if (flag) {
                            for (String dest : dests) {
                                moduleQueue.add(dest);
                                prevQueue.add(module);
                                pulseQueue.add(0);
                            }
                        }
                    }
                }

                buttonPresses += 1;
                
                if (prevModules.size() == 0) {
                    break;
                }
            }

            List<Long> lcmNumbers = new ArrayList<Long>();

            for (String key : numPresses.keySet()) {
                lcmNumbers.add(numPresses.get(key));
            }

            System.out.println("Number of button presses is " + lcmOfGroup(lcmNumbers));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}