public class Snake {
	static final int MAX_INPUT = 100000000;

	Box[] boxArray;
	int numberOfElements;

	BoxRow() {
		boxArray = new Box[MAX_INPUT];
		numberOfElements = 0;
	}
	void addBoxAtTheBack(Box box) {
		boxArray[numberOfElements] = box;
		numberOfElements ++;
	}
	void moveSnake(Box box) {
		for(int i = numberOfElements; i >= 1; i--) {
			boxArray[i] = boxArray[i-1];
		}
		boxArray[0] = box;
	}
}