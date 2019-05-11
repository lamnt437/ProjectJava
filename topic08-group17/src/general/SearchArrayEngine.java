package general;

public abstract class SearchArrayEngine {
	private int target;
	private int[] array;
	private int currentPosition = 0;
	
	public SearchArrayEngine(int[] array, int target) {
		this.target = target;
		this.array = array;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
	
//	public boolean check() {
//		return target == array[getCurrentPosition()];
//	}
	
	public abstract boolean check();
	
	public boolean isFinished() {
		if(currentPosition == array.length ||
				target == array[currentPosition])
			return true;
		return false;
	}
	
	public void goNext() {
		if(!isFinished())
			currentPosition++;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	
	
	
	
//	public int search(int array[], int target) {
//		int size = array.length;
//		for(int i = 0; i < size; i++) {
//			if(array[i] == target)
//				return i;
//		}
//		return -1;
//	}
}
