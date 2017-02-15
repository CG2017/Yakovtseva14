
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame implements ColorModelListener
{
    private ColorModelPanel RGB;
    private ColorModelPanel CMY;
    private ColorModelPanel HSV;
    private ColorModelPanel LUV;
    private ColorModelPanel XYZ;

    // CIE RGB E
    private double[][] RGBtoXYZ = {
            {0.4887180, 0.3106803, 0.2006017},
            {0.1762044, 0.8129847, 0.0108109},
            {0.0000000, 0.0102048, 0.9897952},
            };

    private double[][] XYZtoRGB = {
            {2.3706743, -0.9000405, -0.4706338},
            {-0.518850, 1.4253036, 0.0885814},
            {0.0052982, -0.0146949, 1.0093968},
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
                double r = R / 255.;
                double g = G / 255.;
                double b = B / 255.;
                return matrixToVector(RGBtoXYZ, new double[]{r, g, b});
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {
                double[] rgb = matrixToVector(XYZtoRGB, XYZ);
                slider1.setValue((int) (rgb[0] * 255.));
                slider2.setValue((int) (rgb[1] * 255.));
                slider3.setValue((int) (rgb[2] * 255.));

            }
        };
        CMY = new ColorModelPanel(this, 0, 255, new String[]{"C", "M", "Y"})
        {
            @Override
            double[] getXYZ() {
                int R = 255 - slider1.getValue();
                int G = 255 - slider2.getValue();
                int B = 255 - slider3.getValue();
                double r = R / 255.;
                double g = G / 255.;
                double b = B / 255.;
                return matrixToVector(RGBtoXYZ, new double[]{r, g, b});
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {
                double[] rgb = matrixToVector(XYZtoRGB, XYZ);
                slider1.setValue((int) ((1 - rgb[0]) * 255.));
                slider2.setValue((int) ((1 - rgb[1]) * 255.));
                slider3.setValue((int) ((1 - rgb[2]) * 255.));
            }
        };
        HSV = new ColorModelPanel(this, 0, 360, 0, 100, 0, 100, new String[]{"H", "S", "V"})
        {
            @Override
            double[] getXYZ() {
                double s = slider2.getValue() / 100.;
                double v = slider3.getValue() / 100.;
                double c = v * s;
                double m = v - c;
                double h = slider1.getValue() / 60.;
                double x = c * (1 - Math.abs(h % 2 - 1));
                double[] rgb = new double[3];
                if (h >= 0 && h < 1) {
                    rgb[0] = c;
                    rgb[1] = x;
                    rgb[2] = 0;
                }
                else if (h >= 1 && h < 2) {
                    rgb[0] = x;
                    rgb[1] = c;
                    rgb[2] = 0;
                }
                else if (h >= 2 && h < 3) {
                    rgb[0] = 0;
                    rgb[1] = c;
                    rgb[2] = x;
                }
                else if (h >= 3 && h < 4) {
                    rgb[0] = 0;
                    rgb[1] = x;
                    rgb[2] = c;
                }
                else if (h >= 4 && h < 5) {
                    rgb[0] = x;
                    rgb[1] = 0;
                    rgb[2] = c;
                }
                else if (h >= 5 && h <= 6) {
                    rgb[0] = c;
                    rgb[1] = 0;
                    rgb[2] = x;
                }
                rgb[0] += m;
                rgb[1] += m;
                rgb[2] += m;
                return matrixToVector(RGBtoXYZ, rgb);
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {
                double[] rgb = matrixToVector(XYZtoRGB, XYZ);
                double M = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
                double m = Math.min(rgb[0], Math.min(rgb[1], rgb[2]));
                double C = M - m;
                double H = 60;
                double S;
                double V;
                if (C == 0) {
                    H *= 0;
                }
                else if (rgb[0] == M) {
                    H *= ((rgb[1] - rgb[2]) / M) % 6;
                }
                else if (rgb[1] == M) {
                    H *= ((rgb[2] - rgb[1]) / M) + 2;
                }
                else if (rgb[2] == M) {
                    H *= ((rgb[0] - rgb[2]) / M) + 4;
                }

                if (M == 0) {
                    S = 0;
                }
                else {
                    S = C / M;
                }

                slider1.setValue((int) H);
                slider2.setValue((int) (S * 100.));
                slider3.setValue((int) (M * 100.));

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

        XYZ = new ColorModelPanel(this, 0, 100, new String[]{"X", "Y", "Z"})
        {
            @Override
            double[] getXYZ() {
                return new double[]{
                        slider1.getValue() / 100.,
                        slider2.getValue() / 100.,
                        slider3.getValue() / 100.
                };
            }

            @Override
            void setColorFromXYZ(double[] XYZ) {
                slider1.setValue((int) (XYZ[0] * 100.));
                slider2.setValue((int) (XYZ[1] * 100.));
                slider3.setValue((int) (XYZ[2] * 100.));
            }
        };

        add(RGB);
        add(CMY);
        add(HSV);
        add(LUV);
        add(XYZ);
        colorChanged(new double[]{0, 0, 0}, null);
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
    public void colorChanged(double[] xyz, ColorModelPanel origin) {
        RGB.notifyListener = false;
        CMY.notifyListener = false;
        HSV.notifyListener = false;
        LUV.notifyListener = false;
        XYZ.notifyListener = false;

        if (RGB != origin) {
            RGB.setColorFromXYZ(xyz);
        }
        if (CMY != origin) {
            CMY.setColorFromXYZ(xyz);
        }
        if (HSV != origin) {
            HSV.setColorFromXYZ(xyz);
        }
        if (LUV != origin) {
            LUV.setColorFromXYZ(xyz);
        }
        if (XYZ != origin) {
            XYZ.setColorFromXYZ(xyz);
        }
        RGB.notifyListener = true;
        CMY.notifyListener = true;
        HSV.notifyListener = true;
        LUV.notifyListener = true;
        XYZ.notifyListener = true;
        getContentPane().setBackground(
                new Color(RGB.slider1.getValue(), RGB.slider2.getValue(), RGB.slider3.getValue()));

    }
}
