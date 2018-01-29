package view;

import java.awt.*;

public class GraphView extends View {
    protected int max;
    protected int cars;


    GraphView(int width, int height, int max) {
        setSize(width, height);
        this.max = max;
    }

    /**
     * Returns the preferred size of the graph.
     * @return dimensions of the preferred size.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 200);
    }

    /**
     * repaint the view.
     */
    public void update() {
        repaint();
    }
}

