package bean;

public class UserBean {
	String uid;
	String lname;
	String fname;
	String email;
	String password;
	String user_type;
	
	public UserBean(String uid, String lname, String fname, String email, String password, String user_type) {
		super();
		this.uid = uid;
		this.lname = lname;
		this.fname = fname;
		this.email = email;
		this.password = password;
		this.user_type = user_type;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	
}
