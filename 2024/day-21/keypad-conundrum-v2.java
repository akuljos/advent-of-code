import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyPair;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class KeypadLevel {
    int level;
    String start;
    String end;

    List<String> directionalPads = Arrays.asList(new String[]{"A", "<", "^", ">", "v"});

    KeypadLevel(int level, String start, String end) {
        this.level = level;
        this.start = start;
        this.end = end;
    }

    @Override
    public int hashCode() {
        return (this.level + "," + this.start + "," + this.end).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        KeypadLevel other = (KeypadLevel)object;
        return (this.level == other.level &&
                this.start.equals(other.start) &&
                this.end.equals(other.end));
    }
}

class KeypadConundrumV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java KeypadConundrumV2 [filename]");
            return;
        }
        
        String filename = args[0];

        Map<String, Map<String, String>> directionalKeypad = createDirectionalKeypad();
        Map<String, Map<String, String>> numericalKeypad = createNumericalKeypad();
        Map<KeypadLevel, Long> keypadCache = new HashMap<KeypadLevel, Long>();

        String[] directionalPads = new String[]{"A", "<", "^", ">", "v"};

        long totalComplexity = 0;
        int NUMBER_KEYPADS = 25;

        for (String padOne : directionalPads) {
            for (String padTwo : directionalPads) {
                long score = 0;
                if (!padOne.equals(padTwo)) {
                    score = directionalKeypad.get(padOne).get(padTwo).length();
                }
                keypadCache.put(new KeypadLevel(0, padOne, padTwo), score + 1);
            }
        }

        for (int lvl = 1; lvl < NUMBER_KEYPADS; lvl++) {
            for (String padOne : directionalPads) {
                for (String padTwo : directionalPads) {
                    long score = 0;
                    if (padOne.equals(padTwo)) {
                        score = 1;
                    } else {
                        String dependency = "A" + directionalKeypad.get(padOne).get(padTwo) + "A";
                        for (int k = 0; k < dependency.length() - 1; k++) {
                            KeypadLevel dependencyLevel = new KeypadLevel(lvl-1, dependency.substring(k, k + 1), dependency.substring(k + 1, k + 2));
                            score += keypadCache.get(dependencyLevel);
                        }
                    }
                    keypadCache.put(new KeypadLevel(lvl, padOne, padTwo), score);
                }
            }
        }

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine(); 

                String actualLine = "A" + line;

                long totalLength = 0;
                for (int i = 0; i < actualLine.length() - 1; i++) {
                    String dependency = "A" + numericalKeypad.get(actualLine.substring(i, i + 1)).get(actualLine.substring(i + 1, i + 2)) + "A";
                    for (int j = 0; j < dependency.length() - 1; j++) {
                        totalLength += keypadCache.get(new KeypadLevel(NUMBER_KEYPADS-1, dependency.substring(j, j + 1), dependency.substring(j + 1, j + 2)));
                    }
                }
                totalComplexity += determineKeypadComplexity(line, totalLength);
            }

            s.close();

            System.out.println("Total complexity of keypads is " + totalComplexity);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

    private static long determineKeypadComplexity(String keypadCode, long routeLength) {
        long keypadValue = Long.parseLong(keypadCode.substring(0,3));

        return keypadValue * routeLength;
    }

    private static Map<String, Map<String, String>> createNumericalKeypad() {
        Map<String, Map<String, String>> numericalKeypad = new HashMap<String, Map<String, String>>();

        Map<String, String> sevenPaths = new HashMap<String, String>() {{
            put("8", ">");
            put("9", ">>");
            put("4", "v");
            put("5", "v>");
            put("6", "v>>");
            put("1", "vv");
            put("2", "vv>");
            put("3", "vv>>");
            put("0", ">vvv");
            put("A", ">>vvv");
        }};
        Map<String, String> eightPaths = new HashMap<String, String>() {{
            put("7", "<");
            put("9", ">");
            put("4", "<v");
            put("5", "v");
            put("6", "v>");
            put("1", "<vv");
            put("2", "vv");
            put("3", "vv>");
            put("0", "vvv");
            put("A", "vvv>");
        }};
        Map<String, String> ninePaths = new HashMap<String, String>() {{
            put("7", "<<");
            put("8", "<");
            put("4", "<<v");
            put("5", "<v");
            put("6", "v");
            put("1", "<<vv");
            put("2", "<vv");
            put("3", "vv");
            put("0", "<vvv");
            put("A", "vvv");
        }};
        Map<String, String> fourPaths = new HashMap<String, String>() {{
            put("7", "^");
            put("8", "^>");
            put("9", "^>>");
            put("5", ">");
            put("6", ">>");
            put("1", "v");
            put("2", "v>");
            put("3", "v>>");
            put("0", ">vv");
            put("A", ">>vv");
        }};
        Map<String, String> fivePaths = new HashMap<String, String>() {{
            put("7", "<^");
            put("8", "^");
            put("9", "^>");
            put("4", "<");
            put("6", ">");
            put("1", "<v");
            put("2", "v");
            put("3", "v>");
            put("0", "vv");
            put("A", "vv>");
        }};
        Map<String, String> sixPaths = new HashMap<String, String>() {{
            put("7", "<<^");
            put("8", "<^");
            put("9", "^");
            put("4", "<<");
            put("5", "<");
            put("1", "<<v");
            put("2", "<v");
            put("3", "v");
            put("0", "<vv");
            put("A", "vv");
        }};
        Map<String, String> onePaths = new HashMap<String, String>() {{
            put("7", "^^");
            put("8", "^^>");
            put("9", "^^>>");
            put("4", "^");
            put("5", "^>");
            put("6", "^>>");
            put("2", ">");
            put("3", ">>");
            put("0", ">v");
            put("A", ">>v");
        }};
        Map<String, String> twoPaths = new HashMap<String, String>() {{
            put("7", "<^^");
            put("8", "^^");
            put("9", "^^>");
            put("4", "<^");
            put("5", "^");
            put("6", "^>");
            put("1", "<");
            put("3", ">");
            put("0", "v");
            put("A", "v>");
        }};
        Map<String, String> threePaths = new HashMap<String, String>() {{
            put("7", "<<^^");
            put("8", "<^^");
            put("9", "^^");
            put("4", "<<^");
            put("5", "<^");
            put("6", "^");
            put("1", "<<");
            put("2", "<");
            put("0", "<v");
            put("A", "v");
        }};
        Map<String, String> zeroPaths = new HashMap<String, String>() {{
            put("7", "^^^<");
            put("8", "^^^");
            put("9", "^^^>");
            put("4", "^^<");
            put("5", "^^");
            put("6", "^^>");
            put("1", "^<");
            put("2", "^");
            put("3", "^>");
            put("A", ">");
        }};
        Map<String, String> aPaths = new HashMap<String, String>() {{
            put("7", "^^^<<");
            put("8", "<^^^");
            put("9", "^^^");
            put("4", "^^<<");
            put("5", "<^^");
            put("6", "^^");
            put("1", "^<<");
            put("2", "<^");
            put("3", "^");
            put("0", "<");
        }};
        

        numericalKeypad.put("7", sevenPaths);
        numericalKeypad.put("8", eightPaths);
        numericalKeypad.put("9", ninePaths);
        numericalKeypad.put("4", fourPaths);
        numericalKeypad.put("5", fivePaths);
        numericalKeypad.put("6", sixPaths);
        numericalKeypad.put("1", onePaths);
        numericalKeypad.put("2", twoPaths);
        numericalKeypad.put("3", threePaths);
        numericalKeypad.put("0", zeroPaths);
        numericalKeypad.put("A", aPaths);
        return numericalKeypad;
    }

    private static Map<String, Map<String, String>> createDirectionalKeypad() {
        Map<String, Map<String, String>> directionalKeypad = new HashMap<String, Map<String, String>>();
        
        Map<String, String> upPaths = new HashMap<String, String>() {{
            put("A", ">");
            put("<", "v<");
            put("v", "v");
            put(">", "v>");
        }};
        Map<String,String> aPaths = new HashMap<String, String>() {{
            put("^", "<");
            put("<", "v<<");
            put("v", "<v");
            put(">", "v");
        }};
        Map<String,String> leftPaths = new HashMap<String, String>() {{
            put("^", ">^");
            put("A", ">>^");
            put("v", ">");
            put(">", ">>");
        }};
        Map<String,String> downPaths = new HashMap<String, String>() {{
            put("^", "^");
            put("A", "^>");
            put("<", "<");
            put(">", ">");
        }};
        Map<String,String> rightPaths = new HashMap<String, String>() {{
            put("^", "<^");
            put("A", "^");
            put("<", "<<");
            put("v", "<");
        }};

        directionalKeypad.put("^", upPaths);
        directionalKeypad.put("A", aPaths);
        directionalKeypad.put("<", leftPaths);
        directionalKeypad.put("v", downPaths);
        directionalKeypad.put(">", rightPaths);
        return directionalKeypad;
    }

}