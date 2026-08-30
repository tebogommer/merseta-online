package haj.com.bean;

import java.io.Serializable;
import java.util.Date;

public class SarsLevyStandardDeviationMonthBean implements Serializable {
	
	private Date fromDate;
	private Date toDate;
	private String reportDisplay;
	
	public SarsLevyStandardDeviationMonthBean() {
		super();
	}
	
	public SarsLevyStandardDeviationMonthBean(Date fromDate, Date toDate, String reportDisplay) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reportDisplay = reportDisplay;
	}
	
	/* getters and setters */
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getReportDisplay() {
		return reportDisplay;
	}
	public void setReportDisplay(String reportDisplay) {
		this.reportDisplay = reportDisplay;
	}
	
}
