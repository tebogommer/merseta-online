package haj.com.bean;

import haj.com.framework.IDataEntity;

public class CompanyRegionReportBean implements IDataEntity {

	private String companyName;
	private String tradingName;
	private String levyNumber;
	private String region;
	private String town;
	private String cloUserFullName;
	private String cloUserEmail;
	private String crmUserFullName;
	private String crmUserEmail;

	/* getters and setters */
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCloUserFullName() {
		return cloUserFullName;
	}

	public void setCloUserFullName(String cloUserFullName) {
		this.cloUserFullName = cloUserFullName;
	}

	public String getCloUserEmail() {
		return cloUserEmail;
	}

	public void setCloUserEmail(String cloUserEmail) {
		this.cloUserEmail = cloUserEmail;
	}

	public String getCrmUserFullName() {
		return crmUserFullName;
	}

	public void setCrmUserFullName(String crmUserFullName) {
		this.crmUserFullName = crmUserFullName;
	}

	public String getCrmUserEmail() {
		return crmUserEmail;
	}

	public void setCrmUserEmail(String crmUserEmail) {
		this.crmUserEmail = crmUserEmail;
	}

}