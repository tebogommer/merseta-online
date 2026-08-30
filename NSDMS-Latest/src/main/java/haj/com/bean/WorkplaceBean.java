package haj.com.bean;

import java.util.Date;

public class WorkplaceBean 
{
	private String name;
	private String detailes;
	private String from;
	private String to;
	
	public WorkplaceBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WorkplaceBean(String name, String detailes, String from, String to) {
		super();
		this.name = name;
		this.detailes = detailes;
		this.from = from;
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetailes() {
		return detailes;
	}

	public void setDetailes(String detailes) {
		this.detailes = detailes;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
