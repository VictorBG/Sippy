package model.uncompressed;

import static utils.FileUtils.DEFAULT_ENCODING_EXTENSION;

import algorithms.Algorithm;
import model.compressed.ItemC;
import utils.FileUtils;

public abstract class File extends ItemNC {

  public File(String path) {
    super(path);
  }

  @Override
  public ItemC compress(Algorithm algorithm) {

    Algorithm used = algorithm != null && isAlgorithmSupported(algorithm)
        ? algorithm
        : getDefaultAlgorithm();

    byte[] data = used.getAlgorithm().encode(getData());

    return new ItemC(FileUtils.changeExtension(getPath(), DEFAULT_ENCODING_EXTENSION), data, used);
  }

}
