package bean;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "sid", "name", "credit_taken", "credit_graduate" })
public class StudentBean {
	String sid;
	String name;
	String credit_taken;
	String credit_graduate;

	public StudentBean() {
		this.sid = UUID.randomUUID().toString();
		this.name = "Rodriguez";
		this.credit_taken = "20";
		this.credit_graduate = "120";
	}
	
	public StudentBean(String name, String credit_taken) {
		this.sid = UUID.randomUUID().toString();
		this.name = name;
		this.credit_taken = credit_taken;
		this.credit_graduate = "120";
	}

	public StudentBean(String sid, String name, String credit_taken, String credit_graduate) {
		this.sid = sid;
		this.name = name;
		this.credit_taken = credit_taken;
		this.credit_graduate = credit_graduate;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreditsTaken() {
		return credit_taken;
	}

	public void setCreditsTaken(String creditsTaken) {
		this.credit_taken = creditsTaken;
	}

	public String getCreditsGraduate() {
		return credit_graduate;
	}

	public void setCreditsGraduate(String creditsGraduate) {
		this.credit_graduate = creditsGraduate;
	}
}
