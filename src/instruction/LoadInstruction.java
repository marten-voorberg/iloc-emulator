package instruction;

public class LoadInstruction implements Instruction {
    private int source1;
    private SourceType sourceType1;
    private int source2;
    private SourceType sourceType2;
    private int targetRegister;

    public LoadInstruction(int sourceRegister, int targetRegister, SourceType sourceType) {
        this.source1 = sourceRegister;
        this.sourceType1 = sourceType;
        this.targetRegister = targetRegister;
    }

    public LoadInstruction(int source1, SourceType sourceType1, int source2, SourceType sourceType2,
                           int targetRegister) {
        this.source1 = source1;
        this.sourceType1 = sourceType1;
        this.source2 = source2;
        this.sourceType2 = sourceType2;
        this.targetRegister = targetRegister;
    }

    public int getSource1() {
        return source1;
    }

    public SourceType getSourceType1() {
        return sourceType1;
    }

    public int getSource2() {
        return source2;
    }

    public SourceType getSourceType2() {
        return sourceType2;
    }

    public int getTargetRegister() {
        return targetRegister;
    }

    @Override
    public String toString() {
        if (sourceType2 == null) {
            StringBuilder sb = new StringBuilder();
            if (sourceType1 == SourceType.Register) {
                sb.append('r');
            }
            return String.format("Load %s%s => r%s", sb, source1, targetRegister);
        } else {
            StringBuilder sb = new StringBuilder();
            if (sourceType2 == SourceType.Register) {
                sb.append('r');
            }
            return String.format("Load %s%s %s %s => r%s", sb, source1, source2, sourceType2,
                targetRegister);
        }
    }
}
