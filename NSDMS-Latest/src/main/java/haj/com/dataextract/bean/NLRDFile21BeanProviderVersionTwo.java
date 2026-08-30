package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile21BeanProviderVersionTwo implements IDataEntity {

	/**
	 * NLRD Files 
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 21 NLRD Provider Details
	 * 
	 * @author Arno
	 */
	
	/** File 21 - Provider Details */

	/** Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/** Etqa_Id * Required: Y (also known as providerETQAId) */
	@CSVAnnotation(length = 10)
	private String etqaId;

	/** Std_Industry_Class_Code * Required: N (also known as sic_codes) */
	@CSVAnnotation(length = 10)
	private String stdIndustryClassCode;

	/** Provider_Name * Required: Y */
	@CSVAnnotation(length = 128)
	private String providerName;

	/** Provider_Type_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerTypeId;

	/** Provider_Address_1 * Required: Y (also known as providerPostalAddress1) */
	@CSVAnnotation(length = 50)
	private String providerAddress1;

	/** Provider_Address_2 * Required: Y (also known as providerPostalAddress2) */
	@CSVAnnotation(length = 50)
	private String providerAddress2;

	/** Provider_Address_3 * Required: N (also known as providerPostalAddress3) */
	@CSVAnnotation(length = 50)
	private String providerAddress3;

	/** Provider_Postal_Code * Required: Y (also known as providerPostalAddressCode) */
	@CSVAnnotation(length = 4)
	private String providerPostalCode;

	/** Provider_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerPhoneNumber;

	/** Provider_Fax_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerFaxNumber;

	/** Provider_Sars_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerSarsNumber;

	/** Provider_Contact_Name * Required: N */
	@CSVAnnotation(length = 50)
	private String providerContactName;

	/** Provider_Contact_Email_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String providerContactEmailAddress;

	/** Provider_Contact_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerContactPhoneNumber;

	/** Provider_Contact_Cell_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerContactCellNumber;

	/** Provider_Accreditation_Num * Required: N */
	@CSVAnnotation(length = 20)
	private String providerAccreditationNum;

	/** Provider_Accredit_Start_Date * Required: C */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccreditStartDate;

	/** Provider_Accredit_End_Date * Required: C */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccreditEndDate;

	/** Etqa_Decision_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;

	/** Provider_Class_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerClassId;

	/** Structure_Status_Id * Required: Y (also known as providerStatusId ) */
	@CSVAnnotation(length = 10)
	private String structureStatusId;

	/** Province_Code * Required: Y */
	@CSVAnnotation(length = 2)
	private String provinceCode;

	/** Country_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String countryCode;

	/** Latitude_Degree * Required: C */
	@CSVAnnotation(length = 3)
	private String latitudeDegree;

	/** Latitude_Minutes * Required: C */
	@CSVAnnotation(length = 2)
	private String latitudeMinutes;

	/** Latitude_Seconds * Required: C */
	@CSVAnnotation(length = 6)
	private String latitudeSeconds;

	/** Longitude_Degree * Required: C */
	@CSVAnnotation(length = 2)
	private String longitudeDegree;

	/** Longitude_Minutes * Required: C */
	@CSVAnnotation(length = 2)
	private String longitudeMinutes;

	/** Longitude_Seconds * Required: C */
	@CSVAnnotation(length = 6)
	private String longitudeSeconds;

	/** Provider_Physical_Address_1 * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress1;

	/** Provider_Physical_Address_2 * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress2;

	/** Provider_Physical_Address_Town * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddressTown;

	/** Provider_Phys_Address_Postcode * Required: N */
	@CSVAnnotation(length = 4)
	private String providerPhysAddressPostcode;

	/** Provider_Web_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String providerWebAddress;

	/** Date Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	
	/*
	 * SUBSTRING('', 1, 20) as providerCode,
	 * SUBSTRING('', 1, 10) as etqaId,
	 * SUBSTRING('', 1, 10) as stdIndustryClassCode,
	 * SUBSTRING('', 1, 128) as providerName,
	 * SUBSTRING('', 1, 10) as providerTypeId,
	 * SUBSTRING('', 1, 50) as providerAddress1,
	 * SUBSTRING('', 1, 50) as providerAddress2,
	 * SUBSTRING('', 1, 50) as providerAddress3,
	 * SUBSTRING('', 1, 4) as providerPostalCode,
     * SUBSTRING('', 1, 20) as providerPhoneNumber,
     * SUBSTRING('', 1, 20) as providerFaxNumber,
     * SUBSTRING('', 1, 20) as providerSarsNumber,
     * SUBSTRING('', 1, 50) as providerContactName,
     * SUBSTRING('', 1, 50) as providerContactEmailAddress,
     * SUBSTRING('', 1, 20) as providerContactPhoneNumber,
     * SUBSTRING('', 1, 20) as providerContactCellNumber,
     * SUBSTRING('', 1, 20) as providerAccreditationNum,
     * '' as providerAccreditStartDate,
     * '' as providerAccreditEndDate,
     * SUBSTRING('', 1, 20) as etqaDecisionNumber,
     * SUBSTRING('', 1, 10) as providerClassId,
     * SUBSTRING('', 1, 10) as structureStatusId,
     * SUBSTRING('', 1, 2) as provinceCode,
     * SUBSTRING('', 1, 4) as countryCode,
     * SUBSTRING('', 1, 3) as latitudeDegree,
     * SUBSTRING('', 1, 2) as latitudeMinutes,
     * SUBSTRING('', 1, 6) as latitudeSeconds,
     * SUBSTRING('', 1, 2) as longitudeDegree,
     * SUBSTRING('', 1, 2) as longitudeMinutes,
     * SUBSTRING('', 1, 6) as longitudeSeconds,
     * SUBSTRING('', 1, 50) as providerPhysicalAddress1,
     * SUBSTRING('', 1, 50) as providerPhysicalAddress2,
     * SUBSTRING('', 1, 50) as providerPhysicalAddressTown,
     * SUBSTRING('', 1, 4) as providerPhysAddressPostcode,
     * SUBSTRING('', 1, 50) as providerWebAddress,
     * '' as dateStamp
	 */
	
	public NLRDFile21BeanProviderVersionTwo() {
		super();
	}

	public NLRDFile21BeanProviderVersionTwo(String providerCode, String etqaId, String stdIndustryClassCode, String providerName, String providerTypeId, String providerAddress1, String providerAddress2, String providerAddress3, String providerPostalCode, String providerPhoneNumber, String providerFaxNumber, String providerSarsNumber, String providerContactName, String providerContactEmailAddress, String providerContactPhoneNumber, String providerContactCellNumber, String providerAccreditationNum, Date providerAccreditStartDate, Date providerAccreditEndDate, String etqaDecisionNumber,
			String providerClassId, String structureStatusId, String provinceCode, String countryCode, String latitudeDegree, String latitudeMinutes, String latitudeSeconds, String longitudeDegree, String longitudeMinutes, String longitudeSeconds, String providerPhysicalAddress1, String providerPhysicalAddress2, String providerPhysicalAddressTown, String providerPhysAddressPostcode, String providerWebAddress, Date dateStamp) {
		super();
		this.providerCode = providerCode;
		this.etqaId = etqaId;
		this.stdIndustryClassCode = stdIndustryClassCode;
		this.providerName = providerName;
		this.providerTypeId = providerTypeId;
		this.providerAddress1 = providerAddress1;
		this.providerAddress2 = providerAddress2;
		this.providerAddress3 = providerAddress3;
		this.providerPostalCode = providerPostalCode;
		this.providerPhoneNumber = providerPhoneNumber;
		this.providerFaxNumber = providerFaxNumber;
		this.providerSarsNumber = providerSarsNumber;
		this.providerContactName = providerContactName;
		this.providerContactEmailAddress = providerContactEmailAddress;
		this.providerContactPhoneNumber = providerContactPhoneNumber;
		this.providerContactCellNumber = providerContactCellNumber;
		this.providerAccreditationNum = providerAccreditationNum;
		this.providerAccreditStartDate = providerAccreditStartDate;
		this.providerAccreditEndDate = providerAccreditEndDate;
		this.etqaDecisionNumber = etqaDecisionNumber;
		this.providerClassId = providerClassId;
		this.structureStatusId = structureStatusId;
		this.provinceCode = provinceCode;
		this.countryCode = countryCode;
		this.latitudeDegree = latitudeDegree;
		this.latitudeMinutes = latitudeMinutes;
		this.latitudeSeconds = latitudeSeconds;
		this.longitudeDegree = longitudeDegree;
		this.longitudeMinutes = longitudeMinutes;
		this.longitudeSeconds = longitudeSeconds;
		this.providerPhysicalAddress1 = providerPhysicalAddress1;
		this.providerPhysicalAddress2 = providerPhysicalAddress2;
		this.providerPhysicalAddressTown = providerPhysicalAddressTown;
		this.providerPhysAddressPostcode = providerPhysAddressPostcode;
		this.providerWebAddress = providerWebAddress;
		this.dateStamp = dateStamp;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getEtqaId() {
		return etqaId;
	}

	public void setEtqaId(String etqaId) {
		this.etqaId = etqaId;
	}

	public String getStdIndustryClassCode() {
		return stdIndustryClassCode;
	}

	public void setStdIndustryClassCode(String stdIndustryClassCode) {
		this.stdIndustryClassCode = stdIndustryClassCode;
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

	public String getProviderAddress1() {
		return providerAddress1;
	}

	public void setProviderAddress1(String providerAddress1) {
		this.providerAddress1 = providerAddress1;
	}

	public String getProviderAddress2() {
		return providerAddress2;
	}

	public void setProviderAddress2(String providerAddress2) {
		this.providerAddress2 = providerAddress2;
	}

	public String getProviderAddress3() {
		return providerAddress3;
	}

	public void setProviderAddress3(String providerAddress3) {
		this.providerAddress3 = providerAddress3;
	}

	public String getProviderPostalCode() {
		return providerPostalCode;
	}

	public void setProviderPostalCode(String providerPostalCode) {
		this.providerPostalCode = providerPostalCode;
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

	public Date getProviderAccreditStartDate() {
		return providerAccreditStartDate;
	}

	public void setProviderAccreditStartDate(Date providerAccreditStartDate) {
		this.providerAccreditStartDate = providerAccreditStartDate;
	}

	public Date getProviderAccreditEndDate() {
		return providerAccreditEndDate;
	}

	public void setProviderAccreditEndDate(Date providerAccreditEndDate) {
		this.providerAccreditEndDate = providerAccreditEndDate;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public String getProviderClassId() {
		return providerClassId;
	}

	public void setProviderClassId(String providerClassId) {
		this.providerClassId = providerClassId;
	}

	public String getStructureStatusId() {
		return structureStatusId;
	}

	public void setStructureStatusId(String structureStatusId) {
		this.structureStatusId = structureStatusId;
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

	public String getProviderPhysicalAddressTown() {
		return providerPhysicalAddressTown;
	}

	public void setProviderPhysicalAddressTown(String providerPhysicalAddressTown) {
		this.providerPhysicalAddressTown = providerPhysicalAddressTown;
	}

	public String getProviderPhysAddressPostcode() {
		return providerPhysAddressPostcode;
	}

	public void setProviderPhysAddressPostcode(String providerPhysAddressPostcode) {
		this.providerPhysAddressPostcode = providerPhysAddressPostcode;
	}

	public String getProviderWebAddress() {
		return providerWebAddress;
	}

	public void setProviderWebAddress(String providerWebAddress) {
		this.providerWebAddress = providerWebAddress;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
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
}