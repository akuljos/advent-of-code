import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Propagation {

    static List<Integer> numberPulses (Map<String,String[]> destMap, Map<String,Integer> typeMap, Map<String,Integer> onMap, Map<String,Map<String,Integer>> conjMap) {
        int lowPulses = 1;
        int highPulses = 0;

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

            lowPulses += (1 - pulse);
            highPulses += pulse;

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

                List<String> conjKeys = new ArrayList<String>(conj.keySet());
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

        return Arrays.asList(new Integer[] { lowPulses, highPulses });
    }


    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Propagation [filename]");
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

            int lowPulses = 0;
            int highPulses = 0;

            for (int i = 1; i <= 1000; i++) {
                List<Integer> pulseArray = numberPulses(destMap, typeMap, onMap, conjMap);

                lowPulses += pulseArray.get(0);
                highPulses += pulseArray.get(1);
            }

            System.out.println("Pulse count is " + (lowPulses * highPulses));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}