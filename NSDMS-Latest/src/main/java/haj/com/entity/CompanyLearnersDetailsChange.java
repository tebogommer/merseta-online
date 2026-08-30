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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.HighestEducationEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.entity.lookup.EmploymentType;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.PreviousSchools;
import haj.com.entity.lookup.ProjectType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.STATSSAAreaCode;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnionMembership;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learners_details_change")
public class CompanyLearnersDetailsChange implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyUsers. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;

	/** The company. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = true)
	private Sites site;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;

	//@Fetch(FetchMode.JOIN) Do not use this join
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", nullable = true)
	private Company employer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_seta_company_id", nullable = true)
	private NonSetaCompany nonSetaCompany;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mandatory_grant_getail_id", nullable = true)
	private MandatoryGrantDetail mandatoryGrantDetail;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Column(name = "scheme_year")
	private Integer schemeYear;

	@Column(name = "learnership_code", length = 50)
	private String learnershipCode;

	@Column(name = "qualification_code", length = 20)
	private String qualificationCode;

	@Column(name = "dgtag", length = 50)
	private String dgtag;

	@Column(name = "etqid", length = 10)
	private String etqid;

	@Column(name = "current_status", length = 100)
	private String current_status;

	@Column(name = "funding", length = 30)
	private String funding;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "certificate_date", length = 19)
	private Date certificateDate;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registered_date", length = 19)
	private Date registeredDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionType interventionType;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_credit_bearing_intervation_title_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_aligned_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private YesNoLookup nqfAligned;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsSet skillsSet;

	/** The pivot non pivot. */
	@Enumerated
	@Column(name = "pivot_non_pivot")
	private PivotNonPivotEnum pivotNonPivot;

	@Enumerated
	@Column(name = "merseta_funded")
	private YesNoEnum mersetaFunded;

	@Column(name = "dg_tag", length = 30)
	private String dgTag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_levels_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqfLevels;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionLevel interventionLevel;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dgtag_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private DGYear dgYearTag;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projecttype_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private ProjectType projectType;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dunding_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Funding sourceFunding;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Column(name = "document_box_id")
	private String documentBoxID;
	
	@Column(name = "non_credid_bearing_description", length = 50)
	private String nonCredidBearingDescription;

	@Enumerated
	@Column(name = "review_status")
	private ApprovalEnum reviewStatus;

	@Enumerated
	@Column(name = "learner_status")
	private LearnerStatusEnum learnerStatus;

	@Column(name = "metro_file_barcode", length = 100)
	private String metroFileBarcode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "commencement_date", length = 19)
	private Date commencementDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completion_date", length = 19)
	private Date completionDate;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "appointment_id", nullable = true)
	private Appointment appointment;

	@Column(name = "signoff", nullable = true)
	public Boolean signoff;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "designated_trade_id", nullable = true)
	private DesignatedTrade designatedTrade;

	// The ETQA Review Committee Schedule.
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "review_committee_meeting_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;

	@Enumerated
	@Column(name = "undertaken_learning_program_before")
	private YesNoEnum undertakenLearningProgramBefore;

	@Column(name = "qualification_title", length = 50)
	private String qualificationTitle;

	@Column(name = "registration_number", length = 30)
	private String registrationNumber;

	@Column(name = "credits", length = 30)
	private String credits;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "designated_trade_level_id", nullable = true)
	private DesignatedTradeLevel designatedTradeLevel;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "designated_trade_type_id", nullable = true)
	private DesignatedTradeType designatedTradeType;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stats_saarea_code_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private STATSSAAreaCode statsSaAreaCode;

	@Column(name = "last_school_year", length = 19)
	private Date lastSchoolYear;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learnership_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Learnership learnership;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scheduled_event_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private ScheduledEvent scheduledEvent;

	@Enumerated
	@Column(name = "workplace_based_before")
	private YesNoEnum workplaceBasedBefore;
	
	@Enumerated
	@Column(name = "employed_by_employer_before")
	private YesNoEnum employedByEmployerBefore;

	@Enumerated
	@Column(name = "contract_of_employment_specified")
	private YesNoEnum contractOfEmploymentSpecified;

	@Enumerated
	@Column(name = "contract_of_employment_copy")
	private YesNoEnum copyOfContractOfEmployment;

	@Column(name = "yes_specify")
	private String yesSpecify;

	@Column(name = "no_explain", length = 50)
	private String noExplain;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_implementation_plan_id", nullable = true)
	private ProjectImplementationPlan projectImplementationPlan;

	@Column(name = "employment_contract_start_date", length = 19)
	private Date employmentContractStartDate;

	@Column(name = "employment_contract_end_date", length = 19)
	private Date employmentContractEndDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_learners_parent_id", nullable = true)
	private CompanyLearners companyLearnersParent;
	
	@Column(name = "current_job_title")
	private String currentJobTitle;
	
	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_company_id", nullable = true)
	private Company hostCompany;

	@Column(name = "host_company_available", nullable = true)
	public Boolean hostCompanyAvailable;
	
	@Column(name = "host_company_yes_no_enum", nullable = true)
	public YesNoEnum hostCompanyyesNoEnum;
	
	@Column(name = "system_termination", nullable = true)
	public Boolean systemTermination ;
	
	@Enumerated
	@Column(name = "submission")
	private SubmissionEnum submissionEnum;
	
	@Column(name = "provider_accredited", nullable = true)
	public Boolean provideAccredited;
	
	@Column(name = "provider_workplace_approved", nullable = true)
	public Boolean provideWorkplaceapproved;
	
	@Enumerated
	@Column(name = "employment_status")
	private EmploymentStatusEnum employmentStatus;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "union_membership_id", nullable = true)
	private UnionMembership unionMembership;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organised_labour_union_membership_id", nullable = true)
	private OrganisedLabourUnion organisedLabourUnion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "employment_start_date", length = 19)
	private Date employmentStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "employment_end_date", length = 19)
	private Date employmentEndDate;
	
	@Enumerated
	@Column(name = "part_of_union")
	private YesNoEnum partOfUnion;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "highest_qualification_id", nullable = true)
	private Qualification highestQualification;
	
	@Column(name = "howlong")
	private Integer howLong;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "file_approval_date", length = 19)
	private Date fileApprovalDate;
	
	@Enumerated
	@Column(name = "file_approval")
	private ApprovalEnum fileApprovalEnum;
	
	@Enumerated
	@Column(name = "highest_education_enum")
	private HighestEducationEnum highestEducationEnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signed_date", length = 19)
	private Date signedDate;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_schools", nullable = true)
	@Fetch(FetchMode.JOIN)
	private PreviousSchools previousSchools;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "conditional_placement_date", length = 19)
	private Date conditionalPlacementDate;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employment_type_id", nullable = true)
	private EmploymentType employmentType;
	
	@Column(name = "merseta_company", nullable = true)
	public Boolean mersetaCompany;
	
	@Transient
	private boolean canAction;

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	@Transient
	private List<Doc> docs;

	@Transient
	private List<CompanyLearnersTransfer> companyLearnersTransfer;

	@Transient
	private List<CompanyLearnersLostTime> companyLearnersLostTime;

	@Transient
	private List<CompanyLearnersTermination> companyLearnersTermination;

	@Transient
	private List<CompanyLearnersChange> companyLearnersChange;

	@Transient
	private List<CompanyLearnersTradeTest> companyLearnersTradeTest;

	@Transient
	private List<CompanyLearnersProgress> companyLearnersProgresses;

	@Transient
	private List<CompanyLearners> companyLearnersList;

	@Transient
	private boolean extentionCheck;
	
	@Transient
	private NonSetaQualificationsCompletion nonSetaQualificationsCompletion;

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
		CompanyLearnersDetailsChange other = (CompanyLearnersDetailsChange) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new company users.
	 */
	public CompanyLearnersDetailsChange() {
		super();
		this.learnerStatus = LearnerStatusEnum.Application;
	}

	public CompanyLearnersDetailsChange(CompanyLearners companyLearnersParent) {
		super();
		this.companyLearnersParent = companyLearnersParent;
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

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	public String getLearnershipCode() {
		return learnershipCode;
	}

	public void setLearnershipCode(String learnershipCode) {
		this.learnershipCode = learnershipCode;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public String getDgtag() {
		return dgtag;
	}

	public void setDgtag(String dgtag) {
		this.dgtag = dgtag;
	}

	public String getEtqid() {
		return etqid;
	}

	public void setEtqid(String etqid) {
		this.etqid = etqid;
	}

	public String getCurrent_status() {
		return current_status;
	}

	public void setCurrent_status(String current_status) {
		this.current_status = current_status;
	}

	public String getFunding() {
		return funding;
	}

	public void setFunding(String funding) {
		this.funding = funding;
	}

	public Date getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public MandatoryGrantDetail getMandatoryGrantDetail() {
		return mandatoryGrantDetail;
	}

	public void setMandatoryGrantDetail(MandatoryGrantDetail mandatoryGrantDetail) {
		this.mandatoryGrantDetail = mandatoryGrantDetail;
	}

	@Transient
	public Qualification getNonNullQualification() {
		return qualification != null ? qualification : skillsSet != null ? skillsSet.getQualification() : skillsProgram.getQualification();
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Company getEmployer() {
		return employer;
	}

	public void setEmployer(Company employer) {
		this.employer = employer;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public YesNoLookup getNqfAligned() {
		return nqfAligned;
	}

	public void setNqfAligned(YesNoLookup nqfAligned) {
		this.nqfAligned = nqfAligned;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
	}

	public NqfLevels getNqfLevels() {
		return nqfLevels;
	}

	public void setNqfLevels(NqfLevels nqfLevels) {
		this.nqfLevels = nqfLevels;
	}

	public InterventionLevel getInterventionLevel() {
		return interventionLevel;
	}

	public void setInterventionLevel(InterventionLevel interventionLevel) {
		this.interventionLevel = interventionLevel;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public LearnerStatusEnum getLearnerStatus() {
		return learnerStatus;
	}

	public void setLearnerStatus(LearnerStatusEnum learnerStatus) {
		this.learnerStatus = learnerStatus;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public String getMetroFileBarcode() {
		return metroFileBarcode;
	}

	public void setMetroFileBarcode(String metroFileBarcode) {
		this.metroFileBarcode = metroFileBarcode;
	}

	public List<CompanyLearnersTransfer> getCompanyLearnersTransfer() {
		return companyLearnersTransfer;
	}

	public void setCompanyLearnersTransfer(List<CompanyLearnersTransfer> companyLearnersTransfer) {
		this.companyLearnersTransfer = companyLearnersTransfer;
	}

	public List<CompanyLearnersLostTime> getCompanyLearnersLostTime() {
		return companyLearnersLostTime;
	}

	public void setCompanyLearnersLostTime(List<CompanyLearnersLostTime> companyLearnersLostTime) {
		this.companyLearnersLostTime = companyLearnersLostTime;
	}

	public List<CompanyLearnersTermination> getCompanyLearnersTermination() {
		return companyLearnersTermination;
	}

	public void setCompanyLearnersTermination(List<CompanyLearnersTermination> companyLearnersTermination) {
		this.companyLearnersTermination = companyLearnersTermination;
	}

	public List<CompanyLearnersChange> getCompanyLearnersChange() {
		return companyLearnersChange;
	}

	public void setCompanyLearnersChange(List<CompanyLearnersChange> companyLearnersChange) {
		this.companyLearnersChange = companyLearnersChange;
	}

	public List<CompanyLearnersTradeTest> getCompanyLearnersTradeTest() {
		return companyLearnersTradeTest;
	}

	public void setCompanyLearnersTradeTest(List<CompanyLearnersTradeTest> companyLearnersTradeTest) {
		this.companyLearnersTradeTest = companyLearnersTradeTest;
	}

	public Date getCommencementDate() {
		return commencementDate;
	}

	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public YesNoEnum getMersetaFunded() {
		return mersetaFunded;
	}

	public void setMersetaFunded(YesNoEnum mersetaFunded) {
		this.mersetaFunded = mersetaFunded;
	}

	public String getDgTag() {
		return dgTag;
	}

	public void setDgTag(String dgTag) {
		this.dgTag = dgTag;
	}

	public List<CompanyLearnersProgress> getCompanyLearnersProgresses() {
		return companyLearnersProgresses;
	}

	public void setCompanyLearnersProgresses(List<CompanyLearnersProgress> companyLearnersProgresses) {
		this.companyLearnersProgresses = companyLearnersProgresses;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public DGYear getDgYearTag() {
		return dgYearTag;
	}

	public void setDgYearTag(DGYear dgYearTag) {
		this.dgYearTag = dgYearTag;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public boolean isSignoff() {
		return signoff;
	}

	public void setSignoff(boolean signoff) {
		this.signoff = signoff;
	}

	/**
	 * @return the companyLearnersList
	 */
	public List<CompanyLearners> getCompanyLearnersList() {
		return companyLearnersList;
	}

	/**
	 * @param companyLearnersList
	 *            the companyLearnersList to set
	 */
	public void setCompanyLearnersList(List<CompanyLearners> companyLearnersList) {
		this.companyLearnersList = companyLearnersList;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public ApprovalEnum getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(ApprovalEnum reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getQualificationTitle() {
		return qualificationTitle;
	}

	public YesNoEnum getUndertakenLearningProgramBefore() {
		return undertakenLearningProgramBefore;
	}

	public void setUndertakenLearningProgramBefore(YesNoEnum undertakenLearningProgramBefore) {
		this.undertakenLearningProgramBefore = undertakenLearningProgramBefore;
	}

	public void setQualificationTitle(String qualificationTitle) {
		this.qualificationTitle = qualificationTitle;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public DesignatedTrade getDesignatedTrade() {
		return designatedTrade;
	}

	public void setDesignatedTrade(DesignatedTrade designatedTrade) {
		this.designatedTrade = designatedTrade;
	}

	public boolean isCanAction() {
		return canAction;
	}

	public void setCanAction(boolean canAction) {
		this.canAction = canAction;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public DesignatedTradeLevel getDesignatedTradeLevel() {
		return designatedTradeLevel;
	}

	public void setDesignatedTradeLevel(DesignatedTradeLevel designatedTradeLevel) {
		this.designatedTradeLevel = designatedTradeLevel;
	}

	public DesignatedTradeType getDesignatedTradeType() {
		return designatedTradeType;
	}

	public void setDesignatedTradeType(DesignatedTradeType designatedTradeType) {
		this.designatedTradeType = designatedTradeType;
	}

	public YesNoEnum getWorkplaceBasedBefore() {
		return workplaceBasedBefore;
	}

	public void setWorkplaceBasedBefore(YesNoEnum workplaceBasedBefore) {
		this.workplaceBasedBefore = workplaceBasedBefore;
	}

	public YesNoEnum getEmployedByEmployerBefore() {
		return employedByEmployerBefore;
	}

	public void setEmployedByEmployerBefore(YesNoEnum employedByEmployerBefore) {
		this.employedByEmployerBefore = employedByEmployerBefore;
	}

	public YesNoEnum getContractOfEmploymentSpecified() {
		return contractOfEmploymentSpecified;
	}

	public void setContractOfEmploymentSpecified(YesNoEnum contractOfEmploymentSpecified) {
		this.contractOfEmploymentSpecified = contractOfEmploymentSpecified;
	}

	public YesNoEnum getCopyOfContractOfEmployment() {
		return copyOfContractOfEmployment;
	}

	public void setCopyOfContractOfEmployment(YesNoEnum copyOfContractOfEmployment) {
		this.copyOfContractOfEmployment = copyOfContractOfEmployment;
	}

	public String getYesSpecify() {
		return yesSpecify;
	}

	public void setYesSpecify(String yesSpecify) {
		this.yesSpecify = yesSpecify;
	}

	public String getNoExplain() {
		return noExplain;
	}

	public void setNoExplain(String noExplain) {
		this.noExplain = noExplain;
	}

	public STATSSAAreaCode getStatsSaAreaCode() {
		return statsSaAreaCode;
	}

	public void setStatsSaAreaCode(STATSSAAreaCode statsSaAreaCode) {
		this.statsSaAreaCode = statsSaAreaCode;
	}

	public ProjectImplementationPlan getProjectImplementationPlan() {
		return projectImplementationPlan;
	}

	public void setProjectImplementationPlan(ProjectImplementationPlan projectImplementationPlan) {
		this.projectImplementationPlan = projectImplementationPlan;
	}

	public Funding getSourceFunding() {
		return sourceFunding;
	}

	public void setSourceFunding(Funding sourceFunding) {
		this.sourceFunding = sourceFunding;
	}

	public Date getLastSchoolYear() {
		return lastSchoolYear;
	}

	public void setLastSchoolYear(Date lastSchoolYear) {
		this.lastSchoolYear = lastSchoolYear;
	}

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	public Date getEmploymentContractStartDate() {
		return employmentContractStartDate;
	}

	public void setEmploymentContractStartDate(Date employmentContractStartDate) {
		this.employmentContractStartDate = employmentContractStartDate;
	}

	public Date getEmploymentContractEndDate() {
		return employmentContractEndDate;
	}

	public void setEmploymentContractEndDate(Date employmentContractEndDate) {
		this.employmentContractEndDate = employmentContractEndDate;
	}

	public CompanyLearners getCompanyLearnersParent() {
		return companyLearnersParent;
	}

	public void setCompanyLearnersParent(CompanyLearners companyLearnersParent) {
		this.companyLearnersParent = companyLearnersParent;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public String getDocumentBoxID() {
		return documentBoxID;
	}

	public void setDocumentBoxID(String documentBoxID) {
		this.documentBoxID = documentBoxID;
	}

	public NonCreditBearingIntervationTitle getNonCreditBearingIntervationTitle() {
		return nonCreditBearingIntervationTitle;
	}

	public void setNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle) {
		this.nonCreditBearingIntervationTitle = nonCreditBearingIntervationTitle;
	}

	public String getCurrentJobTitle() {
		return currentJobTitle;
	}

	public void setCurrentJobTitle(String currentJobTitle) {
		this.currentJobTitle = currentJobTitle;
	}

	public Company getHostCompany() {
		return hostCompany;
	}

	public void setHostCompany(Company hostCompany) {
		this.hostCompany = hostCompany;
	}

	public Boolean getHostCompanyAvailable() {
		return hostCompanyAvailable;
	}

	public void setHostCompanyAvailable(Boolean hostCompanyAvailable) {
		this.hostCompanyAvailable = hostCompanyAvailable;
	}

	public YesNoEnum getHostCompanyyesNoEnum() {
		return hostCompanyyesNoEnum;
	}

	public void setHostCompanyyesNoEnum(YesNoEnum hostCompanyyesNoEnum) {
		this.hostCompanyyesNoEnum = hostCompanyyesNoEnum;
	}
	
    public boolean isExtentionCheck() {
        return extentionCheck;
    }

    public void setExtentionCheck(boolean extentionCheck) {
        this.extentionCheck = extentionCheck;
    }

	/**
	 * @return the systemTermination
	 */
	public Boolean getSystemTermination() {
		return systemTermination;
	}

	/**
	 * @param systemTermination the systemTermination to set
	 */
	public void setSystemTermination(Boolean systemTermination) {
		this.systemTermination = systemTermination;
	}

	public NonSetaQualificationsCompletion getNonSetaQualificationsCompletion() {
		return nonSetaQualificationsCompletion;
	}

	public void setNonSetaQualificationsCompletion(NonSetaQualificationsCompletion nonSetaQualificationsCompletion) {
		this.nonSetaQualificationsCompletion = nonSetaQualificationsCompletion;
	}

	public SubmissionEnum getSubmissionEnum() {
		return submissionEnum;
	}

	public void setSubmissionEnum(SubmissionEnum submissionEnum) {
		this.submissionEnum = submissionEnum;
	}

	public Boolean getProvideAccredited() {
		return provideAccredited;
	}

	public void setProvideAccredited(Boolean provideAccredited) {
		this.provideAccredited = provideAccredited;
	}

	public Boolean getProvideWorkplaceapproved() {
		return provideWorkplaceapproved;
	}

	public void setProvideWorkplaceapproved(Boolean provideWorkplaceapproved) {
		this.provideWorkplaceapproved = provideWorkplaceapproved;
	}

	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public UnionMembership getUnionMembership() {
		return unionMembership;
	}

	public void setUnionMembership(UnionMembership unionMembership) {
		this.unionMembership = unionMembership;
	}

	public OrganisedLabourUnion getOrganisedLabourUnion() {
		return organisedLabourUnion;
	}

	public void setOrganisedLabourUnion(OrganisedLabourUnion organisedLabourUnion) {
		this.organisedLabourUnion = organisedLabourUnion;
	}

	public Date getEmploymentStartDate() {
		return employmentStartDate;
	}

	public void setEmploymentStartDate(Date employmentStartDate) {
		this.employmentStartDate = employmentStartDate;
	}

	public Date getEmploymentEndDate() {
		return employmentEndDate;
	}

	public void setEmploymentEndDate(Date employmentEndDate) {
		this.employmentEndDate = employmentEndDate;
	}

	public YesNoEnum getPartOfUnion() {
		return partOfUnion;
	}

	public void setPartOfUnion(YesNoEnum partOfUnion) {
		this.partOfUnion = partOfUnion;
	}

	public Qualification getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(Qualification highestQualification) {
		this.highestQualification = highestQualification;
	}

	public Integer getHowLong() {
		return howLong;
	}

	public void setHowLong(Integer howLong) {
		this.howLong = howLong;
	}

	public Date getFileApprovalDate() {
		return fileApprovalDate;
	}

	public void setFileApprovalDate(Date fileApprovalDate) {
		this.fileApprovalDate = fileApprovalDate;
	}

	public ApprovalEnum getFileApprovalEnum() {
		return fileApprovalEnum;
	}

	public void setFileApprovalEnum(ApprovalEnum fileApprovalEnum) {
		this.fileApprovalEnum = fileApprovalEnum;
	}

	public String getNonCredidBearingDescription() {
		return nonCredidBearingDescription;
	}

	public void setNonCredidBearingDescription(String nonCredidBearingDescription) {
		this.nonCredidBearingDescription = nonCredidBearingDescription;
	}

	public HighestEducationEnum getHighestEducationEnum() {
		return highestEducationEnum;
	}

	public void setHighestEducationEnum(HighestEducationEnum highestEducationEnum) {
		this.highestEducationEnum = highestEducationEnum;
	}

	public Date getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	public PreviousSchools getPreviousSchools() {
		return previousSchools;
	}

	public void setPreviousSchools(PreviousSchools previousSchools) {
		this.previousSchools = previousSchools;
	}

	public Date getConditionalPlacementDate() {
		return conditionalPlacementDate;
	}

	public void setConditionalPlacementDate(Date conditionalPlacementDate) {
		this.conditionalPlacementDate = conditionalPlacementDate;
	}

	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public Boolean getMersetaCompany() {
		return mersetaCompany;
	}

	public void setMersetaCompany(Boolean mersetaCompany) {
		this.mersetaCompany = mersetaCompany;
	}
}
