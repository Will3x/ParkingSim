package model;

import java.util.HashMap;

public class Profits {
    private double winnings;
    private double parkedWinnings;
    private HashMap winningsStats;
    private Time time;

    private static Profits instance = new Profits(0.0, 0.0);

    /**
     * This class is a Singleton
     * Constructor for Winnings. Holds the winnings
     * @param winnings - winnings
     */
    public Profits(double winnings, double parkedWinnings) {
        this.winnings = winnings;
        this.parkedWinnings = parkedWinnings;
        this.time = Time.getInstance();

        winningsStats = new HashMap();
        resetStats();
    }

    public static Profits getInstance(){
        return instance;
    }

    /**
     * Adds winnings
     * @param addition - winnings to be added
     */
    public void addWinnings(double addition) {
        this.time = Time.getInstance();
        if (time.getNewWeek()) {
            time.setNewWeek(false);
            resetStats();
        }
        String day = time.getDayString();
        parkedWinnings = roundToTwoDecimals(parkedWinnings - addition);

        winnings = roundToTwoDecimals(winnings + addition);
        winningsStats.put(day, roundToTwoDecimals((double) winningsStats.get(day) + addition));
    }

    /**
     * Adds parked winnings
     * @param addition - winnings to be added
     */
    public void addParkedWinnings(double addition) {
        parkedWinnings = roundToTwoDecimals(parkedWinnings + addition);
    }

    /**
     * Resets stats.
     */
    private void resetStats() {
        winningsStats.put("Monday",    new Double(0.0));
        winningsStats.put("Tuesday",   new Double(0.0));
        winningsStats.put("Wednesday", new Double(0.0));
        winningsStats.put("Thursday",  new Double(0.0));
        winningsStats.put("Friday",    new Double(0.0));
        winningsStats.put("Saturday",  new Double(0.0));
        winningsStats.put("Sunday",    new Double(0.0));
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
        this.winnings = winnings;
    }

    /**
     * Gets winnings
     * @return Returns the winnings
     */
    public double getWinnings() {
        return winnings;
    }

    /**
     * Gets parked winnings
     * @return Returns parked winnings
     */
    public double getParkedWinnings() {
        return parkedWinnings;
    }

    /**
     * Gets winning stats
     * @return Return HashMap with winning stats
     */
    public HashMap getWinningsStats() {
        return winningsStats;
    }
}
