package domain.algorithms.lzss;

import domain.algorithms.base.BaseAlgorithm;
import utils.Bytes;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

/**
 * Author: Miguel Angel Cabrera
 *
 * Main class of LZSS algorithm
 *
 */
public class Lzss implements BaseAlgorithm {

    public static ByteArrayOutputStream baos;

    public static final int MIN_LEN_MATCH = 2;
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

    public static byte get_length_byte_3_low_bits (byte length) {

        byte result = (byte)(length);
        return result;
    }

    public static byte get_offset_byte_3_4_5_6_high_bits(byte offset) {

        byte result = (byte)(offset << 3);
        return result;
    }

    public static byte codify_offset_length_one_byte_with_flag(byte offset, byte length) {
        //resto 3
        offset = mapOffsetToCodedOffset(offset);
        length = mapLengthToCodedLength(length);

        byte o = get_offset_byte_3_4_5_6_high_bits(offset);
        byte l = get_length_byte_3_low_bits(length);

        byte result = (byte)(o | l);
        result = (byte)(result | 0x80); //higher bit flag=1
        return result;
    }

    public static int[] decodify_offset_length_one_byte(byte off_len) {
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
            int i = 0;

            while (!w.lookAheadIsEmpty()) {
                if (i == 5492) {
                    int a = 0;
                }
                EncodedString es = w.findMatch();

                //System.out.print(w.getFirstCharLookAheadBuffer());
                if (es.getLength() >= MIN_LEN_MATCH) {
                    //es.print();
                    if (es.getLength() != 0) {
                        int a = 0;
                    }
                    flags.addFlag(true); //flag 1 indicates <length,offset> token
                    byte offset = (byte)es.getOffset(); //cast negative!!
                    byte length = (byte)es.getLength();
                    baos.write(offset);
                    baos.write(length);

                    w.shiftLeft(es.getLength());
                } else {

                    flags.addFlag(false); //flag 0 indicates literal
                    //only ASCII
                    String symbol = w.getFirstCharLookAheadBuffer()+"";

                    byte[] symb = symbol.getBytes("UTF-8");

                    //bos.write(flag_literal);

                    baos.write(symb);
                    //System.out.printf("00%c", w.getFirstCharLookAheadBuffer());
                    w.shiftLeft(1);
                }
                NUMBER_OF_TOKENS++;
                i++;

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
                    if (flags.next() == false) { //literal
                        byte byte_read = input[i];
                        if (unsignedByteToInt(byte_read) < 128) { //ascii literal
                            //no flag byte, directly char
                            dw.addChar((char)byte_read);
                            System.out.print((char)byte_read);
                            if (unsignedByteToInt(byte_read) >= 256) {
                                int a = 0;
                            }
                        }
                        else {
                            i++;
                            byte byte2 = input[i];
                            byte[] utfBytes = {byte_read, byte2};
                            String utf = new String(utfBytes, StandardCharsets.UTF_8);
                            dw.addChar(utf.charAt(0));
                            System.out.print(utf.charAt(0));
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
                        System.out.printf("<"+off+","+len+">");

                        if (off < len) {
                            int a = 0;
                        }
                        //OFF_LEN
                        //int[] off_len = decodify_offset_length_one_byte(input[i]);
                        //int len = off_len[1];
                        //int off = off_len[0];
                        dw.copyCharsSince(len,off);
                    }
                    //System.out.print("la i vale");
                    //System.out.print(i);

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
