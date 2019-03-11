import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import static javax.swing.JOptionPane.getRootFrame;

public class Menu {
    private JPanel Menu;
    private JButton wczytajPlikButton;
    private JButton rozmazslaboButton;
    private JButton rozmazsrednioButton;
    private JButton rozmazmocnoButton;
    private JButton wyostrzsrednioButton;
    private JButton wyostrzslaboButton;
    private JButton wyostrzmocnoButton;
    private JButton obrazCzarnoBialyButton;
    private JLabel rozmaz;
    private JLabel wyostrz;
    private JButton ZAPISZOBRAZButton;
    private JLabel tytulobrazka;
    private JButton wyjdzButton;
    private JButton zmienSkaleButton;
    private JSlider sliderKontrast;
    private JLabel Kontrast;
    private JLabel Skalowanie;
    private JSlider sliderskalowanie;
    private JButton zmienKotrastButton;
    private JLabel jasnosc;
    private JSlider slider1;
    private JButton zmienJasnoscButton;
    private JRadioButton gaussarozmazRadioButton;
    private JRadioButton medianowerozmazRadioButton;
    private JRadioButton bilateralnerozmazRadioButton;
    private JRadioButton gaussawyostrzRadioButton;
    private JRadioButton medianowewyostrzRadioButton;
    private JRadioButton bilateralnewyostrzRadioButton;
    private JFrame jFrame = new JFrame();
    private String filepath;
    private String filename;

    private Menu() {

        wczytajPlikButton.addActionListener(e -> chooseImage());
        ZAPISZOBRAZButton.addActionListener(e -> saveImage());

        rozmazslaboButton.addActionListener(e -> OpenCV.addGaussianBlur(5, true, filename, jFrame, 0, 0));
        rozmazsrednioButton.addActionListener(e -> OpenCV.addGaussianBlur(21, true, filename, jFrame, 0, 0));
        rozmazmocnoButton.addActionListener(e -> OpenCV.addGaussianBlur(71, true, filename, jFrame, 0, 0));

        wyostrzslaboButton.addActionListener(e -> OpenCV.addGaussianBlur(0, false, filename, jFrame, 1.5, -0.5));
        wyostrzsrednioButton.addActionListener(e -> OpenCV.addGaussianBlur(41, false, filename, jFrame, 2.0, -1));
        wyostrzmocnoButton.addActionListener(e -> OpenCV.addGaussianBlur(101, false, filename, jFrame, 2.5, -1.5));

        wyjdzButton.addActionListener(e -> System.exit(0));

        obrazCzarnoBialyButton.addActionListener(e -> OpenCV.readGrayscaleImage(filename, jFrame));

        gaussarozmazRadioButton.addActionListener(actionEvent -> {
            gaussarozmazRadioButton.setSelected(true);
            medianowerozmazRadioButton.setSelected(false);
            bilateralnerozmazRadioButton.setSelected(false);
        });
        medianowerozmazRadioButton.addActionListener(actionEvent -> {
            medianowerozmazRadioButton.setSelected(true);
            gaussarozmazRadioButton.setSelected(false);
            bilateralnerozmazRadioButton.setSelected(false);
        });
        bilateralnerozmazRadioButton.addActionListener(actionEvent -> {
            bilateralnerozmazRadioButton.setSelected(true);
            medianowerozmazRadioButton.setSelected(false);
            gaussarozmazRadioButton.setSelected(false);
        });

        gaussawyostrzRadioButton.addActionListener(actionEvent -> {
            gaussawyostrzRadioButton.setSelected(true);
            medianowewyostrzRadioButton.setSelected(false);
            bilateralnewyostrzRadioButton.setSelected(false);
        });
        medianowewyostrzRadioButton.addActionListener(actionEvent -> {
            medianowewyostrzRadioButton.setSelected(true);
            gaussawyostrzRadioButton.setSelected(false);
            bilateralnewyostrzRadioButton.setSelected(false);
        });
        bilateralnewyostrzRadioButton.addActionListener(actionEvent -> {
            bilateralnewyostrzRadioButton.setSelected(true);
            medianowewyostrzRadioButton.setSelected(false);
            gaussawyostrzRadioButton.setSelected(false);
        });
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(getRootFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filepath = chooser.getSelectedFile().getAbsolutePath();
            filename = chooser.getSelectedFile().getName();
            System.out.println("You chose to open this file: " + filename);
            jFrame.setTitle(chooser.getSelectedFile().getName());
            tytulobrazka.setText("Wczytany obraz: " + chooser.getSelectedFile().getName());
            if (OpenCV.readImage(filepath, filename, jFrame)) {
                System.out.println("File successfully loaded");
                enableElements(true);
            } else {
                enableElements(false);
            }
        }
    }

    private void saveImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showSaveDialog(getRootFrame());
        if (JFileChooser.APPROVE_OPTION == returnVal) {
            Mat img = Imgcodecs.imread(filename);
            File fileToSave = chooser.getSelectedFile();
            filepath = fileToSave.getAbsolutePath();

            System.out.println("Selected the file!");
            String extension = "";
            int i = filepath.lastIndexOf('.');

            //sprawdzamy czy jest jakies rozszerzenie
            if (i > 0) {
                extension = filepath.substring(i);
            }

            //jesli brak rozszerzenia lub niepoprawne to dodaj odpowiednie rozszerzenie
            if (!extension.equals(".jpg") && !extension.equals(".png")) {
                i = filename.lastIndexOf('.');
                if (i > 0) {
                    extension = filename.substring(i);
                }
                filepath = filepath + extension;
            }

            System.out.println("Save as file: " + filepath);
            Imgcodecs.imwrite(filepath, img);
        } else {
            System.out.println("The user cancelled the operation");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("IMGEditor");
        frame.setContentPane(new Menu().Menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    private void enableElements(boolean view) {
        obrazCzarnoBialyButton.setEnabled(view);
        wczytajPlikButton.setEnabled(view);
        rozmazslaboButton.setEnabled(view);
        rozmazsrednioButton.setEnabled(view);
        rozmazmocnoButton.setEnabled(view);
        wyostrzsrednioButton.setEnabled(view);
        wyostrzmocnoButton.setEnabled(view);
        rozmaz.setEnabled(view);
        wyostrz.setEnabled(view);
        ZAPISZOBRAZButton.setEnabled(view);
        tytulobrazka.setEnabled(view);
        wyostrzslaboButton.setEnabled(view);
    }
}
