import org.apache.commons.math3.util.FastMath;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class ExternalLibraryPlotter {
    public static void main(String[] args) {
        double startValue = 1.0;
        double endValue = 200001.0;
        double interval = 1000.0;
        double fixedSide = 123456.0;

        exportDataToCSV(startValue, endValue, interval, fixedSide);
        createAndShowChart(startValue, endValue, interval, fixedSide);
    }

    public static void exportDataToCSV(double startValue, double endValue, double interval, double fixedSide) {
        try (FileWriter csvWriter = new FileWriter("dataPlotter.csv")) {
            csvWriter.append("Variable Side,Hypotenuse\n");

            for (double variableSide = startValue; variableSide <= endValue; variableSide += interval) {
                double hypotenuse = FastMath.hypot(variableSide, fixedSide);
                csvWriter.append(String.format("%.2f,%.2f\n", variableSide, hypotenuse));
            }
            System.out.println("Data has been successfully exported to dataPlotter.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAndShowChart(double startValue, double endValue, double interval, double fixedSide) {
        XYSeries series = new XYSeries("Hypotenuse");
        for (double variableSide = startValue; variableSide <= endValue; variableSide += interval) {
            double hypotenuse = FastMath.hypot(variableSide, fixedSide);
            series.add(variableSide, hypotenuse);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Hypotenuse Calculation",
                "Variable Side",
                "Hypotenuse",
                dataset           
        );

        XYPlot plot = lineChart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Plotter with Apache Commons Math and JFreeChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}