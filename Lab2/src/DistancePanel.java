import javax.swing.*;
import javax.swing.event.ChangeListener;

class DistancePanel extends JPanel
{
    private JSlider slider1;
    private JSpinner spinner1;
    private SlideSpinPanel weightPanel;

    DistancePanel(int min, int max) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        spinner1 = new JSpinner(new SpinnerNumberModel(min, min, max, 1));
        slider1 = new JSlider(min, max, min);
        weightPanel = new SlideSpinPanel(0, 100, new String[]{"L weight", "A weight", "B weight"});
        weightPanel.setValues(new int[]{100, 100, 100});

        JPanel foo = new JPanel();
        foo.add(new JLabel("Distance"));
        foo.add(spinner1);
        foo.add(slider1);

        add(foo);
        add(weightPanel);

        spinner1.addChangeListener(e -> {
            slider1.setValue((Integer) spinner1.getValue());
        });

        slider1.addChangeListener(e -> {
            spinner1.setValue(slider1.getValue());
        });

    }

    int getDistance() {
        return slider1.getValue();
    }

    void setDistance(int distance) {
        slider1.setValue(distance);
    }

    double[] getWeights() {
        int[] values = weightPanel.getValues();
        return new double[]{values[0] / 100., values[1] / 100., values[2] / 100.};
    }

    void addCommonListener(ChangeListener al) {
        spinner1.addChangeListener(al);
        slider1.addChangeListener(al);
        weightPanel.addCommonListener(al);
    }
}

