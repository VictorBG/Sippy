package prop.algorithms.lzss;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Miguel Angel Cabrera
 *
 * Class represents a WindowBuffer for encode porpouses.
 *
 */
public class WindowBuffer {

    byte[] input;
    HashMap<Byte, ArrayList<Integer>> searchBuffer;
    int searchL;
    int searchR;
    int lookAheadL;
    int lookAheadR;
    int length;
    int inputPos;

    public Byte getFirstCharLookAheadBuffer() {
        return input[lookAheadL];
    }


    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i<input.length; i++) {
            result += input[i];
        }
        return result;
    }

    public EncodedString continueMatching(int posS) {

        int i = posS; //index for SB
        int j = lookAheadL; //index for LAB

        EncodedString result = new EncodedString();
        result.setOffset((short)(posS));//independiente del length del match
        //while (i <= searchR && j <= lookAheadR && input[i] == input[j] && result.getLength() < result.getOffset()) {
        while ( i < input.length && j < input.length && input[i] == input[j] && result.getLength() < result.getOffset()) {
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
        Byte b = getFirstCharLookAheadBuffer();
        if (searchBuffer.containsKey(b)) {
            ArrayList<Integer> listOfPositions = searchBuffer.get(b);
            for (int i = 0; i< listOfPositions.size(); ++i) {
                EncodedString tok = continueMatching(listOfPositions.get(i));
                if (tok.getLength() > token.getLength()) {
                    token = tok;
                }
            }
        }

        return token;
    }

    public WindowBuffer (short searchBufferSize, short lookAheadBufferSize, byte[] input) {
        this.input = input;
        searchBuffer = new HashMap<Byte, ArrayList<Integer>>();
        length = (short)input.length;
        searchL = 0;
        searchR = 0;
        lookAheadL = 0;
        if (lookAheadBufferSize > input.length) {
            lookAheadR = input.length-1;
            inputPos = lookAheadR;
        }
        else {
            lookAheadR = lookAheadBufferSize-1;
            inputPos = lookAheadR+1;
        }


    }

    public boolean lookAheadIsEmpty() {
        return lookAheadR < lookAheadL;
    }

    public void addByteToDictionary(Byte b, int pos) {
        if (searchBuffer.containsKey(b)) {
            searchBuffer.get(b).add(pos);
        }
        else {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(pos);
            searchBuffer.put(b,list);
        }
    }

    public void shiftLeftOne() {
        inputPos++;
        if (lookAheadL < input.length-1) addByteToDictionary(input[lookAheadL], lookAheadL);
        //shift lookahead
        //searchL++;
        //searchR++;
        //shift searchBuffer
        lookAheadL++;
        lookAheadR++;
        if (lookAheadR > input.length-1) {
            lookAheadR--;
        }

    }

    public void shiftLeft (int positions) {
        for (int i = 0; i<positions; i++) {
            shiftLeftOne();
        }
    }
}
