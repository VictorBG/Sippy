package model;

import java.util.ArrayList;

public class Folder extends ItemNC {

  private ArrayList<ItemNC> items;

  public ArrayList<ItemNC> getItems() {
    return items;
  }

  public void setItems(ArrayList<ItemNC> items) {
    this.items = items;
  }
}
