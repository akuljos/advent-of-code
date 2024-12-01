import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Aplenty {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Aplenty [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            Map<String, String []> ruleMap = new HashMap<String, String[]>();

            String line;

            while ((line = s.nextLine()) != "") {
                String [] parts = line.split("[\\{\\}]");
                ruleMap.put(parts[0], parts[1].split(","));
            }

            int sum = 0;

            while (s.hasNextLine()) {
                line = s.nextLine();
                String [] values = line.substring(1,line.length()-1).split(",");

                Map<String,Integer> partValues = new HashMap<String,Integer>();
                for (int i = 0; i < values.length; i++) {
                    partValues.put(values[i].substring(0,1), Integer.parseInt(values[i].substring(2)));
                }

                String ruleName = "in";
                while (!ruleName.equals("A") && !ruleName.equals("R")) {
                    String [] ruleSet = ruleMap.get(ruleName);

                    for (int i = 0; i < ruleSet.length; i++) {
                        if (i == (ruleSet.length - 1)) {
                            ruleName = ruleSet[i];
                            break;
                        }

                        String [] ruleBreakdown = ruleSet[i].split(":");
                        String newRule = ruleBreakdown[1];

                        String ruleCheck = ruleBreakdown[0]; 

                        String partCheck = ruleCheck.substring(0,1); String sign = ruleCheck.substring(1,2);
                        int numCheck = Integer.parseInt(ruleCheck.substring(2));

                        if (sign.equals(">")) {
                            if (partValues.get(partCheck) > numCheck) {
                                ruleName = newRule;
                                break;
                            }
                        } else {
                            if (partValues.get(partCheck) < numCheck) {
                                ruleName = newRule;
                                break;
                            }
                        }
                    }
                }

                if (ruleName.equals("A")) {
                    for (String partName : partValues.keySet()) {
                        sum += partValues.get(partName);
                    }
                }
            }

            System.out.println("Accept sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}