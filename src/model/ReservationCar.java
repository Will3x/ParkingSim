package model;

import java.util.Random;

public class ReservationCar extends AdHocCar {

    public ReservationCar(){
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 2 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

}