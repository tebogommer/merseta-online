
package haj.com.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressLookup.
 */
public class TestBean {
	private String code;
	private String name;
	private int price;
	private int quantity;

	public TestBean() {
		super();

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "TestBean [code=" + code + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
	}
}