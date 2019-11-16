package domain.algorithms;

import domain.algorithms.base.BaseAlgorithm;
import domain.algorithms.lz78.LZ78;

/**
 * Author: Victor Blanco
 *
 * Enum with the list of the Algorithms availables in the system and its respective id.
 *
 */
public enum Algorithm {
  LZ78((byte) 0x0, new LZ78()),
  LZW((byte) 0x1, null),
  JPEG((byte) 0x2, null),
  LZSS((byte) 0x3, null),
  AUTOMATIC((byte) 0x4, null);

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
      default:
        return null;
    }
  }
}