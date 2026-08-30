package haj.com.bean;

import java.util.Date;

public class UnitStandardBean
{
	private String saqaUnitStandTitle;
	private String saqaUnitStandCode;
	private Date unitStardsCommencementDate;
	private Date unitStardsCompletionDate;
	
	
	public UnitStandardBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


	public UnitStandardBean(String saqaUnitStandTitle, String saqaUnitStandCode, Date unitStardsCommencementDate,
			Date unitStardsCompletionDate) {
		super();
		this.saqaUnitStandTitle = saqaUnitStandTitle;
		this.saqaUnitStandCode = saqaUnitStandCode;
		this.unitStardsCommencementDate = unitStardsCommencementDate;
		this.unitStardsCompletionDate = unitStardsCompletionDate;
	}




	public String getSaqaUnitStandTitle() {
		return saqaUnitStandTitle;
	}




	public void setSaqaUnitStandTitle(String saqaUnitStandTitle) {
		this.saqaUnitStandTitle = saqaUnitStandTitle;
	}




	public String getSaqaUnitStandCode() {
		return saqaUnitStandCode;
	}




	public void setSaqaUnitStandCode(String saqaUnitStandCode) {
		this.saqaUnitStandCode = saqaUnitStandCode;
	}




	public Date getUnitStardsCommencementDate() {
		return unitStardsCommencementDate;
	}




	public void setUnitStardsCommencementDate(Date unitStardsCommencementDate) {
		this.unitStardsCommencementDate = unitStardsCommencementDate;
	}




	public Date getUnitStardsCompletionDate() {
		return unitStardsCompletionDate;
	}




	public void setUnitStardsCompletionDate(Date unitStardsCompletionDate) {
		this.unitStardsCompletionDate = unitStardsCompletionDate;
	}
	
	


	
	

}
