package prop.algorithms.lz78;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import prop.algorithms.Algorithm;
import prop.algorithms.base.BaseAlgorithm;

/**
 * Author: Victor Blanco
 *
 * @class LZ78
 * @brief Implementació de l'algorisme LZ78.
 *
 *     Millores:
 *     - Nombre variable de bits a l'índex. Podem suposar que l’índex x no tindrà un
 *     nombre superior a x-1, per tant, podem definir que el nombre de bits que
 *     aquesta posició pot prendre com a màxim és log2 (x) en lloc de prendre sempre
 *     24 bits, cosa que també imposa un límit teòric màxim de 2 ^ 24 valors per l’índex.
 *     És car per a fitxers de mida petita.
 *
 *     - Utilitza un Tree en comptes d'un HashMap. Es veu millora però no molta.
 */
public class LZ78 implements BaseAlgorithm {

  private ByteArrayOutputStream baos;

  @Override
  public byte[] encode(byte[] data) {
    baos = new ByteArrayOutputStream();
    HashMap<String, Integer> dictionary = new HashMap<>();

    String s = "";
    Integer pos = 0;
    int index = 1;
    for (byte b : data) {
      char c = getChar(b);
      s += c;
      if (!dictionary.containsKey(s)) {
        dictionary.put(s, index++);
        write(new Pair(pos, c).getBytes());
        s = "";
        pos = 0;
      } else {
        pos = dictionary.get(s);
      }
    }

    if (pos != 0) {
      write(new Pair(pos, Character.MIN_VALUE).getBytes());
    }

    return baos.toByteArray();
  }

  @Override
  public byte[] decode(byte[] input) {
    StringBuilder result = new StringBuilder();
    HashMap<Integer, Pair> dictionary = new HashMap<>();

    int k = 1;

    for (int i = 0; i < input.length; i += 4) {
      int number = byteArrayToInt(new byte[]{input[i], input[i + 1], input[i + 2]});
      int value = input[i + 3];
      if (value < 0) {
        value += 256;
      }
      char data = (char) value;
      dictionary.put(k, new Pair(number, data));
      result.append(getString(dictionary, k++));
    }

    return result.toString().getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Escriu un tros de dades al {@link #baos} output stream.
   *
   * @param data dada a escriure.
   */
  private void write(byte[] data) {
    try {
      baos.write(data);
    } catch (IOException ignore) {}
  }

  // TODO: These 2 methods can be implemented better

  /**
   * Retorna un String que conté la paraula recuperada del diccionary.
   *
   * Llegeix l’element de valor i recupera l’objecte {@link Pair} associat a ell,
   * després retorna el char que el pair conté concatenat amb l'string retornat per
   * aquest mètode, pel valor contingut pel pair.
   *
   * Si el valor contingut pel pair és 0, significa que s'ha arribat al final de
   * la recuperació de l'string i no iterará més.
   *
   * @param dic   Diccionari del qual es llegeix.
   * @param value Valor que es llegeix del diccionari.
   *
   * @return L'string recuperat del map que comença amb l'objecte value.
   *
   */
  private String getString(HashMap<Integer, Pair> dic, int value) {
    if (dic.get(value).getFirst() == 0) {
      return charAt(dic, value);
    } else {
      return getString(dic, dic.get(value).getFirst()) + charAt(dic, value);
    }
  }

  /**
   * Retorna el char contingut a la posició (o valor) del diccionari. Si el char
   * és un {@link Character# MIN_VALUE}, retorna un String buit, altrament
   * retorna el char de la posició esmentada.
   *
   * @param dic Diccionari del qual es llegeix.
   * @param pos Posició (valor) que es llegeix del diccionari.
   *
   * @return el char que es troba a la posició pos del diccionari.
   */
  private String charAt(HashMap<Integer, Pair> dic, int pos) {
    char res;
    if ((res = dic.get(pos).getSecond()) == Character.MIN_VALUE) {
      return "";
    }
    return String.valueOf(res);
  }

  /**
   * Retorna un char segons el valor del byte.
   *
   * Si el valor és negatiu (ca2), aquest és convertit a valor positiu amb
   * motiu de seguir la taula ASCII, altrament retorna el valor original.
   *
   * @param b Byte a convertir.
   *
   * @return El byte convertit en un char de la taula ASCII.
   */
  private char getChar(byte b) {
    int i = new Byte(b).intValue();
    if (i < 0) {
      i += 256;
    }
    return (char) i;
  }

  /**
   * Converteix l'array de bytes de tamany 3 donada en un int
   *
   * @param b Array de bytes per convertir
   *
   * @return Integer contingut a l'array de bytes
   */
  private int byteArrayToInt(byte[] b) {
    return
        (b[2] & 0xFF) |
            (b[1] & 0xFF) << 8 |
            (b[0] & 0xFF) << 16;
  }

  @Override
  public Algorithm getAlgorithmUsed() {
    return Algorithm.LZ78;
  }
}
