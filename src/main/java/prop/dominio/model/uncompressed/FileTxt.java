package prop.dominio.model.uncompressed;

import java.util.ArrayList;
import prop.algorithms.Algorithm;
import prop.algorithms.automatic.Automatic;

/**
 * @class FileTxt
 * @brief Classe del model per a mantener la información dels File de tipus txt.
 *     Author: Victor Blanco
 */
public class FileTxt extends File {

  /**
   * @brief Constructora
   *
   *     \pre path valid
   *     \post Es crea una nova instancia de FileTxt amb el path indicat
   */
  public FileTxt(String path) {
    super(path);
  }

  /**
   * @brief Mètode que retorna l’algorisme per defecte a l’hora de comprimir arxius ppm
   *
   *     \pre Cert
   *     \post Algorisme LZW
   */
  @Override
  public Algorithm getDefaultAlgorithm() {
    Algorithm algorithm = Algorithm.AUTOMATIC;
    ((Automatic) algorithm.getAlgorithm()).setAllowedAlgorithms(new ArrayList<Algorithm>() {{
      add(Algorithm.LZ78);
      add(Algorithm.LZSS);
      add(Algorithm.LZW);
    }});
    return algorithm;
  }

  /**
   * @param algorithm Algorithm to check.
   *
   * @brief Retorna true nomès si l’algorisme indicat per parametre és el LZ78, LZSS o LZW
   *
   *     \pre Algorisme a comprovar
   *     \post Retorna si l'algorisme està suportat
   */
  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    return algorithm == Algorithm.LZ78
        || algorithm == Algorithm.LZSS
        || algorithm == Algorithm.LZW;
  }
}
