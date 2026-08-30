package haj.com.bean;

/**
 * The Class DiscretionaryGrantBean.
 */
public class CompanyUpdateInfoBean {

	private Boolean companyName;
	private Boolean tradingName;
	private Boolean levyNumber;
	private Boolean email;
	private Boolean siccode;
	private Boolean tellNumber;
	private Boolean faxNumber;
	private Boolean registrationNumber;
	private Boolean setaSelection;

	public CompanyUpdateInfoBean() {
		super();
		companyName = false;
		tradingName = false;
		levyNumber = false;
		email = false;
		siccode = false;
		tellNumber = false;
		faxNumber = false;
		registrationNumber = false;
		setaSelection = false;
	}

	public Boolean getCompanyName() {
		return companyName;
	}

	public void setCompanyName(Boolean companyName) {
		this.companyName = companyName;
	}

	public Boolean getTradingName() {
		return tradingName;
	}

	public void setTradingName(Boolean tradingName) {
		this.tradingName = tradingName;
	}

	public Boolean getEmail() {
		return email;
	}

	public void setEmail(Boolean email) {
		this.email = email;
	}

	public Boolean getSiccode() {
		return siccode;
	}

	public void setSiccode(Boolean siccode) {
		this.siccode = siccode;
	}

	public Boolean getTellNumber() {
		return tellNumber;
	}

	public void setTellNumber(Boolean tellNumber) {
		this.tellNumber = tellNumber;
	}

	public Boolean getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(Boolean faxNumber) {
		this.faxNumber = faxNumber;
	}

	public Boolean getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(Boolean registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Boolean getSetaSelection() {
		return setaSelection;
	}

	public void setSetaSelection(Boolean setaSelection) {
		this.setaSelection = setaSelection;
	}

	public Boolean getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(Boolean levyNumber) {
		this.levyNumber = levyNumber;
	}	

}