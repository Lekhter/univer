package akhtyrska;

public class ThreadTest {
	static boolean b = false;
	static int x = 0;

	public static void m() {
		x = 1;
		b = true;
	}

	public static void m2() {
		if (b) {
			System.out.println("x=" + x);
		}

	}

	public static void main(String[] args) {
		boolean b = false;
		int x = 0;

		new Thread(new Runnable() {

			public void run() {
				m();
			}

		}).start();

		new Thread(new Runnable() {

			public void run() {
				m2();
			}

		}).start();

	}
}
