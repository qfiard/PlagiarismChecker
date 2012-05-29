/**
 * 
 */

/**
 * Contains the ref, the price of a product and the number of this product per
 * carton
 * 
 * @author matthieu
 * 
 */
public class ProductInformation {

	private String ref;
	private double price;
	private int quantityPerCarton;
	private int stock;

	public ProductInformation(String r, double p, int qpc, int s) {
		this.price = p;
		this.ref = r;
		this.quantityPerCarton = qpc;
		this.stock = s;
	}

	public String getRef() {
		return this.ref;
	}

	public double getPrice() {
		return this.price;
	}

	public int getQuantityPerCarton() {
		return this.quantityPerCarton;
	}
	
	public int getStock() {
		return this.stock;
	}

}
