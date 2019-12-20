package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;
import prop.dominio.model.ItemC;
import prop.dominio.model.ItemNC;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Victor Blanco
 *
 * @class File
 * @brief Classe del model per a mantenir l’informació dels itemNC de tipus file
 */
public abstract class File extends ItemNC {

  /**
   * @brief Constructora
   *
   * \pre path vàlid
   *  \post Nova instància de File amb el path indicat
   */
  public File(String path) {
    super(path);
  }

  @Override
  public List<File> getItems() {
    return new ArrayList<File>() {
      {
        add(File.this);
      }
    };
  }

  public Algorithm getSupportedAlgorithm(Algorithm algorithm) {
    return isAlgorithmSupported(algorithm) ? algorithm : getDefaultAlgorithm();
  }

  /**
   * @brief L'{@link Algorithm} necessari per comprimir l'ítem.
   * \pre: Cert.
   * \post: Retorna l'algorisme per defecte.
   *
   * @return L'{@link Algorithm} per defecte.
   */
  public abstract Algorithm getDefaultAlgorithm();

  /**
   * @brief Retorna si l'{@param algorithm} és admés o no pel tipus de fitxer.
   * En cas de que {@link Algorithm#AUTOMATIC} sigui seleccionat, HAURIA
   * de retornar false.
   *
   * \pre cert
   * \post Si el fitxer accepta l'algorisme passat per parametre.
   *
   * @param algorithm Algorisme per comprovar.
   *
   * @return Si el fitxer accepta l'algorisme passat per parametre.
   */
  public abstract boolean isAlgorithmSupported(Algorithm algorithm);
}
