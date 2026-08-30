package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.UrbanRuralEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.Nationality;
import haj.com.entity.lookup.Title;
import haj.com.framework.IDataEntity;
import haj.com.validators.CheckID;
import haj.com.validators.CheckTelNumber;


// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "user_change_request")
public class UserChangeRequest implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The first name. */
	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;

	@Column(name = "middle_name", length = 100, nullable = true)
	private String middleName;

	/** The last name. */
	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;

	/** The nationality. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationality_id", nullable = true)
	private Nationality nationality;
	
	/** The nationality. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	/** The rsa ID number. */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "rsa_id_number", length = 13, nullable = true)
	private String rsaIDNumber;

	/** The passport number. */
	@Column(name = "passport_number", length = 30, nullable = true)
	private String passportNumber;
	
	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	

	/** The tel number. */
	@CheckTelNumber(message = "Tel number not valid")
	@Column(name = "tel_number", length = 20, nullable = true)
	private String telNumber;

	/** The cell number. */
	@CheckTelNumber(message = "Cell number not valid")
	@Column(name = "cell_number", length = 20, nullable = true)
	private String cellNumber;

	/** The fax number. */
	@CheckTelNumber(message = "Fax number not valid")
	@Column(name = "fax_number", length = 20, nullable = true)
	private String faxNumber;
	
	/** The email. */
	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String email;
	
	/** The gender. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = true)
	private Gender gender;
	
	/** The equity. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equity_id", nullable = true)
	private Equity equity;
	
	/** The disability status. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disabilityStatus")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	private DisabilityStatus disabilityStatus;
	
	/** The title. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", nullable = true)
	private Title title;

	@Enumerated
	@Column(name = "urban_rural_enum")
	private UrbanRuralEnum urbanRuralEnum;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legal_gaurdian_id", nullable = true)
	private Users legalGaurdian;
	
	@Enumerated
	@Column(name = "disability")
	private YesNoEnum disability;
	
	/** The date of birth. */
	@Column(name = "date_of_birth", nullable = true)
	private Date dateOfBirth;
	
	@Enumerated
	@Column(name = "maried")
	private YesNoEnum maried;
	
	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	public UserChangeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public UserChangeRequest(Users user) {
		super();
		this.user = user;
		this.firstName=user.getFirstName();
		this.middleName=user.getMiddleName();
		this.lastName=user.getLastName();
		this.nationality=user.getNationality();
		this.rsaIDNumber=user.getRsaIDNumber();
		this.passportNumber=user.getPassportNumber();
		this.telNumber=user.getTelNumber();
		this.cellNumber=user.getCellNumber();
		this.faxNumber=user.getFaxNumber();
		this.email=user.getEmail();
		this.gender=user.getGender();
		this.equity=user.getEquity();
		this.disabilityStatus=user.getDisabilityStatus();
		this.title=user.getTitle();
		this.urbanRuralEnum = user.getUrbanRuralEnum();
		this.legalGaurdian = user.getLegalGaurdian();
		this.disability = user.getDisability();
		this.dateOfBirth = user.getDateOfBirth();
		this.maried = user.getMaried();
	}

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
		UserChangeRequest other = (UserChangeRequest) obj;
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
	 * @param id the id to set
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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getRsaIDNumber() {
		return rsaIDNumber;
	}

	public void setRsaIDNumber(String rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Equity getEquity() {
		return equity;
	}

	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	public DisabilityStatus getDisabilityStatus() {
		return disabilityStatus;
	}

	public void setDisabilityStatus(DisabilityStatus disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public UrbanRuralEnum getUrbanRuralEnum() {
		return urbanRuralEnum;
	}

	public void setUrbanRuralEnum(UrbanRuralEnum urbanRuralEnum) {
		this.urbanRuralEnum = urbanRuralEnum;
	}

	public Users getLegalGaurdian() {
		return legalGaurdian;
	}

	public void setLegalGaurdian(Users legalGaurdian) {
		this.legalGaurdian = legalGaurdian;
	}

	public YesNoEnum getDisability() {
		return disability;
	}

	public void setDisability(YesNoEnum disability) {
		this.disability = disability;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public YesNoEnum getMaried() {
		return maried;
	}

	public void setMaried(YesNoEnum maried) {
		this.maried = maried;
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
}
