package haj.com.bean;

public class PartyBean {
	private String company;
	private String name;
	private String email;
	
	public PartyBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PartyBean(String company, String name, String email) {
		super();
		this.company = company;
		this.name = name;
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
