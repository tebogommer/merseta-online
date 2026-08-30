package haj.com.bean;

import haj.com.framework.IDataEntity;

public class DgAllocationStatusReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String tradingName;
	private Integer grantYear;
	private Integer numberOfEmployees;
	private String categorization;
	private String organisationType;
	private String town;
	private String municipality;
	private String province;
	private String chamber;
	private String region;
	private String cloUser;
	private Double contractValue;
	private String statusAssigned;
	private String reason;

	/* Getters and Setters */
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

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

	public Integer getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getCategorization() {
		return categorization;
	}

	public void setCategorization(String categorization) {
		this.categorization = categorization;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCloUser() {
		return cloUser;
	}

	public void setCloUser(String cloUser) {
		this.cloUser = cloUser;
	}

	public Double getContractValue() {
		return contractValue;
	}

	public void setContractValue(Double contractValue) {
		this.contractValue = contractValue;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatusAssigned() {
		return statusAssigned;
	}

	public void setStatusAssigned(String statusAssigned) {
		this.statusAssigned = statusAssigned;
	}
}