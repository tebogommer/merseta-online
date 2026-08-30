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
import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.OrganisedLabourUnion;
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
@Table(name = "training_comittee_hostory")
public class TrainingComitteeHistory implements IDataEntity, Cloneable {

	public TrainingComitteeHistory() {
		super();
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 * The create date. Date length = 19
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/**
	 * The company. Must be a valid company May not be left blank
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	// @NotEmpty(message ="Company is required")
	private Company company;

	/** The id type. */
	@Column(name = "id_type")
	@Enumerated
	private IdPassportEnum idType;

	/**
	 * The rsa ID number. Field length = 15 This field may be left blank Field may
	 * not start with a space. Uppercase value in field may only contain characters
	 * 1234567890 Both National_Id and Person_Alternate_Id may not be blank Value
	 * must have a length of exactly 13 Field value may not have characters 0000
	 * from characters 7 to 10 Field may not have characters 0000 from characters 1
	 * to 4 Uppercase value in field may not contain characters 1111111111111 or
	 * 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or
	 * 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 Field must
	 * be blank if POPI_Act_Status_ID has a value of 2
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "rsa_id_number", length = 13, nullable = true)
	// @Size(min = 13, max = 13, message = "RSA ID number must be 13 characters")
	private String rsaIDNumber;

	/**
	 * The passport number. Field length = 20 This field may be left blank Field may
	 * not start with a space. Uppercase value in field may only contain characters
	 * ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_ Uppercase value in field may not
	 * contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or
	 * NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  If
	 * Person_Alternate_Id has a value then Alternate_Id_Type_Id may not = 533 If
	 * Person_Alternate_Id IS blank then Alternate_Id_Type_Id must = 533
	 */
	@Column(name = "passport_number", length = 20, nullable = true)
	// @Size(min = 1, max = 20, message = "Passport number can't be less than 1
	// character or more than 20 characters")
	private String passportNumber;

	/**
	 * The first name. Field length = 26 This field not may be left blank Field may
	 * not start with a space Uppercase value in field may only contain characters
	 * ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- Uppercase value in field may not contain
	 * characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or
	 * TEST or %ONTBREEK% or NIL or - or  Field must be blank if POPI_Act_Status_ID
	 * has a value of 2 Uppercase value in field should not contain characters NA or
	 * U or NONE or GEEN
	 */
	// First Name
	@Column(name = "first_name", length = 26)
	// @Size(min = 1, max = 26, message = "First name can't be less than 1 character
	// or more than 26 characters")
	// @NotEmpty(message ="First name is required")
	private String firstName;
	// Last Name

	/**
	 * The last name. Field length = 45 This field may not be left blank Field may
	 * not start with a space. Uppercase value in field may only contain characters
	 * ABCDEFGHIJKLMNOPQRTSUVWXYZ`'  Uppercase value in field may not contain
	 * characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or
	 * TEST or %ONTBREEK% or NIL or - or  Field must be blank if POPI_Act_Status_ID
	 * has a value of 2 Value must be provided if POPI_Act_Status_ID does not have a
	 * value of 2 Uppercase value in field should not contain characters NA or U or
	 * NONE or GEEN
	 */
	@Column(name = "last_name", length = 45)
	// @Size(min = 1, max = 45, message = "Last name can't be less than 1 character
	// or more than 45 characters")
	// @NotEmpty(message ="Last name is required")
	private String lastName;

	/**
	 * The gender. Field length = 1 This field may not be left blank Only valid code
	 * indicators will be allowed. Field may not start with a space. Uppercase value
	 * in field may only contain characters
	 * ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'- Field must contain a
	 * valid Gender_Code If National_Id is not blank then Gender_Code must match
	 * gender character of National_Id. The 7th character of a SA National ID
	 * indicates a gender of female when less than 5 and male when greater than 4.
	 */
	// Gender
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = true)
	// @NotEmpty(message ="Gender is required")
	private Gender gender;

	/**
	 * The equity. Must be a valid Equity Field may be left blank
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equity_id", nullable = true)
	private Equity equity;

	/**
	 * The title. Must be a valid title Field may not be left blank
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", nullable = true)
	// @NotEmpty(message ="Title is required")
	private Title title;

	/**
	 * The tel number. Field length = 20 This field may be left blank Uppercase
	 * value in field may only contain characters 1234567890 ()/- Uppercase value in
	 * field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or
	 * %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL
	 * or - or  Field must be blank if POPI_Act_Status_ID has a value of 2
	 */
	@CheckTelNumber(message="Tel number not valid")
	@Column(name = "tel_number", length = 20, nullable = true)
	// @Size(min = 1, max = 20, message = "Tel number can't be less than 1 character
	// or more than 20 characters")
	private String telNumber;

