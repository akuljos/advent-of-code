import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class LanParty {

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LanParty [filename]");
            return;
        }
        
        String filename = args[0];
        Map<String, Set<String>> dests = new HashMap<String, Set<String>>();

        Set<Set<String>> triples = new HashSet<Set<String>>();

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

                dests.get(start).add(end);
                dests.get(end).add(start);
            }

            for (String source : dests.keySet()) {
                if (source.charAt(0) != 't') {
                    continue;
                }

                Set<String> sinks = dests.get(source);
                for (String sinkOne : sinks) {
                    for (String sinkTwo : sinks) {
                        if (sinkOne.equals(sinkTwo)) continue;

                        if (dests.get(sinkOne).contains(sinkTwo)) {
                            Set<String> newTriple = new HashSet<String>();
                            newTriple.add(source); newTriple.add(sinkOne); newTriple.add(sinkTwo);

                            triples.add(newTriple);
                        }
                    }
                }
            }

            s.close();

            System.out.println("Number of triples is " + triples.size());
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}