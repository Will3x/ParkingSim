package model;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {

    private Queue<Car> queue = new LinkedList<>();

    private String name;

    CarQueue(String name) {
        this.name = name;
    }

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue() {
        return queue.size();
    }

    public String getName() {
        return name;
    }

    public Queue getQueue() {
        return queue;
    }
}

