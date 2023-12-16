import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.lang.Character;

class TreeNode {
    int value = 0;
    Map<Character, TreeNode> children = new HashMap<Character, TreeNode>();

    public void insertNum(String repr, int val) {
        TreeNode currNode = this;

        for (char c : repr.toCharArray()) {
            if (currNode.children.containsKey(c)) {
                currNode = currNode.children.get(c);
            } else {
                currNode.children.put(c, new TreeNode());
                currNode = currNode.children.get(c);
            }
        }

        currNode.value = val;
    }

    public int findNum(String repr) {
        
        TreeNode curr = this;

        for (char c : repr.toCharArray()) {
            if (curr.children.containsKey(c)) {
                curr = curr.children.get(c);
            } else {
                return -1;
            }
        }

        return curr.value;
    }

}

class TrebuchetV2 {

    public static TreeNode initNumberMap () {
        TreeNode head = new TreeNode();

        head.insertNum("one", 1);
        head.insertNum("two", 2);
        head.insertNum("three", 3);
        head.insertNum("four", 4);
        head.insertNum("five", 5);
        head.insertNum("six", 6);
        head.insertNum("seven", 7);
        head.insertNum("eight", 8);
        head.insertNum("nine", 9);

        return head;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java TrebuchetV2 [filename]");
            return;
        }

        String filename = args[0];
        TreeNode numberMap = initNumberMap();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            int sum = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine();

                int previousNum = -1;
                String currNumber = "";

                for (char c : line.toCharArray()) {
                    if (Character.isDigit(c)) {
                        int digVal = c - '0';

                        if (previousNum == -1) {
                            sum += (10 * digVal);
                        }

                        previousNum = digVal;
                        currNumber = "";
                    } else {
                        currNumber += c;
                        int digVal = numberMap.findNum(currNumber);

                        while (digVal == -1) {
                            currNumber = currNumber.substring(1, currNumber.length());
                            digVal = numberMap.findNum(currNumber);
                        }

                        if (digVal != 0) {
                            if (previousNum == -1) {
                                sum += (10 * digVal);
                            }

                            previousNum = digVal;
                            currNumber = currNumber.substring(1, currNumber.length());
                        }
                    }
                }

                sum += previousNum;
            }

            System.out.println("Trebuchet sum is " + sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}