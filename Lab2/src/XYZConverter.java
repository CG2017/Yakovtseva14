import java.awt.*;

class XYZConverter
{
    final double Xn = 95.047;
    final double Yn = 100.000;
    final double Zn = 108.883;

    final double Epsilon = 0.008856; // Intent is 216/24389
    final double Kappa = 903.3; // Intent is 24389/27

    double[] getXYZ(Color color) {
        double r = PivotRgb(color.getRed() / 255.0);
        double g = PivotRgb(color.getGreen() / 255.0);
        double b = PivotRgb(color.getBlue() / 255.0);

        // Observer. = 2°, Illuminant = D65
        double X = r * 0.4124 + g * 0.3576 + b * 0.1805;
        double Y = r * 0.2126 + g * 0.7152 + b * 0.0722;
        double Z = r * 0.0193 + g * 0.1192 + b * 0.9505;

        return new double[]{X, Y, Z};
    }

    Color getRGB(double[] XYZ) {
        // (Observer = 2°, Illuminant = D65)
        double x = XYZ[0] / 100.0;
        double y = XYZ[1] / 100.0;
        double z = XYZ[2] / 100.0;

        double r = x * 3.2406 + y * -1.5372 + z * -0.4986;
        double g = x * -0.9689 + y * 1.8758 + z * 0.0415;
        double b = x * 0.0557 + y * -0.2040 + z * 1.0570;

        r = r > 0.0031308 ? 1.055 * Math.pow(r, 1 / 2.4) - 0.055 : 12.92 * r;
        g = g > 0.0031308 ? 1.055 * Math.pow(g, 1 / 2.4) - 0.055 : 12.92 * g;
        b = b > 0.0031308 ? 1.055 * Math.pow(b, 1 / 2.4) - 0.055 : 12.92 * b;

        return new Color(ToRgb(r), ToRgb(g), ToRgb(b));
    }

    private int ToRgb(double n) {
        double result = Math.round(255.0 * n);
        if (result < 0) {
            return 0;
        }
        if (result > 255) {
            return 255;
        }
        return (int) result;
    }

    private double PivotRgb(double n) {
        return (n > 0.04045 ? Math.pow((n + 0.055) / 1.055, 2.4) : n / 12.92) * 100.0;
    }
}
