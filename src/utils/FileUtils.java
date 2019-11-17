package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Author: Sergio Vazquez
 */
public class FileUtils {

  public static final String DEFAULT_ENCODING_EXTENSION = "sippy";

  /**
   * Reads a file from the specified path and returns it as a vector of bytes
   *
   * @param file The file to read
   * @return The file as a stream of bytes
   */
  public static byte[] readFile(File file) throws IOException {
    return Files.readAllBytes(file.toPath());
  }

  /**
   * Changes the extension of the given {@param path} to the {@param extension}. If the path is a
   * folder it will just append the extension.
   *
   * @param path      Path to change the extension.
   * @param extension New extension
   * @return Path with the nex extension
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
   * Returns the file extension of the given {@param path}. If it does not have it returns and empty
   * string.
   *
   * @param path Path to get extension
   * @return Extension of the path
   */
  public static String getFileExtension(String path) {
    int lastIndexOf = path.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return "";
    }
    String s = path.substring(lastIndexOf + 1);
    return s;
  }
}
