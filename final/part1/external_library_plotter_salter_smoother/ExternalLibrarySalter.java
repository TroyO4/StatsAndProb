import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;

import javax.swing.*;
import java.io.*;
import java.util.Random;

/**
 * The ExternalLibrarySalter class reads data from a CSV file and salts it by adding or subtracting random maounts
 * from the hypotenuse values. Then, it exports the salted data to another CSV file and creates a line chart 
 * showing the results
 */
public class ExternalLibrarySalter {

    /**
     * Main method to execute the program.
     *
     * @param args
     */
    public static void main(String[] args) {
        String inputCsv = "dataPlotter.csv";
        String outputCsv = "saltedDataPlotter.csv";

        try {
            saltDataAndExportToCSV(inputCsv, outputCsv);
            createAndShowChart(inputCsv, outputCsv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads data from an input CSV file, salts it, and outputs the
     * salted data to a CSV file.
     *
     * @param inputCsv  the path to the input CSV file containing original data
     * @param outputCsv the path to the output CSV file for salted data
     * @throws IOException if an I/O error occurs
     */
    public static void saltDataAndExportToCSV(String inputCsv, String outputCsv) throws IOException {
        try (
            BufferedReader csvReader = new BufferedReader(new FileReader(inputCsv));
            FileWriter csvWriter = new FileWriter(outputCsv)
        ) {
            csvWriter.append("Variable Side,Original Hypotenuse,Salted Hypotenuse\n");

            String row;
            boolean isHeader = true;
            Random random = new Random();

            while ((row = csvReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = row.split(",");
                double variableSide = Double.parseDouble(data[0]);
                double originalHypotenuse = Double.parseDouble(data[1]);
                double saltedHypotenuse = originalHypotenuse + (random.nextGaussian() * 1000.0);

                csvWriter.append(String.format("%.2f,%.2f,%.2f\n", variableSide, originalHypotenuse, saltedHypotenuse));
            }

            System.out.println("Salted data has been successfully exported to " + outputCsv);
        }
    }

    /**
     * Reads the original and salted hypotenuse data from a CSV file and shows them
     * in a line chart.
     *
     * @param inputCsv  the path to the input CSV file containing original data
     * @param outputCsv the path to the output CSV file containing salted data
     * @throws IOException if an I/O error occurs
     */
    public static void createAndShowChart(String inputCsv, String outputCsv) throws IOException {
        XYSeries originalSeries = new XYSeries("Original Hypotenuse");
        XYSeries saltedSeries = new XYSeries("Salted Hypotenuse");

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

                originalSeries.add(variableSide, originalHypotenuse);
                saltedSeries.add(variableSide, saltedHypotenuse);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(originalSeries);
        dataset.addSeries(saltedSeries);

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Original and Salted Hypotenuse Calculation",
                "Variable Side",
                "Hypotenuse",
                dataset
        );

        XYPlot plot = lineChart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Salted Plotter with Apache Commons Math and JFreeChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
