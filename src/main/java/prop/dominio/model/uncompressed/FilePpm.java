package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;

/**
 * Author: Victor Blanco
 *
 * @class FilePpm
 * @brief Classe del model per a mantener la información dels File de tipus ppm.
 *
 */
public class FilePpm extends File {


  /**
   * @brief Constructora
   *
   * \pre path valid
   *  \post Es crea una nova instancia de FilePpm amb el path indicat
   */
  public FilePpm(String path) {
    super(path);
  }

  /**
   * @brief Mètode que retorna l’algorisme per defecte a l’hora de comprimir arxius ppm
   *
   * \pre Cert
   *  \post Algorisme JPEG
   */
  @Override
  public Algorithm getDefaultAlgorithm() {
    return Algorithm.JPEG;
  }

  /**
   * @param algorithm Algorithm to check.
   *
   * @brief Retorna true nomès si l’algorisme indicat per parametre és el JPEG
   *
   * \pre Algorisme a comprovar
   *  \post Retorna si l'algorisme està suportat
   */
  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm == Algorithm.JPEG;
  }
}
