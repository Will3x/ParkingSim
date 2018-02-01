package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.awt.*;


public class Model
{
    private static final String AD_HOC = "1";
    private static final String PASS = "2";
    private static final String RSVC = "3";

    private boolean reservationsGenerated;  // True after generateWeeklyPrivateReservations, False after resetPrivateReservations

    //controls
    private boolean ocdParking = true;

    //Queues
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    //Simulator default stats
    private int weekDayArrivals= 120;       // average number of arriving AdHoc cars per hour during week
    private int weekendArrivals = 300;      // average number of arriving AdHoc cars per hour during weekend

    private int weekDayReservations = 30;   // average number of PrivateReservations per hour during week
    private int weekendReservations = 50;  // average number of PrivateReservations per hour during weekend

    private int passholderAmmount = 120;    // number of passholders
    private int weekDayPassArrivals= 35;    // average number of arriving PassHolder cars per hour during week
    private int weekendPassArrivals = 25;   // average number of arriving PassHolder cars per hour during weekend

    private int enterSpeed = 3;             // number of cars that can enter per minute
    private int paymentSpeed = 7;           // number of cars that can pay per minute
    private int exitSpeed = 5;              // number of cars that can leave per minute

    private int steps = 0;
    private Profits winningsModel;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int numberOfOpenPassSpots;

    private Time time;
    public Car[][][] cars;
    private Reservation[][][] passReservations;

    private HashMap<String, ArrayList<PrivateReservation>> privateReservations;
    public ArrayList<Event> events;

    /**
     * Constructor for SimulatorModel. Makes the garage.
     * @param numberOfFloors - numbers of floors
     * @param numberOfRows - number of rows
     * @param numberOfPlaces - number of places
     */
    public Model(int numberOfFloors, int numberOfRows, int numberOfPlaces) {

        //Creating Queues
        entranceCarQueue = new CarQueue("car");
        entrancePassQueue = new CarQueue("pass");
        paymentCarQueue = new CarQueue("payment");
        exitCarQueue = new CarQueue("exit");

        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;

        this.numberOfOpenSpots = (numberOfFloors * numberOfRows * numberOfPlaces)-passholderAmmount;
        this.time = model.Time.getInstance();

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        passReservations = new Reservation[numberOfFloors][numberOfRows][numberOfPlaces];

        privateReservations = new HashMap<>();
        events = new ArrayList<>();

        setNumberOfOpenPassSpots(passholderAmmount);

    }

