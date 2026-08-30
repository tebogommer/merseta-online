package haj.com.bean;

import java.math.BigDecimal;

public class AnnexureABean {
	
	private String description;
	private Integer maxPossibleLearners;
	private Integer coFundingLearnersAwarded;
	private Integer disabledTotalLearners;
	private BigDecimal disabledGrantAmount;
	private BigDecimal totalAwardAmount;
	
	
	
	public AnnexureABean() {
		super();
	}

	public AnnexureABean(String description, Integer maxPossibleLearners, Integer coFundingLearnersAwarded, Integer disabledTotalLearners, BigDecimal disabledGrantAmount, BigDecimal totalAwardAmount) {
		super();
		this.description = description;
		this.maxPossibleLearners = maxPossibleLearners;
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
		this.disabledTotalLearners = disabledTotalLearners;
		this.disabledGrantAmount = disabledGrantAmount;
		this.totalAwardAmount = totalAwardAmount;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMaxPossibleLearners() {
		return maxPossibleLearners;
	}
	public void setMaxPossibleLearners(Integer maxPossibleLearners) {
		this.maxPossibleLearners = maxPossibleLearners;
	}
	public Integer getCoFundingLearnersAwarded() {
		return coFundingLearnersAwarded;
	}
	public void setCoFundingLearnersAwarded(Integer coFundingLearnersAwarded) {
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
	}
	public Integer getDisabledTotalLearners() {
		return disabledTotalLearners;
	}
	public void setDisabledTotalLearners(Integer disabledTotalLearners) {
		this.disabledTotalLearners = disabledTotalLearners;
	}
	public BigDecimal getDisabledGrantAmount() {
		return disabledGrantAmount;
	}
	public void setDisabledGrantAmount(BigDecimal disabledGrantAmount) {
		this.disabledGrantAmount = disabledGrantAmount;
	}
	public BigDecimal getTotalAwardAmount() {
		return totalAwardAmount;
	}
	public void setTotalAwardAmount(BigDecimal totalAwardAmount) {
		this.totalAwardAmount = totalAwardAmount;
	}
}
