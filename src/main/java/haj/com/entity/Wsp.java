package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.constants.HAJConstants;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.HoldingRoomStatusEnum;
import haj.com.entity.enums.WspReopenLocationEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.DGProjectType;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.SkillsGapTrackLookUp;
import haj.com.entity.lookup.TrainingReportedAtrPtr;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * Wsp.
 * @author Techfinium
 */
@Entity
@Table(name = "wsp")
@AuditTable(value = "wsp_hist")
@Audited
public class Wsp implements IDataEntity {

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

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** The to date period. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submission_date", length = 19)
	private Date submissionDate;

	/** The create users. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "create_users_id", nullable = true)
	private Users createUsers;

	/** The from date period. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date_period", length = 19)
	private Date fromDatePeriod;

	/** The to date period. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date_period", length = 19)
	private Date toDatePeriod;

	/**
	 * The fin year. Length = 40 Field not required
	 */
	@Column(name = "fin_year", length = 40)
	private Integer finYear;

	/**
	 * The wsp guid. Length = 100 Field not required
	 */
	@Column(name = "wsp_guid", length = 100, nullable = true)
	@Size(min = 1, max = 100, message = "WSP guid can't be more than 100 characters")
	private String wspGuid;

	/** The wsp status enum. */
	@Enumerated
	@Column(name = "wsp_status_enum")
	private WspStatusEnum wspStatusEnum;

	@Enumerated
	@Column(name = "holding_room_status_enum")
	private HoldingRoomStatusEnum holdingRoomStatusEnum;

	/** The recognition agreement. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "recognition_agreement_id", nullable = true)
	private YesNoLookup recognitionAgreement;

	/** The recognition agreement. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "deviated_workplace_skills_plan", nullable = true)
	private YesNoLookup deviatedWorkplaceSkillsPlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "completed_training", nullable = true)
	private YesNoLookup completedTraining;

	/** The majority union. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "majority_union_id", nullable = true)
	private YesNoLookup majorityUnion;

	/** The wsp type. */
	@Enumerated
	@Column(name = "wsp_type")
	private WspTypeEnum wspType;

	/** The employment equity plan inline. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "employmentEquityPlanInline", nullable = true)
	private YesNoLookup employmentEquityPlanInline;

	/** The co funding partnership. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "co_funding_partnership", nullable = true)
	private YesNoLookup coFundingPartnership;

	/** The track of skills gaps. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "track_skills_gap", nullable = true)
	private YesNoLookup trackSkillsGap;

	/** The keep track of skills gaps selection. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "skills_gap_track_selection", nullable = true)
	private SkillsGapTrackLookUp skillsGapTrackSelection;

	/** Detail of track of skills gaps selection. */
	@Column(name = "skills_gap_track_selection_detail", length = 500)
	private String skillsGapTrackSelectionDetail;

	/** The recognition agreement. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_reported_atr_ptr_id", nullable = true)
	private TrainingReportedAtrPtr trainingReportedAtrPtr;

	@Column(name = "total_payroll", nullable = true)
	private Double totalPayroll;

	/**
	 * The total costs of the training Validations on the page
	 */
	@Column(name = "total_training_costs", nullable = true)
	private Double totalTrainingCosts;

	/**
	 * The Percentage of Payroll Spent on Training Validations on the page
	 */
	@Column(name = "percentage_payroll_spent", nullable = true)
	private Double percentagePayrollSpent;

	/**
	 * The Total Estimated cost of training as per previous year's WSP application Validations on the page
	 */
	@Column(name = "total_estimated_cost_training", nullable = true)
	private Double totalEstimatedCostTraining;

	/**
	 * The Percentage of Training Cost Spent on Training Validations on the page
	 */
	@Column(name = "percentage_training_cost_spent_training", nullable = true)
	private Double percentageTrainingCostSpentTraining;

	@Column(name = "require_sign_off_upload", columnDefinition = "BIT default false")
	private Boolean requireSignOffUpload;

	@Column(name = "require_project_administration_costs",  columnDefinition = "BIT default false",nullable = false)
	private Boolean requireProjectAdministrationCosts;

	@Column(name = "require_dispute_docs", columnDefinition = "BIT default false")
	private Boolean requireDisputeDocs;

	@Transient
	private ApprovalEnum dgVerificationStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;

	/**
	 * Used to track if the grant appeal process is with the SDF Defaults to false
	 */
	@Column(name = "with_sdf", columnDefinition = "BIT default 0")
	private Boolean withSdf;

