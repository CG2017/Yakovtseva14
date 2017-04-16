package render;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PixelRender extends XYLineAndShapeRenderer {
    private static final double DEFAULT_WIDTH_FACTOR = 1;
    private double barwidthfactor = DEFAULT_WIDTH_FACTOR;
    private Shape thedot = null;

    public PixelRender(double factor) {
        this();
        this.barwidthfactor = factor;
    }

    public PixelRender() {
        super();
        setBaseLinesVisible(false);
        setBaseShapesVisible(true);
        setUseOutlinePaint(true);
    }

    @Override
    public XYItemRendererState initialise(Graphics2D g2,
                                          Rectangle2D dataArea,
                                          XYPlot plot,
                                          XYDataset data,
                                          PlotRenderingInfo info) {

        thedot = null;
        return super.initialise(g2, dataArea, plot, data, info);
    }

    @Override
    public void drawItem(Graphics2D g2, XYItemRendererState state,
                         Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
                         ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
                         int series, int item, CrosshairState crosshairState, int pass) {

        if (thedot == null) {
            double width = 0.0;
            double height = 0.0;
            RectangleEdge domainEdge = plot.getDomainAxisEdge();
            RectangleEdge rangeEdge = plot.getRangeAxisEdge();
            double widthms = 1;
            double left = (int) dataset.getXValue(series, item) - widthms;
            double top = (int) dataset.getYValue(series, item) - widthms;

            for (int j = 0; j < 2; ++j) {
                double right = left + widthms;
                double bottom = top + widthms;
                double lpos = domainAxis.valueToJava2D(left, dataArea, domainEdge);
                double rpos = domainAxis.valueToJava2D(right, dataArea, domainEdge);
                double tpos = rangeAxis.valueToJava2D(top, dataArea, rangeEdge);
                double bpos = rangeAxis.valueToJava2D(bottom, dataArea, rangeEdge);
                width = Math.max(width, Math.abs(rpos - lpos));
                height = Math.max(height, Math.abs(tpos - bpos));
                left += widthms;
                top += widthms;
            }

            width *= barwidthfactor;
            height *= barwidthfactor;
            thedot = new Rectangle(0, -1, (int) width, (int) height);
        }

        super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState,
                pass);
    }

    @Override
    public Shape getItemShape(int series, int item) {
        return thedot;
    }
}
