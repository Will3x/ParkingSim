package controller;

import model.Model;
import view.StepInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepController {
    private StepInterface stepview;
    private Model model;

    public StepController(final StepInterface stepview, Model model){
        this.stepview = stepview;
        this.model = model;
        addEventHandelers();
    }

    private void addEventHandelers() {
        stepview.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(10000);
                System.out.println(model.getSteps());
            }
        });

        stepview.addStopListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(0);/*
                timer.cancel();
                stepInterface.disableStop();*/
            }
        });

        stepview.addSingleListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(1);
            }
        });

        stepview.addHourListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(60);
            }
        });

        stepview.addDayListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setSteps(1440);
            }
        });
    }

}
