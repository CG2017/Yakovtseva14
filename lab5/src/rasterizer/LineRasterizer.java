package rasterizer;

import java.awt.*;


public interface LineRasterizer
{
    double[][] rasterize(Point start, Point end);
}
