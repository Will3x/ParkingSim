package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;



public class LineGraph extends GraphView {
    private BufferedImage graphImage;
    private double lastValueAdHoc = 0;
    private double lastValuePass = 0;
    private double lastValueReserve = 0;
    private int height;
    private int width;
    private int max = 0;

    /**
     * Sets the dimensions of the of the graph and creates the bufferedImage used as canvas for the lines.
     * @param width  Width of the graph.
     * @param height Height of the graph.
     * @param max    Max value of the graph.
     */
    public LineGraph(int width, int height, int max) {
        super(width,height,max);
        this.width = width;
        this.height = height;
        graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        clearImage();
    }

    /**
     * Paint the graph.
     * @param g Graphics for the paint.
     */
    public void paint(Graphics g) {
        super.paint(g);

        paintComponent(g);
    }

    /**
     * Copy the current graph 1 pixel to the left. and draw the lines so that it connects to the last step.
     * @param adHoc   Number of adHocCars.
     * @param pass    Number of abbonement cars.
     * @param reserve Number of reservation cars.
     */
    public void update(int adHoc, int pass, int reserve) {
        super.update();
        Graphics graphG = graphImage.getGraphics();

        int height = graphImage.getHeight();
        int width = graphImage.getWidth();

        // Push everything 1 step back
        graphG.copyArea(1, 0, width-1, height, -1, 0);

        max = adHoc + pass;

        int[] values = {adHoc, pass, reserve};
        Arrays.sort(values);

        for (int i = values.length - 1; i >= 0; i--) {
            if (values[i] == adHoc)
                lastValueAdHoc = drawStep(graphG, adHoc, Color.RED, lastValueAdHoc);
            else if (values[i] == pass)
                lastValuePass = drawStep(graphG, pass, Color.BLUE, lastValuePass);
            else if (values[i] == reserve)
                lastValueReserve = drawStep(graphG, reserve, Color.GREEN, lastValueReserve);
        }
    }

    /**
     * Calculate the correct height for the current value and connect it with the last registered value.
     * Then return the just calculated y position of the value.
     * @param g         The Graphics of the BufferedImage.
     * @param value     The new value.
     * @param color     The color of the line.
     * @param lastValue The last registered y position of the line.
     * @return          The just calculated y position of the value.
     */
    private double drawStep(Graphics g, int value, Color color, double lastValue) {
        double y = ((double) height / (double) max) * (double) value;

        g.setColor(color);
        g.drawLine(width -2, height - (int) lastValue, width - 2, height - (int) y);
        return y;
    }

    /**
     * Clear the bufferedImage canvas.
     */
    private void clearImage()
    {
        Graphics g = graphImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, graphImage.getWidth(), graphImage.getHeight());
        repaint();
    }

    /**
     * Draw the bufferedImage.
     * @param g The Graphics instance used to draw the BufferedImage.
     */
    public void paintComponent(Graphics g)
    {
        if(graphImage != null) {
            g.drawImage(graphImage, 0, 0, null);
        }
    }

    /**
     * Get the height of the graph.
     * @return The height of the graph.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of the graph.
     * @return The width of the graph.
     */
    public int getWidth() {
        return width;
    }
}