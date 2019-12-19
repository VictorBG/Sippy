package prop.dominio.model;

/**
 * Author: Sergio Vazquez
 *
 * @class Statistics
 * @brief Statistics for the zip and unzip operation
 */
public class Statistics {

  private double initialSize;
  private double finalSize;
  private long elapsedTime;
  private long initialTime;

  /*Constructora*/
  public Statistics(double initialSize) {
    this.initialSize = initialSize;
    initialTime = System.currentTimeMillis();
  }

  /**
   * @brief Atura el contador de temps al acabar la compressio/descompressio
   *     \pre cert
   *     \post atura el contador de temps
   */
  public void stopTimer() {
    elapsedTime = System.currentTimeMillis() - initialTime;
  }

  public double getInitialSize() {
    return initialSize;
  }

  public double getFinalSize() {
    return finalSize;
  }

  public void setFinalSize(double finalSize) {
    this.finalSize = finalSize;
  }

  public long getElapsedTime() {
    return elapsedTime;
  }
}
