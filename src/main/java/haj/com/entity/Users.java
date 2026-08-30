package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import haj.com.bean.UserUpdateInfoBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.RsaCitizenTypeEnum;
import haj.com.entity.enums.UrbanRuralEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.Language;
import haj.com.entity.lookup.MaritalStatus;
import haj.com.entity.lookup.Nationality;
import haj.com.entity.lookup.Office;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.PreviousSchools;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Title;
import haj.com.entity.lookup.Town;
import haj.com.entity.lookup.UnionMembership;
import haj.com.framework.IDataEntity;
import haj.com.validators.CheckID;
import haj.com.validators.CheckTelNumber;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.UserValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class Users.
 */
@Entity
@Table(name = "users")

@DynamicInsert(value = true)
@DynamicUpdate(value = true)
@AuditTable(value = "users_hist")
@Audited
@ExportValidation(message = "Invalid User Profile")
public class Users implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The first name. */
	@Column(name = "first_name", length = 100, nullable = false)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateFirstName", paramClass = String.class, message = "Validation Failed For SETMIS First Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "firstName", fieldValue = "NOT_NULL")
	private String firstName;

	@Column(name = "first_name_orginal", length = 100, nullable = true)
	private String firstNameOrginal;

	@Column(name = "first_name_altered", length = 100, nullable = true)
	private String firstNameAltered;

	@Column(name = "middle_name", length = 100, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateMiddleName", paramClass = String.class, message = "Validation Failed For SETMIS Middle Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "middleName", fieldValue = "NOT_NULL")
	private String middleName;

	@Column(name = "middle_name_orginal", length = 100, nullable = true)
	private String middleNameOrginal;

	@Column(name = "middle_name_altered", length = 100, nullable = true)
	private String middleNameAltered;

	@Column(name = "maiden_name", length = 100, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateMiddleName", paramClass = String.class, message = "Validation Failed For SETMIS Last Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "maidenName", fieldValue = "NOT_NULL")
	private String maidenName;

	/** The last name. */
	@Column(name = "last_name", length = 100, nullable = false)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateLastName", paramClass = String.class, message = "Validation Failed For SETMIS Last Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "lastName", fieldValue = "NOT_NULL")
	private String lastName;

	/** The nationality. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationality_id", nullable = true)
	private Nationality nationality;

	/** The nationality. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "office_id", nullable = true)
	private Office office;

	/** The date of birth. */
	@Column(name = "date_of_birth", nullable = true)
	private Date dateOfBirth;

	/** The rsa ID number. */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "rsa_id_number", length = 13, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateID", paramClass = String.class, message = "Validation Failed For SETMIS ID Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890</li><li>Value must have a length of exactly 13</li><li>Field value may not have characters 0000 from characters 7 to 10</li><li>Field may not have characters 0000 from characters 1 to 4</li><li>Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999</li></ul>", fieldName = "passportNumber", fieldValue = "NOT_NULL")
	private String rsaIDNumber;

	/** The passport number. */
	@Column(name = "passport_number", length = 30, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateAltID", paramClass = String.class, message = "Validation Failed For SETMIS Passport Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "passportNumber", fieldValue = "NOT_NULL")
	private String passportNumber;

	/**
	 * The tempRasIdOrPassportNumber This Field is used when deactivating a merSETA employee
	 */
	@Column(name = "temp_ras_id_or_passport_number", length = 30, nullable = true)
	private String tempRasIdOrPassportNumber;

	/** The gender. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = true)
	private Gender gender;

	/** The disabled. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disabled_id", nullable = true)
	private YesNoLookup disabled;

	/** The disability status. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disabilityStatus")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	private DisabilityStatus disabilityStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_language")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	private Language homeLanguage;

	/** The residential address. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "residential_address_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "residentialAddress", fieldValue = "NOT_NULL")
	private Address residentialAddress;

	/** The postal address. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postal_address_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "postalAddress", fieldValue = "NOT_NULL")
	private Address postalAddress;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "birth_address_id", nullable = true)
	private Address birthAddress;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "birth_town_id", nullable = true)
	private Town birthTown;

	/** The municipality. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipality_id", nullable = true)
	private Municipality municipality;

	/** The tel number. */
	@CheckTelNumber(message = "Tel number not valid")
	@Column(name = "tel_number", length = 20, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateNumber", paramClass = String.class, message = "Validation Failed For SETMIS Tel Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "telNumber", fieldValue = "NOT_NULL")
	private String telNumber;

	/** The cell number. */
	@CheckTelNumber(message = "Cell number not valid")
	@Column(name = "cell_number", length = 20, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateNumber", paramClass = String.class, message = "Validation Failed For SETMIS Cell Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "cellNumber", fieldValue = "NOT_NULL")
	private String cellNumber;

	/** The fax number. */
	@CheckTelNumber(message = "Fax number not valid")
	@Column(name = "fax_number", length = 20, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateFaxNumber", paramClass = String.class, message = "Validation Failed For SETMIS Fax Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "faxNumber", fieldValue = "NOT_NULL")
	private String faxNumber;

	/** The work contact number. */
	@CheckTelNumber(message = "Contact Number number not valid")
	@Column(name = "work_contact_number", length = 20, nullable = true)
	@SETMISFieldValidation(className = UserValidationService.class, method = "validateNumber", paramClass = String.class, message = "Validation Failed For SETMIS Work Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "workContactNumber", fieldValue = "NOT_NULL")
	private String workContactNumber;

	/** The email. */
	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String email;

	/** The username. */
	@Column(name = "username", length = 100, nullable = true)
	private String username;

	/** The password. */
	@Column(name = "password", length = 1000, nullable = false)
	private String password;

	/** The last login. */
	@Column(name = "last_login", nullable = true)
	private Date lastLogin;

	/** The status. */
	@Enumerated
	private UsersStatusEnum status;

	@Enumerated
	@Column(name = "rsa_citizen_type")
	private RsaCitizenTypeEnum rsaCitizenTypeEnum;

	/** The approval enum. */
	@Enumerated
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;

	/** The registration date. */
	@Column(name = "registration_date", nullable = true)
	private Date registrationDate;

	/** The email guid. */
	@Column(name = "email_guid", length = 100, nullable = true)
	private String emailGuid;

	/** The email confirm date. */
	@Column(name = "email_confirm_date", nullable = true)
	private Date emailConfirmDate;

	/** The phone id. */
	@Column(name = "phone_id", length = 100)
	private String phoneId;

	/** The ios android. */
	@Column(name = "ios_android", length = 1, nullable = true)
	private String iosAndroid;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "validiation_ran_date", length = 19)
	private Date validiationRanDate;

	/** The confirm expire date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirm_expire_date", length = 19)
	private Date confirmExpireDate;

	/** The active. */
	@Column(name = "active")
	private Boolean active;

	/** The last chg user. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_chg_user_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "lastChgUser", fieldValue = "NOT_NULL")
	private Users lastChgUser;

	/** The equity. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equity_id", nullable = true)
	private Equity equity;

	/** The profile image. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "profile_image_id", nullable = true)
	private Images profileImage;

	/** The admin. */
	@Column(name = "admin", columnDefinition = "BIT default false")
	private Boolean admin;

	@Column(name = "approved", columnDefinition = "BIT default false")
	private Boolean approved;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;

	/** The Super admin. */
	@Column(name = "super_admin", columnDefinition = "BIT default false")
	private Boolean superAdmin;

	/** The Review Committee member. */
	@Column(name = "review_committee_member", columnDefinition = "BIT default false")
	private Boolean reviewCommitteeMember;

	/** The MANCO Review member. */
	@Column(name = "manco_member", columnDefinition = "BIT default false")
	private Boolean mancoMember;

	/** The MANCO Grant Approvals member. */
	@Column(name = "manco_grant_approval", columnDefinition = "BIT default false")
	private Boolean mancoGrantApproval;

	/** The MANCO Review member. */
	@Column(name = "learner_review_member", columnDefinition = "BIT default false")
	private Boolean learnerReviewMember;

	/** process assessor moderator application. */
	@Column(name = "process_assessor_mod_application", columnDefinition = "BIT default false")
	private Boolean processAssessorModApplication;

	/** The finance. */
	@Column(name = "finance", columnDefinition = "BIT default false")
	private Boolean finance;

	/** The Ability to assign DG contracts for batch processing. */
	@Column(name = "dg_contracting_bulk_approval", columnDefinition = "BIT default false")
	private Boolean dgContractingBulkApproval;

	@Column(name = "view_bugs", columnDefinition = "BIT default false")
	private Boolean viewBugs;

	@Column(name = "view_upload_data", columnDefinition = "BIT default false")
	private Boolean viewUploadData;

	@Column(name = "alloocate_dg", columnDefinition = "BIT default false")
	private Boolean allocateDG;

	@Column(name = "upload_data", columnDefinition = "BIT default false")
	private Boolean uploadData;

	@Column(name = "view_extension_request", columnDefinition = "BIT default false")
	private Boolean viewExtensionRequest;

	@Column(name = "recieve_email_task_notification", columnDefinition = "BIT default true")
	private Boolean recieveEmailTaskNotification;

	/**
	 * The view and ability to assign what qualifications require work place approval
	 */
	@Column(name = "qual_workplace_assignment", columnDefinition = "BIT default false")
	private Boolean qualWorkplaceAssignment;

	/** The view and ability to view the temporary reporting pages */
	@Column(name = "temp_reporting", columnDefinition = "BIT default false")
	private Boolean tempReporting;

	@Column(name = "accept_popi", columnDefinition = "BIT default false")
	private Boolean acceptPopi;
	
	@Column(name = "project_termination", columnDefinition = "BIT default false")
	private Boolean projectTermination;

	@Column(name = "project_creation", columnDefinition = "BIT default false")
	private Boolean projectCreation;

	@Column(name = "courseware_distro_sub", columnDefinition = "BIT default false")
	private Boolean coursewareDistroSub;

	@Column(name = "register_learner", columnDefinition = "BIT default false")
	private Boolean registerLearner;

	@Column(name = "funding_window", columnDefinition = "BIT default false")
	private Boolean fundingWindow;

	@Column(name = "holding_room", columnDefinition = "BIT default false")
	private Boolean holdingRoom;

	@Column(name = "strategic_priorities", columnDefinition = "BIT default false")
	private Boolean strategicPriorities;

	// Added for JIRA #667
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "accept_popi_date", length = 19)
	private Date acceptPopiDate;

	/** The title. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", nullable = true)
	private Title title;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Enumerated
	@Column(name = "undertaken_learning_program_before")
	private YesNoEnum undertakenLearningProgramBefore;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legal_gaurdian_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "legalGaurdian", fieldValue = "NOT_NULL")
	private Users legalGaurdian;

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

	@Enumerated
	@Column(name = "part_of_union")
	private YesNoEnum partOfUnion;

	@Enumerated
	@Column(name = "urban_rural_enum")
	private UrbanRuralEnum urbanRuralEnum;

	@Enumerated
	@Column(name = "id_type")
	private IdPassportEnum idType;

	@JoinColumn(name = "ofo_codes_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private OfoCodes ofoCodes;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "disability_rating_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private DisabilityRating disabilityRating;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "marital_status_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private MaritalStatus maritalStatus;

	@Enumerated
	@Column(name = "maried")
	private YesNoEnum maried;

	@Column(name = "last_school_year", length = 19)
	private Date lastSchoolYear;

	@Enumerated
	@Column(name = "disability")
	private YesNoEnum disability;

	/** The docs. */
	@Transient
	private List<Doc> docs;

	/** The chg pwd now. */
	@Transient
	private boolean chgPwdNow;

	@Transient
	private boolean showPopi;

	/** The done search. */
	@Transient
	private boolean doneSearch;

	/** The existing user. */
	@Transient
	private boolean existingUser;

	/** The reg fields done. */
	@Transient
	private boolean regFieldsDone;

	@Transient
	private boolean withSdf;

	@Transient
	private String roles;

	/** The designation. */
	@Transient
	private Designation designation;

	@Transient
	private AssessorModType assessorModType;

	@Column(name = "data_extracts", columnDefinition = "BIT default false")
	private Boolean dataExtracts;

	@Column(name = "wsp_managment", columnDefinition = "BIT default false")
	private Boolean wspManagment;

	@Column(name = "gp_transactions_management", columnDefinition = "BIT default false")
	private Boolean gpTransactionsManagement;

	@Column(name = "sars_levy_detail_calc", columnDefinition = "BIT default false")
	private Boolean sarsLevyDetailCalc;

	@Column(name = "sars_levy_mand_invoices_update", columnDefinition = "BIT default false")
	private Boolean sarsLevyMandInvoicesUpdate;

	@Column(name = "add_assessment", columnDefinition = "BIT default false")
	private Boolean addAssessment;

	@Column(name = "generate_certificates", columnDefinition = "BIT default false")
	private Boolean generateCertificates;

	@Column(name = "gp_banking_details", columnDefinition = "BIT default false")
	private Boolean gpBankingDetails;

	@Column(name = "levy_detail_mg_transactions", columnDefinition = "BIT default false")
	private Boolean levyDetailMgTransactions;

	@Column(name = "alter_legacy_applications", columnDefinition = "BIT default false")
	private Boolean alterLegacyApplications;

	@Column(name = "qmr_access", columnDefinition = "BIT default false")
	private Boolean qmrAccess;

	@Column(name = "moa_register_access", columnDefinition = "BIT default false")
	private Boolean moaRegisterAccess;

	@Column(name = "task_reporting", columnDefinition = "BIT default false")
	private Boolean taskReporting;

	@Column(name = "grant_trend_anylsis", columnDefinition = "BIT default false")
	private Boolean grantTrendAnylsis;

	@Column(name = "prioirty_skills_report", columnDefinition = "BIT default false")
	private Boolean prioirtySkillsReport;

	@Column(name = "company_register_report", columnDefinition = "BIT default false")
	private Boolean companyRegisterReport;

	@Column(name = "mg_verification_report", columnDefinition = "BIT default false")
	private Boolean mgVerificationReport;

	@Column(name = "dg_verification_report", columnDefinition = "BIT default false")
	private Boolean dgVerificationReport;

	@Column(name = "nsdp_report_config", columnDefinition = "BIT default false")
	private Boolean nsdpReportConfig;

	@Column(name = "nsdp_report_view", columnDefinition = "BIT default false")
	private Boolean nsdpReportView;

	@Column(name = "nsdp_report_edit", columnDefinition = "BIT default false")
	private Boolean nsdpReportEdit;

	@Column(name = "withdraw_contracts", columnDefinition = "BIT default false")
	private Boolean withdrawContracts;

	@Column(name = "ssp_reporting", columnDefinition = "BIT default false")
	private Boolean sspReporting;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_schools", nullable = true)
	@Fetch(FetchMode.JOIN)
	private PreviousSchools previousSchools;

	@Transient
	private String deceasedStatus;

	@Transient
	private String validationStatus;

	@Transient
	private UserUpdateInfoBean updateBean;

	@Transient
	private List<UsersLanguage> usersLanguageList = new ArrayList<>();

	@Transient
	private List<UsersDisability> usersDisabilityList = new ArrayList<>();

	@Transient
	private UserPermissions userPermissions;

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

	public void post(Address psAddress, Address pos2Address) {
		this.postalAddress      = psAddress;
		this.residentialAddress = pos2Address;
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
		Users other = (Users) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new users.
	 */
	public Users() {
	}

	/**
	 * Instantiates a new users.
	 * @param id
	 * the id
	 */
	public Users(Long id) {
		this.id = id;
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
	 * Gets the first name.
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * @param firstName
	 * the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 * @param lastName
	 * the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the nationality.
	 * @return the nationality
	 */
	public Nationality getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 * @param nationality
	 * the new nationality
	 */
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the date of birth.
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 * @param dateOfBirth
	 * the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the rsa ID number.
	 * @return the rsa ID number
	 */
	public String getRsaIDNumber() {
		return rsaIDNumber;
	}

	/**
	 * Sets the rsa ID number.
	 * @param rsaIDNumber
	 * the new rsa ID number
	 */
	public void setRsaIDNumber(String rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	/**
	 * Gets the passport number.
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 * @param passportNumber
	 * the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the gender.
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 * @param gender
	 * the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the disabled.
	 * @return the disabled
	 */
	public YesNoLookup getDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled.
	 * @param disabled
	 * the new disabled
	 */
	public void setDisabled(YesNoLookup disabled) {
		this.disabled = disabled;
	}

	/**
	 * Gets the residential address.
	 * @return the residential address
	 */
	public Address getResidentialAddress() {
		return residentialAddress;
	}

	/**
	 * Sets the residential address.
	 * @param residentialAddress
	 * the new residential address
	 */
	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Gets the postal address.
	 * @return the postal address
	 */
	public Address getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postal address.
	 * @param postalAddress
	 * the new postal address
	 */
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * Gets the municipality.
	 * @return the municipality
	 */
	public Municipality getMunicipality() {
		return municipality;
	}

	/**
	 * Sets the municipality.
	 * @param municipality
	 * the new municipality
	 */
	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	/**
	 * Gets the tel number.
	 * @return the tel number
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * Sets the tel number.
	 * @param telNumber
	 * the new tel number
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	/**
	 * Gets the cell number.
	 * @return the cell number
	 */
	public String getCellNumber() {
		return cellNumber;
	}

	/**
	 * Sets the cell number.
	 * @param cellNumber
	 * the new cell number
	 */
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	/**
	 * Gets the fax number.
	 * @return the fax number
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * Sets the fax number.
	 * @param faxNumber
	 * the new fax number
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * Gets the work contact number.
	 * @return the work contact number
	 */
	public String getWorkContactNumber() {
		return workContactNumber;
	}

	/**
	 * Sets the work contact number.
	 * @param workContactNumber
	 * the new work contact number
	 */
	public void setWorkContactNumber(String workContactNumber) {
		this.workContactNumber = workContactNumber;
	}

	/**
	 * Gets the email.
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * @param email
	 * the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the username.
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * @param username
	 * the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * @param password
	 * the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the last login.
	 * @return the last login
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * Sets the last login.
	 * @param lastLogin
	 * the new last login
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * Gets the registration date.
	 * @return the registration date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets the registration date.
	 * @param registrationDate
	 * the new registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the email guid.
	 * @return the email guid
	 */
	public String getEmailGuid() {
		return emailGuid;
	}

	/**
	 * Sets the email guid.
	 * @param emailGuid
	 * the new email guid
	 */
	public void setEmailGuid(String emailGuid) {
		this.emailGuid = emailGuid;
	}

	/**
	 * Gets the email confirm date.
	 * @return the email confirm date
	 */
	public Date getEmailConfirmDate() {
		return emailConfirmDate;
	}

	/**
	 * Sets the email confirm date.
	 * @param emailConfirmDate
	 * the new email confirm date
	 */
	public void setEmailConfirmDate(Date emailConfirmDate) {
		this.emailConfirmDate = emailConfirmDate;
	}

	/**
	 * Gets the phone id.
	 * @return the phone id
	 */
	public String getPhoneId() {
		return phoneId;
	}

	/**
	 * Sets the phone id.
	 * @param phoneId
	 * the new phone id
	 */
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	/**
	 * Gets the ios android.
	 * @return the ios android
	 */
	public String getIosAndroid() {
		return iosAndroid;
	}

	/**
	 * Sets the ios android.
	 * @param iosAndroid
	 * the new ios android
	 */
	public void setIosAndroid(String iosAndroid) {
		this.iosAndroid = iosAndroid;
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
	 * Gets the active.
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 * @param active
	 * the new active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the last chg user.
	 * @return the last chg user
	 */
	public Users getLastChgUser() {
		return lastChgUser;
	}

	/**
	 * Sets the last chg user.
	 * @param lastChgUser
	 * the new last chg user
	 */
	public void setLastChgUser(Users lastChgUser) {
		this.lastChgUser = lastChgUser;
	}

	/**
	 * Checks if is chg pwd now.
	 * @return true, if is chg pwd now
	 */
	public boolean isChgPwdNow() {
		return chgPwdNow;
	}

	/**
	 * Sets the chg pwd now.
	 * @param chgPwdNow
	 * the new chg pwd now
	 */
	public void setChgPwdNow(boolean chgPwdNow) {
		this.chgPwdNow = chgPwdNow;
	}

	/**
	 * Gets the status.
	 * @return the status
	 */
	public UsersStatusEnum getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * @param status
	 * the new status
	 */
	public void setStatus(UsersStatusEnum status) {
		this.status = status;
	}

	/**
	 * Gets the profile image.
	 * @return the profileImage
	 */
	public Images getProfileImage() {
		return profileImage;
	}

	/**
	 * Sets the profile image.
	 * @param profileImage
	 * the profileImage to set
	 */
	public void setProfileImage(Images profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * Gets the profile image for view.
	 * @return the profile image for view
	 */
	public String getProfileImageForView() {
		if (this.profileImage != null) return HAJConstants.DOC_SERVER + (this.profileImage.getNewFname());
		else return null;
	}

	/**
	 * Gets the profile image small.
	 * @return the profile image small
	 */
	public String getProfileImageSmall() {
		if (this.getProfileImage() != null && this.getProfileImage().getSmallFileName() != null) {
			try {
				return HAJConstants.DOC_SERVER + this.getProfileImage().getSmallFileName();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Gets the admin.
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return admin;
	}

	/**
	 * Sets the admin.
	 * @param admin
	 * the new admin
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	/**
	 * Gets the disability status.
	 * @return the disability status
	 */
	public DisabilityStatus getDisabilityStatus() {
		return disabilityStatus;
	}

	/**
	 * Sets the disability status.
	 * @param disabilityStatus
	 * the new disability status
	 */
	public void setDisabilityStatus(DisabilityStatus disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	/**
	 * Checks if is done search.
	 * @return true, if is done search
	 */
	public boolean isDoneSearch() {
		return doneSearch;
	}

	/**
	 * Sets the done search.
	 * @param doneSearch
	 * the new done search
	 */
	public void setDoneSearch(boolean doneSearch) {
		this.doneSearch = doneSearch;
	}

	/**
	 * Checks if is existing user.
	 * @return true, if is existing user
	 */
	public boolean isExistingUser() {
		return existingUser;
	}

	/**
	 * Sets the existing user.
	 * @param existingUser
	 * the new existing user
	 */
	public void setExistingUser(boolean existingUser) {
		this.existingUser = existingUser;
	}

	/**
	 * Checks if is reg fields done.
	 * @return true, if is reg fields done
	 */
	public boolean isRegFieldsDone() {
		return regFieldsDone;
	}

	/**
	 * Sets the reg fields done.
	 * @param regFieldsDone
	 * the new reg fields done
	 */
	public void setRegFieldsDone(boolean regFieldsDone) {
		this.regFieldsDone = regFieldsDone;
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
	 * Gets the valid user.
	 * @return the valid user
	 */
	@Transient
	@JsonIgnore
	public String getValidUser() {
		StringBuilder exceptions = new StringBuilder("");
		if (firstName == null || firstName.isEmpty()) {
			exceptions.append("<li>First name cannot be empty</li>");
		}
		if (lastName == null || lastName.isEmpty()) {
			exceptions.append("<li>Last name cannot be empty</li>");
		}
		if (telNumber == null || telNumber.isEmpty()) {
			exceptions.append("<li>Tel number cannot be empty</li>");
		}
		if (residentialAddress == null) {
			exceptions.append("<li>Residential address cannot be empty</li>");

		} else {
			if (residentialAddress.getAddressLine1() == null || residentialAddress.getAddressLine1().isEmpty()) {
				exceptions.append("<li>Residential address line 1 cannot be empty</li>");
			}
			if (residentialAddress.getPostcode() == null || residentialAddress.getPostcode().isEmpty()) {
				exceptions.append("<li>Residential address postcode cannot be empty</li>");
			}
		}
		if (postalAddress == null) {
			exceptions.append("<li>Postal address cannot be empty</li>");
		} else {
			if (postalAddress.getAddressLine1() == null || postalAddress.getAddressLine1().isEmpty()) {
				exceptions.append("<li>Postal address line 1 cannot be empty</li>");
			}
			if (postalAddress.getPostcode() == null || postalAddress.getPostcode().isEmpty()) {
				exceptions.append("<li>Postal address postcode cannot be empty</li>");
			}
		}
		if ((passportNumber == null || passportNumber.isEmpty()) && (rsaIDNumber == null || rsaIDNumber.isEmpty())) {
			exceptions.append("<li>Either RSA ID or passport number is required</li>");
		}
		if (gender == null) {
			exceptions.append("<li>No gender selected</li>");
		}
		return exceptions.toString();
	}

	/**
	 * Gets the approval enum.
	 * @return the approval enum
	 */
	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	/**
	 * Sets the approval enum.
	 * @param approvalEnum
	 * the new approval enum
	 */
	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	/**
	 * Gets the equity.
	 * @return the equity
	 */
	public Equity getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 * @param equity
	 * the new equity
	 */
	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	/**
	 * Gets the confirm expire date.
	 * @return the confirm expire date
	 */
	public Date getConfirmExpireDate() {
		return confirmExpireDate;
	}

	/**
	 * Sets the confirm expire date.
	 * @param confirmExpireDate
	 * the new confirm expire date
	 */
	public void setConfirmExpireDate(Date confirmExpireDate) {
		this.confirmExpireDate = confirmExpireDate;
	}

	public Boolean getRecieveEmailTaskNotification() {
		return recieveEmailTaskNotification;
	}

	public void setRecieveEmailTaskNotification(Boolean recieveEmailTaskNotification) {
		this.recieveEmailTaskNotification = recieveEmailTaskNotification;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Boolean getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public Boolean getFinance() {
		return finance;
	}

	public void setFinance(Boolean finance) {
		this.finance = finance;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Boolean getAcceptPopi() {
		return acceptPopi;
	}

	public void setAcceptPopi(Boolean acceptPopi) {
		this.acceptPopi = acceptPopi;
	}

	public boolean isShowPopi() {
		return showPopi;
	}

	public void setShowPopi(boolean showPopi) {
		this.showPopi = showPopi;
	}

	public Boolean getViewBugs() {
		return viewBugs;
	}

	public void setViewBugs(Boolean viewBugs) {
		this.viewBugs = viewBugs;
	}

	public Boolean getViewUploadData() {
		return viewUploadData;
	}

	public void setViewUploadData(Boolean viewUploadData) {
		this.viewUploadData = viewUploadData;
	}

	public Boolean getUploadData() {
		return uploadData;
	}

	public void setUploadData(Boolean uploadData) {
		this.uploadData = uploadData;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Boolean getViewExtensionRequest() {
		return viewExtensionRequest;
	}

	public void setViewExtensionRequest(Boolean viewExtensionRequest) {
		this.viewExtensionRequest = viewExtensionRequest;
	}

	public Language getHomeLanguage() {
		return homeLanguage;
	}

	public void setHomeLanguage(Language homeLanguage) {
		this.homeLanguage = homeLanguage;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public YesNoEnum getUndertakenLearningProgramBefore() {
		return undertakenLearningProgramBefore;
	}

	public void setUndertakenLearningProgramBefore(YesNoEnum undertakenLearningProgramBefore) {
		this.undertakenLearningProgramBefore = undertakenLearningProgramBefore;
	}

	public Users getLegalGaurdian() {
		return legalGaurdian;
	}

	public void setLegalGaurdian(Users legalGaurdian) {
		this.legalGaurdian = legalGaurdian;
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

	public YesNoEnum getPartOfUnion() {
		return partOfUnion;
	}

	public void setPartOfUnion(YesNoEnum partOfUnion) {
		this.partOfUnion = partOfUnion;
	}

	public UrbanRuralEnum getUrbanRuralEnum() {
		return urbanRuralEnum;
	}

	public void setUrbanRuralEnum(UrbanRuralEnum urbanRuralEnum) {
		this.urbanRuralEnum = urbanRuralEnum;
	}

	public IdPassportEnum getIdType() {
		return idType;
	}

	public void setIdType(IdPassportEnum idType) {
		this.idType = idType;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Boolean getQualWorkplaceAssignment() {
		return qualWorkplaceAssignment;
	}

	public void setQualWorkplaceAssignment(Boolean qualWorkplaceAssignment) {
		this.qualWorkplaceAssignment = qualWorkplaceAssignment;
	}

	public Boolean getTempReporting() {
		return tempReporting;
	}

	public void setTempReporting(Boolean tempReporting) {
		this.tempReporting = tempReporting;
	}

	public Boolean getReviewCommitteeMember() {
		return reviewCommitteeMember;
	}

	public void setReviewCommitteeMember(Boolean reviewCommitteeMember) {
		this.reviewCommitteeMember = reviewCommitteeMember;
	}

	public Boolean getProcessAssessorModApplication() {
		return processAssessorModApplication;
	}

	public void setProcessAssessorModApplication(Boolean processAssessorModApplication) {
		this.processAssessorModApplication = processAssessorModApplication;
	}

	public OrganisedLabourUnion getOrganisedLabourUnion() {
		return organisedLabourUnion;
	}

	public void setOrganisedLabourUnion(OrganisedLabourUnion organisedLabourUnion) {
		this.organisedLabourUnion = organisedLabourUnion;
	}

	/**
	 * @return the allocateDG
	 */
	public Boolean getAllocateDG() {
		return allocateDG;
	}

	/**
	 * @param allocateDG
	 * the allocateDG to set
	 */
	public void setAllocateDG(Boolean allocateDG) {
		this.allocateDG = allocateDG;
	}

	public Boolean getMancoMember() {
		return mancoMember;
	}

	public void setMancoMember(Boolean mancoMember) {
		this.mancoMember = mancoMember;
	}

	public Date getEmploymentStartDate() {
		return employmentStartDate;
	}

	public void setEmploymentStartDate(Date employmentStartDate) {
		this.employmentStartDate = employmentStartDate;
	}

	public Boolean getLearnerReviewMember() {
		return learnerReviewMember;
	}

	public void setLearnerReviewMember(Boolean learnerReviewMember) {
		this.learnerReviewMember = learnerReviewMember;
	}

	/**
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}

	/**
	 * @param designation
	 * the designation to set
	 */
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public AssessorModType getAssessorModType() {
		return assessorModType;
	}

	public void setAssessorModType(AssessorModType assessorModType) {
		this.assessorModType = assessorModType;
	}

	public String getTempRasIdOrPassportNumber() {
		return tempRasIdOrPassportNumber;
	}

	public void setTempRasIdOrPassportNumber(String tempRasIdOrPassportNumber) {
		this.tempRasIdOrPassportNumber = tempRasIdOrPassportNumber;
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Boolean getProjectTermination() {
		return projectTermination;
	}

	public void setProjectTermination(Boolean projectTermination) {
		this.projectTermination = projectTermination;
	}

	public DisabilityRating getDisabilityRating() {
		return disabilityRating;
	}

	public void setDisabilityRating(DisabilityRating disabilityRating) {
		this.disabilityRating = disabilityRating;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public YesNoEnum getMaried() {
		return maried;
	}

	public void setMaried(YesNoEnum maried) {
		this.maried = maried;
	}

	public Boolean getProjectCreation() {
		return projectCreation;
	}

	public void setProjectCreation(Boolean projectCreation) {
		this.projectCreation = projectCreation;
	}

	public Boolean getCoursewareDistroSub() {
		return coursewareDistroSub;
	}

	public void setCoursewareDistroSub(Boolean coursewareDistroSub) {
		this.coursewareDistroSub = coursewareDistroSub;
	}

	public Boolean getMancoGrantApproval() {
		return mancoGrantApproval;
	}

	public void setMancoGrantApproval(Boolean mancoGrantApproval) {
		this.mancoGrantApproval = mancoGrantApproval;
	}

	public Date getLastSchoolYear() {
		return lastSchoolYear;
	}

	public void setLastSchoolYear(Date lastSchoolYear) {
		this.lastSchoolYear = lastSchoolYear;
	}

	public Boolean getDgContractingBulkApproval() {
		return dgContractingBulkApproval;
	}

	public void setDgContractingBulkApproval(Boolean dgContractingBulkApproval) {
		this.dgContractingBulkApproval = dgContractingBulkApproval;
	}

	public YesNoEnum getDisability() {
		return disability;
	}

	public void setDisability(YesNoEnum disability) {
		this.disability = disability;
	}

	public Boolean getRegisterLearner() {
		return registerLearner;
	}

	public void setRegisterLearner(Boolean registerLearner) {
		this.registerLearner = registerLearner;
	}

	public RsaCitizenTypeEnum getRsaCitizenTypeEnum() {
		return rsaCitizenTypeEnum;
	}

	public void setRsaCitizenTypeEnum(RsaCitizenTypeEnum rsaCitizenTypeEnum) {
		this.rsaCitizenTypeEnum = rsaCitizenTypeEnum;
	}

	public boolean isWithSdf() {
		return withSdf;
	}

	public void setWithSdf(boolean withSdf) {
		this.withSdf = withSdf;
	}

	public Boolean getDataExtracts() {
		return dataExtracts;
	}

	public void setDataExtracts(Boolean dataExtracts) {
		this.dataExtracts = dataExtracts;
	}

	public Boolean getWspManagment() {
		return wspManagment;
	}

	public void setWspManagment(Boolean wspManagment) {
		this.wspManagment = wspManagment;
	}

	public Boolean getGpTransactionsManagement() {
		return gpTransactionsManagement;
	}

	public void setGpTransactionsManagement(Boolean gpTransactionsManagement) {
		this.gpTransactionsManagement = gpTransactionsManagement;
	}

	public Date getValidiationRanDate() {
		return validiationRanDate;
	}

	public void setValidiationRanDate(Date validiationRanDate) {
		this.validiationRanDate = validiationRanDate;
	}

	public String getFirstNameOrginal() {
		return firstNameOrginal;
	}

	public void setFirstNameOrginal(String firstNameOrginal) {
		this.firstNameOrginal = firstNameOrginal;
	}

	public String getFirstNameAltered() {
		return firstNameAltered;
	}

	public void setFirstNameAltered(String firstNameAltered) {
		this.firstNameAltered = firstNameAltered;
	}

	public String getMiddleNameOrginal() {
		return middleNameOrginal;
	}

	public void setMiddleNameOrginal(String middleNameOrginal) {
		this.middleNameOrginal = middleNameOrginal;
	}

	public String getMiddleNameAltered() {
		return middleNameAltered;
	}

	public void setMiddleNameAltered(String middleNameAltered) {
		this.middleNameAltered = middleNameAltered;
	}

	public Town getBirthTown() {
		return birthTown;
	}

	public void setBirthTown(Town birthTown) {
		this.birthTown = birthTown;
	}

	public Boolean getAddAssessment() {
		return addAssessment;
	}

	public void setAddAssessment(Boolean addAssessment) {
		this.addAssessment = addAssessment;
	}

	public Boolean getGenerateCertificates() {
		return generateCertificates;
	}

	public void setGenerateCertificates(Boolean generateCertificates) {
		this.generateCertificates = generateCertificates;
	}

	public PreviousSchools getPreviousSchools() {
		return previousSchools;
	}

	public void setPreviousSchools(PreviousSchools previousSchools) {
		this.previousSchools = previousSchools;
	}

	public Boolean getSarsLevyDetailCalc() {
		return sarsLevyDetailCalc;
	}

	public void setSarsLevyDetailCalc(Boolean sarsLevyDetailCalc) {
		this.sarsLevyDetailCalc = sarsLevyDetailCalc;
	}

	public Boolean getSarsLevyMandInvoicesUpdate() {
		return sarsLevyMandInvoicesUpdate;
	}

	public void setSarsLevyMandInvoicesUpdate(Boolean sarsLevyMandInvoicesUpdate) {
		this.sarsLevyMandInvoicesUpdate = sarsLevyMandInvoicesUpdate;
	}

	public String getDeceasedStatus() {
		return deceasedStatus;
	}

	public void setDeceasedStatus(String deceasedStatus) {
		this.deceasedStatus = deceasedStatus;
	}

	public String getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(String validationStatus) {
		this.validationStatus = validationStatus;
	}

	public Boolean getGpBankingDetails() {
		return gpBankingDetails;
	}

	public void setGpBankingDetails(Boolean gpBankingDetails) {
		this.gpBankingDetails = gpBankingDetails;
	}

	public Boolean getLevyDetailMgTransactions() {
		return levyDetailMgTransactions;
	}

	public void setLevyDetailMgTransactions(Boolean levyDetailMgTransactions) {
		this.levyDetailMgTransactions = levyDetailMgTransactions;
	}

	public Boolean getAlterLegacyApplications() {
		return alterLegacyApplications;
	}

	public void setAlterLegacyApplications(Boolean alterLegacyApplications) {
		this.alterLegacyApplications = alterLegacyApplications;
	}

	public Boolean getQmrAccess() {
		return qmrAccess;
	}

	public void setQmrAccess(Boolean qmrAccess) {
		this.qmrAccess = qmrAccess;
	}

	public UserUpdateInfoBean getUpdateBean() {
		return updateBean;
	}

	public void setUpdateBean(UserUpdateInfoBean updateBean) {
		this.updateBean = updateBean;
	}

	public List<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public List<UsersDisability> getUsersDisabilityList() {
		return usersDisabilityList;
	}

	public void setUsersLanguageList(List<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	public void setUsersDisabilityList(List<UsersDisability> usersDisabilityList) {
		this.usersDisabilityList = usersDisabilityList;
	}

	public Boolean getMoaRegisterAccess() {
		return moaRegisterAccess;
	}

	public void setMoaRegisterAccess(Boolean moaRegisterAccess) {
		this.moaRegisterAccess = moaRegisterAccess;
	}

	public Boolean getTaskReporting() {
		return taskReporting;
	}

	public void setTaskReporting(Boolean taskReporting) {
		this.taskReporting = taskReporting;
	}

	public Boolean getGrantTrendAnylsis() {
		return grantTrendAnylsis;
	}

	public void setGrantTrendAnylsis(Boolean grantTrendAnylsis) {
		this.grantTrendAnylsis = grantTrendAnylsis;
	}

	public Boolean getPrioirtySkillsReport() {
		return prioirtySkillsReport;
	}

	public void setPrioirtySkillsReport(Boolean prioirtySkillsReport) {
		this.prioirtySkillsReport = prioirtySkillsReport;
	}

	public Boolean getCompanyRegisterReport() {
		return companyRegisterReport;
	}

	public void setCompanyRegisterReport(Boolean companyRegisterReport) {
		this.companyRegisterReport = companyRegisterReport;
	}

	public Boolean getMgVerificationReport() {
		return mgVerificationReport;
	}

	public void setMgVerificationReport(Boolean mgVerificationReport) {
		this.mgVerificationReport = mgVerificationReport;
	}

	public Boolean getDgVerificationReport() {
		return dgVerificationReport;
	}

	public void setDgVerificationReport(Boolean dgVerificationReport) {
		this.dgVerificationReport = dgVerificationReport;
	}

	public Boolean getNsdpReportConfig() {
		return nsdpReportConfig;
	}

	public void setNsdpReportConfig(Boolean nsdpReportConfig) {
		this.nsdpReportConfig = nsdpReportConfig;
	}

	public Boolean getNsdpReportView() {
		return nsdpReportView;
	}

	public void setNsdpReportView(Boolean nsdpReportView) {
		this.nsdpReportView = nsdpReportView;
	}

	public Boolean getNsdpReportEdit() {
		return nsdpReportEdit;
	}

	public void setNsdpReportEdit(Boolean nsdpReportEdit) {
		this.nsdpReportEdit = nsdpReportEdit;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public Boolean getWithdrawContracts() {
		return withdrawContracts;
	}

	public void setWithdrawContracts(Boolean withdrawContracts) {
		this.withdrawContracts = withdrawContracts;
	}

	public Boolean getSspReporting() {
		return sspReporting;
	}

	public void setSspReporting(Boolean sspReporting) {
		this.sspReporting = sspReporting;
	}

	public UserPermissions getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(UserPermissions userPermissions) {
		this.userPermissions = userPermissions;
	}

	public Boolean getFundingWindow() {
		return fundingWindow;
	}

	public void setFundingWindow(Boolean fundingWindow) {
		this.fundingWindow = fundingWindow;
	}

	public Boolean getHoldingRoom() {
		return holdingRoom;
	}

	public void setHoldingRoom(Boolean holdingRoom) {
		this.holdingRoom = holdingRoom;
	}

	public Boolean getStrategicPriorities() {
		return strategicPriorities;
	}

	public void setStrategicPriorities(Boolean strategicPriorities) {
		this.strategicPriorities = strategicPriorities;
	}

	// Added for JIRA #667
	public Date getAcceptPopiDate() {
		return acceptPopiDate;
	}

	// Added for JIRA #667
	public void setAcceptPopiDate(Date acceptPopiDate) {
		this.acceptPopiDate = acceptPopiDate;
	}


}
