package model.uncompressed.file;

import algorithms.Algorithm;
import model.compressed.ItemC;
import model.uncompressed.File;

public class FileTxt extends File {

  public FileTxt(String path, byte[] data) {
    super(path);
    setData(data);
  }

  @Override
  public Algorithm getDefaultAlgorithm() {
    // FIXME (Victor): Is LZW the best?
    return Algorithm.LZW;
  }

  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm.name().equals(Algorithm.LZ78.name())
        || algorithm.name().equals(Algorithm.LZSS.name())
        || algorithm.name().equals(Algorithm.LZW.name());
  }
}
