package domain.algorithms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import domain.algorithms.base.BaseAlgorithm;

/**
 * Author: Sergio Vazquez
 * <p>
 * LZW implementation doesnt compress ppm files
 */

public class LZW implements BaseAlgorithm {

  ByteArrayOutputStream arrayOutputStream;

  public byte[] encode(byte[] data) {
    arrayOutputStream = new ByteArrayOutputStream();
    HashMap<String, Integer> dictionary = new HashMap<>();
    int dictSize = 256;
    int j = 0;
    String stringBuilder = "";
    char character = getChar(data, j++);
    byte[] buffer = new byte[3];
    boolean half = true;
    //Here we construct the dictionary with all ASCII characters
    for (int i = 0; i < 256; i++) {
      dictionary.put(Character.toString((char) i), i);
    }
    stringBuilder = "" + character;
    //Loop that iterates through all the data vector
    while (j < data.length) {
      character = getChar(data, j++);

      if (!dictionary.containsKey(stringBuilder + character)) {
        String s12 = extendTo12Bits(dictionary.get(stringBuilder));
        if (half) {
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
            arrayOutputStream.write(buffer[b]);
            buffer[b] = 0;
          }
        }
        half = !half;
        if (dictSize < 4096) {
          dictionary.put(stringBuilder + character, dictSize++);
        }

        stringBuilder = "" + character;
      } else {
        stringBuilder = stringBuilder + character;
      }
    }
    String str12bit = extendTo12Bits(dictionary.get(stringBuilder));
    if (half) {
      buffer[0] = (byte) Integer.parseInt(str12bit.substring(0, 8), 2);
      buffer[1] = (byte) Integer.parseInt(str12bit.substring(8, 12)
          + "0000", 2);
      arrayOutputStream.write(buffer[0]);
      arrayOutputStream.write(buffer[1]);
    } else {
      buffer[1] += (byte) Integer.parseInt(str12bit.substring(0, 4), 2);
      buffer[2] = (byte) Integer.parseInt(str12bit.substring(4, 12), 2);

      for (int b = 0; b < buffer.length; b++) {
        arrayOutputStream.write(buffer[b]);
        buffer[b] = 0;
      }
    }
    return arrayOutputStream.toByteArray();
  }

  /**
   * Converts 8 bits to 12 bits
   *
   * @param i - Integer value
   * @return - String value of integer in 12 bit
   */
  private String extendTo12Bits(int i) {
    return extend(Integer.toBinaryString(i), "0", 12);
  }

  /**
   * Converts 8 bits to 12 bits
   *
   * @param data - bytes vector , pos - the position inside data vector that we want to obtain out -
   *             char to insert in dictionary
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
    arrayOutputStream = new ByteArrayOutputStream();
    String[] arrayChar;
    int dictSize = 256;
    int currword;
    int priorword;
    byte[] buffer = new byte[3];
    boolean onleft = false;

    arrayChar = new String[4096];

    for (int i = 0; i < 256; i++) {
      dictionary.put(i, Character.toString((char) i));
      arrayChar[i] = Character.toString((char) i);
    }
    // Gets the first word in code and outputs its corresponding char
    buffer[0] = input[0];
    buffer[1] = input[1];
    priorword = getValue(buffer[0], buffer[1], true);
    String s = arrayChar[priorword];
    byte[] aux = s.getBytes();
    try {
      arrayOutputStream.write(aux);
    } catch (IOException e) {
      e.printStackTrace();
    }
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
          arrayChar[dictSize] = arrayChar[priorword]
              + arrayChar[priorword].charAt(0);
        }
        dictSize++;

        s = arrayChar[priorword] + arrayChar[priorword].charAt(0);
        aux = s.getBytes();
        try {
          arrayOutputStream.write(aux);
        } catch (IOException e) {
          e.printStackTrace();
        }

      } else {
        if (dictSize < 4096) {
          arrayChar[dictSize] = arrayChar[priorword]
              + arrayChar[currword].charAt(0);
        }
        dictSize++;
        s = arrayChar[currword];

        aux = s.getBytes();
        try {
          arrayOutputStream.write(aux);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      priorword = currword;
    }
    return arrayOutputStream.toByteArray();
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