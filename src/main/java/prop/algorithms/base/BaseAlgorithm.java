package prop.algorithms.base;

import java.io.File;
import java.io.IOException;
import prop.algorithms.Algorithm;
import prop.utils.FileUtils;

/**
 * @class BaseAlgorithm
 * @brief Interficie per a la implementacio dels mètodes comprimir i descomprimir dels
 *     algorismes,
 *     i per a la implementacio del mètode de lectura del arxiu, que per defecte llegeix els bytes
 *     a
 *     traves del metode Files.readAllBytes().
 *     Author: Victor Blanco
 */
public interface BaseAlgorithm {


  byte[] encode(byte[] input);

  byte[] decode(byte[] input);

  /**
   * Returns the algorithm used, it is useful for variable algorithms
   * that may choose between others, for simple algorithms it
   * just returns itself.
   *
   * @return Algorithm used
   */
  Algorithm getAlgorithmUsed();

  /**
   * Default read file, it can be overriden to customize the read of a file.
   *
   * By default it reads the whole file as an array of bytes
   *
   * @param file file to read from
   *
   * @return array of bytes containing the data of the file
   * @throws IOException
   */
  default byte[] readFile(File file) throws IOException {
    return FileUtils.readFile(file);
  }
}
