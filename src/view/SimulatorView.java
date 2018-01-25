package view;

import javax.swing.*;
import java.awt.*;

import controller.Controller;
import model.Model;

public class SimulatorView extends JFrame {

    private CarParkView carParkView;
    private SettingsView setView;

    private JPanel toolbar;
    private JButton start;
    private JButton pause;
    private JButton quit;

    public SimulatorView(Model model) {

        carParkView = new CarParkView(this, model);
        setView = new SettingsView(this, model);

        Controller controller = new Controller(model);

        this.toolbar = new JPanel();

        start = new JButton("start");
        pause = new JButton("pause");
        quit = new JButton("quit");

        start.addActionListener(controller);
        pause.addActionListener(controller);
        quit.addActionListener(controller);

        toolbar.add(start);
        toolbar.add(pause);
        toolbar.add(quit);


        Container contentPane = getContentPane();

        contentPane.add(carParkView, BorderLayout.NORTH);
        contentPane.add(toolbar, BorderLayout.SOUTH);
        contentPane.add(setView, BorderLayout.CENTER);

        toolbar.setLayout(new GridLayout(1, 0));

        pack();
        setVisible(true);
        setResizable(false);

        updateView();
    }

    public void updateView() {
        carParkView.updateView();
    }

}
