
package haj.com.bean;

import java.io.Serializable;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressLookup.
 */
public class SchemeYearBean implements Serializable {
	private Integer schemeYear;
	private Date startDate;
	private Date endDate;
	
	public Integer getSchemeYear() {
		return schemeYear;
	}
	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "SchemeYearBean [schemeYear=" + schemeYear + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	

	
}