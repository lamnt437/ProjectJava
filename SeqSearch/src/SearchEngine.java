public class SearchEngine {
	private int target;
	private int[] array;
	private int currentPosition = 0;
	
	public SearchEngine(int[] array, int target) {
		this.target = target;
		this.array = array;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public boolean check() {
		return target == array[getCurrentPosition()];
	}
	
	public boolean isFinished() {
		if(currentPosition == array.length - 1 ||
				target == array[currentPosition])
			return true;
		return false;
	}
	
	public void goNext() {
		if(!isFinished())
			currentPosition++;
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
