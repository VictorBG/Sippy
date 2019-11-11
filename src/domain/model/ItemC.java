package domain.model;

import domain.algorithms.Algorithm;
import java.io.File;

/**
 * Author: Victor
 *
 * A representation of a compressed item. Note: it is used mainly for the zip operation,
 * for the unzip operation it is merely used to save the file object (algorithm cannot be
 * set due the file has not been parsed, neither its headers).
 */
public class ItemC {

  private File file;
  private Algorithm method;     // method used to compress

  public ItemC(File file){
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public Algorithm getMethod() {
    return method;
  }

  public void setMethod(Algorithm method) {
    this.method = method;
  }

  public double getSize() {
    return file.length();
  }
}
