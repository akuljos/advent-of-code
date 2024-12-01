import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class AplentyV2 {

    static boolean isAccept (String s) {
        return s.equals("A");
    }

    static boolean isReject (String s) {
        return s.equals("R");
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java AplentyV2 [filename]");
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

            long sum = 0;

            List<List<Long>> valuesQueue = new ArrayList<List<Long>>();
            valuesQueue.add(Arrays.asList(new Long[] { (long)1, (long)4000, (long)1, (long)4000, (long)1, (long)4000, (long)1, (long)4000 }));

            List<Integer> stepQueue = new ArrayList<Integer>();
            stepQueue.add(0);

            List<String> ruleQueue = new ArrayList<String>();
            ruleQueue.add("in");

            while (valuesQueue.size() > 0) {
                List<Long> values = valuesQueue.remove(0);

                long xMin = values.get(0); long xMax = values.get(1); 
                long mMin = values.get(2); long mMax = values.get(3);
                long aMin = values.get(4); long aMax = values.get(5); 
                long sMin = values.get(6); long sMax = values.get(7);

                int step = stepQueue.remove(0);

                String rule = ruleQueue.remove(0);
                String [] conditions = ruleMap.get(rule);

                if (step == (conditions.length - 1)) {
                    if (isAccept(conditions[step])) {
                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                        continue;
                    }

                    if (!isReject(conditions[step])) {
                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                        stepQueue.add(0);
                        ruleQueue.add(conditions[step]);
                    }
                } else {
                    String [] condition = conditions[step].split(":");
                    String comp = condition[0]; String nextRule = condition[1];

                    String part = comp.substring(0,1);
                    String sign = comp.substring(1,2);
                    long check = Long.parseLong(comp.substring(2));

                    switch (part) {
                        case "x":
                            if (sign.equals(">")) {
                                if (check >= xMax) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check < xMin) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, check, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - check) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { check+1, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            } else {
                                if (check <= xMin) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check > xMax) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { check, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((check - xMin) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, check-1, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            }
                            break;

                        case "m":
                            if (sign.equals(">")) {
                                if (check >= mMax) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check < mMin) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, check, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - check) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, check+1, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            } else {
                                if (check <= mMin) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check > mMax) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, check, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (check - mMin) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, check-1, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            }
                            break;

                        case "a":
                            if (sign.equals(">")) {
                                if (check >= aMax) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check < aMin) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, check, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - check) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, check+1, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            } else {
                                if (check <= aMin) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check > aMax) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, check, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (check - aMin) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, check-1, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            }
                            break;

                        case "s":
                            if (sign.equals(">")) {
                                if (check >= sMax) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check < sMin) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, check }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - check));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, check+1, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            } else {
                                if (check <= sMin) {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);
                                } else if (check > sMax) {
                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                } else {
                                    valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, check, sMax }));
                                    stepQueue.add(step + 1);
                                    ruleQueue.add(rule);

                                    if (isAccept(nextRule)) {
                                        sum += ((xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (check - sMin));
                                        continue;
                                    }

                                    if (!isReject(nextRule)) {
                                        valuesQueue.add(Arrays.asList(new Long[] { xMin, xMax, mMin, mMax, aMin, aMax, sMin, check-1 }));
                                        stepQueue.add(0);
                                        ruleQueue.add(nextRule);
                                    }
                                }
                            }
                            break;

                        default:
                            break;
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