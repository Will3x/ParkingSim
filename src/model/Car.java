package model;

import java.awt.*;

public abstract class Car extends TimedOccupant{


    private boolean isPaying;
    private boolean hasToPay;
    double payment;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    /**
     * Gets if car is paying
     * @return Return true if car is paying,
     * false when car is not paying
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Sets if the car is paying
     * @param isPaying - True is car should be paying,
     * false if car should not be paying
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Sets if car has to pay
     * @return hasToPay - True if car has to pay, fals if not.
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Abstracht method that gets the color
     * @return color
     */
    public abstract Color getColor();

    /**
     * Gets the payment made by car
     * @return Returns the amount payed
     */
    double getPayment(){
        return payment;
    }
}