package prop.algorithms;

import prop.algorithms.automatic.Automatic;
import prop.algorithms.base.BaseAlgorithm;
import prop.algorithms.jpeg.JPEG;
import prop.algorithms.lz78.LZ78;
import prop.algorithms.lzss.LZSS;
import prop.algorithms.lzw.LZW;

/**
 * Author: Victor Blanco
 *
 * @class Algorithm
 * @brief Enum amb tots els algorismes disponibles al sistema i relacionats amb la seva
 *     corresponent id.
 *
 */
public enum Algorithm {
  LZ78((byte) 0x0, new LZ78()),
  LZW((byte) 0x1, new LZW()),
  JPEG((byte) 0x2, new JPEG()),
  LZSS((byte) 0x3, new LZSS()),
  AUTOMATIC((byte) 0x4, new Automatic());

  private BaseAlgorithm algorithm;
  private byte id;

  Algorithm(byte id, BaseAlgorithm algorithm) {
    this.id = id;
    this.algorithm = algorithm;
  }

  public byte getId() {
    return id;
  }

  public BaseAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * @brief Retorna l’algorisme que s’identifica per el parametre d’entrada
   *
   *     \pre id es d'un algorisme existent
   *     \post Algorisme identificat pel parametre d'entrada
   */
  public static Algorithm valueOf(byte id) {
    switch (id) {
      case 0x0:
        return Algorithm.LZ78;
      case 0x1:
        return Algorithm.LZW;
      case 0x2:
        return Algorithm.JPEG;
      case 0x3:
        return Algorithm.LZSS;
      case 0x4:
        return Algorithm.AUTOMATIC;
      default:
        return null;
    }
  }
}
