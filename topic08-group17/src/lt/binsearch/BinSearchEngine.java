package lt.binsearch;

import general.SearchArrayEngine;

public class BinSearchEngine extends SearchArrayEngine {
	private int start;
	private int end;
	
	public BinSearchEngine(int[] array, int target) {
		super(array, target);
		start = 0;
		end = array.length - 1;
		currentPosition = (start + end) / 2;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public int check() {
		if(target == array[currentPosition])
			return 0;
		if(target < array[currentPosition]) {
			// update range
			end = currentPosition - 1;
			return -1;
		}
		
		//update range
		start = currentPosition + 1;
		return 1;
	}
	
	@Override
	public boolean isFinished() {
		if(target == array[currentPosition] ||
				start > end)
			return true;
		return false;
	}
	
	@Override
	public void goNext() {
		currentPosition = (start + end) / 2;
	}

}
