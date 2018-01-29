package model;

import view.SimulatorView;

import java.awt.*;
import java.util.Random;

public class Model{

    private static final String AD_HOC = "1";
    private static final String PASS = "2";
    private static final String ABBO = "3";

    private Time time;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue entranceAbboQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private Car[][][] cars;
    private Car car;

    private int parkedPassCars;
    private int parkedCars;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;

    public Model(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView( this);
        time = new Time(this);
   }

    public void run(){
        time.run();
    }

    protected void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
//        carsEntering(entranceAbboQueue);
    }

    protected void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    protected void updateViews(){
        tick();
        // Update the car park view.
        simulatorView.updateView();

    }

    private void carsArriving(){
        int numberOfCars=getNumberOfCars(time.weekDayArrivals, time.weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars=getNumberOfCars(time.weekDayPassArrivals, time.weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(time.weekDayAbboArrivals, time.weekendAbboArrivals);
        addArrivingCars(numberOfCars, ABBO);
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue()>0 && getNumberOfOpenSpots()>0 && i<time.enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = getFirstFreeLocation();
            if (car.getColor().equals(Color.orange)) {
                Location freePassLocation = getFirstFreeAbboLocation();
                if (freePassLocation.getFloor() <= freeLocation.getFloor() && freePassLocation.getRow() <= freeLocation.getRow()) {
                    setCarAt(freePassLocation, car);
                } else {
                    setCarAt(freeLocation, car);
                }
                this.parkedPassCars++;
            } else {
                setCarAt(freeLocation, car);
                this.parkedCars++;
            }
            i++;
        }
    }

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

    private void carsPaying(){
        // Let cars pay.
        int i=0;
        while (paymentCarQueue.carsInQueue()>0 && i < time.paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue()>0 && i < time.exitSpeed){
            exitCarQueue.removeCar();
            i++;
        }
    }

    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = (time.getDay() >= 5) ? weekend : weekDay;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new ParkingPassCar());
                }
                break;
            case ABBO:
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new ReservationCar());
                }
                break;
        }
    }

    private void carLeavesSpot(Car car){
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

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
            for (int row = 0; row < getNumberOfRows(); row += 2) {
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

    public Location getFirstFreeAbboLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row += 2) {
                for (int place = 0; place < 5; place++) {

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

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

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

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public int getParkedPassCars() {
        return parkedPassCars;
    }

    public int getParkedCars() {
        return parkedCars;
    }
}
