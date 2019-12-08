package prop.datos.model;

import java.io.File;
import prop.utils.FileUtils;

/**
 * Author: Sergio Vázquez
 *
 * Business object used to store the structure of a file of the system with some
 * helper methods.
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
