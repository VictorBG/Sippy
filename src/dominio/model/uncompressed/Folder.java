package dominio.model.uncompressed;

import dominio.model.ItemNC;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: Designed in class implemented by Victor and Sergio
 */
public class Folder extends ItemNC {

  private List<ItemNC> items;

  public Folder(String path) throws UnsupportedEncodingException {
    super(path);
    populateList();
  }

  public Folder(java.io.File file) throws UnsupportedEncodingException {
    super(file);
    populateList();
  }

  @Override
  public double getSize() {
    return getItems().stream().map(ItemNC::getSize).reduce(Double::sum).get();
  }

  /**
   * Adds an item to the items list.
   * <p>
   * If items is null it will create it before adding a new item.
   *
   * @param itemNC item to add
   */
  public void addItem(ItemNC itemNC) {
    if (items == null) {
      items = new ArrayList<>();
    }
    items.add(itemNC);
  }

  @Override
  public List<File> getItems() {
    List<File> result = new ArrayList<>();
    items.forEach(item -> result.addAll(item.getItems()));
    return result;
  }

  /**
   * Populates the {@link #items} list to have the structure of the {@link #getFile()} folder.
   * <p>
   * It will iterate and create every object inside the folder specified by the file item, if an
   * item inside is another {@link Folder}, it will be populated when creating the folder item, thus
   * giving us the folder structure in a recursively way.
   *
   * @throws UnsupportedEncodingException if there is a file that is not supported by the program.
   */
  private void populateList() throws UnsupportedEncodingException {
    for (java.io.File f : Objects.requireNonNull(getFile().listFiles())) {
      addItem(ItemNC.create(f));
    }
  }
}
