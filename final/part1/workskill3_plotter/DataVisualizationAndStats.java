package part1.workskill3_plotter;

import org.apache.commons.math4.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;

public class DataVisualizationAndStats {
    public static void main(String[] args) {
        double[] data = {23.5, 45.6, 67.8, 89.9, 12.3};
        //test
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double num : data) {
            stats.addValue(num);
        }

        double mean = stats.getMean();
        double variance = stats.getVariance();

        System.out.println("Mean: " + mean);
        System.out.println("Variance: " + variance);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++) {
            dataset.addValue(data[i], "Values", "Data " + (i + 1));
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Data Chart",
                "Category",
                "Value",
                dataset
        );

        ApplicationFrame frame = new ApplicationFrame("JFreeChart Example");
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
