package controller;

import java.awt.event.ActionListener;

import model;

public abstract class AbstractController implements ActionListener{
    protected Model model;

    public Model getModel() {
        return model;
    }


    public void setModel(Model model) {
        this.model = model;
    }


}