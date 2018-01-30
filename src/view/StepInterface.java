package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class StepInterface extends JPanel {
    private JButton start;
    private JButton cont;
    private JButton pause;
    private JButton reset;
    private JButton single;
    private JButton hour;
    private JButton day;

    /**
     * Create the step interface instance and add the buttons in it.
     */
    public StepInterface() {
        addStartButton();
        addContButton();
        addPauseButton();
        addSingleButton();
        addHourButton();
        addDayButton();
        addResetButton();
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

    private void addContButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/Continue.png"));
        cont = new JButton(icon);
        cont.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        cont.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        cont.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        cont.setBorderPainted(false);
        cont.setContentAreaFilled(false);
        cont.setFocusPainted(false);
        cont.setOpaque(false);
        cont.setVisible(false);
        add(cont);
    }

    /**
     * Set the styling of the pause button.
     */
    private void addPauseButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/Pause.png"));
        pause = new JButton(icon);
        pause.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        pause.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        pause.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        pause.setBorderPainted(false);
        pause.setContentAreaFilled(false);
        pause.setFocusPainted(false);
        pause.setOpaque(false);
        add(pause);
    }

    private void addResetButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/Reset.png"));
        reset = new JButton(icon);
        reset.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        reset.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        reset.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setFocusPainted(false);
        reset.setOpaque(false);
        reset.setEnabled(false);
        add(reset);
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
        cont.addActionListener(listener);
    }

    /**
     * Add ActionListener to the pause button.
     *
     * @param listener ActionListener to add.
     */
    public void addPauseListener(ActionListener listener) {
        pause.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        reset.addActionListener(listener);
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
        cont.setEnabled(true);
        pause.setEnabled(false);
        single.setEnabled(true);
        hour.setEnabled(true);
        day.setEnabled(true);
    }

    /**
     * Disable all buttons except the stopbutton.
     */
    public void disableStepButtons() {
        start.setEnabled(false);
        cont.setEnabled(false);
        pause.setEnabled(true);
        single.setEnabled(false);
        hour.setEnabled(false);
        day.setEnabled(false);
    }

    public void disableResetButton(){
        reset.setEnabled(false);
    }

    public void enableResetButton(){
        reset.setEnabled(true);
    }

    public JButton getStart() {
        return start;
    }

    public JButton getCont() {
        return cont;
    }

    public JButton getReset() {
        return reset;
    }
}
