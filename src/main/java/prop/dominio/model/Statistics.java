package prop.dominio.model;

/**
 * @class Statistics
 * @brief Statistics for the zip and unzip operation
 * Author: Sergio Vazquez

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

  /*called from zip when it finishes compressing*/
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
