package haj.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class NsdpSummaryReportBean.
 */
public class NsdpExtractReportBean implements Serializable {

	private Integer orderNumber;
	private String financialYear;
	private String nsdpOutcomes;
	private String nsdpSubOutcomes;
	private String outputIndicators;
	private Integer annualTraget;
	private Integer quarterOneTarget;
	private Integer quarterOneAchived;
	private Integer quarterTwoTarget;
	private Integer quarterTwoAchived;
	private Integer quarterThreeTarget;
	private Integer quarterThreeAchived;
	private Integer quarterFourTarget;
	private Integer quarterFourAchived;
	private Integer overallAchived;
	private Double percentageAchived;
	private Integer varinaceAchived;
	
	public NsdpExtractReportBean() {
		super();
	}
	/* Constructor used on report extract */
	public NsdpExtractReportBean(Integer orderNumber, String financialYear, String nsdpOutcomes, String nsdpSubOutcomes, String outputIndicators, Integer annualTraget) {
		super();
		this.orderNumber = orderNumber;
		this.financialYear = (financialYear != null) ? financialYear.trim() : "";
		this.financialYear = (financialYear != null) ? financialYear.trim() : "";
		this.nsdpOutcomes = (nsdpOutcomes != null) ? nsdpOutcomes.trim() : "";
		this.nsdpSubOutcomes = (nsdpSubOutcomes != null) ? nsdpSubOutcomes.trim() : "";
		this.outputIndicators = (outputIndicators != null) ? outputIndicators.trim() : "";
		this.annualTraget = (annualTraget != null) ? annualTraget : 0;
		this.quarterOneTarget = 0;
		this.quarterOneAchived = 0;
		this.quarterTwoTarget = 0;
		this.quarterTwoAchived = 0;
		this.quarterThreeTarget = 0;
		this.quarterThreeAchived = 0;
		this.quarterFourTarget = 0;
		this.quarterFourAchived = 0;
		this.overallAchived = 0;
		this.percentageAchived = 0.0d;
		this.varinaceAchived = 0;
	}

	/* Getters and setters */
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getNsdpOutcomes() {
		return nsdpOutcomes;
	}
	public void setNsdpOutcomes(String nsdpOutcomes) {
		this.nsdpOutcomes = nsdpOutcomes;
	}
	public String getNsdpSubOutcomes() {
		return nsdpSubOutcomes;
	}
	public void setNsdpSubOutcomes(String nsdpSubOutcomes) {
		this.nsdpSubOutcomes = nsdpSubOutcomes;
	}
	public String getOutputIndicators() {
		return outputIndicators;
	}
	public void setOutputIndicators(String outputIndicators) {
		this.outputIndicators = outputIndicators;
	}
	public Integer getAnnualTraget() {
		return annualTraget;
	}
	public void setAnnualTraget(Integer annualTraget) {
		this.annualTraget = annualTraget;
	}
	public Integer getQuarterOneTarget() {
		return quarterOneTarget;
	}
	public void setQuarterOneTarget(Integer quarterOneTarget) {
		this.quarterOneTarget = quarterOneTarget;
	}
	public Integer getQuarterOneAchived() {
		return quarterOneAchived;
	}
	public void setQuarterOneAchived(Integer quarterOneAchived) {
		this.quarterOneAchived = quarterOneAchived;
	}
	public Integer getQuarterTwoTarget() {
		return quarterTwoTarget;
	}
	public void setQuarterTwoTarget(Integer quarterTwoTarget) {
		this.quarterTwoTarget = quarterTwoTarget;
	}
	public Integer getQuarterTwoAchived() {
		return quarterTwoAchived;
	}
	public void setQuarterTwoAchived(Integer quarterTwoAchived) {
		this.quarterTwoAchived = quarterTwoAchived;
	}
	public Integer getQuarterThreeTarget() {
		return quarterThreeTarget;
	}
	public void setQuarterThreeTarget(Integer quarterThreeTarget) {
		this.quarterThreeTarget = quarterThreeTarget;
	}
	public Integer getQuarterThreeAchived() {
		return quarterThreeAchived;
	}
	public void setQuarterThreeAchived(Integer quarterThreeAchived) {
		this.quarterThreeAchived = quarterThreeAchived;
	}
	public Integer getQuarterFourTarget() {
		return quarterFourTarget;
	}
	public void setQuarterFourTarget(Integer quarterFourTarget) {
		this.quarterFourTarget = quarterFourTarget;
	}
	public Integer getQuarterFourAchived() {
		return quarterFourAchived;
	}
	public void setQuarterFourAchived(Integer quarterFourAchived) {
		this.quarterFourAchived = quarterFourAchived;
	}
	public Integer getOverallAchived() {
		return overallAchived;
	}
	public void setOverallAchived(Integer overallAchived) {
		this.overallAchived = overallAchived;
	}
	public Double getPercentageAchived() {
		return percentageAchived;
	}
	public void setPercentageAchived(Double percentageAchived) {
		this.percentageAchived = percentageAchived;
	}
	public Integer getVarinaceAchived() {
		return varinaceAchived;
	}
	public void setVarinaceAchived(Integer varinaceAchived) {
		this.varinaceAchived = varinaceAchived;
	}
	
	/* Headers for Extract */
	public List<String> getHeaders(){
		List<String> headers = new ArrayList<>();
		headers.add("Number");
		headers.add("Financial Year");
		headers.add("NSDP OUTCOMES");
		headers.add("NSDP SUB-OUTCOMES");
		headers.add("OUTPUT INDICATORS");
		
		headers.add("APP/SLA Targets #FINANCIAL_YEARS#");
		
		headers.add("TOTAL Q1 TARGET");
		headers.add("TOTAL ACHIEVED AT Q1");
		
		headers.add("TOTAL Q2 TARGET");
		headers.add("TOTAL ACHIEVED AT Q2");
		
		headers.add("TOTAL Q3 TARGET");
		headers.add("TOTAL ACHIEVED AT Q3");
		
		headers.add("TOTAL Q4 TARGET");
		headers.add("TOTAL ACHIEVED AT Q4");
		
		headers.add("OVERALL ACHIEVED AT Q4");
		
		headers.add("PERCENTAGES %");
		headers.add("VARIANCE");
		
		return headers;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}