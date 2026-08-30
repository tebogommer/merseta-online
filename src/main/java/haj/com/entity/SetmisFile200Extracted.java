package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "setmis_file_200_extracted")
public class SetmisFile200Extracted implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "line_number")
	private String lineNumber;
	
	
	@CSVAnnotation(length = 10)
	@Column(name = "sdlno")
	private String sDLNo;
	
	@CSVAnnotation(length = 10)
	@Column(name = "siteno")
	private String siteNo;
	
	@CSVAnnotation(length = 3)
	@Column(name = "setaidstring")
	private String sETAIdString;
//	private BigInteger sETAId;
	
	@CSVAnnotation(length = 10)
	@Column(name = "siccode")
	private String sICCode;
	
	@CSVAnnotation(length = 20)
	@Column(name = "employerregistrationnumber")
	private String employerRegistrationNumber;
	
	@CSVAnnotation(length = 70)
	@Column(name = "employercompanyname")
	private String employerCompanyName;
	
	@CSVAnnotation(length = 70)
	@Column(name = "employertradingname")
	private String employerTradingName;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providerpostaladdress1")
	private String providerPostalAddress1;
	
	
	@CSVAnnotation(length = 50)
	@Column(name = "providerpostaladdress2")
	private String providerPostalAddress2;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providerpostaladdress3")
	private String providerPostalAddress3;
	
	@CSVAnnotation(length = 4)
	@Column(name = "providerpostaladdresscode")
	private String providerPostalAddressCode;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providerphysicaladdress1")
	private String providerPhysicalAddress1;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providerphysicaladdress2")
	private String providerPhysicalAddress2;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providerphysicaladdress3")
	private String providerPhysicalAddress3;
	
	@CSVAnnotation(length = 4)
	@Column(name = "providerphysicaladdresscode")
	private String providerPhysicalAddressCode;
	
	@CSVAnnotation(length = 20)
	@Column(name = "providerphonenumber")
	private String providerPhoneNumber;
	
	@CSVAnnotation(length = 20)
	@Column(name = "providerfaxnumber")
	private String providerFaxNumber;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providercontactname")
	private String providerContactName;
	
	@CSVAnnotation(length = 50)
	@Column(name = "providercontactemailaddress")
	private String providerContactEmailAddress;
	
	@CSVAnnotation(length = 20)
	@Column(name = "providercontactphonenumber")
	private String providerContactPhoneNumber;
	
	@CSVAnnotation(length = 20)
	@Column(name = "providercontactcellnumber")
	private String providerContactCellNumber;
	
	@CSVAnnotation(length = 10)
	@Column(name = "employerapprovalstatusidstring")
	private String employerApprovalStatusIdString;
//	private BigInteger employerApprovalStatusId;
	
	@CSVAnnotation(length = 8)
	@Column(name = "employerapprovalstatusstartdatestring")
	private String employerApprovalStatusStartDateString;
//	private Date employerApprovalStatusStartDate;
	
	@CSVAnnotation(length = 8)
	@Column(name = "employerapprovalstatusenddate")
	private String employerApprovalStatusEndDate;
	
	@CSVAnnotation(length = 20)
	@Column(name = "employerapprovalstatusnum")
	private String employerApprovalStatusNum;
	
	@CSVAnnotation(length = 2)
	@Column(name = "provincecode")
	private String provinceCode;
	
	@CSVAnnotation(length = 4)
	@Column(name = "countrycode")
	private String countryCode;
	
	@CSVAnnotation(length = 3)
	@Column(name = "latitudedegree")
	private String latitudeDegree;
	
	@CSVAnnotation(length = 2)
	@Column(name = "latitudeminutes")
	private String latitudeMinutes;
	
	@CSVAnnotation(length = 6)
	@Column(name = "latitudeseconds")
	private String latitudeSeconds;

	@CSVAnnotation(length = 2)
	@Column(name = "longitudedegree")
	private String longitudeDegree;
	
	@CSVAnnotation(length = 2)
	@Column(name = "longitudeminutes")
	private String longitudeMinutes;
	
	
	@CSVAnnotation(length = 6)
	@Column(name = "longitudeseconds")
	private String longitudeSeconds;
	
	@CSVAnnotation(length = 10)
	@Column(name = "mainsdlno")
	private String mainSDLNo;
	

	@CSVAnnotation(length = 20)
	@Column(name = "filler01")
	private String filler01;
	
	@CSVAnnotation(length = 4)
	@Column(name = "filler02")
	private String filler02;
	

	@CSVAnnotation(length = 8)
	@Column(name = "datestampstring")
	private String dateStampString;
