package algorithms;

import algorithms.base.BaseAlgorithm;
import model.ItemC;
import model.ItemNC;

public enum Algorithms {
  LZ78(new LZ78()),
  LZW(null),
  JPEG(null),
  EL_OTRO(null),
  AUTOMATIC(null);

  // TODO: put correct generics here
  private BaseAlgorithm<byte[], byte[]> algorithm;
//  private BaseAlgorithm<ItemNC, ItemC> algorithm; should be this

  Algorithms(BaseAlgorithm<byte[], byte[]> algorithm) {
    this.algorithm = algorithm;
  }

  public BaseAlgorithm<byte[], byte[]> getAlgorithm() {
    return algorithm;
  }
}
