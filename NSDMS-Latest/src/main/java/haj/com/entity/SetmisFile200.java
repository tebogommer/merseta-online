package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.ExtractFilename;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;

// TODO: Auto-generated Javadoc
/**
 * SetmisFile200.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_200")
@ExtractFilename(fileName = "Setmis File 200")
public class SetmisFile200 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile200. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	@Column(name = "sdl_no")
	private String sDLNo;
	
	@Column(name = "site_no")
	private BigInteger siteNo;
	
	@Column(name = "seta_id")
	private String sETAId;
	
	@Column(name = "sic_code")
	private String sICCode;
	
	@Column(name = "employer_registration_number")
	private String employerRegistrationNumber;
	
	@Column(name = "employer_company_name")
	private String employerCompanyName;
	
	@Column(name = "employer_trading_name")
	private String employerTradingName;
	
	@Column(name = "employer_postal_address_1")
	private String employerPostalAddress1;
	
	@Column(name = "employer_postal_address_2")
	private String employerPostalAddress2;
	
	@Column(name = "employer_postal_address_3")
	private String employerPostalAddress3;
	
	@Column(name = "employer_postal_address_code")
	private String employerPostalAddressCode;
	
	@Column(name = "employer_physical_address_1")
	private String employerPhysicalAddress1;
	
	@Column(name = "employer_physical_address_2")
	private String employerPhysicalAddress2;
	
	@Column(name = "employer_physical_address_3")
	private String employerPhysicalAddress3;
	
	@Column(name = "employer_physical_address_code")
	private String employerPhysicalAddressCode;
	
	@Column(name = "employer_phone_number")
	private String employerPhoneNumber;
	
	@Column(name = "employer_fax_number")
	private String employerFaxNumber;
	
	@Column(name = "employer_contact_name")
	private String employerContactName;
	
	@Column(name = "employer_contact_email_address")
	private String employerContactEmailAddress;
	
	@Column(name = "employer_contact_phone_number")
	private String employerContactPhoneNumber;
	
	@Column(name = "employer_contact_cell_number")
	private String employerContactCellNumber;
	
	@Column(name = "employer_approval_status_id")
	private String employerApprovalStatusId;
	
	@Column(name = "employer_approval_status_start_date")
	private Date employerApprovalStatusStartDate;
	
	@Column(name = "employer_approval_status_end_date")
	private Date employerApprovalStatusEndDate;
	
	@Column(name = "employer_approval_status_num")
	private String employerApprovalStatusNum;
	
	@Column(name = "province_code")
	private String provinceCode;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "latitude_degree")
	private String latitudeDegree;
	
	@Column(name = "latitude_minutes")
	private String latitudeMinutes;
	
	@Column(name = "latitude_seconds")
	private String latitudeSeconds;
	
	@Column(name = "longitude_degree")
	private String longitudeDegree;
	
	@Column(name = "longitude_minutes")
	private String longitudeMinutes;
	
	@Column(name = "longitude_seconds")
	private String longitudeSeconds;
		
	@Column(name = "main_sdl_no")
	private String mainSDLNo;
	
	@Column(name = "filler_01")
	private String filler01;
	
	@Column(name = "filler_02")
	private String filler02;
	
	@Column(name = "date_stamp")
	private Date dateStamp;

	
    
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
		SetmisFile200 other = (SetmisFile200) obj;
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

	public String getsDLNo() {
		return sDLNo;
	}

	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}

	public BigInteger getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(BigInteger siteNo) {
		this.siteNo = siteNo;
	}

	public String getsETAId() {
		return sETAId;
	}

	public void setsETAId(String sETAId) {
		this.sETAId = sETAId;
	}

	public String getsICCode() {
		return sICCode;
	}

	public void setsICCode(String sICCode) {
		this.sICCode = sICCode;
	}

	public String getEmployerRegistrationNumber() {
		return employerRegistrationNumber;
	}

	public void setEmployerRegistrationNumber(String employerRegistrationNumber) {
		this.employerRegistrationNumber = employerRegistrationNumber;
	}

	public String getEmployerCompanyName() {
		return employerCompanyName;
	}

	public void setEmployerCompanyName(String employerCompanyName) {
		this.employerCompanyName = employerCompanyName;
	}

	public String getEmployerTradingName() {
		return employerTradingName;
	}

	public void setEmployerTradingName(String employerTradingName) {
		this.employerTradingName = employerTradingName;
	}

	public String getEmployerPostalAddress1() {
		return employerPostalAddress1;
	}

	public void setEmployerPostalAddress1(String employerPostalAddress1) {
		this.employerPostalAddress1 = employerPostalAddress1;
	}

	public String getEmployerPostalAddress2() {
		return employerPostalAddress2;
	}

	public void setEmployerPostalAddress2(String employerPostalAddress2) {
		this.employerPostalAddress2 = employerPostalAddress2;
	}

	public String getEmployerPostalAddress3() {
		return employerPostalAddress3;
	}

	public void setEmployerPostalAddress3(String employerPostalAddress3) {
		this.employerPostalAddress3 = employerPostalAddress3;
	}

	public String getEmployerPostalAddressCode() {
		return employerPostalAddressCode;
	}

	public void setEmployerPostalAddressCode(String employerPostalAddressCode) {
		this.employerPostalAddressCode = employerPostalAddressCode;
	}

	public String getEmployerPhysicalAddress1() {
		return employerPhysicalAddress1;
	}

	public void setEmployerPhysicalAddress1(String employerPhysicalAddress1) {
		this.employerPhysicalAddress1 = employerPhysicalAddress1;
	}

	public String getEmployerPhysicalAddress2() {
		return employerPhysicalAddress2;
	}

	public void setEmployerPhysicalAddress2(String employerPhysicalAddress2) {
		this.employerPhysicalAddress2 = employerPhysicalAddress2;
	}

	public String getEmployerPhysicalAddress3() {
		return employerPhysicalAddress3;
	}

	public void setEmployerPhysicalAddress3(String employerPhysicalAddress3) {
		this.employerPhysicalAddress3 = employerPhysicalAddress3;
	}

	public String getEmployerPhoneNumber() {
		return employerPhoneNumber;
	}

	public void setEmployerPhoneNumber(String employerPhoneNumber) {
		this.employerPhoneNumber = employerPhoneNumber;
	}

	public String getEmployerFaxNumber() {
		return employerFaxNumber;
	}

	public void setEmployerFaxNumber(String employerFaxNumber) {
		this.employerFaxNumber = employerFaxNumber;
	}

	public String getEmployerContactName() {
		return employerContactName;
	}

	public void setEmployerContactName(String employerContactName) {
		this.employerContactName = employerContactName;
	}

	public String getEmployerContactEmailAddress() {
		return employerContactEmailAddress;
	}

	public void setEmployerContactEmailAddress(String employerContactEmailAddress) {
		this.employerContactEmailAddress = employerContactEmailAddress;
	}

	public String getEmployerContactPhoneNumber() {
		return employerContactPhoneNumber;
	}

	public void setEmployerContactPhoneNumber(String employerContactPhoneNumber) {
		this.employerContactPhoneNumber = employerContactPhoneNumber;
	}

	public String getEmployerContactCellNumber() {
		return employerContactCellNumber;
	}

	public void setEmployerContactCellNumber(String employerContactCellNumber) {
		this.employerContactCellNumber = employerContactCellNumber;
	}

	public String getEmployerApprovalStatusId() {
		return employerApprovalStatusId;
	}

	public void setEmployerApprovalStatusId(String employerApprovalStatusId) {
		this.employerApprovalStatusId = employerApprovalStatusId;
	}

	public Date getEmployerApprovalStatusStartDate() {
		return employerApprovalStatusStartDate;
	}

	public void setEmployerApprovalStatusStartDate(Date employerApprovalStatusStartDate) {
		this.employerApprovalStatusStartDate = employerApprovalStatusStartDate;
	}

	public Date getEmployerApprovalStatusEndDate() {
		return employerApprovalStatusEndDate;
	}

	public void setEmployerApprovalStatusEndDate(Date employerApprovalStatusEndDate) {
		this.employerApprovalStatusEndDate = employerApprovalStatusEndDate;
	}

	public String getEmployerApprovalStatusNum() {
		return employerApprovalStatusNum;
	}

	public void setEmployerApprovalStatusNum(String employerApprovalStatusNum) {
		this.employerApprovalStatusNum = employerApprovalStatusNum;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLatitudeDegree() {
		return latitudeDegree;
	}

	public void setLatitudeDegree(String latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}

	public String getLatitudeMinutes() {
		return latitudeMinutes;
	}

	public void setLatitudeMinutes(String latitudeMinutes) {
		this.latitudeMinutes = latitudeMinutes;
	}

	public String getLatitudeSeconds() {
		return latitudeSeconds;
	}

	public void setLatitudeSeconds(String latitudeSeconds) {
		this.latitudeSeconds = latitudeSeconds;
	}

	public String getLongitudeDegree() {
		return longitudeDegree;
	}

	public void setLongitudeDegree(String longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}

	public String getLongitudeMinutes() {
		return longitudeMinutes;
	}

	public void setLongitudeMinutes(String longitudeMinutes) {
		this.longitudeMinutes = longitudeMinutes;
	}

	public String getLongitudeSeconds() {
		return longitudeSeconds;
	}

	public void setLongitudeSeconds(String longitudeSeconds) {
		this.longitudeSeconds = longitudeSeconds;
	}

	public String getMainSDLNo() {
		return mainSDLNo;
	}

	public void setMainSDLNo(String mainSDLNo) {
		this.mainSDLNo = mainSDLNo;
	}

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}

	public String getFiller02() {
		return filler02;
	}

	public void setFiller02(String filler02) {
		this.filler02 = filler02;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getEmployerPhysicalAddressCode() {
		return employerPhysicalAddressCode;
	}

	public void setEmployerPhysicalAddressCode(String employerPhysicalAddressCode) {
		this.employerPhysicalAddressCode = employerPhysicalAddressCode;
	}
}
