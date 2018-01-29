package controller;

import view.TimeView;
import model.Time;


public class TimeController {
    private TimeView view;
    private Time time;

    public TimeController(TimeView view) {
        this.view = view;
        this.time = Time.getInstance();
    }

    public void updateTime() {
        time = Time.getInstance();
        time.advanceTime();
        view.setLabel(time.getTimeString());
    }
}
