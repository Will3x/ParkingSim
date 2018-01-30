package view;

import controller.ProfitController;

import javax.swing.*;
import java.awt.*;


public class ProfitView extends View {
    private JLabel winnings;
    private JLabel parkedWinnings;
    private ProfitController profit;

    /**
     * Create a JLabel with the winningsStats
     */
    public ProfitView() {
        setLayout(new GridLayout(2, 1));
        winnings = new JLabel("Totale opbrengst: ");
        parkedWinnings = new JLabel("Geparkeerde winst: ");
        profit = new ProfitController(this);

        add(winnings);
        add(parkedWinnings);
    }

    public void setWinningsText(double winnings) {
        this.winnings.setText("Totale opbrengst: " + winnings);
    }

    /**
     * Update the winningsView with the current parkedWinnings.
     * @param parkedWinnings amount of money currently parked
     */
    public void setParkedWinningsText(double parkedWinnings) {
        this.parkedWinnings.setText("Geparkeerde winst: " + parkedWinnings);
    }

    @Override
    public void update() {
        super.update();
    }
}
