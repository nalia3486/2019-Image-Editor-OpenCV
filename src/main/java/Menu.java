import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private String filepath;
    private String filename;

    private Menu() {

        wczytajPlikButton.addActionListener(e -> chooseImage());

        rozmazButton.addActionListener(e -> OpenCV.addGaussianBlur(7, true, filename, jFrame));

        wyostrzButton.addActionListener(e -> OpenCV.addGaussianBlur(0, false, filename, jFrame));
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
            if (OpenCV.readImage(filepath, filename, jFrame)) {
                System.out.println("File successfully loaded");
                enableElements(true);
            } else {
                enableElements(false);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("IMGEditor");
        frame.setContentPane(new Menu().Menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void enableElements(boolean view) {
        rozmazslider.setMinimum(0);
        rozmazslider.setMaximum(20);
        rozmazslider.setValue(10);
        wyostrzslider.setMinimum(0);
        wyostrzslider.setMaximum(20);
        wyostrzslider.setValue(10);
        rozmazButton.setEnabled(view);
        wyostrzButton.setEnabled(view);
        obrazCzarnoBialyButton.setEnabled(view);
        rozmazslider.setEnabled(view);
        wyostrzslider.setEnabled(view);
    }
}
