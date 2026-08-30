package haj.com.bean;

public class AnnexureCBean {
	
	// Attribute added for Jira #2201
	private String qualificationTitle;

	// Attribute added for Jira #271
	private String ofoCodes;
	
	private String interventionType;
	private String interventionTitle;
	private String qualificationCode;
	private Integer maxPossibleLearners;
	private Integer coFundingLearnersAwarded;

	public AnnexureCBean() {
		super();
	}
	
	// Constructor added for Jira #2201
	public AnnexureCBean(String qualificationTitle, String ofoCodes, String interventionType, String interventionTitle, String qualificationCode, Integer maxPossibleLearners, Integer coFundingLearnersAwarded) {
		super();
		this.qualificationTitle = qualificationTitle;
		this.ofoCodes = ofoCodes;
		this.interventionType = interventionType;
		this.interventionTitle = interventionTitle;
		this.qualificationCode = qualificationCode;
		this.maxPossibleLearners = maxPossibleLearners;
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
	}

	// Constructor added for Jira #271
	public AnnexureCBean(String ofoCodes, String interventionType, String interventionTitle, String qualificationCode, Integer maxPossibleLearners, Integer coFundingLearnersAwarded) {
		super();
		this.ofoCodes = ofoCodes;
		this.interventionType = interventionType;
		this.interventionTitle = interventionTitle;
		this.qualificationCode = qualificationCode;
		this.maxPossibleLearners = maxPossibleLearners;
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
	}

	public AnnexureCBean(String interventionType, String interventionTitle, String qualificationCode, Integer maxPossibleLearners, Integer coFundingLearnersAwarded) {
		super();
		this.interventionType = interventionType;
		this.interventionTitle = interventionTitle;
		this.qualificationCode = qualificationCode;
		this.maxPossibleLearners = maxPossibleLearners;
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
	}
	

	// Getter added for Jira #2201
	public String getQualificationTitle() {
	    return qualificationTitle;
	}

	// Setter added for Jira #2201
	public void setQualificationTitle(String qualificationTitle) {
	    this.qualificationTitle = qualificationTitle;
	}

	// Getter added for Jira #271
	public String getOfoCodes() {
		return ofoCodes;
	}
	// Setter added for Jira #271
	public void setOfoCodes(String ofoCodes) {
		this.ofoCodes = ofoCodes;
	}
	public String getInterventionType() {
		return interventionType;
	}
	public void setInterventionType(String interventionType) {
		this.interventionType = interventionType;
	}
	public String getInterventionTitle() {
		return interventionTitle;
	}
	public void setInterventionTitle(String interventionTitle) {
		this.interventionTitle = interventionTitle;
	}
	public String getQualificationCode() {
		return qualificationCode;
	}
	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
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
}
