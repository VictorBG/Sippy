package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileIO {

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

  public static void save(final String s, String file) {
    try (PrintWriter out = new PrintWriter(s)) {
      out.print(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static String changeExtension(String path, String newExtension) {
    String[] p = path.split("\\.");
    return Stream.of(p).limit(p.length - 1).collect(Collectors.joining(".")) + "." + newExtension;
  }
}
