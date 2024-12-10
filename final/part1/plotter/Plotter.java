package part1.plotter;

import java.io.FileWriter;
import java.io.IOException;

public class Plotter {
    public static void main(String[] args) {
        double startValue = 1.0;
        double endValue = 500001.0;
        double interval = 500.0;
        double fixedSide = 200234.0;

        exportDataToCSV(startValue, endValue, interval, fixedSide);
    }

    public static void exportDataToCSV(double startValue, double endValue, double interval, double fixedSide) {
        try (FileWriter csvWriter = new FileWriter("data5.csv")) {
            csvWriter.append("Start Value:").append(String.valueOf(startValue)).append("\n");
            csvWriter.append("End Value:").append(String.valueOf(endValue)).append("\n");
            csvWriter.append("Interval:").append(String.valueOf(interval)).append("\n");
            csvWriter.append("Fixed Side:").append(String.valueOf(fixedSide)).append("\n\n");

            csvWriter.append("variableSide,hypotenuse\n");

            for (double variableSide = startValue; variableSide <= endValue; variableSide += interval) {
                double hypotenuse = Math.sqrt(Math.pow(variableSide, 2) + Math.pow(fixedSide, 2));
                csvWriter.append(String.format("%.2f,%.2f\n", variableSide, hypotenuse));
            }

            System.out.println("Data has been successfully exported to data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
