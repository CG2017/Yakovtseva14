import javax.swing.*;
import java.awt.*;

abstract class ColorModelPanel extends JPanel
{
    JSlider slider1;
    JSlider slider2;
    JSlider slider3;
    boolean notifyListener;

    JLabel icon1;
    JLabel icon2;
    JLabel icon3;

    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;

    ColorModelPanel(ColorModelListener listener, int min1, int max1, int min2, int max2, int min3, int max3,
                    String[] name) {

        Dimension size = new Dimension(500, 200);
        setSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        JLabel label0 = new JLabel(String.format("%s model", String.join("", (CharSequence[]) name)));
        JLabel label1 = new JLabel(name[0]);
        JLabel label2 = new JLabel(name[1]);
        JLabel label3 = new JLabel(name[2]);

        spinner1 = new JSpinner(new SpinnerNumberModel(min1, min1, max1, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(min2, min2, max2, 1));
        spinner3 = new JSpinner(new SpinnerNumberModel(min3, min3, max3, 1));

        slider1 = new JSlider(min1, max1, min1);
        slider2 = new JSlider(min3, max2, min2);
        slider3 = new JSlider(min3, max2, min3);


        icon1 = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
        icon2 = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
        icon3 = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
        icon1.setEnabled(false);
        icon2.setEnabled(false);
        icon3.setEnabled(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(label0);

        JPanel foo = new JPanel();
        foo.setOpaque(false);
        foo.add(label1);
        foo.add(spinner1);
        foo.add(slider1);
        foo.add(icon1);
        add(foo);

        JPanel bar = new JPanel();
        bar.setOpaque(false);
        bar.add(label2);
        bar.add(spinner2);
        bar.add(slider2);
        bar.add(icon2);
        add(bar);

        JPanel lala = new JPanel();
        lala.setOpaque(false);
        lala.add(label3);
        lala.add(spinner3);
        lala.add(slider3);
        lala.add(icon3);
        add(lala);

        spinner1.addChangeListener(e -> {
            slider1.setValue((Integer) spinner1.getValue());
            if (notifyListener) {
                listener.colorChanged(getRGB(), this);
            }
        });
        spinner2.addChangeListener(e -> {
            slider2.setValue((Integer) spinner2.getValue());
            if (notifyListener) {
                listener.colorChanged(getRGB(), this);
            }
        });
        spinner3.addChangeListener(e -> {
            slider3.setValue((Integer) spinner3.getValue());
            if (notifyListener) {
                listener.colorChanged(getRGB(), this);
            }
        });

        slider1.addChangeListener(e -> {
            spinner1.setValue(slider1.getValue());
            if (notifyListener) {
                listener.colorChanged(getRGB(), this);
            }
        });
        slider2.addChangeListener(e -> {
            spinner2.setValue(slider2.getValue());
            if (notifyListener) {
                listener.colorChanged(getRGB(), this);
            }
        });
        slider3.addChangeListener(e -> {
            spinner3.setValue(slider3.getValue());
            if (notifyListener) {
                listener.colorChanged(getRGB(), this);
            }
        });

        setOpaque(false);
        notifyListener = true;
    }

    ColorModelPanel(ColorModelListener listener, int min, int max, String[] name) {
        this(listener, min, max, min, max, min, max, name);
    }

    void disableIcons(){
        icon1.setEnabled(false);
        icon2.setEnabled(false);
        icon3.setEnabled(false);
    }

    abstract double[] getRGB();

    abstract void setColorFromRGB(double[] RGB);
}
