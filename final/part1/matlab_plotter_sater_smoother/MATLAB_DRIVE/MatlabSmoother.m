opts = detectImportOptions('MatlabSaltedData.csv');
opts.DataLines = [2, Inf];
data = readmatrix('MatlabSaltedData.csv', opts);

x = data(:, 1);
original_y = data(:, 2);
salted_y = data(:, 3);   

windowValue = 3;

current_y = salted_y;

smoothed_data = zeros(length(x), 5);

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

output_data = [x, original_y, salted_y, smoothed_data];

headers = {'X', 'Original_Hypotenuse', 'Salted_Hypotenuse', ...
           'Smoothed_Hypotenuse_1', 'Smoothed_Hypotenuse_2', 'Smoothed_Hypotenuse_3', 'Smoothed_Hypotenuse_4', 'Smoothed_Hypotenuse_5'};

output_filename = 'MatlabSmoothedData.csv';
writecell(headers, output_filename);
writematrix(output_data, output_filename, 'WriteMode', 'append');

figure;
hold on;

plot(x, original_y, 'b-', 'LineWidth', 2, 'DisplayName', 'Original Data');

plot(x, salted_y, 'ro', 'MarkerSize', 2, 'DisplayName', 'Salted Data');

colors = lines(5);
for iteration = 1:5
    plot(x, smoothed_data(:, iteration), 'LineWidth', 1.5, ...
         'DisplayName', ['Smoothed ', num2str(iteration), ' Times'], ...
         'Color', colors(iteration, :));
end

xlabel('Variable Side');
ylabel('Hypotenuse Smoothed');
title('Smoothed Pythagorean Theorem');
legend('Location', 'best');
grid on;

hold off;
