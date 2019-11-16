package domain.algorithms.lzss;

/**
 * Author: Miguel Angel Cabrera
 *
 * Class representing the a WindowBuffer for decoding porpouses.
 *
 */
public class WindowBuffer {

    char[] buffers;
    StringBuilder input;
    short searchL;
    short searchR;
    short lookAheadL;
    short lookAheadR;
    short length;

    public int getSearchL() {
        return searchL;
    }

    public int getSearchR() {
        return searchR;
    }

    public int getLookAheadL() {
        return lookAheadL;
    }

    public int getLookAheadR() {
        return lookAheadR;
    }

    public int length () {
        return buffers.length;
    }


    public char getFirstCharLookAheadBuffer() {
        return buffers[lookAheadL];
    }


    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i<buffers.length; i++) {
            result += buffers[i];
        }
        return result;
    }

    public EncodedString continueMatching(short posS) {

        short i = posS; //index for SB
        short j = lookAheadL; //index for LAB

        EncodedString result = new EncodedString();
        result.setOffset((short)(lookAheadL - posS));//independiente del length del match

        while (i <= searchR && j <= lookAheadR && buffers[i] == buffers[j]) {
            result.incrementLengthByOne();
            i++;
            j++;
        }
        if (j>lookAheadR) { //next symbol is in input [abc|ab]c
            //we decided dont continue matching more
            result.decrementLengthByOne();
            j--;
        }
        if (result.length > result.offset) {
            int a = 0;
        }
        return result;
    }

    public EncodedString findMatch () {

        EncodedString matchData = new EncodedString();
        matchData.setLength((short)0);
        int longestMatch = -1;

        for (short i = searchR; i>=searchL; i--) {
            if (buffers[i] == buffers[lookAheadL]) { //char match!
                EncodedString es = continueMatching(i);
                if ( es.getLength() > longestMatch ) {
                    longestMatch = es.getLength();
                    matchData = es;
                }
            }
        }
        return matchData;
    }

    public WindowBuffer (short searchBufferSize, short lookAheadBufferSize, StringBuilder inputString) {
        buffers = new char[searchBufferSize+lookAheadBufferSize];
        input = inputString;
        length = (short)buffers.length;
        searchL = 0;
        searchR = (short)(searchBufferSize-1);
        lookAheadL = searchBufferSize;
        lookAheadR = (short)(buffers.length-1);
    }

    public boolean lookAheadIsEmpty() {
        return lookAheadR < lookAheadL;
    }

    public void fillLookAheadBuffer() {
        int originalLookAheadR = lookAheadR;
        for (int i = lookAheadL; i<=originalLookAheadR; i++) {
            shiftLeftOne();
        }
    }

    public void shiftLeftOne() {
        //shift searchBuffer
        for (int i = searchL+1; i<=searchR; i++) {
            buffers[i-1] = buffers[i];
        }

        //shift lookahead
        for (int i = lookAheadL; i<=lookAheadR; i++) {
            buffers[i-1] = buffers[i];
        }

        if (input.length() != 0) {
            //shift by 1 input to buffers
            buffers[buffers.length-1] = input.charAt(0);
            //shift by 1 input
            StringBuilder inputString = new StringBuilder(input.substring(1));
            input = new StringBuilder(inputString);
        }
        else {//input is empty -> '<EOF>'
            if (lookAheadL <= lookAheadR) { //LAbuffer is partially filled
                lookAheadR--;
            }
            //LAbuffer is empty but searchB id partially filled
            else if (lookAheadL == lookAheadR && searchL < searchR){
                searchR--;
            }

        }
    }

    public void shiftLeft (int positions) {

        for (int i = 0; i<positions; i++) {
            shiftLeftOne();
        }
    }
}
