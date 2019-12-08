package prop.algorithms.lzss;
import prop.algorithms.lzss.CircularBuffer;
/**
 * Author: Miguel Angel Cabrera
 *
 * This class represents an Decode window buffer.
 *
 */
public class DecodeWindow {

    CircularBuffer buffer;
    StringBuffer result;

    public DecodeWindow(int searchBufferLength) {

        buffer = new CircularBuffer(searchBufferLength);
        result = new StringBuffer();
    }

    public void addChar(char c) {
        result.append(c);
        buffer.enqueue(c);
    }

    public void print() {
        System.out.println(result);
    }

    public StringBuffer getBuffer() {
        return result;
    }

    public void copyCharsSince(int len, int offset) {
        int positions = len;
        int off = offset;
        String repetition = buffer.toString(offset, len);
        //fill decode buffer too
        for (int i = 0; i<len; i++) {
            buffer.enqueue(repetition.charAt(i));
        }

        result.append(repetition);
    }


}
