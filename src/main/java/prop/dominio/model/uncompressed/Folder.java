package prop.dominio.model.uncompressed;

import prop.dominio.model.ItemNC;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @class Folder
 * @brief Classe del model per a mantener la información dels Folder
 * Author: Victor Blanco
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

  /**
   * @brief Afegeix l’itemNC indicat per parametre a la llista d’items, en cas que sigui el primer element, crea la llista
   *
   * \pre cert
   * \post L'inserta a la llista
   *
   * Inserta un item a la llista d'ítems.
   *
   * Si l'ítem és nul, el crea abans d'afegir un nou ítem.
   *
   * @param itemNC item to add
   */
  public void addItem(ItemNC itemNC) {
    if (items == null) {
      items = new ArrayList<>();
    }
    items.add(itemNC);
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

//  /**
//   *
//   * @brief metode usat per a generar els elements ItemNC que s’afegeixen al parametre ítem i que están continguts a la carpeta del file. Es cridat desde el constructor i serveix per tenir sempre en forma de llista tots els ítems continguts a la carpeta
//   *
//   * \pre cert
//   * \post llista generada del items del folder
//   *
//   * Populates the {@link #items} list to have the structure of the {@link #getFile()} folder.
//   * <p>
//   * It will iterate and create every object inside the folder specified by the file item, if an
//   * item inside is another {@link Folder}, it will be populated when creating the folder item, thus
//   * giving us the folder structure in a recursively way.
//   *
//   * @throws UnsupportedEncodingException if there is a file that is not supported by the program.
//   */
//  private void populateList() throws UnsupportedEncodingException {
//    for (java.io.File f : Objects.requireNonNull(getFile().listFiles())) {
//      addItem(ItemNC.create(f));
//    }
//  }
}
