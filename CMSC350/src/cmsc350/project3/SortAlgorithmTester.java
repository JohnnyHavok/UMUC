package cmsc350.project3;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 3
 * Date: 12/1/12 1:37 PM
 * Requires: J2SE 7+
 */


/**
 * Class derived from Lewis/Chase ch. 8 and Dr. Nicholas Duchon and modified to keep track of algorithm runtime
 * information including number of comparisons, swaps, recursive calls (if applicable), and total run time.
 * This information is returned as a SortingStatsNode.
 */
public class SortAlgorithmTester {
	/**
	 * Selection Sort Algorithm
	 * @param data : Array to be sorted
	 * @return SortingStatsNode containing runtime information on algorithm.
	 */
	public static <T extends Comparable<? super T>> SortingStatsNode selectionSort(T[] data) {
		SortingStatsNode stats = new SortingStatsNode("Selection Sort");
		long c = 0, s = 0;
		double start = System.nanoTime();

		int min;
		T temp;

		for(int index = 0; index < data.length - 1; index++) {
			min = index;
			for(int scan = index+1; scan < data.length; scan++) {
				c+=1;
				if(data[scan].compareTo(data[min]) < 0)
					min = scan;
			}

			// Swap the values
			temp = data[min];
			data[min] = data[index];
			data[index] = temp;
			s+=1;
		}

		stats.setRunTime(System.nanoTime() - start);
		stats.setComparisons(c);
		stats.setSwaps(s);
		return stats;
	}

	/**
	 * Insertion Sort Algorithm
	 * @param data : Array to be sorted
	 * @return SortingStatsNode containing runtime information on algorithm.
	 */
	public static <T extends Comparable<? super T>> SortingStatsNode insertionSort(T[] data) {
		SortingStatsNode stats = new SortingStatsNode("Insertion Sort");
		long c = 0, s = 0;
		double start = System.nanoTime();

		for(int index = 1; index < data.length; index++) {
			T key = data[index];
			int position = index;

			// Shift larger values to the right
			while(position > 0 && data[position-1].compareTo(key) > 0) {
				c+=1;
				data[position] = data[position-1];
				position--; // Fixed error in book
			}
			// Java will short-circuit && op so we trade off a little run time speed so our comparison count is
			// correct.  Check to see if position > 0 meaning we did execute a compareTo event though we didn't
			// execute the while.
			if(position > 0) c+=1;
			data[position] = key;
			s+=1;
		}

		stats.setRunTime(System.nanoTime() - start);
		stats.setComparisons(c);
		stats.setSwaps(s);
		return stats;
	}

	/**
	 * Bubble Sort Algorithm
	 * @param data : Array to be sorted
	 * @return SortingStatsNode containing runtime information on algorithm.
	 */
	public static <T extends Comparable<? super T>> SortingStatsNode bubbleSort(T[] data) {
		SortingStatsNode stats = new SortingStatsNode("Bubble Sort");
		long c = 0, s = 0;
		double start = System.nanoTime();

		int position, scan;
		T temp;

		for(position = data.length - 1; position >= 0; position--) {
			for(scan = 0; scan <= position - 1; scan++) {
				if(data[scan].compareTo(data[scan+1]) > 0) {
					// Swap values
					temp = data[scan];
					data[scan] = data[scan + 1];
					data[scan + 1] = temp;
					s+=1;
				}
				c+=1;
			}
		}

		stats.setRunTime(System.nanoTime() - start);
		stats.setComparisons(c);
		stats.setSwaps(s);
		return stats;
	}

	/**
	 * Quick Sort Algorithm public entry point into quick sort.
	 * @param data : Array to be sorted
	 * @return SortingStatsNode containing runtime information on algorithm.
	 */
	public static <T extends Comparable<? super T>> SortingStatsNode quickSort(T[] data) {
		SortingStatsNode stats = new SortingStatsNode("Quick Sort");
		qsRecCalls = 0;
		qsSwaps = qsComps = 0;
		double start = System.nanoTime();
		quickSort(data, 0, data.length - 1);
		stats.setRunTime(System.nanoTime() - start);
		stats.setComparisons(qsComps);
		stats.setSwaps(qsSwaps);
		stats.setRecursiveCalls(qsRecCalls);
		return stats;
	}

	/**
	 * Static variables used each time Quick Sort is run that tally information about comparisons, calls, and swaps.
	 */
	private static int qsRecCalls;
	private static long qsComps, qsSwaps;

