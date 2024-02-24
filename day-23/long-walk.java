import java.io.File;
import java.io.FileNotFoundException;

import java.lang.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LongWalk {

    static List<List<Character>> extractTable (Scanner s) {
        List<List<Character>> table = new ArrayList<List<Character>>();

        while (s.hasNextLine()) {
            char [] line = s.nextLine().toCharArray();
            List<Character> row = new ArrayList<Character>();

            for (char c : line) {
                row.add(c);
            }

            table.add(row);
        }

        return table;
    }

    static List<List<Integer>> buildVisited (int numRows, int numCols) {
        List<List<Integer>> visited = new ArrayList<List<Integer>>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<Integer>();

            for (int j = 0; j < numCols; j++) {
                row.add(0);
            }

            visited.add(row);
        }

        return visited;
    }

    static int maximumPath (List<List<Character>> table, List<List<Integer>> visited, int row, int col, int distance) {

        if (row < 0 || col < 0 || row >= table.size() || col >= table.get(0).size() || table.get(row).get(col) == '#' || visited.get(row).get(col) == 1) {
            return -1;
        }

        if (row == (table.size() - 1)) {
            return distance;
        }

        int maxLength = -1;
        visited.get(row).set(col,1);

        switch (table.get(row).get(col)) {
            case '>':
                maxLength = Math.max(maxLength, maximumPath(table, visited, row, col + 1, distance + 1));
                break;
            case '<':
                maxLength = Math.max(maxLength, maximumPath(table, visited, row, col - 1, distance + 1));
                break;
            case 'v':
                maxLength = Math.max(maxLength, maximumPath(table, visited, row + 1, col, distance + 1));
                break;
            case '^':
                maxLength = Math.max(maxLength, maximumPath(table, visited, row - 1, col, distance + 1));
                break;
            default:
                maxLength = Math.max(maxLength, maximumPath(table, visited, row, col + 1, distance + 1));
                maxLength = Math.max(maxLength, maximumPath(table, visited, row, col - 1, distance + 1));
                maxLength = Math.max(maxLength, maximumPath(table, visited, row + 1, col, distance + 1));
                maxLength = Math.max(maxLength, maximumPath(table, visited, row - 1, col, distance + 1));
        }

        visited.get(row).set(col,0);
        return maxLength;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java LongWalk [filename]");
            return;
        }

        String filename = args[0];

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            List<List<Character>> table = extractTable(s);
            List<List<Integer>> visited = buildVisited(table.size(), table.get(0).size());

            int maxPath = maximumPath(table, visited, 0, 1, 0);
            System.out.println("Max path length is " + maxPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}