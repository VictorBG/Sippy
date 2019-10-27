package model.uncompressed;

import algorithms.Algorithm;
import model.Item;
import model.compressed.ItemC;

public abstract class ItemNC extends Item {

  ItemNC(String path) {
    super(path);
  }

  /**
   * Returns the default algorithm used for the file to
   * compress and decompress.
   *
   * @return              The default algorithm to use on a compress operation.
   */
  public abstract Algorithm getDefaultAlgorithm();

  /**
   * Checks if the algorithm is supported for a specific file.
   *
   * @param algorithm     The algorithm to check
   * @return              If the algorithm is valid
   */
  public abstract boolean isAlgorithmSupported(Algorithm algorithm);

  /**
   * Compress the item using a specific {@link Algorithm}
   * <p>
   * Pre: The algorithm is valid for the file extension.
   *
   * @param algorithm     The algorithm to use to compress
   * @return              The item compressed.
   */
  public abstract ItemC compress(Algorithm algorithm);

  /**
   * Compress the item using a default {@link Algorithm}.
   *
   * @return              The item compressed.
   */
  public ItemC compress() {
    return compress(getDefaultAlgorithm());
  }




}
