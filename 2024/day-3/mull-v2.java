import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MullItOverV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java MullItOverV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int prod = 0;
            boolean doMul = true;

            while (s.hasNextLine()) {
                List<Integer> mulIndices = new ArrayList<Integer>();
                List<Integer> doIndices = new ArrayList<Integer>();
                List<Integer> dontIndices = new ArrayList<Integer>();

                String line = s.nextLine();
                for (int i = 0; i < line.length() - 2; i++) {
                    if (line.substring(i, i+3).equals("mul")) {
                        mulIndices.add(i);
                    }
                }
                mulIndices.add(line.length());

                for (int i = 0; i < line.length() - 3; i++) {
                    if (line.substring(i, i+4).equals("do()")) {
                        doIndices.add(i);
                    }
                }
                
                for (int i = 0; i < line.length() - 6; i++) {
                    if (line.substring(i, i+7).equals("don't()")) {
                        dontIndices.add(i);
                    }
                }

                int mulIter = 0;
                int doIter = 0;
                int dontIter = 0;

                while (mulIter < (mulIndices.size()-1)) {
                    int mulIdx = mulIndices.get(mulIter);
                    int doIdx = (doIter < doIndices.size()) ? doIndices.get(doIter) : line.length();
                    int dontIdx = (dontIter < dontIndices.size()) ? dontIndices.get(dontIter) : line.length();

                    if (doIdx < mulIdx && doIdx < dontIdx) {
                        doMul = true;
                        doIter += 1;
                    } else if (dontIdx < mulIdx && dontIdx < doIdx) {
                        doMul = false;
                        dontIter += 1;
                    } else {
                        if (doMul) {
                            int nextMulIdx = mulIndices.get(mulIter+1);
                            String statement = line.substring(mulIdx+3, nextMulIdx);
                        
                            int openIdx = statement.indexOf("(");
                            int closeIdx = statement.indexOf(")");

                            if (openIdx != 0 || closeIdx == -1) {
                                mulIter += 1;
                                continue;
                            }

                            String[] components = statement.substring(1, closeIdx).split(",");

                            if (components.length != 2) {
                                mulIter += 1;
                                continue;
                            }

                            try {
                                int numOne = Integer.parseInt(components[0]);
                                int numTwo = Integer.parseInt(components[1]);

                                prod += (numOne * numTwo);
                            } catch (Exception e) {
                                mulIter += 1;
                                continue;
                            }               
                        }

                        mulIter += 1;
                    }
                }
            }

            s.close();

            System.out.println("Mull it over count is " + prod);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}