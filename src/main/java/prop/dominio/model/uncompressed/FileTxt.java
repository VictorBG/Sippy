package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;

/**
 * @class FileTxt
 * @brief Classe del model per a mantener la información dels File de tipus txt.
 * Author: Victor Blanco
 */
public class FileTxt extends File {

  /**
   * @brief Constructora
   *
   * \pre path valid
   * \post Es crea una nova instancia de FileTxt amb el path indicat
   */
  public FileTxt(String path) {
    super(path);
  }

  /**
   * @brief Constructora
   *
   * \pre file valid
   * \post Es crea una nova instancia de FileTxt amb el path del file indicat
   */
  public FileTxt(java.io.File file) {
    super(file);
  }

  /**
   * @brief  Mètode que retorna l’algorisme per defecte a l’hora de comprimir arxius ppm
   *
   * \pre Cert
   * \post Algorisme LZW
   */
  @Override
  public Algorithm getDefaultAlgorithm() {
    return Algorithm.LZW;
  }

  /**
   * @brief Retorna true nomès si l’algorisme indicat per parametre és el LZ78, LZSS o LZW
   *
   * \pre Algorisme a comprovar
   * \post Retorna si l'algorisme està suportat
   *
   * @param algorithm Algorithm to check.
   * @return
   */
  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm == Algorithm.LZ78
        || algorithm == Algorithm.LZSS
        || algorithm == Algorithm.LZW;
  }
}
