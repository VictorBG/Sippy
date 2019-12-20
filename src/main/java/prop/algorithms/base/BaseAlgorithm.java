package prop.algorithms.base;

import java.io.File;
import java.io.IOException;
import prop.algorithms.Algorithm;
import prop.utils.FileUtils;

/**
 * Author: Victor Blanco
 *
 * @class BaseAlgorithm
 * @brief Interfície per la implementació dels mètodes comprimir i descomprimir dels
 *     algorismes, a més a més de la del mètode de lectura del arxiu, que per defecte
 *     llegeix els bytes a través del mètode Files.readAllBytes().
 */
public interface BaseAlgorithm {


  byte[] encode(byte[] input);

  byte[] decode(byte[] input);

  /**
   * Retorna l'agorisme utilitzat; és útil per algorismes variables
   * (aquells que poden ser escollits envers altres), per algorismes
   * simples, només els retorna.
   *
   * @return Algorisme utilitzat.
   */
  Algorithm getAlgorithmUsed();

  /**
   * Lector de fitxer per defecte: pot ésser sobreescrit per a personalitzar.
   *
   * Llegeix el fitxer sencer com un array de bytes per defecte.
   *
   * @param file fitxer del qual es llegeix
   *
   * @return array de bytes que conté les dades del fitxer.
   */
  default byte[] readFile(File file) throws IOException {
    return FileUtils.readFile(file);
  }
}
