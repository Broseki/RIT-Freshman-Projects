import time
import random
import matplotlib.pyplot
'''
Designed by Mike Canning @ RIT CS
Quick Sort Reference: https://interactivepython.org/runestone/static/pythonds/SortSearch/TheQuickSort.html
'''


# PLACE CONFIGURATION CONSTANTS HERE.
DATA_SIZE = 50000
MAX_INT = 900


min = 0 # This value can be set and reset


# Begin Referenced Quick Sort Implementation

def bigsort(data: list, start: int, end: int) -> None:
    """
    Starts the recursive quick sort function
    :param data: The list being sorted
    :param start: Where the start of the list section being sorted is
    :param end: The end of the section to be sorted in data
    :pre: data is unsorted
    :post: data is sorted in the given section
    """
    quickSortHelper(data, start, end)


def quickSortHelper(alist: list, first: int, last: int) -> None:
    """
    This is where the quicksort recursion is actually implemented.
    :param alist: The list being sorted
    :param first: The start of the list section being sorted
    :param last: The end of the list section being sorted
    :pre: The list section is not sorted
    :post: The list section is sorted after a series of recursive calls to this function
    """
    if first < last:
        if len(alist[first: last]) < min:
            smallsort(alist, first, last)
        else:
            splitpoint = partition(alist, first, last)
            quickSortHelper(alist, first, splitpoint - 1)
            quickSortHelper(alist, splitpoint + 1, last)


def partition(alist: list, first: int, last: int) -> int:
    """
    Splits the list into the correct high and low sides determined by the pivot
    :param alist: The list being sorted
    :param first: The start of the list section being sorted
    :param last: The end of the list section being sorted
    :return rightmark: Where the function left off sorting
    :pre: The list section is unsorted and the pivot and partitions have not been determined
    :post: The list section is sorted after the pivot and partitions are determined
    """
    pivotvalue = alist[first]

    leftmark = first+1
    rightmark = last

    done = False

    while not done:

        while leftmark <= rightmark and alist[leftmark] <= pivotvalue:
            leftmark = leftmark + 1

        while alist[rightmark] >= pivotvalue and rightmark >= leftmark:
            rightmark = rightmark -1

        if rightmark < leftmark:
            done = True
        else:
            temp = alist[leftmark]
            alist[leftmark] = alist[rightmark]
            alist[rightmark] = temp

    temp = alist[first]
    alist[first] = alist[rightmark]
    alist[rightmark] = temp
    return rightmark

# End of quick sort implementation


def is_sorted( data: list, start: int, end: int ) -> bool:
    """
    This function determines whether or not the list provided is sorted
    :param data: The list being checked
    :param start: The start of the list section being checked
    :param end: The end of the list section being checked
    :return: Returns True if the list is sorted or False if the list is not sorted
    :pre: The list is provided and nothing has been checked yet
    :post: The list has been checked for proper sorting, and True or False has been returned
    """
    for x in range(start, end - 1):
        if data[x] <= data[x + 1]:
            pass
        else:
            return False
    return True


def hybrid_sort( data: list, start: int, end: int ) -> None:
    """
    Sort data in-place using one of several algorithms depending
    on the size of the data.
    :param data: a list of comparable elements
    :param start: The first index in the segment to be sorted
    :param end: The last index in the segment to be sorted
    :pre: The list is not sorted and has not been passed to any sorted function
    :post: The list has been passed to the appropriate sorting function based on the min global variable and list length
    """
    size = end + 1 - start
    if size < 2:
        return
    elif size == 2:
        sort2( data, start )
    elif size == 3:
        sort3( data, start )
    elif size < min:
        smallsort( data, start, end )
    else:
        bigsort( data, start, end )


def int_test( the_list: list ) -> float:
    """
    Times the sorting of the list and returns the time it took to sort
    :param the_list: The list of randomly generated numbers to pass to hybrid_sort
    :return: The time the sorting of the_list took
    :pre: An integer list is provided in no particular order
    :post: The list is sorted and the time the sorting took has been returned
    """
    numbers = the_list
    h_start = time.time( )
    hybrid_sort( numbers, 0, len( numbers ) - 1 )
    h_end = time.time( )
    if not is_sorted(numbers, 0, len(numbers) - 1):
        print("Oops!")
    return h_end - h_start


def sort2(lst: list, start: int) -> None:
    """
    Sorts a list of length 2
    :param lst: The list being sorted
    :param start: Where the section being sorted starts
    :pre: The list lst has not been sorted
    :post: The list lst has been sorted for the given section
    """
    if lst[start] > lst[1 + start]:
        lst[start], lst[start + 1] = lst[start + 1], lst[start]


def sort3(lst: list, start: int) -> None:
    """
    Sorts a list of length 3
    :param lst: The list being sorted
    :param start: Where the section being sorted starts
    :pre: The list lst has not been sorted
    :post: The list lst has been sorted for the given section
    """
    if lst[0 + start] > lst[1 + start]:
        lst[start], lst[start + 1] = lst[start + 1], lst[start]
    if lst[1 + start] > lst[2 + start]:
        lst[start + 1], lst[start + 2] = lst[start + 2], lst[start + 1]
    if lst[0 + start] > lst[1 + start]:
        lst[start], lst[start + 1] = lst[start + 1], lst[start]


def smallsort(data: list, start: int, end: int) -> None:
    """
    Sorts a list using bubble sort
    :param data: The list being sorted
    :param start: The start of the list section being sorted
    :param end: The end of the list section being sorted
    :pre: The list data is not sorted
    :post: The list data is sorted for the given section
    """
    size = end + 1 - start
    if size == 3:
        sort3(data, start)
    elif size == 2:
        sort2(data, start)
    else:
        for i in range(start, end + 1):
            x = i
            while x > start and data[x-1] > data[x]:
                data[x], data[x-1] = data[x-1], data[x]
                x -= 1


def main( ) -> None:
    """
    Runs all tests on the hybrid sorting function and plot the results.
    :Pre: None
    :Post: THe program has been completely run, and the bar graph is displayed
    """
    global min
    print("Running test...please wait...")
    test_values = {}
    for min_test_number in range(0, 151):
        print("Testing: " + str(min_test_number))
        total_time = 0
        min = min_test_number
        for _ in range(0, 3):
            random_array = [ random.randint( 0, MAX_INT ) for _ in range( DATA_SIZE ) ]
            for _ in range(0, 3):
                total_time += int_test(random_array)
        test_values[min_test_number] = total_time / 1

    matplotlib.pyplot.bar(range(len(test_values)), test_values.values(), align='center')
    matplotlib.pyplot.xticks(range(len(test_values)), list(test_values.keys()))
    print("Close the graph when you are done.")
    matplotlib.pyplot.show()


if __name__ == "__main__":
    main( )