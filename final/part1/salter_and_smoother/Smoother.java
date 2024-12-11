package part1.salter_and_smoother;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Smoother class smooths the salted data, read from the saltedData.csv file, 
 * and outputs it into a new csv file (smoothedData.csv).
 */
public class Smoother {

    /**
     * Data point with its original, salted, and smoothed values.
     */
    private static class Point {
        double x;
        double originalY;
        double y_salted_small;
        double smoothedY1;
        double smoothedY2;
        double smoothedY3;
        double smoothedY4;
        double smoothedY5;

        /**
         * Constructor to initialize a Point with x, originalY, and y_salted_small values.
         *
         * @param x the x-coordinate
         * @param originalY the original y-value
         * @param y_salted_small the salted y-value
         */
        Point(double x, double originalY, double y_salted_small) {
            this.x = x;
            this.originalY = originalY;
            this.y_salted_small = y_salted_small;
            this.smoothedY1 = y_salted_small;
            this.smoothedY2 = y_salted_small;
            this.smoothedY3 = y_salted_small;
            this.smoothedY4 = y_salted_small;
            this.smoothedY5 = y_salted_small;
        }

        /**
         * Converts the Point data into a string that can be exported as a CSV.
         *
         * @return a comma-separated string representation of the Point
         */
        @Override
        public String toString() {
            return String.format("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f",
                    x, originalY, y_salted_small, smoothedY1, smoothedY2, smoothedY3, smoothedY4, smoothedY5);
        }
    }

    /**
     * Loads data points from a CSV file.
     *
     * @param filePath the path to the input CSV file
     * @return a list of values taken from the CSV file
     * @throws IOException if an I/O error occurs during file reading
     */
    public List<Point> loadCSV(String filePath) throws IOException {
        List<Point> points = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                double x = Double.parseDouble(values[0]);
                double originalY = Double.parseDouble(values[1]);
                double y_salted_small = Double.parseDouble(values[2]);
                points.add(new Point(x, originalY, y_salted_small));
            }
        }
        return points;
    }

    /**
     * Applies smoothing to the hypotenuse values of the points using a specified window size and pass number.
     *
     * @param points the list of Points to be smoothed
     * @param windowSize the size of the smoothing window
     * @param passNumber the smoothing pass number (1 to 5)
     */
    public void applySmoothing(List<Point> points, int windowSize, int passNumber) {
        List<Double> smoothedValues = new ArrayList<>(points.size());

        for (int i = 0; i < points.size(); i++) {
            double sum = 0;
            int count = 0;

            for (int j = i - windowSize; j <= i + windowSize; j++) {
                if (j >= 0 && j < points.size()) {
                    double value = switch (passNumber) {
                        case 1 -> points.get(j).y_salted_small;
                        case 2 -> points.get(j).smoothedY1;
                        case 3 -> points.get(j).smoothedY2;
                        case 4 -> points.get(j).smoothedY3;
                        case 5 -> points.get(j).smoothedY4;
                        default -> throw new IllegalArgumentException("Invalid pass number");
                    };
                    sum += value;
                    count++;
                }
            }

            smoothedValues.add(sum / count);
        }

        for (int i = 0; i < points.size(); i++) {
            switch (passNumber) {
                case 1 -> points.get(i).smoothedY1 = smoothedValues.get(i);
                case 2 -> points.get(i).smoothedY2 = smoothedValues.get(i);
                case 3 -> points.get(i).smoothedY3 = smoothedValues.get(i);
                case 4 -> points.get(i).smoothedY4 = smoothedValues.get(i);
                case 5 -> points.get(i).smoothedY5 = smoothedValues.get(i);
            }
        }
    }

    /**
     * Saves the smoothed data points to a CSV file.
     *
     * @param filePath the path to the output CSV file
     * @param points the list of Points to be saved
     * @throws IOException if an I/O error occurs during file writing
     */
    public void saveCSV(String filePath, List<Point> points) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("x,original_hypotenuse,hypotenuse_salted_small,smoothed_hypotenuse1,smoothed_hypotenuse2,smoothed_hypotenuse3,smoothed_hypotenuse4,smoothed_hypotenuse5");
            for (Point point : points) {
                writer.println(point.toString());
            }
        }
    }

    /**
     * The main method initializes the Smoother class, loads data, applies smoothing, and saves the results.
     *
     * @param args
     */
    public static void main(String[] args) {
        Smoother smoother = new Smoother();

        String inputFilePath = "final/part1/salter_and_smoother/saltedData.csv";
        String outputFilePath = "final/part1/salter_and_smoother/smoothedData.csv";

        int windowSize = 3;

        try {
            List<Point> points = smoother.loadCSV(inputFilePath);

            for (int i = 1; i <= 5; i++) {
                smoother.applySmoothing(points, windowSize, i);
            }

            smoother.saveCSV(outputFilePath, points);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data exported to new CSV file");
    }
}
