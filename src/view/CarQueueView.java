package view;

import java.awt.*;

public class CarQueueView extends View {

    private String drawString;
    public Graphics graph;

    public void drawQueues(Graphics graphics, String name, int queuePlace, Color color, int queueAmount){
        int x = 75;
        int y = 370;
        int m = 20;
        switch (name) {
            case "car": y = 350;
                break;
            case "pass": y = 380;
                break;
            case "payment": y = 410;
                break;
            case "exit": y = 440;
        }
        graphics.setColor(Color.BLACK);
        drawString = queueAmount + " cars in " + name + " queue";
        graphics.drawString(drawString, x, y);
        if(queueAmount > 0) {
            graphics.setColor(color);
            graphics.fillRect(x + queuePlace * m, y+5, 20 - 1, 10 - 1);
        }
        graph = graphics;
        update();
    }

    public Graphics getGraphics(){
        return this.graph;
    }

}
