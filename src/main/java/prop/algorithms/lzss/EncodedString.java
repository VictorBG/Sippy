package prop.algorithms.lzss;
/**
 * Author: Miguel Angel Cabrera
 *
 * Class represents an encoded token
 *
 */
public class EncodedString {

    short offset; //offset to start the longest match
    short length; //length of the longest match

    public EncodedString() {
        offset = 0;
        length = 0;
    }

    public void print() {
        System.out.printf("<%d,%d>", offset, length);
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public void setOffset(short offset) {
        this.offset = offset;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public void incrementLengthByOne() {
        length++;
    }

    public void decrementLengthByOne() {
        length--;
    }
}
