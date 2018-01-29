package model;

import java.awt.*;
public abstract class Reservation extends Car{
    Reservation(){

    }
    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public abstract Color getColor();
}
