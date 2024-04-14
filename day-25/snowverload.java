import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

class Snowverload {
    static private class Node {

        int dist;
        String val;
        
        public Node(int dist, String val) {
            this.dist = dist;
            this.val = val;
        }

    }

    static int [] minCutPhase (Map<String, Map<String,Integer>> edgeMap, int numNodes) {

        Comparator<Node> edgeComp = new Comparator<Node>() {
            public int compare(Node t1, Node t2) {
                return - Integer.compare(t1.dist, t2.dist);
            }
        };

        Map<String,Node> edgeVals = new HashMap<String,Node>();
        for (String key : edgeMap.keySet()) {
            edgeVals.put(key, new Node(0, key));
        }

        PriorityQueue<Node> edgeQueue = new PriorityQueue<Node>(edgeComp);
        for (String key : edgeVals.keySet()) {
            edgeQueue.add(edgeVals.get(key));
        }

        Set<String> visited = new HashSet<String>();
        String lastSrc = ""; String lastDest = ""; int lastDist = 0;

        while (visited.size() != edgeMap.keySet().size()) {
            Node node = edgeQueue.poll();
            visited.add(node.val);
            lastSrc = lastDest; lastDest = node.val; lastDist = node.dist;

            for (String dest: edgeMap.get(node.val).keySet()) {
                if (!visited.contains(dest)) {
                    edgeQueue.remove(edgeVals.get(dest));
                    edgeVals.get(dest).dist += edgeMap.get(node.val).get(dest);
                    edgeQueue.add(edgeVals.get(dest));
                }
            }
        }

        String nodeName = lastSrc + "," + lastDest;

        Map<String, Integer> nodeMap = new HashMap<String, Integer>();

        Map<String, Integer> srcMap = edgeMap.get(lastSrc);
        Map<String, Integer> destMap = edgeMap.get(lastDest);

        Set<String> srcKeys = new HashSet<String>(srcMap.keySet());
        Set<String> destKeys = new HashSet<String>(destMap.keySet());

        for (String node : srcKeys) {
            int newDist = srcMap.get(node) + (destMap.containsKey(node) ? destMap.get(node) : 0);
            nodeMap.put(node, newDist);
            edgeMap.get(node).put(nodeName, newDist);
        }

        for (String node : destKeys) {
            int newDist = destMap.get(node) + (srcMap.containsKey(node) ? srcMap.get(node) : 0);
            nodeMap.put(node, newDist);
            edgeMap.get(node).put(nodeName, newDist);
        }

        edgeMap.remove(lastSrc);
        edgeMap.remove(lastDest);

        edgeMap.put(nodeName,nodeMap);

        for (String node : edgeMap.keySet()) {
            edgeMap.get(node).remove(lastSrc);
            edgeMap.get(node).remove(lastDest);
        }

        int numInCut = lastDest.split(",").length;
        int nodeProduct = numInCut * (numNodes - numInCut);

        return new int[] { nodeProduct, lastDist };
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java Snowverload [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            Map<String, Map<String,Integer>> edgeMap = new HashMap<String, Map<String,Integer>>();

            while (s.hasNextLine()) {
                String [] line = s.nextLine().split(": ");
                String src = line[0]; String [] dests = line[1].split(" ");

                if (!edgeMap.containsKey(src)) { edgeMap.put(src, new HashMap<String,Integer>()); }

                for (String dest : dests) {
                    edgeMap.get(src).put(dest, 1);

                    if (!edgeMap.containsKey(dest)) { edgeMap.put(dest, new HashMap<String,Integer>()); }
                    edgeMap.get(dest).put(src, 1);
                }
            }

            int numNodes = edgeMap.keySet().size();

            int minDist = (int)Math.pow(2.0,32.0) - 1;
            int size = 0;

            for (int i = 0; i < numNodes - 2; i++) {
                int [] phaseRet = minCutPhase(edgeMap, numNodes);

                if (phaseRet[1] < minDist) {
                    minDist = phaseRet[1];
                    size = phaseRet[0];
                }
            }

            System.out.println("Product of component sizes is " + size);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


    }
}