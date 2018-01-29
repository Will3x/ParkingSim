package model;

import controller.InfoController;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import java.awt.*;
import java.util.ArrayList;

public class PieChart02 implements ExampleChart<PieChart> {

    private Model car;
    private PieChart chart;
    private InfoController infoCon;

    @Override
    public PieChart getChart() {

        chart = new PieChartBuilder().width(500).height(400).title("Pie Chart").build();

        // Customize Chart
        Color[] sliceColors = new Color[]{new Color(0, 91, 224), new Color(230, 24, 0), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setChartBackgroundColor(Color.lightGray).setLegendBackgroundColor(Color.lightGray).setLegendBorderColor(Color.lightGray).setPlotBackgroundColor(Color.lightGray);
        chart.getStyler().setPlotBorderVisible(false).setChartTitleVisible(false);

        chart.addSeries("adhoc", 1);
        chart.addSeries("pass", 1);

        return chart;

    }

    public void updateChart(int amount, int amount2){
        chart.updatePieSeries("adhoc", amount);
        chart.updatePieSeries("pass", amount2);
    }
}
