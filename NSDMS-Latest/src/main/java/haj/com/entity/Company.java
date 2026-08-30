package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.Month;
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
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import haj.com.bean.CompanyUpdateInfoBean;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.InstitutionType;
import haj.com.entity.lookup.OrganisationType;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.SICCode;
import haj.com.entity.lookup.Seta;
import haj.com.entity.lookup.SizeOfCompany;
import haj.com.framework.IDataEntity;
import haj.com.sars.SARSConstants;
import haj.com.validators.CheckTelNumber;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.CompanyValidationService;

// TODO: Auto-generated Javadoc
/**
 * Company.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company")
@AuditTable(value = "company_hist")
@Audited
@ExportValidation(message = "Invalid Company Information")
public class Company implements IDataEntity, Cloneable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Unique Id of Company. Id can't be null
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 * Create Date of Company. Create Date length can't be more than 19 characters.
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "validiation_ran_date", length = 19)
	private Date validiationRanDate;

	/**
	 * The company name of Company. Field length = 70 This field may not be left
	 * blank Field may not start with a space.(Trim if applicable) Uppercase value
	 * in field may only contain characters
	 * ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'- Uppercase value in field
	 * may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE%
	 * or N/A or NA
	 */
	@Column(name = "company_name", length = 70, nullable = false)
	// @Size(min = 1, max = 70, message = "Company name can't be less than 1
	// character or more than 70 characters")
	// @NotEmpty(message ="Company name is required")
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "companyNameValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._,`'-</li></ul>", fieldName = "companyName", fieldValue = "NOT_NULL")
	private String companyName;

	/**
	 * The guid of Company. The company guid can be null Company guid cannot be more
	 * than 100 characters
	 */
	@Column(name = "company_guid", length = 100, nullable = true)
	@Size(min = 1, max = 100, message = "Company guid can't be more than 100 characters")
	private String companyGuid;

	/**
	 * The trading name Company. Field length = 70 This field may not be left blank
	 * Field may not start with a space.(Trim if applicable) Uppercase value in
	 * field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+()
	 * /\:._,`'- Uppercase value in field may not contain characters %UNKNOWN% or
	 * %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or
	 * TEST or %ONTBREEK% or NIL or - or
	 */
	@Column(name = "trading_name", length = 70, nullable = true)
	// @Size(min = 1, max = 70, message = "Company name can't be more than 70
	// characters")
	// @NotEmpty(message ="Company trading name is required")
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "companyTradingNameValidation", paramClass = String.class, message = "Validation Failed For SETMIS Trading Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>", fieldName = "tradingName", fieldValue = "NOT_NULL")
	private String tradingName;

	/**
	 * The residential address of Company. The residential address of Company can be
	 * null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "residential_address_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "residentialAddress", fieldValue = "true")
	private Address residentialAddress;

	/**
	 * The postal address Company. The postal address Company can be null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postal_address_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "postalAddress", fieldValue = "true")
	private Address postalAddress;

	/**
	 * The registered address of Company. The registered address of company can be
	 * null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "registered_address_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "registeredAddress", fieldValue = "true")
	private Address registeredAddress;

	/**
	 * The company registration number of Company. Field length = 20 This field may
	 * be left blank Field may not start with a space. Uppercase value in field may
	 * only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'-
	 */
	@Column(name = "company_registration_number", length = 40, nullable = true)
	// @Size(min = 1, max = 20, message = "Company registration number can't be less
	// than 1 character or more than 20 characters")
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "companyRegValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Registration Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>", fieldName = "companyRegistrationNumber", fieldValue = "NOT_NULL")
	private String companyRegistrationNumber;

	/**
	 * The tel number of Company. Field length = 20 This field may be left blank
	 * Field may not start with a space. Uppercase value in field may only contain
	 * characters 1234567890 ()/- Uppercase value in field may not contain
	 * characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U
	 * or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or
	 */
	@CheckTelNumber(message = "Tel number not valid")
	@Column(name = "tel_number", length = 20, nullable = true)
	// @Size(min = 1, max = 20, message = "Tel number length can't be less than 1
	// character or more than 20 characters")
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "contactNumberValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Tel Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890 ()/-</li></ul>", fieldName = "telNumber", fieldValue = "NOT_NULL")
	private String telNumber;

	/**
	 * The fax number of Company. Field length = 20 This field may be left blank
	 * Field may not start with a space. Uppercase value in field may only contain
	 * characters 1234567890 ()/- Uppercase value in field may not contain
	 * characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U
	 * or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or
	 */
	@CheckTelNumber(message = "Fax number not valid")
	@Column(name = "fax_number", length = 20, nullable = true)
	// @Size(min = 0, max = 20, message = "Fax number length can't be more than 20
	// characters")
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "faxNumberValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company FAX Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890 ()/-</li></ul>", fieldName = "faxNumber", fieldValue = "NOT_NULL")
	private String faxNumber;

	/**
	 * The email of Company. Field length = 50 This field may be left blank Field
	 * may not start with a space. Uppercase value in field may only contain
	 * characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@ Uppercase value in
	 * field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or
	 * %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL
	 * or - or Field must contain the @ character
	 */
	@Column(name = "email", length = 50, nullable = true)
	@Size(min = 1, max = 50, message = "Email address can't be less than 1 character or more than 50 characters")
	@Email(message = "Please enter a valid Email Address")
	private String email;

	/**
	 * The chamber of Company. Field length = 10 This field may be left blank Field
	 * may not start with a space. Uppercase value in field may only contain
	 * characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'- Field must
	 * contain a valid SIC_Code
	 */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chamber_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "chamber", fieldValue = "true")
	private Chamber chamber;

	/**
	 * The sic code of Company. The sic code of Company can be null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sic_code_id", nullable = true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@SETMISFieldValidation(process = true,fieldName = "sicCode", fieldValue = "true")
	private SICCode sicCode;

	/**
	 * The number of employees of Company. The number of employees must be an
	 * Integer The length of number of employees can't be more than 40 characters
	 */

	// @Size(min = 1, max = 40, message = "Number of employees can't be more than 40
	// digits")
	// @NotEmpty(message ="Number of employees is required")
	private Integer numberOfEmployees;

	/**
	 * The paye SDL number of Company. The paye SDL Field Length = 10 This field may
	 * not be left blank Field may not start with a space. Value may only contain
	 * characters L0123456789 OR N0123456789 Value must have a length of exactly 10
	 * Value must start with L followed by 9 digits, OR N followed by 9
	 * digits(indicating a non levy paying company) The combination of SDL_No and
	 * Site_No must be unique.
	 */
	@Column(name = "paye_sdl_number", length = 10, nullable = true)
	/*
	 * @Size(min = 1, max = 10, message =
	 * "Company paye SDL number can't be less than 1 character or more than 10 characters"
	 * )
	 * 
	 * @NotEmpty(message ="Company paye SDL number is required")
	 */
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "payeSDLNumberValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company PAYE SDL Number failed.<ul> <li>Field may not start with a space.</li><li>Value must have a length of exactly 10</li><li>Value must start with 'L' followed by 9 digits or 'N' followed by 9 digits </li><li>Field may not equal: 'L000000000'</li> <li>Field must contain value '7' or '8' in the 5 position (LNNN*7*NNNNN 0R LNNN*8*NNNNN)</li> </ul>", fieldName = "payeSDLNumber", fieldValue = "NOT_NULL")
	private String payeSDLNumber;

	/**
	 * The levy number of Company i.e. L190711657. The levy number length can't be
	 * less than 1 character or more than 10 characters.
	 */
	// @Pattern(regexp = "L\\d\\d\\d7\\d\\d\\d\\d\\d",message="Invalid Levy number")
	@Column(name = "levy_number", length = 10)
	// @Size(min = 1, max = 10, message = "Company levy number can't be less than 1
	// character or more than 10 characters")
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "levyNumberValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Levy Number failed.<ul> <li>Field may not start with a space.</li><li>Value must have a length of exactly 10</li><li>Value must start with 'L' followed by 9 digits </li><li>Field may not equal: 'L000000000'</li> <li>Field must contain value '7' or '8' in the 5 position (LNNN*7*NNNNN 0R LNNN*8*NNNNN)</li>  </ul>", fieldName = "levyNumber", fieldValue = "NOT_NULL")
	private String levyNumber;

	/**
	 * The additional levy number of Company. i.e. L190711657. Company additional
	 * levy number can't be less than 1 character or more than 40 characters.
	 */
	// @Pattern(regexp = "L\\d\\d\\d7\\d\\d\\d\\d\\d",message="Invalid Levy number")
	@Column(name = "levy_number_additional", length = 10)
	// @Size(min = 1, max = 10, message = "Company additional levy number can't be
	// less than 1 character or more than 10 characters")
	private String levyNumberAdditional;

	/** The company type. */
	@Enumerated
	@Column(name = "company_type")
	private CompanyTypeEnum companyType;

	/** The institution Company. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institution_type_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "institutionType", fieldValue = "true")
	private InstitutionType institutionType;

	/** The form user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "form_user_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "formUser", fieldValue = "true")
	private Users formUser;

	/** The linked company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "linkedCompany", fieldValue = "true")
	private Company linkedCompany;

	/** The company status. */
	@Enumerated
	@Column(name = "company_status")
	private CompanyStatusEnum companyStatus;

	/** The approval enum. */
	@Enumerated
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;

	@Enumerated
	@Column(name = "categorization")
	private CategorizationEnum categorization;

	@Column(name = "on_gp", columnDefinition = "BIT default false")
	private Boolean onGP;

	/**
	 * This is the training provider accreditation number __-__/ACC/____/__
	 */
	@Column(name = "accreditation_number", nullable = true)
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "accreditationNumberValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Accreditation Number failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>", fieldName = "accreditationNumber", fieldValue = "NOT_NULL")
	private String accreditationNumber;

	/** The docs. */
	@Transient
	private List<Doc> docs;

	/** The linked companies. */
	@Transient
	private List<SDFCompany> linkedCompanies;

	@Transient
	private List<SkillsRegistration> skillsRegistrations;

	@Transient
	private List<LearnershipDevelopmentRegistration> learnershipDevelopmentRegistrations;

	@Transient
	private List<QualificationsCurriculumDevelopment> qualificationsCurriculumDevelopmentList;

	@Transient
	private List<CompanyHistory> companyHistories;

	/** The sites. */
	@Transient
	private List<Sites> sites;

	@Transient
	private BankingDetails bankingDetails;

	@Transient
	private CategorizationEnum categorizationLookUp;

	/* Total Courseware distribution request */
	@Transient
	private Integer totalRequest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */

	/** The done search. */
	@Transient
	private boolean doneSearch;

	/** The existing company. */
	@Transient
	private boolean existingCompany;

	/** The temp update. */
	@Transient
	private boolean tempUpdate;

	/** The reg done. */
	@Transient
	private boolean regDone;

	/** Note. */
	@Column(name = "reject_reason", columnDefinition = "LONGTEXT")
	private String rejectReason;

	@Column(name = "training_committee_in_place")
	private Boolean trainingCommitteeInPlace;

	/** The recognition agreement. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "recognition_agreement_id", nullable = true)
	private YesNoLookup recognitionAgreement;

	/** The majority union. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "majority_union_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "majorityUnion", fieldValue = "true")
	private OrganisedLabourUnion majorityUnion;

	/** The organisation type */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "organisation_type_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "organisationType", fieldValue = "true")
	private OrganisationType organisationType;

	@Column(name = "non_levy_paying")
	private Boolean nonLevyPaying;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "financial_year_start_date", length = 19)
	private Date financialYearStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "financial_year_end_date", length = 19)
	private Date financialYearEndDate;

	@Enumerated
	@Column(name = "financial_year_start_month")
	private Month financialYearStartMonth;

	@Column(name = "financial_year_start_day")
	private Integer financialYearStartDay;

	@Enumerated
	@Column(name = "financial_year_end_month")
	private Month financialYearEndMonth;

	@Column(name = "financial_year_end_day")
	private Integer financialYearEndDay;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "etqa_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "etqa", fieldValue = "true")
	private Etqa etqa;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "seta_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "seta", fieldValue = "true")
	private Seta seta;
	
	
	@Column(name = "non_seta_company", columnDefinition = "BIT default false")
	private Boolean nonSetaCompany;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_company_id", nullable = true)
	@SETMISFieldValidation(process = true,fieldName = "previousCompany", fieldValue = "true")
	private Company previousCompany;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deregister_date", length = 19)
	private Date deregisterDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deregister_user_id", nullable = true)
	private Users deregisterUser;
	
	@Column(name = "company_site_number", length = 10)
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "siteNumberValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Site Number Validation failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>", fieldName = "companySiteNumber", fieldValue = "NOT_NULL")
	private String companySiteNumber;
	
	@Column(name = "company_website", length = 50)
	@SETMISFieldValidation(className = CompanyValidationService.class, method = "companyWebsiteValidation", paramClass = String.class, message = "Validation Failed For SETMIS Company Website failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>", fieldName = "companyWebsite", fieldValue = "NOT_NULL")
	private String companyWebsite;

	@Transient
	private boolean expanded;

	@Column(name = "locked")
	private Boolean locked;

	@Column(name = "sars_trading_status", length = 1)
	private String sarsTradingStatus;

	@Column(name = "workplace_approval_number", length = 40, nullable = true)
	private String workplaceApprovalNumber;
	
	@Column(name = "use_trading_name_setmis", columnDefinition = "BIT default false")
	private Boolean useTradingNameSetmis;

	@Transient
	private String sarsTradingStatusDecoded;

	@Transient
	private boolean addToTrainingComittee;

	@Transient
	private SizeOfCompany sizeOfCompany;

	@Transient
	private String lastestGrantStatus;

	@Transient
	private String lastestDgVerificationStatus;

	@Transient
	private String lastestMgVerificationStatus;

	@Transient
	private RegionTown regionTown;

	@Transient
	private Region region;

	@Transient
	private BigDecimal discretionaryLevyForReport;

	@Transient
	private Users clo;

	@Transient
	private Users crm;

	@Transient
	private Users contactPerson;

	@Transient
	private TrainingProviderApplication trainingProviderApplication;

	@Transient
	private String setaRegisteredAt;
	
	@Transient
	private CompanyUpdateInfoBean companyUpdateInfoBean;
	
	@Transient
	private Boolean lockInfoUpdate;
	
	@Transient
	private Province province;

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
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Constructor. Creates a new instance of the company while setting the
	 * companyGuid in the process
	 */
	public Company() {
		super();
		this.companyGuid = UUID.randomUUID().toString();
		this.companyStatus = CompanyStatusEnum.Pending;
		this.trainingCommitteeInPlace = true;
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
	 *            the new create date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Transient
	public String getCompanyNameDisplay() {
		return companyName + ((levyNumber != null) ? " (" + levyNumber + ")" : "");
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName
	 *            the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Gets the trading name.
	 *
	 * @return the trading name
	 */
	public String getTradingName() {
		return tradingName;
	}

	/**
	 * Sets the trading name.
	 *
	 * @param tradingName
	 *            the new trading name
	 */
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	/**
	 * Gets the residential address.
	 *
	 * @return the residential address
	 */
	public Address getResidentialAddress() {
		return residentialAddress;
	}

	/**
	 * Sets the residential address.
	 *
	 * @param residentialAddress
	 *            the new residential address
	 */
	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Gets the postal address.
	 *
	 * @return the postal address
	 */
	public Address getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postal address.
	 *
	 * @param postalAddress
	 *            the new postal address
	 */
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * Gets the registered address.
	 *
	 * @return the registered address
	 */
	public Address getRegisteredAddress() {
		return registeredAddress;
	}

	/**
	 * Sets the registered address.
	 *
	 * @param registeredAddress
	 *            the new registered address
	 */
	public void setRegisteredAddress(Address registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	/**
	 * Gets the company registration number.
	 *
	 * @return the company registration number
	 */
	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}

	/**
	 * Sets the company registration number.
	 *
	 * @param companyRegistrationNumber
	 *            the new company registration number
	 */
	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}

	/**
	 * Gets the tel number.
	 *
	 * @return the tel number
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * Sets the tel number.
	 *
	 * @param telNumber
	 *            the new tel number
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	/**
	 * Gets the fax number.
	 *
	 * @return the fax number
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * Sets the fax number.
	 *
	 * @param faxNumber
	 *            the new fax number
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the chamber.
	 *
	 * @return the chamber
	 */
	public Chamber getChamber() {
		return chamber;
	}

	/**
	 * Sets the chamber.
	 *
	 * @param chamber
	 *            the new chamber
	 */
	public void setChamber(Chamber chamber) {
		this.chamber = chamber;
	}

	/**
	 * Gets the sic code.
	 *
	 * @return the sic code
	 */
	public SICCode getSicCode() {
		return sicCode;
	}

	/**
	 * Sets the sic code.
	 *
	 * @param sicCode
	 *            the new sic code
	 */
	public void setSicCode(SICCode sicCode) {
		this.sicCode = sicCode;
	}

	/**
	 * Gets the number of employees.
	 *
	 * @return the number of employees
	 */
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	/**
	 * Sets the number of employees.
	 *
	 * @param numberOfEmployees
	 *            the new number of employees
	 */
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	/**
	 * Gets the paye SDL number.
	 *
	 * @return the paye SDL number
	 */
	public String getPayeSDLNumber() {
		return payeSDLNumber;
	}

	/**
	 * Sets the paye SDL number.
	 *
	 * @param payeSDLNumber
	 *            the new paye SDL number
	 */
	public void setPayeSDLNumber(String payeSDLNumber) {
		this.payeSDLNumber = payeSDLNumber;
	}

	/**
	 * Gets the levy number.
	 *
	 * @return the levy number
	 */
	public String getLevyNumber() {
		return levyNumber;
	}

	/**
	 * Sets the levy number.
	 *
	 * @param levyNumber
	 *            the new levy number
	 */
	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	/**
	 * Gets the company guid.
	 *
	 * @return the company guid
	 */
	public String getCompanyGuid() {
		return companyGuid;
	}

	/**
	 * Sets the company guid.
	 *
	 * @param companyGuid
	 *            the new company guid
	 */
	public void setCompanyGuid(String companyGuid) {
		this.companyGuid = companyGuid;
	}

	/**
	 * Gets the levy number additional.
	 *
	 * @return the levy number additional
	 */
	public String getLevyNumberAdditional() {
		return levyNumberAdditional;
	}

	/**
	 * Sets the levy number additional.
	 *
	 * @param levyNumberAdditional
	 *            the new levy number additional
	 */
	public void setLevyNumberAdditional(String levyNumberAdditional) {
		this.levyNumberAdditional = levyNumberAdditional;
	}

	/**
	 * Gets the company type.
	 *
	 * @return the company type
	 */
	public CompanyTypeEnum getCompanyType() {
		return companyType;
	}

	/**
	 * Sets the company type.
	 *
	 * @param companyType
	 *            the new company type
	 */
	public void setCompanyType(CompanyTypeEnum companyType) {
		this.companyType = companyType;
	}

	/**
	 * Gets the form user.
	 *
	 * @return the form user
	 */
	public Users getFormUser() {
		return formUser;
	}

	/**
	 * Sets the form user.
	 *
	 * @param formUser
	 *            the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	/**
	 * Gets the company status.
	 *
	 * @return the company status
	 */
	public CompanyStatusEnum getCompanyStatus() {
		return companyStatus;
	}

	/**
	 * Sets the company status.
	 *
	 * @param companyStatus
	 *            the new company status
	 */
	public void setCompanyStatus(CompanyStatusEnum companyStatus) {
		this.companyStatus = companyStatus;
	}

	/**
	 * Checks if is done search.
	 *
	 * @return true, if is done search
	 */
	public boolean isDoneSearch() {
		return doneSearch;
	}

	/**
	 * Sets the done search.
	 *
	 * @param doneSearch
	 *            the new done search
	 */
	public void setDoneSearch(boolean doneSearch) {
		this.doneSearch = doneSearch;
	}

	/**
	 * Gets the docs.
	 *
	 * @return the docs
	 */
	public List<Doc> getDocs() {
		return docs;
	}

	/**
	 * Sets the docs.
	 *
	 * @param docs
	 *            the new docs
	 */
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	/**
	 * Checks if is existing company.
	 *
	 * @return true, if is existing company
	 */
	public boolean isExistingCompany() {
		return existingCompany;
	}

	/**
	 * Sets the existing company.
	 *
	 * @param existingCompany
	 *            the new existing company
	 */
	public void setExistingCompany(boolean existingCompany) {
		this.existingCompany = existingCompany;
	}

	/**
	 * Checks if is temp update.
	 *
	 * @return true, if is temp update
	 */
	public boolean isTempUpdate() {
		return tempUpdate;
	}

	/**
	 * Sets the temp update.
	 *
	 * @param tempUpdate
	 *            the new temp update
	 */
	public void setTempUpdate(boolean tempUpdate) {
		this.tempUpdate = tempUpdate;
	}

	/**
	 * Checks if is reg done.
	 *
	 * @return true, if is reg done
	 */
	public boolean isRegDone() {
		return regDone;
	}

	/**
	 * Sets the reg done.
	 *
	 * @param regDone
	 *            the new reg done
	 */
	public void setRegDone(boolean regDone) {
		this.regDone = regDone;
	}

	/**
	 * Gets the institution type.
	 *
	 * @return the institution type
	 */
	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	/**
	 * Sets the institution type.
	 *
	 * @param institutionType
	 *            the new institution type
	 */
	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	/**
	 * Gets the approval enum.
	 *
	 * @return the approval enum
	 */
	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	/**
	 * Sets the approval enum.
	 *
	 * @param approvalEnum
	 *            the new approval enum
	 */
	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	/**
	 * Gets the linked company.
	 *
	 * @return the linked company
	 */
	public Company getLinkedCompany() {
		return linkedCompany;
	}

	/**
	 * Sets the linked company.
	 *
	 * @param linkedCompany
	 *            the new linked company
	 */
	public void setLinkedCompany(Company linkedCompany) {
		this.linkedCompany = linkedCompany;
	}

	/**
	 * Gets the linked companies.
	 *
	 * @return the linked companies
	 */
	public List<SDFCompany> getLinkedCompanies() {
		return linkedCompanies;
	}

	/**
	 * Sets the linked companies.
	 *
	 * @param linkedCompanies
	 *            the new linked companies
	 */
	public void setLinkedCompanies(List<SDFCompany> linkedCompanies) {
		this.linkedCompanies = linkedCompanies;
	}

	/**
	 * Gets the valid company.
	 *
	 * @return the valid company
	 */
	@Transient
	@JsonIgnore
	public String getValidCompany() {
		StringBuilder errors = new StringBuilder("");
		if (companyName == null || companyName.isEmpty()) {
			errors.append("<li>Company name cannot be empty</li>");
		}
		if (tradingName == null || tradingName.isEmpty()) {
			errors.append("<li>Trading name cannot be empty</li>");
		}
		if (telNumber == null || telNumber.isEmpty()) {
			errors.append("<li>Tel number cannot be empty</li>");
		}
		if (residentialAddress == null) {
			errors.append("<li>Residential address cannot be empty</li>");
		} else {
			
			if (residentialAddress.getAddressLine1() == null || residentialAddress.getAddressLine1().isEmpty()) {
				errors.append("<li>Residential address line 1 cannot be empty</li>");
			}
			if (residentialAddress.getAddressLine2() == null || residentialAddress.getAddressLine2().isEmpty()) {
				errors.append("<li>Residential address line 2 cannot be empty</li>");
			}
			if (residentialAddress.getPostcode() == null || residentialAddress.getPostcode().isEmpty()) {
				errors.append("<li>Residential address postcode cannot be empty</li>");
			}
			
			if (residentialAddress.getLatitudeDegrees() == null) {
				errors.append("<li>Residential address latitude: degrees cannot be empty</li>");
			}
			if (residentialAddress.getLatitudeMinutes() == null) {
				errors.append("<li>Residential address latitude: minutes cannot be empty</li>");
			}
			if (residentialAddress.getLatitudeSeconds() == null) {
				errors.append("<li>Residential address latitude: seconds cannot be empty</li>");
			}
			
			if (residentialAddress.getLongitudeDegrees() == null) {
				errors.append("<li>Residential address longitude: degrees cannot be empty</li>");
			}
			if (residentialAddress.getLongitudeMinutes() == null) {
				errors.append("<li>Residential address longitude: minutes cannot be empty</li>");
			}
			if (residentialAddress.getLongitudeSeconds() == null) {
				errors.append("<li>Residential address longitude: seconds cannot be empty</li>");
			}
		}
		if (postalAddress == null) {
			errors.append("<li>Postal address cannot be empty</li>");
		} else {
			if (postalAddress.getAddressLine1() == null || postalAddress.getAddressLine1().isEmpty()) {
				errors.append("<li>Postal address line 1 cannot be empty</li>");
			}
			if (postalAddress.getAddressLine2() == null || postalAddress.getAddressLine2().isEmpty()) {
				errors.append("<li>Postal address line 2 cannot be empty</li>");
			}
			if (postalAddress.getPostcode() == null || postalAddress.getPostcode().isEmpty()) {
				errors.append("<li>Postal address postcode cannot be empty</li>");
			}
		}
		if (companyRegistrationNumber == null || companyRegistrationNumber.isEmpty()) {
			errors.append("<li>Company registration number cannot be empty</li>");
		}
		if (email == null || email.isEmpty()) {
			errors.append("<li>Email cannot be empty</li>");
		}

		boolean levyNumberProvided = (nonLevyPaying == null || !nonLevyPaying) && (levyNumber == null || levyNumber.isEmpty());
		boolean accrediationNumberProvided = accreditationNumber == null;
		if (levyNumberProvided && accrediationNumberProvided) {
			errors.append("<li>Levy number cannot be empty</li>");
		}

		if (sicCode == null) {
			errors.append("<li>SIC code cannot be empty</li>");
		}
		if (numberOfEmployees == null) {
			errors.append("<li>Number of employees cannot be empty</li>");
		}
		return errors.toString();
	}

	@Transient
	@JsonIgnore
	public String getValidProviderCompany() {
		StringBuilder errors = new StringBuilder("");
		if (companyName == null || companyName.isEmpty()) {
			errors.append("<li>Company name cannot be empty</li>");
		}
		if (tradingName == null || tradingName.isEmpty()) {
			errors.append("<li>Trading name cannot be empty</li>");
		}
		if (telNumber == null || telNumber.isEmpty()) {
			errors.append("<li>Tel number cannot be empty</li>");
		}
		if (residentialAddress == null) {
			errors.append("<li>Residential address cannot be empty</li>");

		} else {
			if (residentialAddress.getAddressLine1() == null || residentialAddress.getAddressLine1().isEmpty()) {
				errors.append("<li>Residential address line 1 cannot be empty</li>");
			}
			if (residentialAddress.getAddressLine2() == null || residentialAddress.getAddressLine2().isEmpty()) {
				errors.append("<li>Residential address line 2 cannot be empty</li>");
			}
			if (residentialAddress.getPostcode() == null || residentialAddress.getPostcode().isEmpty()) {
				errors.append("<li>Residential address postcode cannot be empty</li>");
			}
			
			if (residentialAddress.getLatitudeDegrees() == null) {
				errors.append("<li>Residential address latitude: degrees cannot be empty</li>");
			}
			if (residentialAddress.getLatitudeMinutes() == null) {
				errors.append("<li>Residential address latitude: minutes cannot be empty</li>");
			}
			if (residentialAddress.getLatitudeSeconds() == null) {
				errors.append("<li>Residential address latitude: seconds cannot be empty</li>");
			}
			
			if (residentialAddress.getLongitudeDegrees() == null) {
				errors.append("<li>Residential address longitude: degrees cannot be empty</li>");
			}
			if (residentialAddress.getLongitudeMinutes() == null) {
				errors.append("<li>Residential address longitude: minutes cannot be empty</li>");
			}
			if (residentialAddress.getLongitudeSeconds() == null) {
				errors.append("<li>Residential address longitude: seconds cannot be empty</li>");
			}
		}
		if (postalAddress == null) {
			errors.append("<li>Postal address cannot be empty</li>");
		} else {
			if (postalAddress.getAddressLine1() == null || postalAddress.getAddressLine1().isEmpty()) {
				errors.append("<li>Postal address line 1 cannot be empty</li>");
			}
			if (postalAddress.getAddressLine2() == null || postalAddress.getAddressLine2().isEmpty()) {
				errors.append("<li>Postal address line 2 cannot be empty</li>");
			}
			if (postalAddress.getPostcode() == null || postalAddress.getPostcode().isEmpty()) {
				errors.append("<li>Postal address postcode cannot be empty</li>");
			}
		}
		if (companyRegistrationNumber == null || companyRegistrationNumber.isEmpty()) {
			errors.append("<li>Company registration number cannot be empty</li>");
		}
		if (email == null || email.isEmpty()) {
			errors.append("<li>Email cannot be empty</li>");
		}

		boolean levyNumberProvided = (nonLevyPaying == null || !nonLevyPaying) && (levyNumber == null || levyNumber.isEmpty());
		boolean accrediationNumberProvided = accreditationNumber == null;
		if (levyNumberProvided && accrediationNumberProvided) {
			errors.append("<li>Levy number cannot be empty</li>");
		}
		// if ((nonLevyPaying == null || !nonLevyPaying) && (levyNumber == null ||
		// levyNumber.isEmpty())) {
		// errors.append("<li>Levy number cannot be empty</li>");
		// }
		if (sicCode == null) {
			errors.append("<li>SIC code cannot be empty</li>");
		}
		return errors.toString();
	}

	/**
	 * Gets the sites.
	 *
	 * @return the sites
	 */
	public List<Sites> getSites() {
		return sites;
	}

	/**
	 * Sets the sites.
	 *
	 * @param sites
	 *            the new sites
	 */
	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}

	/**
	 * Gets the reject reason.
	 *
	 * @return the reject reason
	 */
	public String getRejectReason() {
		return rejectReason;
	}

	/**
	 * Sets the reject reason.
	 *
	 * @param rejectReason
	 *            the new reject reason
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public YesNoLookup getRecognitionAgreement() {
		return recognitionAgreement;
	}

	public void setRecognitionAgreement(YesNoLookup recognitionAgreement) {
		this.recognitionAgreement = recognitionAgreement;
	}

	public CategorizationEnum getCategorization() {
		return categorization;
	}

	public void setCategorization(CategorizationEnum categorization) {
		this.categorization = categorization;
	}

	public OrganisedLabourUnion getMajorityUnion() {
		return majorityUnion;
	}

	public void setMajorityUnion(OrganisedLabourUnion majorityUnion) {
		this.majorityUnion = majorityUnion;
	}

	public OrganisationType getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(OrganisationType organisationType) {
		this.organisationType = organisationType;
	}

	public Boolean getTrainingCommitteeInPlace() {
		return trainingCommitteeInPlace;
	}

	public void setTrainingCommitteeInPlace(Boolean trainingCommitteeInPlace) {
		this.trainingCommitteeInPlace = trainingCommitteeInPlace;
	}

	public Boolean getNonLevyPaying() {
		return nonLevyPaying;
	}

	public void setNonLevyPaying(Boolean nonLevyPaying) {
		this.nonLevyPaying = nonLevyPaying;
	}

	public Date getFinancialYearStartDate() {
		return financialYearStartDate;
	}

	public void setFinancialYearStartDate(Date financialYearStartDate) {
		this.financialYearStartDate = financialYearStartDate;
	}

	public Date getFinancialYearEndDate() {
		return financialYearEndDate;
	}

	public void setFinancialYearEndDate(Date financialYearEndDate) {
		this.financialYearEndDate = financialYearEndDate;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getSarsTradingStatus() {
		return sarsTradingStatus;
	}

	public void setSarsTradingStatus(String sarsTradingStatus) {
		this.sarsTradingStatus = sarsTradingStatus;
	}

	public String getSarsTradingStatusDecoded() {
		this.sarsTradingStatusDecoded = SARSConstants.getStatus(this.sarsTradingStatus);
		return sarsTradingStatusDecoded;
	}

	public void setSarsTradingStatusDecoded(String sarsTradingStatusDecoded) {
		this.sarsTradingStatusDecoded = sarsTradingStatusDecoded;
	}

	public Month getFinancialYearStartMonth() {
		return financialYearStartMonth;
	}

	public void setFinancialYearStartMonth(Month financialYearStartMonth) {
		this.financialYearStartMonth = financialYearStartMonth;
	}

	public Integer getFinancialYearStartDay() {
		return financialYearStartDay;
	}

	public void setFinancialYearStartDay(Integer financialYearStartDay) {
		this.financialYearStartDay = financialYearStartDay;
	}

	public Month getFinancialYearEndMonth() {
		return financialYearEndMonth;
	}

	public void setFinancialYearEndMonth(Month financialYearEndMonth) {
		this.financialYearEndMonth = financialYearEndMonth;
	}

	public Integer getFinancialYearEndDay() {
		return financialYearEndDay;
	}

	public void setFinancialYearEndDay(Integer financialYearEndDay) {
		this.financialYearEndDay = financialYearEndDay;
	}

	public boolean isAddToTrainingComittee() {
		return addToTrainingComittee;
	}

	public void setAddToTrainingComittee(boolean addToTrainingComittee) {
		this.addToTrainingComittee = addToTrainingComittee;
	}

	public SizeOfCompany getSizeOfCompany() {
		return sizeOfCompany;
	}

	public void setSizeOfCompany(SizeOfCompany sizeOfCompany) {
		this.sizeOfCompany = sizeOfCompany;
	}

	public List<CompanyHistory> getCompanyHistories() {
		return companyHistories;
	}

	public void setCompanyHistories(List<CompanyHistory> companyHistories) {
		this.companyHistories = companyHistories;
	}

	public Boolean getOnGP() {
		return onGP;
	}

	public void setOnGP(Boolean onGP) {
		this.onGP = onGP;
	}

	public List<SkillsRegistration> getSkillsRegistrations() {
		return skillsRegistrations;
	}

	public void setSkillsRegistrations(List<SkillsRegistration> skillsRegistrations) {
		this.skillsRegistrations = skillsRegistrations;
	}

	public List<LearnershipDevelopmentRegistration> getLearnershipDevelopmentRegistrations() {
		return learnershipDevelopmentRegistrations;
	}

	public void setLearnershipDevelopmentRegistrations(List<LearnershipDevelopmentRegistration> learnershipDevelopmentRegistrations) {
		this.learnershipDevelopmentRegistrations = learnershipDevelopmentRegistrations;
	}

	public List<QualificationsCurriculumDevelopment> getQualificationsCurriculumDevelopmentList() {
		return qualificationsCurriculumDevelopmentList;
	}

	public void setQualificationsCurriculumDevelopmentList(List<QualificationsCurriculumDevelopment> qualificationsCurriculumDevelopmentList) {
		this.qualificationsCurriculumDevelopmentList = qualificationsCurriculumDevelopmentList;
	}

	public Etqa getEtqa() {
		return etqa;
	}

	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

	public String getLastestGrantStatus() {
		return lastestGrantStatus;
	}

	public void setLastestGrantStatus(String lastestGrantStatus) {
		this.lastestGrantStatus = lastestGrantStatus;
	}

	public String getLastestDgVerificationStatus() {
		return lastestDgVerificationStatus;
	}

	public void setLastestDgVerificationStatus(String lastestDgVerificationStatus) {
		this.lastestDgVerificationStatus = lastestDgVerificationStatus;
	}

	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}

	/**
	 * @return the regionTown
	 */
	public RegionTown getRegionTown() {
		return regionTown;
	}

	/**
	 * @param regionTown
	 *            the regionTown to set
	 */
	public void setRegionTown(RegionTown regionTown) {
		this.regionTown = regionTown;
	}

	public BigDecimal getDiscretionaryLevyForReport() {
		return discretionaryLevyForReport;
	}

	public void setDiscretionaryLevyForReport(BigDecimal discretionaryLevyForReport) {
		this.discretionaryLevyForReport = discretionaryLevyForReport;
	}

	public Users getClo() {
		return clo;
	}

	public void setClo(Users clo) {
		this.clo = clo;
	}

	public Users getCrm() {
		return crm;
	}

	public void setCrm(Users crm) {
		this.crm = crm;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public CategorizationEnum getCategorizationLookUp() {
		return categorizationLookUp;
	}

	public void setCategorizationLookUp(CategorizationEnum categorizationLookUp) {
		this.categorizationLookUp = categorizationLookUp;
	}

	public Users getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Users contactPerson) {
		this.contactPerson = contactPerson;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public Integer getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(Integer totalRequest) {
		this.totalRequest = totalRequest;
	}

	public Seta getSeta() {
		return seta;
	}

	public void setSeta(Seta seta) {
		this.seta = seta;
	}

	public Boolean getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(Boolean nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public String getLastestMgVerificationStatus() {
		return lastestMgVerificationStatus;
	}

	public void setLastestMgVerificationStatus(String lastestMgVerificationStatus) {
		this.lastestMgVerificationStatus = lastestMgVerificationStatus;
	}

	public String getWorkplaceApprovalNumber() {
		return workplaceApprovalNumber;
	}

	public void setWorkplaceApprovalNumber(String workplaceApprovalNumber) {
		this.workplaceApprovalNumber = workplaceApprovalNumber;
	}

	public Company getPreviousCompany() {
		return previousCompany;
	}

	public void setPreviousCompany(Company previousCompany) {
		this.previousCompany = previousCompany;
	}

	public Date getDeregisterDate() {
		return deregisterDate;
	}

	public void setDeregisterDate(Date deregisterDate) {
		this.deregisterDate = deregisterDate;
	}

	public Users getDeregisterUser() {
		return deregisterUser;
	}

	public void setDeregisterUser(Users deregisterUser) {
		this.deregisterUser = deregisterUser;
	}

	public String getSetaRegisteredAt() {
		return setaRegisteredAt;
	}

	public void setSetaRegisteredAt(String setaRegisteredAt) {
		this.setaRegisteredAt = setaRegisteredAt;
	}

	public String getCompanySiteNumber() {
		return companySiteNumber;
	}

	public void setCompanySiteNumber(String companySiteNumber) {
		this.companySiteNumber = companySiteNumber;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public Date getValidiationRanDate() {
		return validiationRanDate;
	}

	public void setValidiationRanDate(Date validiationRanDate) {
		this.validiationRanDate = validiationRanDate;
	}

	public CompanyUpdateInfoBean getCompanyUpdateInfoBean() {
		return companyUpdateInfoBean;
	}

	public void setCompanyUpdateInfoBean(CompanyUpdateInfoBean companyUpdateInfoBean) {
		this.companyUpdateInfoBean = companyUpdateInfoBean;
	}

	public Boolean getLockInfoUpdate() {
		return lockInfoUpdate;
	}

	public void setLockInfoUpdate(Boolean lockInfoUpdate) {
		this.lockInfoUpdate = lockInfoUpdate;
	}

	public Boolean getUseTradingNameSetmis() {
		return useTradingNameSetmis;
	}

	public void setUseTradingNameSetmis(Boolean useTradingNameSetmis) {
		this.useTradingNameSetmis = useTradingNameSetmis;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
}
