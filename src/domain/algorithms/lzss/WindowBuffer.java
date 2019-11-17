package domain.algorithms.lzss;

/**
 * Author: Miguel Angel Cabrera
 *
 * Class represents a WindowBuffer for encode porpouses.
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
        if (result.length > result.offset) {
            int a = 0;
        }
        return result;
    }

    public EncodedString findMatch () {

        EncodedString matchData = new EncodedString();
        matchData.setLength((short)0);
        if (searchIsEmpty()) {
            return matchData;
        }
        String buffer = String.valueOf(buffers);
        if (lookAheadR > buffer.length()) {
            int a = 0;
        }

        String LAB = buffer.substring(lookAheadL,lookAheadR);
        String SB = buffer.substring(searchL,searchR);

        String pattern = getFirstCharLookAheadBuffer()+"";
        if (pattern.equals("m")) {
            int a = 0;
        }
        int indexOf = 0; //first offset, no match
        int j = 0; //index for LAB
        while(LAB.length() > j && SB.indexOf(LAB.substring(0,j)) >= indexOf) {
            int indexOfNew = SB.indexOf(LAB.substring(0,j));
            if (indexOfNew>indexOf) indexOf = indexOfNew;
            j++;
        }

        if (j>0) {
            matchData.setOffset((short)(SB.indexOf(LAB.substring(0,j-1)) + 1 ));
            matchData.setLength((short)(j-1));
        }
        if (matchData.getLength() <= 0) matchData.setOffset((short)0);

        return matchData;

        /*
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

         */
    }

    public WindowBuffer (short searchBufferSize, short lookAheadBufferSize, StringBuilder inputString) {
        buffers = new char[searchBufferSize+lookAheadBufferSize];
        input = inputString;
        length = (short)buffers.length;
        searchL = searchBufferSize;
        searchR = (short)(searchBufferSize-1);
        lookAheadL = (short)(buffers.length-1);
        lookAheadR = (short)(buffers.length-1);
    }

    public boolean lookAheadIsEmpty() {
        return lookAheadR < lookAheadL;
    }
    public boolean searchIsEmpty() {
        return searchR < searchL;
    }

    public void fillLookAheadBuffer() {
        int originalLookAheadR = lookAheadR;
        for (int i = searchR+1; i<=originalLookAheadR; i++) {
            shiftLeftOne();
        }
    }

    public void shiftLeftOne() {

        //shift search
        for (int i = searchL+1; i<=searchR; i++) {
            buffers[i-1] = buffers[i];
        }
        if (lookAheadL == searchR+1 && searchL > 0) --searchL;

        //shift lookAhead
        for (int i = lookAheadL; i<=lookAheadR; i++) {
            buffers[i-1] = buffers[i];
        }
        if (lookAheadL > searchR+1) --lookAheadL;

        //shift input
        if (input.length() != 0) {
            //shift by 1 input to buffers
            buffers[buffers.length-1] = input.charAt(0);
            //shift by 1 input
            StringBuilder inputString = new StringBuilder(input.substring(1));
            input = new StringBuilder(inputString);
        }
        else {
            if (lookAheadR >= lookAheadL) {
                buffers[lookAheadR] = '-';
                --lookAheadR;

            }
        }













        /*
        //shift searchBuffer
        if (lookAheadL == searchL && searchL > 0) {
            --searchL;
        }
        for (int i = searchL+1; i<=searchR; i++) {
            buffers[i-1] = buffers[i];
        }
        //first time lookAhead is empty
        if (lookAheadL == buffers.length) lookAheadL--;
        //shift lookahead
        for (int i = lookAheadL; i<=lookAheadR; i++) {
            buffers[i-1] = buffers[i];
        }

        if (input.length() != 0) {
            //shift by 1 input to buffers
            buffers[buffers.length-1] = input.charAt(0);
            if (lookAheadL> searchR+1) --lookAheadL;
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

         */
    }

    public void shiftLeft (int positions) {
        for (int i = 0; i<positions; i++) {
            shiftLeftOne();
        }
    }
}
