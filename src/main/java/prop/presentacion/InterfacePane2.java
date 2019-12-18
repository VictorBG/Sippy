package prop.presentacion;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import java.awt.CardLayout;
import java.awt.Font;
import java.nio.file.Paths;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import prop.algorithms.Algorithm;
import prop.utils.Constants;
import prop.utils.FileUtils;

public class InterfacePane2 extends JFrame implements InterfacePanelContract {

  private JTextField pathField;
  private JTextField decPathField;
  private String path;
  private JPanel mainCardPane;
  private JPanel manAutoRadioButtonsPanel;
  private JPanel manAutoCardPane;
  private JPanel compressPanel;
  private JPanel decompressPanel;
  private JButton explorerButton;
  private JTextField fileNameField;
  private JTextField fileNameFieldDec;
  private JLabel outputPathLabelDec;

  private InterfaceController interfaceController;

  public static void start() {
    new InterfacePane2();
  }

  private InterfacePane2() {
    interfaceController = new InterfaceController(this);

    compressPanel = new JPanel();
    decompressPanel = new JPanel();
    createMainPage();
    createCompressPanel();
    createDecompressPanel();
    setVisible(true);
  }

  private void createMainPage() {
    mainCardPane = new JPanel();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 423, 400);
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel sippyLabel = new JLabel("Sippy");
    sippyLabel.setFont(new Font("SimSun-ExtB", Font.PLAIN, 31));
    sippyLabel.setBounds(10, 0, 101, 53);
    contentPane.add(sippyLabel);

    JLabel welcomeLabel = new JLabel("Welcome to Sippy, your favorite compressor");
    welcomeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
    welcomeLabel.setBounds(10, 58, 350, 19);
    contentPane.add(welcomeLabel);

    JRadioButton rdbtnCompress = new JRadioButton("Compress");
    JRadioButton rdbtnDecompress = new JRadioButton("Decompress");
    ButtonGroup compDecompRButtonsGroup = new ButtonGroup();

    rdbtnCompress.setFont(new Font("Calibri", Font.PLAIN, 14));
    rdbtnCompress.setBounds(10, 84, 109, 23);
    contentPane.add(rdbtnCompress);

    rdbtnDecompress.setFont(new Font("Calibri", Font.PLAIN, 14));
    rdbtnDecompress.setBounds(121, 84, 109, 23);
    contentPane.add(rdbtnDecompress);

    createListenersForRadioButtons("decompressPanel", mainCardPane, rdbtnDecompress);
    createListenersForRadioButtons("compressPanel", mainCardPane, rdbtnCompress);

    compDecompRButtonsGroup.add(rdbtnDecompress);
    compDecompRButtonsGroup.add(rdbtnCompress);

    mainCardPane.setBounds(10, 104, 386, 250);
    contentPane.add(mainCardPane);
    mainCardPane.setLayout(new CardLayout(0, 0));

