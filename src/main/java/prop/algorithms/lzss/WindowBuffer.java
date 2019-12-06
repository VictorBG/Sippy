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

    public EncodedString continueMatching(short posS) {

        short i = posS; //index for SB
        short j = lookAheadL; //index for LAB

        EncodedString result = new EncodedString();
        result.setOffset((short)(lookAheadL - posS));//independiente del length del match

        while (i <= searchR && j <= lookAheadR && buffers[i] == buffers[j] && result.getLength() < result.getOffset()) {
            result.incrementLengthByOne();
            i++;
            j++;
        }
        if (j>lookAheadR) { //next symbol is in input [abc|ab]c
            //we decided dont continue matching more
            result.decrementLengthByOne();
            j--;
        }
        return result;
    }

    public EncodedString findMatch () {

        EncodedString token = new EncodedString();
        token.setLength((short)0);
        int longestMatch = -1;
        token = searchBuffer.findMatch(token);
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

    }

    public void shiftLeftOne() {
        ++inputPos;
        char inputChar = input.charAt(inputPos);
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
