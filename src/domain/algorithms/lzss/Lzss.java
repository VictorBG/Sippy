package domain.algorithms.lzss;

import domain.algorithms.base.BaseAlgorithm;
import utils.Bytes;

import java.io.*;
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

    public static final int MIN_LEN_MATCH = 2;
                                                //max9
    public static final int BUFFER_SIZE_LOOKAHEAD = 5;
                                                //max16
    public static final int BUFFER_SIZE_SEARCH = 5;

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
        StringBuilder inputSB = new StringBuilder(new String(input));
        try {
            WindowBuffer w = new WindowBuffer((short)BUFFER_SIZE_SEARCH,(short)BUFFER_SIZE_LOOKAHEAD,inputSB);
            w.fillLookAheadBuffer();

            while (!w.lookAheadIsEmpty()) {
                EncodedString es = w.findMatch();
                es.print();
                if (es.getLength() >= MIN_LEN_MATCH) {
                    //es.print();
                    byte offset = (byte)es.getOffset();
                    byte length = (byte)es.getLength();
                    byte off_len = codify_offset_length_one_byte_with_flag(offset, length);
                    //bos.write(offset);
                    //bos.write(length);
                    baos.write(off_len);

                    w.shiftLeft(es.getLength());
                } else {
                    //String out = "0" + w.getFirstCharLookAheadBuffer();
                    //System.out.print(out);
                    byte flag_literal = (byte)0;
                    //only ASCII
                    String symbol = w.getFirstCharLookAheadBuffer()+"";
                    byte[] symb = symbol.getBytes("UTF-8");

                    //bos.write(flag_literal);

                    baos.write(symb);
                    //System.out.printf("00%c", w.getFirstCharLookAheadBuffer());
                    w.shiftLeft(1);
                }
                NUMBER_OF_TOKENS++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        //return baos.toByteArray();
        byte[] hola = Bytes.concat(intToBytes(NUMBER_OF_TOKENS), baos.toByteArray());
        return hola;
    }

    @Override
    public byte[] decode(byte[] input) {
        baos = new ByteArrayOutputStream();
        StringBuilder empty_string = new StringBuilder();
        try {
            DecodeWindow dw = new DecodeWindow(BUFFER_SIZE_SEARCH);
            try {
                int i = 0;
                while (i < input.length) {
                    byte byte_read = input[i];
                    if (byte_read < 128 && byte_read > 8) {
                        //if (byte_read > 8) { //flag literal or coded token
                        //b = bis2.read(); //literal
                        //dw.addChar((char)b);

                        //no flag byte, directly char
                        dw.addChar((char)byte_read);
                    }
                    else {
                        //b was offset_length
                        //int len = bis2.read(); //length
                        int[] off_len = decodify_offset_length_one_byte(input[i]);
                        int len = off_len[1];
                        //System.out.println(len);
                        int off = off_len[0];
                        dw.copyCharsSince(len,off);
                    }
                    i++;
                }
            }
            finally {
                baos.write(dw.getBuffer().toString().getBytes());
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
