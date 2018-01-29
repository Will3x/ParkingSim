package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class SettingsView extends JPanel {

    private Dimension size;
    private SimulatorView simSimulatorView;
    private Model model;
    private Controller controller;

    private JTextField steps;
    private JTextField enterSpeed;
    private JTextField paymentSpeed;
    private JTextField exitSpeed;

    private JLabel stepsLab;
    private JLabel speedLab;
    private JLabel paymentLab;
    private JLabel exitLab;


    protected SettingsView(SimulatorView simSimulatorView, Model model) {
        size = new Dimension(0, 0);
        this.simSimulatorView = simSimulatorView;
        this.model = model;

        steps = new JTextField(10);
        enterSpeed = new JTextField("3", 10);
        paymentSpeed = new JTextField("7", 10);
        exitSpeed = new JTextField("5", 10);

        stepsLab = new JLabel("Enter amount of steps: ");
        speedLab = new JLabel("Enter speed: ");
        paymentLab = new JLabel("Enter payment speed: ");
        exitLab = new JLabel("Enter exit speed: ");

        add(stepsLab);
        add(steps);
        add(speedLab);
        add(enterSpeed);
        add(paymentLab);
        add(paymentSpeed);
        add(exitLab);
        add(exitSpeed);

        steps.addActionListener(controller);

        this.setLayout(new GridLayout(4,0,0,7));
    }

    public Dimension getPreferredSize() {
        return new Dimension(870, 120);
    }

    public int getSteps(String txt) {
        return parseInt(txt);
    }
}