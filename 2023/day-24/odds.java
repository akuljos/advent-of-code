import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Odds {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Odds [filename]");
            return;
        }

        String filename = args[0];

        long MIN_BOUND = 200000000000000L;
        long MAX_BOUND = 400000000000000L;

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Double>> positions = new ArrayList<List<Double>>();
            List<List<Double>> velocities = new ArrayList<List<Double>>();

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(" @ ");

                List<Double> position = new ArrayList<Double>();
                for (String st : line[0].split(", ")) {
                    position.add(Double.parseDouble(st));
                }
                positions.add(position);

                List<Double> velocity = new ArrayList<Double>();
                for (String st : line[1].split(", ")) {
                    velocity.add(Double.parseDouble(st));
                }
                velocities.add(velocity);
            }

            int sum = 0;

            for (int i = 0; i < positions.size(); i++) {
                for (int j = i+1; j < positions.size(); j++) {

                    List<Double> hailA_pos = positions.get(i); List<Double> hailA_vel = velocities.get(i);
                    List<Double> hailB_pos = positions.get(j); List<Double> hailB_vel = velocities.get(j);

                    if ((hailB_vel.get(0) / hailA_vel.get(0)) == (hailB_vel.get(1) / hailA_vel.get(1))) {
                        continue;
                    }

                    double a = hailB_pos.get(1) - hailA_pos.get(1) + hailB_vel.get(1) * (hailA_pos.get(0) - hailB_pos.get(0)) / hailB_vel.get(0);
                    a /= (hailA_vel.get(1) - hailB_vel.get(1) * hailA_vel.get(0) / hailB_vel.get(0));

                    double b = hailA_pos.get(1) - hailB_pos.get(1) + hailA_vel.get(1) * (hailB_pos.get(0) - hailA_pos.get(0)) / hailA_vel.get(0);
                    b /= (hailB_vel.get(1) - hailA_vel.get(1) * hailB_vel.get(0) / hailA_vel.get(0));                    

                    if (a <= 0 || b <= 0) {
                        continue;
                    }

                    double x = hailA_pos.get(0) + hailA_vel.get(0) * a;
                    double y = hailA_pos.get(1) + hailA_vel.get(1) * a;

                    if (x >= MIN_BOUND && y >= MIN_BOUND && x <= MAX_BOUND && y <= MAX_BOUND) {
                        sum += 1;
                    }
                }
            }

            System.out.println("Number intersecting is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}