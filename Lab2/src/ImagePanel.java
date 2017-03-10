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
    private LABConverter converter = new LABConverter();


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

    void setImage(BufferedImage image) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(size.getWidth() / image.getWidth(), size.getHeight() / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        this.image = op.filter(image, null);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
        g.dispose();
    }

    private int calcDistance(double[] lab1, double[] lab2, double[] weights) {
        return (int) Math.pow(weights[0] * (lab1[0] - lab2[0]) * (lab1[0] - lab2[0]) +
                              weights[1] * (lab1[1] - lab2[1]) * (lab1[1] - lab2[1]) +
                              weights[2] * (lab1[2] - lab2[2]) * (lab1[2] - lab2[2]), 0.5);
    }

    void convertImage(Color oldColor, Color newColor, int distance, double[] weights) {
        double[] newLAB = converter.getLAB(newColor);
        double[] oldLAB = converter.getLAB(oldColor);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color currColor = new Color(image.getRGB(i, j));
                double[] currLAB = converter.getLAB(currColor);
                int currDistance = calcDistance(oldLAB, currLAB, weights);
                if (currDistance <= distance) {
                    double distanceL = currLAB[0] - oldLAB[0];
                    double distanceA = currLAB[1] - oldLAB[1];
                    double distanceB = currLAB[2] - oldLAB[2];
                    image.setRGB(i, j, converter.getRGB(new double[]{
                            newLAB[0] + distanceL,
                            newLAB[1] + distanceA,
                            newLAB[2] + distanceB
                    }).hashCode());
                }
            }
        }

        repaint();
    }

}
