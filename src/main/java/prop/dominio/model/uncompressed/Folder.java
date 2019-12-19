package prop.dominio.model.uncompressed;

import prop.dominio.model.ItemNC;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: Victor Blanco
 * @class Folder
 * @brief Classe del model per a mantener la información dels Folder
 *
 */
public class Folder extends ItemNC {

  private List<ItemNC> items;

  /**
   * @brief Constructora
   *
   * \pre path valid
   * \post Nova instancia de Folder amb el path indicat i la llista dels items generada
   *
   * @param path
   */
  public Folder(String path) {
    super(path);
  }


  public void setItems(List<ItemNC> items) {
    this.items = items;
  }

  /**
   * @brief Retorna la llista de items que hi ha al folder (si hi han mes folders, repeteix aquesta accio)
   *
   * \pre cert
   * \post llista d'items
   */
  @Override
  public List<File> getItems() {
    List<File> result = new ArrayList<>();
    items.forEach(item -> result.addAll(item.getItems()));
    return result;
  }

}
