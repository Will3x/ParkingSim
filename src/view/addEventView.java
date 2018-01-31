package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class addEventView {
    private JFrame frame;
    private JPanel panel;
    private JButton addButton = new JButton("Add Event");

    private JLabel nameL, arrivalsL, startDayL, startHourL, endDayL, endHourL, EndDayL;
    private JTextField nameI, arrivalsI, startDayI, startHourI, endDayI, endHourI;

    public void makeFrame(){
        frame = new JFrame("Add event");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        makePanel();
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void makePanel(){
        panel = new JPanel(new GridLayout(4,2));
        nameL = new JLabel("Name");
        nameI = new JTextField(14);
        arrivalsL = new JLabel("Arrivals:");
        arrivalsI = new JTextField(4);
        startDayL = new JLabel("Start Day:");
        startDayI = new JTextField(1);
        endDayL = new JLabel("End Day:");
        endDayI = new JTextField(1);
        startHourL = new JLabel("Start Hour:");
        startHourI = new JTextField(1);
        endHourL = new JLabel("End Hour:");
        endHourI = new JTextField(1);

        panel.add(nameL);
        panel.add(nameI);
        panel.add(arrivalsL);
        panel.add(arrivalsI);
        panel.add(startDayL);
        panel.add(startDayI);
        panel.add(endDayL);
        panel.add(endDayI);
        panel.add(startHourL);
        panel.add(startHourI);
        panel.add(endHourL);
        panel.add(endHourI);

        panel.add(addButton);
    }



    /**
     * Gets the name of the event
     * @return Returns the name of the event
     */
    public String getName(){
        return nameI.getText();
    }

    /**
     * Gets the amount of arrivals of the event
     * @return Returns the amount of arrivals of the event
     */
    public Integer getArrivals(){
        return Integer.parseInt(arrivalsI.getText());
    }

    /**
     * Gets the start day of the event
     * @return Returns the start day of the event
     */
    public Integer getStartDay(){
        return Integer.parseInt(startDayI.getText());
    }

    /**
     * Gets the start hour of the event
     * @return Returns the start hour of the event
     */
    public Integer getStartHour(){
        return Integer.parseInt(startHourI.getText());
    }

    /**
     * Gets the end day of the event
     * @return Returns the end day of the event
     */
    public Integer getEndDay(){
        return Integer.parseInt(endDayI.getText());
    }

    /**
     * Gets the end day of the event
     * @return Returns the end day of the event
     */
    public Integer getEndHour(){
        return Integer.parseInt(endHourI.getText());
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void addAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }


}

