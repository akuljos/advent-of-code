// two 2D arrays (one for tracking blocks and one for tracking height)
// one set for blocks that cannot be disintegrated

// for each block in height order, insert block into blocks array and update
// nondisintegrate set and height based on maximum height of updated positions and
// corresponding blocks

// if max height is shared by multiple blocks, no fall; if max height only one block, that block
// cannot be disintegrated

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class SandSlabs {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java SandSlabs [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            Comparator<List<Integer>> heightComp = new Comparator<List<Integer>>() {
                public int compare(List<Integer> t1, List<Integer> t2) {
                    return t1.get(5).compareTo(t2.get(5));
                }
            };

            int xMax = -1;
            int yMax = -1;
            List<List<Integer>> blockQueue = new ArrayList<List<Integer>>();

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split("~");
                List<Integer> currentBlock = new ArrayList<Integer>();

                for (String pos : line[0].split(",")) {
                    currentBlock.add(Integer.parseInt(pos));
                }

                for (String pos : line[1].split(",")) {
                    currentBlock.add(Integer.parseInt(pos));
                }

                blockQueue.add(currentBlock);
                
                xMax = Math.max(xMax, currentBlock.get(3));
                yMax = Math.max(yMax, currentBlock.get(4));
            }

            blockQueue.sort(heightComp);

            List<List<Integer>> blockArray = new ArrayList<List<Integer>>();
            List<List<Integer>> heightArray = new ArrayList<List<Integer>>();

            for (int i = 0; i < xMax + 1; i++) {
                List<Integer> blockRow = new ArrayList<Integer>();
                List<Integer> heightRow = new ArrayList<Integer>();
                
                for (int j = 0; j < yMax + 1; j++) {
                    blockRow.add(-1);
                    heightRow.add(0);
                }

                blockArray.add(blockRow);
                heightArray.add(heightRow);
            }

            Set<Integer> nonDis = new HashSet<Integer>();

            for (int idx = 0; idx < blockQueue.size(); idx++) {

                List<Integer> block = blockQueue.get(idx);
                int startX = block.get(0), startY = block.get(1), startZ = block.get(2), endX = block.get(3), endY = block.get(4), endZ = block.get(5);

                Map<Integer, Set<Integer>> heightMap = new HashMap<Integer, Set<Integer>>();

                if (startX != endX) {
                    for (int i = startX; i < endX + 1; i++) {
                        int currHeight = heightArray.get(i).get(startY);
                        int currBlock = blockArray.get(i).get(startY);

                        if (!heightMap.containsKey(currHeight)) {
                            heightMap.put(currHeight, new HashSet<Integer>());
                        }

                        heightMap.get(currHeight).add(currBlock);
                    }
                } else {
                    for (int i = startY; i < endY + 1; i++) {
                        int currHeight = heightArray.get(startX).get(i);
                        int currBlock = blockArray.get(startX).get(i);

                        if (!heightMap.containsKey(currHeight)) {
                            heightMap.put(currHeight, new HashSet<Integer>());
                        }

                        heightMap.get(currHeight).add(currBlock);
                    }
                }

                int maxHeight = Collections.max(heightMap.keySet());
                Set<Integer> maxHeightBlocks = heightMap.get(maxHeight);

                if (maxHeightBlocks.size() == 1) {
                    nonDis.add((int)maxHeightBlocks.toArray()[0]);
                }

                if (startZ != endZ) {
                    heightArray.get(startX).set(startY, maxHeight + endZ - startZ + 1);
                    blockArray.get(startX).set(startY, idx);
                    continue;
                }

                if (startX != endX) {
                    for (int i = startX; i < endX + 1; i++) {
                        heightArray.get(i).set(startY, maxHeight+1);
                        blockArray.get(i).set(startY, idx);
                    }
                } else {
                    for (int i = startY; i < endY + 1; i++) {
                        heightArray.get(startX).set(i, maxHeight+1);
                        blockArray.get(startX).set(i, idx);
                    }
                }
            }

            nonDis.remove(-1);
            System.out.println("Num can be disintegrated is " + (blockQueue.size() - nonDis.size()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }
}