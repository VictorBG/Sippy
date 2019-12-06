package prop.algorithms.base;

import java.io.File;
import java.io.IOException;
import prop.utils.FileUtils;

/**
 * @class BaseAlgorithm
 * @brief Interficie per a la implementació dels mètodes comprimir i descomprimir dels algorismes,
 * i per a la implementació del mètode de lectura del arxiu, que per defecte llegeix els bytes a
 * través del mètode Files.readAllBytes().
 * Author: Victor Blanco
 */
public interface BaseAlgorithm {

  byte[] encode(byte[] input);

  byte[] decode(byte[] input);

  default byte[] readFile(File file) throws IOException {
    return FileUtils.readFile(file);
  }
}