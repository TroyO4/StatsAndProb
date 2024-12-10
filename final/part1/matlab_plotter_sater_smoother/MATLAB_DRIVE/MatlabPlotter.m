% Pythagorean Theorem Plot with CSV Export

% Define the range for variable side and fixed side
variableSide = 0:0.1:10;
fixedSide = 5;

Hypotenuse = sqrt(variableSide.^2 + fixedSide^2);

figure;
plot(variableSide, Hypotenuse, 'c-', 'LineWidth', 2);
xlabel('Variable Side');
ylabel('Hypotenuse');
title(['Pythagorean Theorem (Fixed Side = ' num2str(fixedSide) ')']);
grid on;

data = table(variableSide', Hypotenuse', 'VariableNames', {'VariableSide', 'Hypotenuse'});

csvFileName = 'MatlabPlotterData.csv';
writetable(data, csvFileName);

disp(['Data has been saved to ', csvFileName]);
