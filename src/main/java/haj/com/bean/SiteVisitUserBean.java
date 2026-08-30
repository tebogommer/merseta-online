package haj.com.bean;

public class SiteVisitUserBean {
	
	
	private String jobTitle;
	private String firstName;
	private String lastName;
	
	
	
	public SiteVisitUserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SiteVisitUserBean(String jobTitle, String firstName, String lastName) {
		super();
		this.jobTitle = jobTitle;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
