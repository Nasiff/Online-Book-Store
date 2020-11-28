package bean;

public class PurchaseItemBean {
	String order_id;
	String bid; 
	double price;
	int quantity;
	
	public PurchaseItemBean(String order_id, String bid, double price, int quantity) {
		super();
		this.order_id = order_id;
		this.bid = bid;
		this.price = price;
		this.quantity = quantity;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
