package prop.datos.model;

import java.io.File;
import prop.utils.FileUtils;

/**
 * Author: Sergio Vázquez
 *
 * Business object utilitzat per emmagatzemar l'estructura d'un fitxer
 * del sistema amb alguns mètodes auxiliars.
 */
public class ItemBO {

  private File file;

  public ItemBO(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public long getSize() {
    return file.length();
  }

  public String getPath() {
    return file.getPath();
  }

  public String getExtension() {
    return FileUtils.getFileExtension(getPath());
  }
}
