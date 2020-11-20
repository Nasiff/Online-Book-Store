package bean;

public class EnrollmentBean {
	String cid;
	String students;
	String credit;

	public EnrollmentBean(String cid, String students, String credit) {
		this.cid = cid;
		this.students = students;
		this.credit = credit;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStudents() {
		return students;
	}

	public void setStudents(String students) {
		this.students = students;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	
}
