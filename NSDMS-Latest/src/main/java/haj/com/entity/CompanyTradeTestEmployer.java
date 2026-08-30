package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import haj.com.framework.IDataEntity;
import haj.com.validators.CheckTelNumber;

// TODO: Auto-generated Javadoc
/**
 * Company.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_trade_test_employer")
public class CompanyTradeTestEmployer implements IDataEntity, Cloneable {

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
	private String tradingName;

	/**
	 * The residential address of Company. The residential address of Company can be
	 * null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "residential_address_id", nullable = true)
	private Address residentialAddress;

	/**
	 * The postal address Company. The postal address Company can be null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postal_address_id", nullable = true)
	private Address postalAddress;

	/**
	 * The registered address of Company. The registered address of company can be
	 * null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "registered_address_id", nullable = true)
	private Address registeredAddress;

	/**
	 * The tel number of Company. Field length = 20 This field may be left blank
	 * Field may not start with a space. Uppercase value in field may only contain
	 * characters 1234567890 ()/- Uppercase value in field may not contain
	 * characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U
	 * or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or
	 */
	@CheckTelNumber(message = "Tel number not valid")
	@Column(name = "tel_number", length = 20, nullable = true)
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
	private String faxNumber;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Column(name = "levy_number", length = 10)
	private String levyNumber;

	/**
	 * The email of Company. Field length = 50 This field may be left blank Field
	 * may not start with a space. Uppercase value in field may only contain
	 * characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@ Uppercase value in
	 * field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or
	 * %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL
	 * or - or Field must contain the @ character
	 */
	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String email;

	/** The first name. */
	@Column(name = "user_first_name", length = 100, nullable = true)
	private String userFirstName;

	@Column(name = "user_middle_name", length = 100, nullable = true)
	private String userMiddleName;

	/** The last name. */
	@Column(name = "user_last_name", length = 100, nullable = false)
	private String userLastName;
	
	@Column(name = "user_email", length = 50, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String userEmail;
	
	@CheckTelNumber(message = "Tel number not valid")
	@Column(name = "user_tel_number", length = 20, nullable = true)
	private String userTelNumber;
	
	/** The cell number. */
	@CheckTelNumber(message = "Cell number not valid")
	@Column(name = "user_cell_number", length = 20, nullable = true)
	private String userCellNumber;
	
	
	
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyGuid() {
		return companyGuid;
	}

	public void setCompanyGuid(String companyGuid) {
		this.companyGuid = companyGuid;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Address getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(Address registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
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

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserMiddleName() {
		return userMiddleName;
	}

	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserTelNumber() {
		return userTelNumber;
	}

	public void setUserTelNumber(String userTelNumber) {
		this.userTelNumber = userTelNumber;
	}

	public String getUserCellNumber() {
		return userCellNumber;
	}

	public void setUserCellNumber(String userCellNumber) {
		this.userCellNumber = userCellNumber;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

}
