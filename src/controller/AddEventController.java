package controller;

import model.Event;
import model.Model;
import view.addEventView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventController {
    private  Model model;
    private  addEventView view;

    public AddEventController(addEventView view, Model model){
        this.view = view;
        this.model = model;

        view.addAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEvent();
            }
        });
    }

    private void addEvent(){
        Event event = new Event(view.getName(), view.getArrivals(), view.getStartDay(), view.getStartHour(), view.getEndDay(), view.getEndHour());
        model.events.add(event);
    }
}
