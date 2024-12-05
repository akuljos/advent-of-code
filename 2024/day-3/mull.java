import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MullItOver {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java MullItOver [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int prod = 0;

            while (s.hasNextLine()) {
                List<Integer> indices = new ArrayList<Integer>();

                String line = s.nextLine();
                for (int i = 0; i < line.length() - 2; i++) {
                    if (line.substring(i, i+3).equals("mul")) {
                        indices.add(i);
                    }
                }
                indices.add(line.length());

                for (int i = 0; i < indices.size()-1; i++) {
                    int idx = indices.get(i);
                    int nextIdx = indices.get(i+1);

                    String statement = line.substring(idx+3, nextIdx);
                    
                    int openIdx = statement.indexOf("(");
                    int closeIdx = statement.indexOf(")");

                    if (openIdx != 0 || closeIdx == -1) {
                        continue;
                    }

                    String[] components = statement.substring(1, closeIdx).split(",");

                    if (components.length != 2) {
                        continue;
                    }

                    try {
                        int numOne = Integer.parseInt(components[0]);
                        int numTwo = Integer.parseInt(components[1]);

                        prod += (numOne * numTwo);
                    } catch (Exception e) {
                        continue;
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