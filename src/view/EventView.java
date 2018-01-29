package view;
import javax.swing.*;
import java.awt.*;

public class EventView extends JPanel {
    private Dimension size;

    public EventView() {
        size = new Dimension(200, 180);
        setMinimumSize(size);
        GridLayout layout = new GridLayout(10, 7);
        setLayout(layout);
    }

    public Dimension getPrefferedSize() {
        return new Dimension(350, 360);
    }

    public void addHeader() {
        GridLayout layout = new GridLayout(0, 7);
        JPanel jPanel = new JPanel();
        //JPanel.setLayout(layout);
        JLabel name = new JLabel("Name");
        JLabel arrivals = new JLabel("Arrivals:");
        JLabel startDay = new JLabel("Start Dy:");
        JLabel startHour = new JLabel("Start Hr:");
        JLabel endDay = new JLabel("End Dy:");
        JLabel endHour = new JLabel("End Hr:");
        JLabel running = new JLabel("Running:");

        jPanel.add(name);
        jPanel.add(arrivals);
        jPanel.add(startDay);
        jPanel.add(startHour);
        jPanel.add(endDay);
        jPanel.add(endHour);
        jPanel.add(running);

        add(jPanel);
    }

    public void drawEvent(model.Event event) {
        GridLayout layout = new GridLayout(0, 7);
        //JPanel jPanel = JPanel();
        //Panel.setLayout(layout);
        //JLabel name = new JLabel(event.getName());
        //JLabel arrivals = JLabel(String.valueOf(event.getArrivals()));
        //JLabel startDay = new JLabel(String.valueOf(event.getStartDay()));
        //JLabel startHour = new JLabel(String.valueOf(event.getStartHour()));
        //JLabel endDay = new JLabel(String.valueOf(event.getEndDay()));
        //JLabel endHour = new JLabel(String.valueOf(event.getEndHour()));
//        JLabel running = new JLabel(event.isRunning().toString());
//
//JPanel.add(name);
// JPanel.add(arrivals);
// JPanel.add(startDay);
// JPanel.add(startHour);
// JPanel.add(endDay);
// JPanel.add(endHour);
// JPanel.add(running);
// add(JPanel);


    }
}