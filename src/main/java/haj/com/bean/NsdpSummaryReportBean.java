package haj.com.bean;

import java.io.Serializable;

/**
 * The Class NsdpSummaryReportBean.
 */
public class NsdpSummaryReportBean implements Serializable {

	private Integer overallAchived;
	private Double overallPercentage;
	private Integer overallVariance;
	private Boolean targetsMatch;

	/* getters and setters */
	public Integer getOverallAchived() {
		return overallAchived;
	}

	public void setOverallAchived(Integer overallAchived) {
		this.overallAchived = overallAchived;
	}

	public Double getOverallPercentage() {
		return overallPercentage;
	}

	public void setOverallPercentage(Double overallPercentage) {
		this.overallPercentage = overallPercentage;
	}

	public Integer getOverallVariance() {
		return overallVariance;
	}

	public void setOverallVariance(Integer overallVariance) {
		this.overallVariance = overallVariance;
	}

	public Boolean getTargetsMatch() {
		return targetsMatch;
	}

	public void setTargetsMatch(Boolean targetsMatch) {
		this.targetsMatch = targetsMatch;
	}

}