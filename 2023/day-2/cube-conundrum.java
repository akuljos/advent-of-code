import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

class CubeConundrum {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CubeConundrum [filename]");
            return;
        }

        int maxRed = 12;
        int maxGreen = 13;
        int maxBlue = 14;

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                boolean flag = true;

                int id = Integer.parseInt(line.split(": ")[0].split(" ")[1]);

                String [] games = line.split(": ")[1].split("; ");
                for (String game : games) {
                    String [] cubes = game.split(", ");

                    for (String cube : cubes) {
                        String [] cubeVals = cube.split(" ");
                        int cubeNum = Integer.parseInt(cubeVals[0]);

                        if (cubeVals[1].equals("red")) {
                            flag = flag && (cubeNum <= maxRed);
                        } else if (cubeVals[1].equals("green")) {
                            flag = flag && (cubeNum <= maxGreen);
                        } else {
                            flag = flag && (cubeNum <= maxBlue);
                        }
                    }
                }

                if (flag) {
                    sum += id;
                }
            }

            System.out.println("Cube conundrum sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


    }

}