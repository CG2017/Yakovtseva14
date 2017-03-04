import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame
{
    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Lab2");

        ImagePanel imagePanel = new ImagePanel();
        ColorModelPanel p1 = new ColorModelPanel(0, 255, new String[]{"R", "B", "B"});
        ColorModelPanel p2 = new ColorModelPanel(0, 255, new String[]{"R", "B", "B"});
        DistancePanel p3 = new DistancePanel(0, 100);
        p3.setDistance(10);

        imagePanel.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (imagePanel.getImage() == null) {
                    return;
                }
                Point point = e.getPoint();
                p1.setColor(new Color(imagePanel.getImage().getRGB(point.x, point.y)));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (imagePanel.getImage() == null) {
                    return;
                }
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                imagePanel.setCursor(cursor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (imagePanel.getImage() == null) {
                    return;
                }
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                imagePanel.setCursor(cursor);
            }
        });


        JButton chooseImgButton = new JButton("Choose picture");
        chooseImgButton.addActionListener(e -> {
                                              JFileChooser fc = new JFileChooser("./res");
                                              int returnVal = fc.showOpenDialog(Main.this);
                                              if (returnVal == JFileChooser.APPROVE_OPTION) {
                                                  File file = fc.getSelectedFile();
                                                  try {
                                                      imagePanel.setImage(file);
                                                  } catch (IOException e1) {
                                                      e1.printStackTrace();
                                                  }
                                              }
                                          }

        );

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(imagePanel, BorderLayout.NORTH);
        panel1.add(chooseImgButton, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(p1);
        panel2.add(p3);
        panel2.add(p2);

        JButton convertColorsButton = new JButton("Convert colors");
        convertColorsButton.addActionListener(e -> {
            imagePanel.convertImage(p1.getColor(), p2.getColor(), p3.getDistance(), p3.getWeights());
        });
        panel2.add(convertColorsButton);
        panel2.add(Box.createVerticalGlue());

        add(panel1);
        add(panel2);

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
