package prop.datos;

import static prop.utils.Constants.DEFAULT_ENCODING_EXTENSION;
import static prop.utils.Constants.ENCODING_EXTENSION_PPM;
import static prop.utils.Constants.ENCODING_EXTENSION_TXT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import prop.datos.model.FolderBO;
import prop.datos.model.ItemBO;
import prop.utils.FileUtils;

/**
 * Author: Sergio Vázquez
 *
 * Controller for the file operations.
 */
public class FilesController {

  /**
   * Reads a path and returns an {@link ItemBO} containing the structure of
   * the file referenced by the path. If it is a folder it returns a {@link FolderBO}
   * that contains the structure of that folder and populates it using
   * the {@link #populateFolder(FolderBO)} method
   *
   * @param path Path to read from
   *
   * @return {@link ItemBO} with the structure of the file referenced by the path
   *
   * @throws FileNotFoundException        If the file could not be found
   * @throws UnsupportedEncodingException If the extension of the file is not supported by the
   *                                      system
   */
  public ItemBO getFile(String path) throws IOException {
    String extension = FileUtils.getFileExtension(path);

    File file = new File(path);

    if (!file.exists()) {
      throw new FileNotFoundException("Couldn't find the file");
    }

    if (file.isDirectory()) {
      FolderBO folderBO = new FolderBO(file);
      populateFolder(folderBO);
      return folderBO;
    } else {
      if (!isExtensionValid(extension)) {
        throw new UnsupportedEncodingException("Sippy does not support this file type yet");
      }
      return new ItemBO(file);
    }
  }

  /**
   * Populates the {@link FolderBO} with every item inside the folder referenced by the path
   * the folder has. It lists every file, and for every file it returns an {@link ItemBO} using
   * {@link #getFile(String)}. If there is another folder inside, it will repeat this process.
   *
   * @param folderBO Item to populate
   */
  private void populateFolder(FolderBO folderBO) throws IOException {
    for (File f : Objects.requireNonNull(folderBO.getFile().listFiles())) {
      folderBO.addItem(getFile(f.getAbsolutePath()));
    }
  }

  /**
   * Returns if the extension is a supported extension of the system.
   *
   * @param extension Extension to check
   *
   * @return If the extension is supported
   */
  private boolean isExtensionValid(String extension) {
    return extension.equals(DEFAULT_ENCODING_EXTENSION)
        || extension.equals(ENCODING_EXTENSION_PPM)
        || extension.equals(ENCODING_EXTENSION_TXT);
  }

}
