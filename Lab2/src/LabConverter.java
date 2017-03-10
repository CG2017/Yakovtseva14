import java.awt.*;

class LABConverter
{
    private XYZConverter xyzConverter = new XYZConverter();
    
    double[] getLAB(Color color) {
        double[] xyz = xyzConverter.getXYZ(color);

        double x = PivotXyz(xyz[0] / xyzConverter.Xn);
        double y = PivotXyz(xyz[1] / xyzConverter.Yn);
        double z = PivotXyz(xyz[2] / xyzConverter.Zn);

        double L = Math.max(0, 116 * y - 16);
        double A = 500 * (x - y);
        double B = 200 * (y - z);
        return new double[]{L, A, B};
    }

    Color getRGB(double[] LAB) {
        double y = (LAB[0] + 16.0) / 116.0;
        double x = LAB[1] / 500.0 + y;
        double z = y - LAB[2] / 200.0;

        double x3 = x * x * x;
        double z3 = z * z * z;
        double[] xyz = new double[]{
                xyzConverter.Xn * (x3 > xyzConverter.Epsilon ? x3 : (x - 16.0 / 116.0) / 7.787),
                xyzConverter.Yn *
                (LAB[0] > (xyzConverter.Kappa * xyzConverter.Epsilon) ? Math.pow(((LAB[0] + 16.0) / 116.0), 3) :
                 LAB[0] / xyzConverter.Kappa),
                xyzConverter.Zn * (z3 > xyzConverter.Epsilon ? z3 : (z - 16.0 / 116.0) / 7.787)
        };

        return xyzConverter.getRGB(xyz);
    }

    private double PivotXyz(double n) {
        return n > xyzConverter.Epsilon ? CubicRoot(n) : (xyzConverter.Kappa * n + 16) / 116;
    }

    private double CubicRoot(double n) {
        return Math.pow(n, 1.0 / 3.0);
    }

}
