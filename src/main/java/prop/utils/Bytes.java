package prop.utils;

/**
 * @class Bytes
 * @brief  Utils class, used for concat byte arrays
 * Author: Sergio Vazquez
 */
public class Bytes {
  /**
   * @brief Concatena arrays de bytes
   *\pre array existent
   * \post Concatena els arrays de bytes i retorna la concatenació
   */
  public static byte[] concat(byte[]... arrays) {
    int length = 0;
    for (byte[] array : arrays) {
      length += array.length;
    }
    byte[] result = new byte[length];
    int pos = 0;
    for (byte[] array : arrays) {
      System.arraycopy(array, 0, result, pos, array.length);
      pos += array.length;
    }
    return result;
  }
}
