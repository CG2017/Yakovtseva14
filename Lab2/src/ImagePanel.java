import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImagePanel extends JPanel
{
    private BufferedImage image;
    private Dimension size = new Dimension(600, 400);

    ImagePanel() {
        setPreferredSize(size);
        setMaximumSize(size);
        setMaximumSize(size);
    }

    BufferedImage getImage() {
        return image;
    }

    void setImage(File file) throws IOException {
        setImage(ImageIO.read(file));
    }

    private void setImage(BufferedImage image) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(size.getWidth() / image.getWidth(), size.getHeight() / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        this.image = op.filter(image, null);
        repaint();
    }

    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        g.dispose();
    }

    private int calcDistance(double[] luv1, double[] luv2, double[] weights) {
        return (int) Math.pow(weights[0] * (luv1[0] - luv2[0]) * (luv1[0] - luv2[0]) +
                              weights[1] * (luv1[1] - luv2[1]) * (luv1[1] - luv2[1]) +
                              weights[2] * (luv1[2] - luv2[2]) * (luv1[2] - luv2[2]), 0.5);
    }

    void convertImage(Color oldColor, Color newColor, int distance, double[] weights) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color currColor = new Color(image.getRGB(i, j));
                int currDistance = calcDistance(getLUV(oldColor), getLUV(currColor), weights);
                if (currDistance <= distance) {
                    int distanceR = currColor.getRed() - oldColor.getRed();
                    int distanceG = currColor.getGreen() - oldColor.getGreen();
                    int distanceB = currColor.getBlue() - oldColor.getBlue();
                    Color newColorWithDistance = new Color(Math.min(255, Math.max(0, newColor.getRed() + distanceR)),
                                                           Math.min(255, Math.max(0, newColor.getGreen() + distanceG)),
                                                           Math.min(255, Math.max(0, newColor.getBlue() + distanceB)));
                    image.setRGB(i, j, newColorWithDistance.hashCode());
                }
            }
        }

        repaint();
    }


    // CIE RGB E xyz=(1/3,1/3,1/3) XYZ(100,100,100)
    private double[][] RGBtoXYZ = {
            {0.4887180, 0.3106803, 0.2006017},
            {0.1762044, 0.8129847, 0.0108109},
            {0.0000000, 0.0102048, 0.9897952},
            };

    private double[] matrixToVector(double[][] M, double[] v) {
        double[] res = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            double temp = 0;
            for (int j = 0; j < v.length; j++) {
                temp += M[i][j] * v[j];
            }
            res[i] = temp;
        }
        return res;
    }

    private double[] getLUV(Color color) {
        double[] RGB = new double[]{
                color.getRed() / 255.,
                color.getGreen() / 255.,
                color.getBlue() / 255.
        };
        double[] XYZ = matrixToVector(RGBtoXYZ, RGB);
        double L = 100 * XYZ[1];
        double u = 100 * 4 * XYZ[0] / (XYZ[0] + 15 * XYZ[1] + 3 * XYZ[2]);
        double v = 100 * 9 * XYZ[1] / (XYZ[0] + 15 * XYZ[1] + 3 * XYZ[2]);

        return new double[]{L, u, v}; // max 1. 0.7 0.6
    }


}
