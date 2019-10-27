package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;

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

  public static String readFileAsString(File file) throws IOException {
    StringBuilder sb = new StringBuilder();
    Files.lines(file.toPath()).forEach((s) -> sb.append(s).append("\n"));
    return sb.toString();
  }

  public static void save(final byte[] s, String file) {
    try (PrintWriter out = new PrintWriter(new String(s))) {
      out.print(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static String changeExtension(String path, String extension) {
    String[] sp = path.split("\\.");
    sp[sp.length - 1] = extension;
    return String.join(".", sp);
  }

  public static String getFileExtension(String path) {
    int lastIndexOf = path.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return "";
    }
    return path.substring(lastIndexOf);
  }
}
