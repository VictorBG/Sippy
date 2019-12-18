package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;
import prop.dominio.model.ItemC;
import prop.dominio.model.ItemNC;
import java.util.ArrayList;
import java.util.List;

/**
 * @class File
 * @brief Classe del model per a mantenir l’informació dels itemNC de tipus file
 *     Author: Victor Blanco
 */
public abstract class File extends ItemNC {

  /**
   * @brief Constructora
   *
   *     \pre path vàlid
   *     \post Nova instància de File amb el path indicat
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
   * L'{@link Algorithm} necessari per comprimir l'ítem.
   * Pre: Cert.
   * Post: Retorna l'algorisme per defecte.
   *
   * @return L'{@link Algorithm} per defecte.
   */
  public abstract Algorithm getDefaultAlgorithm();

  /**
   * Retorna si l'{@param algorithm} és admés o no pel tipus de fitxer.
   * En cas de que {@link Algorithm#AUTOMATIC} sigui seleccionat, HAURIA
   * de retornar false.
   *
   * @param algorithm Algorisme per comprovar.
   *
   * @return Estat de l'algorisme respecte al fitxer.
   */
  public abstract boolean isAlgorithmSupported(Algorithm algorithm);
}
