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
        wczytajPlikButton.addActionListener(e -> {
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
        });


        rozmazButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenCV.addGaussianBlur(15, true, filename, jFrame);
            }
        });

        wyostrzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenCV.addGaussianBlur(15, false, filename, jFrame);
            }
        });
    }

    private void enableElements(boolean view) {
        rozmazButton.setEnabled(view);
        wyostrzButton.setEnabled(view);
        obrazCzarnoBialyButton.setEnabled(view);
        rozmazslider.setEnabled(view);
        wyostrzslider.setEnabled(view);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transform the image");
        frame.setContentPane(new Menu().Menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
