package rasterizer.brezenham;

import rasterizer.LineRasterizer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class BrezenhamLineRasterizer implements LineRasterizer
{
    @Override
    public double[][] rasterize(Point start, Point end) {
        List<Point> points = new ArrayList<>();
        int deltaX = Math.abs(end.x - start.x);
        int deltaY = Math.abs(end.y - start.y);
        int signY = start.y < end.y ? 1 : -1;
        int error = deltaX - deltaY;

        int x = start.x;
        int y = start.y;
        while (x < end.x || y < end.y) {
            points.add(new Point(x, y));
            if (error * 2 > -deltaY) {
                error -= deltaY;
                x++;
            }
            if (error * 2 < deltaX) {
                error += deltaX;
                y += signY;
            }
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

    @Override
    public String toString() {
        return "Brezenham Algorithm";
    }
}
