package prop.dominio.model.uncompressed;

import prop.algorithms.Algorithm;
import prop.dominio.model.ItemC;
import prop.dominio.model.ItemNC;
import java.util.ArrayList;
import java.util.List;

/**
 * @class File
 * @brief Classe del model per a mantenir l’informació dels itemNC de tipus file
 * Author: Victor Blanco
 */
public abstract class File extends ItemNC {

  /**
   * @brief Constructora
   *
   * \pre path valid
   * \post Nova instancia de File amb el path indicat
   *
   */
  public File(String path) {
    super(path);
  }

  /**
   * @brief Constructora
   *
   * \pre file valid
   * \post Nova instancia de File amb el path del file indicat
   *
   */
  public File(java.io.File file) {
    super(file);
  }

  @Override
  public List<File> getItems() {
    return new ArrayList<File>() {
      {
        add(File.this);
      }
    };
  }

  /**
   * @brief  Retorna un ItemC amb la informació necesaria per a l’operació zip
   *
   * \pre Algorisme a utilitzar
   * \post Retorna un ItemC amb la informació necesaria per a l'operació zip usant l'algorisme indicat per parametre si es compatible
   *
   * Returns an {@link ItemC} with the info for the zip operation.
   * <p>
   * A default {@link Algorithm} is used if the algorithm provided is in {@link Algorithm#AUTOMATIC}
   * mode.
   *
   * @param algorithm {@link Algorithm} to use.
   * @return {@link ItemC} with the algorithm method to use.
   */
  public ItemC zipInfo(Algorithm algorithm) {
    ItemC itemC = new ItemC(getFile());
    // No need to check for the automatic algorithm, isAlgorithmSupported should return false
    // whit automatic.
    itemC.setMethod(isAlgorithmSupported(algorithm) ? algorithm : getDefaultAlgorithm());
    return itemC;
  }

  @Override
  public double getSize() {
    return getFile().length();
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
   * @return Support status of the algorithm.
   */
  public abstract boolean isAlgorithmSupported(Algorithm algorithm);
}
