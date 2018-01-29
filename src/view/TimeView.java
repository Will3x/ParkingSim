package view;

import javax.swing.*;


public class TimeView extends JPanel {
    private JLabel label;

    /**
     * JLabel that shows the current timestamp in the simulator.
     */
    public TimeView() {
        label = new JLabel(" ");
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
