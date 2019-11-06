package model.uncompressed;

import algorithms.Algorithm;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.compressed.ItemC;
import utils.Bytes;
import utils.FileUtils;

public class Folder extends ItemNC {

  private List<ItemNC> items;

  public Folder(String path) {
    super(path);
  }

  @Override
  public Algorithm getDefaultAlgorithm() {
    // there is no default algorithm for folder, it depends
    // on the files inside
    return null;
  }

  @Override
  public boolean isAlgorithmSupported(Algorithm algorithm) {
    // None of the algorithms are supported
    return false;
  }

  /**
   * Compress the {@link #items} in a wat that every item is compressed by itself and then they are
   * combined into an array of bytes. Every item is mapped to put its size before the data.
   *
   * @param algorithm The algorithm to use to compress
   * @return
   */
  @Override
  public ItemC compress(Algorithm algorithm) {
    return ItemC.createForFolder(buildEncodedPath(), items.stream()
        .map(ItemNC::compress)
        .map((item) -> Bytes.concat(new byte[]{(byte) item.getSize()}, item.getData()
        ))
        .collect(
            ByteArrayOutputStream::new,
            (b, e) -> {
              try {
                b.write(e);
              } catch (IOException e1) {
                throw new RuntimeException(e1);
              }
            },
            (a, b) -> {
            }).toByteArray());
  }

  public List<ItemNC> getItems() {
    return items;
  }

  public void setItems(ArrayList<ItemNC> items) {
    this.items = items;
  }

  public void addItem(ItemNC itemNC) {
    if (items == null) {
      items = new ArrayList<>();
    }
    items.add(itemNC);
  }

  private String buildEncodedPath() {
    String originalPath = getPath();
    // remove last / if present
    int pos = originalPath.lastIndexOf("\\/");
    if (pos == originalPath.length() - 2) {
      originalPath = originalPath.substring(0, originalPath.length() - 2);
    }

    return originalPath + "." + FileUtils.DEFAULT_ENCODING_EXTENSION;
  }

  /**
   * Decodes the data to put into {@link #items}
   */
  private void decodeData(byte[] data) {
    // TODO: not implemented on first release
  }
}
