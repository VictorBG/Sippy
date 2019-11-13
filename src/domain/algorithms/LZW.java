package domain.algorithms;

import java.util.Arrays;
import java.util.HashMap;

import domain.algorithms.base.BaseAlgorithm;
import utils.Bytes;


public class LZW implements BaseAlgorithm {

  int dictSize = 256;

  public byte[] encode(byte[] data) {
    int dictSize = 256;

    HashMap<String, Integer> dictionary = new HashMap<>();
    String str = "";
    byte[] buffer = new byte[3];
    boolean onleft = true;
    int j = 0;
    byte[] output = new byte[]{};
    // Builds the initial dictionary with all ASCII characters
    for (int i = 0; i < 256; i++) {
      dictionary.put(Character.toString((char) i), i);
    }

    char ch = getChar(data, j++);
    str = "" + ch;

    // Reads Character by Character
    while (j < data.length) {
      System.out.println(j);
      ch = getChar(data, j++);

      // If str + ch is in the dictionary..
      // Set str to str + ch
      if (dictionary.containsKey(str + ch)) {
        str = str + ch;
      } else {
        String s12 = to12bit(dictionary.get(str));

        // Store the 12 bits into an array and then write it to the
        // output file
        if (onleft) {
          buffer[0] = (byte) Integer.parseInt(
              s12.substring(0, 8), 2);
          buffer[1] = (byte) Integer.parseInt(
              s12.substring(8, 12) + "0000", 2);
        } else {
          buffer[1] += (byte) Integer.parseInt(
              s12.substring(0, 4), 2);
          buffer[2] = (byte) Integer.parseInt(
              s12.substring(4, 12), 2);

          for (int b = 0; b < buffer.length; b++) {
            output = Bytes.concat(output, new byte[]{buffer[b]});
            buffer[b] = 0;
          }
        }
        onleft = !onleft;

        // Add str + ch to the dictionary
        if (dictSize < 4096) {
          dictionary.put(str + ch, dictSize++);
        }

        // Set str to ch
        str = "" + ch;
      }
    }
    /*
     * Handles input/output file failure by converting 8bit to 12bit
     * then storing integers to byte and writing to output file else add
     * the buffers to [1] or use buffer[2] then using the length and a
     * for loop to output the bytes and then zero out the buffer, note
     * this code is similar to above code, which insures bits are stored
     */

    String str12bit = to12bit(dictionary.get(str));
    if (onleft) {
      buffer[0] = (byte) Integer.parseInt(str12bit.substring(0, 8), 2);
      buffer[1] = (byte) Integer.parseInt(str12bit.substring(8, 12)
          + "0000", 2);
      output = Bytes.concat(output, new byte[]{buffer[0]});
      output = Bytes.concat(output, new byte[]{buffer[1]});
    } else {
      buffer[1] += (byte) Integer.parseInt(str12bit.substring(0, 4), 2);
      buffer[2] = (byte) Integer.parseInt(str12bit.substring(4, 12), 2);

      for (int b = 0; b < buffer.length; b++) {
        output = Bytes.concat(output, new byte[]{buffer[b]});
        buffer[b] = 0;
      }
    }

    return output;
  }

  /**
   * Converts 8 bits to 12 bits
   *
   * @param i - Integer value
   * @return - String value of integer in 12 bit
   */
  private String to12bit(int i) {
    return extend(Integer.toBinaryString(i), "0", 12);
  }

  /**
   * Converts 8 bits to 12 bits
   *
   * @param data - bytes vector , pos - the position inside data vector that we want to obtain
   *             out - char to insert in dictionary
   * @return - String value of integer in 12 bit
   */
  private char getChar(byte[] data, int pos) {
    byte b = data[pos];
    int i = new Byte(b).intValue();

    if (i < 0) {
      i += 256;
    }
    return (char) i;
  }


  @Override
  public byte[] decode(final byte[] input) {
    HashMap<Integer, String> dictionary = new HashMap<>();
    String[] Array_char;
    int dictSize = 256;
    int currword;
    int priorword;
    byte[] buffer = new byte[3];
    boolean onleft = false;

    Array_char = new String[4096];
    byte[] output = new byte[0];

    for (int i = 0; i < 256; i++) {
      dictionary.put(i, Character.toString((char) i));
      Array_char[i] = Character.toString((char) i);
    }
    // Gets the first word in code and outputs its corresponding char
    buffer[0] = input[0];
    buffer[1] = input[1];
    priorword = getValue(buffer[0], buffer[1], true);
    String s = Array_char[priorword];
    byte[] aux = s.getBytes();
    output = Bytes.concat(output, aux);
    // Reads every 3 bytes and generates corresponding characters
    int j = 2;
    while (j < input.length) {
      if (onleft) {
        buffer[0] = input[j++];
        buffer[1] = input[j++];
        currword = getValue(buffer[0], buffer[1], onleft);
      } else {
        buffer[2] = input[j++];
        currword = getValue(buffer[1], buffer[2], onleft);
      }
      onleft = !onleft;

      if (currword >= dictSize) {
        if (dictSize < 4096) {
          String test = Array_char[priorword];
          System.out.println(priorword);
          Array_char[dictSize] = Array_char[priorword]
              + Array_char[priorword].charAt(0);
        }
        dictSize++;

        s = Array_char[priorword] + Array_char[priorword].charAt(0);
        aux = s.getBytes();
        output = Bytes.concat(output, aux);

      } else {
        if (dictSize < 4096) {
          Array_char[dictSize] = Array_char[priorword]
              + Array_char[currword].charAt(0);
        }
        dictSize++;
        s = Array_char[currword];

        aux = s.getBytes();
        output = Bytes.concat(output, aux);
      }
      priorword = currword;
    }
    return output;
  }

  /**
   * Extract the 12 bit key from 2 bytes and gets the integer value of the key
   *
   * @param b1     - First byte
   * @param b2     - Second byte
   * @param onleft - True if on left, false if not
   * @return - An Integer which holds the value of the key
   */
  public int getValue(byte b1, byte b2, boolean onleft) {
    String s1 = extend(Integer.toBinaryString(b1), "0", 8);
    String s2 = extend(Integer.toBinaryString(b2), "0", 8);

    if (s1.length() == 32) {
      s1 = s1.substring(24, 32);
    }
    if (s2.length() == 32) {
      s2 = s2.substring(24, 32);
    }

    if (onleft) {
      return Integer.parseInt(s1 + s2.substring(0, 4), 2);
    } else {
      return Integer.parseInt(s1.substring(4, 8) + s2, 2);
    }
  }

  private String extend(String input, String value, int length) {
    StringBuilder inputBuilder = new StringBuilder(input);
    while (inputBuilder.length() < length) {
      inputBuilder.insert(0, value);
    }
    return inputBuilder.toString();
  }


}