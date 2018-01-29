package controller;

import model.Winnings;
import view.WinningsView;

import java.util.HashMap;

public class WinningsController {
    private WinningsView view;
    private Winnings model;

    public WinningsController(WinningsView view) {
        this.view = view;
    }

    public void updateWinnings(double amount) {
        model.addWinnings(amount);
    }

    public void updateParkedWinnings(double amount) {
        model.addParkedWinnings(amount);
    }

    public void resetWinnings() {
        model.setWinnings(0.0);
    }

    public void updateView() {
        view.setWinningsText(model.getWinnings());
        view.setParkedWinningsText(model.getParkedWinnings());
    }

    public HashMap getWinningsStats() {
        model = Winnings.getInstance();
        return model.getWinningsStats();
    }

    public WinningsView getWinningsView() {
        return view;
    }
}
