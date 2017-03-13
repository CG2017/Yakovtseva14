import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame
{
    private Main() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lab3");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));


        ImagePanel imagePanelOriginal = new ImagePanel();

        JButton chooseImgButton = new JButton("Choose picture");
        chooseImgButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        DefaultXYDataset data = new DefaultXYDataset();

        ValueAxis xAxis = new NumberAxis("Color value");
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setRange(0, 255);
        ValueAxis yAxis = new NumberAxis("Number of pixels");
        XYPlot plot = new XYPlot(data, xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
        JFreeChart chart = new JFreeChart("Color histogram", JFreeChart.DEFAULT_TITLE_FONT, plot, false);

        JLabel redAvg = new JLabel();
        JLabel greenAvg = new JLabel();
        JLabel blueAvg = new JLabel();

        chooseImgButton.addActionListener(e -> {
                                              JFileChooser fc = new JFileChooser("./res");
                                              int returnVal = fc.showOpenDialog(Main.this);
                                              if (returnVal == JFileChooser.APPROVE_OPTION) {
                                                  while (data.getSeriesCount() != 0) {
                                                      data.removeSeries(data.getSeriesKey(0));
                                                      data.removeSeries(data.getSeriesKey(0));
                                                      data.removeSeries(data.getSeriesKey(0));
                                                  }
                                                  File file = fc.getSelectedFile();
                                                  try {
                                                      imagePanelOriginal.setImage(file);
                                                      HistogramBuilder histogramBuilder = new HistogramBuilder(imagePanelOriginal.getImage());
                                                      data.addSeries("green", histogramBuilder.getGreen());
                                                      data.addSeries("red", histogramBuilder.getRed());
                                                      data.addSeries("blue", histogramBuilder.getBlue());

                                                      redAvg.setText(Double.toString(histogramBuilder.getAvgRed()));
                                                      greenAvg.setText(Double.toString(histogramBuilder.getAvgGreen()));
                                                      blueAvg.setText(Double.toString(histogramBuilder.getAvgBlue()));

                                                  } catch (IOException e1) {
                                                      e1.printStackTrace();
                                                  }
                                              }
                                          }

        );


        JPanel redAvgPanel = new JPanel();
        redAvgPanel.add(new Label("Red average: "));
        redAvgPanel.add(redAvg);

        JPanel greenAvgPanel = new JPanel();
        greenAvgPanel.add(new Label("Green average: "));
        greenAvgPanel.add(greenAvg);


        JPanel blueAvgPanel = new JPanel();
        blueAvgPanel.add(new Label("Blue average: "));
        blueAvgPanel.add(blueAvg);

        JPanel avgPanel = new JPanel();
        avgPanel.setLayout(new BoxLayout(avgPanel, BoxLayout.Y_AXIS));
        avgPanel.add(redAvgPanel);
        avgPanel.add(greenAvgPanel);
        avgPanel.add(blueAvgPanel);


        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new BoxLayout(imgPanel, BoxLayout.Y_AXIS));
        imgPanel.add(imagePanelOriginal);
        imgPanel.add(chooseImgButton);
        imgPanel.add(avgPanel);


        ChartPanel chartPanelRed = new ChartPanel(chart);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.Y_AXIS));
        chartPanel.add(chartPanelRed);

        add(imgPanel);
        add(chartPanel);

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

        JFrame frame = new Main();
        frame.pack();
        frame.setVisible(true);
    }
}
