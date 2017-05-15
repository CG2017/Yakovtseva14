import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainForm extends JFrame
{

    private ImagePanel panelImageBefore = new ImagePanel();
    private BufferedImage image;

    private JMenuBar initFileMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(0, 3));
        JButton menuOpenFile = new JButton("Open File");
        JButton menuOtsuAlgorithm = new JButton("Otsu Method");
        JButton menuBradleyAlgorithm = new JButton("Bradley Algorithm");
        JButton menuHistogramMethod = new JButton("Histogram Method");
        JButton menuSobelAlgorithm = new JButton("Sobel Algorithm");
        menuBar.add(menuOpenFile);
        menuBar.add(menuOtsuAlgorithm);
        menuBar.add(menuHistogramMethod);
        menuBar.add(menuBradleyAlgorithm);
        menuBar.add(menuSobelAlgorithm);

        menuOpenFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(panelImageBefore);
            File chosenFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(chosenFile);
                panelImageBefore.setSize(image.getWidth() + 40, image.getHeight() + 70);
                panelImageBefore.setImage(image);
                setSize(image.getWidth() + 40, image.getHeight() + 130);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        menuOtsuAlgorithm.addActionListener(e -> {
            OtsuAlgorithm otsuAlgorithm = new OtsuAlgorithm(image);
            ImageDialog dialog = new ImageDialog(MainForm.this, menuOtsuAlgorithm.getText(), otsuAlgorithm.binarize());
            dialog.setSize(image.getWidth() + 40, image.getHeight() + 70);
            dialog.setVisible(true);
        });

        menuHistogramMethod.addActionListener(e -> {
            HistogramThreshold histogramThreshold = new HistogramThreshold(image);
            ImageDialog dialog = new ImageDialog(MainForm.this, menuHistogramMethod.getText(),
                                                 histogramThreshold.binarize());
            dialog.setSize(image.getWidth() + 40, image.getHeight() + 70);
            dialog.setVisible(true);
        });

        menuBradleyAlgorithm.addActionListener(e -> {
            BradleyAlgorithm bradleyAlgorithm = new BradleyAlgorithm(image);
            ImageDialog dialog = new ImageDialog(MainForm.this, menuBradleyAlgorithm.getText(),
                                                 bradleyAlgorithm.binarize());
            dialog.setSize(image.getWidth() + 40, image.getHeight() + 70);
            dialog.setVisible(true);
        });

        menuSobelAlgorithm.addActionListener(e -> {
            SobelAlgorithm sobelAlgorithm = new SobelAlgorithm(image);
            ImageDialog dialog = new ImageDialog(MainForm.this, menuSobelAlgorithm.getText(),
                                                 sobelAlgorithm.binarize());
            dialog.setSize(image.getWidth() + 40, image.getHeight() + 70);
            dialog.setVisible(true);
        });


        return menuBar;
    }

    private MainForm() throws IOException {
        super("Image Processing Lab");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelImageBefore.setSize(this.getWidth(), 0);
        panelImageBefore.setLayout(new GridLayout(0, 1));
        setLayout(new GridLayout(1, 1));
        add(panelImageBefore);
        setSize(500, 500);
        setJMenuBar(initFileMenu());
    }

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            }
        }

        try {
            MainForm app = new MainForm();
            app.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
