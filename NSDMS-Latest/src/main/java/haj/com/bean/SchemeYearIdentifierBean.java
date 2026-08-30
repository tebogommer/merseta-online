
package haj.com.bean;

import java.util.Date;
import java.util.List;

/**
 * The Class Codes.
 */
public class SchemeYearIdentifierBean {

    private Date startOfYear;
    private Date endOfYear;
    private Integer schemeYear;
    private List<Date> monthsToDate;
    
        
	public SchemeYearIdentifierBean() {
		super();
	}
	
	public Date getStartOfYear() {
		return startOfYear;
	}
	public void setStartOfYear(Date startOfYear) {
		this.startOfYear = startOfYear;
	}
	public Date getEndOfYear() {
		return endOfYear;
	}
	public void setEndOfYear(Date endOfYear) {
		this.endOfYear = endOfYear;
	}
	public Integer getSchemeYear() {
		return schemeYear;
	}
	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}
	public List<Date> getMonthsToDate() {
		return monthsToDate;
	}
	public void setMonthsToDate(List<Date> monthsToDate) {
		this.monthsToDate = monthsToDate;
	}
}
