package domain.model.uncompressed;

import domain.algorithms.Algorithm;

/**
 * Author: Designed in class implemented by Victor and Sergio
 */
public class FilePpm extends File {

  public FilePpm(String path) {
    super(path);
  }

  public FilePpm(java.io.File file) {
    super(file);
  }

  @Override
  public Algorithm getDefaultAlgorithm() {
    return Algorithm.JPEG;
  }

  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm == Algorithm.JPEG;
  }
}
