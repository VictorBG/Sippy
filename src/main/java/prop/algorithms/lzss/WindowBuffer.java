package prop.algorithms.lzss;
import prop.algorithms.lzss.CircularBuffer;

/**
 * Author: Miguel Angel Cabrera
 *
 * Class represents a WindowBuffer for encode porpouses.
 *
 */
public class WindowBuffer {

    CircularBuffer searchBuffer;
    int lookAheadBufferSize;
    int lookAheadL;
    int lookAheadR;
    StringBuilder input;
    int inputPos;



    public char getFirstCharLookAheadBuffer() {
        return input.charAt(lookAheadL);
    }


    @Override
    public String toString() {
//        String result = "";
//        for (int i = 0; i<buffers.length; i++) {
//            result += buffers[i];
//        }
//        return result;
        return "hola";
    }


    public EncodedString findMatch () {

        EncodedString token = new EncodedString();
        String lookAheadBuffer = input.substring(lookAheadL,lookAheadR);
        token = KMP.searchKMP(lookAheadBuffer, searchBuffer.toString());
        int circularBufferOffset = searchBuffer.front;
        int newOffset = (token.getOffset()+circularBufferOffset) % searchBuffer.size();
        token.setOffset((short)newOffset);
        return token;
    }

    public WindowBuffer (short searchBufferSize, short lookAheadBufferSize, StringBuilder inputString) {
        searchBuffer = new CircularBuffer(searchBufferSize);
        this.lookAheadBufferSize = lookAheadBufferSize;
        lookAheadL = 0;
        lookAheadR = lookAheadBufferSize;
        input = inputString;
        inputPos = 0;
    }

    public boolean lookAheadIsEmpty() {
        return lookAheadL >= lookAheadR;
    }

    public void fillLookAheadBuffer() {
        lookAheadR = lookAheadBufferSize;
        if (input.length() < lookAheadR) {
            lookAheadR = input.length();
        }

    }

    public void shiftLeftOne() {
        inputPos++;
        if (inputPos < input.length()) {
            char inputChar = input.charAt(inputPos);
        }
        //shift lookahead
        char lookAheadChar = input.charAt(lookAheadL);
        searchBuffer.enqueue(lookAheadChar);
        //shift searchBuffer
        lookAheadL++;
        lookAheadR++;
        if (lookAheadR > input.length()-1) {
            lookAheadR--;
        }
    }

    public void shiftLeft (int positions) {
        for (int i = 0; i<positions; i++) {
            shiftLeftOne();
        }
    }
}