	/**
	 * The cell number. Field length = 20 This field may be left blank Field may not
	 * start with a space. Uppercase value in field may only contain characters
	 * 1234567890 ()- Uppercase value in field may not contain characters %UNKNOWN%
	 * or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0
	 * or TEST or %ONTBREEK% or NIL or - or  Field must be blank if
	 * POPI_Act_Status_ID has a value of 2
	 */
	@CheckTelNumber(message="Cell number not valid")
	@Column(name = "cell_number", length = 20, nullable = true)
	// @Size(min = 1, max = 20, message = "Cell number can't be less than 1
	// character or more than 20 characters")
	private String cellNumber;

	/**
	 * The fax number. Field length = 20 This field may be left blank Field may not
	 * start with a space. Uppercase value in field may only contain characters
	 * 1234567890 ()/- Uppercase value in field may not contain characters %UNKNOWN%
	 * or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0
	 * or TEST or %ONTBREEK% or NIL or - or  Field must be blank if
	 * POPI_Act_Status_ID has a value of 2
	 */
	@Column(name = "fax_number", length = 20, nullable = true)
	// @Size(min = 1, max = 20, message = "Fax number can't be less than 1 character
	// or more than 20 characters")
	private String faxNumber;

	/**
	 * The email. Field length = 50 This field may be left blank Field may not start
	 * with a space. Uppercase value in field may only contain characters
	 * ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@ Uppercase value in field may not
	 * contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or
	 * NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  Field
	 * must contain the @ character
	 */
	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	// @Size(min = 1, max = 50, message = "Email can't be less than 1 character or
	// more than 50 characters")
	private String email;

	/**
	 * The union name. Field length = 100 Field may not start with a space.
	 */
	@Column(name = "union_name", length = 100)
	// @Size(min = 1, max = 100, message = "Union can't be less than 1 character or
	// more than 100 characters")
	private String unionName;

	/**
	 * The title. Must be a valid title Field may not be left blank
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "union_id", nullable = true)
	// @NotEmpty(message ="Title is required")
	private OrganisedLabourUnion union;
	
	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	/** The For training Committee. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="for_training_comittee", nullable = true)
	private TrainingComittee forTrainingComittee;


	public TrainingComitteeHistory(TrainingComittee entity) 
	{
		this.forTrainingComittee=entity;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainingComitteeHistory other = (TrainingComitteeHistory) obj;
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

	/**
	 * Gets the id type.
	 *
	 * @return the id type
	 */
	public IdPassportEnum getIdType() {
		return idType;
	}

	/**
	 * Sets the id type.
	 *
	 * @param idType
	 *            the new id type
	 */
	public void setIdType(IdPassportEnum idType) {
		this.idType = idType;
	}

	/**
	 * Gets the rsa ID number.
	 *
	 * @return the rsa ID number
	 */
	public String getRsaIDNumber() {
		return rsaIDNumber;
	}

	/**
	 * Sets the rsa ID number.
	 *
	 * @param rsaIDNumber
	 *            the new rsa ID number
	 */
	public void setRsaIDNumber(String rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	/**
	 * Gets the passport number.
	 *
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber
	 *            the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the equity.
	 *
	 * @return the equity
	 */
	public Equity getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 *
	 * @param equity
	 *            the new equity
	 */
	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(Title title) {
		this.title = title;
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
	 * Gets the cell number.
	 *
	 * @return the cell number
	 */
	public String getCellNumber() {
		return cellNumber;
	}

	/**
	 * Sets the cell number.
	 *
	 * @param cellNumber
	 *            the new cell number
	 */
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
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
	 * Gets the union name.
	 *
	 * @return the union name
	 */
	public String getUnionName() {
		return unionName;
	}

	/**
	 * Sets the union name.
	 *
	 * @param unionName
	 *            the new union name
	 */
	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public OrganisedLabourUnion getUnion() {
		return union;
	}

	public void setUnion(OrganisedLabourUnion union) {
		this.union = union;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public TrainingComittee getForTrainingComittee() {
		return forTrainingComittee;
	}

	public void setForTrainingComittee(TrainingComittee forTrainingComittee) {
		this.forTrainingComittee = forTrainingComittee;
	}

}
