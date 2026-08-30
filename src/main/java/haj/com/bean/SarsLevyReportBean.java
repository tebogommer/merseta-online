package haj.com.bean;

public class SarsLevyReportBean {
	
	/*
	 * SARS Levy Detail Report Bean
	 * 
	 * Used on the following reports:
	 * SARS Report By Scheme Year Summary (/MerSETA/src/main/webapp/admin/sarsReportBySchemeYearSummary.xhtml)
	 * Summary of SARS Levy Detail By Arrival Dates: (/MerSETA/src/main/webapp/admin/sarsReportBetweenArrivalDates.xhtml)
	 * SARS Summary Over Multiple Scheme Year Report: (/MerSETA/src/main/webapp/admin/sarsReportSummaryMultipleSchemeYears.xhtml)
	 */
	
	// used for reference number based on sepecific report
	private String description;
	// this filed is used for the company name based on reference number
	private String additionalInformation;
	// used for the Scheme Year Reports
	private Integer schemeYear;
	
	private Double mandatoryLevy;
	private Double discretionaryLevy;
	private Double adminLevy;
	private Double interest;
	private Double penalty;
	private Double total;
	
	
	/* Getters and setters */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public Double getMandatoryLevy() {
		return mandatoryLevy;
	}
	public void setMandatoryLevy(Double mandatoryLevy) {
		this.mandatoryLevy = mandatoryLevy;
	}
	public Double getDiscretionaryLevy() {
		return discretionaryLevy;
	}
	public void setDiscretionaryLevy(Double discretionaryLevy) {
		this.discretionaryLevy = discretionaryLevy;
	}
	public Double getAdminLevy() {
		return adminLevy;
	}
	public void setAdminLevy(Double adminLevy) {
		this.adminLevy = adminLevy;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getPenalty() {
		return penalty;
	}
	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}
	public Integer getSchemeYear() {
		return schemeYear;
	}
	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

}