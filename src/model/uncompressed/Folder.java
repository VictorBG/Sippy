package model.uncompressed;

import algorithms.Algorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.compressed.ItemC;
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

  @Override
  public ItemC compress(Algorithm algorithm) {
    // FIXME: Collect is not correct due toString just returns the iId of the object
    return new ItemC(buildEncodedPath(),
        items.stream()
            .map(ItemNC::compress)
            .collect(Collectors.toList())
            .toString()
            .getBytes());
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
}
