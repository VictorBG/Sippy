package domain.streams;

import domain.algorithms.Algorithm;
import domain.model.ItemC;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import utils.FileUtils;

/**
 * Author: Victor Blanco.
 * <p>
 * Unzips a sippy file format extension.
 *
 * <p>
 * It recursively reads the headers and decodes the data using the method that the header indicates.
 * It also preserves the state of the folders before zipping.
 * <p>
 * Note: it does not override files, it will throw an exception if trying to decode a file to an
 * existing file. For now it is not supported and not sure if support will be available for this
 * type of functionality.
 * <p>
 * TODO: I think headerSize is not util and we could remove it from the header. The other fields are
 * mandatory for the correct usage of this stream.
 */
public class UnzipStream {

  private String basePath;

  private DataInputStream dis;

  public UnzipStream(ItemC item) throws IOException {
    if (!FileUtils.DEFAULT_ENCODING_EXTENSION
        .equals(FileUtils.getFileExtension(item.getFile().getAbsolutePath()))) {
      throw new UnsupportedEncodingException();
    }

    this.dis = new DataInputStream(new FileInputStream(item.getFile()));
    basePath = item.getFile().getAbsolutePath().replace(item.getFile().getName(), "");
  }

  public void unzip() throws IOException {
    try {
      int headerSize = getHeaderSize();
      Algorithm algorithm = getAlgorithm();
      int dataSize = getDataSize();
      String name = getName();

      File file = new File(basePath + name);
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();

        byte[] data = new byte[dataSize];
        dis.read(data, 0, dataSize);

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        dos.writeBytes(new String(algorithm.getAlgorithm().decode(data)));
        dos.close();

        unzip();
      } else {
        throw new RuntimeException("File already exists, cannot be overwritten");
      }
    } catch (EOFException ignore) {
      // do not throw EOF exception to presentation layer, it is an exception we expect to get
    } finally {
      dis.close();
    }
  }

  private int getHeaderSize() throws IOException {
    return dis.readInt();
  }

  private Algorithm getAlgorithm() throws IOException {
    return Algorithm.valueOf(dis.readByte());
  }

  private int getDataSize() throws IOException {
    return dis.readInt();
  }

  private String getName() throws IOException {
    int nameSize = dis.readInt();
    byte[] name = new byte[nameSize];
    dis.read(name, 0, nameSize);
    return new String(name);
  }
}
