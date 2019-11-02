package algorithms.LZSS;

/**
 * Class representing an encoded string
 *
 */
public class EncodedString {

    int offset; //offset to start the longest match
    int length; //length of the longest match
    char c; //letter

    public EncodedString() {
        offset = 0;
        length = 0;
        c = '_';
    }

    public void print() {
        System.out.printf("%d%d%c", offset, length, c );
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public char getC() {
        return c;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setC(char c) {
        this.c = c;
    }

    public void incrementLengthByOne() {
        length++;
    }
}
