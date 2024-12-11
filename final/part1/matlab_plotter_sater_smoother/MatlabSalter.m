% Salts the Hypotenuse
% This file gets the hypotenuse from data from a CSV file,
% salts the hypotenuse values, saves the salted data to another CSV file,
% and creates a line chart for both the original and salted data.

% Define file paths
% inputCsvFile: Path to the input CSV file containing original data.
% outputCsvFile: Path to the output CSV file where salted data will be saved.
inputCsvFile = '/MATLAB Drive/MatlabPlotterData.csv';
outputCsvFile = '/MATLAB Drive/MatlabSaltedData.csv';

% Read data from the input CSV file
% The file expects the input CSV to contain "VariableSide" and "Hypotenuse" columns.
data = readtable(inputCsvFile);

if ~all(ismember({'VariableSide', 'Hypotenuse'}, data.Properties.VariableNames))
    error('Input CSV must contain columns "VariableSide" and "Hypotenuse".');
end

% Extract columns from the table
% variableSide: Values representing the variable side of the triangle.
% hypotenuse: Original hypotenuse values.
variableSide = data.VariableSide;
hypotenuse = data.Hypotenuse;

% Salt hypotenuse
% saltedHypotenuse: Hypotenuse values after salting
saltedHypotenuse = hypotenuse + randn(size(hypotenuse)) * 10;

% Create a new table with the salted data
% saltedData: Table containing variable side, original hypotenuse, and salted hypotenuse.
saltedData = table(variableSide, hypotenuse, saltedHypotenuse, ...
                   'VariableNames', {'VariableSide', 'Original_Hypotenuse', 'Salted_Hypotenuse'});

writetable(saltedData, outputCsvFile);

% Visualize the original and salted hypotenuse values
figure;
hold on;
plot(variableSide, saltedHypotenuse, 'ro', 'MarkerSize', 2, 'MarkerFaceColor', 'r', ...
     'DisplayName', 'Salted Hypotenuse');
plot(variableSide, hypotenuse, 'b-', 'LineWidth', 2, 'DisplayName', 'Original Hypotenuse'); % Original data (blue line)
hold off;

xlabel('Variable Side');
ylabel('Hypotenuse');
title('Salted Pythagorean Theorem');
legend('Location', 'best');
grid on;

disp(['Salted data has been saved to ', outputCsvFile]);
