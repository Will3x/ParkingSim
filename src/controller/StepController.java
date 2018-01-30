package controller;

import model.Model;
import model.Profits;
import model.Time;
import view.SimulatorView;
import view.StepInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepController {
    private Sound sound;
    private StepInterface stepview;
    private Model model;
    private boolean paused = false;
    private int temp = 0;
    private Time time;
    private Profits profit;

    public StepController(final StepInterface stepview, Model model){
        this.stepview = stepview;
        this.model = model;
        time = time.getInstance();
        sound = new Sound();
        addEventHandelers();
    }

    private void addEventHandelers() {
        stepview.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paused){
                    model.setSteps(temp);
                } else {
                    model.setSteps(10078);
                }
                sound.playSound("click.wav");
                stepview.getStart().setVisible(false);
                stepview.getCont().setVisible(true);
                stepview.enableResetButton();
                System.out.println(model.getSteps() + " steps remaining");
            }
        });

        stepview.addStopListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp = model.getSteps();
                model.setSteps(0);
                paused = true;
                sound.playSound("click.wav");
            }
        });

        stepview.addResetListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playSound("click.wav");
            }
        });

        stepview.addQuartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(15);
                sound.playSound("click.wav");
            }
        });

        stepview.addHourListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(60);
                sound.playSound("click.wav");
            }
        });

        stepview.addDayListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(1440);
                sound.playSound("click.wav");
            }
        });
    }
}


