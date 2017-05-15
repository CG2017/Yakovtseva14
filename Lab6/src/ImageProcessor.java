import java.awt.*;
import java.awt.image.BufferedImage;

abstract class ImageProcessor
{
    private BufferedImage grayScale;
    private BufferedImage original;

    abstract int getThreshold();

    ImageProcessor(BufferedImage image) {
        original = image;
        grayScale = toGrayScale(image);
    }

    int[] imageHistogram() {
        int[] histogram = new int[256];
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }
        for (int i = 0; i < grayScale.getWidth(); i++) {
            for (int j = 0; j < grayScale.getHeight(); j++) {
                int red = new Color(grayScale.getRGB(i, j)).getRed();
                histogram[red]++;
            }
        }
        return histogram;
    }

    BufferedImage getGrayScale() {
        return grayScale;
    }

    public BufferedImage getOriginal() {
        return original;
    }

    private BufferedImage toGrayScale(BufferedImage original) {
        int alpha, red, green, blue, gray;
        BufferedImage grayScale = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                Color current = new Color(original.getRGB(i, j));
                alpha = current.getAlpha();
                red = current.getRed();
                green = current.getGreen();
                blue = current.getBlue();
                gray = (int) Math.round((1.0 * (red + green + blue)) / 3);
//                gray = (int) (0.2125 * red + 0.7154 * green + 0.0721 * blue);
                grayScale.setRGB(i, j, colorToRGB(gray, gray, gray, alpha));
            }
        }
        return grayScale;
    }


    int colorToRGB(int red, int green, int blue, int alpha) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }


    BufferedImage binarize() {
        int gray, newPixel;
        int threshold = getThreshold();
        BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                Color current = new Color(grayScale.getRGB(i, j));
                gray = current.getRed();
                if (gray > threshold) {
                    newPixel = 255;
                } else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(newPixel, newPixel, newPixel, current.getAlpha());
                binarized.setRGB(i, j, newPixel);
            }
        }
        return binarized;
    }

}
