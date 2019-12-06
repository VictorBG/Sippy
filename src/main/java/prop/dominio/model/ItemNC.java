package prop.dominio.model;

import prop.dominio.model.uncompressed.FilePpm;
import prop.dominio.model.uncompressed.FileTxt;
import prop.dominio.model.uncompressed.Folder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import prop.utils.FileUtils;

/**
 * @class ItemNC
 * @brief Representation of a non compressed item. Used mainly for the zip operation.
 * Author: Sergio Vazquez
 */
public abstract class ItemNC {

  private File file;
  /**
   * @brief Constructora que assigna un path.
   *\pre path correcte
   * \post Instancia un ItemNC
   */
  public ItemNC(String path) {
    this(new File(path));
  }

  /**
   * @brief Constructora que assigna un file.
   *\pre file correcte
   * \post Instancia un ItemNC
   */
  public ItemNC(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  /**
   * @brief Operació abstracta que retorna la mida d’un fitxer i si és una folder retorna
   * la suma de mides de tots els seus arxius.
   *\pre cert
   * \post retorna la mida
   */
  public abstract double getSize();


  /**
   * @brief Operació abstracta que retorna els ítems.
   *\pre cert
   * \post Retorna una llista d'items
   */
  public abstract List<prop.dominio.model.uncompressed.File> getItems();

  /**
   * @brief Si el parametre és una folder, la crea, en cas contrari segons la seva extensió crea una filePpm o una fileTxt.
   *\pre file existeix
   * \post Retorna un ItemNC
   */
  public static ItemNC create(File file) throws UnsupportedEncodingException {
    if (file.isDirectory()) {
      return new Folder(file);
    } else {
      String extension = FileUtils.getFileExtension(file.getPath());
      switch (extension) {
        case "ppm":
          return new FilePpm(file);
        case "txt":
          return new FileTxt(file);
        default:
          throw new UnsupportedEncodingException();
      }
    }
  }

}
