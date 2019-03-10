import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;


public class OpenCV_image_operations {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

//    public static void main(String[] args) {
//        //readImage("cat.jpg");
//        //readImage("lenka.png");
//
//        try {
//            imageToPDF("lenka.png");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Mat img = Imgcodecs.imread("1.jpg");
//        Mat img1 = Imgcodecs.imread("2.jpg");
//
//        superimposingImages(img, img1);
//

    //readImage("cat.jpg");
    //readImage("lenka.png");


//        Mat img = Imgcodecs.imread("1.jpg");
//        Mat img1 = Imgcodecs.imread("2.jpg");
//
//        OpenCV_image_operations.superimposingImages(img, img1);
//    }

    static void superimposingImages(Mat img, Mat img1) {
        Mat img3 = new Mat();
        Core.addWeighted(img, 0.5, img1, 0.5, 0, img3);
        BufferedImage image = Mat2BufferedImage(img3);
        displayImage(Mat2BufferedImage(img3));
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

    static void readImage(String filename) {
        Mat lena = Imgcodecs.imread(filename);
        if (lena.empty()) {
            System.out.println("Could not open or find the image");
        } else {
            System.out.println(lena.size());
            Imgcodecs.imwrite(filename, lena);
            displayImage(Mat2BufferedImage(lena));
        }
    }

    static BufferedImage Mat2BufferedImage(Mat m) {
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

    static void displayImage(Image img) {
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
