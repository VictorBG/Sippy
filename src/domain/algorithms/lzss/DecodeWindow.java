package domain.algorithms.lzss;
/**
 * Author: Miguel Angel Cabrera
 *
 * Enum with the list of the Algorithms availables in the system and its respective id.
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

    public void copyCharsSince(int len, int offset, char c) {
        int positions = len;
        int off = offset;
        //System.out.println(buffer);

        int index = buffer.length()-off;
        buffer.append( buffer.substring(index, index+positions));
        buffer.append(c);
    }


}
