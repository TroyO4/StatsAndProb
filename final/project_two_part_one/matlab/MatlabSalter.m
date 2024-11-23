% Generate large dataset
num_points = 10000; % Number of data points
x = 0:1:(num_points-1); % Generate 10,000 evenly spaced x values (start at 0, step by 1)
y = 0:2:((num_points-1)*2); % Generate 10,000 evenly spaced y values (start at 0, step by 2)

% Add salt (random noise) to the y values
salted_y = y + randn(size(y)) * 10; % Add noise scaled by 10

% Plot the salted data first
figure;
saltedPlot = plot(x, salted_y, 'ro', 'MarkerSize', 2, 'MarkerFaceColor', 'r', 'DisplayName', 'Salted Data');
hold on;

% Plot the original data
originalPlot = plot(x, y, 'b-', 'LineWidth', 2, 'DisplayName', 'Original Data');

% Bring the original data line to the front
uistack(originalPlot, 'top');

% Add labels, title, and legend
xlabel('X Values');
ylabel('Y Values');
title('Original and Salted Data');
legend('Location', 'best'); % Automatically place the legend
grid on; % Add grid for better visualization
hold off;

% Save original and salted data to a .csv file with labels
headers = {'X', 'Original_Y', 'Salted_Y'}; % Define column headers
data = [x', y', salted_y']; % Combine x, original y, and salted y into a 3-column matrix

% Write headers and data
writecell(headers, 'MatlabSaltedData.csv'); % Write headers to the file
writematrix(data, 'MatlabSaltedData.csv', 'WriteMode', 'append'); % Append data to the file
