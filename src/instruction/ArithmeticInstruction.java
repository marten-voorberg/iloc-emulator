package instruction;

import emulator.Emulator;

public class ArithmeticInstruction implements Instruction {
    public enum Operation {
        Add,
        Sub,
        Mult,
        LShift,
        RShift,
        Or,
        And,
        Xor
    }

    private Operation operation;
    private int targetRegister;
    private int source1Register;
    private int source2;
    private SourceType source2Type;

    public ArithmeticInstruction(Operation operation, int targetRegister, int source1Register,
                                 int source2, SourceType source2Type) {
        this.operation = operation;
        this.targetRegister = targetRegister;
        this.source1Register = source1Register;
        this.source2 = source2;
        this.source2Type = source2Type;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getTargetRegister() {
        return targetRegister;
    }

    public int getSource1Register() {
        return source1Register;
    }

    public int getSource2() {
        return source2;
    }

    public SourceType getSource2Type() {
        return source2Type;
    }

    @Override
    public String toString() {
        StringBuilder secondRegister = new StringBuilder();
        if (source2Type == SourceType.Register) {
            secondRegister.append('r');
        }
        return String.format("%s r%s %s%s r%s", operation, source1Register, secondRegister,
            source2, targetRegister);
    }
}
