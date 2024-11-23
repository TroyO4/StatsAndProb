% Import Salted Data from CSV
opts = detectImportOptions('MatlabSaltedData.csv');
opts.DataLines = [2, Inf];
data = readmatrix('MatlabSaltedData.csv', opts);

x = data(:, 1);
original_y = data(:, 2);
salted_y = data(:, 3);   

windowValue = 3;

current_y = salted_y;

smoothed_data = zeros(length(x), 5);

% Perform iterative smoothing
for iteration = 1:5
    smoothed_y = zeros(size(current_y));
    
    for i = 1:length(current_y)
        left_index = max(1, i - windowValue);
        right_index = min(length(current_y), i + windowValue);
        
        smoothed_y(i) = mean(current_y(left_index:right_index));
    end
    
    current_y = smoothed_y;
    
    smoothed_data(:, iteration) = current_y;
end

% Combine data for output
output_data = [x, original_y, salted_y, smoothed_data];

% Define headers for the CSV file
headers = {'X', 'Original_Y', 'Salted_Y', ...
           'Smoothed_Y_1', 'Smoothed_Y_2', 'Smoothed_Y_3', 'Smoothed_Y_4', 'Smoothed_Y_5'};

% Write headers and data to the CSV file
output_filename = 'MatlabSmoothedData.csv';
writecell(headers, output_filename);
writematrix(output_data, output_filename, 'WriteMode', 'append');

% Create a graph
figure;
hold on;

% Plot Original Data
plot(x, original_y, 'b-', 'LineWidth', 2, 'DisplayName', 'Original Data');

% Plot Salted Data
plot(x, salted_y, 'ro', 'MarkerSize', 2, 'DisplayName', 'Salted Data');

% Plot Smoothed Data from each iteration
colors = lines(5); % Use distinct colors for each iteration
for iteration = 1:5
    plot(x, smoothed_data(:, iteration), 'LineWidth', 1.5, ...
         'DisplayName', ['Smoothed ', num2str(iteration), ' Times'], ...
         'Color', colors(iteration, :));
end

% Add labels, legend, and title
xlabel('X Values');
ylabel('Y Values');
title('Original, Salted, and Smoothed Data');
legend('Location', 'best');
grid on;

hold off;
