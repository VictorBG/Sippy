package domain.model;

/**
 * Author: Sergio Vazquez
 * <p>
 * Statistics for the zip operation.
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
