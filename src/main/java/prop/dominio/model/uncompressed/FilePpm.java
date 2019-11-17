package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;

/**
 * Author: Victor Blanco
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