    ((CardLayout) mainCardPane.getLayout()).show(mainCardPane, null);
    // We add the three panels to cardPane
    JPanel voidPanel = new JPanel();
    mainCardPane.add(voidPanel, "voidPane");
    mainCardPane.add(decompressPanel, "decompressPanel");
    decompressPanel.setVisible(false);
    decompressPanel.setLayout(null);
    mainCardPane.add(compressPanel, "compressPanel");
    compressPanel.setVisible(false);
    compressPanel.setLayout(null);

  }

  private void createListenersForRadioButtons(String panel, JPanel cardPane, JRadioButton rb) {
    rb.addActionListener(e -> ((CardLayout) cardPane.getLayout()).show(cardPane, panel));
  }

  private void addActionToRadioButtons(JRadioButton radioButton) {
    radioButton.addActionListener(e -> fileNameField
        .setText(FileUtils.changeExtension(path, Constants.DEFAULT_ENCODING_EXTENSION)));
  }

  private void createManualCompressPanel() {
    JPanel manualCompressPanel = new JPanel();

    JRadioButton rdbtnLz78 = new JRadioButton("LZ78");
    JRadioButton rdbtnLzss = new JRadioButton("LZSS");
    JRadioButton rdbtnLzw = new JRadioButton("LZW");
    JRadioButton rdbtnJpeg = new JRadioButton("JPEG");

    addActionToRadioButtons(rdbtnLz78);
    addActionToRadioButtons(rdbtnLzss);
    addActionToRadioButtons(rdbtnLzw);
    addActionToRadioButtons(rdbtnJpeg);

    ButtonGroup algorithmsrbsGroup = new ButtonGroup();

    JLabel algorithmsLabel = new JLabel("Algorithm to use");
    algorithmsLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
    algorithmsLabel.setBounds(0, 0, 360, 20);
    manualCompressPanel.add(algorithmsLabel);

    rdbtnLz78.setFont(new Font("Calibri", Font.PLAIN, 14));
    rdbtnLz78.setBounds(0, 21, 69, 23);
    manualCompressPanel.add(rdbtnLz78);

    rdbtnLzss.setFont(new Font("Calibri", Font.PLAIN, 14));
    rdbtnLzss.setBounds(86, 21, 69, 23);
    manualCompressPanel.add(rdbtnLzss);

    rdbtnLzw.setFont(new Font("Calibri", Font.PLAIN, 14));
    rdbtnLzw.setBounds(170, 21, 83, 23);
    manualCompressPanel.add(rdbtnLzw);

    rdbtnJpeg.setFont(new Font("Calibri", Font.PLAIN, 14));
    rdbtnJpeg.setBounds(255, 21, 83, 23);
    manualCompressPanel.add(rdbtnJpeg);

    algorithmsrbsGroup.add(rdbtnLzw);
    algorithmsrbsGroup.add(rdbtnJpeg);
    algorithmsrbsGroup.add(rdbtnLzss);
    algorithmsrbsGroup.add(rdbtnLz78);

    manAutoCardPane.add(manualCompressPanel, "manualPane");
    manualCompressPanel.setVisible(false);
    manualCompressPanel.setLayout(null);

    JLabel outputPathLabel = new JLabel("Output path");
    outputPathLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
    outputPathLabel.setBounds(0, 50, 360, 20);
    manualCompressPanel.add(outputPathLabel);

    JButton zipButton = new JButton("Sippejar");
    zipButton.setBounds(287, 117, 89, 23);
    manualCompressPanel.add(zipButton);

    fileNameField = new JTextField();
    fileNameField.setBounds(0, 80, 386, 23);
    manualCompressPanel.add(fileNameField);
    fileNameField.setColumns(10);

    zipButton.addActionListener(e -> {
      byte algorithm = -1;
      if (rdbtnLz78.isSelected()) {
        algorithm = 0;
      } else if (rdbtnLzss.isSelected()) {
        algorithm = 3;
      } else if (rdbtnLzw.isSelected()) {
        algorithm = 1;
      } else if (rdbtnJpeg.isSelected()) {
        algorithm = 2;
      }
      interfaceController.onCompressClick(path, fileNameField.getText(), algorithm);
    });
  }

  private void createCompressPanel() {
    manAutoCardPane = new JPanel();
    manAutoCardPane.setBounds(0, 99, 386, 151);
    manAutoCardPane.setLayout(new CardLayout(0, 0));

    compressPanel.add(manAutoCardPane);
    JPanel voidAutoPane = new JPanel();
    manAutoCardPane.add(voidAutoPane, "voidPane");

    JLabel compressPathLabel = new JLabel("Please, select the path:");
    compressPathLabel.setBounds(0, 11, 398, 20);
    compressPanel.add(compressPathLabel);
    compressPathLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

    explorerButton = new JButton("Examinar...");
    explorerButton.setBounds(0, 40, 100, 23);
    addActionToExplorerButton(explorerButton);

    manAutoRadioButtonsPanel = new JPanel(); // contains the 2 radio buttons
    manAutoRadioButtonsPanel.setVisible(false);
    manAutoRadioButtonsPanel.setBounds(0, 70, 236, 23);
    manAutoRadioButtonsPanel.setLayout(null);

    JRadioButton rdbtnManual = new JRadioButton("Manual");
    rdbtnManual.setBounds(0, 0, 109, 23);
    manAutoRadioButtonsPanel.add(rdbtnManual);
    rdbtnManual.setFont(new Font("Calibri", Font.PLAIN, 14));
    createListenersForRadioButtons("manualPane", manAutoCardPane, rdbtnManual);

    JRadioButton rdbtnAutomatic = new JRadioButton("Automatic");
    rdbtnAutomatic.setBounds(111, 0, 109, 23);
    manAutoRadioButtonsPanel.add(rdbtnAutomatic);
    rdbtnAutomatic.setFont(new Font("Calibri", Font.PLAIN, 14));
    createListenersForRadioButtons("autoPanel", manAutoCardPane, rdbtnAutomatic);

    ButtonGroup manualAutorbsGroup = new ButtonGroup();
    manualAutorbsGroup.add(rdbtnAutomatic);
    manualAutorbsGroup.add(rdbtnManual);

    pathField = new JTextField();
    pathField.setEditable(false);
    pathField.setColumns(10);
    pathField.setBounds(114, 36, 272, 30);

    compressPanel.add(explorerButton);
    compressPanel.add(manAutoRadioButtonsPanel);
    compressPanel.add(pathField);

    createManualCompressPanel();
    createAutomaticCompressPanel();
  }

  private void createAutomaticCompressPanel() {
    JPanel autoPanel = new JPanel();
    autoPanel.setLayout(null);
    manAutoCardPane.add(autoPanel, "autoPanel");

    JButton automaticZipButton = new JButton("Sippejar");
    automaticZipButton.setBounds(140, 30, 89, 23);
    autoPanel.add(automaticZipButton);

    // On automatic, as the name says, all of the fields (output name and algorithm are
    // choosen automatically based on the input)
    automaticZipButton.addActionListener(e -> interfaceController
        .onCompressClick(path, path, Algorithm.LZW.getId()));
  }

  private void createDecompressPanel() {
    JButton decBtn = new JButton("UnSippejar");
    decBtn.setVisible(false);
    decBtn.setBounds(281, 216, 105, 23);
    decompressPanel.add(decBtn);

    JLabel lblYouveSelectedDecompress = new JLabel("Please, select the path:");
    lblYouveSelectedDecompress.setFont(new Font("Calibri", Font.PLAIN, 16));
    lblYouveSelectedDecompress.setBounds(0, 11, 408, 20);
    decompressPanel.add(lblYouveSelectedDecompress);

    explorerButton = new JButton("Examinar...");
    explorerButton.setBounds(0, 40, 100, 23);
    decompressPanel.add(explorerButton);

    decPathField = new JTextField();
    decPathField.setEditable(false);
    decPathField.setColumns(10);
    decPathField.setBounds(114, 36, 272, 30);

    decompressPanel.add(decPathField);

    explorerButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser("frame:");
      fileChooser.setFileFilter(new FileNameExtensionFilter(".sippy", "sippy"));
      int selection = fileChooser.showOpenDialog(null);
      if (selection == JFileChooser.APPROVE_OPTION) {
        path = fileChooser.getSelectedFile().getAbsolutePath();
        decPathField.setText(path);
        decBtn.setVisible(true);
        outputPathLabelDec.setVisible(true);
        fileNameFieldDec.setVisible(true);
        fileNameFieldDec.setText(Paths.get(path).getParent().toString());
      }
    });
    decBtn.addActionListener(
        e -> interfaceController.onDecompressClick(decPathField.getText(), fileNameFieldDec.getText()));

    fileNameFieldDec = new JTextField();
    fileNameFieldDec.setBounds(0, 112, 386, 23);
    decompressPanel.add(fileNameFieldDec);
    fileNameFieldDec.setColumns(10);

    outputPathLabelDec = new JLabel("Output directory");
    outputPathLabelDec.setFont(new Font("Calibri", Font.PLAIN, 16));
    outputPathLabelDec.setBounds(0, 81, 376, 20);
    decompressPanel.add(outputPathLabelDec);

    outputPathLabelDec.setVisible(false);
    fileNameFieldDec.setVisible(false);
  }

  private void addActionToExplorerButton(JButton explorerButton) {
    explorerButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser("frame:");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      fileChooser.setFileFilter(new FileNameExtensionFilter(".txt, .ppm", "txt", "ppm"));
      int selection = fileChooser.showOpenDialog(null);
      if (selection == JFileChooser.APPROVE_OPTION) {
        path = fileChooser.getSelectedFile().getAbsolutePath();
        pathField.setText(path);
        manAutoRadioButtonsPanel.setVisible(true);
      }
    });
  }

  @Override
  public void showStatistics(String statistics) {
    JOptionPane.showMessageDialog(mainCardPane, statistics, "Statistics", INFORMATION_MESSAGE);
  }

  @Override
  public void showAlert(String message, String title) {
    JOptionPane.showMessageDialog(mainCardPane, message, title, JOptionPane.ERROR_MESSAGE);
  }
}
