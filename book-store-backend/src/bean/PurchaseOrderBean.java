package bean;

import java.sql.Date;
import java.util.Set;

public class PurchaseOrderBean {
	String order_id;
	String lname;
	String fname;
	String status;
	String address_id;
	Date po_date;
	Set <PurchaseItemBean> poItems;
	
	public PurchaseOrderBean(String order_id, String lname, String fname, String status, String address_id,
			Date po_date, Set<PurchaseItemBean> poItems) {
		super();
		this.order_id = order_id;
		this.lname = lname;
		this.fname = fname;
		this.status = status;
		this.address_id = address_id;
		this.po_date = po_date;
		this.poItems = poItems;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public Date getPo_date() {
		return po_date;
	}

	public void setPo_date(Date po_date) {
		this.po_date = po_date;
	}

	public Set<PurchaseItemBean> getPoItems() {
		return poItems;
	}

	public void setPoItems(Set<PurchaseItemBean> poItems) {
		this.poItems = poItems;
	}
	
}
