import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JPanel {

    private BufferedImage image;

    ImagePanel() {
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

}