//	private Date dateStamp;

	

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
		SetmisFile200Extracted other = (SetmisFile200Extracted) obj;
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

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getsDLNo() {
		return sDLNo;
	}

	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}

	public String getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}

	public String getsETAIdString() {
		return sETAIdString;
	}

	public void setsETAIdString(String sETAIdString) {
		this.sETAIdString = sETAIdString;
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

	public String getProviderPostalAddress1() {
		return providerPostalAddress1;
	}

	public void setProviderPostalAddress1(String providerPostalAddress1) {
		this.providerPostalAddress1 = providerPostalAddress1;
	}

	public String getProviderPostalAddress2() {
		return providerPostalAddress2;
	}

	public void setProviderPostalAddress2(String providerPostalAddress2) {
		this.providerPostalAddress2 = providerPostalAddress2;
	}

	public String getProviderPostalAddress3() {
		return providerPostalAddress3;
	}

	public void setProviderPostalAddress3(String providerPostalAddress3) {
		this.providerPostalAddress3 = providerPostalAddress3;
	}

	public String getProviderPostalAddressCode() {
		return providerPostalAddressCode;
	}

	public void setProviderPostalAddressCode(String providerPostalAddressCode) {
		this.providerPostalAddressCode = providerPostalAddressCode;
	}

	public String getProviderPhysicalAddress1() {
		return providerPhysicalAddress1;
	}

	public void setProviderPhysicalAddress1(String providerPhysicalAddress1) {
		this.providerPhysicalAddress1 = providerPhysicalAddress1;
	}

	public String getProviderPhysicalAddress2() {
		return providerPhysicalAddress2;
	}

	public void setProviderPhysicalAddress2(String providerPhysicalAddress2) {
		this.providerPhysicalAddress2 = providerPhysicalAddress2;
	}

	public String getProviderPhysicalAddress3() {
		return providerPhysicalAddress3;
	}

	public void setProviderPhysicalAddress3(String providerPhysicalAddress3) {
		this.providerPhysicalAddress3 = providerPhysicalAddress3;
	}

	public String getProviderPhysicalAddressCode() {
		return providerPhysicalAddressCode;
	}

	public void setProviderPhysicalAddressCode(String providerPhysicalAddressCode) {
		this.providerPhysicalAddressCode = providerPhysicalAddressCode;
	}

	public String getProviderPhoneNumber() {
		return providerPhoneNumber;
	}

	public void setProviderPhoneNumber(String providerPhoneNumber) {
		this.providerPhoneNumber = providerPhoneNumber;
	}

	public String getProviderFaxNumber() {
		return providerFaxNumber;
	}

	public void setProviderFaxNumber(String providerFaxNumber) {
		this.providerFaxNumber = providerFaxNumber;
	}

	public String getProviderContactName() {
		return providerContactName;
	}

	public void setProviderContactName(String providerContactName) {
		this.providerContactName = providerContactName;
	}

	public String getProviderContactEmailAddress() {
		return providerContactEmailAddress;
	}

	public void setProviderContactEmailAddress(String providerContactEmailAddress) {
		this.providerContactEmailAddress = providerContactEmailAddress;
	}

	public String getProviderContactPhoneNumber() {
		return providerContactPhoneNumber;
	}

	public void setProviderContactPhoneNumber(String providerContactPhoneNumber) {
		this.providerContactPhoneNumber = providerContactPhoneNumber;
	}

	public String getProviderContactCellNumber() {
		return providerContactCellNumber;
	}

	public void setProviderContactCellNumber(String providerContactCellNumber) {
		this.providerContactCellNumber = providerContactCellNumber;
	}

	public String getEmployerApprovalStatusIdString() {
		return employerApprovalStatusIdString;
	}

	public void setEmployerApprovalStatusIdString(String employerApprovalStatusIdString) {
		this.employerApprovalStatusIdString = employerApprovalStatusIdString;
	}

	public String getEmployerApprovalStatusStartDateString() {
		return employerApprovalStatusStartDateString;
	}

	public void setEmployerApprovalStatusStartDateString(String employerApprovalStatusStartDateString) {
		this.employerApprovalStatusStartDateString = employerApprovalStatusStartDateString;
	}

	public String getEmployerApprovalStatusEndDate() {
		return employerApprovalStatusEndDate;
	}

	public void setEmployerApprovalStatusEndDate(String employerApprovalStatusEndDate) {
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

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}
}