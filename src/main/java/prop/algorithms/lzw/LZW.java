package prop.algorithms.lzw;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;

/**
 * @class LZW
 * @brief LZW implementation doesnt compress ppm files
 *     Author: Sergio Vazquez
 */

public class LZW implements BaseAlgorithm {

  private ByteArrayOutputStream arrayOutputStream;

  public byte[] encode(byte[] data) {
    arrayOutputStream = new ByteArrayOutputStream();
    HashMap<String, Integer> dictionary = new HashMap<>();
    int dictSize = 256;
    int j = 0;
    String stringBuilder = "";
    char character = getChar(data, j++);
    byte[] buffer = new byte[3];
    boolean lowerBits = true;
    //Here we construct the dictionary with all ASCII characters
    for (int i = 0; i < 256; i++) {
      dictionary.put(Character.toString((char) i), i);
    }
    stringBuilder = "" + character;
    //Loop that iterates through all the data vector
    while (j < data.length) {
      character = getChar(data, j++);

      if (!dictionary.containsKey(stringBuilder + character)) {
        int position = dictionary.get(stringBuilder);
        //This boolean indicates when we have to write in the lower part of the buffer
        if (lowerBits) {
          //Takes the 12 bits of position
          buffer[0] = (byte) (position & 0xff);
          buffer[1] = (byte) ((position >> 8) << 4);
        } else {
          //Takes the 12 bits of position
          buffer[1] += (byte) (position & 0xf);
          buffer[2] = (byte) (position >> 4);
          try {
            arrayOutputStream.write(buffer);
          } catch (IOException e) {
            e.printStackTrace();
          }
          Arrays.fill(buffer, (byte) 0);
        }
        lowerBits = !lowerBits;
        if (dictSize < 4096) {
          dictionary.put(stringBuilder + character, dictSize++);
        }
        stringBuilder = "" + character;
      } else {
        stringBuilder = stringBuilder + character;
      }
    }
    int compressed = dictionary.get(stringBuilder);
    if (lowerBits) {
      buffer[0] = (byte) (compressed & 0xff);
      buffer[1] = (byte) ((compressed >> 8) << 4);
      arrayOutputStream.write(buffer[0]);
      arrayOutputStream.write(buffer[1]);
    } else {
      buffer[1] += (byte) (compressed & 0xf);
      buffer[2] = (byte) (compressed >> 4);

      try {
        arrayOutputStream.write(buffer);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return arrayOutputStream.toByteArray();
  }

  /**
   * Converts 8 bits to 12 bits
   *
   * @param data - bytes vector , pos - the position inside data vector that we want to obtain out -
   *             char to insert in dictionary
   *
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
    int currentValue;
    int lastValue;
    byte[] buffer = new byte[3];
    boolean lowerBits = false;

    for (int i = 0; i < 256; i++) {
      dictionary.put(i, Character.toString((char) i));
    }
    buffer[0] = input[0];
    buffer[1] = input[1];
    lastValue = getValue(buffer[0], buffer[1], true);
    String s = dictionary.get(lastValue);
    byte[] aux = s.getBytes();
    try {
      arrayOutputStream.write(aux);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Reads 3 bytes and decode
    int j = 2;
    while (j < input.length) {
      if (lowerBits) {
        buffer[0] = input[j++];
        buffer[1] = input[j++];
        currentValue = getValue(buffer[0], buffer[1], true);
      } else {
        buffer[2] = input[j++];
        currentValue = getValue(buffer[1], buffer[2], false);
      }
      lowerBits = !lowerBits;

      if (currentValue >= dictionary.size()) {
        s = dictionary.get(lastValue) + dictionary.get(lastValue).charAt(0);
        if (dictionary.size() < 4096) {
          dictionary.put(dictionary.size(), s);
        }
        try {
          arrayOutputStream.write(s.getBytes());
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        s = dictionary.get(currentValue);
        if (dictionary.size() < 4096) {
          dictionary.put(dictionary.size(), dictionary.get(lastValue) + s.charAt(0));
        }
        try {
          arrayOutputStream.write(s.getBytes());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      lastValue = currentValue;
    }

    return arrayOutputStream.toByteArray();
  }

  /**
   * Extract the 12 bit key from 2 bytes and gets the integer value of the key
   *
   * @param b1     - First byte
   * @param b2     - Second byte
   * @param onleft - True if on left, false if not
   *
   * @return - An Integer which holds the value of the key
   */
  public int getValue(byte b1, byte b2, boolean onleft) {
    int value;
    if (onleft) {
      value = ((int) b1 & 0xFF) + ((((int) b2 & 0xFF) >> 4) << 8);
    } else {
      value = ((int) b1 & 0xF) + (((int) b2 & 0xFF) << 4);
    }
    return value;
  }

  @Override
  public Algorithm getAlgorithmUsed() {
    return Algorithm.LZW;
  }
}
