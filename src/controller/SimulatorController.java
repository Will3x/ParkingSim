package controller;

import model.*;
import view.CarParkView;
import model.Car;
import java.awt.*;
import java.util.Queue;


public class SimulatorController {
    private CarParkView view;
    private Model model;

    /**
     * Constructor for SimulatorController. This class controls the info of the main garage simulator.
     * @param view - CarParkView
     * @param model - SimulatorModel
     */
    public SimulatorController(CarParkView view, Model model) {
        this.view = view;
        this.model = model;
    }

    /**Get number of floors*/
    private int getNumberOfFloors() {
        return model.getNumberOfFloors();
    }

    /**
     * Gets the number of rows
     * @return Returns the number of rows.
     */
    private int getNumberOfRows() {
        return model.getNumberOfRows();
    }

    /**
     * Gets the number of places
     * @return Returns the number of places.
     */
    private int getNumberOfPlaces() {
        return model.getNumberOfPlaces();
    }

    /*public int getNumberOfOpenSpots(){
        return model.getNumberOfOpenSpots();
    }*/

    /**
     * Gets the occupant on a location
     * @param location - Location where occupant is.
     * @return car on the location.
     */
    private Car getCarAt(Location location){
        return model.getCarAt(location);
    }

    /**
     * Updates the CarParkView
     */
    public void updateCarParkView() {
        // Create a new car park image if the size has changed.

        if (!view.size.equals(view.getSize())) {
            view.size = view.getSize();
            view.carParkImage = view.createImage(view.size.width, view.size.height);
        }

        Graphics graphics = view.carParkImage.getGraphics();
        graphics.clearRect(0,0, view.carParkImage.getWidth(null), view.carParkImage.getHeight(null));
        updateCarPark(graphics);

        updateEntrance(graphics, getEntranceCarQueue());
        updateEntrance(graphics, getEntrancePassQueue());
        updateEntrance(graphics, getPaymentCarQueue());
        updateEntrance(graphics, getExitCarQueue());

        view.repaint();

    }

    /**
     * Updates the entrances
     * @param graphics - Graphihc to be drawn
     * @param carQueue - CarQueue that needs to be updated
     */
    private void updateEntrance(Graphics graphics, CarQueue carQueue){
        String name = carQueue.getName();
        int queueAmount = carQueue.carsInQueue();
        Queue<Car> queue = carQueue.getQueue();
        if(queueAmount == 0){
            view.drawQueues(graphics, name, 0, Color.black, queueAmount);
        } else {
            int i = 0;
            for (Car car : queue) {
                Color color = car.getColor();
                view.drawQueues(graphics, name, i, color, queueAmount);
                i++;
            }
        }

    }

    private void updateCarPark(Graphics graphics){
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if(car instanceof PrivateReservation && ((PrivateReservation) car).getCar() != null){
                        car = ((PrivateReservation) car).getCar();
                    }
                    Color color = new Color(255, 255, 255  );
                    if (car != null) color = car.getColor();

                    view.drawPlace(graphics, location, color);
                }
            }
        }
    }

    /**
     * Gets the entrance car queue
     * @return Returns the entrance car queue
     */
    private CarQueue getEntranceCarQueue() {
        return model.getEntranceCarQueue();
    }

    /**
     * Gets the entrance pass queue
     * @return Returns the entrance pass queue
     */
    private CarQueue getEntrancePassQueue() {
        return model.getEntrancePassQueue();
    }

    /**
     * Gets the payment car queue
     * @return Returns the entrance payment car queue
     */
    private CarQueue getPaymentCarQueue() {
        return model.getPaymentCarQueue();
    }

    /**
     * Gets the exit car queue
     * @return Returns the exit car queue
     */
    private CarQueue getExitCarQueue() {
        return model.getExitCarQueue();
    }

    // innerclasses die eventhandelen v
    // an button kliks zouden hieronder moeten komen

    /*private void tick() {
        //timeController.updateTime();
        //timeController.setModelTime(model.getDay(), model.getHour(),model.getMinute());
        timeController.updateTime();
        model.handleReservations();
        model.handleExit();
        model.handleEntrance();
        updateViews();
    }

    public void simulate(final int steps) {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int counter = 0;

            @Override
            public void run() {
                if (counter < steps) {
                    counter++;
                    tick();
                    stepInterface.disableStepButtons();
                } else {
                    stepInterface.disableStop();
                    cancel();
                }
            }
        }, 0, 10);

    }*/

}

