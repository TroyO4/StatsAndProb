package part1.salter_and_smoother;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Salter {
    private static class Point {
        double x;
        double y;
        double ySaltedSmall;
        double ySaltedMedium;
        double ySaltedLarge;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y + "," + ySaltedSmall + "," + ySaltedMedium + "," + ySaltedLarge;
        }
    }

    public void processCSV(String inputFilePath, String outputFilePath) throws IOException {
        List<Point> points = loadCSV(inputFilePath);
        applySalt(points);
        saveCSV(outputFilePath, points);
    }

    private List<Point> loadCSV(String filePath) throws IOException {
        List<Point> points = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] values = row.split(",");
                
                // Skip rows that do not have numeric values
                if (values.length < 2) {
                    continue;
                }
                try {
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);
                    points.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    // Skip non-numeric rows
                    continue;
                }
            }
        }
        System.out.println("Loaded " + points.size() + " points from " + filePath);
        return points;
    }
    

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

        System.out.println("Applied salt with small, medium, and large ranges to " + points.size() + " points.");
    }

    private void saveCSV(String filePath, List<Point> points) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("variableSide,hypotenuse,hypotenuse_salted_small,hypotenuse_salted_medium,hypotenuse_salted_large");
            for (Point point : points) {
                writer.println(point.toString());
            }
        }
        System.out.println("Saved salted data to " + filePath);
    }

    public static void main(String[] args) {
        Salter salter = new Salter();

        // File paths
        String inputFilePath = "final/part1/plotter/data.csv";
        String outputFilePath = "final/part1/saltedData.csv";

        try {
            // Process the dataset
            System.out.println("Processing dataset");
            salter.processCSV(inputFilePath, outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
