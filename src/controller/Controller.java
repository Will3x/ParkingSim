package controller;

import java.awt.event.ActionEvent;
import model.*;

public class Controller extends AbstractController {

    private boolean flag = false;

    public Controller(Model model) {
        setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("start")){
            while(!flag) {
                Thread thread = new Thread(() -> model.run());
                thread.start();
                flag = true;
            }
        }

        if(e.getActionCommand().equals("pause")) {
            System.out.println("Paused");
        }

        if(e.getActionCommand().equals("quit")){
            System.out.println("Stopped the program");
        }

        if(e.getActionCommand().equals("step")){
            System.out.println("Step");
        }
    }
}