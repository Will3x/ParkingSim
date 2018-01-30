package view;

import javax.swing.*;
import java.awt.*;


public class TimeView extends JPanel {
    private JLabel label;

    /**
     * JLabel that shows the current timestamp in the simulator.
     */
    public TimeView() {
        label = new JLabel(" ");
        setPreferredSize(new Dimension(200,50));
        add(label);
    }

    /**
     * Update the label
     * @param timeString New timestamp.
     */
    public void setLabel(String timeString) {
        label.setText(timeString);
    }

}
