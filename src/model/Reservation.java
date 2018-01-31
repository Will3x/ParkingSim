package model;

import java.awt.*;
public abstract class Reservation extends Car{

    public Location getLocation(){
        return super.location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public abstract Color getColor();
}
