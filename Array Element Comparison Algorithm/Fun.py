import time  # Used to measure execution time of the algorithm

def Fun(A, p, r):
    """
    A  : Input array
    p  : Starting index of the array
    r  : Ending index of the array

    The function searches for:
    - The 2nd greater element on the right for each element
    - For the last two elements, the first greater element on the left
    """

    output_values = []   # Stores the found values
    index_values = []    # Stores the 1-based indices of found values

    # For each element between p and r-2
    # find the second element on the right that is greater than A[i]
    for i in range(p, r - 1):
        count = 0
        found_value = "none"
        found_index = 0

        # Scan elements to the right of index i
        for j in range(i + 1, r + 1):
            if A[j] > A[i]:
                count += 1
                # When the second greater element is found, stop searching
                if count == 2:
                    found_value = A[j]
                    found_index = j + 1  # Convert to 1-based index
                    break

        output_values.append(found_value)
        index_values.append(found_index)

    # For the last two elements of the array
    # find the first greater element on the left side
    for i in range(r - 1, r + 1):
        found_value = "none"
        found_index = 0

        # Scan elements to the left of index i
        for j in range(i - 1, p - 1, -1):
            if A[j] > A[i]:
                found_value = A[j]
                found_index = j + 1  # Convert to 1-based index
                break

        output_values.append(found_value)
        index_values.append(found_index)

    return output_values, index_values


try:
    # Read input from user
    user_input = input("Enter elements separated by space\n")
    if not user_input.strip():
        raise ValueError("Input cannot be empty.")

    parts = user_input.split()
    if len(parts) < 2:
        raise ValueError("At least two numbers are required.")

    # Convert input strings to integers
    A = []
    for x in parts:
        A.append(int(x))

    start_time = time.time()  # Start timing the function execution

    values, indexes = Fun(A, 0, len(A) - 1)

    end_time = time.time()  # End timing

    # Print output values
    print("Output values:", end=" ")
    for v in values:
        print(v, end=" ")
    print()

    # Print index values
    print("Index Values:", end=" ")
    for idx in indexes:
        print(idx, end=" ")
    print()

    # Calculate elapsed time in milliseconds
    elapsed_time = (end_time - start_time) * 1000
    print(f"Elapsed Time is {elapsed_time:.4f} Milliseconds.")

except ValueError as ve:
    # Handle invalid user input
    print(f"Input Error: {ve}")
except Exception as e:
    # Handle unexpected errors
    print(f"Unexpected error: {e}")