import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame
{
    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lab2");

        ImagePanel imagePanelOriginal = new ImagePanel();
        ImagePanel imagePanelConverted = new ImagePanel();
        ColorModelPanel p1 = new ColorModelPanel(0, 255, new String[]{"R", "G", "B"});
        ColorModelPanel p2 = new ColorModelPanel(0, 255, new String[]{"R", "G", "B"});
        DistancePanel p3 = new DistancePanel(0, 300);
        p3.setDistance(10);
        p3.addCommonListener(e -> {
            if (imagePanelOriginal.getImage() != null) {
                imagePanelConverted.setImage(deepCopy(imagePanelOriginal.getImage()));
                imagePanelConverted.convertImage(p1.getColor(), p2.getColor(), p3.getDistance(), p3.getWeights());
            }
        });

        imagePanelOriginal.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (imagePanelOriginal.getImage() == null) {
                    return;
                }
                Point point = e.getPoint();
                p1.setColor(new Color(imagePanelOriginal.getImage().getRGB(point.x, point.y)));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (imagePanelOriginal.getImage() == null) {
                    return;
                }
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                imagePanelOriginal.setCursor(cursor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (imagePanelOriginal.getImage() == null) {
                    return;
                }
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                imagePanelOriginal.setCursor(cursor);
            }
        });


        JButton chooseImgButton = new JButton("Choose picture");
        chooseImgButton.addActionListener(e -> {
                                              JFileChooser fc = new JFileChooser("./res");
                                              int returnVal = fc.showOpenDialog(Main.this);
                                              if (returnVal == JFileChooser.APPROVE_OPTION) {
                                                  File file = fc.getSelectedFile();
                                                  try {
                                                      imagePanelOriginal.setImage(file);
                                                      imagePanelConverted.setImage(file);
                                                  } catch (IOException e1) {
                                                      e1.printStackTrace();
                                                  }
                                              }
                                          }

        );

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel imgPanel = new JPanel();
        imgPanel.add(imagePanelOriginal);
        imgPanel.add(imagePanelConverted);

        JButton convertColorsButton = new JButton("Convert colors");
        convertColorsButton.addActionListener(e -> {
            if (imagePanelOriginal.getImage() != null) {
                imagePanelConverted.setImage(deepCopy(imagePanelOriginal.getImage()));
                imagePanelConverted.convertImage(p1.getColor(), p2.getColor(), p3.getDistance(), p3.getWeights());
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(chooseImgButton);
        btnPanel.add(convertColorsButton);
        btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel sliderPanel = new JPanel();
        sliderPanel.add(p1);
        sliderPanel.add(p3);
        sliderPanel.add(p2);


        add(imgPanel);
        add(btnPanel);
        add(sliderPanel);

    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
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

        JFrame frame = new Main();
        frame.pack();
        frame.setVisible(true);
    }
}