	/**
	 * Used to track if the grant has been appealed by the SDF Defaults to false
	 */
	@Column(name = "sdf_appealed_grant", columnDefinition = "BIT default 0")
	private Boolean sdfAppealedGrant;

	/**
	 * Date Grant was appealed by the by the SDF
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sdf_appealed_grant_date", length = 19, nullable = true)
	private Date sdfAppealedGrantDate;

	/**
	 * if the grant was rejected by the manager
	 */
	@Column(name = "grant_rejected")
	private Boolean grantRejected;

	/**
	 * Date Grant was rejected by the manager
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "grant_rejected_date", length = 19, nullable = true)
	private Date grantRejectedDate;

	/**
	 * User put the status to awaiting MANCO Approval
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_requested_manco_approval", nullable = true)
	private Users userRequestedMancoApproval;

	/**
	 * Date tracker of when grant went submitted for MANCO Approval
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_manco_approval_required", length = 19, nullable = true)
	private Date dateMancoApprovalRequired;

	/**
	 * Used to track if application is awaiting MANCO Approval
	 */
	@Column(name = "manco_approval_required", columnDefinition = "BIT default 0")
	private Boolean mancoApprovalRequired;

	/**
	 * indicator is decision reached
	 */
	@Column(name = "manco_decision_reached", columnDefinition = "BIT default 0")
	private Boolean mancoDecisionReached;

	/**
	 * Final Response on decision regarding action taken on appeal
	 */
	@Column(name = "final_response", columnDefinition = "LONGTEXT")
	private String finalResponse;

