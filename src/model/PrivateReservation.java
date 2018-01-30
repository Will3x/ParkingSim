package model;

import java.awt.*;
import java.util.Random;

public class PrivateReservation extends Car {
    private static Color COLOR=new Color(244, 203, 0);
    private int arrivalDay;
    private int arrivalHour;
    private int arrivalMinute;
    private int tickAmount;
    private int actualArrival = 15;
    private boolean willArrive = true;
    private boolean handled = false;
    private ReservationCar car;
    private int minutesStaying;

    PrivateReservation(int arrivalDay, int arrivalHour, int arrivalMinute, int minutesStaying) {
        Random random = new Random();
        this.arrivalDay = arrivalDay;
        this.arrivalHour = arrivalHour;
        this.arrivalMinute = arrivalMinute;
        this.setMinutesLeft(minutesStaying+30);
        this.minutesStaying = minutesStaying;

        //calculate random arrival time difference
        int difference = ((random.nextInt(40)));
        int rand = random.nextInt(5);

        if (difference >= 30) { //car won't arrive
            willArrive = false;
            this.minutesStaying = 45;
        } else if (rand >= 1){
            actualArrival += difference; //car will arrive difference minutes later
        } else if(rand <= 0){
            actualArrival -= difference; //car will arrive difference minutes earlier
        } else {
            actualArrival = 0; // op tijd
        }
    }

    /**
     * Gets the color
     * @return Returns the color
     */
    public Color getColor(){
        return COLOR;
    }

    /**
     * Gets arrival day
     * @return Returns arrival day
     */
    int getArrivalDay() {
        return arrivalDay;
    }

    /**
     * Gets arrival hour
     * @return Returns the arrival hour
     */
    int getArrivalHour() {
        return arrivalHour;
    }

    /**
     * Gets the minute of arrival
     * @return Returns the minute of arrival
     */
    int getArrivalMinute() {
        return arrivalMinute;
    }

    /**
     * Ticks whenever a minute passes.
     */
    public void tick(){
        super.tick();
        tickAmount++;
    }

    /**
     * Gets if car will arrive
     * @return Returns true if car will arrive, false if not
     */
    boolean getWillArive() {
        return willArrive;
    }


    /**
     * Gets actual arrival
     * @return Returns actual arrival
     */
    public int getActualArrival() {
        return actualArrival;
    }

    /**
     * Gets if reservation is arriving
     * @return Returns true is reservation is arriving, false if not
     */
    boolean isArriving(){
        return tickAmount >= actualArrival;
    }

    /**
     * Gets if reservation is handled
     * @return Returns true is reservation is handled, false if not
     */
    boolean isHandled() {
        return handled;
    }

    /**
     * Sets if reservation should be handled
     * @param handled - true if reservation should be handled, false if not
     */
    void setHandled(boolean handled) {
        this.handled = handled;
    }

    /**
     * Sets the car for the reservation
     * @param car - car to be set
     */
    public void setCar(ReservationCar car){
        this.car = car;
    }

    /**
     * Gets the car
     * @return Returns the car
     */
    public ReservationCar getCar(){
        return car;
    }

    /**
     * Gets the minutes car is staying
     * @return Returns the minutes car is staying
     */
    int getMinutesStaying(){
        return minutesStaying;
    }

}
