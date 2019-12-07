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
 */
public class FilesController {

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

  private void populateFolder(FolderBO folderBO) throws IOException {
    for (File f : Objects.requireNonNull(folderBO.getFile().listFiles())) {
      folderBO.addItem(getFile(f.getAbsolutePath()));
    }
  }

  private boolean isExtensionValid(String extension) {
    return extension.equals(DEFAULT_ENCODING_EXTENSION) || extension.equals(ENCODING_EXTENSION_PPM)
        || extension.equals(ENCODING_EXTENSION_TXT);
  }

}
