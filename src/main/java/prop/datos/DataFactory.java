package prop.datos;

import prop.datos.controllers.FilesController;
import prop.datos.controllers.StreamController;
import prop.datos.controllers.impl.FilesControllerImpl;
import prop.datos.controllers.impl.StreamControllerImpl;

/**
 * Author: Sergio Vázquez
 *
 * Singleton per obtenir els controladors de capa de dades.
 */
public final class DataFactory {

  /**
   * Inicialització "lazy" del singleton.
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

  private FilesController filesController = new FilesControllerImpl();
  private StreamController streamController = new StreamControllerImpl();


  public FilesController getFilesController() {
    return filesController;
  }

  public StreamController getStreamController() {
    return streamController;
  }
}
