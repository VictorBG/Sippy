package prop.datos.model;

import java.io.File;
import prop.utils.FileUtils;

/**
 * Author: Sergio Vázquez
 */
public class ItemBO {

  private File file;

  public ItemBO(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
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
