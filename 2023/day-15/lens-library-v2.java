import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class LensLibraryV2 {

    static int getLabelValue(String label) {
        int currVal = 0;

        for (char point : label.toCharArray()) {
            currVal += (int)point;
            currVal *= 17;
            currVal %= 256;
        }

        return currVal;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LensLibraryV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String [] initializers = s.nextLine().split(",");

            Map<String,Integer> focalMap = new HashMap<String,Integer>();
            Map<String,Integer> boxMap = new HashMap<String,Integer>();
            Map<Integer,List<String>> boxListMap = new HashMap<Integer,List<String>>();

            for (String init : initializers) {
                if (init.charAt(init.length()-1) == '-') {
                    String labelStr = init.substring(0,init.length()-1);
                    int label = getLabelValue(labelStr);

                    if (focalMap.containsKey(labelStr)) {
                        int box = boxMap.get(labelStr);

                        focalMap.remove(labelStr);
                        boxMap.remove(labelStr);

                        List<String> boxList = boxListMap.get(box);
                        for (int i = 0; i < boxList.size(); i++) {
                            if (boxList.get(i).equals(labelStr)) {
                                boxList.remove(i);
                                break;
                            }
                        }

                        if (boxList.size() == 0) {
                            boxListMap.remove(box);
                        } else {
                            boxListMap.put(box, boxList);
                        }
                    }
                } else {
                    String [] initSplit = init.split("=");
                    int label = getLabelValue(initSplit[0]);

                    focalMap.put(initSplit[0], Integer.parseInt(initSplit[1]));

                    if (!boxMap.containsKey(initSplit[0])) {
                        if (!boxListMap.containsKey(label)) {
                            boxListMap.put(label, new ArrayList<String>());
                        }
                        boxListMap.get(label).add(initSplit[0]);
                    }

                    boxMap.put(initSplit[0], label);
                }
            }

            int sum = 0;

            for (int box : boxListMap.keySet()) {
                List<String> boxList = boxListMap.get(box);

                for (int idx = 0; idx < boxList.size(); idx++) {
                    int focalLength = focalMap.get(boxList.get(idx));

                    sum += ((box + 1) * (idx + 1) * focalLength);
                }
            }

            System.out.println("Lens sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}