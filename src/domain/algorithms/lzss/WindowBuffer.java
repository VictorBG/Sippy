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

        // abr'a'|abra
        if (i == searchR) {
            EncodedString result = new EncodedString();
            result.setC(buffers[lookAheadL+1]);
            result.setLength((short)1);
            result.setOffset((short)1);
            return result;
        }
        boolean match = true;

        EncodedString result = new EncodedString();
        result.setOffset((short)(lookAheadL - posS));//independiente del length del match

        while (i <= searchR && j <= lookAheadR && buffers[i] == buffers[j]) {
            result.incrementLengthByOne();
            //result.setC(buffers[j]);
            i++;
            j++;
        }
        if (j>lookAheadR) { //next symbol is in input [abc|ab]c
            //we decided dont continue matching more
            result.decrementLengthByOne();
            j--;
        }
        result.setC(buffers[j]);
        return result;

        /*
        boolean match = true;
        int posLA = lookAheadL;
        EncodedString result = new EncodedString();
        result.setOffset(posLA - posS); //always posLA > posS
        result.setC(buffers[posLA]);
        result.setLength(1); //miminum 1 because que actually matched
        ++posLA; //we have at minimum 1 pos more in searchBuffer searchBuffer[length-2]
        ++posS;
        match = buffers[posS] == buffers[posLA];
        while (match && posS <= searchR && posLA <= lookAheadR) {
            result.incrementLengthByOne();
            ++posLA;
            ++posS;
            match = buffers[posS] == buffers[posLA];
        }
        //TODO: EOF in lookAheadBuffer? .hasNext
        if (result.getLength() >= 1) {
            result.setC( buffers[lookAheadL+result.length] ); //if match, next symbol
        }
        return result;

         */
    }

    public EncodedString findMatch () {

        EncodedString matchData = new EncodedString();
        matchData.setLength((short)0);
        matchData.setC(getFirstCharLookAheadBuffer());
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



        /*
        EncodedString matchData = new EncodedString();
        matchData.setLength(0);
        matchData.setC(getFirstCharLookAheadBuffer());
        //start at the begining of the sliding window
        int i = searchL;
        int j = 0;
        int longestMatchLenght = -1;

        while (i <= searchR) {
            if (buffers[i] == buffers[lookAheadL] && i < buffers[searchR]) {
                j=1; //match length
                EncodedString es = continueMatching(i);
                if (es.getLength() > longestMatchLenght) {
                    longestMatchLenght = es.getLength();
                    matchData = es;
                }
            }
            i++;
        }

        return matchData;

         */
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
        System.out.println("----");
        for (int i = lookAheadL; i<=lookAheadR; i++) {
            shiftLeftOne();
            //System.out.println(i);
        }
        //System.out.println("----");

        /*
        int j = 0;
        for (int i = lookAheadL; i<buffers.length && j<input.length(); i++) {
            buffers[i] = input.charAt(j);
            j++;
            lookAheadR = i;
        }
        input = input.substring(j);
         */
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
            String inputString = input.substring(1);
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


        /*
        int j = positions;
        int i = 0;
        while (j<=buffers.length-1) {
            buffers[i] = buffers[j];
            i++;
            j++;
            //lookAheadR--;
            //first iterations searchbuffer is partially empty
            //if (searchL > 0) searchL--;
        }
        //fill buffers with input[0..positions]
        int k;
        for (k = 0; i < buffers.length && k<positions && input.charAt(k) != '.'; k++) {
            buffers[i] = input.charAt(k);
            //shift input by 1

            //lookAheadR++;
            i++;
        }
        input = input.substring(k);
        if (input.equalsIgnoreCase(".")) {
            input = "*";//next iter end of program

        }
        else if (input.equalsIgnoreCase("*")) {
            for (i = lookAheadL; i<buffers.length; i++) {
                buffers[i] = '.';
            }
        }
         */
    }



}
