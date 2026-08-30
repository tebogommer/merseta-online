package haj.com.bean;

import java.io.Serializable;

public class StatusReportBean implements Serializable {

	private String status;
	private Integer count;
	
	
	
	public StatusReportBean() {
		super();
	}
	public StatusReportBean(String status, Integer count) {
		super();
		this.status = status;
		this.count = count;
	}
	/** Getters and setters */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
