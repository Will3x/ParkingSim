package model;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    private String name;

    /**
     * Constructor for CarQueue. Sets the name of the queue
     * @param name - name of the car queue
     */
    CarQueue(String name ) {this.name = name;}

    /**
     * Adds a car to the queue
     * @param car - Car to be added to the queue
     * @return Returns true if car is added succesfully, false if not
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Removes a car from the queue
     * @return Returns the latest car in the queue
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Return the amount of cars in queue
     * @return Returns the amount of cars in queue
     */
    public int carsInQueue(){
    	return queue.size();
    }

    /**
     * Gets the name
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the queue
     * @return Returns the queue
     */
    public Queue getQueue(){
        return queue; }

}

