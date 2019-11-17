package domain.algorithms.lzss;

import domain.algorithms.base.BaseAlgorithm;
import utils.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Author: Miguel Angel Cabrera
 *
 * Main class of LZSS algorithm
 *
 */
public class Lzss implements BaseAlgorithm {

    public static ByteArrayOutputStream baos;

    public static final int MIN_LEN_MATCH = 3;
                                                //max2^8 = 255
    public static final int BUFFER_SIZE_LOOKAHEAD = 100;
                                                //max2^8 = 255
    public static final int BUFFER_SIZE_SEARCH = 255;

    public static int NUMBER_OF_TOKENS = 0;

    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }

    public static byte mapOffsetToCodedOffset(byte offset) {
        return (byte)(offset-MIN_LEN_MATCH);
    }

    public static byte mapLengthToCodedLength(byte length) {
        return (byte)(length-MIN_LEN_MATCH);
    }

    public static int mapCodedOffsetToOffset(int offset) {
        return (offset+MIN_LEN_MATCH);
    }

    public static int mapCodedLengthToLenght(int length) {
        return (length+MIN_LEN_MATCH);
    }

    private byte[] intToBytes( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    public static int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return 0x00 << 24 | 0x00 << 16 | (b[0] & 0xff) << 8 | (b[1] & 0xff);

        return 0;
    }

    public static byte getLengthByte3LowBits(byte length) {

        byte result = (byte)(length);
        return result;
    }

    public static byte getOffsetByte3456HighBits(byte offset) {

        byte result = (byte)(offset << 3);
        return result;
    }

    public static byte codifyOffsetLengthOneByteWithFlag(byte offset, byte length) {
        //resto 3
        offset = mapOffsetToCodedOffset(offset);
        length = mapLengthToCodedLength(length);

        byte o = getOffsetByte3456HighBits(offset);
        byte l = getLengthByte3LowBits(length);

        byte result = (byte)(o | l);
        result = (byte)(result | 0x80); //higher bit flag=1
        return result;
    }

    public static int[] decodifyOffsetLengthOneByte(byte off_len) {
        int off_len_int = unsignedByteToInt(off_len);
        int length = off_len_int & 0x07;
        off_len_int = off_len_int >> 3;
        //off_len_int = unsignedByteToInt(off_len);
        int offset = off_len_int & 0x0F;

        //sumo 3
        offset = mapCodedOffsetToOffset(offset);
        length = mapCodedLengthToLenght(length);

        return new int[] {offset, length};
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
                //es.print();
                if (es.getLength() >= MIN_LEN_MATCH) {
                    flags.addFlag(true); //flag 1 indicates <length,offset> token
                    byte offset = (byte)es.getOffset(); //cast negative!!
                    byte length = (byte)es.getLength();
                    if (unsignedByteToInt(offset) < unsignedByteToInt(length)) {
                        int a = 0;
                    }

                    baos.write(offset);
                    baos.write(length);

                    w.shiftLeft(es.getLength());
                } else {

                    flags.addFlag(false); //flag 0 indicates literal
                    //only ASCII
                    String symbol = w.getFirstCharLookAheadBuffer()+"";

                    byte[] symb = symbol.getBytes("UTF-8");
                    baos.write(symb);
                    w.shiftLeft(1);
                }
                NUMBER_OF_TOKENS++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
        }

        flags.addFlag(true); //special flag at last
        byte[] losf = flags.toByteArray();
        byte[] hola = Bytes.concat(intToBytes(NUMBER_OF_TOKENS), flags.toByteArray());
        byte[] hola2 = Bytes.concat(hola, baos.toByteArray());
        return hola2;
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
                    if (i == 246679) {
                        int a = 0;
                    }
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
                            String utf = new String(utfBytes, StandardCharsets.UTF_8);
                            dw.addChar(utf.charAt(0));
                        }
                    }

                    else {
                        //b was offset_length
                        byte o = input[i];
                        int off = input[i];
                        off = unsignedByteToInt(input[i]);
                        i++;
                        int len = input[i];
                        len = unsignedByteToInt(input[i]);
                        //OFF_LEN encoded
                        //int[] off_len = decodify_offset_length_one_byte(input[i]);
                        //int len = off_len[1];
                        //int off = off_len[0];
                        dw.copyCharsSince(len,off);
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
}
