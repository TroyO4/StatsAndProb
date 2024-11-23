% Pythagorean Theorem Plot
a = 0:0.1:10;
b = 5;
c = sqrt(a.^2 + b^2);

figure;
plot(a, c, 'b-', 'LineWidth', 2);

xlabel('Side a');
ylabel('Hypotenuse c');
title(['Pythagorean Theorem (b = ' num2str(b) ')']);

grid on;
