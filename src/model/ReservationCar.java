package model;

import java.awt.*;
import java.util.Random;
public class ReservationCar extends Car {
    private static final Color COLOR = new Color(49,188,37);

    ReservationCar(int minutesStaying){
        Random random = new Random();
        int stayMinutes = minutesStaying + random.nextInt(30);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }
    public Color getColor(){
    return COLOR;
    }
}
