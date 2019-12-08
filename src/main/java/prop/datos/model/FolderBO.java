package prop.datos.model;

import java.io.File;
import java.util.ArrayList;

/**
 * Author: Sergio Vázquez
 */
public class FolderBO extends ItemBO {

  private ArrayList<ItemBO> items;

  public FolderBO(File file) {
    super(file);
  }

  public ArrayList<ItemBO> getItems() {
    return items;
  }

  public void addItem(ItemBO itemBO) {
    if (items == null) {
      items = new ArrayList<>();
    }
    items.add(itemBO);
  }
}
