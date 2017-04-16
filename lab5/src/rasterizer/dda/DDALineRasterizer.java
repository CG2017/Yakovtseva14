package rasterizer.dda;

import rasterizer.LineRasterizer;

import java.awt.*;

public class DDALineRasterizer implements LineRasterizer
{
    @Override
    public double[][] rasterize(Point start, Point end) {
        int l = Math.max(Math.abs(end.x - start.x), Math.abs(end.y - start.y));
        double[][] points = new double[2][l];

        double stepX = (end.x - start.x) * 1.0 / l;
        double stepY = (end.y - start.y) * 1.0 / l;

        double x = start.x;
        double y = start.y;
        for (int i = 0; i < l; i++) {
            points[0][i] = x;
            points[1][i] = y;
            x += stepX;
            y += stepY;
        }
        return points;
    }

    private double[][] convertPointsList(java.util.List<Point> pointsList) {
        double[][] pointsArray = new double[2][pointsList.size()];
        for (int i = 0; i < pointsList.size(); i++) {
            pointsArray[0][i] = pointsList.get(i).x;
            pointsArray[1][i] = pointsList.get(i).y;
        }
        return pointsArray;
    }

    @Override
    public String toString() {
        return "DDA Algorithm";
    }
}
