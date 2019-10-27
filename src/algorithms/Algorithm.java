package algorithms;

import algorithms.base.BaseAlgorithm;

public enum Algorithm {
  LZ78((byte) 0, new LZ78()),
  LZW((byte) 1, null),
  JPEG((byte) 2, null),
  LZSS((byte) 3, null),
  AUTOMATIC((byte) 4, null);

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
      case 0:
        return Algorithm.LZ78;
      case 1:
        return Algorithm.LZW;
      case 2:
        return Algorithm.JPEG;
      case 3:
        return Algorithm.LZSS;
      default:
        return null;
    }
  }
}
