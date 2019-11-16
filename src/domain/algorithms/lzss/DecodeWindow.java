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
        if (len > offset) {
            int a = 0;
        }
        int positions = len;
        int off = offset;

        int index = buffer.length()-off;
        if (index+positions < 0 ) {
            int a = 0;
        }
        buffer.append( buffer.substring(index, index+positions));
    }


}
