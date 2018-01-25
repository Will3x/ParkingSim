package view;

import model.Model;

import javax.swing.*;
import java.awt.*;

public class PiechartView extends JPanel {

    private Dimension size;
    private SimulatorView simView;
    private Model model;

    public PiechartView(SimulatorView simView, Model model) {
        size = new Dimension(0, 0);
        this.simView = simView;
        this.model = model;
        setBackground(Color.lightGray);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }


}