    /**
     * Gets the AdHoc Cars
     * @return adHoc - returns the amount of adHoc cars currently in garage.
     */
    public int getAdHocCars() {
        int adHoc = 0;
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof AdHocCar){
                        adHoc++;
                    }
                }
            }
        }
        return adHoc;
    }

    /**
     * Gets the reservation cars
     * @return Returns the amount of reservation cars currently in garage
     */
    public int getReservationCars() {
        int reservation = 0;
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof ReservationCar){
                        reservation++;
                    }
                }
            }
        }
        return reservation;

    }

    /**
     * Gets the pass cars
     * @return Returns the amount of pass cars currently in garage
     */
    public int getPassCars() {
        int pass = 0;
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof ParkingPassCar){
                        pass++;
                    }
                }
            }
        }
        return pass;
    }

    /**
     * Handles the reservations
     */
    public void handleReservations(){
        if(!reservationsGenerated) generateWeeklyPrivateReservations(weekDayReservations, weekendReservations);
        resetPrivateReservations();
        arrivingPrivateReservations();
        reservePrivateReservations();
    }

    /**
     * Handles the entrance
     */
    public void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    /**
     * Handles the exit
     */
    public void handleExit(){
        privateReservationsExpired();
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Gets first expired reservation
     * @return Returns PrivateReservation that is going to expire first
     */
    private PrivateReservation getFirstExpiredReservation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof PrivateReservation){
                        if(car.getMinutesLeft() <= 0) {
                            return (PrivateReservation) car;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get expired reservation
     */
    private void privateReservationsExpired(){
        // Add leaving cars to the payment queue.
        PrivateReservation reservation = getFirstExpiredReservation();
        while (reservation!=null) {
            removeCarAt(reservation.getLocation());
            reservation = getFirstExpiredReservation();
        }
    }

    public void removeAllCars() {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        removeCarAt(location);
                    }
                }
            }
        }

    /**
     * Handles arriving private reservation
     */
    private void arrivingPrivateReservations(){
        PrivateReservation reservation = getArrivingPrivateReservation();
        while (reservation!=null) {
            if (reservation.isArriving()){
                Car car = new ReservationCar();
                reservation.setHandled(true);
                if (entranceCarQueue.carsInQueue() > 5 && entrancePassQueue.carsInQueue() % 2 == 0) {
                    entrancePassQueue.addCar(car);
                } else {
                    entranceCarQueue.addCar(car);
                }
            }
            reservation = getArrivingPrivateReservation();
        }
    }

    /**
     * Handles cars arriving
     */
    private void carsArriving(){

        int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals, AD_HOC);
        Random rand = new Random();
        double y = (100 + rand.nextInt(20))- entranceCarQueue.carsInQueue() * 7;
        numberOfCars = (int) Math.floor(numberOfCars * y / 100);

        addArrivingCars(numberOfCars, AD_HOC);

        if(getNumberOfOpenPassSpots() > 0) {

            numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals, PASS);

            for(int i = 1; i <= numberOfCars; i++){

                if ((getNumberOfOpenPassSpots() - i - entrancePassQueue.carsInQueue()) >= 0){
                    addArrivingCars(1, PASS);
                } else {
                    break;
                }

            }

        }
    }


    /**
     * Handles car entering
     * @param queue - queue where car is entering
     */

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue()>0 && getNumberOfOpenSpots()>0  && i< enterSpeed) {
            Location freeLocation = null;
            Car car = queue.removeCar();

            if ( car instanceof AdHocCar ) {
                freeLocation = (ocdParking) ? getFirstFreeLocation() : getRandomFreeLocation();
            } else
            if ( car instanceof ParkingPassCar ){
                freeLocation = (ocdParking) ? getFirstFreePassLocation() : getRandomReservedPassLocation();
            } else
            if ( car instanceof ReservationCar ){
                freeLocation = getAssignedReservedLocation((ReservationCar) car);
            }
            setCarAt(freeLocation, car);
            i++;
        }
    }


    /**
     * Hanles cars ready to leave
     */
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()){
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    /**
     * Handles cars paying
     */
    private void carsPaying(){
        // Let cars pay.
        int i=0;
        while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            carLeavesSpot(car);
            i++;
        }
    }

    /**
     * Handles cars leaving
     */
    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
        }
    }
    
    /**
     * generates the amount of cars per hour for the specific type of car
     * @param weekDay - day of the week
     * @param weekend -
     * @param type - type of car
     * @return return int amount of cars this hour
     */
    private int getNumberOfCars(int weekDay, int weekend, String type){

        int averageNumberOfCarsPerHour = 0;
        Random random = new Random();
        time = model.Time.getInstance();
        switch (type){
            case AD_HOC:
                //PARABOLA FORMULE, ZODAT ROND 1500 UUR DRUKKER IS
                double formula = ((-0.069444*Math.pow(time.getHour(),2))+(2.083333*time.getHour()) - 5.625) * 10;
                // Get the average number of cars that arrive per hour.

                if (time.getDay() == 0){ // Maandag
                        averageNumberOfCarsPerHour = (int) Math.floor(weekDay / 100 * formula);
                }
                if (time.getDay() == 1){ // Dinsdag
                    averageNumberOfCarsPerHour = (int) Math.floor(weekDay/100*formula);
                }
                if (time.getDay() == 2){ // Woensdag
                    averageNumberOfCarsPerHour = (int) Math.floor(weekDay/100*formula);
                }
                if (time.getDay() == 3){ // Donderdag
                    if(time.getHour() <= 18) {
                        averageNumberOfCarsPerHour = (int) Math.floor(weekDay / 100 * formula);
                    } else { // Koop avond
                        averageNumberOfCarsPerHour = (int) Math.floor(weekend / 100 * formula);
                    }
                }
                if (time.getDay() == 4){ // Vrijdag
                    if(time.getHour() <= 18) {
                        averageNumberOfCarsPerHour = (int) Math.floor(weekDay / 100 * formula);
                    } else { // Vrijdag avond
                        averageNumberOfCarsPerHour = (int) Math.floor(weekend / 100 * formula);
                    }
                }
                if (time.getDay() == 5){
                    averageNumberOfCarsPerHour = (int) Math.floor(weekend/100*formula);
                }
                if (time.getDay() == 6) {
                    if (time.getHour() <= 19) {
                        averageNumberOfCarsPerHour = (int) Math.floor(weekend / 100 * formula);
                    } else {
                        averageNumberOfCarsPerHour = (int) Math.floor(weekDay / 100 * formula);
                    }
                }
                break;
            case PASS:
                if ( time.getDay() >= 0 && time.getDay() <= 4   ) averageNumberOfCarsPerHour = weekDay; //doordeweeksedag
                if ( time.getDay() >= 5 && time.getDay() <= 6   ) averageNumberOfCarsPerHour = weekend; //weekend
                if ( time.getDay() >= 0 && time.getHour() > 15  ) averageNumberOfCarsPerHour = ((int) Math.floor(weekend * 0.90)); //Doordeweekse avond
                if ( time.getDay() == 3 && time.getHour() > 19  ) averageNumberOfCarsPerHour = weekend; //donderdagavond  (koopavond)
                if ( time.getDay() >= 4 && time.getHour() > 18  ) averageNumberOfCarsPerHour = weekend; //vrijdagavond    (weekend begint)
                if ( time.getDay() <= 3 && time.getHour() <= 5  ) averageNumberOfCarsPerHour = ((int) Math.floor(weekDay * 0.50));   //'s nachts minder druk
                if ( time.getDay() >= 4 && time.getHour() <= 5  ) averageNumberOfCarsPerHour = ((int) Math.floor(weekend * 0.65));   //'s nachts minder druk
                break;

        }

//        //event shit
        Iterator<Event> i = events.iterator();
        while (i.hasNext()) {
            Event event = i.next();
            if(time.getDay() == event.getStartDay() && time.getHour() == event.getStartHour()){
                event.setRunning(true);
            }
            if(time.getDay() == event.getEndDay() && time.getHour() == event.getEndHour()){
                event.setRunning(false);
                i.remove();
            }
            if(event.isRunning()){
                averageNumberOfCarsPerHour = event.getArrivals();
            }
        }
        //System.out.println(events.size());

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    /**
     * Add arriving cars
     * @param numberOfCars - number of cars to be added
     * @param type - type of cars to be added
     */
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        winningsModel = Profits.getInstance();
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    Car car = new AdHocCar();
                    entranceCarQueue.addCar(new AdHocCar());
                    winningsModel.addParkedWinnings(car.getPayment());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    Car car = new ParkingPassCar();
                    entrancePassQueue.addCar(new ParkingPassCar());
                    winningsModel.addParkedWinnings(car.getPayment());
                }
                break;
            case RSVC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new ReservationCar());
                }
                break;
        }
    }

    /**
     * Car leaves spot
     * @param car - Car that leaves the spot
     */
    private void carLeavesSpot(Car car){
        winningsModel = Profits.getInstance();
        removeCarAt(car.getLocation());
        winningsModel.addWinnings(car.getPayment());
        exitCarQueue.addCar(car);
    }

    /**
     * Generates a string from the integers day hour minute
     * If day 1 hour 1 minute 12, format is ddhhmm
     * @param day day to be converted
     * @param hour hour to be converted
     * @param minute minute to be converted
     * @return String ddhhmm
     */

    private String toTimeString(int day, int hour, int minute){
        String dayString = Integer.toString(day);
        String hourString = Integer.toString(hour);
        String minuteString = Integer.toString(minute);

        if (day     <10) dayString = "0"+dayString;
        if (hour    <10) hourString = "0"+hourString;
        if (minute  <10) minuteString = "0"+minuteString;

        return dayString+hourString+minuteString;
    }

    /**
     * Calculates the day hour and minute to a proper ddhhmm format
     * @param day day to be converted
     * @param hour hour to be converted
     * @param minute minute to be converted
     * @return String in ddhhmm format generated by toTimeString();
     */
    private String calculateTimeString(int day, int hour, int minute){
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (minute < 0){
            minute +=60;
            hour--;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (hour < 0){
            hour += 24;
            day--;
        }
        while (day > 6) {
            day -= 7;
        }
        while (day < 0){
            day = 0;
        }

        return toTimeString(day, hour, minute);
    }

    /**
     * Gets the number of floors
     * @return number of floors in parking lot
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Gets the number of rows per floor
     * @return the number of rows per floor in parking lot
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Gets the number of places per row
     * @return the number of places per row
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Gets the number of open spots in the parking lot
     * These open spots only count for Reservation or AdHoc cars
     * @return the number of open spots
     */
    public int getNumberOfOpenSpots(){
        return numberOfOpenSpots;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }


    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 5; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(getRandomFloor(floor, 100), getRandomRow(row, 6), getRandomPlace(place, 30, getNumberOfPlaces()));
                        if (getCarAt(location) == null) {
                            return location;
                        }
                    }
                }
            }
            return null;
        }

    public Location getFirstFreePassLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < 5; place++) {
                    Location location = new Location(floor, getRandomRow(row,4), getRandomPlace(place,4,4));
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    private int getRandomFloor(int floor, int randomBound){
        int newFloor = floor;

        Random rand = new Random();
        int n = rand.nextInt(randomBound);

        if (n < 2 && newFloor+1 < getNumberOfFloors()) {
            newFloor += n;
            return newFloor;
        }
        return floor;
    }

    private int getRandomPlace(int place, int randomBound, int numMaxPlaces){
        int newPlace = place;

        Random rand = new Random();
        int n = rand.nextInt(randomBound);

        if (newPlace + n < numMaxPlaces) {
            newPlace += n;
        }
        return newPlace;
    }

    private int getRandomRow(int row, int randomBound){
        int newRow = row;

        Random rand = new Random();
        int n = rand.nextInt(randomBound);

        if (newRow + n < getNumberOfRows()) {
            newRow += n;
        }
        return newRow;
    }


    /**
     * The getPassReservationAt method gets the PassReservation at given location
     * @param location The location to be searched in
     * @return The Reservation found, null if location not valid or no Reservation found
     */
    private Reservation getPassReservationAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return passReservations[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * The getRandomReservedPassLocation generates a random location in the parking lot
     * @return Location that is randomly chosen.
     */
    private Location getRandomReservedPassLocation() {
        Random rand = new Random();
        int randFloor = 0;
        int randRow = 0;
        int randPlace = 0;

        Location location = new Location(randFloor, randRow, randPlace);
        while(!locationIsValid(location) || getCarAt(location) != null || getPassReservationAt(location) == null){
            location = setLocation(rand, randFloor, randRow, randPlace, location);
        }

        return location;

    }
    /**
     * The getAssignedReservedLocation gets the Assigned reservation for the given ReservationCar.
     * @param car The car that needs it's Reserved location
     * @return Location that is assigned to the ReservationCar
     */
    private Location getAssignedReservedLocation(ReservationCar car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) instanceof PrivateReservation) {
                        ReservationCar assignedCar = ((PrivateReservation) getCarAt(location)).getCar();
                        if(assignedCar != null && assignedCar.equals(car)){
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * The getRandomFreeLocation method generates an randomly chosen free location.
     * @return Location that is randomly choosen
     */
    private Location getRandomFreeLocation() {
        Random rand = new Random();
        int randFloor = 0;
        int randRow = 0;
        int randPlace = 0;

        Location location = new Location(randFloor, randRow, randPlace);
        while(!locationIsValid(location) || getCarAt(location) != null || getPassReservationAt(location) != null){
            location = setLocation(rand, randFloor, randRow, randPlace, location);
        }

        return location;

    }

    /**
     * The getFirstLeavingCar method iterates through the carpark and gets the first leaving car.
     * Done through checking the car's getMinutesLeft method/
     * @return the first car that leaves. null if none
     */
    private Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof PrivateReservation && ((PrivateReservation) car).getCar() != null) {
                        car = ((PrivateReservation) car).getCar();
                    }
                    if(car instanceof Car) {
                        car  = (Car) car;
                        if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                            return car;
                        }
                    }

                }
            }
        }
        return null;
    }

    /**
     * the getArravingPrivateReservation checks if there is any PrivateReservation that's about to arrive
     * @return PrivateReservation that's about to arrive.
     */
    private PrivateReservation getArrivingPrivateReservation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof PrivateReservation) {
                        PrivateReservation reservation = (PrivateReservation) car;
                        if (reservation != null && reservation.isArriving() && reservation.getWillArive() && !reservation.isHandled()) {
                            return reservation;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * The tick method iterates through the whole parking lot.
     * The method triggers the tick method of every TimedOccupant in the parking lot.
     */
    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     * The locationIsValid method checks if a given location is exists.
     * @param location Location to checked.
     * @return boolean true if location is valid
     */
    private boolean locationIsValid(Location location) {
        if(location == null) {return false;}
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return !(floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces);
    }

    /**
     * The getNumberOfOpenPassSpots gets the number of open Passholder spots
     * @return int The number of open Passholder spots.
     */
    private int getNumberOfOpenPassSpots() {
        return numberOfOpenPassSpots;
    }

    /**
     * The setNumberOfOpenPassSpots method set the number of unoccupied Passholder spots
     * @param numberOfOpenPassSpots the number of open passholder Spots
     */
    private void setNumberOfOpenPassSpots(int numberOfOpenPassSpots) {
        this.numberOfOpenPassSpots = numberOfOpenPassSpots;
    }

    /**
     * The setPrivateReservations method stores the given PrivateReservation
     * @param reservation
     */
    private void setPrivateReservations(PrivateReservation reservation){

        String timeString = calculateTimeString(    reservation.getArrivalDay(),
                reservation.getArrivalHour(),
                reservation.getArrivalMinute()-15
        );

        if( privateReservations.get(timeString) == null ){

            ArrayList<PrivateReservation> reservationList = new ArrayList<>();
            reservationList.add(reservation);
            privateReservations.put(timeString, reservationList);

        } else {

            ArrayList<PrivateReservation> reservationList = privateReservations.get(timeString);
            if(!reservationList.contains(reservation)) reservationList.add(reservation);

        }

    }

    /**
     * The getPrivateReservations method gets an ArrayList with PrivateReservations at the current day,hour and minute.
     * @return ArrayList with PrivateReservations.
     */
    private ArrayList getPrivateReservations(){
        time = model.Time.getInstance();
        return privateReservations.get(calculateTimeString(time.getDay(), time.getHour(), time.getMinute()));
    }


    /**
     * The reservePrivateReservations method reserves spots in the parking lot.
     * Done by iterating through an array
     */
    private void reservePrivateReservations(){
        ArrayList<PrivateReservation> reservationsArray = getPrivateReservations();
        if(reservationsArray != null) {
            for (PrivateReservation reservation : reservationsArray) {
                Location freeLocation = (ocdParking) ? getFirstFreeLocation() : getRandomFreeLocation();
                setCarAt(freeLocation, reservation);
            }
        }
    }

    private Location setLocation(Random rand, int randFloor, int randRow, int randPlace, Location location) {
        randFloor = rand.nextInt(getNumberOfFloors());
        location.setFloor(randFloor);

        randRow = rand.nextInt(getNumberOfRows());
        location.setRow(randRow);

        randPlace = rand.nextInt(getNumberOfPlaces());
        location.setPlace(randPlace);

        return location;
    }



    /**
     * This method empties the HashMap.
     * reservationsGenerated is set to false.
     */
    private void resetPrivateReservations() {
        if(time.getTimeString().equals("Sunday: 23:59")){
            privateReservations.clear();
            privateReservations = new HashMap<>();
            reservationsGenerated = false;
        }
    }

    /**
     * generates the reservations for the coming week
     * day 0 == monday 1 == tuesday etc etc
     * @param weekDay The average amount of reservations throughout the week
     * @param weekend The average amount of reservations in the weekend
     */
    private void generateWeeklyPrivateReservations(int weekDay, int weekend) {
        double averageNumberOfCarsPerHour;
        Random rand = new Random();

        // Get the average number of cars that arrive per hour during week.
        // Maandag t/m woensdag
        for(int day = 0; day < 3; day++) {
            for (int hour = 6; hour < 23; hour++) {
                int r = rand.nextInt(3);
                averageNumberOfCarsPerHour = weekDay;
                if (hour < 18 ){createReservations(averageNumberOfCarsPerHour*0.01, day, hour,r) ;}
                if (hour == 18){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, r) ;}
                if (hour == 19){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, r) ;}
                if (hour == 20){createReservations(averageNumberOfCarsPerHour*0.1, day, hour,r) ;}
                if (hour == 21){createReservations(averageNumberOfCarsPerHour*0.1, day, hour,r) ;}
                if (hour > 21 ){createReservations(averageNumberOfCarsPerHour*0.1, day, hour,r) ;}
            }
        }

        // Donderdag
        for(int day = 3; day < 4; day++) {
            for (int hour = 6; hour < 22; hour++) {
                int r = rand.nextInt(2);
                averageNumberOfCarsPerHour = weekDay;
                if (hour < 18){createReservations(averageNumberOfCarsPerHour*0.1, day, hour,r) ;}
                if (hour == 18){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, r) ;}
                if (hour == 19){createReservations(averageNumberOfCarsPerHour*0.7, day, hour, r) ;}
                if (hour == 20){createReservations(averageNumberOfCarsPerHour*0.5, day, hour,r) ;}
                if (hour == 21){createReservations(averageNumberOfCarsPerHour*0.4, day, hour,r) ;}
                if (hour > 21 ){createReservations(averageNumberOfCarsPerHour*0.1, day, hour,r) ;}
            }
        }

        // Vrijdag
        for(int day = 4; day < 5; day++){ // Vrijdag
            for (int hour = 6; hour < 23; hour++) {
                int r = rand.nextInt(4);
                averageNumberOfCarsPerHour = weekDay;
                if (hour < 18){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, r) ;}
                averageNumberOfCarsPerHour = weekend;
                if (hour == 18){createReservations(averageNumberOfCarsPerHour*0.5, day, hour, r) ;}
                if (hour == 19){createReservations(averageNumberOfCarsPerHour*0.8, day, hour, 4) ;}
                if (hour == 20){createReservations(averageNumberOfCarsPerHour*0.8, day, hour, r) ;}
                if (hour == 21){createReservations(averageNumberOfCarsPerHour*0.5, day, hour, r) ;}
                if (hour == 22){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, 1) ;}
                if (hour == 22){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, 0) ;}
            }
        }

        // Zaterdag
        for(int day = 5; day < 6; day++){
            for (int hour = 6; hour < 22; hour++) {
                int r = rand.nextInt(5);
                averageNumberOfCarsPerHour = weekend;
                if (hour < 12){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, r) ;}
                if (hour == 12){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, r) ;}
                if (hour == 13){createReservations(averageNumberOfCarsPerHour*0.6, day, hour, r) ;}
                if (hour == 14){createReservations(averageNumberOfCarsPerHour*0.7, day, hour, r) ;}
                if (hour == 15){createReservations(averageNumberOfCarsPerHour*0.4, day, hour, r) ;}
                if (hour == 16){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, r) ;}
                if (hour == 17){createReservations(averageNumberOfCarsPerHour*0.4, day, hour, r) ;}
                if (hour == 18){createReservations(averageNumberOfCarsPerHour*0.5, day, hour, r) ;}
                if (hour == 19){createReservations(averageNumberOfCarsPerHour*0.5, day, hour, r) ;}
                if (hour == 20){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, r) ;}
                if (hour > 21 ){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, r) ;}
            }
        }

        // Zondag
        for(int day = 6; day < 7; day++){
            for (int hour = 6; hour < 22; hour++) {
                int r = rand.nextInt(5);
                averageNumberOfCarsPerHour = weekDay;
                if (hour < 12){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, r) ;}
                if (hour == 12){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, r) ;}
                if (hour == 13){createReservations(averageNumberOfCarsPerHour*0.8, day, hour, r) ;}
                if (hour == 14){createReservations(averageNumberOfCarsPerHour*0.9, day, hour, 4) ;}
                if (hour == 15){createReservations(averageNumberOfCarsPerHour*0.4, day, hour, r) ;}
                if (hour == 16){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, r) ;}
                if (hour == 17){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, r) ;}
                if (hour > 17 && hour <= 21 ){createReservations(averageNumberOfCarsPerHour*0.1, day, hour, r) ;}
            }
        }

        reservationsGenerated = true;
    }

    /**
     * This method create's reservations and passes them through to @setPrivateReservations()
     * @param averageNumberOfCarsPerHour    amount of cars per hour
     * @param eventDay                      Day of the event that causes the reservations
     * @param fromHour                      Hour of the event that causes the reservations
     * @param eventLength                   Length of the event that causes the reservations
     */
    private void createReservations(double averageNumberOfCarsPerHour, int eventDay, int fromHour, Integer eventLength){
        Random random = new Random();
        eventLength = (eventLength == null) ? random.nextInt(180) : (eventLength * 60) + random.nextInt(30); //eventLength to minutes
        fromHour -= 1;

        for (int car = 0; car < averageNumberOfCarsPerHour; car++){
            PrivateReservation a = new PrivateReservation(eventDay, fromHour, random.nextInt(59), 15+eventLength);
            setPrivateReservations(a);
        }
    }

    /**
     * @return CarQueue entranceCarQueue
     */
    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    /**
     * @return CarQueue EntrancePassQueue
     */
    public CarQueue getEntrancePassQueue() {
        return entrancePassQueue;
    }

    /**
     * @return CarQueue PaymentCarQueue
     */
    public CarQueue getPaymentCarQueue() {
        return paymentCarQueue;
    }

    /**
     * @return CarQueue exitCarQueue
     */
    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }

    /**
     * @return steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Reduces steps field with 1.
     */
    public void reduceOneStep() {
        this.steps--;
    }

    /**
     * @param steps steps to be set
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }
}