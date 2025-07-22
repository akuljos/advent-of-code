import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class ChronospatialComputerV2 {

    static int getComboOperand(int regA, int regB, int regC, int operand) {
        if (operand == 4) {
            return regA;
        }
        if (operand == 5) {
            return regB;
        }
        if (operand == 6) {
            return regC;
        }
        return operand;
    }

    static int chronoSolver(int[] instructions, int currPos, long regA) {
        for (int i = 0; i < 8; i++) {
            long startVal = (regA << 3) | i;

            long regA_ = startVal;
            long regB_ = regA_ % 8;
            regB_ = regB_ ^ 5;
            long regC_ = regA_ / ((long)Math.pow(2, regB_));
            regB_ = regB_ ^ 6;
            regA_ = regA_ / 8;
            regB_ = regB_ ^ regC_;

            if (regB_ % 8 == instructions[currPos] && regA_ == regA) {
                if (currPos == 0) {
                    System.out.println("Valid starting A register is " + startVal);
                    return 0;
                }
                
                if (chronoSolver(instructions, currPos - 1, startVal) == 0) {
                    return 0;
                }
            }
        }

        return -1;
    }

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ChronospatialComputerV2 [filename]");
            return;
        }

        String filename = args[0];

        Pattern programPattern = Pattern.compile("Program: (([0-9]|,)+)");

        String program;
        int[] instructions; 

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String line = s.nextLine();
            line = s.nextLine();
            line = s.nextLine();
            line = s.nextLine();
            line = s.nextLine();
            Matcher programMatcher = programPattern.matcher(line);
            if (programMatcher.find()) {
                program = programMatcher.group(1);
                String[] programArr = program.split(",");
                instructions = new int[programArr.length];

                for (int i = 0; i < programArr.length; i++) {
                    instructions[i] = Integer.parseInt(programArr[i]);
                }
            } else {
                s.close();
                return;
            }

            s.close();
                
            chronoSolver(instructions, instructions.length-1, 0);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}