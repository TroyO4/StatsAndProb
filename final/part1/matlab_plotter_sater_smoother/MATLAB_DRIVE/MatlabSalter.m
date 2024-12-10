inputCsvFile = '/MATLAB Drive/PlotterData.csv';  % Path to the uploaded file
outputCsvFile = '/MATLAB Drive/MatlabSaltedData.csv'; % Output file for salted data

data = readtable(inputCsvFile);

if ~all(ismember({'VariableSide', 'Hypotenuse'}, data.Properties.VariableNames))
    error('Input CSV must contain columns "VariableSide" and "Hypotenuse".');
end

variableSide = data.VariableSide;
hypotenuse = data.Hypotenuse;

saltedHypotenuse = hypotenuse + randn(size(hypotenuse)) * 10; % Add Gaussian noise scaled by 10

saltedData = table(variableSide, hypotenuse, saltedHypotenuse, ...
                   'VariableNames', {'VariableSide', 'Original_Hypotenuse', 'Salted_Hypotenuse'});

writetable(saltedData, outputCsvFile);

figure;
hold on;
plot(variableSide, saltedHypotenuse, 'ro', 'MarkerSize', 2, 'MarkerFaceColor', 'r', ...
     'DisplayName', 'Salted Hypotenuse'); % Salted data (red dots)
plot(variableSide, hypotenuse, 'b-', 'LineWidth', 2, 'DisplayName', 'Original Hypotenuse'); % Original data (blue line)
hold off;

xlabel('Variable Side');
ylabel('Hypotenuse');
title('Salted Pythagorean Theorem');
legend('Location', 'best');
grid on;

disp(['Salted data has been saved to ', outputCsvFile]);
