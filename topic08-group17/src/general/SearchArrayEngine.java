package general;

public abstract class SearchArrayEngine {
	protected int target;
	protected int[] array;
	protected int currentPosition = 0;
	
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
	
//	public boolean isFinished() {
//		if(currentPosition == array.length ||
//				target == array[currentPosition])
//			return true;
//		return false;
//	}
	
	public abstract boolean isFinished();
	
//	public void goNext() {
//		if(!isFinished())
//			currentPosition++;
//	}
	
	public abstract void goNext();
}
