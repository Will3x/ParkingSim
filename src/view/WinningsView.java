package view;

import javax.swing.*;
import java.awt.*;


public class WinningsView extends JPanel {
    private JLabel winnings;
    private JLabel parkedWinnings;

    /**
     * Create a JLabel with the winningsStats
     */
    public WinningsView () {
        setLayout(new GridLayout(2, 1));
        winnings = new JLabel("Opbrengst: ");
        parkedWinnings = new JLabel("Geparkeerde winst: ");

        add(winnings);
        add(parkedWinnings);
    }

    public void setWinningsText(double winnings) {
        this.winnings.setText("Opbrengst: " + winnings);
    }

    /**
     * Update the winningsView with the current parkedWinnings.
     * @param parkedWinnings amount of money currently parked
     */
    public void setParkedWinningsText(double parkedWinnings) {
        this.parkedWinnings.setText("Geparkeerde winst: " + parkedWinnings);
    }
}
