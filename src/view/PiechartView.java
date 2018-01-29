package view;

import model.Model;
import model.PieChart02;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;

public class PiechartView extends View {

    private Dimension size;
    private SimulatorView simView;
    private Model model;
    private PieChart02 chart;
    private JPanel pnlChart;


    public PiechartView(SimulatorView simView, Model model, PieChart02 pie) {
        size = new Dimension(0, 0);
        this.simView = simView;
        this.model = model;
        chart = pie;

        //Add piechart
        chart = new PieChart02();
        pnlChart = new XChartPanel(chart.getChart());
        this.add(pnlChart);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 460);
    }

    @Override
    public void update() {
        if (model.getPassCars() == 0 && model.getAdHocCars() == 0){
            chart.updateChart(1, 1);
        } else {
            chart.updateChart(model.getPassCars(), model.getAdHocCars());
        }
        super.update();
    }
}
