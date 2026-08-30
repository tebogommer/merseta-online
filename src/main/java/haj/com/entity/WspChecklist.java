package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;



import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * WspChecklist.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_checklist")
public class WspChecklist implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** The extension granted. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "extension_granted", nullable = true)
	private YesNoLookup extensionGranted;

	/** The wspy 18 submitted. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_y18_submitted", nullable = true)
	private YesNoLookup wspy18Submitted;

	/** The banking details checked. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "banking_details_checked", nullable = true)
	private YesNoLookup bankingDetailsChecked;

	/** The other SDF signed. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "other_sdf_signed", nullable = true)
	private YesNoLookup otherSDFSigned;

	/** The atr submitted. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "atr_submitted", nullable = true)
	private YesNoLookup atrSubmitted;

	/** The atr implemented percent. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "atr_implemented_percent", nullable = true)
	private YesNoLookup atrImplementedPercent;

	/** The pivitol plan submitted. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pivitol_plan_submitted", nullable = true)
	private YesNoLookup pivitolPlanSubmitted;

	@Column(name = "grant_deviated")
	private Boolean grantDeviated;
	
	/** The signed recognition agreement. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "signed_recognition_agreement", nullable = true)
	private YesNoLookup signedRecognitionAgreement;

	/** The sign off complete. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sign_off_complete", nullable = true)
	private YesNoLookup signOffComplete;

	/** The non nqf motivation. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "non_nqf_Motivation", nullable = true)
	private YesNoLookup nonNqfMotivation;

	/** The deviation motivation. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "deviation_motivation", nullable = true)
	private YesNoLookup deviationMotivation;

	/** The minutes uplaoded. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "minutes_uplaoded", nullable = true)
	private YesNoLookup minutesUplaoded;
	
	/** The majority union. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "majority_union_id", nullable = true)
	private OrganisedLabourUnion majorityUnion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workplace_skills_plan_submitted", nullable = true)
	private YesNoLookup workplaceSkillsPlanSubmitted;

	@Column(name = "percentage_calculated", nullable = true)
	private BigDecimal percentageCalculated;
	
	/** The training_deviation_motivation_approved. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_deviation_motivation_approved", nullable = true)
	private YesNoLookup trainingDeviationMotivationApproved;
	
	/** The non_nqf_training_motivation_approved. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "non_nqf_training_motivation_approved", nullable = true)
	private YesNoLookup nonNqfTrainingMotivationApproved;
	
	/** The training_committee_minutes_approved. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_committee_minutes_approved", nullable = true)
	private YesNoLookup trainingCommitteeMinutesApproved;

	/** The trade_part_of_training_committee. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "trade_part_of_training_committee", nullable = true)
	private YesNoLookup tradePartOfTrainingCommittee;
	
	/** The employer_representative_part_training_committee. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "employer_representative_part_training_committee", nullable = true)
	private YesNoLookup employerRepresentativePartTrainingCommittee;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WspChecklist other = (WspChecklist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the extension granted.
	 *
	 * @return the extension granted
	 */
	public YesNoLookup getExtensionGranted() {
		return extensionGranted;
	}

	/**
	 * Sets the extension granted.
	 *
	 * @param extensionGranted the new extension granted
	 */
	public void setExtensionGranted(YesNoLookup extensionGranted) {
		this.extensionGranted = extensionGranted;
	}

	/**
	 * Gets the wspy 18 submitted.
	 *
	 * @return the wspy 18 submitted
	 */
	public YesNoLookup getWspy18Submitted() {
		return wspy18Submitted;
	}

	/**
	 * Sets the wspy 18 submitted.
	 *
	 * @param wspy18Submitted the new wspy 18 submitted
	 */
	public void setWspy18Submitted(YesNoLookup wspy18Submitted) {
		this.wspy18Submitted = wspy18Submitted;
	}

	/**
	 * Gets the banking details checked.
	 *
	 * @return the banking details checked
	 */
	public YesNoLookup getBankingDetailsChecked() {
		return bankingDetailsChecked;
	}

	/**
	 * Sets the banking details checked.
	 *
	 * @param bankingDetailsChecked the new banking details checked
	 */
	public void setBankingDetailsChecked(YesNoLookup bankingDetailsChecked) {
		this.bankingDetailsChecked = bankingDetailsChecked;
	}

	/**
	 * Gets the other SDF signed.
	 *
	 * @return the other SDF signed
	 */
	public YesNoLookup getOtherSDFSigned() {
		return otherSDFSigned;
	}

	/**
	 * Sets the other SDF signed.
	 *
	 * @param otherSDFSigned the new other SDF signed
	 */
	public void setOtherSDFSigned(YesNoLookup otherSDFSigned) {
		this.otherSDFSigned = otherSDFSigned;
	}

	/**
	 * Gets the atr submitted.
	 *
	 * @return the atr submitted
	 */
	public YesNoLookup getAtrSubmitted() {
		return atrSubmitted;
	}

	/**
	 * Sets the atr submitted.
	 *
	 * @param atrSubmitted the new atr submitted
	 */
	public void setAtrSubmitted(YesNoLookup atrSubmitted) {
		this.atrSubmitted = atrSubmitted;
	}

	/**
	 * Gets the atr implemented percent.
	 *
	 * @return the atr implemented percent
	 */
	public YesNoLookup getAtrImplementedPercent() {
		return atrImplementedPercent;
	}

	/**
	 * Sets the atr implemented percent.
	 *
	 * @param atrImplementedPercent the new atr implemented percent
	 */
	public void setAtrImplementedPercent(YesNoLookup atrImplementedPercent) {
		this.atrImplementedPercent = atrImplementedPercent;
	}

	/**
	 * Gets the pivitol plan submitted.
	 *
	 * @return the pivitol plan submitted
	 */
	public YesNoLookup getPivitolPlanSubmitted() {
		return pivitolPlanSubmitted;
	}

	/**
	 * Sets the pivitol plan submitted.
	 *
	 * @param pivitolPlanSubmitted the new pivitol plan submitted
	 */
	public void setPivitolPlanSubmitted(YesNoLookup pivitolPlanSubmitted) {
		this.pivitolPlanSubmitted = pivitolPlanSubmitted;
	}

	/**
	 * Gets the signed recognition agreement.
	 *
	 * @return the signed recognition agreement
	 */
	public YesNoLookup getSignedRecognitionAgreement() {
		return signedRecognitionAgreement;
	}

	/**
	 * Sets the signed recognition agreement.
	 *
	 * @param signedRecognitionAgreement the new signed recognition agreement
	 */
	public void setSignedRecognitionAgreement(YesNoLookup signedRecognitionAgreement) {
		this.signedRecognitionAgreement = signedRecognitionAgreement;
	}

	/**
	 * Gets the sign off complete.
	 *
	 * @return the sign off complete
	 */
	public YesNoLookup getSignOffComplete() {
		return signOffComplete;
	}

	/**
	 * Sets the sign off complete.
	 *
	 * @param signOffComplete the new sign off complete
	 */
	public void setSignOffComplete(YesNoLookup signOffComplete) {
		this.signOffComplete = signOffComplete;
	}

	/**
	 * Gets the non nqf motivation.
	 *
	 * @return the non nqf motivation
	 */
	public YesNoLookup getNonNqfMotivation() {
		return nonNqfMotivation;
	}

	/**
	 * Sets the non nqf motivation.
	 *
	 * @param nonNqfMotivation the new non nqf motivation
	 */
	public void setNonNqfMotivation(YesNoLookup nonNqfMotivation) {
		this.nonNqfMotivation = nonNqfMotivation;
	}

	/**
	 * Gets the deviation motivation.
	 *
	 * @return the deviation motivation
	 */
	public YesNoLookup getDeviationMotivation() {
		return deviationMotivation;
	}

	/**
	 * Sets the deviation motivation.
	 *
	 * @param deviationMotivation the new deviation motivation
	 */
	public void setDeviationMotivation(YesNoLookup deviationMotivation) {
		this.deviationMotivation = deviationMotivation;
	}

	/**
	 * Gets the minutes uplaoded.
	 *
	 * @return the minutes uplaoded
	 */
	public YesNoLookup getMinutesUplaoded() {
		return minutesUplaoded;
	}

	/**
	 * Sets the minutes uplaoded.
	 *
	 * @param minutesUplaoded the new minutes uplaoded
	 */
	public void setMinutesUplaoded(YesNoLookup minutesUplaoded) {
		this.minutesUplaoded = minutesUplaoded;
	}

	public OrganisedLabourUnion getMajorityUnion() {
		return majorityUnion;
	}

	public void setMajorityUnion(OrganisedLabourUnion majorityUnion) {
		this.majorityUnion = majorityUnion;
	}

	public YesNoLookup getWorkplaceSkillsPlanSubmitted() {
		return workplaceSkillsPlanSubmitted;
	}

	public void setWorkplaceSkillsPlanSubmitted(YesNoLookup workplaceSkillsPlanSubmitted) {
		this.workplaceSkillsPlanSubmitted = workplaceSkillsPlanSubmitted;
	}


	public YesNoLookup getTrainingDeviationMotivationApproved() {
		return trainingDeviationMotivationApproved;
	}

	public void setTrainingDeviationMotivationApproved(YesNoLookup trainingDeviationMotivationApproved) {
		this.trainingDeviationMotivationApproved = trainingDeviationMotivationApproved;
	}

	public YesNoLookup getNonNqfTrainingMotivationApproved() {
		return nonNqfTrainingMotivationApproved;
	}

	public void setNonNqfTrainingMotivationApproved(YesNoLookup nonNqfTrainingMotivationApproved) {
		this.nonNqfTrainingMotivationApproved = nonNqfTrainingMotivationApproved;
	}

	public YesNoLookup getTrainingCommitteeMinutesApproved() {
		return trainingCommitteeMinutesApproved;
	}

	public void setTrainingCommitteeMinutesApproved(YesNoLookup trainingCommitteeMinutesApproved) {
		this.trainingCommitteeMinutesApproved = trainingCommitteeMinutesApproved;
	}

	public YesNoLookup getTradePartOfTrainingCommittee() {
		return tradePartOfTrainingCommittee;
	}

	public void setTradePartOfTrainingCommittee(YesNoLookup tradePartOfTrainingCommittee) {
		this.tradePartOfTrainingCommittee = tradePartOfTrainingCommittee;
	}

	public YesNoLookup getEmployerRepresentativePartTrainingCommittee() {
		return employerRepresentativePartTrainingCommittee;
	}

	public void setEmployerRepresentativePartTrainingCommittee(YesNoLookup employerRepresentativePartTrainingCommittee) {
		this.employerRepresentativePartTrainingCommittee = employerRepresentativePartTrainingCommittee;
	}

	public BigDecimal getPercentageCalculated() {
		return percentageCalculated;
	}

	public void setPercentageCalculated(BigDecimal percentageCalculated) {
		this.percentageCalculated = percentageCalculated;
	}

	public Boolean getGrantDeviated() {
		return grantDeviated;
	}

	public void setGrantDeviated(Boolean grantDeviated) {
		this.grantDeviated = grantDeviated;
	}
}
