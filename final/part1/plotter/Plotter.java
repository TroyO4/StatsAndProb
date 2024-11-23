package part1.plotter;
import java.io.FileWriter;
import java.io.IOException;

public class Plotter {
    public static void main(String[] args) {
        exportDataToCSV(1.0, 200001.0, 1000.0, 123456.0);
    }

    public static void exportDataToCSV(double startValue, double endValue, double interval, double fixedSide) {
        try (FileWriter csvWriter = new FileWriter("data5.csv")) {
            csvWriter.append("variableSide,hypotenuse\n");

            for (double variableSide = startValue; variableSide <= endValue; variableSide += interval) {
                double hypotenuse = Math.sqrt(Math.pow(variableSide, 2) + Math.pow(fixedSide, 2));
                csvWriter.append(String.format("%.2f,%.2f\n", variableSide, hypotenuse));
            }
            System.out.println("Data has been successfully exported to data5.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
