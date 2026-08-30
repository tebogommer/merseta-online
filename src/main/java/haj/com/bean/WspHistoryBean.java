package haj.com.bean;

public class WspHistoryBean {
	
	private Long  id;
	private String companyName;
	private String grantType;
	private String levyNumber;
	private Integer finYear;
	private String status;
	
	
	public WspHistoryBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WspHistoryBean(String companyName, String grantType, String levyNumber, Integer finYear, String status) {
		super();
		this.companyName = companyName;
		this.grantType = grantType;
		this.levyNumber = levyNumber;
		this.finYear = finYear;
		this.status = status;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getLevyNumber() {
		return levyNumber;
	}
	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}
	public Integer getFinYear() {
		return finYear;
	}
	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
