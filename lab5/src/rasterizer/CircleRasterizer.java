package rasterizer;

import java.awt.*;

public interface CircleRasterizer {
    double[][] rasterize(Point center, int radius);
}
