import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

class GearChar {

    char value;
    int asteriskNum;


    public GearChar (char value, int asteriskNum) {
        this.value = value;
        this.asteriskNum = asteriskNum;
    }

    public char getValue () {
        return value;
    }

    public void setValue (char value) {
        this.value = value;
    }

    public int getAsteriskNum () {
        return asteriskNum;
    }

    public void setAsteriskNum (int asteriskNum) {
        this.asteriskNum = asteriskNum;
    }

    public boolean isDigit () {
        return Character.isDigit(value);
    }

}

class GearRatiosV2 {

    static List<ArrayList<GearChar>> gearboard = new ArrayList<ArrayList<GearChar>>();
    static List<ArrayList<Integer>> gearboardTracker = new ArrayList<ArrayList<Integer>>();

    static int numRows = 0;
    static int numCols = 0;

    private static void dfs (int x, int y, int asteriskNum) {

        if ((x < 0) || (x >= numRows) || (y < 0) 
            || (y >= numCols) || (!gearboard.get(x).get(y).isDigit()) || (gearboardTracker.get(x).get(y) == 1)) {
                return;
        }

        gearboardTracker.get(x).set(y, 1);
        gearboard.get(x).get(y).setAsteriskNum(asteriskNum);
        
        dfs(x,y-1, asteriskNum);
        dfs(x,y+1, asteriskNum);
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java GearRatiosV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int asteriskNum = 0;

            while (s.hasNextLine()) {
                ArrayList<GearChar> gearboardRow = new ArrayList<GearChar>();
                ArrayList<Integer> gearboardTrackerRow = new ArrayList<Integer>();

                for (char c : s.nextLine().toCharArray()) {
                    
                    if (c == '*') {
                        gearboardRow.add(new GearChar(c, asteriskNum));
                        asteriskNum += 1;
                    } else {
                        gearboardRow.add(new GearChar(c, -1));
                    }
                    
                    gearboardTrackerRow.add(0);
                }

                numCols = gearboardRow.size();
                numRows += 1;

                gearboard.add(gearboardRow);
                gearboardTracker.add(gearboardTrackerRow);           
            }

            for (int x = 0; x < numRows; x++) {
                for (int y = 0; y < numCols; y++) {
                    GearChar currGC = gearboard.get(x).get(y);
                    char currGCVal = currGC.getValue();
                    int currGCAstNum = currGC.getAsteriskNum();

                    if (currGCVal == '*') {
                        dfs(x-1, y-1, currGCAstNum);
                        dfs(x-1, y, currGCAstNum);
                        dfs(x-1, y+1, currGCAstNum);
                        dfs(x, y-1, currGCAstNum);
                        dfs(x, y+1, currGCAstNum);
                        dfs(x+1, y-1, currGCAstNum);
                        dfs(x+1, y, currGCAstNum);
                        dfs(x+1, y+1, currGCAstNum);
                    }
                }
            }

            Map<Integer, ArrayList<Integer>> asteriskMap = new HashMap<Integer, ArrayList<Integer>>();

            for (int x = 0; x < numRows; x++) {
                int currentVal = 0;
                int currAstNum = -1;

                for (int y = 0; y < numCols; y++) {
                    GearChar currGC = gearboard.get(x).get(y);
                    int currTracker = gearboardTracker.get(x).get(y);

                    if (currTracker == 1) {
                        currentVal = 10 * currentVal + (currGC.getValue() - '0');
                        currAstNum = currGC.getAsteriskNum();
                    } else {
                        if (!asteriskMap.containsKey(currAstNum)) {
                            asteriskMap.put(currAstNum, new ArrayList<Integer>());
                        }

                        asteriskMap.get(currAstNum).add(currentVal);

                        currentVal = 0;
                        currAstNum = -1;
                    }
                }

                if (currAstNum != -1) {
                    if (!asteriskMap.containsKey(currAstNum)) {
                        asteriskMap.put(currAstNum, new ArrayList<Integer>());
                    }

                    asteriskMap.get(currAstNum).add(currentVal);

                    currentVal = 0;
                    currAstNum = -1;
                }
            }

            int sum = 0;
            for (int astNum : asteriskMap.keySet()) {
                List<Integer> astValues = asteriskMap.get(astNum);

                if (astValues.size() == 2) {
                    sum += (astValues.get(0) * astValues.get(1));
                }
            }

            System.out.println("The gear ratio sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }
}