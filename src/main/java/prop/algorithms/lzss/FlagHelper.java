package prop.algorithms.lzss;

import java.util.BitSet;

/**
 * Author: Miguel Angel Cabrera
 *
 * Auxiliary class for manage 1 bit flags of Encoded
 * Strings and literals.
 *
 */
public class FlagHelper {

    public BitSet flags;
    private int current_flag;

    public FlagHelper() {
        flags = new BitSet();
        current_flag = -1;

    }

    public FlagHelper(int numFlags, byte[] flagArray) {
        flags = BitSet.valueOf(flagArray);
        current_flag = -1;

    }

    public void addFlag(boolean flag) {
        current_flag++;
        flags.set(current_flag, flag);
    }

    public void addFlagAt(int pos, boolean flag) {
        flags.set(pos, flag);

    }

    public boolean next() {
        ++current_flag;
        boolean a = flags.get(current_flag);
        return a;
    }

    public byte[] toByteArray() {
        return flags.toByteArray();
    }

    public void print() {
        for (int i=0; i<flags.length(); i++) System.out.println(flags.get(i));
    }
}
