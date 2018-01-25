//package model;
//
//import javafx.scene.chart.PieChart;
//import javafx.scene.chart.PieChartBuilder;
//import sun.reflect.annotation.AnnotationType;
//
//public class Piechart {
//
//          // Create Chart
//        PieChart chart = new PieChartBuilder().width(800).height(600).title("My Pie Chart").theme(ChartTheme.GGPlot2).build();
//
//        // Customize Chart
//        chart.getStyler().setLegendVisible(false);
//        chart.getStyler().setAnnotationType(AnnotationType.LabelAndPercentage);
//        chart.getStyler().setAnnotationDistance(1.15);
//        chart.getStyler().setPlotContentSize(.7);
//        chart.getStyler().setStartAngleInDegrees(90);
//
//        // Series
//        chart.addSeries("Prague", 2);
//        chart.addSeries("Dresden", 4);
//        chart.addSeries("Munich", 34);
//        chart.addSeries("Hamburg", 22);
//        chart.addSeries("Berlin", 29);
//
//        // Show it
//        new SwingWrapper(chart).displayChart();
//
//        // Save it
//        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG);
//
//        // or save it in high-res
//        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapFormat.PNG, 300);
//    }
//
//}