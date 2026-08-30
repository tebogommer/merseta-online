import java.util.Random;

public class testNumGen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random r = new Random(System.currentTimeMillis());
		int num = ((1 + r.nextInt(2)) * 1000 + r.nextInt(1000));
		System.out.println(num);
	}

	public int gen() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 1000 + r.nextInt(1000));
	}

}
