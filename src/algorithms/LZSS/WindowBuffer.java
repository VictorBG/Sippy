package algorithms.LZSS;



/**
 * Class WindowBuffer
 */
public class WindowBuffer {

    char[] buffers;
    String input;
    int searchL;
    int searchR;
    int lookAheadL;
    int lookAheadR;
    int length;

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


    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i<buffers.length; i++) {
            result += buffers[i];
        }
        return result;
    }

    public EncodedString continueMatching(int posS) {

        boolean match = true;
        int posLA = lookAheadL;
        EncodedString result = new EncodedString();
        result.setOffset(posLA - posS); //always posLA > posS
        result.setC(buffers[posS]);
        result.setLength(-1);

        while (match && posS <= searchR && posLA <= lookAheadR) {
            match = buffers[posS] == buffers[posLA];
            result.incrementLengthByOne();
            ++posLA;
            ++posS;
        }
        //TODO: EOF in lookAheadBuffer? .hasNext
        if (result.getLength() > 1) {
            result.setC( buffers[lookAheadL+1] ); //if match is big, next symbol
        }
        return result;
    }

    public EncodedString findMatch () {

        EncodedString matchData = new EncodedString();
        matchData.setLength(0);
        //start at the begining of the sliding window
        int i = searchR;
        int j = 0;
        int longestMatchLenght = -1;

        while (i >= searchL) {
            if (buffers[i] == buffers[lookAheadL]) {
                j=1; //match length
                EncodedString es = continueMatching(i);
                if (es.getLength() > longestMatchLenght) {
                    longestMatchLenght = es.getLength();
                    matchData = es;
                }
            }
            i--;
        }
        return matchData;
    }

    public WindowBuffer (int searchBufferSize, int lookAheadBufferSize, String inputString) {
        buffers = new char[searchBufferSize+lookAheadBufferSize];
        input = inputString;
        length = buffers.length;
        searchL = 0;
        searchR = searchBufferSize-1;
        lookAheadL = searchBufferSize;
        lookAheadR = buffers.length-1;
    }

    public boolean lookAheadIsEmpty() {
        return lookAheadR == lookAheadL;
    }

    public void fillLookAheadBuffer() {
        int j = 0;
        for (int i = lookAheadL; i<buffers.length && j<input.length(); i++) {
            buffers[i] = input.charAt(j);
            j++;
            lookAheadR = i;
        }
        input = input.substring(j);
    }

    public void shiftLeft (int positions) {
        int j = positions;
        int i = 0;
        while (j<=buffers.length-1) {
            buffers[i] = buffers[j];
            i++;
            j++;
            lookAheadR--;
        }
        //fill buffers with input[0..positions]
        int k;
        for (k = 0; i < buffers.length && k<input.length(); k++) {
            buffers[i] = input.charAt(k);
            lookAheadR++;
            i++;
        }
        input = input.substring(k);
    }

}
