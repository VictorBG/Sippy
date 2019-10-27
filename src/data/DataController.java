package data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import model.Item;
import model.compressed.ItemC;
import model.uncompressed.ItemNC;

public interface DataController {

  /**
   * Returns the structure of the path as an {@link Item} (it does not matter if it is compressed or
   * uncompressed)
   * <p>
   * The objects returned has no data inside regarding the data of the file, it should be read
   * apart.
   *
   * @param path The path to read from
   * @return The structure of the path as an {@link Item}
   */
  ItemNC getNonCompressedItem(String path) throws IOException;

  ItemC getCompressedItem(String path) throws IOException;

  void saveCompress(byte[] data, String path);

  void saveUncompress(byte[] data, String path);

}
