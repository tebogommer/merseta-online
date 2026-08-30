package haj.com.bean;

public class EmploymentBean {
	private String fromToDate;
	private String position;
	private String description;
	
	public EmploymentBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmploymentBean(String fromToDate, String position, String description) {
		super();
		this.fromToDate = fromToDate;
		this.position = position;
		this.description = description;
	}

	public String getFromToDate() {
		return fromToDate;
	}

	public void setFromToDate(String fromToDate) {
		this.fromToDate = fromToDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
