package controller;

import model.Profits;
import view.ProfitView;

import java.util.HashMap;

public class ProfitController {
    private ProfitView view;
    private Profits model;

    public ProfitController(ProfitView view) {
        this.view = view;
    }

    public void updateView() {
        view.setWinningsText(model.getWinnings());
        view.setParkedWinningsText(model.getParkedWinnings());
    }

    public HashMap getWinningsStats() {
        model = Profits.getInstance();
        return model.getWinningsStats();
    }

    public ProfitView getProfitsView() {
        return view;
    }
}
