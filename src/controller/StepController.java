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
    private ProfitController profit;
    private Profits pro;

    public StepController(final StepInterface stepview, Model model){
        this.stepview = stepview;
        this.model = model;
        pro = pro.getInstance();
        time = time.getInstance();
        sound = new Sound();
        addEventHandelers();
    }

    private void addEventHandelers() {
        stepview.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playSound("click.wav");
                if (paused){
                    model.setSteps(temp);
                } else {
                    model.setSteps(10078);
                }
                stepview.getStart().setVisible(false);
                stepview.getCont().setVisible(true);
                stepview.disableResetButton();
                System.out.println(model.getSteps() + " steps remaining");
            }
        });

        stepview.addPauseListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playSound("click.wav");
                temp = model.getSteps();
                model.setSteps(0);
                paused = true;
                stepview.enableResetButton();
            }
        });

        stepview.addResetListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sound.playSound("click.wav");
                model.removeAllCars();
                time.setDay(0);
                time.setHour(0);
                time.setMinute(0);
                pro.resetStats();
                paused = false;
                stepview.disableStop();
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


