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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.WorkplaceApprovalTypeEnum;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "work_place_approval")
public class WorkPlaceApproval implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_captured", length = 19)
	private Date dateCaptured;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sites_id", nullable = true)
	private Sites sites;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "mandatory_grant_id", nullable = true)
	private MandatoryGrant mandatoryGrant;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pretective_wear_sdf", nullable = true)
	private YesNoLookup pretectiveWearSdf;
	// ---------------------------------------------------------------------------------------------------------------------------//

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "tax_clearance_certificate", nullable = true)
	private YesNoLookup taxClearanceCertificate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "identified_committed_staff", nullable = true)
	private YesNoLookup identifiedCommittedStaff; 

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "identified_committed_staff_sdf", nullable = true)
	private YesNoLookup identifiedCommittedStaffSdf; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "declaration_from_employer", nullable = true)
	private YesNoLookup declarationFromEmployer;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "structured_implementation_plan_sdf", nullable = true)
	private YesNoLookup structuredImplementationPlanSdf; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "structured_implementation_plan", nullable = true)
	private YesNoLookup structuredImplementationPlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "suitable_qualified_mentors", nullable = true)
	private YesNoLookup suitableQualifiedMentors;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "letter_of_commitment", nullable = true)
	private YesNoLookup letterOfCommitment;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "declaration_indication_commitment", nullable = true)
	private YesNoLookup declarationIndicationCommitment;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "copy_of_self_evaluation", nullable = true)
	private YesNoLookup copyOfSelfEvaluation;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workplace_able_to_cover_scope", nullable = true)
	private YesNoLookup workplaceAbleToCoverScope;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "formal_agreement_aprroved_workpalces", nullable = true)
	private YesNoLookup formalAgreementAprrovedWorkpalces;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "mentor_artisan_ration_acceptable", nullable = true)
	private YesNoLookup mentorArtisanRationAcceptable; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "mentor_artisan_ration_acceptable_sdf", nullable = true)
	private YesNoLookup mentorArtisanRationAcceptableSdf;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "evidence_attached_of_previos", nullable = true)
	private YesNoLookup evidenceAttachedOfPrevios;

	// ---------------------------------------------------------------------------------------------------------------------------//

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "all_tools_available", nullable = true)
	private YesNoLookup allToolsAvailable; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "all_tools_available_sdf", nullable = true)
	private YesNoLookup allToolsAvailableSdf; 

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "access_to_material", nullable = true)
	private YesNoLookup accessToMaterial; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "accessToMaterial_sdf", nullable = true)
	private YesNoLookup accessToMaterialSdf;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ability_to_cover_entire_scope", nullable = true)
	private YesNoLookup abilityToCoverEntireScope;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "formal_agreement_with_other", nullable = true)
	private YesNoLookup formalAgreementWithOther; 

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "formal_agreement_with_other_sdf", nullable = true)
	private YesNoLookup formalAgreementWithOtherSdf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "layout_environment_safe", nullable = true)
	private YesNoLookup layoutEnvironmentSafe;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "layout_environment_safe_sdf", nullable = true)
	private YesNoLookup layoutEnvironmentSafeSdf;	

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "record_keeping_system", nullable = true)
	private YesNoLookup recordKeepingSystem; 

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "record_keeping_system_sdf", nullable = true)
	private YesNoLookup recordKeepingSystemSdf; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualified_mentors_for_trade", nullable = true)
	private YesNoLookup qualifiedMentorsForTrade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualified_mentors_for_trade_sdf", nullable = true)
	private YesNoLookup qualifiedMentorsForTradeSdf;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ohsa_mhsa_compliant", nullable = true)
	private YesNoLookup ohsaMhsaCompliant;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workplace_cover_trade_or_qualification", nullable = true)
	private YesNoLookup workplaceAbleToCoverTradeOrQualification; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workplace_cover_trade_or_qualification_sdf", nullable = true)
	private YesNoLookup workplaceAbleToCoverTradeOrQualificationSdf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "committed_ohsa_or_mhsa_compliant", nullable = true)
	private YesNoLookup committedOhsaOrMhsaCompliant; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "committed_ohsa_or_mhsa_compliant_sdf", nullable = true)
	private YesNoLookup committedOhsaOrMhsaCompliantSdf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "committedto_becompliant_relevant_legislation_applicable", nullable = true)
	private YesNoLookup committedtoBecompliantRelevantLegislationApplicable;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "committedto_becompliant_relevant_legislation_applicable_sdf", nullable = true)
	private YesNoLookup committedtoBecompliantRelevantLegislationApplicableSdf;

	// ---------------------------------------------------------------------------------------------------------------------------//

	@Column(name = "ensure_explanation", columnDefinition = "LONGTEXT")
	private String ensureExplanation;

	@Enumerated
	@Column(name = "approval_enum", columnDefinition = "LONGTEXT")
	private ApprovalEnum approvalEnum;
	
	@Enumerated
	@Column(name = "origional_approval_enum", columnDefinition = "LONGTEXT")
	private ApprovalEnum origionalApprovalEnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "review_date", length = 19)
	private Date reviewDate;
	
	/** The form user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_user_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Users reviewUser;
	
	@Enumerated
	@Column(name = "clo_recommendation")
	private CloRecommendationEnum cloRecommendation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	private Date endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;
	
	@Column(name = "wpa_appealed")
	private Boolean wpaAppealed;
	
	@Column(name = "date_for_visit_provided")
	private Boolean dateForVisitProvided;
	
	/**
	 * Indicator to see if its with the client company
	 */
	@Column(name = "with_client_company")
	private Boolean withClientCompany;
	
	@Column(name = "with_manager")
	private Boolean withManager;
	
	/**
	 * If the client company failed the first visit
	 * this will be true if the 5 days time limit
	 * to provide amendments is required 
	 */
	@Column(name = "amendments_required")
	private Boolean amendmentsRequired;
	
	@Column(name = "wpa_number")
	private String workPlaceApprovalNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_review_date", length = 19)
	private Date approvalReviewDate;
	
	@Column(name = "reject_message")
	private String rejectMessage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_employer_wa2_workplace_id", nullable = true)
	private LegacyEmployerWA2Workplace legacyEmployerWA2Workplace;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitstandard_id", nullable = true)
	private UnitStandards unitstandard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id", nullable = true)
	private SkillsSet skillsSet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id", nullable = true)
	private SkillsProgram skillsProgram;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learnership_id", nullable = true)
	private Learnership learnership;
	
	@Enumerated
	@Column(name = "workplace_approval_type_enum")
	private WorkplaceApprovalTypeEnum workplaceApprovalTypeEnum;
	
	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<Signoff> signOffs;
	
	@Transient
	private List<AppraisalChecklist> appraisalChecklist;
	
	@Transient
	private List<SkillsProgram> skillsProgramlist;
	
	@Transient
	private List<SkillsSet> skillsSetlist;
	
	@Transient
	private List<Learnership> learnershiplist;
	
	@Transient
	private List<UnitStandards> unitStandardslist;
	
	@Transient
	private List<WorkPlaceApprovalToolList> workPlaceApprovalToolList;
	
	public WorkPlaceApproval() {
		super();
	}

	public WorkPlaceApproval(MandatoryGrant mandatoryGrant) {
		super();
		this.mandatoryGrant = mandatoryGrant;
	}

	public WorkPlaceApproval(Company company, Qualification qualification) {
		super();
		this.company = company;
		this.qualification = qualification;
	}
	
	public WorkPlaceApproval(Company company, Qualification qualification, Sites sites) {
		super();
		this.company = company;
		this.qualification = qualification;
		this.sites = sites;
	}

	public WorkPlaceApproval(Company company, OfoCodes ofoCodes) {
		super();
		this.company = company;
		this.ofoCodes = ofoCodes;
	}
	
	public WorkPlaceApproval(Company company, OfoCodes ofoCodes, Sites sites) {
		super();
		this.company = company;
		this.ofoCodes = ofoCodes;
		this.sites = sites;
	}

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
		WorkPlaceApproval other = (WorkPlaceApproval) obj;
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

	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
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

	public Date getDateCaptured() {
		return dateCaptured;
	}

	public void setDateCaptured(Date dateCaptured) {
		this.dateCaptured = dateCaptured;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public YesNoLookup getTaxClearanceCertificate() {
		return taxClearanceCertificate;
	}

	public void setTaxClearanceCertificate(YesNoLookup taxClearanceCertificate) {
		this.taxClearanceCertificate = taxClearanceCertificate;
	}

	public YesNoLookup getIdentifiedCommittedStaff() {
		return identifiedCommittedStaff;
	}

	public void setIdentifiedCommittedStaff(YesNoLookup identifiedCommittedStaff) {
		this.identifiedCommittedStaff = identifiedCommittedStaff;
	}

	public YesNoLookup getDeclarationFromEmployer() {
		return declarationFromEmployer;
	}

	public void setDeclarationFromEmployer(YesNoLookup declarationFromEmployer) {
		this.declarationFromEmployer = declarationFromEmployer;
	}

	public YesNoLookup getStructuredImplementationPlan() {
		return structuredImplementationPlan;
	}

	public void setStructuredImplementationPlan(YesNoLookup structuredImplementationPlan) {
		this.structuredImplementationPlan = structuredImplementationPlan;
	}

	public YesNoLookup getSuitableQualifiedMentors() {
		return suitableQualifiedMentors;
	}

	public void setSuitableQualifiedMentors(YesNoLookup suitableQualifiedMentors) {
		this.suitableQualifiedMentors = suitableQualifiedMentors;
	}

	public YesNoLookup getLetterOfCommitment() {
		return letterOfCommitment;
	}

	public void setLetterOfCommitment(YesNoLookup letterOfCommitment) {
		this.letterOfCommitment = letterOfCommitment;
	}

	public YesNoLookup getDeclarationIndicationCommitment() {
		return declarationIndicationCommitment;
	}

	public void setDeclarationIndicationCommitment(YesNoLookup declarationIndicationCommitment) {
		this.declarationIndicationCommitment = declarationIndicationCommitment;
	}

	public YesNoLookup getCopyOfSelfEvaluation() {
		return copyOfSelfEvaluation;
	}

	public void setCopyOfSelfEvaluation(YesNoLookup copyOfSelfEvaluation) {
		this.copyOfSelfEvaluation = copyOfSelfEvaluation;
	}

	public YesNoLookup getWorkplaceAbleToCoverScope() {
		return workplaceAbleToCoverScope;
	}

	public void setWorkplaceAbleToCoverScope(YesNoLookup workplaceAbleToCoverScope) {
		this.workplaceAbleToCoverScope = workplaceAbleToCoverScope;
	}

	public YesNoLookup getFormalAgreementAprrovedWorkpalces() {
		return formalAgreementAprrovedWorkpalces;
	}

	public void setFormalAgreementAprrovedWorkpalces(YesNoLookup formalAgreementAprrovedWorkpalces) {
		this.formalAgreementAprrovedWorkpalces = formalAgreementAprrovedWorkpalces;
	}

	public YesNoLookup getMentorArtisanRationAcceptable() {
		return mentorArtisanRationAcceptable;
	}

	public void setMentorArtisanRationAcceptable(YesNoLookup mentorArtisanRationAcceptable) {
		this.mentorArtisanRationAcceptable = mentorArtisanRationAcceptable;
	}

	public YesNoLookup getEvidenceAttachedOfPrevios() {
		return evidenceAttachedOfPrevios;
	}

	public void setEvidenceAttachedOfPrevios(YesNoLookup evidenceAttachedOfPrevios) {
		this.evidenceAttachedOfPrevios = evidenceAttachedOfPrevios;
	}

	public YesNoLookup getAllToolsAvailable() {
		return allToolsAvailable;
	}

	public void setAllToolsAvailable(YesNoLookup allToolsAvailable) {
		this.allToolsAvailable = allToolsAvailable;
	}

	public YesNoLookup getAccessToMaterial() {
		return accessToMaterial;
	}

	public void setAccessToMaterial(YesNoLookup accessToMaterial) {
		this.accessToMaterial = accessToMaterial;
	}

	public YesNoLookup getAbilityToCoverEntireScope() {
		return abilityToCoverEntireScope;
	}

	public void setAbilityToCoverEntireScope(YesNoLookup abilityToCoverEntireScope) {
		this.abilityToCoverEntireScope = abilityToCoverEntireScope;
	}

	public YesNoLookup getFormalAgreementWithOther() {
		return formalAgreementWithOther;
	}

	public void setFormalAgreementWithOther(YesNoLookup formalAgreementWithOther) {
		this.formalAgreementWithOther = formalAgreementWithOther;
	}

	public YesNoLookup getLayoutEnvironmentSafe() {
		return layoutEnvironmentSafe;
	}

	public void setLayoutEnvironmentSafe(YesNoLookup layoutEnvironmentSafe) {
		this.layoutEnvironmentSafe = layoutEnvironmentSafe;
	}

	public YesNoLookup getRecordKeepingSystem() {
		return recordKeepingSystem;
	}

	public void setRecordKeepingSystem(YesNoLookup recordKeepingSystem) {
		this.recordKeepingSystem = recordKeepingSystem;
	}

	public YesNoLookup getQualifiedMentorsForTrade() {
		return qualifiedMentorsForTrade;
	}

	public void setQualifiedMentorsForTrade(YesNoLookup qualifiedMentorsForTrade) {
		this.qualifiedMentorsForTrade = qualifiedMentorsForTrade;
	}

	public YesNoLookup getOhsaMhsaCompliant() {
		return ohsaMhsaCompliant;
	}

	public void setOhsaMhsaCompliant(YesNoLookup ohsaMhsaCompliant) {
		this.ohsaMhsaCompliant = ohsaMhsaCompliant;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public CloRecommendationEnum getCloRecommendation() {
		return cloRecommendation;
	}

	public void setCloRecommendation(CloRecommendationEnum cloRecommendation) {
		this.cloRecommendation = cloRecommendation;
	}

	public Boolean getWithClientCompany() {
		return withClientCompany;
	}

	public void setWithClientCompany(Boolean withClientCompany) {
		this.withClientCompany = withClientCompany;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Boolean getAmendmentsRequired() {
		return amendmentsRequired;
	}

	public void setAmendmentsRequired(Boolean amendmentsRequired) {
		this.amendmentsRequired = amendmentsRequired;
	}

	public Boolean getDateForVisitProvided() {
		return dateForVisitProvided;
	}

	public void setDateForVisitProvided(Boolean dateForVisitProvided) {
		this.dateForVisitProvided = dateForVisitProvided;
	}

	public Sites getSites() {
		return sites;
	}

	public void setSites(Sites sites) {
		this.sites = sites;
	}

	public List<Signoff> getSignOffs() {
		return signOffs;
	}

	public void setSignOffs(List<Signoff> signOffs) {
		this.signOffs = signOffs;
	}

	public Boolean getWpaAppealed() {
		return wpaAppealed;
	}

	public void setWpaAppealed(Boolean wpaAppealed) {
		this.wpaAppealed = wpaAppealed;
	}

	public List<AppraisalChecklist> getAppraisalChecklist() {
		return appraisalChecklist;
	}

	public void setAppraisalChecklist(List<AppraisalChecklist> appraisalChecklist) {
		this.appraisalChecklist = appraisalChecklist;
	}

	public YesNoLookup getWorkplaceAbleToCoverTradeOrQualification() {
		return workplaceAbleToCoverTradeOrQualification;
	}

	public void setWorkplaceAbleToCoverTradeOrQualification(YesNoLookup workplaceAbleToCoverTradeOrQualification) {
		this.workplaceAbleToCoverTradeOrQualification = workplaceAbleToCoverTradeOrQualification;
	}

	public YesNoLookup getCommittedOhsaOrMhsaCompliant() {
		return committedOhsaOrMhsaCompliant;
	}

	public void setCommittedOhsaOrMhsaCompliant(YesNoLookup committedOhsaOrMhsaCompliant) {
		this.committedOhsaOrMhsaCompliant = committedOhsaOrMhsaCompliant;
	}

	public YesNoLookup getCommittedtoBecompliantRelevantLegislationApplicable() {
		return committedtoBecompliantRelevantLegislationApplicable;
	}

	public void setCommittedtoBecompliantRelevantLegislationApplicable(
			YesNoLookup committedtoBecompliantRelevantLegislationApplicable) {
		this.committedtoBecompliantRelevantLegislationApplicable = committedtoBecompliantRelevantLegislationApplicable;
	}

	public YesNoLookup getPretectiveWearSdf() {
		return pretectiveWearSdf;
	}

	public void setPretectiveWearSdf(YesNoLookup pretectiveWearSdf) {
		this.pretectiveWearSdf = pretectiveWearSdf;
	}

	public YesNoLookup getIdentifiedCommittedStaffSdf() {
		return identifiedCommittedStaffSdf;
	}

	public void setIdentifiedCommittedStaffSdf(YesNoLookup identifiedCommittedStaffSdf) {
		this.identifiedCommittedStaffSdf = identifiedCommittedStaffSdf;
	}

	public YesNoLookup getStructuredImplementationPlanSdf() {
		return structuredImplementationPlanSdf;
	}

	public void setStructuredImplementationPlanSdf(YesNoLookup structuredImplementationPlanSdf) {
		this.structuredImplementationPlanSdf = structuredImplementationPlanSdf;
	}

	public YesNoLookup getMentorArtisanRationAcceptableSdf() {
		return mentorArtisanRationAcceptableSdf;
	}

	public void setMentorArtisanRationAcceptableSdf(YesNoLookup mentorArtisanRationAcceptableSdf) {
		this.mentorArtisanRationAcceptableSdf = mentorArtisanRationAcceptableSdf;
	}

	public YesNoLookup getAllToolsAvailableSdf() {
		return allToolsAvailableSdf;
	}

	public void setAllToolsAvailableSdf(YesNoLookup allToolsAvailableSdf) {
		this.allToolsAvailableSdf = allToolsAvailableSdf;
	}

	public YesNoLookup getAccessToMaterialSdf() {
		return accessToMaterialSdf;
	}

	public void setAccessToMaterialSdf(YesNoLookup accessToMaterialSdf) {
		this.accessToMaterialSdf = accessToMaterialSdf;
	}

	public YesNoLookup getFormalAgreementWithOtherSdf() {
		return formalAgreementWithOtherSdf;
	}

	public void setFormalAgreementWithOtherSdf(YesNoLookup formalAgreementWithOtherSdf) {
		this.formalAgreementWithOtherSdf = formalAgreementWithOtherSdf;
	}

	public YesNoLookup getLayoutEnvironmentSafeSdf() {
		return layoutEnvironmentSafeSdf;
	}

	public void setLayoutEnvironmentSafeSdf(YesNoLookup layoutEnvironmentSafeSdf) {
		this.layoutEnvironmentSafeSdf = layoutEnvironmentSafeSdf;
	}

	public YesNoLookup getRecordKeepingSystemSdf() {
		return recordKeepingSystemSdf;
	}

	public void setRecordKeepingSystemSdf(YesNoLookup recordKeepingSystemSdf) {
		this.recordKeepingSystemSdf = recordKeepingSystemSdf;
	}

	public YesNoLookup getQualifiedMentorsForTradeSdf() {
		return qualifiedMentorsForTradeSdf;
	}

	public void setQualifiedMentorsForTradeSdf(YesNoLookup qualifiedMentorsForTradeSdf) {
		this.qualifiedMentorsForTradeSdf = qualifiedMentorsForTradeSdf;
	}

	public YesNoLookup getWorkplaceAbleToCoverTradeOrQualificationSdf() {
		return workplaceAbleToCoverTradeOrQualificationSdf;
	}

	public void setWorkplaceAbleToCoverTradeOrQualificationSdf(YesNoLookup workplaceAbleToCoverTradeOrQualificationSdf) {
		this.workplaceAbleToCoverTradeOrQualificationSdf = workplaceAbleToCoverTradeOrQualificationSdf;
	}

	public YesNoLookup getCommittedOhsaOrMhsaCompliantSdf() {
		return committedOhsaOrMhsaCompliantSdf;
	}

	public void setCommittedOhsaOrMhsaCompliantSdf(YesNoLookup committedOhsaOrMhsaCompliantSdf) {
		this.committedOhsaOrMhsaCompliantSdf = committedOhsaOrMhsaCompliantSdf;
	}

	public YesNoLookup getCommittedtoBecompliantRelevantLegislationApplicableSdf() {
		return committedtoBecompliantRelevantLegislationApplicableSdf;
	}

	public void setCommittedtoBecompliantRelevantLegislationApplicableSdf(
			YesNoLookup committedtoBecompliantRelevantLegislationApplicableSdf) {
		this.committedtoBecompliantRelevantLegislationApplicableSdf = committedtoBecompliantRelevantLegislationApplicableSdf;
	}

	public String getWorkPlaceApprovalNumber() {
		return workPlaceApprovalNumber;
	}

	public void setWorkPlaceApprovalNumber(String workPlaceApprovalNumber) {
		this.workPlaceApprovalNumber = workPlaceApprovalNumber;
	}

	public Users getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(Users reviewUser) {
		this.reviewUser = reviewUser;
	}

	public List<SkillsProgram> getSkillsProgramlist() {
		return skillsProgramlist;
	}

	public void setSkillsProgramlist(List<SkillsProgram> skillsProgramlist) {
		this.skillsProgramlist = skillsProgramlist;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<WorkPlaceApprovalToolList> getWorkPlaceApprovalToolList() {
		return workPlaceApprovalToolList;
	}

	public void setWorkPlaceApprovalToolList(List<WorkPlaceApprovalToolList> workPlaceApprovalToolList) {
		this.workPlaceApprovalToolList = workPlaceApprovalToolList;
	}

	public Date getApprovalReviewDate() {
		return approvalReviewDate;
	}

	public void setApprovalReviewDate(Date approvalReviewDate) {
		this.approvalReviewDate = approvalReviewDate;
	}

	public Boolean getWithManager() {
		return withManager;
	}

	public void setWithManager(Boolean withManager) {
		this.withManager = withManager;
	}

	public String getRejectMessage() {
		return rejectMessage;
	}

	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}

	public LegacyEmployerWA2Workplace getLegacyEmployerWA2Workplace() {
		return legacyEmployerWA2Workplace;
	}

	public void setLegacyEmployerWA2Workplace(LegacyEmployerWA2Workplace legacyEmployerWA2Workplace) {
		this.legacyEmployerWA2Workplace = legacyEmployerWA2Workplace;
	}

	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public UnitStandards getUnitstandard() {
		return unitstandard;
	}

	public void setUnitstandard(UnitStandards unitstandard) {
		this.unitstandard = unitstandard;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	public WorkplaceApprovalTypeEnum getWorkplaceApprovalTypeEnum() {
		return workplaceApprovalTypeEnum;
	}

	public void setWorkplaceApprovalTypeEnum(WorkplaceApprovalTypeEnum workplaceApprovalTypeEnum) {
		this.workplaceApprovalTypeEnum = workplaceApprovalTypeEnum;
	}

	public List<SkillsSet> getSkillsSetlist() {
		return skillsSetlist;
	}

	public void setSkillsSetlist(List<SkillsSet> skillsSetlist) {
		this.skillsSetlist = skillsSetlist;
	}

	public List<Learnership> getLearnershiplist() {
		return learnershiplist;
	}

	public void setLearnershiplist(List<Learnership> learnershiplist) {
		this.learnershiplist = learnershiplist;
	}

	public List<UnitStandards> getUnitStandardslist() {
		return unitStandardslist;
	}

	public void setUnitStandardslist(List<UnitStandards> unitStandardslist) {
		this.unitStandardslist = unitStandardslist;
	}

	public ApprovalEnum getOrigionalApprovalEnum() {
		return origionalApprovalEnum;
	}

	public void setOrigionalApprovalEnum(ApprovalEnum origionalApprovalEnum) {
		this.origionalApprovalEnum = origionalApprovalEnum;
	}
}
