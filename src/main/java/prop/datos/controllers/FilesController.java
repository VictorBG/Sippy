package prop.datos.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import prop.datos.model.FolderBO;
import prop.datos.model.ItemBO;

/**
 * Author: Sergio Vázquez
 *
 * Controller for the file operations.
 */
public interface FilesController {

  /**
   * Reads a path and returns an {@link ItemBO} containing the structure of
   * the file referenced by the path. If it is a folder it returns a {@link FolderBO}
   * that contains the structure of that folder populated.
   *
   * @param path Path to read from
   *
   * @return {@link ItemBO} with the structure of the file referenced by the path
   *
   * @throws FileNotFoundException        If the file could not be found
   * @throws UnsupportedEncodingException If the extension of the file is not supported by the
   *                                      system
   */
  ItemBO getFile(String path) throws IOException;
}
