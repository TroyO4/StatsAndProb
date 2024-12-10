import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExternalLibrarySmoother {
    public static void main(String[] args) {
        String inputCsv = "saltedDataPlotter.csv";
        String outputCsv = "smoothedDataPlotter.csv";

        try {
            smoothDataAndExportToCSV(inputCsv, outputCsv);
            createAndShowChart(outputCsv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void smoothDataAndExportToCSV(String inputCsv, String outputCsv) throws IOException {
        try (
            BufferedReader csvReader = new BufferedReader(new FileReader(inputCsv));
            FileWriter csvWriter = new FileWriter(outputCsv)
        ) {
            csvWriter.append("Variable Side,Original Hypotenuse,Salted Hypotenuse,Smoothed Iteration 1,Smoothed Iteration 2,Smoothed Iteration 3,Smoothed Iteration 4,Smoothed Iteration 5\n");

            String row;
            boolean isHeader = true;
            List<Double> saltedHypotenuseList = new ArrayList<>();
            List<Double> variableSideList = new ArrayList<>();
            List<Double> originalHypotenuseList = new ArrayList<>();

            while ((row = csvReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = row.split(",");
                double variableSide = Double.parseDouble(data[0]);
                double originalHypotenuse = Double.parseDouble(data[1]);
                double saltedHypotenuse = Double.parseDouble(data[2]);

                variableSideList.add(variableSide);
                originalHypotenuseList.add(originalHypotenuse);
                saltedHypotenuseList.add(saltedHypotenuse);
            }

            List<Double> currentIteration = new ArrayList<>(saltedHypotenuseList);
            List<List<Double>> allIterations = new ArrayList<>();
            int windowSize = 3;

            for (int iteration = 0; iteration < 5; iteration++) {
                List<Double> smoothedIteration = new ArrayList<>();
                for (int i = 0; i < currentIteration.size(); i++) {
                    double sum = 0.0;
                    int count = 0;
                    for (int j = Math.max(0, i - windowSize); j <= Math.min(currentIteration.size() - 1, i + windowSize); j++) {
                        sum += currentIteration.get(j);
                        count++;
                    }
                    smoothedIteration.add(sum / count);
                }
                currentIteration = smoothedIteration;
                allIterations.add(smoothedIteration);
            }

            for (int i = 0; i < variableSideList.size(); i++) {
                csvWriter.append(String.format("%.2f,%.2f,%.2f",
                        variableSideList.get(i),
                        originalHypotenuseList.get(i),
                        saltedHypotenuseList.get(i)
                ));
                for (List<Double> iteration : allIterations) {
                    csvWriter.append(String.format(",%.2f", iteration.get(i)));
                }
                csvWriter.append("\n");
            }

            System.out.println("Smoothed data has been successfully exported to " + outputCsv);
        }
    }

    public static void createAndShowChart(String outputCsv) throws IOException {
        XYSeries originalSeries = new XYSeries("Original Hypotenuse");
        XYSeries saltedSeries = new XYSeries("Salted Hypotenuse");
        XYSeries smoothedSeries1 = new XYSeries("Smoothed Iteration 1");
        XYSeries smoothedSeries2 = new XYSeries("Smoothed Iteration 2");
        XYSeries smoothedSeries3 = new XYSeries("Smoothed Iteration 3");
        XYSeries smoothedSeries4 = new XYSeries("Smoothed Iteration 4");
        XYSeries smoothedSeries5 = new XYSeries("Smoothed Iteration 5");

        try (BufferedReader csvReader = new BufferedReader(new FileReader(outputCsv))) {
            String row;
            boolean isHeader = true;

            while ((row = csvReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = row.split(",");
                double variableSide = Double.parseDouble(data[0]);
                double originalHypotenuse = Double.parseDouble(data[1]);
                double saltedHypotenuse = Double.parseDouble(data[2]);
                double smoothed1 = Double.parseDouble(data[3]);
                double smoothed2 = Double.parseDouble(data[4]);
                double smoothed3 = Double.parseDouble(data[5]);
                double smoothed4 = Double.parseDouble(data[6]);
                double smoothed5 = Double.parseDouble(data[7]);

                originalSeries.add(variableSide, originalHypotenuse);
                saltedSeries.add(variableSide, saltedHypotenuse);
                smoothedSeries1.add(variableSide, smoothed1);
                smoothedSeries2.add(variableSide, smoothed2);
                smoothedSeries3.add(variableSide, smoothed3);
                smoothedSeries4.add(variableSide, smoothed4);
                smoothedSeries5.add(variableSide, smoothed5);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(originalSeries);
        dataset.addSeries(saltedSeries);
        dataset.addSeries(smoothedSeries1);
        dataset.addSeries(smoothedSeries2);
        dataset.addSeries(smoothedSeries3);
        dataset.addSeries(smoothedSeries4);
        dataset.addSeries(smoothedSeries5);

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Original, Salted, and Smoothed Data",
                "Variable Side",
                "Hypotenuse",
                dataset
        );

        XYPlot plot = lineChart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Smoother with Iterations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
