package domain.algorithms.lzss;

/**
 * Class representing an encoded token
 *
 */
public class EncodedString {

    short offset; //offset to start the longest match
    short length; //length of the longest match
    char c; //letter

    public EncodedString() {
        offset = 0;
        length = 0;
        c = '_';
    }

    public void print() {
        System.out.printf("<%d%d%c> ", offset, length, c );
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

    public void setOffset(short offset) {
        this.offset = offset;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public void setC(char c) {
        this.c = c;
    }

    public void incrementLengthByOne() {
        length++;
    }

    public void decrementLengthByOne() {
        length--;
    }
}
