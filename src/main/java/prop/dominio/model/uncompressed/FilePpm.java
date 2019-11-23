package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;

/**
 * @class FilePpm
 * @brief Classe del model per a mantener la información dels File de tipus ppm.
 * Author: Victor Blanco
 */
public class FilePpm extends File {


  /**
   * @brief Constructora
   *
   * \pre path valid
   * \post Es crea una nova instancia de FilePpm amb el path indicat
   */
  public FilePpm(String path) {
    super(path);
  }

  /**
   * @brief Constructora
   *
   * \pre file valid
   * \post Es crea una nova instancia de FilePpm amb el path del file indicat
   */
  public FilePpm(java.io.File file) {
    super(file);
  }

  /**
   * @brief  Mètode que retorna l’algorisme per defecte a l’hora de comprimir arxius ppm
   *
   * \pre Cert
   * \post Algorisme JPEG
   */
  @Override
  public Algorithm getDefaultAlgorithm() {
    return Algorithm.JPEG;
  }

  /**
   * @brief Retorna true nomès si l’algorisme indicat per parametre és el JPEG
   *
   * \pre Algorisme a comprovar
   * \post Retorna si l'algorisme està suportat
   *
   * @param algorithm Algorithm to check.
   */
  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm == Algorithm.JPEG;
  }
}
