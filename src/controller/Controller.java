package controller;

import java.awt.event.ActionEvent;

import model.Model;

public class Controller extends AbstractController {


    public Controller(){

    }

    public Controller(Model model) {
        setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("start")){
            System.out.println("Started");
        }
        if(e.getActionCommand().equals("pause")){

        }
        if(e.getActionCommand().equals("quit")){

        }
        if(e.getActionCommand().equals("step")){

        }
    }

}