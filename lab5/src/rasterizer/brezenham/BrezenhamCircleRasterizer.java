package rasterizer.brezenham;

import rasterizer.CircleRasterizer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class BrezenhamCircleRasterizer implements CircleRasterizer
{
    @Override
    public double[][] rasterize(Point center, int radius) {
        List<Point> points = new ArrayList<>();
        int x = 0;
        int y = radius;
        int delta = 1 - radius * 2;
        int error;
        while (y >= 0) {
            points.add(new Point(center.x + x, center.y + y));
            points.add(new Point(center.x + x, center.y - y));
            points.add(new Point(center.x - x, center.y + y));
            points.add(new Point(center.x - x, center.y - y));

            error = 2 * (delta + y) - 1;
            if ((delta < 0) && (error <= 0)) {
                delta += 2 * ++x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if ((delta > 0) && (error >= 0)) {
                delta += 1 - 2 * --y;
                continue;
            }
            x++;
            delta += 2 * (x - y);
            y--;
        }
        return convertPointsList(points);
    }

    private double[][] convertPointsList(List<Point> pointsList) {
        double[][] pointsArray = new double[2][pointsList.size()];
        for (int i=0; i<pointsList.size(); i++) {
            pointsArray[0][i] = pointsList.get(i).x;
            pointsArray[1][i] = pointsList.get(i).y;
        }
        return pointsArray;
    }
}
