import dialog.NewCircleDialog;
import dialog.NewLineDialog;
import render.PixelRender;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import rasterizer.brezenham.BrezenhamCircleRasterizer;

import javax.swing.*;

public class MainForm extends JFrame
{
    private DefaultXYDataset dataset = new DefaultXYDataset();
    private JFreeChart chart = ChartFactory.createXYLineChart("", "x", "y", dataset,
                                                              PlotOrientation.VERTICAL, true, true, false);

    private static int X_SIZE = 150;
    private static int Y_SIZE = 100;

    private JMenuBar initFileMenu() {
        JMenuBar menuBar = new JMenuBar();
        JButton menuInitialScale = new JButton("Initial Scale");
        JButton menuNewLine = new JButton("New Line");
        JButton menuNewCircle = new JButton("New Circle");
        JButton menuClear = new JButton("Clear All");
        menuBar.add(menuInitialScale);
        menuBar.add(menuNewLine);
        menuBar.add(menuNewCircle);
        menuBar.add(menuClear);

        menuInitialScale.addActionListener(e -> {
            XYPlot plot = chart.getXYPlot();
            NumberAxis domain = (NumberAxis) plot.getDomainAxis();
            domain.setRange(-0.5, X_SIZE);
            NumberAxis range = (NumberAxis) plot.getRangeAxis();
            range.setRange(-0.5, Y_SIZE);

        });

        menuNewLine.addActionListener(e ->
                                      {

                                          NewLineDialog dialog = new NewLineDialog(MainForm.this, true);
                                          dialog.setVisible(true);
                                          double[][] points = dialog.getLineRasterizer().rasterize(dialog.getStart(),
                                                                                                   dialog.getEnd());
                                          dataset.addSeries(dialog.getLineRasterizer().toString(), points);

                                      });

        menuNewCircle.addActionListener(e -> {
                                            NewCircleDialog dialog = new NewCircleDialog(MainForm.this, true);
                                            dialog.setVisible(true);
                                            double[][] points = new BrezenhamCircleRasterizer().rasterize(dialog.getCenter(), dialog.getRadius());
                                            dataset.addSeries("Brezenham Circle", points);
                                        }
        );

        menuClear.addActionListener(e -> {
            while (dataset.getSeriesCount() != 0) {
                dataset.removeSeries(dataset.getSeriesKey(0));
            }

        });
        return menuBar;
    }

    private MainForm() {
        super("Raster Algorithms Demo");
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(new PixelRender());
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(-0.5, X_SIZE);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(-0.5, Y_SIZE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
        setJMenuBar(initFileMenu());
    }

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            }
        }

        MainForm app = new MainForm();
        app.setVisible(true);
    }
}
