package prop.datos.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import prop.datos.model.FolderBO;
import prop.datos.model.ItemBO;

/**
 * Author: Sergio Vázquez
 *
 * @class FilesController
 * @brief Controlador per les operacions del fitxer.
 */
public interface FilesController {

  /**
   * Llegeix el path i retorna un {@link ItemBO} el qual conté l'estructura
   * del fitxer referenciat pel path. En cas de que sigui una carpeta, retorna
   * un {@link FolderBO} que conté la estructura de la carpeta desplegada.
   *
   * @param path Path del qual es llegeix.
   *
   * @return {@link ItemBO} amb l'estructura del fitxer referenciat pel path.
   *
   * @throws FileNotFoundException        Si el fitxer no s'ha trobat.
   * @throws UnsupportedEncodingException Si l'extensió del fitxer no és admesa pel sistema.
   *
   */
  ItemBO getFile(String path) throws IOException;
}
