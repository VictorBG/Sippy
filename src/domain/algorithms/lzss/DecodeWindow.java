package domain.algorithms.lzss;
/**
 * Author: Miguel Angel Cabrera
 *
 * This class represents an Decode window buffer.
 *
 */
public class DecodeWindow {

    StringBuffer buffer;

    public DecodeWindow(int searchBufferLength) {

        buffer = new StringBuffer("");
    }

    public void addChar(char c) {
        buffer.append(c);
    }

    public void print() {
        System.out.println(buffer);
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public void copyCharsSince(int len, int offset) {
        int positions = len;
        int off = offset;

        int index = buffer.length()-off;
        buffer.append( buffer.substring(index, index+positions));
    }


}
