import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

class HistorianHysteria {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java HistorianHysteria [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            PriorityQueue<Integer> groundTruth = new PriorityQueue<Integer>();
            PriorityQueue<Integer> found = new PriorityQueue<Integer>();

            while (s.hasNextLine()) {
                String line = s.nextLine();

                String [] numbers = line.split("   ");
                groundTruth.add(Integer.parseInt(numbers[0]));
                found.add(Integer.parseInt(numbers[1]));
            }

            int numElements = groundTruth.size();
            int sum = 0;

            for (int i = 0; i < numElements; i++) {
                sum += Math.abs(groundTruth.poll() - found.poll());
            }

            s.close();

            System.out.println("Historian hysteria sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}