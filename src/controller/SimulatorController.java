package controller;

import model.*;
import view.CarParkView;
import model.Car;
import view.CarQueueView;

import java.awt.*;
import java.util.Queue;


public class SimulatorController {
    private CarParkView view;
    private Model model;
    private Graphics graphics;
    private CarQueueModel queueModel;
    private CarQueueView queueView;

    /**
     * Constructor for SimulatorController. This class controls the info of the main garage simulator.
     * @param view - CarParkView
     * @param model - SimulatorModel
     */
    public SimulatorController(CarParkView view, CarQueueView queueView, Model model) {
        this.view = view;
        this.model = model;
        this.queueModel = new CarQueueModel();
        this.queueView = queueView;
    }

    public void updateCarParkView() {
        // Create a new car park image if the size has changed.

        if (!view.size.equals(view.getSize())) {
            view.size = view.getSize();
            view.carParkImage = view.createImage(view.size.width, view.size.height);
        }

        graphics = view.carParkImage.getGraphics();
        graphics.clearRect(0,0, view.carParkImage.getWidth(null), view.carParkImage.getHeight(null));
        view.updateView();

        updateEntrance(graphics, getEntranceCarQueue());
        updateEntrance(graphics, getEntrancePassQueue());
        updateEntrance(graphics, getPaymentCarQueue());
        updateEntrance(graphics, getExitCarQueue());

        view.repaint();

    }

    public void updateQueue(){
        graphics.clearRect(0,0, view.carParkImage.getWidth(null), view.carParkImage.getHeight(null));
        view.updateView();
        updateEntrance(graphics, getEntranceCarQueue());
        updateEntrance(graphics, getEntrancePassQueue());
        updateEntrance(graphics, getPaymentCarQueue());
        updateEntrance(graphics, getExitCarQueue());
        queueView.update();
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
            queueModel.drawQueues(graphics, name, 0, Color.black, queueAmount);
        } else {
            int i = 0;
            for (Car car : queue) {
                Color color = car.getColor();
                queueModel.drawQueues(graphics, name, i, color, queueAmount);
                i++;
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

}

