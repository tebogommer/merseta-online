package haj.com.bean;

import java.io.Serializable;

import haj.com.annotations.TechFiniumColumn;


public class SarsLevyDeviation implements Serializable {

	@TechFiniumColumn(heading="SDL Number")
	private String sdlnumber;

	@TechFiniumColumn(heading = "Latest Levy")
	private Double latest_levy;

	@TechFiniumColumn(heading = "Average Levy")
	private Double average_levy;

	@TechFiniumColumn(heading = "Deviation Amount")
	private Double deviation_amount;

	@TechFiniumColumn(heading = "Deviation Percentage")
	private Double deviation_percentage;

	@TechFiniumColumn(heading = "Levy Status")
	private String levyStatus;

	public SarsLevyDeviation() {
	}



	public SarsLevyDeviation(String sdlnumber, Double latest_levy, Double average_levy, Double deviation_amount,
			Double deviation_percentage, String levyStatus) {
		super();
		this.sdlnumber = sdlnumber;
		this.latest_levy = latest_levy;
		this.average_levy = average_levy;
		this.deviation_amount = deviation_amount;
		this.deviation_percentage = deviation_percentage;
		this.levyStatus = levyStatus;
	}



	public String getSdlnumber() {
		return sdlnumber;
	}



	public void setSdlnumber(String sdlnumber) {
		this.sdlnumber = sdlnumber;
	}



	public Double getLatest_levy() {
		return latest_levy;
	}



	public void setLatest_levy(Double latest_levy) {
		this.latest_levy = latest_levy;
	}



	public Double getAverage_levy() {
		return average_levy;
	}



	public void setAverage_levy(Double average_levy) {
		this.average_levy = average_levy;
	}



	public Double getDeviation_amount() {
		return deviation_amount;
	}



	public void setDeviation_amount(Double deviation_amount) {
		this.deviation_amount = deviation_amount;
	}



	public Double getDeviation_percentage() {
		return deviation_percentage;
	}



	public void setDeviation_percentage(Double deviation_percentage) {
		this.deviation_percentage = deviation_percentage;
	}



	public String getLevyStatus() {
		return levyStatus;
	}



	public void setLevyStatus(String levyStatus) {
		this.levyStatus = levyStatus;
	}


}
