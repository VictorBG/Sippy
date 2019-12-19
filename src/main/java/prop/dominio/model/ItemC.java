package prop.dominio.model;

import prop.algorithms.Algorithm;
import java.io.File;

/**
 * Author: Sergio Vazquez
 *
 * @class ItemC
 * @brief A representation of a compressed item. Note: it is used mainly for the zip operation,
 *     for the unzip operation it is merely used to save the file object (algorithm cannot be
 *     set due the file has not been parsed, neither its headers).
 */
public class ItemC {

  private String path;
  private Algorithm method;     // method used to compress
  private long size;

  public ItemC(String path) {
    this.path = path;
  }

  public Algorithm getMethod() {
    return method;
  }

  public void setMethod(Algorithm method) {
    this.method = method;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}
