package dialog;

import rasterizer.LineRasterizer;
import rasterizer.RasterizeMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class NewLineDialog extends JDialog
{

    private Point start;
    private Point end;
    private LineRasterizer lineRasterizer;

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public LineRasterizer getLineRasterizer() {
        return lineRasterizer;
    }

    public NewLineDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("New Line");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel panelPointStart = new JPanel();
        JPanel panelPointEnd = new JPanel();

        Dimension inputSize = new Dimension(50, 20);
        JTextField startPointX = new JTextField();
        JTextField startPointY = new JTextField();
        JTextField endPointX = new JTextField();
        JTextField endPointY = new JTextField();

        startPointX.setPreferredSize(inputSize);
        startPointY.setPreferredSize(inputSize);
        endPointX.setPreferredSize(inputSize);
        endPointY.setPreferredSize(inputSize);
        JButton submit = new JButton("Add");
        RasterizeMethod[] methods = RasterizeMethod.values();
        JComboBox<RasterizeMethod> methodsComboBox = new JComboBox<>(methods);
        submit.addActionListener(e -> {
            try {
                start = new Point(Integer.parseInt(startPointX.getText()), Integer.parseInt(startPointY.getText()));
                end = new Point(Integer.parseInt(endPointX.getText()), Integer.parseInt(endPointY.getText()));
                RasterizeMethod method = (RasterizeMethod) methodsComboBox.getSelectedItem();
                lineRasterizer = method.getLineRasterizer();
                NewLineDialog.this.dispatchEvent(new WindowEvent(NewLineDialog.this, WindowEvent.WINDOW_CLOSING));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        panelPointStart.add(new Label("Start point:"));
        panelPointStart.add(startPointX);
        panelPointStart.add(startPointY);
        panelPointEnd.add(new Label("  End point:"));
        panelPointEnd.add(endPointX);
        panelPointEnd.add(endPointY);

        add(panelPointStart);
        add(panelPointEnd);
        add(methodsComboBox);
        add(submit);

        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        setSize(300, 150);

    }

}
