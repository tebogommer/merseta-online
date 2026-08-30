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
import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.ArplReportingEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Seta;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.AssessorModeratorApplicaitonValidationService;
import haj.com.validators.exports.services.UserValidationService;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsers.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learners_trade_test")
@AuditTable(value = "company_learners_trade_test_hist")
@Audited
public class CompanyLearnersTradeTest implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preferred_training_center_id", nullable = true)
	private Company preferredTrainingCenter;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	@Enumerated
	@Column(name = "employer_on_nsdms")
	private YesNoEnum employerOnNsdms;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empoyer_id", nullable = true)
	private CompanyTradeTestEmployer employer;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_employer", nullable = true)
	private Company companyEmployer;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_provider_id", nullable = true)
	private Company companyProvider;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_id", nullable = true)
	private Users learner;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_sect_twenty_eight_id", nullable = true)
	private LegacySECTTwentyEight legacysecttwentyeight;

	@Enumerated
	@Column(name = "flc")
	private YesNoEnum flc;
	
	@Enumerated
	@Column(name = "flc_english")
	private YesNoEnum flcEnglish;
	
	@Enumerated
	@Column(name = "flc_maths_lit")
	private YesNoEnum flcMathsLit;

	@Enumerated
	@Column(name = "attempted_trade_test")
	private YesNoEnum attemptedTradeTest;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_training_center_id", nullable = true)
	private Company previousTrainingCenter;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "learner_readiness_date", length = 19)
	private Date learnerReadinessDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "min_date", length = 19, nullable = true)
	private Date minDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "max_date", length = 19, nullable = true)
	private Date maxDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "namb_submission_date", length = 19)
	private Date nambSubmissionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "namb_received_date", length = 19)
	private Date nambReceivedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "previous_attempt_date", length = 19)
	private Date previousAttemptDate;

	@Column(name = "attempt_number")
	private Integer attemptNumber;

	@Column(name = "qualification_code", columnDefinition = "LONGTEXT")
	private String qualificationCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approved_user_id", nullable = true)
	private Users approvedUser;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Enumerated
	@Column(name = "namb_decision")
	private ApprovalEnum nambDecision;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_set_to_awaiting_namb", length = 19)
	private Date dateSetToAwaitingNamb;

	@Enumerated
	@Column(name = "competence_enum")
	private CompetenceEnum competenceEnum;

	/** TrainingProviderApplication AccreditationApplicationType */
	@Enumerated
	@Column(name = "collection_type")
	private CollectionEnum collectionEnum;
	
	@Column(name = "collection_information")
	private String collectionInformation;

	@Column(name = "serial_number", length = 30)
	private String serialNumber;

	@Column(name = "specialisation")
	private String specialisation;

	@Column(name = "details_of_experience")
	private String detailsOfExperience;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "region")
	private String region;
	
	@Enumerated
	@Column(name = "learner_agreement")
	private YesNoEnum learnerAgreement;
	
	@Column(name = "learner_agreement_number")
	private String learnerAgreementNumber;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "relevent_seta_id", nullable = true)
	private Seta releventSeta;
	
	@Enumerated
	@Column(name = "medical_info")
	private YesNoEnum medicalInfo;
	
	@Column(name = "nature_medical_info", columnDefinition = "LONGTEXT")
	private String natureMedicalInfo;
	
	@Enumerated
	@Column(name = "disability")
	private YesNoEnum disability;
	
	// not required anymore
	@Column(name = "disability_info", columnDefinition = "LONGTEXT")
	private String disabilityInfo;
	
	@Enumerated
	@Column(name = "employed_un_employed")
	private EmployedUnEmployedEnum employedUnEmployed;
	
	@Column(name = "current_employer_address", columnDefinition = "LONGTEXT")
	private String currentEmployerAddress;
	
	@Column(name = "current_occ", columnDefinition = "LONGTEXT")
	private String currentOcc;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "occupation_category_id", nullable = true)
	private OccupationCategory occupationCategory;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_code_id", nullable = true)
	private OfoCodes ofoCode;

	@Enumerated
	@Column(name = "arpl_reporting")
	private ArplReportingEnum arplReporting;
	
	@Enumerated
	@Column(name = "trade_test_type")
	private TradeTestTypeEnum tradeTestType;

	@Enumerated
	@Column(name = "aprl_progress_enum")
	private AprlProgressEnum aprlProgress;
	
	@Enumerated
	@Column(name = "trade_test_progress")
	private TradeTestProgressEnum tradeTestProgress;
	
	@Column(name = "alter_employer_info")
	private Boolean alterEmployerInfo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_submitted_to_test_center", length = 19)
	private Date dateSubmittedToTestCenter;
	
	@Column(name = "test_dates_provided")
	private Boolean testDatesProvided;
	
	@Column(name = "test_center_submitted")
	private Boolean testCenterSubmitted;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	// should be labeled designated qualification
	@Column(name = "cbmt_qualification")
	private Boolean cbmtQualification;
	
	// should be labeled final designated qualification
	@Column(name = "final_cbmt_qualification")
	private Boolean finalCbmtQualification;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designated_trade_level_id", nullable = true)
	private DesignatedTradeLevel designatedTradeLevel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_test", length = 19)
	private Date dateOfTest;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_test_to_date", length = 19)
	private Date dateOfTestToDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_from_date_of_test", length = 19)
	private Date actualFromDateOfTest;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_to_date_of_test", length = 19)
	private Date actualToDateOfTest;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collect_detail_id", nullable = true)
	private CollectDetail collectDetail;
	
	@Enumerated
	@Column(name = "employment_status")
	private EmploymentStatusEnum employmentStatus;

	@Transient
	private List<Doc> docs;
	
	@Transient
	private Boolean emailSupport;
	
	@Column(name = "results_verified")
	private Boolean resultsVerified;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_to_provide_original_copies", length = 19)
	private Date dateToProvideOriginalCopies;
	
	/* Employee task users ID */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_service_admin_user_id", nullable = true)
	private Users clientServiceAdminUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quality_assuror_user_id", nullable = true)
	private Users qualityAssurorUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coordinator_user_id", nullable = true)
	private Users coordinatorUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "test_center_user_id", nullable = true)
	private Users testCenterUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_user_id", nullable = true)
	private Users adminUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_submission_original_doc", length = 19)
	private Date dateSubmissionOriginalDoc;
	
	// Deadline date for original doc hand-in
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "due_date_submission_original_doc", length = 19)
	private Date dueDateSubmissionOriginalDoc;
	
	@Column(name = "on_hold" , columnDefinition = "BIT default false")
	private Boolean onHold;
	
	/** The disability status. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disabilityStatus")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	private DisabilityStatus disabilityStatus;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name="disability_rating_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private DisabilityRating disabilityRating;
	
	/* ASSESSOR INFORMATION START */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_application_id", nullable = true)
	private AssessorModeratorApplication assessorApplication;

	/** The first name. */
	@Column(name = "assessor_first_name", length = 100, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateName", paramClass = String.class, message = "Validation Failed For SETMIS Assessor First Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "assessorFirstName", fieldValue = "NOT_NULL")
	private String assessorFirstName;

	/** The last name. */
	@Column(name = "assessor_last_name", length = 100, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateName", paramClass = String.class, message = "Validation Failed For SETMIS Assessor Last Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "assessorLastName", fieldValue = "NOT_NULL")
	private String assessorLastName;
	
	/** The email. */
	@Column(name = "assessor_email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address For Assessor")
	private String assessorEmail;
	
	@Column(name = "assessor_certificate_number", nullable = true)
	@SETMISFieldValidation(className = AssessorModeratorApplicaitonValidationService.class, method = "validateCertificateNumber", paramClass = String.class, message = "Validation Failed For SETMIS Assessor Registration Number<ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Value in field may only contain the characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>", fieldName = "assessorCertificateNumber", fieldValue = "NOT_NULL")
	private String assessorCertificateNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "assessor_etqa_id", nullable = true)
	private Etqa assessorEtqa;
	
	/* ASSESSOR INFORMATION END */
	
	/* moderator INFORMATION START */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moderator_application_id", nullable = true)
	private AssessorModeratorApplication moderatorApplication;
	
	/** The first name. */
	@Column(name = "moderator_first_name", length = 100, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateName", paramClass = String.class, message = "Validation Failed For SETMIS Moderator First Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "moderatorFirstName", fieldValue = "NOT_NULL")
	private String moderatorFirstName;

	/** The last name. */
	@Column(name = "moderator_last_name", length = 100, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateName", paramClass = String.class, message = "Validation Failed For SETMIS Moderator Last Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "moderatorLastName", fieldValue = "NOT_NULL")
	private String moderatorLastName;
	
	/** The email. */
	@Column(name = "moderator_email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address For Moderator")
	private String moderatorEmail;
	
	@Column(name = "moderator_certificate_number", nullable = true)
	@SETMISFieldValidation(className = AssessorModeratorApplicaitonValidationService.class, method = "validateCertificateNumber", paramClass = String.class, message = "Validation Failed For SETMIS Moderator Registration Number<ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Value in field may only contain the characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>", fieldName = "moderatorCertificateNumber", fieldValue = "NOT_NULL")
	private String moderatorCertificateNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "moderator_etqa_id", nullable = true)
	private Etqa moderatorEtqa;
	
	/* moderator INFORMATION End */
	@Column(name = "certificate_number", length = 30)
	private String certificateNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_certified", length = 19)
	private Date dateCertified;
	
	@Column(name = "certificate_collected")
	private Boolean certificateCollected;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_collection", length = 19)
	private Date dateOfCollection;
	
	
	@Transient
	private List<UsersDisability> usersDisabilityList;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyLearnersTradeTest other = (CompanyLearnersTradeTest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Company getPreferredTrainingCenter() {
		return preferredTrainingCenter;
	}

	public void setPreferredTrainingCenter(Company preferredTrainingCenter) {
		this.preferredTrainingCenter = preferredTrainingCenter;
	}

	public YesNoEnum getFlc() {
		return flc;
	}

	public void setFlc(YesNoEnum flc) {
		this.flc = flc;
	}

	public YesNoEnum getAttemptedTradeTest() {
		return attemptedTradeTest;
	}

	public void setAttemptedTradeTest(YesNoEnum attemptedTradeTest) {
		this.attemptedTradeTest = attemptedTradeTest;
	}

	public Company getPreviousTrainingCenter() {
		return previousTrainingCenter;
	}

	public void setPreviousTrainingCenter(Company previousTrainingCenter) {
		this.previousTrainingCenter = previousTrainingCenter;
	}

	public Date getPreviousAttemptDate() {
		return previousAttemptDate;
	}

	public void setPreviousAttemptDate(Date previousAttemptDate) {
		this.previousAttemptDate = previousAttemptDate;
	}

	public Integer getAttemptNumber() {
		return attemptNumber;
	}

	public void setAttemptNumber(Integer attemptNumber) {
		this.attemptNumber = attemptNumber;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public Date getLearnerReadinessDate() {
		return learnerReadinessDate;
	}

	public void setLearnerReadinessDate(Date learnerReadinessDate) {
		this.learnerReadinessDate = learnerReadinessDate;
	}

	public CompetenceEnum getCompetenceEnum() {
		return competenceEnum;
	}

	public void setCompetenceEnum(CompetenceEnum competenceEnum) {
		this.competenceEnum = competenceEnum;
	}

	public Date getNambSubmissionDate() {
		return nambSubmissionDate;
	}

	public void setNambSubmissionDate(Date nambSubmissionDate) {
		this.nambSubmissionDate = nambSubmissionDate;
	}

	public CollectionEnum getCollectionEnum() {
		return collectionEnum;
	}

	public void setCollectionEnum(CollectionEnum collectionEnum) {
		this.collectionEnum = collectionEnum;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}

	public String getDetailsOfExperience() {
		return detailsOfExperience;
	}

	public void setDetailsOfExperience(String detailsOfExperience) {
		this.detailsOfExperience = detailsOfExperience;
	}

	public TradeTestTypeEnum getTradeTestType() {
		return tradeTestType;
	}

	public void setTradeTestType(TradeTestTypeEnum tradeTestType) {
		this.tradeTestType = tradeTestType;
	}

	public AprlProgressEnum getAprlProgress() {
		return aprlProgress;
	}

	public void setAprlProgress(AprlProgressEnum aprlProgress) {
		this.aprlProgress = aprlProgress;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Users getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(Users approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Users getLearner() {
		return learner;
	}

	public void setLearner(Users learner) {
		this.learner = learner;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Boolean getTestCenterSubmitted() {
		return testCenterSubmitted;
	}

	public void setTestCenterSubmitted(Boolean testCenterSubmitted) {
		this.testCenterSubmitted = testCenterSubmitted;
	}

	public ArplReportingEnum getArplReporting() {
		return arplReporting;
	}

	public void setArplReporting(ArplReportingEnum arplReporting) {
		this.arplReporting = arplReporting;
	}

	public EmployedUnEmployedEnum getEmployedUnEmployed() {
		return employedUnEmployed;
	}

	public void setEmployedUnEmployed(EmployedUnEmployedEnum employedUnEmployed) {
		this.employedUnEmployed = employedUnEmployed;
	}

	public OccupationCategory getOccupationCategory() {
		return occupationCategory;
	}

	public void setOccupationCategory(OccupationCategory occupationCategory) {
		this.occupationCategory = occupationCategory;
	}

	public OfoCodes getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(OfoCodes ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getCurrentEmployerAddress() {
		return currentEmployerAddress;
	}

	public void setCurrentEmployerAddress(String currentEmployerAddress) {
		this.currentEmployerAddress = currentEmployerAddress;
	}

	public Seta getReleventSeta() {
		return releventSeta;
	}

	public void setReleventSeta(Seta releventSeta) {
		this.releventSeta = releventSeta;
	}

	public YesNoEnum getLearnerAgreement() {
		return learnerAgreement;
	}

	public void setLearnerAgreement(YesNoEnum learnerAgreement) {
		this.learnerAgreement = learnerAgreement;
	}

	public String getLearnerAgreementNumber() {
		return learnerAgreementNumber;
	}

	public void setLearnerAgreementNumber(String learnerAgreementNumber) {
		this.learnerAgreementNumber = learnerAgreementNumber;
	}

	public YesNoEnum getMedicalInfo() {
		return medicalInfo;
	}

	public void setMedicalInfo(YesNoEnum medicalInfo) {
		this.medicalInfo = medicalInfo;
	}

	public String getNatureMedicalInfo() {
		return natureMedicalInfo;
	}

	public void setNatureMedicalInfo(String natureMedicalInfo) {
		this.natureMedicalInfo = natureMedicalInfo;
	}

	public String getCurrentOcc() {
		return currentOcc;
	}

	public void setCurrentOcc(String currentOcc) {
		this.currentOcc = currentOcc;
	}

	public Date getDateOfTest() {
		return dateOfTest;
	}

	public void setDateOfTest(Date dateOfTest) {
		this.dateOfTest = dateOfTest;
	}

	public String getCollectionInformation() {
		return collectionInformation;
	}

	public void setCollectionInformation(String collectionInformation) {
		this.collectionInformation = collectionInformation;
	}

	public Boolean getCbmtQualification() {
		return cbmtQualification;
	}

	public void setCbmtQualification(Boolean cbmtQualification) {
		this.cbmtQualification = cbmtQualification;
	}

	public Boolean getFinalCbmtQualification() {
		return finalCbmtQualification;
	}

	public void setFinalCbmtQualification(Boolean finalCbmtQualification) {
		this.finalCbmtQualification = finalCbmtQualification;
	}

	public DesignatedTradeLevel getDesignatedTradeLevel() {
		return designatedTradeLevel;
	}

	public void setDesignatedTradeLevel(DesignatedTradeLevel designatedTradeLevel) {
		this.designatedTradeLevel = designatedTradeLevel;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public TradeTestProgressEnum getTradeTestProgress() {
		return tradeTestProgress;
	}

	public void setTradeTestProgress(TradeTestProgressEnum tradeTestProgress) {
		this.tradeTestProgress = tradeTestProgress;
	}

	public Boolean getEmailSupport() {
		return emailSupport;
	}

	public void setEmailSupport(Boolean emailSupport) {
		this.emailSupport = emailSupport;
	}

	public CompanyTradeTestEmployer getEmployer() {
		return employer;
	}

	public void setEmployer(CompanyTradeTestEmployer employer) {
		this.employer = employer;
	}

	public Boolean getResultsVerified() {
		return resultsVerified;
	}

	public void setResultsVerified(Boolean resultsVerified) {
		this.resultsVerified = resultsVerified;
	}

	public CollectDetail getCollectDetail() {
		return collectDetail;
	}

	public void setCollectDetail(CollectDetail collectDetail) {
		this.collectDetail = collectDetail;
	}

	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public YesNoEnum getFlcEnglish() {
		return flcEnglish;
	}

	public void setFlcEnglish(YesNoEnum flcEnglish) {
		this.flcEnglish = flcEnglish;
	}

	public YesNoEnum getFlcMathsLit() {
		return flcMathsLit;
	}

	public void setFlcMathsLit(YesNoEnum flcMathsLit) {
		this.flcMathsLit = flcMathsLit;
	}

	public YesNoEnum getEmployerOnNsdms() {
		return employerOnNsdms;
	}

	public void setEmployerOnNsdms(YesNoEnum employerOnNsdms) {
		this.employerOnNsdms = employerOnNsdms;
	}

	public Company getCompanyEmployer() {
		return companyEmployer;
	}

	public void setCompanyEmployer(Company companyEmployer) {
		this.companyEmployer = companyEmployer;
	}

	public YesNoEnum getDisability() {
		return disability;
	}

	public void setDisability(YesNoEnum disability) {
		this.disability = disability;
	}

	public String getDisabilityInfo() {
		return disabilityInfo;
	}

	public void setDisabilityInfo(String disabilityInfo) {
		this.disabilityInfo = disabilityInfo;
	}

	public Boolean getAlterEmployerInfo() {
		return alterEmployerInfo;
	}

	public void setAlterEmployerInfo(Boolean alterEmployerInfo) {
		this.alterEmployerInfo = alterEmployerInfo;
	}

	public Users getClientServiceAdminUser() {
		return clientServiceAdminUser;
	}

	public void setClientServiceAdminUser(Users clientServiceAdminUser) {
		this.clientServiceAdminUser = clientServiceAdminUser;
	}

	public Users getQualityAssurorUser() {
		return qualityAssurorUser;
	}

	public void setQualityAssurorUser(Users qualityAssurorUser) {
		this.qualityAssurorUser = qualityAssurorUser;
	}

	public Users getCoordinatorUser() {
		return coordinatorUser;
	}

	public void setCoordinatorUser(Users coordinatorUser) {
		this.coordinatorUser = coordinatorUser;
	}

	public Date getDateToProvideOriginalCopies() {
		return dateToProvideOriginalCopies;
	}

	public void setDateToProvideOriginalCopies(Date dateToProvideOriginalCopies) {
		this.dateToProvideOriginalCopies = dateToProvideOriginalCopies;
	}

	public Boolean getTestDatesProvided() {
		return testDatesProvided;
	}

	public void setTestDatesProvided(Boolean testDatesProvided) {
		this.testDatesProvided = testDatesProvided;
	}

	public Date getDateSubmittedToTestCenter() {
		return dateSubmittedToTestCenter;
	}

	public void setDateSubmittedToTestCenter(Date dateSubmittedToTestCenter) {
		this.dateSubmittedToTestCenter = dateSubmittedToTestCenter;
	}

	public Date getDateOfTestToDate() {
		return dateOfTestToDate;
	}

	public void setDateOfTestToDate(Date dateOfTestToDate) {
		this.dateOfTestToDate = dateOfTestToDate;
	}

	public Date getActualFromDateOfTest() {
		return actualFromDateOfTest;
	}

	public void setActualFromDateOfTest(Date actualFromDateOfTest) {
		this.actualFromDateOfTest = actualFromDateOfTest;
	}

	public Date getActualToDateOfTest() {
		return actualToDateOfTest;
	}

	public void setActualToDateOfTest(Date actualToDateOfTest) {
		this.actualToDateOfTest = actualToDateOfTest;
	}

	public Users getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(Users adminUser) {
		this.adminUser = adminUser;
	}

	public Date getDateSetToAwaitingNamb() {
		return dateSetToAwaitingNamb;
	}

	public void setDateSetToAwaitingNamb(Date dateSetToAwaitingNamb) {
		this.dateSetToAwaitingNamb = dateSetToAwaitingNamb;
	}

	public Users getTestCenterUser() {
		return testCenterUser;
	}

	public void setTestCenterUser(Users testCenterUser) {
		this.testCenterUser = testCenterUser;
	}

	public ApprovalEnum getNambDecision() {
		return nambDecision;
	}

	public void setNambDecision(ApprovalEnum nambDecision) {
		this.nambDecision = nambDecision;
	}

	public Date getDateSubmissionOriginalDoc() {
		return dateSubmissionOriginalDoc;
	}

	public void setDateSubmissionOriginalDoc(Date dateSubmissionOriginalDoc) {
		this.dateSubmissionOriginalDoc = dateSubmissionOriginalDoc;
	}

	public Date getDueDateSubmissionOriginalDoc() {
		return dueDateSubmissionOriginalDoc;
	}

	public void setDueDateSubmissionOriginalDoc(Date dueDateSubmissionOriginalDoc) {
		this.dueDateSubmissionOriginalDoc = dueDateSubmissionOriginalDoc;
	}

	public Boolean getOnHold() {
		return onHold;
	}

	public void setOnHold(Boolean onHold) {
		this.onHold = onHold;
	}

	public List<UsersDisability> getUsersDisabilityList() {
		return usersDisabilityList;
	}

	public void setUsersDisabilityList(List<UsersDisability> usersDisabilityList) {
		this.usersDisabilityList = usersDisabilityList;
	}

	public DisabilityStatus getDisabilityStatus() {
		return disabilityStatus;
	}

	public void setDisabilityStatus(DisabilityStatus disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	public DisabilityRating getDisabilityRating() {
		return disabilityRating;
	}

	public void setDisabilityRating(DisabilityRating disabilityRating) {
		this.disabilityRating = disabilityRating;
	}

	public Company getCompanyProvider() {
		return companyProvider;
	}

	public void setCompanyProvider(Company companyProvider) {
		this.companyProvider = companyProvider;
	}

	public String getAssessorFirstName() {
		return assessorFirstName;
	}

	public void setAssessorFirstName(String assessorFirstName) {
		this.assessorFirstName = assessorFirstName;
	}

	public String getAssessorLastName() {
		return assessorLastName;
	}

	public void setAssessorLastName(String assessorLastName) {
		this.assessorLastName = assessorLastName;
	}

	public String getAssessorEmail() {
		return assessorEmail;
	}

	public void setAssessorEmail(String assessorEmail) {
		this.assessorEmail = assessorEmail;
	}

	public String getAssessorCertificateNumber() {
		return assessorCertificateNumber;
	}

	public void setAssessorCertificateNumber(String assessorCertificateNumber) {
		this.assessorCertificateNumber = assessorCertificateNumber;
	}

	public Etqa getAssessorEtqa() {
		return assessorEtqa;
	}

	public void setAssessorEtqa(Etqa assessorEtqa) {
		this.assessorEtqa = assessorEtqa;
	}

	public String getModeratorFirstName() {
		return moderatorFirstName;
	}

	public void setModeratorFirstName(String moderatorFirstName) {
		this.moderatorFirstName = moderatorFirstName;
	}

	public String getModeratorLastName() {
		return moderatorLastName;
	}

	public void setModeratorLastName(String moderatorLastName) {
		this.moderatorLastName = moderatorLastName;
	}

	public String getModeratorEmail() {
		return moderatorEmail;
	}

	public void setModeratorEmail(String moderatorEmail) {
		this.moderatorEmail = moderatorEmail;
	}

	public String getModeratorCertificateNumber() {
		return moderatorCertificateNumber;
	}

	public void setModeratorCertificateNumber(String moderatorCertificateNumber) {
		this.moderatorCertificateNumber = moderatorCertificateNumber;
	}

	public Etqa getModeratorEtqa() {
		return moderatorEtqa;
	}

	public void setModeratorEtqa(Etqa moderatorEtqa) {
		this.moderatorEtqa = moderatorEtqa;
	}

	public Boolean getCertificateCollected() {
		return certificateCollected;
	}

	public void setCertificateCollected(Boolean certificateCollected) {
		this.certificateCollected = certificateCollected;
	}

	public Date getDateOfCollection() {
		return dateOfCollection;
	}

	public void setDateOfCollection(Date dateOfCollection) {
		this.dateOfCollection = dateOfCollection;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public AssessorModeratorApplication getModeratorApplication() {
		return moderatorApplication;
	}

	public void setModeratorApplication(AssessorModeratorApplication moderatorApplication) {
		this.moderatorApplication = moderatorApplication;
	}

	public AssessorModeratorApplication getAssessorApplication() {
		return assessorApplication;
	}

	public void setAssessorApplication(AssessorModeratorApplication assessorApplication) {
		this.assessorApplication = assessorApplication;
	}

	public Date getNambReceivedDate() {
		return nambReceivedDate;
	}

	public void setNambReceivedDate(Date nambReceivedDate) {
		this.nambReceivedDate = nambReceivedDate;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public Date getDateCertified() {
		return dateCertified;
	}

	public void setDateCertified(Date dateCertified) {
		this.dateCertified = dateCertified;
	}

	public LegacySECTTwentyEight getLegacysecttwentyeight() {
		return legacysecttwentyeight;
	}

	public void setLegacysecttwentyeight(LegacySECTTwentyEight legacysecttwentyeight) {
		this.legacysecttwentyeight = legacysecttwentyeight;
	}
}