package prop.datos;

/**
 * Author: Sergio Vázquez
 *
 * Singleton to get the data layer controllers
 */
public final class DataFactory {

  /**
   * Lazy initialization of the singleton and thread safe
   */
  private static final class SingletonHelper {

    public static final DataFactory INSTANCE = new DataFactory();
  }

  public static DataFactory getInstance() {
    return SingletonHelper.INSTANCE;
  }

  private DataFactory() {
    // Prevent instantiation from outside
  }

  private FilesController filesController = new FilesController();
  private StreamController streamController = new StreamController();


  public FilesController getFilesController() {
    return filesController;
  }

  public StreamController getStreamController() {
    return streamController;
  }
}
