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

}
