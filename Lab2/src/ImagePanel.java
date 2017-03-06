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
    private LUVConverter converter = new LUVConverter();


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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        double[] newLUV = converter.getLUV(newColor);
        double[] oldLUV = converter.getLUV(oldColor);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color currColor = new Color(image.getRGB(i, j));
                double[] currLUV = converter.getLUV(currColor);
                int currDistance = calcDistance(oldLUV, currLUV, weights);
                if (currDistance <= distance) {
                    double distanceL = currLUV[0] - oldLUV[0];
                    double distanceU = currLUV[1] - oldLUV[1];
                    double distanceV = currLUV[2] - oldLUV[2];
                    image.setRGB(i, j, converter.getRGB(new double[]{
                            newLUV[0] + distanceL,
                            newLUV[1] + distanceU,
                            newLUV[2] + distanceV
                    }).hashCode());
                }
            }
        }

        repaint();
    }

}
