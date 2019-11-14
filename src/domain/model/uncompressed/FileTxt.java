package domain.model.uncompressed;

import domain.algorithms.Algorithm;

/**
 * Author: Designed in class implemented by Victor and Sergio
 */
public class FileTxt extends File {

  public FileTxt(String path) {
    super(path);
  }

  public FileTxt(java.io.File file) {
    super(file);
  }

  @Override
  public Algorithm getDefaultAlgorithm() {
    return Algorithm.LZW;
  }

  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm == Algorithm.LZ78
        || algorithm == Algorithm.LZSS
        || algorithm == Algorithm.LZW;
  }
}
