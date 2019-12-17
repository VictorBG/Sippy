package prop.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @class FileUtils
 * @brief different utils for reading a file
 *     Author: Sergio Vazquez
 */
public class FileUtils {


  /**
   * @brief Llegeix un arxiu d'un path especific i el retorna com a vector de bytes
   *     \pre file existeix
   *     \post Retorna la file en forma de vector de bytes
   */
  public static byte[] readFile(File file) throws IOException {
    return Files.readAllBytes(file.toPath());
  }

  /**
   * @brief Cambia l'extensio de l'arxiu passat al path per l'extensió del aparametre
   *     \pre path i extensio correctes
   *     \post Es retorna el nou path amb la nova extensió
   */
  public static String changeExtension(String path, String extension) {
    File file = new File(path);
    if (file.isDirectory()) {
      return path + "." + extension;
    } else {
      return path.replace(getFileExtension(path), extension);
    }
  }

  /**
   * @brief Retorna la extensió del path passat per parametre
   *     \pre path correcte
   *     \post Extensio del path
   */
  public static String getFileExtension(String path) {
    int lastIndexOf = path.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return "";
    }
    return path.substring(lastIndexOf + 1);
  }

  public static void openFile(File f) throws UnsupportedOperationException, IOException {
    if (!Desktop.isDesktopSupported()) {
      throw new UnsupportedOperationException("Desktop is not supported");
    }

    if (!f.exists()) {
      throw new FileNotFoundException();
    }

    Desktop.getDesktop().open(f);

  }
}
