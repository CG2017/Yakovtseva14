import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageDialog extends JDialog {

    private BufferedImage image;
    private ImagePanel imagePanel = new ImagePanel();

    public BufferedImage getImage() {
        return image;
    }

    private JMenuBar initFileMenu() {
        JMenuBar menuBar = new JMenuBar();
        JButton menuSaveFile = new JButton("Save File");
        menuBar.add(menuSaveFile);

        menuSaveFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(imagePanel);
            File updatedFile = fileChooser.getSelectedFile();
            try {
                ImageIO.write(image,
                        FilenameUtils.getExtension(updatedFile.getAbsolutePath()),
                        updatedFile
                );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        return menuBar;
    }

    ImageDialog(JFrame owner, String name, BufferedImage image) {
        super(owner, name);
        this.image = image;
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setImage(image);
        setJMenuBar(initFileMenu());
        add(imagePanel);
    }
}
