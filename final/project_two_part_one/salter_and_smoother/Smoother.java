package project_two_part_one.salter_and_smoother;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Smoother {

    private static class Point {
        double x;
        double y_salted_small;
        double smoothedY1;
        double smoothedY2;
        double smoothedY3;
        double smoothedY4;
        double smoothedY5;

        Point(double x, double y_salted_small) {
            this.x = x;
            this.y_salted_small = y_salted_small;
            this.smoothedY1 = y_salted_small; // Initialize each smoothed value to y_salted_small
            this.smoothedY2 = y_salted_small;
            this.smoothedY3 = y_salted_small;
            this.smoothedY4 = y_salted_small;
            this.smoothedY5 = y_salted_small;
        }

        @Override
        public String toString() {
            return String.format("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f", x, y_salted_small, smoothedY1, smoothedY2, smoothedY3, smoothedY4, smoothedY5);
        }
    }

    public List<Point> loadCSV(String filePath) throws IOException {
        List<Point> points = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Skip header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                double x = Double.parseDouble(values[0]);
                double y_salted_small = Double.parseDouble(values[2]); // Assuming y_salted_small is in the third column
                points.add(new Point(x, y_salted_small));
            }
        }
        System.out.println("Loaded " + points.size() + " points from " + filePath);
        return points;
    }

    public void applySmoothing(List<Point> points, int windowSize, int passNumber) {
        List<Double> smoothedValues = new ArrayList<>(points.size());

        // Compute the smoothed values based on the current pass
        for (int i = 0; i < points.size(); i++) {
            double sum = 0;
            int count = 0;

            // Calculate the moving average within the window
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

            // Calculate the average and store it in the smoothedValues list
            smoothedValues.add(sum / count);
        }

        // Assign the smoothed values back to the points based on the pass number
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

    public void saveCSV(String filePath, List<Point> points) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("x,y_salted_small,smoothed_y1,smoothed_y2,smoothed_y3,smoothed_y4,smoothed_y5");
            for (Point point : points) {
                writer.println(point.toString());
            }
        }
        System.out.println("Saved smoothed data to " + filePath);
    }

    public static void main(String[] args) {
        Smoother smoother = new Smoother();
        
        String inputFilePath = "final/project_two_part_one/salter_and_smoother/saltedData_full.csv"; // Salted data file
        String outputFilePath = "final/project_two_part_one/salter_and_smoother/smoothedData.csv";  // Smoothed data file

        int windowSize = 3; // Example window size

        try {
            // Load salted data
            List<Point> points = smoother.loadCSV(inputFilePath);

            // Apply smoothing for five passes
            System.out.println("Smoothing data with window size: " + windowSize);
            for (int i = 1; i <= 5; i++) {
                smoother.applySmoothing(points, windowSize, i);
            }

            // Save smoothed data with all five passes
            smoother.saveCSV(outputFilePath, points);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
