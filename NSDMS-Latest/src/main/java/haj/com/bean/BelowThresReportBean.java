
package haj.com.bean;

import java.util.Date;
import java.util.List;

import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class Codes.
 */
public class BelowThresReportBean {
	
	private Integer schemeYear;
	private String refNumber;
	private Date startFinDate;
	private Date startFinDatePlusMonth;
	private Date endFinDate;
	private Date endFinDatePlusMonth;
	private Integer filesFound;
	private Double minAmount;
	private Double maxAmount;
	private List<ResultsBean> resultsForSchemeYear;
	
	public BelowThresReportBean() {
		super();
	}

	public BelowThresReportBean(Integer schemeYear, String refNumber) {
		super();
		this.schemeYear = schemeYear;
		this.refNumber = refNumber;
	}

	public BelowThresReportBean(Integer schemeYear, Date startFinDate, Date endFinDate, Integer filesFound, Double minAmount, Double maxAmount) {
		super();
		this.schemeYear = schemeYear;
		this.startFinDate = startFinDate;
		this.endFinDate = endFinDate;
		this.filesFound = filesFound;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public List<ResultsBean> getResultsForSchemeYear() {
		return resultsForSchemeYear;
	}

	public void setResultsForSchemeYear(List<ResultsBean> resultsForSchemeYear) {
		this.resultsForSchemeYear = resultsForSchemeYear;
	}

	public Date getStartFinDate() {
		return startFinDate;
	}

	public void setStartFinDate(Date startFinDate) {
		this.startFinDate = startFinDate;
	}

	public Date getEndFinDate() {
		return endFinDate;
	}

	public void setEndFinDate(Date endFinDate) {
		this.endFinDate = endFinDate;
	}

	public Integer getFilesFound() {
		return filesFound;
	}

	public void setFilesFound(Integer filesFound) {
		this.filesFound = filesFound;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Date getStartFinDatePlusMonth() {
		if (this.getStartFinDate() != null) {
			return GenericUtility.getStartOfDay(GenericUtility.addMonthsToDate(this.getStartFinDate(), 1));
		}else {
			return null;
		}
	}

	public Date getEndFinDatePlusMonth() {
		if (this.getEndFinDate() != null) {
			return GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth((GenericUtility.addMonthsToDate(this.getEndFinDate(), 1))));
		}else {
			return null;
		}
	}
	
}