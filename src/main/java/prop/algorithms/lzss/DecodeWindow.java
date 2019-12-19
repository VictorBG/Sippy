package prop.algorithms.lzss;
/**
 * Author: Miguel Angel Cabrera
 *
 * This class represents an Decode window buffer.
 *
 */
public class DecodeWindow {

    StringBuilder buffer;

    public DecodeWindow(int searchBufferLength) {

        buffer = new StringBuilder("");
    }

    public void addChar(char c) {
        buffer.append(c);
    }

    public void print() {
        System.out.println(buffer);
    }

    public StringBuilder getBuffer() {
        return buffer;
    }

    public void copyCharsSince(int len, int offset) {
        int positions = len;
        int off = offset;

        int index = offset;
        buffer.append( buffer.substring(index, index+positions));
    }


}
