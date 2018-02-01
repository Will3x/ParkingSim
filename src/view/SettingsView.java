package view;

//import controller.Controller;
//import controller.SettingsController;
import model.Model;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class SettingsView extends JPanel {

    private Dimension size;
    private SimulatorView simSimulatorView;
    private Model model;
//    private SettingsController setCont;

    private JTextField steps;
    private JTextField enterSpeed;
    private JTextField paymentSpeed;
    private JTextField exitSpeed;

    private JLabel stepsLab;
    private JLabel speedLab;
    private JLabel paymentLab;
    private JLabel exitLab;


    protected SettingsView(SimulatorView simSimulatorView) {
        size = new Dimension(0, 0);
        this.simSimulatorView = simSimulatorView;
        this.model = model;

        steps = new JTextField("10078",5);
        enterSpeed = new JTextField("3", 5);
        paymentSpeed = new JTextField("7", 5);
        exitSpeed = new JTextField("5", 5);

        stepsLab = new JLabel("Enter amount of steps: ");
        speedLab = new JLabel("Enter speed: ");
        paymentLab = new JLabel("Enter payment speed: ");
        exitLab = new JLabel("Enter exit speed: ");

        steps.setEnabled(false);
        enterSpeed.setEnabled(false);
        paymentSpeed.setEnabled(false);
        exitSpeed.setEnabled(false);

        add(stepsLab);
        add(steps);
        add(speedLab);
        add(enterSpeed);
        add(paymentLab);
        add(paymentSpeed);
        add(exitLab);
        add(exitSpeed);

        this.setLayout(new GridLayout(4,0,0,4));
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 120);
    }

//    public void addStepListener(){
//        steps.addActionListener(setCont);
//    }

}