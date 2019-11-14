package domain.algorithms.lzss;


import java.util.BitSet;

public class FlagHelper {

    private BitSet flags;

    public FlagHelper() {
        flags = new BitSet();

    }

    public void FlagHelper(int numFlags) {
        flags = new BitSet(numFlags);

    }

    public void addFlag(boolean flag) {
        flags.set(flags.length()-1, flag);
    }

    public byte[] toByteArray() {
        return flags.toByteArray();
    }
}
