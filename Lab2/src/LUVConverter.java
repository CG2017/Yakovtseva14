import java.awt.*;

public class LUVConverter
{
    // CIE RGB E xyz=(1/3,1/3,1/3) XYZ(100,100,100)
    private double[][] RGBtoXYZ = {
            {0.4124564, 0.3575761, 0.1804375},
            {0.2126729, 0.7151522, 0.0721750},
            {0.0193339, 0.1191920, 0.9503041},
            };

    private double[][] XYZtoRGB = {
            {3.2404542, -1.5371385, -0.4985314},
            {-0.9692660, 1.8760108, 0.0415560},
            {0.0556434, -0.2040259, 1.0572252},
            };


    private double Xn = 95.047;
    private double Yn = 100;
    private double Zn = 108.883;
    private double un = 4 * Xn / (Xn + 15 * Yn + 3 * Zn);
    private double vn = 9 * Yn / (Xn + 15 * Yn + 3 * Zn);
    private double yEps = Math.pow(6. / 29, 3);

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

    double[] getLUV(Color color) {
        double[] RGB = new double[]{
                color.getRed() / 255.,
                color.getGreen() / 255.,
                color.getBlue() / 255.
        };
        double[] XYZ = matrixToVector(RGBtoXYZ, RGB);
        for (int i = 0; i < 3; i++) {
            XYZ[i] *= 100;
        }

        double L = XYZ[1] / Yn;
        if (L <= yEps) {
            L *= Math.pow(29 / 3., 3);
        } else {
            L = 116 * Math.pow(L, 1 / 3.) - 16;
        }

        double u = 4 * XYZ[0] / (XYZ[0] + 15 * XYZ[1] + 3 * XYZ[2]);
        double v = 9 * XYZ[1] / (XYZ[0] + 15 * XYZ[1] + 3 * XYZ[2]);

        if (Double.isNaN(u)) {
            u = 0;
        }
        if (Double.isNaN(v)) {
            v = 0;
        }

        double U = 13 * L * (u - un);
        double V = 13 * L * (v - vn);

//        System.out.printf("%f %f %f\n", L, U, V);
        return new double[]{L, U, V};
    }


    Color getRGB(double[] LUV) {
        double u = LUV[1] / (13 * LUV[0]) + un;
        double v = LUV[2] / (13 * LUV[0]) + vn;

        double Y = Yn;
        if (LUV[0] <= 8) {
            Y *= LUV[0] * Math.pow(3 / 29., 3);
        } else {
            Y *= Math.pow((LUV[0] + 16) / 116., 3);
        }


        double X = (Y * 9 * u) / (4 * v);
        double Z = Y * (12 - 3 * u - 20 * v) / (4 * v);
        double[] xyz = new double[]{X / 100., Y / 100., Z / 100.};
        double[] rgb = matrixToVector(XYZtoRGB, xyz);
//        System.out.printf("%f %f %f", rgb[0], rgb[1], rgb[2]);
        for (int i = 0; i < 3; i++) {
            rgb[i] = Math.max(0, Math.min(1, rgb[i]));
        }
        return new Color((int) (rgb[0] * 255),
                         (int) (rgb[1] * 255),
                         (int) (rgb[2] * 255));
    }

}
