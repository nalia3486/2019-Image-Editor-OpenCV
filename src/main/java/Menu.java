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
    private JLabel obraz = new JLabel();

    Menu() {
        wczytajPlikButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(getRootFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
                OpenCV.readImage(chooser.getSelectedFile().getAbsolutePath());

                enableElements();
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
        JFrame frame1 = new JFrame("Transform the image");
        frame1.setContentPane(new Menu().Menu);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setSize(500, 500);
        frame1.setVisible(true);

        try {
            OpenCV.imageToPDF("lenka.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
