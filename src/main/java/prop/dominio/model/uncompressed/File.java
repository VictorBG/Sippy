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
   *     \pre path valid
   *     \post Nova instancia de File amb el path indicat
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
   * The default {@link Algorithm} that needs to be used to compress the item.
   * <p>
   * <p>
   * Pre: None. Post: Returns default algorithm.
   *
   * @return default {@link Algorithm} to use
   */
  public abstract Algorithm getDefaultAlgorithm();

  /**
   * Returns if the {@param algorithm} is supported by the file type or not. If {@link
   * Algorithm#AUTOMATIC} is passed it SHOULD return false.
   *
   * @param algorithm Algorithm to check.
   *
   * @return Support status of the algorithm.
   */
  public abstract boolean isAlgorithmSupported(Algorithm algorithm);
}
