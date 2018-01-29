package model;

public class Event {
    private String name;
    private int arrivals;
    private int startDay;
    private int startHour;
    private int endDay;
    private int endHour;
    private Boolean running = false;

    /**
     * Constructor for Event. Creates an event
     * @param name - name of the event
     * @param arrivals - amount of cars that are going to be arriving
     * @param startDay - start day of the event
     * @param startHour - start hour of the event
     * @param endDay - end day of the event
     * @param endHour - end hour of the event
     */
    public Event(String name, int arrivals, int startDay, int startHour, int endDay, int endHour){
        this.name = name;
        this.arrivals = arrivals;
        this.startDay = startDay;
        this.startHour = startHour;
        this.endDay = endDay;
        this.endHour = endHour;
    }

    /**
     * Gets the name of the event
     * @return Returns the name of the event
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the start day of the event
     * @return Returns the start day of the event
     */
    public int getStartDay(){
        return startDay;
    }

    /**
     * Gets the start hour of the event
     * @return Returns the start hour of the event
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * Gets the end day of the event
     * @return Returns the name of the event
     */
    public int getEndDay(){
        return endDay;
    }

    /**
     * Gets the end hour of the event
     * @return Returns the end hour of the event
     */
    public int getEndHour(){
        return endHour;
    }

    /**
     * Gets the arrivals of the event
     * @return Returns the arrivals of the event
     */
    public int getArrivals() {
        return arrivals;
    }

    /**
     * Gets if the event is running
     * @return Returns true if event is running, false when not
     */
    public Boolean isRunning(){
        return running;
    }

    /**
     * Sets that the event is running
     * @param running - true if event should be running, false when event should end
     */
    void setRunning(Boolean running){
        this.running = running;
    }
}
