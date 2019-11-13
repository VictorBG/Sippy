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

  public Statistics(double initialSize) {
    this.initialSize = initialSize;
  }

  public void startTimer() {
    initialTime = System.currentTimeMillis();
  }

  public void stopTimer() {
    elapsedTime = System.currentTimeMillis() - initialTime;
  }

  public double getInitialSize() {
    return initialSize;
  }

  public void setInitialSize(double initialSize) {
    this.initialSize = initialSize;
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

  public void setElapsedTime(long elapsedTime) {
    this.elapsedTime = elapsedTime;
  }
}
