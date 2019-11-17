package dominio.model.uncompressed;

import algorithms.Algorithm;
import dominio.model.ItemC;
import dominio.model.ItemNC;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Victor Blanco
 * <p>
 * File model class to keep info of the {@link ItemNC} items that are single files.
 */
public abstract class File extends ItemNC {

  public File(String path) {
    super(path);
  }

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
