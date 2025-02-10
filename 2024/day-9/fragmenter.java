import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Fragmenter {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Fragmenter [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String numString = s.nextLine();

            s.close();

            List<String> realString = new ArrayList<String>();
            int id = 0;

            for (int i = 0; i < numString.length(); i++) {
                for (int j = 0; j < Integer.parseInt(numString.substring(i, i+1), 10); j++) {
                    realString.add(Integer.toString(id));
                }
                id += 1;
                if (i != (numString.length() - 1)) {
                    i += 1;
                    for (int j = 0; j < Integer.parseInt(numString.substring(i, i+1), 10); j++) {
                        realString.add(".");
                    }
                }
            }

            int startPtr = 0;
            int endPtr = realString.size() - 1;

            while (startPtr < endPtr) {
                if (realString.get(startPtr).equals(".")) {
                    String tmp = realString.get(startPtr);
                    realString.set(startPtr, realString.get(endPtr));
                    realString.set(endPtr, tmp);
           
                    while (realString.get(endPtr).equals(".")) {
                        endPtr -= 1;
                    }
                }
                startPtr += 1;
            }

            long sum = 0;
            for (int i = 0; i < realString.size(); i++) {
                if (realString.get(i).equals(".")) {
                    break;
                }
                sum += ((long)i * Long.parseLong(realString.get(i),10));
            }

            System.out.println("Fragmenter sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}