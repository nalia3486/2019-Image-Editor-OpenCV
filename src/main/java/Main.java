import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import static javafx.application.Application.launch;
import static javafx.application.Application.launch;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;

import static org.opencv.core.Core.BORDER_DEFAULT;

import org.opencv.core.Core.MinMaxLocResult;

import static org.opencv.core.Core.NORM_MINMAX;
import static org.opencv.core.Core.addWeighted;
import static org.opencv.core.Core.flip;
import static org.opencv.core.Core.minMaxLoc;
import static org.opencv.core.Core.normalize;
import static org.opencv.core.Core.randu;
import static org.opencv.core.Core.transpose;

import org.opencv.core.CvType;

import static org.opencv.core.CvType.CV_64F;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;

import org.opencv.core.Mat;

import static org.opencv.core.Mat.zeros;

import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.MORPH_OPEN;
import static org.opencv.imgproc.Imgproc.filter2D;
import static org.opencv.imgproc.Imgproc.line;
import static org.opencv.imgproc.Imgproc.rectangle;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        readImage("cat.jpg");
        readImage("lenka.png");
    }

    private static void readImage(String filename) {
        Mat lena = Imgcodecs.imread(filename);
        if (lena.empty()) {
            System.out.println("Could not open or find the image");
        } else {
            System.out.println(lena.size());
            Imgcodecs.imwrite(filename, lena);
            displayImage(Mat2BufferedImage(lena));
        }
    }

    public static BufferedImage Mat2BufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public static void displayImage(Image img) {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(null) + 50, img.getHeight(null) + 50);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
