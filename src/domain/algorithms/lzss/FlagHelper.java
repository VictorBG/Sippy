package domain.algorithms.lzss;

public class FlagHelper {

    private boolean[] flags;
    private int current_flag;

    public void FlagHelper(int numFlags) {
        flags = new boolean[numFlags];
        current_flag = 0;

    }
    public void addFlag(boolean flag) {
        current_flag++;
        flags[current_flag] = flag;
    }

    public boolean next() {
        current_flag++;
        return flags[current_flag];
    }

    public byte[] toByte() {
        byte[] toReturn = new byte[flags.length / 8];
        for (int entry = 0; entry < toReturn.length; entry++) {
            for (int bit = 0; bit < 8; bit++) {
                if (flags[entry * 8 + bit]) {
                    toReturn[entry] |= (128 >> bit);
                }
            }
        }

        return toReturn;
    }
}
