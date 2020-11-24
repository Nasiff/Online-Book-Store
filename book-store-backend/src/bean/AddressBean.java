package bean;

public class AddressBean {
	private String id;
	private String street;
	private String province_state;
	private String country;
	private String zip;
	private String phone;
	
	public AddressBean(String id, String street, String province_state, String country, String zip, String phone) {
		super();
		this.id = id;
		this.street = street;
		this.province_state = province_state;
		this.country = country;
		this.zip = zip;
		this.phone = phone;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getProvince_state() {
		return province_state;
	}
	public void setProvince_state(String province_state) {
		this.province_state = province_state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
