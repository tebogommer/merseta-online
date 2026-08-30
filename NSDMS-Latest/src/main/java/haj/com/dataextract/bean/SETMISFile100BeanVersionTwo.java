package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class SETMISFile100BeanVersionTwo implements IDataEntity {

	/**
	 * SETMIS Files
	 * Version Two Bean
	 * 
	 * 20 December 2019
	 * 
	 * Enhanced extract added for file 100 SETMIS
	 * 
	 * @author jonathanbowett
	 * 
	 */

	/**
	 * File 100 - Provider
	 */

	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerETQAId;
	/** * SIC_Code * Required: N */
	@CSVAnnotation(length = 10)
	private String sICCode;
	/** * Provider_Name * Required: Y */
	@CSVAnnotation(length = 70)
	private String providerName;
	/** * Provider_Type_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerTypeId;
	/** * Provider_Postal_Address_1 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPostalAddress1;
	/** * Provider_Postal_Address_2 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPostalAddress2;
	/** * Provider_Postal_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPostalAddress3;
	/** * Provider_Postal_Address_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String providerPostalAddressCode;
	/** * Provider_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerPhoneNumber;
	/** * Provider_Fax_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerFaxNumber;
	/** * Provider_Sars_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerSarsNumber;
	/** * Provider_Contact_Name * Required: N */
	@CSVAnnotation(length = 50)
	private String providerContactName;
	/** * Provider_Contact_Email_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String providerContactEmailAddress;
	/** * Provider_Contact_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerContactPhoneNumber;
	/** * Provider_Contact_Cell_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerContactCellNumber;
	/** * Provider_Accreditation_Num * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerAccreditationNum;
	/** * Provider_Start_Date * Required: N */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerStartDate;
	/** * Provider_End_Date * Required: N */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerEndDate;
	/** * Etqe_Decision_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String etqeDecisionNumber;
	/** * Provider_Class_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String providerClassId;
	/** * Provider_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerStatusId;
	/** * Province_Code * Required: Y */
	@CSVAnnotation(length = 2)
	private String provinceCode;
	/** * Country_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String countryCode;
	/** * Latitude_Degree * Required: Y */
	@CSVAnnotation(length = 3)
	private String latitudeDegree;
	/** * Latitude_Minutes * Required: Y */
	@CSVAnnotation(length = 2)
	private String latitudeMinutes;
	/** * Latitude_Seconds * Required: Y */
	@CSVAnnotation(length = 6)
	private String latitudeSeconds;
	/** * Longitude_Degree * Required: Y */
	@CSVAnnotation(length = 2)
	private String longitudeDegree;
	/** * Longitude_Minutes * Required: Y */
	@CSVAnnotation(length = 2)
	private String longitudeMinutes;
	/** * Longitude_Seconds * Required: Y */
	@CSVAnnotation(length = 6)
	private String longitudeSeconds;
	/** * Provider_Physical_Address_1 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress1;
	/** * Provider_Physical_Address_2 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress2;
	/** * Provider_Physical_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress3;
	/** * Provider_Physical_Address_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String providerPhysicalAddressCode;
	/** * Provider_Web_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String providerWebAddress;
	/** * SDL_No * Required: N */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** Date_Stamp Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;

	public SETMISFile100BeanVersionTwo() {
		super();
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

	public Date getProviderStartDate() {
		return providerStartDate;
	}

	public void setProviderStartDate(Date providerStartDate) {
		this.providerStartDate = providerStartDate;
	}

	public Date getProviderEndDate() {
		return providerEndDate;
	}

	public void setProviderEndDate(Date providerEndDate) {
		this.providerEndDate = providerEndDate;
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
