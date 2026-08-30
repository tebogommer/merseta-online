package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.lookup.Etqa;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * DgVerification.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "dg_verification")
@AuditTable(value = "dg_verification_history")
@Audited
public class DgVerification implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of DgVerification. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** Create Date of Object. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_captured", length = 19)
	private Date dateCaptured;

	/** The Wsp */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "new_application", nullable = true)
	private YesNoLookup newApplication;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "extension_of_scope", nullable = true)
	private YesNoLookup extensionOfScope;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "previously_approved", nullable = true)
	private YesNoLookup previouslyApproved;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "approved_by_seta", nullable = true)
	private Etqa approvedBySeta;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "as_per_skills_area", nullable = true)
	private YesNoLookup asPerSkillsArea;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "relevant_learner_ratio", nullable = true)
	private YesNoLookup relevantLearnerRatio;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "relevant_core_work", nullable = true)
	private YesNoLookup relevantCoreWork;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "access_material_consumable", nullable = true)
	private YesNoLookup accessMaterialConsumable;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "access_cover", nullable = true)
	private YesNoLookup accessCover;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "effective_record", nullable = true)
	private YesNoLookup effectiveRecord;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "suitable_qualified_mentor", nullable = true)
	private YesNoLookup suitableQualifiedMentor;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workplace_layout", nullable = true)
	private YesNoLookup workplaceLayout;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "health_safety_officer", nullable = true)
	private YesNoLookup healthSafetyOfficer;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pretective_wear", nullable = true)
	private YesNoLookup pretectiveWear;

	@Column(name = "ensure_explanation", columnDefinition = "LONGTEXT")
	private String ensureExplanation;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Enumerated
	@Column(name = "discretional_withdrawal_appeal_enum")
	private DiscretionalWithdrawalAppealEnum discretionalWithdrawalAppealEnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;

	@Column(name = "requires_site_visit", nullable = true)
	private Boolean requiresSiteVisit;

	/** Client liaison officer section start (liaison) */

	/**
	 * The DG application recommended
	 */
	@Column(name = "liaison_application_recommended", nullable = true)
	private Boolean liaisonApplicationRecommended;
 
	/**
	 * The DG application not recommended
	 */
	@Column(name = "liaison_application_not_recommended", nullable = true)
	private Boolean liaisonApplicationNotRecommended;

	/**
	 * The DG application not recommended detail
	 */
	@Column(name = "liaison_application_not_recommended_detail", length = 500)
	private String liaisonApplicationNotRecommendedDetail;

	/** Client liaison officer section end */

	/** Client relationship manager section start (rm) */

	/**
	 * The DG application recommended
	 */
	@Column(name = "rm_application_recommended", nullable = true)
	private Boolean rmApplicationRecommended;

	/**
	 * The DG application not recommended
	 */
	@Column(name = "rm_application_not_recommended", nullable = true)
	private Boolean rmApplicationNotRecommended;

	/**
	 * The Reason for non-DG application recommendation
	 */
	@Column(name = "rm_non_dg_application_not_recommended_detail", length = 500)
	private String rmNonDgApplicationNotRecommendedDetail;

	/**
	 * The Reason for overiding clo non-DG application recommendation
	 */

	@Column(name = "rm_overiding_non_dg_application_recommended_detail", length = 500)
	private String rmOveridingNonDgApplicationRecommendedDetail;

	/** Client relationship manager section end */

	@Transient
	private List<Signoff> signOffs;

	@Transient
	private List<Doc> docs;
	
	@Enumerated
	@Column(name = "clo_recommendation")
	private CloRecommendationEnum cloRecommendation;
	
	/* Indicator who was the CLO */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clo_user_id", nullable = true)
	private Users cloUser;
	
	@Enumerated
	@Column(name = "crm_decision")
	private CloRecommendationEnum crmDecision;

	/* Indicator who was the CRM */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crm_user_id", nullable = true)
	private Users crmUser;
	
	/** Date CRM approved / Rejected DG. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crm_approval_rejection_date", length = 19)
	private Date crmApprovalRejectionDate;

	@Column(name = "clo_alteration")
	private Boolean cloAlteration;
	
	@Column(name = "with_sdf", nullable = true)
	private Boolean withSdf;
	
	/**
	 * Indicator is the system auto approved / Rejected the application
	 */
	@Column(name = "system_approval_rejection", nullable = true)
	private Boolean systemApprovalRejection;
	
	@Column(name = "application_appealed", nullable = true)
	private Boolean applicationAppealed;
	
	/** Date SDF appealed. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_appealed", length = 19, nullable = true)
	private Date dateAppealed;
	
	/** Date CRM approved / Rejected DG. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crm_appealed_date_approved", length = 19, nullable = true)
	private Date crmAppealedDateApproved;
	
	@Column(name = "final_response", columnDefinition = "LONGTEXT")
	private String finalResponse;
	
	/* User who completed final response */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "final_response_id", nullable = true)
	private Users finalResponseUser;
	
	@Column(name = "ready_for_allocation")
	private Boolean readyForAllocation;
	
	@Transient
	private String dgVerificationRejectionReasons;
	
	@Transient
	private String cloRejectionReasons;
	
	@Transient
	private String crmRejectionReasons;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DgVerification other = (DgVerification) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Boolean getLiaisonApplicationRecommended() {
		return liaisonApplicationRecommended;
	}

	public void setLiaisonApplicationRecommended(Boolean liaisonApplicationRecommended) {
		this.liaisonApplicationRecommended = liaisonApplicationRecommended;
	}

	public Boolean getLiaisonApplicationNotRecommended() {
		return liaisonApplicationNotRecommended;
	}

	public void setLiaisonApplicationNotRecommended(Boolean liaisonApplicationNotRecommended) {
		this.liaisonApplicationNotRecommended = liaisonApplicationNotRecommended;
	}

	public String getLiaisonApplicationNotRecommendedDetail() {
		return liaisonApplicationNotRecommendedDetail;
	}

	public void setLiaisonApplicationNotRecommendedDetail(String liaisonApplicationNotRecommendedDetail) {
		this.liaisonApplicationNotRecommendedDetail = liaisonApplicationNotRecommendedDetail;
	}

	public Boolean getRmApplicationRecommended() {
		return rmApplicationRecommended;
	}

	public void setRmApplicationRecommended(Boolean rmApplicationRecommended) {
		this.rmApplicationRecommended = rmApplicationRecommended;
	}

	public Boolean getRmApplicationNotRecommended() {
		return rmApplicationNotRecommended;
	}

	public void setRmApplicationNotRecommended(Boolean rmApplicationNotRecommended) {
		this.rmApplicationNotRecommended = rmApplicationNotRecommended;
	}

	public String getRmNonDgApplicationNotRecommendedDetail() {
		return rmNonDgApplicationNotRecommendedDetail;
	}

	public void setRmNonDgApplicationNotRecommendedDetail(String rmNonDgApplicationNotRecommendedDetail) {
		this.rmNonDgApplicationNotRecommendedDetail = rmNonDgApplicationNotRecommendedDetail;
	}

	public String getRmOveridingNonDgApplicationRecommendedDetail() {
		return rmOveridingNonDgApplicationRecommendedDetail;
	}

	public void setRmOveridingNonDgApplicationRecommendedDetail(String rmOveridingNonDgApplicationRecommendedDetail) {
		this.rmOveridingNonDgApplicationRecommendedDetail = rmOveridingNonDgApplicationRecommendedDetail;
	}

	public Date getDateCaptured() {
		return dateCaptured;
	}

	public void setDateCaptured(Date dateCaptured) {
		this.dateCaptured = dateCaptured;
	}

	public YesNoLookup getNewApplication() {
		return newApplication;
	}

	public void setNewApplication(YesNoLookup newApplication) {
		this.newApplication = newApplication;
	}

	public YesNoLookup getExtensionOfScope() {
		return extensionOfScope;
	}

	public void setExtensionOfScope(YesNoLookup extensionOfScope) {
		this.extensionOfScope = extensionOfScope;
	}

	public List<Signoff> getSignOffs() {
		return signOffs;
	}

	public void setSignOffs(List<Signoff> signOffs) {
		this.signOffs = signOffs;
	}

	public YesNoLookup getPreviouslyApproved() {
		return previouslyApproved;
	}

	public void setPreviouslyApproved(YesNoLookup previouslyApproved) {
		this.previouslyApproved = previouslyApproved;
	}

	public Etqa getApprovedBySeta() {
		return approvedBySeta;
	}

	public void setApprovedBySeta(Etqa approvedBySeta) {
		this.approvedBySeta = approvedBySeta;
	}

	public YesNoLookup getAsPerSkillsArea() {
		return asPerSkillsArea;
	}

	public void setAsPerSkillsArea(YesNoLookup asPerSkillsArea) {
		this.asPerSkillsArea = asPerSkillsArea;
	}

	public YesNoLookup getRelevantLearnerRatio() {
		return relevantLearnerRatio;
	}

	public void setRelevantLearnerRatio(YesNoLookup relevantLearnerRatio) {
		this.relevantLearnerRatio = relevantLearnerRatio;
	}

	public YesNoLookup getRelevantCoreWork() {
		return relevantCoreWork;
	}

	public void setRelevantCoreWork(YesNoLookup relevantCoreWork) {
		this.relevantCoreWork = relevantCoreWork;
	}

	public YesNoLookup getAccessMaterialConsumable() {
		return accessMaterialConsumable;
	}

	public void setAccessMaterialConsumable(YesNoLookup accessMaterialConsumable) {
		this.accessMaterialConsumable = accessMaterialConsumable;
	}

	public YesNoLookup getAccessCover() {
		return accessCover;
	}

	public void setAccessCover(YesNoLookup accessCover) {
		this.accessCover = accessCover;
	}

	public YesNoLookup getEffectiveRecord() {
		return effectiveRecord;
	}

	public void setEffectiveRecord(YesNoLookup effectiveRecord) {
		this.effectiveRecord = effectiveRecord;
	}

	public YesNoLookup getSuitableQualifiedMentor() {
		return suitableQualifiedMentor;
	}

	public void setSuitableQualifiedMentor(YesNoLookup suitableQualifiedMentor) {
		this.suitableQualifiedMentor = suitableQualifiedMentor;
	}

	public YesNoLookup getWorkplaceLayout() {
		return workplaceLayout;
	}

	public void setWorkplaceLayout(YesNoLookup workplaceLayout) {
		this.workplaceLayout = workplaceLayout;
	}

	public YesNoLookup getHealthSafetyOfficer() {
		return healthSafetyOfficer;
	}

	public void setHealthSafetyOfficer(YesNoLookup healthSafetyOfficer) {
		this.healthSafetyOfficer = healthSafetyOfficer;
	}

	public YesNoLookup getPretectiveWear() {
		return pretectiveWear;
	}

	public void setPretectiveWear(YesNoLookup pretectiveWear) {
		this.pretectiveWear = pretectiveWear;
	}

	public String getEnsureExplanation() {
		return ensureExplanation;
	}

	public void setEnsureExplanation(String ensureExplanation) {
		this.ensureExplanation = ensureExplanation;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Boolean getRequiresSiteVisit() {
		return requiresSiteVisit;
	}

	public void setRequiresSiteVisit(Boolean requiresSiteVisit) {
		this.requiresSiteVisit = requiresSiteVisit;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public DiscretionalWithdrawalAppealEnum getDiscretionalWithdrawalAppealEnum() {
		return discretionalWithdrawalAppealEnum;
	}

	public void setDiscretionalWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum discretionalWithdrawalAppealEnum) {
		this.discretionalWithdrawalAppealEnum = discretionalWithdrawalAppealEnum;
	}

	public CloRecommendationEnum getCloRecommendation() {
		return cloRecommendation;
	}

	public void setCloRecommendation(CloRecommendationEnum cloRecommendation) {
		this.cloRecommendation = cloRecommendation;
	}

	public Date getCrmApprovalRejectionDate() {
		return crmApprovalRejectionDate;
	}

	public void setCrmApprovalRejectionDate(Date crmApprovalRejectionDate) {
		this.crmApprovalRejectionDate = crmApprovalRejectionDate;
	}

	public Boolean getCloAlteration() {
		return cloAlteration;
	}

	public void setCloAlteration(Boolean cloAlteration) {
		this.cloAlteration = cloAlteration;
	}

	public CloRecommendationEnum getCrmDecision() {
		return crmDecision;
	}

	public void setCrmDecision(CloRecommendationEnum crmDecision) {
		this.crmDecision = crmDecision;
	}

	public Boolean getApplicationAppealed() {
		return applicationAppealed;
	}

	public void setApplicationAppealed(Boolean applicationAppealed) {
		this.applicationAppealed = applicationAppealed;
	}

	public String getFinalResponse() {
		return finalResponse;
	}

	public void setFinalResponse(String finalResponse) {
		this.finalResponse = finalResponse;
	}

	public Date getDateAppealed() {
		return dateAppealed;
	}

	public void setDateAppealed(Date dateAppealed) {
		this.dateAppealed = dateAppealed;
	}

	public Date getCrmAppealedDateApproved() {
		return crmAppealedDateApproved;
	}

	public void setCrmAppealedDateApproved(Date crmAppealedDateApproved) {
		this.crmAppealedDateApproved = crmAppealedDateApproved;
	}

	public Boolean getWithSdf() {
		return withSdf;
	}

	public void setWithSdf(Boolean withSdf) {
		this.withSdf = withSdf;
	}

	public Boolean getReadyForAllocation() {
		return readyForAllocation;
	}

	public void setReadyForAllocation(Boolean readyForAllocation) {
		this.readyForAllocation = readyForAllocation;
	}

	public Boolean getSystemApprovalRejection() {
		return systemApprovalRejection;
	}

	public void setSystemApprovalRejection(Boolean systemApprovalRejection) {
		this.systemApprovalRejection = systemApprovalRejection;
	}

	public String getDgVerificationRejectionReasons() {
		return dgVerificationRejectionReasons;
	}

	public void setDgVerificationRejectionReasons(String dgVerificationRejectionReasons) {
		this.dgVerificationRejectionReasons = dgVerificationRejectionReasons;
	}

	public Users getCloUser() {
		return cloUser;
	}

	public void setCloUser(Users cloUser) {
		this.cloUser = cloUser;
	}

	public Users getCrmUser() {
		return crmUser;
	}

	public void setCrmUser(Users crmUser) {
		this.crmUser = crmUser;
	}

	public Users getFinalResponseUser() {
		return finalResponseUser;
	}

	public void setFinalResponseUser(Users finalResponseUser) {
		this.finalResponseUser = finalResponseUser;
	}

	public String getCloRejectionReasons() {
		return cloRejectionReasons;
	}

	public void setCloRejectionReasons(String cloRejectionReasons) {
		this.cloRejectionReasons = cloRejectionReasons;
	}

	public String getCrmRejectionReasons() {
		return crmRejectionReasons;
	}

	public void setCrmRejectionReasons(String crmRejectionReasons) {
		this.crmRejectionReasons = crmRejectionReasons;
	}
}
