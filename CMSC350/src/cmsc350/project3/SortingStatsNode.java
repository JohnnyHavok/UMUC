package cmsc350.project3;

/**
 * Name: Justin Smith
 * CMSC 350
 * Project: 3
 * Date: 12/1/12 6:47 PM
 * Requires: J2SE 7+
 */


public class SortingStatsNode implements Comparable<SortingStatsNode> {
	private String name;
	private int recursiveCalls;
	private long comparisons, swaps;
	private double runTime;

	public SortingStatsNode(String name) {
		this.name = name;
		recursiveCalls = 0;
		comparisons = swaps = 0;
	}

	public String getName() { return name; }

	public double getRunTime() { return runTime; }
	public void setRunTime(double runTime) { this.runTime = runTime; }

	public long getComparisons() { return comparisons; }
	public void setComparisons(long comparisons) { this.comparisons = comparisons; }

	public int getRecursiveCalls() { return recursiveCalls; }
	public void setRecursiveCalls(int recursiveCalls) { this.recursiveCalls = recursiveCalls; }

	public long getSwaps() { return swaps; }
	public void setSwaps(long swaps) { this.swaps = swaps; }

	@Override
	public int compareTo(SortingStatsNode o) {
		if(this.runTime < o.getRunTime())
			return -1;
		if(this.runTime > o.getRunTime())
			return 1;
		return 0;
	}
}
