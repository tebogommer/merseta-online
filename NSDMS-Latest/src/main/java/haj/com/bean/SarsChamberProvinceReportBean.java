
package haj.com.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class SarsChamberProvinceReportBean.
 */
public class SarsChamberProvinceReportBean {

	private String description;
	
	private List<SarsProvinceCountBean> resultsList;
	
	private Boolean totalEntry;

	public SarsChamberProvinceReportBean() {
		super();
	}
	
	public SarsChamberProvinceReportBean(String description) {
		super();
		this.description = description;
		this.resultsList = new ArrayList<>();
		this.totalEntry = false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SarsProvinceCountBean> getResultsList() {
		return resultsList;
	}

	public void setResultsList(List<SarsProvinceCountBean> resultsList) {
		this.resultsList = resultsList;
	}

	public Boolean getTotalEntry() {
		return totalEntry;
	}

	public void setTotalEntry(Boolean totalEntry) {
		this.totalEntry = totalEntry;
	}

}