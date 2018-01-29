package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class StepInterface extends JPanel {
    private JButton start;
    private JButton stop;
    private JButton single;
    private JButton hour;
    private JButton day;

    /**
     * Create the step interface instance and add the buttons in it.
     */
    public StepInterface() {
        addStartButton();
        addStopButton();
        addSingleButton();
        addHourButton();
        addDayButton();
    }

    /**
     * Set the styling of the start button.
     */
    private void addStartButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/Start.png"));
        start = new JButton(icon);
        start.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        start.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        start.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);
        start.setFocusPainted(false);
        start.setOpaque(false);
        add(start);
    }

    /**
     * Set the styling of the stop button.
     */
    private void addStopButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/Pause.png"));
        stop = new JButton(icon);
        stop.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        stop.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        stop.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        stop.setBorderPainted(false);
        stop.setContentAreaFilled(false);
        stop.setFocusPainted(false);
        stop.setOpaque(false);
        add(stop);
    }

    /**
     * Set the styling of the single step button.
     */
    private void addSingleButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/a1minute.png"));
        single = new JButton(icon);
        single.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        single.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        single.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        single.setBorderPainted(false);
        single.setContentAreaFilled(false);
        single.setFocusPainted(false);
        single.setOpaque(false);
        add(single);
    }

    /**
     * Set the styling of the hour button.
     */
    private void addHourButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/a60minutes.png"));
        hour = new JButton(icon);
        hour.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        hour.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        hour.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        hour.setBorderPainted(false);
        hour.setContentAreaFilled(false);
        hour.setFocusPainted(false);
        hour.setOpaque(false);
        add(hour);
    }

    /**
     * Set the styling of the day button.
     */
    private void addDayButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/a1day.png"));
        day = new JButton(icon);
        day.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        day.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        day.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        day.setBorderPainted(false);
        day.setContentAreaFilled(false);
        day.setFocusPainted(false);
        day.setOpaque(false);
        add(day);
    }


    /**
     * Add ActionListener to the start button.
     *
     * @param listener ActionListener to add.
     */
    public void addStartListener(ActionListener listener) {
        start.addActionListener(listener);
    }

    /**
     * Add ActionListener to the stop button.
     *
     * @param listener ActionListener to add.
     */
    public void addStopListener(ActionListener listener) {
        stop.addActionListener(listener);
    }

    /**
     * Add ActionListener to the single step button.
     *
     * @param listener ActionListener to add.
     */
    public void addQuartListener(ActionListener listener) {
        single.addActionListener(listener);
    }

    /**
     * Add ActionListener to the hour button.
     *
     * @param listener ActionListener to add.
     */
    public void addHourListener(ActionListener listener) {
        hour.addActionListener(listener);
    }

    /**
     * Add ActionListener to the day button.
     *
     * @param listener ActionListener to add.
     */
    public void addDayListener(ActionListener listener) {
        day.addActionListener(listener);
    }

    /**
     * Enable all buttons except the stopbutton.
     */
    public void disableStop() {
        start.setEnabled(true);
        stop.setEnabled(false);
        single.setEnabled(true);
        hour.setEnabled(true);
        day.setEnabled(true);
    }

    /**
     * Disable all buttons except the stopbutton.
     */
    public void disableStepButtons() {
        start.setEnabled(false);
        stop.setEnabled(true);
        single.setEnabled(false);
        hour.setEnabled(false);
        day.setEnabled(false);
    }
}
