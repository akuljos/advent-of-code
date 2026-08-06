import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class KeypadConundrum {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java KeypadConundrum [filename]");
            return;
        }
        
        String filename = args[0];

        Map<String, Map<String, String>> directionalKeypad = createDirectionalKeypad();
        Map<String, Map<String, String>> numericalKeypad = createNumericalKeypad();

        long totalComplexity = 0;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine(); 

                String numericalKeypadedLine = "";
                String actualLine = "A" + line;
                for (int i = 0; i < actualLine.length() - 1; i++) {
                    String previous = actualLine.substring(i, i+1);
                    String next = actualLine.substring(i+1, i+2);

                    if (!previous.equals(next)) {
                        numericalKeypadedLine += numericalKeypad.get(previous).get(next);
                    }
                    numericalKeypadedLine += "A";
                }

                String directionalKeypadedLineOne = "";
                numericalKeypadedLine = "A" + numericalKeypadedLine;
                for (int i = 0; i < numericalKeypadedLine.length() - 1; i++) {
                    String previous = numericalKeypadedLine.substring(i, i+1);
                    String next = numericalKeypadedLine.substring(i+1, i+2);
                
                    if (!previous.equals(next)) {
                        directionalKeypadedLineOne += directionalKeypad.get(previous).get(next);
                    }
                    directionalKeypadedLineOne += "A";
                }

                String directionalKeypadedLineTwo = "";
                directionalKeypadedLineOne = "A" + directionalKeypadedLineOne;
                for (int i = 0; i < directionalKeypadedLineOne.length() - 1; i++) {
                    String previous = directionalKeypadedLineOne.substring(i, i+1);
                    String next = directionalKeypadedLineOne.substring(i+1, i+2);
                
                    if (!previous.equals(next)) {
                        directionalKeypadedLineTwo += directionalKeypad.get(previous).get(next);
                    }
                    directionalKeypadedLineTwo += "A";
                }

                totalComplexity += determineKeypadComplexity(line, directionalKeypadedLineTwo);
            }

            s.close();

            System.out.println("Total complexity of keypads is " + totalComplexity);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

    private static long determineKeypadComplexity(String keypadCode, String route) {
        long keypadValue = Long.parseLong(keypadCode.substring(0,3));

        return keypadValue * route.length();
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