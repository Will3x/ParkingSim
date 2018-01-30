package view;

import java.awt.*;

public class GraphView extends View {
    protected int max;


    GraphView(int width, int height, int max) {
        setSize(width, height);
        this.max = max;
    }

    /**
     * Returns the preferred size of the graph.
     * @return dimensions of the preferred size.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 300);
    }

}

