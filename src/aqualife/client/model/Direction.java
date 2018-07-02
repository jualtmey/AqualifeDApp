package aqualife.client.model;

public enum Direction {
	LEFT(-1), RIGHT(+1);

	private int vector;

	private Direction(int vector) {
		this.vector = vector;
	}

	public int getVector() {
		return vector;
	}

	public Direction reverse() {
		return this == LEFT ? RIGHT : LEFT;
	}

	public static Direction toDirection(int d) {
		if (d == -1) {
			return LEFT;
		} else {
			return RIGHT;
		}
	}
}
