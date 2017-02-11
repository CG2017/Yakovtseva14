
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame implements ColorModelListener
{
    private ColorModelPanel RGB;
    private ColorModelPanel CMY;
    private ColorModelPanel HSV;
    private ColorModelPanel LUV;

    Main() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Color Models");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        RGB = new ColorModelPanel(this, 0, 255, new String[]{"R", "G", "B"})
        {
            @Override
            double[] getXYZ() {
                int R = slider1.getValue();
                int G = slider2.getValue();
                int B = slider3.getValue();
                double X = (0.49 * R + 0.31 * G + 0.2 * B) / 0.17697;
                double Y = (0.17697 * R + 0.8124 * G + 0.010630 * B) / 0.17697;
                double Z = (0.01 * G + 0.99 * B) / 0.17697;
                return new double[]{X, Y, Z};
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {
                double R = 0.41847 * XYZ[0] - 0.15866 * XYZ[1] - 0.082835 * XYZ[2];
                double G = -0.091169 * XYZ[0] + 0.25243 * XYZ[1] + 0.015708 * XYZ[2];
                double B = 0.0009209 * XYZ[0] - 0.0025498 * XYZ[1] + 0.1786 * XYZ[2];
                slider1.setValue((int) R);
                slider2.setValue((int) G);
                slider3.setValue((int) B);

            }
        };
        CMY = new ColorModelPanel(this, 0, 255, new String[]{"C", "M", "Y"})
        {
            @Override
            double[] getXYZ() {
                int R = 255 - slider1.getValue();
                int G = 255 - slider2.getValue();
                int B = 255 - slider3.getValue();
                double X = (0.49 * R + 0.31 * G + 0.2 * B) / 0.17697;
                double Y = (0.17697 * R + 0.8124 * G + 0.010630 * B) / 0.17697;
                double Z = (0.01 * G + 0.99 * B) / 0.17697;
                return new double[]{X, Y, Z};
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {
                double R = 0.41847 * XYZ[0] - 0.15866 * XYZ[1] - 0.082835 * XYZ[2];
                double G = -0.091169 * XYZ[0] + 0.25243 * XYZ[1] + 0.015708 * XYZ[2];
                double B = 0.0009209 * XYZ[0] - 0.0025498 * XYZ[1] + 0.1786 * XYZ[2];
                slider1.setValue((int) (255 - R));
                slider2.setValue((int) (255 - G));
                slider3.setValue((int) (255 - B));
            }
        };
        HSV = new ColorModelPanel(this, 0, 360, 0, 100, 0, 100, new String[]{"H", "S", "V"})
        {
            @Override
            double[] getXYZ() {
                return new double[0];
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {

            }
        };
        LUV = new ColorModelPanel(this, 0, 255, new String[]{"L", "u\'", "v\'"})
        {
            @Override
            double[] getXYZ() {
                return new double[0];
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {

            }
        };
        add(RGB);
        add(CMY);
        add(HSV);
        add(LUV);
        getContentPane().setBackground(Color.BLACK);
    }

    public static void main(String args[]) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
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

    @Override
    public void colorChanged(double[] XYZ, ColorModelPanel origin) {
        RGB.notifyListener = false;
        CMY.notifyListener = false;
        HSV.notifyListener = false;
        LUV.notifyListener = false;
        if (RGB != origin) {
            RGB.setColorFromXYZ(XYZ);
        }
        if (CMY != origin) {
            CMY.setColorFromXYZ(XYZ);
        }
        if (HSV != origin) {
            HSV.setColorFromXYZ(XYZ);
        }
        if (LUV != origin) {
            LUV.setColorFromXYZ(XYZ);
        }
        RGB.notifyListener = true;
        CMY.notifyListener = true;
        HSV.notifyListener = true;
        LUV.notifyListener = true;
        getContentPane().setBackground(
                new Color(RGB.slider1.getValue(), RGB.slider2.getValue(), RGB.slider3.getValue()));

    }
}