	/**
	 * Indicator is the system auto approved / Rejected the application
	 */
	@Column(name = "system_approval_rejection", nullable = true)
	private Boolean systemApprovalRejection;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "dg_project_type_id", nullable = true)
	private DGProjectType dgProjectType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dg_termination_date", length = 19)
	private Date dgTerminationDate;

	/**
	 * Indicator if application was re-opened
	 */
	@Column(name = "application_reopened", nullable = true)
	private Boolean applicationReopened;

	/**
	 * Date application was re-opened
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apllicationReopenedDate", length = 19, nullable = true)
	private Date applicationReopenedDate;

	/**
	 * Indicator if application was re-opened by super admin user
	 */
	@Column(name = "admin_reopened", nullable = true)
	private Boolean adminReopened;

	/**
	 * User who re-opened application
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_reopened_application", nullable = true)
	private Users userReopenedApplication;

	/**
	 * Indicator where the grant was re-opened to Only admin will have the choice to select while staff can only reopen to themselves
	 */
	@Enumerated
	@Column(name = "reopened_to_location", nullable = true)
	private WspReopenLocationEnum reopenedToLocation;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "dg_year_id", nullable = true)
	private DGYear dgYear;

	@Column(name = "rejection_notified", columnDefinition = "BIT default 0")
	private Boolean rejectionNotified;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_owner", nullable = true)
	private Users projectOwner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_manager", nullable = true)
	private Users projectManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_contact", nullable = true)
	private CompanyUsers companyContact;

	/** The docs. */
	@Transient
	private List<Doc> docs;

	/** The wsp signoffs. */
	@Transient
	private List<WspSignoff> wspSignoffs;

	@Transient
	private List<ExtensionRequest> extensionRequests;

	/** The active sign off. */
	@Transient
	private WspSignoff activeSignOff;

	@Transient
	private RegionTown regionTown;

	@Transient
	private String applicationType;

	@Transient
	private BigDecimal percentageCalculatedForDeviation;

	@Transient
	private Double percentageCalculatedForDeviationDoubleValue;

	@Transient
	private Boolean grantDeviated;

	@Transient
	private Boolean verificationGeneratedForWsp;

	@Transient
	private String rejectionReasons;

	@Transient
	private String systemApprovalRejectionMessage;

	@Transient
	private String employeeUsers;

	@Transient
	private String clientUsers;

	@Transient
	private Users lastActionUser;

	@Transient
	private DgVerification dgVerification;

	@Transient
	private Boolean applyForExtension;

	// NEW FIELDS FOR PROJECT CHANGE REQUEST START
	@Column(name = "project_description", columnDefinition = "LONGTEXT")
	private String projectDescription;

	@Column(name = "purpose", columnDefinition = "LONGTEXT")
	private String purpose;

	@Column(name = "outcomes", columnDefinition = "LONGTEXT")
	private String outcomes;

	@Column(name = "location")
	private String location;

	@Column(name = "alignment_to_merseta_strategic_priorities")
	private String alignmentToMerSetaStrategicPriorities;

	@Column(name = "interventions")
	private String interventions;

	@Column(name = "benefits", columnDefinition = "LONGTEXT")
	private String benefits;

	@Column(name = "potential_risks", columnDefinition = "LONGTEXT")
	private String potentialRisks;

	@Column(name = "estimated_overall_project_cost")
	private Double estimatedOverallProjectCost;

	@Column(name = "approved_amount")
	private Double approvedAmount;
	// NEW FIELDS FOR PROJECT CHANGE REQUEST END
	
	// JIRA #530 - New columns added for manually addition of WSP 
	@Column(name = "manually_added", columnDefinition = "BIT default 0")
	private Boolean manuallyAdded;

	@Column(name = "manually_submission_date", length = 19, nullable = true)
	private Date manuallySubmissionDate;

	// CR #40 - New columns added 
	@Column(name = "number_of_employees")
	private Integer numberOfEmployees;

	// CR #40 - Submitted Date For Report 
	@Transient
	private String submittedDateForReport;

	@Column(name = "number_of_beneficiaries")
	private Integer numberOfBeneficiaries;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
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
		Wsp other = (Wsp) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new wsp.
	 */
	public Wsp() {
		super();
		this.wspGuid       = UUID.randomUUID().toString();
		this.finYear       = 0;
		this.wspStatusEnum = WspStatusEnum.Draft;
	}

	/**
	 * Gets the id.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * @param id
	 * the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 * @param createDate
	 * the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the company.
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 * @param company
	 * the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the from date period.
	 * @return the from date period
	 */
	public Date getFromDatePeriod() {
		return fromDatePeriod;
	}

	/**
	 * Sets the from date period.
	 * @param fromDatePeriod
	 * the new from date period
	 */
	public void setFromDatePeriod(Date fromDatePeriod) {
		this.fromDatePeriod = fromDatePeriod;
	}

	/**
	 * Gets the to date period.
	 * @return the to date period
	 */
	public Date getToDatePeriod() {
		return toDatePeriod;
	}

	/**
	 * Sets the to date period.
	 * @param toDatePeriod
	 * the new to date period
	 */
	public void setToDatePeriod(Date toDatePeriod) {
		this.toDatePeriod = toDatePeriod;
	}

	/**
	 * Gets the wsp guid.
	 * @return the wsp guid
	 */
	public String getWspGuid() {
		return wspGuid;
	}

	/**
	 * Sets the wsp guid.
	 * @param wspGuid
	 * the new wsp guid
	 */
	public void setWspGuid(String wspGuid) {
		this.wspGuid = wspGuid;
	}

	/**
	 * Gets the fin year.
	 * @return the fin year
	 */
	public Integer getFinYear() {
		return finYear;
	}

	@Transient
	public Integer getFinYearNonNull() {
		return finYear != null ? finYear : HAJConstants.DG_ALLOCATION_FOCUS_YEAR;
	}

	/**
	 * Sets the fin year.
	 * @param finYear
	 * the new fin year
	 */
	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	/**
	 * Gets the wsp status enum.
	 * @return the wsp status enum
	 */
	public WspStatusEnum getWspStatusEnum() {
		return wspStatusEnum;
	}

	/**
	 * Sets the wsp status enum.
	 * @param wspStatusEnum
	 * the new wsp status enum
	 */
	public void setWspStatusEnum(WspStatusEnum wspStatusEnum) {
		this.wspStatusEnum = wspStatusEnum;
	}

	/**
	 * Gets the docs.
	 * @return the docs
	 */
	public List<Doc> getDocs() {
		return docs;
	}

	/**
	 * Sets the docs.
	 * @param docs
	 * the new docs
	 */
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	/**
	 * Gets the creates the users.
	 * @return the creates the users
	 */
	public Users getCreateUsers() {
		return createUsers;
	}

	/**
	 * Sets the creates the users.
	 * @param createUsers
	 * the new creates the users
	 */
	public void setCreateUsers(Users createUsers) {
		this.createUsers = createUsers;
	}

	/**
	 * Gets the recognition agreement.
	 * @return the recognition agreement
	 */
	public YesNoLookup getRecognitionAgreement() {
		return recognitionAgreement;
	}

	/**
	 * Sets the recognition agreement.
	 * @param recognitionAgreement
	 * the new recognition agreement
	 */
	public void setRecognitionAgreement(YesNoLookup recognitionAgreement) {
		this.recognitionAgreement = recognitionAgreement;
	}

	/**
	 * Gets the majority union.
	 * @return the majority union
	 */
	public YesNoLookup getMajorityUnion() {
		return majorityUnion;
	}

	/**
	 * Sets the majority union.
	 * @param majorityUnion
	 * the new majority union
	 */
	public void setMajorityUnion(YesNoLookup majorityUnion) {
		this.majorityUnion = majorityUnion;
	}

	/**
	 * Gets the wsp signoffs.
	 * @return the wsp signoffs
	 */
	public List<WspSignoff> getWspSignoffs() {
		return wspSignoffs;
	}

	/**
	 * Sets the wsp signoffs.
	 * @param wspSignoffs
	 * the new wsp signoffs
	 */
	public void setWspSignoffs(List<WspSignoff> wspSignoffs) {
		this.wspSignoffs = wspSignoffs;
	}

	/**
	 * Gets the wsp type.
	 * @return the wsp type
	 */
	public WspTypeEnum getWspType() {
		return wspType;
	}

	/**
	 * Sets the wsp type.
	 * @param wspType
	 * the new wsp type
	 */
	public void setWspType(WspTypeEnum wspType) {
		this.wspType = wspType;
	}

	/**
	 * Gets the active sign off.
	 * @return the active sign off
	 */
	public WspSignoff getActiveSignOff() {
		return activeSignOff;
	}

	/**
	 * Sets the active sign off.
	 * @param activeSignOff
	 * the new active sign off
	 */
	public void setActiveSignOff(WspSignoff activeSignOff) {
		this.activeSignOff = activeSignOff;
	}

	/**
	 * Gets the employment equity plan inline.
	 * @return the employment equity plan inline
	 */
	public YesNoLookup getEmploymentEquityPlanInline() {
		return employmentEquityPlanInline;
	}

	/**
	 * Sets the employment equity plan inline.
	 * @param employmentEquityPlanInline
	 * the new employment equity plan inline
	 */
	public void setEmploymentEquityPlanInline(YesNoLookup employmentEquityPlanInline) {
		this.employmentEquityPlanInline = employmentEquityPlanInline;
	}

	/**
	 * Gets the co funding partnership.
	 * @return the co funding partnership
	 */
	public YesNoLookup getCoFundingPartnership() {
		return coFundingPartnership;
	}

	/**
	 * Sets the co funding partnership.
	 * @param coFundingPartnership
	 * the new co funding partnership
	 */
	public void setCoFundingPartnership(YesNoLookup coFundingPartnership) {
		this.coFundingPartnership = coFundingPartnership;
	}

	/**
	 * Gets The track of skills gaps.
	 * @return trackSkillsGap The track of skills gaps
	 */
	public YesNoLookup getTrackSkillsGap() {
		return trackSkillsGap;
	}

	/**
	 * Sets the track of skills gaps.
	 * @param the
	 * track of skills gaps.
	 */
	public void setTrackSkillsGap(YesNoLookup trackSkillsGap) {
		this.trackSkillsGap = trackSkillsGap;
	}

	/**
	 * Gets the track of skills gaps selection.
	 * @return the track of skills gaps selection.
	 */
	public SkillsGapTrackLookUp getSkillsGapTrackSelection() {
		return skillsGapTrackSelection;
	}

	/**
	 * Sets the track of skills gaps selection.
	 * @param the
	 * track of skills gaps selection.
	 */
	public void setSkillsGapTrackSelection(SkillsGapTrackLookUp skillsGapTrackSelection) {
		this.skillsGapTrackSelection = skillsGapTrackSelection;
	}

	public String getSkillsGapTrackSelectionDetail() {
		return skillsGapTrackSelectionDetail;
	}

	public void setSkillsGapTrackSelectionDetail(String skillsGapTrackSelectionDetail) {
		this.skillsGapTrackSelectionDetail = skillsGapTrackSelectionDetail;
	}

	public YesNoLookup getDeviatedWorkplaceSkillsPlan() {
		return deviatedWorkplaceSkillsPlan;
	}

	public void setDeviatedWorkplaceSkillsPlan(YesNoLookup deviatedWorkplaceSkillsPlan) {
		this.deviatedWorkplaceSkillsPlan = deviatedWorkplaceSkillsPlan;
	}

	public Double getTotalTrainingCosts() {
		return totalTrainingCosts;
	}

	public void setTotalTrainingCosts(Double totalTrainingCosts) {
		this.totalTrainingCosts = totalTrainingCosts;
	}

	public Double getPercentagePayrollSpent() {
		return percentagePayrollSpent;
	}

	public void setPercentagePayrollSpent(Double percentagePayrollSpent) {
		this.percentagePayrollSpent = percentagePayrollSpent;
	}

	public Double getTotalEstimatedCostTraining() {
		return totalEstimatedCostTraining;
	}

	public void setTotalEstimatedCostTraining(Double totalEstimatedCostTraining) {
		this.totalEstimatedCostTraining = totalEstimatedCostTraining;
	}

	public Double getPercentageTrainingCostSpentTraining() {
		return percentageTrainingCostSpentTraining;
	}

	public void setPercentageTrainingCostSpentTraining(Double percentageTrainingCostSpentTraining) {
		this.percentageTrainingCostSpentTraining = percentageTrainingCostSpentTraining;
	}

	public TrainingReportedAtrPtr getTrainingReportedAtrPtr() {
		return trainingReportedAtrPtr;
	}

	public void setTrainingReportedAtrPtr(TrainingReportedAtrPtr trainingReportedAtrPtr) {
		this.trainingReportedAtrPtr = trainingReportedAtrPtr;
	}

	public Boolean getRequireSignOffUpload() {
		return requireSignOffUpload;
	}

	public void setRequireSignOffUpload(Boolean requireSignOffUpload) {
		this.requireSignOffUpload = requireSignOffUpload;
	}

	public Boolean getRequireDisputeDocs() {
		return requireDisputeDocs;
	}

	public void setRequireDisputeDocs(Boolean requireDisputeDocs) {
		this.requireDisputeDocs = requireDisputeDocs;
	}

	public YesNoLookup getCompletedTraining() {
		return completedTraining;
	}

	public void setCompletedTraining(YesNoLookup completedTraining) {
		this.completedTraining = completedTraining;
	}

	public List<ExtensionRequest> getExtensionRequests() {
		return extensionRequests;
	}

	public void setExtensionRequests(List<ExtensionRequest> extensionRequests) {
		this.extensionRequests = extensionRequests;
	}

	public Double getTotalPayroll() {
		return totalPayroll;
	}

	public void setTotalPayroll(Double totalPayroll) {
		this.totalPayroll = totalPayroll;
	}

	public RegionTown getRegionTown() {
		return regionTown;
	}

	public void setRegionTown(RegionTown regionTown) {
		this.regionTown = regionTown;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public ApprovalEnum getDgVerificationStatus() {
		return dgVerificationStatus;
	}

	public void setDgVerificationStatus(ApprovalEnum dgVerificationStatus) {
		this.dgVerificationStatus = dgVerificationStatus;
	}

	public DGProjectType getDgProjectType() {
		return dgProjectType;
	}

	public void setDgProjectType(DGProjectType dgProjectType) {
		this.dgProjectType = dgProjectType;
	}

	public Date getDgTerminationDate() {
		return dgTerminationDate;
	}

	public void setDgTerminationDate(Date dgTerminationDate) {
		this.dgTerminationDate = dgTerminationDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Boolean getWithSdf() {
		return withSdf;
	}

	public void setWithSdf(Boolean withSdf) {
		this.withSdf = withSdf;
	}

	public Boolean getSdfAppealedGrant() {
		return sdfAppealedGrant;
	}

	public void setSdfAppealedGrant(Boolean sdfAppealedGrant) {
		this.sdfAppealedGrant = sdfAppealedGrant;
	}

	public Date getSdfAppealedGrantDate() {
		return sdfAppealedGrantDate;
	}

	public void setSdfAppealedGrantDate(Date sdfAppealedGrantDate) {
		this.sdfAppealedGrantDate = sdfAppealedGrantDate;
	}

	public Boolean getGrantRejected() {
		return grantRejected;
	}

	public void setGrantRejected(Boolean grantRejected) {
		this.grantRejected = grantRejected;
	}

	public Date getGrantRejectedDate() {
		return grantRejectedDate;
	}

	public void setGrantRejectedDate(Date grantRejectedDate) {
		this.grantRejectedDate = grantRejectedDate;
	}

	public String getFinalResponse() {
		return finalResponse;
	}

	public void setFinalResponse(String finalResponse) {
		this.finalResponse = finalResponse;
	}

	public BigDecimal getPercentageCalculatedForDeviation() {
		return percentageCalculatedForDeviation;
	}

	public void setPercentageCalculatedForDeviation(BigDecimal percentageCalculatedForDeviation) {
		this.percentageCalculatedForDeviation = percentageCalculatedForDeviation;
	}

	public Boolean getGrantDeviated() {
		return grantDeviated;
	}

	public void setGrantDeviated(Boolean grantDeviated) {
		this.grantDeviated = grantDeviated;
	}

	public Boolean getVerificationGeneratedForWsp() {
		return verificationGeneratedForWsp;
	}

	public void setVerificationGeneratedForWsp(Boolean verificationGeneratedForWsp) {
		this.verificationGeneratedForWsp = verificationGeneratedForWsp;
	}

	public Double getPercentageCalculatedForDeviationDoubleValue() {
		return percentageCalculatedForDeviationDoubleValue;
	}

	public void setPercentageCalculatedForDeviationDoubleValue(Double percentageCalculatedForDeviationDoubleValue) {
		this.percentageCalculatedForDeviationDoubleValue = percentageCalculatedForDeviationDoubleValue;
	}

	public Boolean getSystemApprovalRejection() {
		return systemApprovalRejection;
	}

	public void setSystemApprovalRejection(Boolean systemApprovalRejection) {
		this.systemApprovalRejection = systemApprovalRejection;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public String getEmployeeUsers() {
		return employeeUsers;
	}

	public void setEmployeeUsers(String employeeUsers) {
		this.employeeUsers = employeeUsers;
	}

	public String getClientUsers() {
		return clientUsers;
	}

	public void setClientUsers(String clientUsers) {
		this.clientUsers = clientUsers;
	}

	public String getSystemApprovalRejectionMessage() {
		return systemApprovalRejectionMessage;
	}

	public void setSystemApprovalRejectionMessage(String systemApprovalRejectionMessage) {
		this.systemApprovalRejectionMessage = systemApprovalRejectionMessage;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public Boolean getApplicationReopened() {
		return applicationReopened;
	}

	public void setApplicationReopened(Boolean applicationReopened) {
		this.applicationReopened = applicationReopened;
	}

	public Boolean getAdminReopened() {
		return adminReopened;
	}

	public void setAdminReopened(Boolean adminReopened) {
		this.adminReopened = adminReopened;
	}

	public Users getUserReopenedApplication() {
		return userReopenedApplication;
	}

	public void setUserReopenedApplication(Users userReopenedApplication) {
		this.userReopenedApplication = userReopenedApplication;
	}

	public Date getApplicationReopenedDate() {
		return applicationReopenedDate;
	}

	public void setApplicationReopenedDate(Date applicationReopenedDate) {
		this.applicationReopenedDate = applicationReopenedDate;
	}

	public WspReopenLocationEnum getReopenedToLocation() {
		return reopenedToLocation;
	}

	public void setReopenedToLocation(WspReopenLocationEnum reopenedToLocation) {
		this.reopenedToLocation = reopenedToLocation;
	}

	public Users getUserRequestedMancoApproval() {
		return userRequestedMancoApproval;
	}

	public void setUserRequestedMancoApproval(Users userRequestedMancoApproval) {
		this.userRequestedMancoApproval = userRequestedMancoApproval;
	}

	public Date getDateMancoApprovalRequired() {
		return dateMancoApprovalRequired;
	}

	public void setDateMancoApprovalRequired(Date dateMancoApprovalRequired) {
		this.dateMancoApprovalRequired = dateMancoApprovalRequired;
	}

	public Boolean getMancoApprovalRequired() {
		return mancoApprovalRequired;
	}

	public void setMancoApprovalRequired(Boolean mancoApprovalRequired) {
		this.mancoApprovalRequired = mancoApprovalRequired;
	}

	public Boolean getMancoDecisionReached() {
		return mancoDecisionReached;
	}

	public void setMancoDecisionReached(Boolean mancoDecisionReached) {
		this.mancoDecisionReached = mancoDecisionReached;
	}

	public Boolean getApplyForExtension() {
		return applyForExtension;
	}

	public void setApplyForExtension(Boolean applyForExtension) {
		this.applyForExtension = applyForExtension;
	}

	public DGYear getDgYear() {
		return dgYear;
	}

	public void setDgYear(DGYear dgYear) {
		this.dgYear = dgYear;
	}

	public Boolean getRejectionNotified() {
		return rejectionNotified;
	}

	public void setRejectionNotified(Boolean rejectionNotified) {
		this.rejectionNotified = rejectionNotified;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(String outcomes) {
		this.outcomes = outcomes;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAlignmentToMerSetaStrategicPriorities() {
		return alignmentToMerSetaStrategicPriorities;
	}

	public void setAlignmentToMerSetaStrategicPriorities(String alignmentToMerSetaStrategicPriorities) {
		this.alignmentToMerSetaStrategicPriorities = alignmentToMerSetaStrategicPriorities;
	}

	public String getInterventions() {
		return interventions;
	}

	public void setInterventions(String interventions) {
		this.interventions = interventions;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	public String getPotentialRisks() {
		return potentialRisks;
	}

	public void setPotentialRisks(String potentialRisks) {
		this.potentialRisks = potentialRisks;
	}

	public Double getEstimatedOverallProjectCost() {
		return estimatedOverallProjectCost;
	}

	public void setEstimatedOverallProjectCost(Double estimatedOverallProjectCost) {
		this.estimatedOverallProjectCost = estimatedOverallProjectCost;
	}

	public HoldingRoomStatusEnum getHoldingRoomStatusEnum() {
		return holdingRoomStatusEnum;
	}

	public void setHoldingRoomStatusEnum(HoldingRoomStatusEnum holdingRoomStatusEnum) {
		this.holdingRoomStatusEnum = holdingRoomStatusEnum;
	}

	public Users getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(Users projectOwner) {
		this.projectOwner = projectOwner;
	}

	public Users getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Users projectManager) {
		this.projectManager = projectManager;
	}

	public CompanyUsers getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(CompanyUsers companyContact) {
		this.companyContact = companyContact;
	}

	public Double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	/**
	 * Added for JIRA #530
	 * Gets the manuallyAdded attribute.
	 * @return the manuallyAdded
	 */
	public Boolean getManuallyAdded() {
		return manuallyAdded;
	}

	/**
	 * Sets the manuallyAdded.
	 * Added for JIRA #530
	 * @param manuallyAdded
	 * the manuallyAdded
	 */
	public void setManuallyAdded(Boolean manuallyAdded) {
		this.manuallyAdded = manuallyAdded;
	}

	/**
	 * Added for JIRA #530
	 * Gets the manuallySubmissionDate.
	 * @return the manuallySubmissionDate
	 */
	public Date getManuallySubmissionDate() {
		return manuallySubmissionDate;
	}

	/**
	 * Added for JIRA #530
	 * Sets the manuallySubmissionDate.
	 * @param manuallySubmissionDate
	 * the manuallySubmissionDate
	 */
	public void setManuallySubmissionDate(Date manuallySubmissionDate) {
		this.manuallySubmissionDate = manuallySubmissionDate;
	}

	/**
	 * Added for CR #40
	 * Gets the numberOfEmployees.
	 * @return the numberOfEmployees
	 */
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	/**
	 * Added for CR #40
	 * Sets the numberOfEmployees.
	 * @param numberOfEmployees
	 * the numberOfEmployees
	 */
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	/**
	 * Added for CR #40
	 * Gets the submittedDateForReport.
	 * @return the submittedDateForReport
	 */
	public String getSubmittedDateForReport() {
		return submittedDateForReport;
	}

	/**
	 * Added for CR #40
	 * Sets the submittedDateForReport.
	 * @param submittedDateForReport
	 * the submittedDateForReport
	 */
	public void setSubmittedDateForReport(String submittedDateForReport) {
		this.submittedDateForReport = submittedDateForReport;
	}


	public Boolean getRequireProjectAdministrationCosts() {
		return requireProjectAdministrationCosts;
	}

	public void setRequireProjectAdministrationCosts(Boolean requireProjectAdministrationCosts) {
		this.requireProjectAdministrationCosts = requireProjectAdministrationCosts;
	}

	public Integer getNumberOfBeneficiaries(){
		return numberOfBeneficiaries;
	}

	public void setNumberOfBeneficiaries(Integer numberOfBeneficiaries){
		this.numberOfBeneficiaries = numberOfBeneficiaries;
	}
}
