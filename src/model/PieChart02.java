package model;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import java.awt.*;
import java.util.ArrayList;

public class PieChart02 implements ExampleChart<PieChart> {

    private ArrayList<Integer> cars = new ArrayList<>();
    private Model car;
    private PieChart chart;

    @Override
    public PieChart getChart() {

        chart = new PieChartBuilder().width(550).height(450).title("Pie Chart").build();

        // Customize Chart
        Color[] sliceColors = new Color[] { new Color(0, 91, 224), new Color(230, 24, 0), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setChartBackgroundColor(Color.lightGray).setLegendBackgroundColor(Color.lightGray).setLegendBorderColor(Color.lightGray).setPlotBackgroundColor(Color.lightGray);
        chart.getStyler().setPlotBorderVisible(false).setChartTitleVisible(false);

        // Add to array
        cars.add(0, 5);
        cars.add(1,7);

        // Series
        chart.addSeries("Pass cars", cars.get(0));
        chart.addSeries("Ad-hoc cars", cars.get(1));

        return chart;
    }
}