	/**
	 * Quick Sort Algorithm - Internal entry point
	 * @param data : Array to be sorted
	 * @param min : Lower bound of partition
	 * @param max : Upper bound of partition
	 */
	private static <T extends Comparable<? super T>> void quickSort(T[] data, int min, int max) {
		int indexOfPartition;

		if(max - min > 0) {
			// Create partitions
			indexOfPartition = findPartition(data, min, max);

			// Sort left side
			quickSort(data, min, indexOfPartition - 1);
			qsRecCalls+=1;

			// Sort right side
			quickSort(data, indexOfPartition + 1, max);
			qsRecCalls+=1;
		}
	}

	/**
	 * Internal mechanics that does the work of comparisons and swaps.
	 * @param data : Array/Sub-Array to be sorted
	 * @param min : Lower bound of partition
	 * @param max : Upper bound of partition
	 * @return The next partition for Quick Sort
	 */
	private static <T extends Comparable<? super T>> int findPartition(T[] data, int min, int max) {
		T temp, partitionelement;

		// ND: array with 2 elements is a special case
		if(min + 1 == max) {
			if(data[min].compareTo(data[max]) > 0) {
				temp = data[min];
				data[min] = data[max];
				data[max] = temp;
				qsSwaps+=1;
			} // end if just two elements out of order
			qsComps+=1;
			return min;
		} // end if just two elements

		int left, right;
		int middle = (min + max) / 2;

		partitionelement = data[middle];
		left = min;
		right = max;

		// ND: move the pivot to the first element in the partition so the code can handle repeats nicely
		temp = data[left];
		data[left] = partitionelement;
		data[middle] = temp;
		left++; // ND: DO not include the pivot in the partitioning process
		qsSwaps+=1;

		while(left < right) {

			// Search for an element that is > the partitionelement
			while(data[left].compareTo(partitionelement) <= 0 && left < right) {
				left++;
				qsComps+=1;
			}

			// Search for an element that is < the partitionelement
			while(data[right].compareTo(partitionelement) > 0) {
				right--;
				qsComps+=1;
			}

			// Swap the elements
			if(left < right) {
				temp = data[left];
				data[left] = data[right];
				data[right] = temp;
				qsSwaps+=1;
			}
		}

		// Move partition element to partition index

		temp = data[min];
		data[min] = data[right];
		data[right] = temp;
		qsSwaps+=1;

		return right;
	}

	/**
	 * Merge Sort Algorithm - public entry point
	 * @param data : Array to be sorted
	 * @return SortingStatsNode containing runtime information on algorithm.
	 */
	public static <T extends Comparable<? super T>> SortingStatsNode mergeSort(T[] data) {
		SortingStatsNode stats = new SortingStatsNode("Merge Sort");
		msRecCalls = 0;
		msSwaps = msComps = 0;
		double start = System.nanoTime();
		mergeSort(data, 0, data.length - 1);
		stats.setRunTime(System.nanoTime() - start);
		stats.setComparisons(msComps);
		stats.setSwaps(msSwaps);
		stats.setRecursiveCalls(msRecCalls);
		return stats;
	}

	/**
	 * Static variables that keep track of merge sort comparisons, swaps, and recursive calls.
	 */
	private static int msRecCalls;
	private static long msComps, msSwaps;

	/**
	 * Merge Sort Algorithm - private entry point
	 * @param data : Array to be sorted
	 * @param min : Lower bound of Array/Sub-Array currently working on
	 * @param max : Upper bound of Array/Sub-Array currently working on
	 */
	@SuppressWarnings({"unchecked"})
	private static <T extends Comparable<? super T>> void mergeSort(T[] data, int min, int max) {
		T[] temp;
		int index, left, right;

		// return on list of length 1
		if(min == max)
			return;

		// Find the length and the midpoint of the list
		int size = max - min + 1;
		int pivot = (min + max) / 2;
		temp = (T[])(new Comparable[size]);  // Java doesn't handle generic arrays well.

		mergeSort(data, min, pivot); // Sort left half of list
		msRecCalls+=1;
		mergeSort(data, pivot + 1, max); // Sort right half of list
		msRecCalls+=1;

		// Copy sorted data into workspace
		for(index = 0; index < size; index++)
			temp[index] = data[min + index];

		// Merge the two sorted lists
		left = 0;
		right = pivot - min + 1;
		for(index = 0; index < size; index++) {
			if(right <= max - min) {
				if (left <= pivot - min) {
					if (temp[left].compareTo(temp[right]) > 0) {
						data[index + min] = temp[right++];
						msSwaps+=1;
						msComps+=1;
					} else {
						data[index + min] = temp[left++];
						msSwaps+=1;
						msComps+=1;
					}
				} else {
					data[index + min] = temp[right++];
					msSwaps+=1;
				}
			} else {
				data[index + min] = temp[left++];
				msSwaps+=1;
			}
		}
	}
}
