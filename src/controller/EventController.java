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

    public void updateView(){
            view.removeAll();

            view.addHeader();
            for (Event event : model.events) {
                view.drawEvent(event);
            }
            view.repaint();
            view.revalidate();
    }
}
