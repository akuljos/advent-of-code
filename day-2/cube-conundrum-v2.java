import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CubeConundrumV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java CubeConundrum [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                int rgbArr[] = new int[]{0, 0, 0};

                int id = Integer.parseInt(line.split(": ")[0].split(" ")[1]);

                String [] games = line.split(": ")[1].split("; ");
                for (String game : games) {
                    String [] cubes = game.split(", ");

                    for (String cube : cubes) {
                        String [] cubeVals = cube.split(" ");
                        int cubeNum = Integer.parseInt(cubeVals[0]);

                        if (cubeVals[1].equals("red")) {
                            rgbArr[0] = Math.max(rgbArr[0], cubeNum);
                        } else if (cubeVals[1].equals("green")) {
                            rgbArr[1] = Math.max(rgbArr[1], cubeNum);
                        } else {
                            rgbArr[2] = Math.max(rgbArr[2], cubeNum);
                        }
                    }
                }

                sum += (rgbArr[0] * rgbArr[1] * rgbArr[2]);
            }

            System.out.println("Cube conundrum sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


    }

}