package view;

import model.Time;

import javax.swing.*;
import java.awt.*;

public class TimeView extends View {
    private JLabel label;
    private JLabel kAvondLabel;
    private JLabel weekendLabel;
    private JLabel eventLabel;
    private Time time;
    /**
     * JLabel that shows the current timestamp in the simulator.
     */
    public TimeView() {
        label = new JLabel(" ");
        kAvondLabel = new JLabel(" - Koopavond");
        weekendLabel = new JLabel(" - Weekend");
        eventLabel = new JLabel("Evenement: Theater");
        time = time.getInstance();
        setPreferredSize(new Dimension(200,50));
        add(label);
    }

    /**
     * Update the label
     * @param timeString New timestamp.
     */
    public void setTimeLabel(String timeString) {
        label.setText(timeString);
    }

    public void update(){
        if ((time.getDay() ==  3) && (time.getHour() >= 17) && (time.getHour() <= 21)) {
            add(kAvondLabel);
        } else {
            remove(kAvondLabel);
        }

        if((time.getDay() ==  4) && (time.getHour() >= 19) && (time.getHour() <= 22)
                || time.getDay() == 5 && time.getHour() >= 19 && time.getHour() <= 22
                || time.getDay() == 6 && time.getHour() >= 13 && time.getHour() <= 16){
            add(eventLabel);
        } else {
            remove(eventLabel);
        }

        if ((time.getDay() ==  4) && time.getHour() >= 17 || time.getDay() == 5 || time.getDay() == 6 && time.getHour() <= 23) {
            add(weekendLabel);
        } else {
            remove(weekendLabel);
        }
        revalidate();
        repaint();
    }
}

