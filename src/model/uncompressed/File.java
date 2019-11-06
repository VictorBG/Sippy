package model.uncompressed;

import static utils.FileUtils.DEFAULT_ENCODING_EXTENSION;

import algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import model.compressed.ItemC;
import model.uncompressed.file.FilePpm;
import model.uncompressed.file.FileTxt;
import utils.FileUtils;

public abstract class File extends ItemNC {

  public File(String path) {
    super(path);
  }

  public static ItemNC forExtension(String extension, String path, byte[] data)
      throws UnsupportedEncodingException {
    if ("ppm".equals(extension)) {
      return new FilePpm(FileUtils.changeExtension(path, "ppm"), data);
    } else if ("txt".equals(extension)) {
      return new FileTxt(FileUtils.changeExtension(path, "txt"), data);
    }
    throw new UnsupportedEncodingException();
  }

  @Override
  public ItemC compress(Algorithm algorithm) {

    Algorithm used = algorithm != null && isAlgorithmSupported(algorithm)
        ? algorithm
        : getDefaultAlgorithm();

    byte[] data = used.getAlgorithm().encode(getData());

    return ItemC.createForFile(getPath(), data, used);
  }

}
