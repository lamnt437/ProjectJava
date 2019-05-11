package lt.seqsearch;

import general.SearchArrayEngine;

public class SeqSearchEngine extends SearchArrayEngine {
	
	public SeqSearchEngine(int[] array, int target) {
		super(array, target);
	}
	
	public boolean check() {
		return target == array[getCurrentPosition()];
	}
	
	@Override
	public boolean isFinished() {
		if(currentPosition == array.length ||
				target == array[currentPosition])
			return true;
		return false;
	}
	
	@Override
	public void goNext() {
		if(!isFinished())
			currentPosition++;
	}
}
