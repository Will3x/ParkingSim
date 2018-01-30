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

        chart = new PieChartBuilder().width(500).height(400).title("Geparkeerde auto's").build();

        // Customize Chart
        Color[] sliceColors = new Color[]{new Color(0, 91, 224), new Color(230, 24, 0), new Color(236, 201, 0), new Color(243, 180, 159), new Color(246, 199, 182)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setChartBackgroundColor(new Color(238, 238, 238)).setLegendBackgroundColor(new Color(238, 238, 238)).setLegendBorderColor(new Color(238, 238, 238)).setPlotBackgroundColor(new Color(238, 238, 238));
        chart.getStyler().setPlotBorderVisible(false).setChartTitleVisible(true);

        chart.addSeries("pass", 1);
        chart.addSeries("adhoc", 1);
        chart.addSeries("Abonnement", 1);

        return chart;

    }

    public void updateChart(int amount, int amount2, int amount3){

            chart.updatePieSeries("pass", amount);
            chart.updatePieSeries("adhoc", amount2);
        chart.updatePieSeries("Abonnement", amount3);

    }
}
