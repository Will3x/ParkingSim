package view;

import model.Model;
import model.PieChart02;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;

public class PiechartView extends JPanel {

    private Dimension size;
    private SimulatorView simView;
    private Model model;
    private PieChart02 chart;
    private JPanel pnlChart;


    public PiechartView(SimulatorView simView, Model model) {
        size = new Dimension(0, 0);
        this.simView = simView;
        this.model = model;

        //Add piechart
        chart = new PieChart02();
        pnlChart = new XChartPanel(chart.getChart());
        this.add(pnlChart);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 460);
    }
}
