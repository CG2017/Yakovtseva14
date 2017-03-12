import java.awt.image.BufferedImage;


class HistogramBuilder
{
    private double[][] red;
    private double[][] green;
    private double[][] blue;

    HistogramBuilder(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int[] imgPixels = image.getRGB(0, 0, w, h, null, 0, w);
        processImagePixels(imgPixels);
    }

    double[][] getRed() {
        return red;
    }

    double[][] getGreen() {
        return green;
    }

    double[][] getBlue() {
        return blue;
    }

    double getAvgRed() {
        double avg = 0;
        int total = 0;
        for (int i = 0; i < 256; i++) {
            avg += red[0][i] * red[1][i];
            total += red[1][i];
        }
        return avg / total;
    }

    double getAvgGreen() {
        double avg = 0;
        int total = 0;
        for (int i = 0; i < 256; i++) {
            avg += green[0][i] * green[1][i];
            total += green[1][i];
        }
        return avg / total;
    }

    double getAvgBlue() {
        double avg = 0;
        int total = 0;
        for (int i = 0; i < 256; i++) {
            avg += blue[0][i] * blue[1][i];
            total += blue[1][i];
        }
        return avg / total;
    }

    private void processImagePixels(int[] pixels) {
        red = new double[2][256];
        green = new double[2][256];
        blue = new double[2][256];
        for (int i = 0; i < 256; i++) {
            red[0][i] = green[0][i] = blue[0][i] = (double) i;
            red[1][i] = green[1][i] = blue[1][i] = 0;
        }
        for (int pixel : pixels) {
            red[1][pixel & 0xFF]++;
            green[1][(pixel >> 8) & 0xFF]++;
            blue[1][(pixel >> 16) & 0xFF]++;
        }
    }
}
