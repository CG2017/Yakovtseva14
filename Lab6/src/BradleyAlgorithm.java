import java.awt.*;
import java.awt.image.BufferedImage;

class BradleyAlgorithm extends ImageProcessor
{
    private int[][] integratedImage;
    private double t = 0.85;
    private int window;
    private int radius;

    BradleyAlgorithm(BufferedImage image) {
        super(image);
        integratedImage = toIntegratedImage(getGrayScale());
        window = image.getWidth() / 8;
        radius = window / 2;
    }

    private int[][] toIntegratedImage(BufferedImage input) {
        int[][] integrated = new int[input.getWidth()][input.getHeight()];
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color current = new Color(input.getRGB(i, j));
                int value = current.getRed();
                if (i > 0) {
                    value += integrated[i - 1][j];
                }
                if (j > 0) {
                    value += integrated[i][j - 1];
                }
                if (j > 0 && i > 0) {
                    value -= integrated[i - 1][j - 1];
                }
                integrated[i][j] = value;
            }
        }
        return integrated;
    }

    @Override
    BufferedImage binarize() {
        BufferedImage grayScale = getGrayScale();
        BufferedImage binarized = new BufferedImage(grayScale.getWidth(),
                                                    grayScale.getHeight(),
                                                    grayScale.getType());

        for (int i = 0; i < grayScale.getWidth(); i++) {
            int x1 = i - radius;
            int x2 = i + radius;
            if (x1 < 0) {
                x1 = 0;
            }
            if (x2 >= grayScale.getWidth()) {
                x2 = grayScale.getWidth() - 1;
            }
            for (int j = 0; j < grayScale.getHeight(); j++) {
                int y1 = j - radius;
                int y2 = j + radius;

                if (y1 < 0) {
                    y1 = 0;
                }
                if (y2 >= grayScale.getHeight()) {
                    y2 = grayScale.getHeight() - 1;
                }

                int pixelsInWindow = (x2 - x1) * (y2 - y1);
                double sum = integratedImage[x2][y2] + integratedImage[x1][y1] -
                             integratedImage[x2][y1] - integratedImage[x1][y2];
                if (new Color(grayScale.getRGB(i, j)).getRed() < (sum / pixelsInWindow) * t) {
                    binarized.setRGB(i, j, colorToRGB(0, 0, 0, new Color(grayScale.getRGB(i, j)).getAlpha()));
                } else {
                    binarized.setRGB(i, j, colorToRGB(255, 255, 255, new Color(grayScale.getRGB(i, j)).getAlpha()));
                }
            }
        }

        return binarized;
    }


    @Override
    int getThreshold() {
        return 0;
    }
}
