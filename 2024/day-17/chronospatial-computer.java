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

class ChronospatialComputer {

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

    public static void main (String [] args) {
        if (args.length != 1) {
            System.out.println("usage: java ChronospatialComputer [filename]");
            return;
        }

        String filename = args[0];

        Pattern regAPattern = Pattern.compile("Register A: ([0-9]+)");
        Pattern regBPattern = Pattern.compile("Register B: ([0-9]+)");
        Pattern regCPattern = Pattern.compile("Register C: ([0-9]+)");
        Pattern programPattern = Pattern.compile("Program: (([0-9]|,)+)");

        int regA = 0; int regB = 0; int regC = 0;
        String program;
        int[] instructions; 
        int instPtr = 0;

        List<String> output = new ArrayList<String>();

        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);

            String line = s.nextLine();
            Matcher regAMatcher = regAPattern.matcher(line);
            if (regAMatcher.find()) {
                regA = Integer.parseInt(regAMatcher.group(1));
            }

            line = s.nextLine();
            Matcher regBMatcher = regBPattern.matcher(line);
            if (regBMatcher.find()) {
                regB = Integer.parseInt(regBMatcher.group(1));
            }

            line = s.nextLine();
            Matcher regCMatcher = regCPattern.matcher(line);
            if (regCMatcher.find()) {
                regC = Integer.parseInt(regCMatcher.group(1));
            }

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
                
            while (instPtr < instructions.length) {
                boolean jumped = false;
                int comboOperand = 0;
                switch(instructions[instPtr]) {
                    case 0:
                        if (instructions[instPtr+1] != 7) {
                            comboOperand = getComboOperand(regA, regB, regC, instructions[instPtr+1]);
                            regA = (regA / (int)Math.pow(2,comboOperand));
                        }
                        break;
                    case 1:
                        regB = regB ^ instructions[instPtr+1];
                        break;
                    case 2:
                        if (instructions[instPtr+1] != 7) {
                            regB = getComboOperand(regA, regB, regC, instructions[instPtr+1]) % 8;
                        }
                        break;
                    case 3:
                        if (regA != 0) {
                            instPtr = instructions[instPtr+1];
                            jumped = true;
                        }
                        break;
                    case 4:
                        regB = regB ^ regC;
                        break;
                    case 5:
                        if (instructions[instPtr+1] != 7) {
                            comboOperand = getComboOperand(regA, regB, regC, instructions[instPtr+1]);
                            output.add(Integer.toString(comboOperand % 8));
                        }
                        break;
                    case 6:
                        if (instructions[instPtr+1] != 7) {
                            comboOperand = getComboOperand(regA, regB, regC, instructions[instPtr+1]);
                            regB = (regA / (int)Math.pow(2,comboOperand));
                        }
                        break; 
                    case 7:
                        if (instructions[instPtr+1] != 7) {
                            comboOperand = getComboOperand(regA, regB, regC, instructions[instPtr+1]);
                            regC = (regA / (int)Math.pow(2,comboOperand));
                        }
                        break;
                    default:
                        break;
                }

                if (!jumped) {
                    instPtr += 2;
                }
            }
            
            System.out.println("Output is " + String.join(",", output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } 
    }

}