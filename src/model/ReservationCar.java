package model;

import java.awt.*;
import java.util.Random;

public class ReservationCar extends Car {
    private static final Color COLOR = Color.orange;

    public ReservationCar(){
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 2 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor(){
    return COLOR;
    }
}
