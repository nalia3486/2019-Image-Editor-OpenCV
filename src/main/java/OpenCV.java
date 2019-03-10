import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import java.util.ArrayList;

import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;


public class OpenCV {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static void superimposingImages(Mat img, Mat img1, JFrame jFrame) {
        Mat img3 = new Mat();
        Core.addWeighted(img, 0.5, img1, 0.5, 0, img3);
        BufferedImage image = Mat2BufferedImage(img3);
        displayImage(Mat2BufferedImage(img3), jFrame);
    }


    static void imageToPDF(String filename) throws Exception {
        File root = new File("..\\2019-Image-Editor-OpenCV");

        String outputFile = "some.pdf";

        //to na wypadek, gdyby mialo byc wiecej zdjec w jednym pdfie
        List<String> files = new ArrayList<>();
        files.add(filename);

        Document document = new Document(PageSize.A4, 10, 10, 10, 10);

        PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
        document.open();

        for (String f : files) {
            document.newPage();
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(new File(root, f).getAbsolutePath());
            if (image.getHeight() > (document.getPageSize().getHeight() - 20)
                    || image.getScaledWidth() > (document.getPageSize().getWidth() - 20)) {
                image.scaleToFit(document.getPageSize().getWidth() - 20, document.getPageSize().getHeight() - 20);
            }

            document.add(image);
        }

        document.close();
    }

    static boolean readImage(String filename, JFrame jFrame) {
        Mat lena = Imgcodecs.imread(filename);
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
}
