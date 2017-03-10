import javax.swing.*;
import javax.swing.event.ChangeListener;

class SlideSpinPanel extends JPanel
{
    private JSlider slider1;
    private JSlider slider2;
    private JSlider slider3;

    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;


    SlideSpinPanel(int min1, int max1, int min2, int max2, int min3, int max3,
                   String[] name) {

        JLabel label1 = new JLabel(name[0]);
        JLabel label2 = new JLabel(name[1]);
        JLabel label3 = new JLabel(name[2]);

        spinner1 = new JSpinner(new SpinnerNumberModel(min1, min1, max1, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(min2, min2, max2, 1));
        spinner3 = new JSpinner(new SpinnerNumberModel(min3, min3, max3, 1));

        slider1 = new JSlider(min1, max1, min1);
        slider2 = new JSlider(min3, max2, min2);
        slider3 = new JSlider(min3, max2, min3);

        JPanel foo = new JPanel();
        foo.add(label1);
        foo.add(spinner1);
        foo.add(slider1);

        JPanel bar = new JPanel();
        bar.add(label2);
        bar.add(spinner2);
        bar.add(slider2);

        JPanel lala = new JPanel();
        lala.add(label3);
        lala.add(spinner3);
        lala.add(slider3);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(foo);
        add(bar);
        add(lala);


        spinner1.addChangeListener(e -> {
            slider1.setValue((Integer) spinner1.getValue());
        });

        spinner2.addChangeListener(e -> {
            slider2.setValue((Integer) spinner2.getValue());
        });

        spinner3.addChangeListener(e -> {
            slider3.setValue((Integer) spinner3.getValue());
        });

        slider1.addChangeListener(e -> {
            spinner1.setValue(slider1.getValue());
        });

        slider2.addChangeListener(e -> {
            spinner2.setValue(slider2.getValue());
        });

        slider3.addChangeListener(e -> {
            spinner3.setValue(slider3.getValue());
        });

    }

    SlideSpinPanel(int min, int max, String[] name) {
        this(min, max, min, max, min, max, name);
    }


    void setValues(int values[]) {
        slider1.setValue(values[0]);
        slider2.setValue(values[1]);
        slider3.setValue(values[2]);
    }

    int[] getValues() {
        return new int[]{slider1.getValue(), slider2.getValue(), slider3.getValue()};
    }

    void addCommonListener(ChangeListener al) {
        spinner1.addChangeListener(al);
        spinner2.addChangeListener(al);
        spinner3.addChangeListener(al);
        slider1.addChangeListener(al);
        slider2.addChangeListener(al);
        slider3.addChangeListener(al);
    }

}
