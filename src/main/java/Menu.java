import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.getRootFrame;

public class Menu {
    private JPanel Menu;
    private JButton wczytajPlikButton;
    private JButton rozmazButton;
    private JButton wyostrzButton;
    private JButton obrazCzarnoBialyButton;
    private JLabel obraz = new JLabel();

    Menu() {
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("cat.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ImageIcon icon = new ImageIcon(img);
//        JFrame frame = new JFrame();
//        frame.setLayout(new FlowLayout());
//        frame.setSize(img.getWidth(null) + 50, img.getHeight(null) + 50);
//        obraz.setIcon(icon);
//        frame.add(obraz);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame1 = new JFrame("Transform the image");
        frame1.setContentPane(new Menu().Menu);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
