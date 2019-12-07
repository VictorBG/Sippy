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

  Algorithm getAlgorithmUsed();

  default byte[] readFile(File file) throws IOException {
    return FileUtils.readFile(file);
  }
}
