package data;

import algorithms.Algorithms;
import algorithms.LZ78;

public final class FactoryData {

  // Lazy init + thread safe
  private static class FactoryDataWrapper {

    static FactoryData INSTANCE = new FactoryData();
  }


  public static FactoryData getInstance() {
    return FactoryDataWrapper.INSTANCE;
  }

  private FactoryData() {
    // Prevent instantiation outside
  }

}
