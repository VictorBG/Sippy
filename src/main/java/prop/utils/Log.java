package prop.utils;

import java.text.SimpleDateFormat;
/**
 * @class Log
 * @brief Classe usada per mostrar missatges per la consola a mode de debug
 *     Author: Victor Blanco
 */
public class Log {

  private static final boolean DEBUG_MODE = true;
  private static final boolean SHOW_EXCEPTION_TRACES = true;
  private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");

  public void log(String tag, Exception exc) {
    this.log(tag, exc.getMessage());
  }

  public void log(String tag, String message) {
    this.d(tag, message);
  }

  public void d(String tag, Exception exc) {
    this.d(tag, exc.getMessage());
    if (SHOW_EXCEPTION_TRACES) {
      exc.printStackTrace();
    }
  }

  public void d(String tag, String message) {
    logInternal("\033[1;36mDebug\033[0;36m " + tag + "\u001B[0m", message);
  }

  public void e(String tag, Exception exc) {
    this.e(tag, exc.getMessage());
    if (SHOW_EXCEPTION_TRACES) {
      exc.printStackTrace();
    }
  }

  public void e(String tag, String message) {
    logInternal("\033[1;31mError\033[0;31m " + tag + "\u001B[0m", message);
  }

  private void logInternal(String tag, String message) {
    if (DEBUG_MODE) {
      System.out.print("\n");
      System.out
          .println("[" + format.format(System.currentTimeMillis()) + "] " + tag + " : " + message);
      System.out.print("\n");
    }
  }
}
