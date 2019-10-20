package model;

public abstract class File extends ItemNC {

  private String path;
  private double size;

  public File(java.io.File file) {
    this.path = file.toPath().toString();
    this.size = file.length();
  }

  public File(String path, float size) {
    this.path = path;
    this.size = size;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public double getSize() {
    return size;
  }

  public void setSize(double size) {
    this.size = size;
  }
}
