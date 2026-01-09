================================================================================
CPE312 - DESIGN AND ANALYSIS OF ALGORITHMS
HOMEWORK DESCRIPTION & DOCUMENTATION
================================================================================

This program processes a user-provided integer array and applies two different
linear search–based algorithmic rules to identify specific “larger” neighboring
elements on the right or left side of each element. The solution is implemented
without using built-in helper functions, relying entirely on manual loops and
comparisons as required by the assignment.

-------------------------------------------------------------------------------
1. HOW TO RUN
-------------------------------------------------------------------------------
1. Make sure that Python 3.x is installed on your computer.
2. Open a Terminal, Command Prompt (CMD), or PowerShell window.
3. Navigate to the directory containing the file "Fun.py".
4. Run the program using the following command:
   python Fun.py
5. When the message "Enter elements separated by space" appears,
   enter integers separated by spaces
   (e.g., 3 5 6 2 7 12) and press Enter.

-------------------------------------------------------------------------------
2. ALGORITHM RULES AND OVERALL LOGIC
-------------------------------------------------------------------------------

The program applies two distinct rule sets:

A) SECOND RIGHT LARGER ELEMENT
- Applied to elements from index p up to index r-2 of the array.
- For each selected element, the array is scanned to the RIGHT.
- Elements larger than the current one are counted in the order they appear.
- The SECOND encountered larger element is selected as the result.
- If a second larger element does not exist, the value is reported as "none"
  and the index as "0".

B) FIRST LEFT LARGER ELEMENT
- Applied only to the LAST TWO elements of the array (indices r-1 and r).
- For each of these elements, the array is scanned backwards to the LEFT.
- The FIRST element that is larger than the current one is selected.
- If no such element exists on the left side, the value is "none"
  and the index is "0".

C) NO-SOLUTION CASE
- If no element satisfies the required condition:
  Value  : "none"
  Index : 0

-------------------------------------------------------------------------------
3. DETAILED LINE-BY-LINE CODE EXPLANATION
-------------------------------------------------------------------------------

[Library Usage]
- import time:
  Used to measure the execution time of the algorithm in milliseconds.

[Function Definition]
- def Fun(A, p, r):
  A : List of integers to be processed
  p : Starting index of the array
  r : Ending index of the array

- output_values:
  A list that stores the resulting values found for each element.

- index_values:
  A list that stores the corresponding 1-based indices of the found values.

------------------------------------------------------------------
[SECTION 1: FINDING THE SECOND RIGHT LARGER ELEMENT]
------------------------------------------------------------------
- for i in range(p, r - 1):
  Iterates over all elements except the last two.

- count = 0:
  Counter used to track how many larger elements are found on the right.

- found_value = "none", found_index = 0:
  Default values indicating that no valid element has been found yet.

- for j in range(i + 1, r + 1):
  Scans all elements to the right of the current element.

- if A[j] > A[i]:
  Checks whether the right-side element is larger than the current element.

- if count == 2:
  When the second larger element is encountered:
  - The value is stored
  - The index is stored as j + 1 to maintain 1-based indexing
  - The inner loop is terminated early using break for efficiency

------------------------------------------------------------------
[SECTION 2: FINDING THE FIRST LEFT LARGER ELEMENT]
------------------------------------------------------------------
- for i in range(r - 1, r + 1):
  Processes only the last two elements of the array.

- for j in range(i - 1, p - 1, -1):
  Scans the array backwards toward the left.

- if A[j] > A[i]:
  When the first larger element on the left is found:
  - The value and index are stored
  - The search is terminated using break

------------------------------------------------------------------
[SECTION 3: INPUT VALIDATION AND TIME MEASUREMENT]
------------------------------------------------------------------
- try / except blocks:
  Prevent program crashes due to invalid user input.

- user_input.split():
  Splits the input string into separate elements using spaces.

- A.append(int(x)):
  Converts string inputs into integers and stores them in the list.

- start_time / end_time:
  Used to record the start and end time of the algorithm execution.

- elapsed_time:
  The total execution time is calculated in milliseconds.

-------------------------------------------------------------------------------
4. CONSTRAINTS AND TECHNICAL NOTES
-------------------------------------------------------------------------------
- Built-in functions such as max(), min(), sort(), and index() are NOT used.
- All operations are implemented using manual loops and comparisons.
- Output indices follow the assignment requirement of 1-based indexing.
- The worst-case time complexity of the algorithm is O(n²).

-------------------------------------------------------------------------------
5. SAMPLE INPUT AND OUTPUT
-------------------------------------------------------------------------------
Input:
3 5 6 2 7 12

Output:
Output values: 6 7 12 12 none none
Index Values: 3 5 6 6 0 0
Elapsed Time is ... Milliseconds.
================================================================================
