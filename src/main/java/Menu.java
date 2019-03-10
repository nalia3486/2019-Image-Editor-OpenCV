import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static javax.swing.JOptionPane.getRootFrame;

public class Menu {
    private JPanel Menu;
    private JButton wczytajPlikButton;
    private JButton rozmazButton;
    private JButton wyostrzButton;
    private JButton obrazCzarnoBialyButton;
    private JSlider rozmazslider;
    private JSlider wyostrzslider;
    private JFrame jFrame = new JFrame();

    private Menu() {
        wczytajPlikButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(getRootFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
                if (OpenCV.readImage(chooser.getSelectedFile().getAbsolutePath(), jFrame)) {
                    System.out.println("File successfully loaded");
                    enableElements();
                }
            }
        });
    }

    private void enableElements() {
        rozmazButton.setEnabled(true);
        wyostrzButton.setEnabled(true);
        obrazCzarnoBialyButton.setEnabled(true);
        rozmazslider.setEnabled(true);
        wyostrzslider.setEnabled(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transform the image");
        frame.setContentPane(new Menu().Menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);

        try {
            OpenCV.imageToPDF("lenka.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
