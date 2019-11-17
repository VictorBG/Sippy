package prop.algorithms.base;

import java.io.File;
import java.io.IOException;
import prop.utils.FileUtils;

/**
 * Author: Victor Blanco
 * <p>
 * Base interface for the implementation of the encode/decode of an algorithm
 */
public interface BaseAlgorithm {

  byte[] encode(byte[] input);

  byte[] decode(byte[] input);

  default byte[] readFile(File file) throws IOException {
    return FileUtils.readFile(file);
  }
}
