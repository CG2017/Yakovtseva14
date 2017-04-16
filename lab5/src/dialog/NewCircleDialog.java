package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class NewCircleDialog extends JDialog
{
    private Point center;
    private int radius;

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }


    public NewCircleDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("New Circle");
        JPanel panelPointCenter = new JPanel();
        JPanel panelRadius = new JPanel();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        Dimension inputSize = new Dimension(50, 20);
        JTextField centerPointX = new JTextField();
        JTextField centerPointY = new JTextField();
        JTextField radiusField = new JTextField();
        JButton submit = new JButton("Add");
        centerPointX.setPreferredSize(inputSize);
        centerPointY.setPreferredSize(inputSize);
        radiusField.setPreferredSize(inputSize);

        submit.addActionListener(e -> {
            try {
                center = new Point(Integer.parseInt(centerPointX.getText()),
                                   Integer.parseInt(centerPointY.getText()));
                radius = Integer.parseInt(radiusField.getText());
                NewCircleDialog.this.dispatchEvent(new WindowEvent(NewCircleDialog.this, WindowEvent.WINDOW_CLOSING));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        panelPointCenter.add(new Label("Center:"));
        panelPointCenter.add(centerPointX);
        panelPointCenter.add(centerPointY);
        panelRadius.add(new Label("Radius:"));
        panelRadius.add(radiusField);

        add(panelPointCenter);
        add(panelRadius);
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(submit);
        setSize(200, 150);
        setLocationRelativeTo(null);
    }
}
