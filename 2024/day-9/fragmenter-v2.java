import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;

class FragmenterV2 {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java FragmenterV2 [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String numString = s.nextLine();

            s.close();

            Map<Long, PriorityQueue<Long>> numZeroIndices = new HashMap<Long, PriorityQueue<Long>>();
            for (long i = 0; i < 10; i++) {
                numZeroIndices.put(i, new PriorityQueue<Long>());
            }

            Map<Long, Long> posIndices = new HashMap<Long, Long>();
            List<List<Long>> toSlot = new ArrayList<List<Long>>(); // (id, currIndex, length)

            Map<Long, Long> idLengths = new HashMap<Long, Long>();

            long currentIndex = 0;
            long nextId = 0;

            for (int idx = 0; idx < numString.length(); idx++) {
                long length = Long.parseLong(numString.substring(idx, idx + 1));
                if (!(idx % 2 == 0)) {
                    numZeroIndices.get(length).add(currentIndex);
                } else {
                    List<Long> toAdd = new ArrayList<Long>();
                    toAdd.add(nextId); nextId += 1;
                    toAdd.add(currentIndex); toAdd.add(length);
                    toSlot.add(0, toAdd);
                }
                currentIndex += length;
            }

            for (List<Long> slot : toSlot) {
                long id = slot.get(0); long currIndex = slot.get(1); long length = slot.get(2);
                
                long minIdx = Long.MAX_VALUE;
                long minNumZeros = -1;
                for (long len = length; len < 10; len++) {
                    if (!numZeroIndices.get(len).isEmpty()) {
                        if (numZeroIndices.get(len).peek() < minIdx) {
                            minIdx = numZeroIndices.get(len).peek();
                            minNumZeros = len;
                        }
                    }
                }

                if (minNumZeros != -1 && minIdx < currIndex) {
                    posIndices.put(id, minIdx);
                    numZeroIndices.get(minNumZeros).poll();
                    numZeroIndices.get(minNumZeros - length).offer(minIdx + length); 
                } else {
                    posIndices.put(id, currIndex);
                }

                idLengths.put(id, length);
            }

            long fragmenterSum = 0;
            for (Long id : posIndices.keySet()) {
                long idx = posIndices.get(id);
                long length = idLengths.get(id);

                fragmenterSum += ((id * idx * length) + (id * length * (length - 1) / 2));
            }
            System.out.println("Fragmenter sum is " + fragmenterSum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}