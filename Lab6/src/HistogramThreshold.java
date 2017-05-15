import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class HistogramThreshold extends ImageProcessor
{

    HistogramThreshold(BufferedImage image) {
        super(image);
    }

    @Override
    int getThreshold() {
        int gray, threshold, avg;
        double eps = 0.0001;
        threshold = 100;
        BufferedImage grayScale = getGrayScale();
        List<Integer> G1 = new ArrayList<>();
        List<Integer> G2 = new ArrayList<>();
        do {
            G1.clear();
            G2.clear();
            for (int i = 0; i < grayScale.getWidth(); i++) {
                for (int j = 0; j < grayScale.getHeight(); j++) {
                    Color current = new Color(grayScale.getRGB(i, j));
                    gray = current.getRed();
                    if (gray > threshold) {
                        G1.add(gray);
                    } else {
                        G2.add(gray);
                    }
                }
            }

            double avg1 = 1.0 * G1.stream().reduce(Integer::sum).orElse(0) / (G1.size() == 0 ? 1 : G1.size());
            double avg2 = 1.0 * G2.stream().reduce(Integer::sum).orElse(0) / (G2.size() == 0 ? 1 : G2.size());
            avg = ((int) Math.round((avg1 + avg2) / 2.0));
            if (Math.abs(threshold - avg) <= eps) {
                threshold = avg;
                break;
            }
            threshold = avg;
        } while (true);

        return threshold;
    }

}
