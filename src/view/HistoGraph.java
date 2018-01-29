package view;

import java.awt.*;
import java.util.HashMap;

import controller.WinningsController;


public class HistoGraph extends GraphView{
    private int height;
    private int width;
    private int xPos;
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private WinningsController winnings;

    /**
     * Create the HistoGraph instance and apply the arguments to its fields.
     * @param width    Width of the graph.
     * @param height   Height of the graph.
     * @param max      Max value of the graph.
     * @param xPos     starting xposition of the drawing.
     * @param winnings Winningscontroller.
     */
    public HistoGraph (int width, int height ,int max, int xPos, WinningsController winnings) {
        super(width,height,max);
        this.width = width;
        this.height = height;
        this.xPos = xPos + 25;
        this.winnings = winnings;
    }

    /**
     * TODO: scale with the new max value if it gets overridden.
     * repaint....
     */
    //public void update() {
        //super.update();
        /*HashMap stats = winnings.getWinningsStats();

        for (String s : days) {
            if (stats.get(s) > max) {
                max = (int) stats.get(s);
            }
        }*/
        //repaint();
    //}

    /**
     * Repaint every day of the histograph based on the data that it gets from the winningsController.
     * @param g Graphics instance used for the drawing.
     */
    public void paint(Graphics g) {
        super.paint(g);
        int startingHeight  = height - 50;

        HashMap stats = winnings.getWinningsStats();

        g.drawLine(xPos, startingHeight , width - 25, startingHeight);
        int part = ((width - 25) - xPos) / 7;
        int offset = part / 2;

        for (String s : days)
        {
            double value = (double) stats.get(s);
            double rectheight = (double) startingHeight / (double) max * value;
            g.drawString(s, xPos + offset - part / 2, startingHeight + 15);

            g.setColor(Color.RED);
            g.fillRect(xPos + offset - part / 2, startingHeight - (int) rectheight, 50, (int) rectheight);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(value), xPos + offset - part / 2, startingHeight - (int) rectheight - 5);
            offset += part;
        }
    }
}