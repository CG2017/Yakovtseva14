import java.awt.image.BufferedImage;

class OtsuAlgorithm extends ImageProcessor
{
    OtsuAlgorithm(BufferedImage image) {
        super(image);
    }

    int getThreshold() {
        BufferedImage grayScale = getGrayScale();
        int[] histogram = imageHistogram();
        int total = grayScale.getHeight() * grayScale.getWidth();
        int wB = 0;
        int wF;
        int threshold = 0;
        double sum = 0;
        double sumB = 0;
        double varMax = 0;

        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) {
                continue;
            }
            wF = total - wB;
            if (wF == 0) {
                break;
            }
            sumB += (double) (i * histogram[i]);
            double mB = sumB / wB;
            double mF = (sum - sumB) / wF;
            double varBetween = (double) wB * (double) wF * (mB - mF) * (mB - mF);
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }

        return threshold;

    }
}
