package spite;

public class test {
	public static void main(String[] args) {
		int x, y;
		for (x = -100; x < 100; x++) {
			for (y = -100; y < 100; y++) {
				if (x + y == 4 && x - y == 2) {
					System.out.println(x + " " + y);
				}
				continue;
			}
			continue;
		}
	}
}
