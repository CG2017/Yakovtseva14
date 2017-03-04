import javax.swing.*;
import java.awt.*;

class ColorModelPanel extends JPanel
{
    private SlideSpinPanel slideSpinPanel;
    private JPanel colorPreview;

    ColorModelPanel(int min1, int max1, int min2, int max2, int min3, int max3,
                    String[] name) {

        slideSpinPanel = new SlideSpinPanel(min1, max1, min2, max2, min3, max3, name);
        colorPreview = new JPanel();
        setColorPreview();
        Dimension size = new Dimension(50, 50);
        colorPreview.setPreferredSize(size);
        colorPreview.setMaximumSize(size);
        colorPreview.setMinimumSize(size);

        add(slideSpinPanel);
        add(colorPreview);

        slideSpinPanel.addCommonListener(e -> setColorPreview());
    }

    ColorModelPanel(int min, int max, String[] name) {
        this(min, max, min, max, min, max, name);
    }

    private void setColorPreview() {
        colorPreview.setBackground(getColor());
    }

    void setColor(Color color) {
        slideSpinPanel.setValues(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
    }

    Color getColor() {
        int[] rgb = slideSpinPanel.getValues();
        return new Color(rgb[0], rgb[1], rgb[2]);
    }
}
