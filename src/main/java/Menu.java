import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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
    private JSlider sliderskalowanie;
    private JButton zmienKontrastButton;
    private JSlider slider1;
    private JButton zmienJasnoscButton;
    private JRadioButton gaussarozmazRadioButton;
    private JRadioButton medianowerozmazRadioButton;
    private JRadioButton bilateralnerozmazRadioButton;
    private JRadioButton gaussawyostrzRadioButton;
    private JRadioButton medianowewyostrzRadioButton;
    private JRadioButton bilateralnewyostrzRadioButton;
    private JButton COFNIJButton;
    private JComboBox rodzajzapisu;
    private JButton obroc;
    private JRadioButton obrocWPoziomie;
    private JRadioButton obrocWPionie;
    private JFrame jFrame = new JFrame();
    private String filepath;
    private String filename;

    private Menu() {
        wczytajPlikButton.addActionListener(e -> chooseImage());
        ZAPISZOBRAZButton.addActionListener(e -> {
            if (rodzajzapisu.getSelectedItem().equals("Format JPG/PNG")) saveImage();
            else OpenCV.imageToPDF(filename, filepath);
        });

        rozmazslaboButton.addActionListener(e -> {
            if (gaussarozmazRadioButton.isSelected())
                OpenCV.addGaussianBlur(5, true, filename, jFrame, 0, 0);
            else if (medianowerozmazRadioButton.isSelected())
                OpenCV.addMedianBlur(3, true, filename, jFrame, 0, 0);
            else OpenCV.addBilateralBlur(20, true, filename, jFrame, 0, 0);
        });
        rozmazsrednioButton.addActionListener(e -> {
            if (gaussarozmazRadioButton.isSelected())
                OpenCV.addGaussianBlur(21, true, filename, jFrame, 0, 0);
            else if (medianowerozmazRadioButton.isSelected())
                OpenCV.addMedianBlur(5, true, filename, jFrame, 0, 0);
            else OpenCV.addBilateralBlur(60, true, filename, jFrame, 0, 0);
        });
        rozmazmocnoButton.addActionListener(e -> {
            if (gaussarozmazRadioButton.isSelected())
                OpenCV.addGaussianBlur(71, true, filename, jFrame, 0, 0);
            else if (medianowerozmazRadioButton.isSelected())
                OpenCV.addMedianBlur(7, true, filename, jFrame, 0, 0);
            else OpenCV.addBilateralBlur(100, true, filename, jFrame, 0, 0);
        });

        wyostrzslaboButton.addActionListener(e -> {
            if (gaussawyostrzRadioButton.isSelected())
                OpenCV.addGaussianBlur(5, false, filename, jFrame, 1.5, -0.5);
            else if (medianowewyostrzRadioButton.isSelected()) {
                OpenCV.addMedianBlur(3, false, filename, jFrame, 1.5, -0.5);
            } else {
                OpenCV.addBilateralBlur(20, false, filename, jFrame, 1.5, -0.5);
            }
        });
        wyostrzsrednioButton.addActionListener(e ->
        {
            if (gaussawyostrzRadioButton.isSelected())
                OpenCV.addGaussianBlur(41, false, filename, jFrame, 2.0, -1);
            else if (medianowewyostrzRadioButton.isSelected()) {
                OpenCV.addMedianBlur(5, false, filename, jFrame, 2.0, -1);
            } else {
                OpenCV.addBilateralBlur(60, false, filename, jFrame, 2.0, -1);
            }
        });

        wyostrzmocnoButton.addActionListener(e -> {
            if (gaussawyostrzRadioButton.isSelected())
                OpenCV.addGaussianBlur(101, false, filename, jFrame, 2.5, -1.5);
            else if (medianowewyostrzRadioButton.isSelected()) {
                OpenCV.addMedianBlur(7, false, filename, jFrame, 2.5, -1.5);
            } else {
                OpenCV.addBilateralBlur(200, false, filename, jFrame, 2.5, -1.5);
            }
        });

        wyjdzButton.addActionListener(ex -> {
            try {
                Paths.get(filename);
                Files.deleteIfExists(Paths.get(filename));
            } catch (InvalidPathException | NullPointerException e) {
                System.out.println("There is no file");
            } catch (NoSuchFileException e) {
                System.out.println("No such file/directory exists");
            } catch (DirectoryNotEmptyException e) {
                System.out.println("Directory is not empty");
            } catch (IOException e) {
                System.out.println("Invalid permissions");
            }
            System.out.println("Deletion successful");
            if (JOptionPane.showConfirmDialog(null,
                    "Czy na pewno chcesz wyjść? Ewentualne niezapisane zmiany zostaną utracone.", "Zamknąć?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

        });

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
        COFNIJButton.addActionListener(e -> reverseChanges());
        obroc.addActionListener(e -> {
            if (obrocWPionie.isSelected()) OpenCV.addFlip(true, jFrame, filename);
            else OpenCV.addFlip(false, jFrame, filename);
        });

        obrocWPionie.addActionListener(actionEvent -> {
            obrocWPionie.setSelected(true);
            obrocWPoziomie.setSelected(false);
        });

        obrocWPoziomie.addActionListener(actionEvent -> {
            obrocWPoziomie.setSelected(true);
            obrocWPionie.setSelected(false);
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
            jFrame.setTitle(filename);
            tytulobrazka.setText("Wczytany obraz: " + filename);
            reverseChanges();
        }
        }

    void saveImage() {
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
                filepath += extension;
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

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Czy na pewno chcesz wyjść? Ewentualne niezapisane zmiany zostaną utracone.", "Zamknąć?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
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
        COFNIJButton.setEnabled(view);
        rodzajzapisu.setEnabled(view);
        obroc.setEnabled(view);
    }

    private void reverseChanges() {
        if (OpenCV.readImage(filepath, filename, jFrame)) {
            System.out.println("File successfully loaded");
            enableElements(true);
        } else {
            enableElements(false);
        }
    }
}
