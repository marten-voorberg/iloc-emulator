package parser;

import instruction.ArithmeticInstruction;
import instruction.Instruction;
import instruction.LoadInstruction;
import program.Program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;

public class Parser {
    private Program program;
    private String fileName;

    public Parser(String fileName) {
        this.program = new Program();
        this.fileName = fileName;
    }

    public void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            do {
                program.addInstruction(parseLine(line));
                line = reader.readLine();
            } while (line != null);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private Instruction parseLine(String line) throws ParseException {
        String[] parts = line.split(" ");

        Instruction result = null;
        result = parseLoadInstruction(parts);
        System.out.println(result);
        return result;
    }

    private Optional<Instruction> parseArithmeticInstruction(String[] parts) throws ParseException {
        String keyword = parts[0];

        int lastIndex = keyword.length() - 1;
        boolean isImmediate = keyword.charAt(lastIndex) == 'I';
        if (isImmediate) {
            keyword = keyword.substring(0, lastIndex);
        }

        ArithmeticInstruction.Operation operation = null;
        for (ArithmeticInstruction.Operation op : ArithmeticInstruction.Operation.values()) {
            String lowerCaseOpWord = op.toString().toLowerCase();
            if (keyword.equals(lowerCaseOpWord)) {
                operation = op;
                break;
            }
        }

        if (operation == null) {
            return Optional.empty();
        }

        if (parts.length != 4) {
            throw new ParseException("Arithmetic instruction does not have 4 parameters.");
        }

        if (parts[1].charAt(0) != 'r') {
            throw new ParseException("First parameter of arithmetic instruction should be a" +
                " register");
        }

        if (parts[3].charAt(0) != 'r') {
            throw new ParseException("First parameter of arithmetic instruction should be a" +
                " register");
        }

        parts[1] = parts[1].substring(1);
        parts[3] = parts[3].substring(1);

        Instruction.SourceType sourceType;
        if (parts[2].charAt(0) == 'r') {
            sourceType = Instruction.SourceType.Register;
            parts[2] = parts[2].substring(1);
        } else {
            sourceType = Instruction.SourceType.Constant;
        }

        int parameter1, parameter2, parameter3;
        try {
            parameter1 = Integer.valueOf(parts[1]);
            parameter2 = Integer.valueOf(parts[2]);
            parameter3 = Integer.valueOf(parts[3]);
        } catch (NumberFormatException e) {
            throw new ParseException("Parameters are not numbers.");
        }

        Instruction result = new ArithmeticInstruction(operation, parameter1, parameter2,
            parameter3, sourceType);
        System.out.println(result);
        return Optional.of(result);
    }

    private Instruction parseLoadInstruction(String[] parts) throws ParseException {
        final String INSTRUCTION_WORD = "load";
        String keyword = parts[0].toLowerCase();

        // Check if the keyword contains at least 4 letters and those match the INSTRUCTION_WORD
        if (keyword.length() < INSTRUCTION_WORD.length() ||
            !keyword.substring(0, 4).toLowerCase().equals(INSTRUCTION_WORD)) {
            return null;
        }

        if (keyword.length() == INSTRUCTION_WORD.length()) {
            if (parts.length != 3) {
                throw new ParseException("Load instruction should have 2 parameters");
            }

            if (parts[1].charAt(0) != 'r' || parts[2].charAt(0) != 'r') {
                throw new ParseException("Load instruction parameters should be registers");
            }
            int source;
            int target;
            try {
                source = Integer.valueOf(parts[1].substring(1));
                target = Integer.valueOf(parts[2].substring(1));
            } catch (NumberFormatException e) {
                throw new ParseException("Parameters are not valid registers");
            }

            return new LoadInstruction(source, target, Instruction.SourceType.Register);
        } else if (keyword.length() == INSTRUCTION_WORD.length() + 1) {
            if (keyword.charAt(INSTRUCTION_WORD.length()) != 'i') {
                throw new ParseException("Expected loadI instruction.");
            }

            if (parts[2].charAt(0) != 'r') {
                throw new ParseException("Load instruction target should be a register");
            }

            int source;
            int target;
            try {
                source = Integer.valueOf(parts[1]);
                target = Integer.valueOf(parts[2].substring(1));
            } catch (NumberFormatException e) {
                throw new ParseException("Parameters are not valid registers");
            }

            return new LoadInstruction(source, target, Instruction.SourceType.Constant);
        }
        return null;
    }

    public Program getProgram() {
        return this.program;
    }

    public static void main(String[] args) {
        Parser parser = new Parser("sample-programs/test.iloc");
        parser.parse();
    }
}
