def find_largest_even_index(lst):
    max_even = float('-inf') 
    index = 0
    for i in range(len(lst)):
        if lst[i] % 2 == 0 and lst[i] > max_even:
            max_even = lst[i]
            index = i + 1 
    if max_even == float('-inf'): 
        return 0
    else:
        return index

input_str = input("Enter a list of integers separated by spaces: ")
list_of_integers = list(map(int, input_str.split()))

largest_even_index = find_largest_even_index(list_of_integers)
if largest_even_index == 0:
    print("No even integers found in the list.")
else:
    print("Location in list of the largest even integer:", largest_even_index)
