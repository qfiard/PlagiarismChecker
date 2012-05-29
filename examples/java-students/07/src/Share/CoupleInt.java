package Share;

/**
 * Contient deux entiers disponible grâce à getA() et getB()
 * @author Iskeinder
 *
 */
public class CoupleInt {
	private int a;
	private int b;
	
	public CoupleInt(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}
	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}
}
