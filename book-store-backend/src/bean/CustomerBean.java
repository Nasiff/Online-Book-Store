package bean;

public class CustomerBean {
	private int uid;
	private String address_id;
	
	public CustomerBean(int uid, String address_id) {
		super();
		this.uid = uid;
		this.address_id = address_id;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	
}
