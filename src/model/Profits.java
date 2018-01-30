package model;

import java.util.HashMap;

public class Profits {
    private double profits;
    private double parkedProfits;
    private HashMap profitStats;
    private Time time;

    private static Profits instance = new Profits(0.0, 0.0);

    public Profits(double profit, double parkedProfit) {
        this.profits = profit;
        this.parkedProfits = parkedProfit;
        this.time = Time.getInstance();

        profitStats = new HashMap();
        resetStats();
    }

    public static Profits getInstance(){
        return instance;
    }

    /**
     * Adds profit
     * @param addition - profit to be added
     */
    public void addWinnings(double addition) {
        this.time = Time.getInstance();
        if (time.getNewWeek()) {
            time.setNewWeek(false);
            resetStats();
        }
        String day = time.getDayString();
        parkedProfits = roundToTwoDecimals(parkedProfits - addition);

        profits = roundToTwoDecimals(profits + addition);
        profitStats.put(day, roundToTwoDecimals((double) profitStats.get(day) + addition));
    }

    /**
     * Adds parked winnings
     * @param addition - winnings to be added
     */
    public void addParkedWinnings(double addition) {
        parkedProfits = roundToTwoDecimals(parkedProfits + addition);
    }

    /**
     * Resets stats.
     */
    public void resetStats() {
        profitStats.put("Monday",    new Double(0.0));
        profitStats.put("Tuesday",   new Double(0.0));
        profitStats.put("Wednesday", new Double(0.0));
        profitStats.put("Thursday",  new Double(0.0));
        profitStats.put("Friday",    new Double(0.0));
        profitStats.put("Saturday",  new Double(0.0));
        profitStats.put("Sunday",    new Double(0.0));

    }

    /**
     * Rounds to two decimals
     * @param value - value to be rounded on two decimals
     * @return Return the value rounded on two decimals
     */
    private double roundToTwoDecimals(double value) {
        return (double) Math.round(value * 100) / 100;
    }

    /**
     * Set the winnings
     * @param winnings - winnings to be set
     */
    public void setWinnings(double winnings) {
        this.profits = winnings;
    }

    /**
     * Gets winnings
     * @return Returns the winnings
     */
    public double getWinnings() {
        return profits;
    }

    /**
     * Gets parked winnings
     * @return Returns parked winnings
     */
    public double getParkedWinnings() {
        return parkedProfits;
    }

    /**
     * Gets winning stats
     * @return Return HashMap with winning stats
     */
    public HashMap getWinningsStats() {
        return profitStats;
    }
}
