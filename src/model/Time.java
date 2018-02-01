package model;

public class Time {

    private Model model;
    private String[] dayStrings;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private boolean newWeek = false;

    private static Time instance = new Time();

    public Time(){
        dayStrings = new String[7];
        dayStrings[0] = "Monday";
        dayStrings[1] = "Tuesday";
        dayStrings[2] = "Wednesday";
        dayStrings[3] = "Thursday";
        dayStrings[4] = "Friday";
        dayStrings[5] = "Saturday";
        dayStrings[6] = "Sunday";
    }

    private int tickPause = 100;
    private int steps = 0;

    /**
     * The time class is a singleton. This function returns the instance of time class.
     * @return Instance of the time class.
     */
    public static Time getInstance(){
        return instance;
    }

    /**
     * Returns the current day.
     * @return the current day.
     */
    public int getDay(){
        return day;
    }

    /**
     * Return the current hour.
     * @return the current hour.
     */
    public int getHour(){
        return hour;
    }

    /**
     * Return the current minute.
     * @return the current minute.
     */
    int getMinute(){
        return minute;
    }

    public Time(Model model){
        this.model = model;
    }

    public void run() {
        for (int i = 0; i < steps; i++) {
            tick();
        }
    }

    private void tick() {
        advanceTime();
        model.handleExit();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model.handleEntrance();
    }

    public void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
            newWeek = true;
        }
    }

    /**
     * Return the current day in the string format.
     * @return current day.
     */
    String getDayString(){
        return  dayStrings[day];
    }

    public String getTimeString(){
        String hourString = hour <10 ? "0" + hour : "" + hour;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        return getDayString() + ": " + hourString + ":" + minuteString;
    }

    boolean getNewWeek(){
        return newWeek;
    }

    void setNewWeek(boolean newWeek){
        this.newWeek = newWeek;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}