package prop.presentacion;

public interface InterfacePanelContract {

  void showStatistics(String statistics);

  void showCompressPanel();
  void showDecompressPanel();
  void showAlgorithmsPanel();
  void showOutputPathPanel();

  void showAlert(String message, String title);

}
