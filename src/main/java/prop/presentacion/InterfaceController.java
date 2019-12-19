package prop.presentacion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import prop.algorithms.Algorithm;
import prop.datos.exception.UnsupportedOutputDirectoryPathname;
import prop.dominio.UnzipTransaction;
import prop.dominio.ZipTransaction;
import prop.dominio.model.Statistics;
import prop.utils.Log;
import prop.utils.StatisticsUtils;

/**
 * Author: Victor Blanco
 *
 * @class InterfaceController
 * @brief Controlador de la interficie que es comunica amb la interficie a través d'un contracte.
 * Serveix per desacoplar la logica de la comunicacio amb la capa de domini de la logica de creacio
 * de la interficie.
 */
public class InterfaceController {

  private final Log log = new Log();

  private InterfacePanelContract contract;

  /**
   * @brief Constructora
   *
   * \pre Contracte no nul
   * \post Crea un objecte InterfaceController
   */
  public InterfaceController(InterfacePanelContract contract) {
    this.contract = contract;
  }

  /**
   * @brief Executa la transaccio de compressio i maneja les excepcions si n'hi han
   *
   * \pre paths no nuls i algorisme id dintre dels ids soportats
   * \post mostra les estadistiques o l'error
   *
   * @param path
   * @param outputPath
   * @param algorithmOptionSelected
   */
  public void onCompressClick(String path, String outputPath, int algorithmOptionSelected) {
    try {
      ZipTransaction zip = new ZipTransaction(path, outputPath, Algorithm.valueOf((byte) algorithmOptionSelected));
      zip.execute();
      showStatistics(zip.getResult());
    } catch (Exception ex) {
      handleZipException(ex);
    }
  }

  /**
   * @brief Executa la transaccio de descompressio i maneja les excepcions si n'hi han
   *
   * \pre paths no nuls
   * \post mostra l'error si n'hi ha
   *
   * @param path
   * @param outputPath
   */
  public void onDecompressClick(String path, String outputPath) {
    try {
      new UnzipTransaction(path, outputPath).execute();
    } catch (IOException ex) {
      handleUnzipException(ex);
    }
  }

  /**
   * @brief Maneja les excepcions de la transacció de descompressio
   *
   * \pre Excepcio no nula
   * \post Informa a la interficie per a mostrar un avís a l'usuari
   *
   * @param exception
   */
  private void handleUnzipException(IOException exception) {
    log.log("InterfaceControllerUnzipException", exception);

    unhandledException(exception);
  }

  /**
   * @brief Maneja les excepcions de la transacció de compressio
   *
   * \pre Excepcio no nula
   * \post Informa a la interficie per a mostrar un avís a l'usuari
   *
   * @param exception
   */
  private void handleZipException(Exception exception) {
    log.log("InterfaceControllerZipException", exception);

    if (exception instanceof UnsupportedOutputDirectoryPathname) {
      contract.showAlert("The output path is not a valid path", "Output path error");
    } else if (exception instanceof UnsupportedOperationException) {
      contract.showAlert(exception.getMessage(), "The operation is not supported");
    } else if (exception instanceof FileNotFoundException) {
      contract.showAlert("Could not find the file specified", "File not found");
    } else if (exception instanceof UnsupportedEncodingException) {
      contract.showAlert(exception.getMessage(), "File format not supported");
    } else {
      unhandledException(exception);
    }

  }

  /**
   * @brief Informa a la interficie per a mostrar un avís a l'usuari de que hi ha hagut un
   * error no controlat
   *
   * \pre Excepcio no nula
   * \post Informa a la interficie per a mostrar un avís a l'usuari de que hi ha hagut un
   *    * error no controlat
   *
   * @param exception
   */
  private void unhandledException(Exception exception) {
    contract.showAlert("An unhandled exception of type " + exception.getClass().getName() +
        " has been found ", "Unhandled exception");
  }

  /**
   * @brief Formateja les dades de les estadistiques i informa a la interficie per mostrarles
   *
   * \pre Estadistiques no nules
   * \post Informa a la interficie de les estadistiques
   *
   */
  private void showStatistics(Statistics statistics) {
    NumberFormat formatter = new DecimalFormat("#0.000");
    double time = statistics.getElapsedTime();
    time = time / 1000;
    double initialSize = statistics.getInitialSize();
    double finalSize = statistics.getFinalSize();
    double compression = ((initialSize - finalSize) / initialSize) * 100.0;

    contract.showStatistics("Elapsed time: "
        + StatisticsUtils.getTime(time)
        + "\n"

        + "Initial size: "
        + StatisticsUtils.getSize(initialSize)
        + "\n"

        + "Compressed size: "
        + StatisticsUtils.getSize(finalSize)
        + "\n"

        + "Compression ratio: "
        + formatter.format(compression)
        + "%\n");
  }
}
