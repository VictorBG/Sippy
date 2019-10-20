package model;

public class Statistics {

  private String initialPath;
  private String finalPath;
  private double initialSize;
  private double finalSize;
  private long elapsedTime;
  private long initialTime;

  public Statistics(String initialPath, double initialSize) {
    this.initialPath = initialPath;
    this.initialSize = initialSize;
  }

  public void startTimer() {
    initialTime = System.currentTimeMillis();
  }

  public void stopTimer() {
    elapsedTime = System.currentTimeMillis() - initialTime;
  }

  public String getInitialPath() {
    return initialPath;
  }

  public void setInitialPath(String initialPath) {
    this.initialPath = initialPath;
  }

  public String getFinalPath() {
    return finalPath;
  }

  public void setFinalPath(String finalPath) {
    this.finalPath = finalPath;
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
