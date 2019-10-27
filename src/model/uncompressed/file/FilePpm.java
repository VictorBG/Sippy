package model.uncompressed.file;

import algorithms.Algorithm;
import model.uncompressed.File;

public class FilePpm extends File {

  public FilePpm(String path, byte[] data) {
    super(path);
    setData(data);
  }

  @Override
  public Algorithm getDefaultAlgorithm() {
    return Algorithm.JPEG;
  }

  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm.name().equals(Algorithm.JPEG.name());
  }
}
