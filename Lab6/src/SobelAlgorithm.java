import java.awt.*;
import java.awt.image.BufferedImage;

public class SobelAlgorithm extends ImageProcessor
{
    private static int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private static int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

    SobelAlgorithm(BufferedImage image) {
        super(image);
    }

    @Override
    BufferedImage binarize() {
        BufferedImage grayScale = getGrayScale();
        BufferedImage binarized = new BufferedImage(grayScale.getWidth(),
                                                    grayScale.getHeight(),
                                                    grayScale.getType());

        for (int i = 1; i < grayScale.getWidth() - 2; i++) {
            for (int j = 1; j < grayScale.getHeight() - 2; j++) {
                Color current = new Color(grayScale.getRGB(i, j));

                int pixel_x =
                        (sobelX[0][0] * new Color(grayScale.getRGB(i - 1, j - 1)).getRed()) +
                        (sobelX[0][1] * new Color(grayScale.getRGB(i, j - 1)).getRed()) +
                        (sobelX[0][2] * new Color(grayScale.getRGB(i + 1, j - 1)).getRed()) +
                        (sobelX[1][0] * new Color(grayScale.getRGB(i - 1, j)).getRed()) +
                        (sobelX[1][1] * new Color(grayScale.getRGB(i, j)).getRed()) +
                        (sobelX[1][2] * new Color(grayScale.getRGB(i + 1, j)).getRed()) +
                        (sobelX[2][0] * new Color(grayScale.getRGB(i - 1, j + 1)).getRed()) +
                        (sobelX[2][1] * new Color(grayScale.getRGB(i, j + 1)).getRed()) +
                        (sobelX[2][2] * new Color(grayScale.getRGB(i + 1, j + 1)).getRed());

                int pixel_y =
                        (sobelY[0][0] * new Color(grayScale.getRGB(i - 1, j - 1)).getRed()) +
                        (sobelY[0][1] * new Color(grayScale.getRGB(i, j - 1)).getRed()) +
                        (sobelY[0][2] * new Color(grayScale.getRGB(i + 1, j - 1)).getRed()) +
                        (sobelY[1][0] * new Color(grayScale.getRGB(i - 1, j)).getRed()) +
                        (sobelY[1][1] * new Color(grayScale.getRGB(i, j)).getRed()) +
                        (sobelY[1][2] * new Color(grayScale.getRGB(i + 1, j)).getRed()) +
                        (sobelY[2][0] * new Color(grayScale.getRGB(i - 1, j + 1)).getRed()) +
                        (sobelY[2][1] * new Color(grayScale.getRGB(i, j + 1)).getRed()) +
                        (sobelY[2][2] * new Color(grayScale.getRGB(i + 1, j + 1)).getRed());

                int val = (int)Math.ceil(Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y)));
                int newPixel = colorToRGB(val, val, val, current.getAlpha());
                binarized.setRGB(i, j, newPixel);
            }
        }
        return binarized;
    }

    @Override
    int getThreshold() {
        return 0;
    }
}
