package prop.dominio.model;

import java.util.List;

/**
 * @class ItemNC
 * @brief Representation of a non compressed item. Used mainly for the zip operation.
 *     Author: Sergio Vazquez
 */
public abstract class ItemNC {

  private String path;
  private long size;

  /**
   * @brief Constructora que assigna un path.
   *     \pre path correcte
   *     \post Instancia un ItemNC
   */
  public ItemNC(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setSize(long size) {

    this.size = size;
  }

  public long getSize() {
    return size;
  }

  /**
   * @brief Operació abstracta que retorna els ítems.
   *     \pre cert
   *     \post Retorna una llista d'items
   */
  public abstract List<prop.dominio.model.uncompressed.File> getItems();

}
