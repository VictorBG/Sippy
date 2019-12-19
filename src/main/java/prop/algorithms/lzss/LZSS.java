package prop.algorithms.lzss;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;
import prop.utils.Bytes;

/**
 * @class LZSS
 * @brief Main class of LZSS algorithm
 * Author: Miguel Angel Cabrera
 */
public class LZSS implements BaseAlgorithm {

    private ByteArrayOutputStream baos;

    private static final int MIN_LEN_MATCH = 3;
                                                //max2^4 = 16
    private static final int BUFFER_SIZE_LOOKAHEAD = 16;
                                                //max2^12 = 4096
    private static final int BUFFER_SIZE_SEARCH = 4000;

    private int NUMBER_OF_TOKENS = 0;

    public int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }

    public byte mapOffsetToCodedOffset(byte offset) {
        return (byte)(offset-MIN_LEN_MATCH);
    }

    public byte mapLengthToCodedLength(byte length) {
        return (byte)(length-MIN_LEN_MATCH);
    }

    public int mapCodedOffsetToOffset(int offset) {
        return (offset+MIN_LEN_MATCH);
    }

    public int mapCodedLengthToLength(int length) {
        return (length+MIN_LEN_MATCH);
    }

    private byte[] intToBytes( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    private int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return 0x00 << 24 | 0x00 << 16 | (b[0] & 0xff) << 8 | (b[1] & 0xff);

        return 0;
    }

    private byte getLengthByte3LowBits(byte length) {

        byte result = (byte)(length);
        return result;
    }

    private byte getOffsetByte3456HighBits(byte offset) {

        byte result = (byte)(offset << 3);
        return result;
    }

    private byte codifyOffsetLengthOneByteWithFlag(byte offset, byte length) {
        //resto 3
        offset = mapOffsetToCodedOffset(offset);
        length = mapLengthToCodedLength(length);

        byte o = getOffsetByte3456HighBits(offset);
        byte l = getLengthByte3LowBits(length);

        byte result = (byte)(o | l);
        result = (byte)(result | 0x80); //higher bit flag=1
        return result;
    }

    private int[] decodifyOffsetLengthOneByte(byte b1, byte b2) {
        int offset8HighBits = unsignedByteToInt(b1);
        int offset4LowBits = unsignedByteToInt(b2) >> 4;
        int offset12bits = offset8HighBits<<4 | offset4LowBits;
        int length = unsignedByteToInt(b2) & 0x0F;

        return new int[] {offset12bits, length};
    }

    private byte[] get_offset_length_encoded_12_4_bits(short off, byte len) {

        byte offset8HighBits = (byte) (off >> 4);
        byte offset4LowBits = (byte) (off & 0x0F);
        offset4LowBits = (byte) (offset4LowBits << 4);
        offset4LowBits = (byte) (offset4LowBits | len);
        byte[] result = new byte[2];
        result[0] = (byte) (offset8HighBits & 0xFF);
        result[1] = (byte) (offset4LowBits & 0xFF);
        return result;
    }

    @Override
    public byte[] encode(byte[] input) {
        baos = new ByteArrayOutputStream();
        FlagHelper flags = new FlagHelper();
        StringBuilder inputSB = new StringBuilder(new String(input));
        try {
            WindowBuffer w = new WindowBuffer((short)BUFFER_SIZE_SEARCH,(short)BUFFER_SIZE_LOOKAHEAD,inputSB);
            w.fillLookAheadBuffer();
            while (!w.lookAheadIsEmpty()) {

                EncodedString es = w.findMatch();
                if (es.getLength() >= MIN_LEN_MATCH) {
                    flags.addFlag(true); //flag 1 indicates <length,offset> token
                    short offset = (short)es.getOffset(); //cast negative!!
                    byte length = (byte)es.getLength();
                    byte[] off_len = get_offset_length_encoded_12_4_bits(offset,length);
                    baos.write(off_len[0]);
                    baos.write(off_len[1]);
                    //es.print();

                    w.shiftLeft(es.getLength());
                } else {

                    flags.addFlag(false); //flag 0 indicates literal
                    //only ASCII
                    String symbol = w.getFirstCharLookAheadBuffer()+"";
                    if (symbol == "Í") {
                        int a = 42;
                    }
                    byte[] symb = symbol.getBytes("UTF-8");
                    //byte[] symb =symbol.getBytes();
                    baos.write(symb);
                    //System.out.print(symbol);
                    w.shiftLeft(1);
                }
                NUMBER_OF_TOKENS++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
        }
        flags.addFlag(true); //special flag at last
        byte[] tokensFlags = Bytes.concat(intToBytes(NUMBER_OF_TOKENS), flags.toByteArray());
        byte[] tfAndBytes = Bytes.concat(tokensFlags, baos.toByteArray());
        return tfAndBytes;
    }

    @Override
    public byte[] decode(byte[] input) {
        baos = new ByteArrayOutputStream();
        byte[] aInt = new byte[4];
        aInt[0] = input[0];
        aInt[1] = input[1];
        aInt[2] = input[2];
        aInt[3] = input[3];
        int numFlags = byteArrayToInt(aInt);
        if (numFlags < 0) numFlags += 256;
        NUMBER_OF_TOKENS = numFlags;
        numFlags = numFlags+1; //special bit at last
        byte[] flagArray;
        if (numFlags%8 != 0) flagArray = new byte[(numFlags/8)+1];
        else flagArray = new byte[numFlags/8];

        for (int i = 0; i < flagArray.length; i++) {
            flagArray[i] = input[i + 4];
        }
        FlagHelper flags = new FlagHelper(NUMBER_OF_TOKENS, flagArray);
        StringBuilder empty_string = new StringBuilder();
        try {
            DecodeWindow dw = new DecodeWindow(BUFFER_SIZE_SEARCH);
            try {
                int i = 4+flagArray.length;
                while (i < input.length) {
                    if (flags.next() == false) { //literal
                        byte byte_read = input[i];
                        if (unsignedByteToInt(byte_read) < 128) { //ascii literal
                            //no flag byte, directly char
                            dw.addChar((char)byte_read);
                        }
                        else {
                            i++;
                            byte byte2 = input[i];
                            byte[] utfBytes = {byte_read, byte2};
                            //String utf = new String(utfBytes);
                            String utf = new String(utfBytes, StandardCharsets.UTF_8);
                            dw.addChar(utf.charAt(0));
                        }
                    }
                    else {
                        //b was offset_length
                        //byte o = input[i];
                        byte off = input[i];
                        //off = unsignedByteToInt(input[i]);
                        i++;
                        //int len = input[i];
                        //len = unsignedByteToInt(input[i]);
                        //OFF_LEN encoded with 12|4
                        int[] off_len = decodifyOffsetLengthOneByte(off,input[i]);
                        int length = off_len[1];
                        int offset = off_len[0];
                        dw.copyCharsSince(length,offset);
                    }
                    i++;
                }
            }
            finally {
                //BOM UTF-8 ??
                baos.write(dw.getBuffer().toString().getBytes(StandardCharsets.UTF_8));
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Override
    public byte[] readFile(File file) throws IOException {
        BufferedReader bufRdr = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        return bufRdr.lines().map(i -> i + "\n").reduce(String::concat).get().getBytes();
    }

    @Override
    public Algorithm getAlgorithmUsed() {
        return Algorithm.LZSS;
    }
}
