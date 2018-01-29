package view;

import model.*;

import javax.swing.*;
import java.awt.*;

public class CarParkView extends JPanel {

    public Dimension size;
    public Image carParkImage;
    public SimulatorView simView;
    public Model model;
    public JLabel daysPassed;
    public Time time;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorView simView, Model model) {
        size = new Dimension(0, 0);
        this.simView = simView;
        this.model = model;
        this.daysPassed = new JLabel();
        time = time.getInstance();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(870, 500);
    }

    public void tickDays(){
        daysPassed.setText(time.getTimeString());
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for(int row = 0; row < model.getNumberOfRows(); row++) {
                for(int place = 0; place < 5; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = model.getCarAt(location);
                    Color color = car == null ? Color.lightGray : car.getColor();
                    drawPlace(graphics, location, color);
                }
                for(int place = 5; place < model.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car= model.getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    public void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }

    public void drawQueues(Graphics graphics, String name, int queuePlace, Color color, int queueAmount){
        int x = 75 - 29;
        int y = 370;
        int m = 20;
        switch (name) {
            case "car": y = 390;
                break;
            case "pass": y = 420;
                break;
            case "payment": y = 450;
                break;
            case "exit": y = 480;
        }
        graphics.setColor(Color.BLACK);
        String drawString = queueAmount + " cars in " + name + " queue";
        graphics.drawString(drawString, x, y);
        if(queueAmount > 0) {
            graphics.setColor(color);
            graphics.fillRect(x + queuePlace * m, y+5, 20 - 1, 10 - 1);
        }
    }

}