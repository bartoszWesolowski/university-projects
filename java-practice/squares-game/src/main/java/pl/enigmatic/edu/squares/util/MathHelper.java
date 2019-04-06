package pl.enigmatic.edu.squares.util;

public class MathHelper {
	public static boolean isInRange(int x, int left, int right) {
		return (x >= left && x <= right);
	}

	public static int clamp(int x, int left, int right) {
		if (x < left) {
			return left;
		}
		if (x > right) {
			return right;
		}
		return x;
	}
}
