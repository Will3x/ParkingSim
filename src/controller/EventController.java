package controller;

import model.Event;
import model.Model;
import view.EventView;

public class EventController {
    private Model model;
    private EventView view;

    public EventController(EventView view, Model model){
        this.view = view;
        this.model = model;
    }
}
