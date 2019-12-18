package prop.presentacion;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import prop.algorithms.Algorithm;
import prop.datos.exception.UnsupportedOutputDirectoryPathname;
import prop.dominio.Unzip;
import prop.dominio.Zip;
import prop.dominio.model.Statistics;
import prop.utils.Log;
import prop.utils.StatisticsUtils;

/**
 * Author: Victor Blanco
 */
public class InterfaceController {

  private final Log log = new Log();

  private InterfacePanelContract contract;

  public InterfaceController(InterfacePanelContract contract) {
    this.contract = contract;
  }

  public void onCompressClick(String path, String outputPath, int algorithmOptionSelected) {

    try {
      Zip zip = new Zip(path, outputPath, Algorithm.valueOf((byte) algorithmOptionSelected));
      zip.execute();
      showStatistics(zip.getResult());
    } catch (Exception ex) {
      handleZipException(ex);
    }
  }

  public void onDecompressClick(String path, String outputPath) {
    try {
      new Unzip(path, outputPath).execute();
    } catch (IOException ex) {
      handleUnzipException(ex);
    }
  }

  private void handleUnzipException(IOException exception) {
    log.log("InterfaceControllerUnzipException", exception);
  }

  private void handleZipException(Exception exception) {
    log.log("InterfaceControllerZipException", exception);

    if (exception instanceof UnsupportedOutputDirectoryPathname) {
      contract.showAlert("The output path is not a valid path", "Output path error");
    }

    if (exception instanceof UnsupportedOperationException) {
      contract.showAlert(exception.getMessage(), "The operation is not supported");
    }

    // TODO: Do all
  }

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
