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
    private static final String ABBO = "3";

    private boolean reservationsGenerated;  // True after generateWeeklyPrivateReservations, False after resetPrivateReservations

    //controls
    private boolean ocdParking = true;

    //Queues
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    //Simulator default stats
    private int weekDayArrivals= 110;       // average number of arriving AdHoc cars per hour during week
    private int weekendArrivals = 300;      // average number of arriving AdHoc cars per hour during weekend

    private int weekDayReservations = 40;   // average number of PrivateReservations per hour during week
    private int weekendReservations = 100;  // average number of PrivateReservations per hour during weekend

    private int passholderAmmount = 120;    // number of passholders
    private int weekDayPassArrivals= 30;    // average number of arriving PassHolder cars per hour during week
    private int weekendPassArrivals = 50;   // average number of arriving PassHolder cars per hour during weekend

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

    private model.Time time;
    public Car[][][] cars;
    private Reservation[][][] passReservations;
    private int parkedPassCars;
    private int parkedCars;

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

        reserveSpots(passholderAmmount);
        setNumberOfOpenPassSpots(passholderAmmount);

    }
    public void run(){
        time.run();
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
//        carsEntering(entranceAbboQueue);
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

    /**
     * Reserves spot for reservation
     * @param amount - amount of reservation to be reserved
     */
    private void reserveSpots(int amount){
        for(int i = 0; i < amount; i++){
            Reservation reservation = new PassReservation();
            Location location = getLastFreeLocation();
            setPassReservationAt(location, reservation);
        }
    }

    /**
     * Reservers pass spots
     * @param amount - amount of reservations to be reserved
     */
    private void removePassSpots(int amount){
        for(int i = 0; i < amount; i++){
            Location location = getFirstReservedPassLocation();
            removePassReservationAt(location);
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
                reservation.setCar((ReservationCar) car);
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

                if (time.getDay() == 0){              // Maandag
                    averageNumberOfCarsPerHour = (int) Math.floor(weekDay/100*formula);
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
                if (time.getDay() == 6){
                    averageNumberOfCarsPerHour = (int) Math.floor(weekend/100*formula);
                }
                break;
            case PASS:
                if ( time.getDay() >= 0 && time.getDay() <= 4   ) averageNumberOfCarsPerHour = weekDay; //doordeweeksedag
                if ( time.getDay() >= 4 && time.getDay() <= 6   ) averageNumberOfCarsPerHour = weekend; //weekend
                if ( time.getDay() == 3 && time.getHour() > 19  ) averageNumberOfCarsPerHour = weekend; //donderdagavond  (koopavond)
                if ( time.getDay() >= 4 && time.getHour() > 19  ) averageNumberOfCarsPerHour = weekend; //vrijdagavond    (weekend begint)
                if ( time.getDay() <= 3 && time.getHour() <= 5    ) averageNumberOfCarsPerHour = ((int) Math.floor(weekDay * 0.40));   //'s nachts minder druk
                if ( time.getDay() >= 4 && time.getHour() <= 5    ) averageNumberOfCarsPerHour = ((int) Math.floor(weekend * 0.60));   //'s nachts minder druk
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

    /*public ArrayList<Event> getEvents(){
        return events;
    }
*/
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
            case ABBO:
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new ReservationCar());
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
            if (car.getColor().equals(Color.blue)){
                parkedPassCars++;
            } else {
                parkedCars++;
            }
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public void addNewCar(Integer amount, String type){
        if(amount == null) amount = 1;

        Car car = null;
        Location location = null;
        int freelocations = 0;

        for (int i = 0; i < amount; i++) {
            switch (type) {
                case "ADHOC":
                    car = new AdHocCar();
                    location = getFirstFreeLocation();
                    freelocations = getNumberOfOpenSpots();
                    freelocations = freelocations - i;
                    break;
                case "PASS":
                    car = new ParkingPassCar();
                    location = getFirstReservedPassLocation();
                    freelocations = getNumberOfOpenPassSpots();
                    freelocations = freelocations - i;
                    break;
                case "RSRV":
                    Time time = Time.getInstance();
                    int day = time.getDay();
                    int hour = time.getHour()+2;
                    if((getNumberOfOpenSpots()-i) > 0) {
                        createReservations(1.0, day, hour, null);
                    }

            }
            if( freelocations > 0 && car != null && location != null ){
                setCarAt(location, car);
            }
        }


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
            for (int row = 0; row < getNumberOfRows(); row += 1) {
                    for (int place = 5; place < getNumberOfPlaces(); place++) {

                        Random rand = new Random();
                        int n = rand.nextInt(4);
                        int y = rand.nextInt(30);
                        int newRow = row;
                        int newPlace = place;

                        if (row + n < getNumberOfRows() && place + y < getNumberOfPlaces()) {
                            newRow += n;
                            newPlace += y;
                        }

                        Location location = new Location(floor, newRow, newPlace);
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
            for (int row = 0; row < getNumberOfRows(); row += 2) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {

                    Random rand = new Random();
                    int n = rand.nextInt(4);
                    int y = rand.nextInt(4);
                    int newRow = row;
                    int newPlace = place;

                    if (row + n < getNumberOfRows() && place + y < getNumberOfPlaces()) {
                        newRow += n;
                        newPlace += y;
                    }
                    Location location = new Location(floor, newRow, newPlace);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
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
     * The setPassReservationAt method sets a Reservation at the given Location
     * @param location The location of the Reservation
     * @param reservation Reservation to be stored on Location
     * @return boolean true if success
     */
    private boolean setPassReservationAt(Location location, Reservation reservation) {
        if (!locationIsValid(location)) {
            return false;
        }
        Reservation oldReservation = getPassReservationAt(location);
        if (oldReservation == null) {
            passReservations[location.getFloor()][location.getRow()][location.getPlace()] = reservation;
            reservation.setLocation(location);
            return true;
        }
        return false;
    }

    /**
     * The removePassReservationAt method removes a PassReservation at given location
     * @param location The location where PassReservation needs to be removed
     * @return the Reservation that has been removed
     */
    private Reservation removePassReservationAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Reservation reservation = getPassReservationAt(location);
        if (reservation == null) {
            return null;
        }
        passReservations[location.getFloor()][location.getRow()][location.getPlace()] = null;
        reservation.setLocation(null);
        return reservation;
    }

    /**
     * Gets the first free location that's reserved for pass holders.
     * @return a free location for passholders only.
     */
    private Location getFirstReservedPassLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && getPassReservationAt(location) != null) {
                        return location;
                    }
                }
            }
        }
        return null;
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
     * The getLastFreeLocation gets the last free location in the parking lot
     * @return free location that is the last spot in parking lot
     */
    private Location getLastFreeLocation() {
        for (int floor = (getNumberOfFloors()-1); floor >= 0; floor--) {
            for (int row = (getNumberOfRows()-1); row >= 0; row--) {
                for (int place = (getNumberOfPlaces()-1); place >= 0; place--) {
                    Location location = new Location(floor, row, place);
                    if(getCarAt(location) == null && getPassReservationAt(location) == null){
                        return location;
                    }
                }
            }
        }
        return null;
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
                Location freeLocation = (ocdParking) ? getLastFreeLocation() : getRandomFreeLocation();
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
     * generates the reservations for the comming week
     * day 0 == monday 1 == tuesday etc etc
     * @param weekDay The average amount of reservations throughout the week
     * @param weekend The average amount of reservations in the weekend
     */
    private void generateWeeklyPrivateReservations(int weekDay, int weekend) {
        Random rand = new Random();
        double averageNumberOfCarsPerHour = 0;
        //PARABOLA FORMULE, ZODAT ROND 1500 UUR DRUKKER IS

        // Get the average number of cars that arrive per hour during week.
        for(int day = 0; day < 4; day++) {
            for (int hour = 18; hour < 22; hour++) {
                averageNumberOfCarsPerHour = weekDay;
                int eventLength = 23-hour;
                if (hour == 18){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, eventLength) ;}
                if (hour == 19){createReservations(averageNumberOfCarsPerHour*0.6, day, hour, eventLength) ;}
                if (hour == 20){createReservations(averageNumberOfCarsPerHour*1.0, day, hour, eventLength) ;}
                if (hour == 21){createReservations(averageNumberOfCarsPerHour*0.5, day, hour, eventLength) ;}
            }
        }

        for(int day = 4; day < 5; day++){
            for (int hour = 18; hour < 22; hour++) {
                averageNumberOfCarsPerHour = weekend;
                if (hour == 18){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, 2) ;}
                if (hour == 19){createReservations(averageNumberOfCarsPerHour*0.6, day, hour, 2) ;}
                if (hour == 20){createReservations(averageNumberOfCarsPerHour*1.0, day, hour, 2) ;}
                if (hour == 21){createReservations(averageNumberOfCarsPerHour*0.5, day, hour, 2) ;}
                if (hour == 22){createReservations(averageNumberOfCarsPerHour*0.3, day, hour, 2) ;}
            }
        }

        for(int day = 5; day < 7; day++){
            for (int hour = 12; hour < 18; hour++) {
                averageNumberOfCarsPerHour = weekend;
                if (hour == 12){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, 2) ;}
                if (hour == 13){createReservations(averageNumberOfCarsPerHour*1.0, day, hour, 2) ;}
                if (hour == 14){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, 2) ;}
                if (hour == 15){createReservations(averageNumberOfCarsPerHour*1.0, day, hour, 2) ;}
                if (hour == 16){createReservations(averageNumberOfCarsPerHour*0.2, day, hour, 2) ;}
                if (hour == 16){createReservations(averageNumberOfCarsPerHour*1.0, day, hour, 2) ;}
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

    /**
     * Switches ordered parking on or off.
     */
    public void switchOCDParking (){
        ocdParking = (!ocdParking);
    }

    /**
     * raises weekday AdHoc arrivals with 10
     */
    public void plusWeekDayArrivals() {
        weekDayArrivals += 10;
    }

    /**
     * lowers weekday AdHoc arrivals with 10
     */
    public void minusWeekDayArrivals() {
        if((weekDayArrivals-10) > 0) weekDayArrivals -= 10;
    }

    /**
     * raises weekend AdHoC arrivals with 10
     */
    public void plusWeekendArrivals() {
        weekendArrivals += 10;
    }

    /**
     * lowers weekend AdHoc arrivals with 10
     */
    public void minusWeekendArrivals() {
        if((weekendArrivals-10) > 0) weekendArrivals -= 10;
    }

    /**
     * Raises weekday reservation arrivals with 10
     */
    public void plusWeekDayReservations() {
        weekDayReservations += 10;
    }

    /**
     * Lowers weekday reservation arrivals with 10
     */
    public void minusWeekDayReservations() {
        if((weekDayReservations - 10) > 0){ weekDayReservations -= 10; }
    }

    /**
     * rauses weekend reservation arrivals with 10
     */
    public void plusWeekendReservations() {
        weekendReservations += 10;
    }

    /**
     * lowers weekend reservation arrivals with 10
     */
    public void minusWeekendReservations() {
        if((weekendReservations - 10) > 0){ weekendReservations -= 10; }
    }

    /**
     * raises the weekday pass arrivals with 10
     */
    public void plusWeekDayPassArrivals() {
        weekDayPassArrivals += 10;
    }

    /**
     * lowers the weekday pass arrivals with 10
     */
    public void minusWeekDayPassArrivals() {
        if((weekDayPassArrivals - 10) > 0){ weekDayPassArrivals -= 10; }
    }

    /**
     * raises the weekend pass arrivals with 10
     */
    public void plusWeekendPassArrivals() {
        weekendPassArrivals += 10;
    }

    /**
     * lowers the weekday pass arrivals with 10
     */
    public void minusWeekendPassArrivals() {
        if((weekendPassArrivals - 10) > 0) {weekendPassArrivals -= 10;}
    }

    /**
     * raises the amount of pass holders with 10
     */
    public void plusPassholderAmmount() { passholderAmmount += 10; reserveSpots(10);
    }

    /**
     * lowers the amount of passholders with 10
     */
    public void minusPassholderAmmount() {
        if((passholderAmmount - 10) > 0) { passholderAmmount -= 10; removePassSpots(10); }
    }

    /**
     * Raises the entrance queue speeds with 1 car/hour
     */
    public void plusEnterSpeed() {
        enterSpeed += 1;
    }

    /**
     * Lowers the entrance queue speeds with 1 car/hour
     */
    public void minusEnterSpeed() {
        if((enterSpeed - 1) > 0) {
            enterSpeed -= 1;
        }
    }

    /**
     * raises the payment queue speed with 1 car/hour
     */
    public void plusPaymentSpeed() {
        paymentSpeed += 1;
    }

    /**
     * lowers the payment queue speed with 1 car/hour
     */
    public void minusPaymentSpeed() {
        if ((paymentSpeed - 1) > 0) { paymentSpeed -= 1; }
    }

    /**
     * raises the exit queue speed with 1 car/hour
     */
    public void plusExitSpeed() {
        exitSpeed += 1;
    }

    /**
     * lowers the exit queue speed with 1 car/hour
     */
    public void minusExitSpeed() {
        if((exitSpeed - 1) > 0 ) { exitSpeed -= 1; }
    }


}