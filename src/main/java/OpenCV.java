import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.opencv.core.Core.addWeighted;
import static org.opencv.core.Core.flip;
import static org.opencv.core.CvType.CV_64F;


public class OpenCV {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static void superimposingImages(Mat img, Mat img1, JFrame jFrame) {
        Mat img3 = new Mat();
        addWeighted(img, 0.5, img1, 0.5, 0, img3);
        BufferedImage image = Mat2BufferedImage(img3);
        displayImage(Mat2BufferedImage(img3), jFrame);
    }


    static void imageToPDF(String filename, String filepath) {
        File root = new File("D:\\eclipse-workspace\\2019-Image-Editor-OpenCV");

        String outputFile = "some.pdf";

        Document document = new Document(PageSize.A4, 10, 10, 10, 10);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
            document.open();
            document.newPage();
            com.itextpdf.text.Image image =
                    com.itextpdf.text.Image.getInstance(new File(root, filename).getAbsolutePath());
            if (image.getHeight() > (document.getPageSize().getHeight() - 20)
                    || image.getScaledWidth() > (document.getPageSize().getWidth() - 20)) {
                image.scaleToFit(document.getPageSize().getWidth() - 20, document.getPageSize().getHeight() - 20);
            }
            document.add(image);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean readImage(String filepath, String filename, JFrame jFrame) {
        Mat lena = Imgcodecs.imread(filepath);
        if (lena.empty()) {
            System.out.println("Could not open or find the image");
            JOptionPane.showMessageDialog(null, "Wybrano niewłaściwy format pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            System.out.println(lena.size());
            Imgcodecs.imwrite(filename, lena);
            displayImage(Mat2BufferedImage(lena), jFrame);
            return true;
        }
    }

    static void readGrayscaleImage(String name, JFrame jFrame) {
        Mat src = Imgcodecs.imread(name, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        System.out.println("Gray scale image");
        Imgcodecs.imwrite(name, src);
        displayImage(Mat2BufferedImage(src), jFrame);
    }

    private static BufferedImage Mat2BufferedImage(Mat m) {
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

    private static void displayImage(Image img, JFrame frame) {
        frame.getContentPane().removeAll();
        frame.repaint();
        ImageIcon icon = new ImageIcon(img);
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(null) + 50, img.getHeight(null) + 50);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        frame.setLocation(505, 10);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    static void addGaussianBlur(int size, boolean blur, String name, JFrame jFrame, double alpha, double beta) {
        Mat dst = new Mat();
        Mat src = Imgcodecs.imread(name);
        if (!blur) {
            Imgproc.GaussianBlur(src, dst, new Size(0, 0), 3);
            addWeighted(src, alpha, dst, beta, 0, dst);
        } else {
            Imgproc.GaussianBlur(src, dst, new Size(size, size), 3);
        }
        System.out.println("Added Gaussian Blur");
        Imgcodecs.imwrite(name, dst);
        displayImage(Mat2BufferedImage(dst), jFrame);
    }

    static void addMedianBlur(int size, boolean blur, String name, JFrame jFrame, double alpha, double beta) {
        Mat dst = new Mat();
        Mat src = Imgcodecs.imread(name);
        Imgproc.medianBlur(src, dst, size);
        if (!blur) {
            addWeighted(src, alpha, dst, beta, 0, dst);
        }
        System.out.println("Added Median Blur");
        Imgcodecs.imwrite(name, dst);
        displayImage(Mat2BufferedImage(dst), jFrame);
    }

    static void addBilateralBlur(int size, boolean blur, String name, JFrame jFrame, double alpha, double beta) {
        Mat dst = new Mat();
        Mat src = Imgcodecs.imread(name);
        Imgproc.bilateralFilter(src, dst, CV_64F, size, size);
        if (!blur) {
            addWeighted(src, alpha, dst, beta, 0, dst);
        }
        System.out.println("Added Bilateral Blur");
        Imgcodecs.imwrite(name, dst);
        displayImage(Mat2BufferedImage(dst), jFrame);
    }

    static void addFlip(boolean flip, JFrame jFrame, String name) {
        Mat img = Imgcodecs.imread(name);
        if (flip) flip(img, img, 1); //odbity w pionie
        else flip(img, img, 0); //odbity w poziomie
        System.out.println("Flip image");
        Imgcodecs.imwrite(name, img);
        displayImage(Mat2BufferedImage(img), jFrame);
    }
}
