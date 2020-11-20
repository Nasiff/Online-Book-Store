package bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "sisReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper {
	@XmlAttribute
	private String namePrefix;

	@XmlAttribute(name = "creditTaken")
	private int creditTaken;

	@XmlElement(name = "studentList")
	private List<StudentBean> list;

	public ListWrapper() {
		this.namePrefix = "Rodriguez";
		this.creditTaken = 20;
		this.list = new ArrayList<StudentBean>();
	}

	public ListWrapper(String namePrefix, String credit_taken, List<StudentBean> list) {
		this.namePrefix = namePrefix;
		this.creditTaken = Integer.parseInt(credit_taken);
		this.list = list;
	}
}