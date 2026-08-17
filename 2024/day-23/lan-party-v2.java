import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class LanPartyV2 {

    private static List<List<String>> cliques = new ArrayList<List<String>>();

    private static void bronKerbosch(Set<String> r, Set<String> p, Set<String> x, Map<String, Set<String>> dests) {
        if (p.size() == 0 && x.size() == 0) {
            if (r.size() > 2) {
                cliques.add(new ArrayList<String>(r));
            }
            return;
        }
        Iterator<String> itr = p.iterator();
        while (itr.hasNext()) {
            String v = itr.next();

            Set<String> r_ = new HashSet<String>(r); r_.add(v);
            Set<String> p_ = new HashSet<String>(p); p_.retainAll(dests.get(v));
            Set<String> x_ = new HashSet<String>(x); x_.retainAll(dests.get(v));
            bronKerbosch(r_, p_, x_, dests);

            itr.remove();
            x.add(v);
        }
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LanPartyV2 [filename]");
            return;
        }
        
        String filename = args[0];
        Map<String, Set<String>> dests = new HashMap<String, Set<String>>();
        Set<String> terminals = new HashSet<String>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] connections = line.split("-");
                String start = connections[0]; String end = connections[1];

                if (!dests.containsKey(start)) {
                    dests.put(start, new HashSet<String>());
                }
                if (!dests.containsKey(end)) {
                    dests.put(end, new HashSet<String>());
                }

                terminals.add(start);
                terminals.add(end);

                dests.get(start).add(end);
                dests.get(end).add(start);
            }

            s.close();

            Set<String> r = new HashSet<String>();
            Set<String> p = new HashSet<String>(terminals);
            Set<String> x = new HashSet<String>();

            bronKerbosch(r, p, x, dests);

            int maxCliqueSize = cliques.get(0).size();
            List<String> maxClique = cliques.get(0);

            for (List<String> clique : cliques) {
                if (clique.size() > maxCliqueSize) {
                    maxCliqueSize = clique.size();
                    maxClique = clique;
                }
            }
            String[] maxCliqueArr = maxClique.toArray(new String[0]);
            Arrays.sort(maxCliqueArr);

            System.out.println("Max clique size is " + maxCliqueSize);
            System.out.println("Ordered clique is " + String.join(",", maxCliqueArr));
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}