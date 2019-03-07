import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;

public class Menu {
    private JPanel Menu;
    private JButton button1;
    private JTextField helloTextField;
    private JButton button2;
    private JButton button3;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new Menu().Menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //readImage("cat.jpg");
        //readImage("lenka.png");

        try {
            OpenCV_image_operations.imageToPDF("lenka.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mat img = Imgcodecs.imread("1.jpg");
        Mat img1 = Imgcodecs.imread("2.jpg");

        OpenCV_image_operations.superimposingImages(img, img1);
    }
}
