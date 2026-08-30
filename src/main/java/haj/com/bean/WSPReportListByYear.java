package haj.com.bean;

import java.util.List;

import haj.com.framework.IDataEntity;

public class WSPReportListByYear implements IDataEntity {

	
	private Integer finYear;
	private List<WSPReport> wspReportList;

	public WSPReportListByYear() {
		super();
	}

	/** getters and Setters */
	public Integer getFinYear() {
		return finYear;
	}

	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	public List<WSPReport> getWspReportList() {
		return wspReportList;
	}

	public void setWspReportList(List<WSPReport> wspReportList) {
		this.wspReportList = wspReportList;
	}

}
