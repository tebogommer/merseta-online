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
import haj.com.service.DataExtractService;

@Entity
@Table(name = "setmis_file_100_extracted")
public class SetmisFile100Extracted implements IDataEntity {

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

	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;

	@CSVAnnotation(length = 10)
	@Column(name = "provideretqaid")
	private String providerETQAId;

	@CSVAnnotation(length = 10)
	@Column(name = "siccode")
	private String sICCode;

	@CSVAnnotation(length = 70)
	@Column(name = "providername")
	private String providerName;

	@CSVAnnotation(length = 10)
	@Column(name = "providertypeid")
	private String providerTypeId;

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

	@CSVAnnotation(length = 20)
	@Column(name = "providerphonenumber")
	private String providerPhoneNumber;

	@CSVAnnotation(length = 20)
	@Column(name = "providerfaxnumber")
	private String providerFaxNumber;

	@CSVAnnotation(length = 20)
	@Column(name = "providersarsnumber")
	private String providerSarsNumber;

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

	@CSVAnnotation(length = 20)
	@Column(name = "provideraccreditationnum")
	private String providerAccreditationNum;

	@CSVAnnotation(length = 8)
	@Column(name = "providerstartdatestring")
	private String providerStartDateString;
	// private Date providerStartDate;

	@CSVAnnotation(length = 8)
	@Column(name = "providerenddatestring")
	private String providerEndDateString;
	// private Date providerEndDateString;

	@CSVAnnotation(length = 20)
	@Column(name = "etqedecisionnumber")
	private String etqeDecisionNumber;

	@CSVAnnotation(length = 10)
	@Column(name = "providerclassid")
	private String providerClassId;

	@CSVAnnotation(length = 10)
	@Column(name = "providerstatusid")
	private String providerStatusId;

	@CSVAnnotation(length = 2)
	@Column(name = "provincecode")
	private String provinceCode;

	@CSVAnnotation(length = 4)
	@Column(name = "countrycode")
	private String countryCode;

	@CSVAnnotation(length = 3)
	@Column(name = "latitudedegreestring")
	private String latitudeDegreeString;
	// private Double latitudeDegree;

	@CSVAnnotation(length = 2)
	@Column(name = "latitudeminutesstring")
	private String latitudeMinutesString;
	// private Double latitudeMinutes;

	@CSVAnnotation(length = 6)
	@Column(name = "latitudesecondsstring")
	private String latitudeSecondsString;
	// private Double latitudeSeconds;

	@CSVAnnotation(length = 2)
	@Column(name = "longitudedegreestring")
	private String longitudeDegreeString;
	// private Double longitudeDegree;

	@CSVAnnotation(length = 2)
	@Column(name = "longitudeminutesstring")
	private String longitudeMinutesString;
	// private Double longitudeMinutes;

	@CSVAnnotation(length = 6)
	@Column(name = "longitudesecondsstring")
	private String longitudeSecondsString;
	// private Double longitudeSeconds;

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

	@CSVAnnotation(length = 50)
	@Column(name = "providerwebaddress")
	private String providerWebAddress;

	@CSVAnnotation(length = 10)
	@Column(name = "sdlno")
	private String sDLNo;

	@CSVAnnotation(length = 8)
	@Column(name = "dateStampString")
	private String dateStampString;
	// private Date dateStamp;

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
		SetmisFile100Extracted other = (SetmisFile100Extracted) obj;
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

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderETQAId() {
		return providerETQAId;
	}

	public void setProviderETQAId(String providerETQAId) {
		this.providerETQAId = providerETQAId;
	}

	public String getsICCode() {
		return sICCode;
	}

	public void setsICCode(String sICCode) {
		this.sICCode = sICCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderTypeId() {
		return providerTypeId;
	}

	public void setProviderTypeId(String providerTypeId) {
		this.providerTypeId = providerTypeId;
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

	public String getProviderSarsNumber() {
		return providerSarsNumber;
	}

	public void setProviderSarsNumber(String providerSarsNumber) {
		this.providerSarsNumber = providerSarsNumber;
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

	public String getProviderAccreditationNum() {
		return providerAccreditationNum;
	}

	public void setProviderAccreditationNum(String providerAccreditationNum) {
		this.providerAccreditationNum = providerAccreditationNum;
	}

	public String getProviderStartDateString() {
		return providerStartDateString;
	}

	public void setProviderStartDateString(String providerStartDateString) {
		this.providerStartDateString = providerStartDateString;
	}

	public String getProviderEndDateString() {
		return providerEndDateString;
	}

	public void setProviderEndDateString(String providerEndDateString) {
		this.providerEndDateString = providerEndDateString;
	}

	public String getEtqeDecisionNumber() {
		return etqeDecisionNumber;
	}

	public void setEtqeDecisionNumber(String etqeDecisionNumber) {
		this.etqeDecisionNumber = etqeDecisionNumber;
	}

	public String getProviderClassId() {
		return providerClassId;
	}

	public void setProviderClassId(String providerClassId) {
		this.providerClassId = providerClassId;
	}

	public String getProviderStatusId() {
		return providerStatusId;
	}

	public void setProviderStatusId(String providerStatusId) {
		this.providerStatusId = providerStatusId;
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

	public String getLatitudeDegreeString() {
		return latitudeDegreeString;
	}

	public void setLatitudeDegreeString(String latitudeDegreeString) {
		this.latitudeDegreeString = latitudeDegreeString;
	}

	public String getLatitudeMinutesString() {
		return latitudeMinutesString;
	}

	public void setLatitudeMinutesString(String latitudeMinutesString) {
		this.latitudeMinutesString = latitudeMinutesString;
	}

	public String getLatitudeSecondsString() {
		return latitudeSecondsString;
	}

	public void setLatitudeSecondsString(String latitudeSecondsString) {
		this.latitudeSecondsString = latitudeSecondsString;
	}

	public String getLongitudeDegreeString() {
		return longitudeDegreeString;
	}

	public void setLongitudeDegreeString(String longitudeDegreeString) {
		this.longitudeDegreeString = longitudeDegreeString;
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

	public String getProviderWebAddress() {
		return providerWebAddress;
	}

	public void setProviderWebAddress(String providerWebAddress) {
		this.providerWebAddress = providerWebAddress;
	}

	public String getsDLNo() {
		return sDLNo;
	}

	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}

	public String getLongitudeMinutesString() {
		return longitudeMinutesString;
	}

	public void setLongitudeMinutesString(String longitudeMinutesString) {
		this.longitudeMinutesString = longitudeMinutesString;
	}

	public String getLongitudeSecondsString() {
		return longitudeSecondsString;
	}

	public void setLongitudeSecondsString(String longitudeSecondsString) {
		this.longitudeSecondsString = longitudeSecondsString;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}

}