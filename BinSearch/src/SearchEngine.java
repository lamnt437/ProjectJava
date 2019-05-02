
public class SearchEngine {
	private int[] array;
	private int target;
	private int currentPosition;
	private int start;
	private int end;
	
	public SearchEngine(int[] array, int target) {
		this.target = target;
		this.array = array;
		start = 0;
		end = array.length - 1;
		currentPosition = (start + end) / 2;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
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
	
	public boolean isFinished() {
		if(target == array[currentPosition] ||
				start > end)
			return true;
		return false;
	}
	
	public void goNext() {
		currentPosition = (start + end) / 2;
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	
	
//	public int search(int array[], int target) {
//		
//	}
}
