package model;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
    private static final Color COLOR= new Color(37, 95, 188);

    /**
     * Constructor for ParkingPassCar.  Sets the mimutes that a car is going to stay.
     */
    ParkingPassCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        payment = Math.ceil(stayMinutes / 60) * 1.00;
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    /**
     * Gets the color
     * @return Return the color
     */
    public Color getColor(){
        return COLOR;
    }
}
