package instruction;

import emulator.Emulator;

public interface Instruction {
    void execute(Emulator emulator);

    enum SourceType {
        Constant,
        Register
    }
}
