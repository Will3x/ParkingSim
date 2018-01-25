package controller;

import java.awt.event.ActionEvent;
import model.*;

public class Controller extends AbstractController {

    private Thread t;
    private boolean flag = false;

    public Controller(Model model) {
        setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("start")) {

            if (!flag) {
                t = new Thread(() -> model.run());
                t.start();
                System.out.println("Started the simulator");
                flag = true;
            } else {
                System.out.println("Simulator is already running.");
            }
        }

        if (e.getActionCommand().equals("pause")) {
            System.out.println("Paused");
        }

        if (e.getActionCommand().equals("quit")) {
                System.out.println("Stopped the program");
                System.exit(0);
        }

    }
}
