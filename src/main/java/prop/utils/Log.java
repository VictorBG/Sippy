package prop.utils;

public class Log {

  private static final boolean DEBUG_MODE = true;

  public void log(String tag, Exception exc) {
    logInternal(tag, exc.getMessage());
    exc.printStackTrace();

  }

  public void log(String tag, String message) {
    logInternal(tag, message);
  }

  private void logInternal(String tag, String message) {
    if (DEBUG_MODE) {
      System.out.println("[" + System.currentTimeMillis() + "] " + tag + " : " + message);
    }
  }
}
