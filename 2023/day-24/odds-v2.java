// idea: build a huge system of 3 * {num hailstones} equations where it's rock_pos + rock_vel * t_x = hailstone_pos + hailstone_vel * t_x
// for every hailstone in all dimensions


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class OddsV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java OddsV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            FileWriter posVelWriter = new FileWriter("matrix_coeffs.txt");

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" @ ");
                String posVel = "";

                for (String mvmt : line) {
                    for (String st : mvmt.split(", ")) {
                        posVel += Double.parseDouble(st) + ",";
                    }
                }

                posVelWriter.write(posVel.substring(0,posVel.length() - 1) + "\n");
            }

            posVelWriter.close();

        } catch (IOException e) {
            System.out.println("File not found!");
            return;
        }
    }
}