package part1.salter_and_smoother;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The Salter class reads data from a CSV file, applies random salt to the data,
 * and exports the salted data to a new CSV file.
 */
public class Salter {

    /**
     * Single data point with its original and salted values.
     */
    private static class Point {
        double x;
        double y;
        double ySaltedSmall;
        double ySaltedMedium;
        double ySaltedLarge;

        /**
         * Constructor to initialize a Point with x and y values.
         *
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Converts the Point data into a CSV-compatible string.
         *
         * @return the point in the form of a CSV
         */
        @Override
        public String toString() {
            return x + "," + y + "," + ySaltedSmall + "," + ySaltedMedium + "," + ySaltedLarge;
        }
    }

    /**
     * Get the data from the input CSV file,salt, and saves the salted data to the output file.
     *
     * @param inputFilePath  the path to the input CSV file
     * @param outputFilePath the path to the output CSV file
     * @throws IOException if an I/O error occurs during file reading or writing
     */
    public void processCSV(String inputFilePath, String outputFilePath) throws IOException {
        List<Point> points = loadCSV(inputFilePath);
        applySalt(points);
        saveCSV(outputFilePath, points);
    }

    /**
     * Loads data points from a CSV file.
     *
     * @param filePath the path to the CSV file
     * @return a list of Points loaded from the file
     * @throws IOException if an I/O error occurs during file reading
     */
    private List<Point> loadCSV(String filePath) throws IOException {
        List<Point> points = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] values = row.split(",");
                if (values.length < 2) {
                    continue;
                }
                try {
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);
                    points.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        return points;
    }

    /**
     * Salts to the y-values
     *
     * @param the points that get salted
     */
    private void applySalt(List<Point> points) {
        Random random = new Random();
        double[][] saltRanges = {
            {-5, 5},     // Small salt range
            {-20, 20},   // Medium salt range
            {-50, 50}    // Large salt range
        };

        for (Point point : points) {
            point.ySaltedSmall = point.y + saltRanges[0][0] + (saltRanges[0][1] - saltRanges[0][0]) * random.nextDouble();
            point.ySaltedMedium = point.y + saltRanges[1][0] + (saltRanges[1][1] - saltRanges[1][0]) * random.nextDouble();
            point.ySaltedLarge = point.y + saltRanges[2][0] + (saltRanges[2][1] - saltRanges[2][0]) * random.nextDouble();
        }
    }

    /**
     * Saves the salted data to a CSV file.
     *
     * @param filePath the path to the output CSV file
     * @param points the list of Points to save
     * @throws IOException if an I/O error occurs during file writing
     */
    private void saveCSV(String filePath, List<Point> points) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("variableSide,hypotenuse,hypotenuse_salted_small,hypotenuse_salted_medium,hypotenuse_salted_large");
            for (Point point : points) {
                writer.println(point.toString());
            }
        }
    }

    /**
     * The main method initializes the Salter class and processes the dataset.
     *
     * @param args
     */
    public static void main(String[] args) {
        Salter salter = new Salter();
        String inputFilePath = "final/part1/plotter/data.csv";
        String outputFilePath = "final/part1/saltedData.csv";

        try {
            salter.processCSV(inputFilePath, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data exported to new CSV file");
    }
}
