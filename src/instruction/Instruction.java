package instruction;

import emulator.Emulator;

public interface Instruction {
    enum SourceType {
        Constant,
        Register
    }
}
