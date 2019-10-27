package data;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.xml.crypto.Data;
import model.uncompressed.Folder;
import model.uncompressed.ItemNC;

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

  public DataController getDataController() {
    // TODO: Should be initialized only once
    return new DataControllerImpl();
  }
}
