package model;

public abstract class Item {

  private String path;
  private byte[] data;

  public Item(String path) {
    this.path = path;
  }

  public Item(String path, byte[] data) {
    this.path = path;
    this.data = data;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getSize() {
    if (data == null) {
      return 0;
    }
    return data.length;
  }
}
