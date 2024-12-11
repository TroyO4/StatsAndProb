% Pythagorean Theorem Plot with CSV Output
% This file calculates the hypotenuse of a right triangle for a range of
% variable side lengths (Pythagorean Theorem), plots the results on a line chart, and exports the data to a CSV file.

% Define the range for variable side and fixed side
% variableSide: A vector of values representing the variable side of the triangle.
% fixedSide: A scalar representing the fixed side of the triangle.
variableSide = 0:0.1:10;
fixedSide = 5;

% Calculate the hypotenuse for each value of the variable side
% Hypotenuse: A vector containing the hypotenuse values for each variable side.
Hypotenuse = sqrt(variableSide.^2 + fixedSide^2);

% Plot the hypotenuse
% The plot displays the relationship between the variable side and the hypotenuse,
% with the fixed side length indicated in the title.
figure;
plot(variableSide, Hypotenuse, 'c-', 'LineWidth', 2);
xlabel('Variable Side');  % Label for the x-axis
ylabel('Hypotenuse');     % Label for the y-axis
title(['Pythagorean Theorem (Fixed Side = ' num2str(fixedSide) ')']); % Plot title
grid on;                  % Enable grid for better visualization

% Export the data to a CSV file
% data: A table containing the variable side and corresponding hypotenuse values.
% csvFileName: Name of the CSV file where the data is saved.
data = table(variableSide', Hypotenuse', 'VariableNames', {'VariableSide', 'Hypotenuse'});
csvFileName = 'MatlabPlotterData.csv';
writetable(data, csvFileName);

disp(['Data has been saved to ', csvFileName]);
