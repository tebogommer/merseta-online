package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class NLRDFile21Bean  implements IDataEntity {
	
	
	/** NLRD Files spec as at 18 May 2018
	 */
	
	/** File 21 - Provider Details
	 */
	
	/**
	 * Provider_Code Required: Y
	 */
	@CSVAnnotation(length = 20)
	private String providerCode;
	
	
	/** 
	 * Etqa_Id 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 10)
	private String etqaId;
	
	
	/** 
	 * Std_Industry_Class_Code 
	 * Required: N 
	 */
	@CSVAnnotation(length = 10)
	private String stdIndustryClassCode;
	
	
	/** 
	 * Provider_Name 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 128)
	private String providerName;
	
	
	/** 
	 * Provider_Type_Id 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 10)
	private String providerTypeId;
	
	
	/** 
	 * Provider_Address_1 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 50)
	private String providerAddress1;
	
	
	/** 
	 * Provider_Address_2 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 50)
	private String providerAddress2;
	
	
	/** 
	 * Provider_Address_3 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerAddress3;
	
	
	/** 
	 * Provider_Postal_Code 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 4)
	private String providerPostalCode;
	
	
	/** 
	 * Provider_Phone_Number 
	 * Required: N 
	 */
	@CSVAnnotation(length = 20)
	private String providerPhoneNumber;
	
	
	/** 
	 * Provider_Fax_Number 
	 * Required: N *
	 */
	@CSVAnnotation(length = 20)
	private String providerFaxNumber;
	
	
	/** 
	 * Provider_Sars_Number 
	 * Required: N 
	 */
	@CSVAnnotation(length = 20)
	private String providerSarsNumber;
	
	
	/** 
	 * Provider_Contact_Name 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerContactName;
	
	
	/** 
	 * Provider_Contact_Email_Address 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerContactEmailAddress;
	
	
	/** 
	 * Provider_Contact_Phone_Number 
	 * Required: N 
	 */
	@CSVAnnotation(length = 20)
	private String providerContactPhoneNumber;
	
	
	/** 
	 * Provider_Contact_Cell_Number 
	 * Required: N 
	 */
	@CSVAnnotation(length = 20)
	private String providerContactCellNumber;
	
	
	/** 
	 * Provider_Accreditation_Num 
	 * Required: N 
	 */
	@CSVAnnotation(length = 20)
	private String providerAccreditationNum;
	
	
	/** 
	 * Provider_Accredit_Start_Date 
	 *  Required: C 
	 *  */
	@CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccreditStartDate;
	
	
	/** 
	 * Provider_Accredit_End_Date 
	 * Required: C 
	 */
	@CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccreditEndDate;
	
	
	/** 
	 * Etqa_Decision_Number 
	 * Required: N 
	 */
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;
	
	
	/** 
	 * Provider_Class_Id 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 10)
	private String providerClassId;
	
	
	/** 
	 * Structure_Status_Id 
	 * Required: Y *
	 */
	@CSVAnnotation(length = 10)
	private String structureStatusId;
	
	
	/** 
	 * Province_Code 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 2)
	private String provinceCode;
	
	
	/** 
	 * Country_Code 
	 * Required: Y 
	 */
	@CSVAnnotation(length = 4)
	private String countryCode;
	
	
	/** 
	 * Latitude_Degree 
	 * Required: C 
	 */
	@CSVAnnotation(length = 3)
	private Double latitudeDegree;
	
	
	/** 
	 * Latitude_Minutes 
	 * Required: C 
	 */
	@CSVAnnotation(length = 2)
	private Double latitudeMinutes;
	
	
	/** 
	 * Latitude_Seconds 
	 * Required: C 
	 */
	@CSVAnnotation(length = 6)
	private Double latitudeSeconds;
	
	/** 
	 * Longitude_Degree 
	 * Required: C 
	 */
	@CSVAnnotation(length = 2)
	private Double longitudeDegree;
	
	
	/** 
	 * Longitude_Minutes 
	 * Required: C 
	 */
	@CSVAnnotation(length = 2)
	private Double longitudeMinutes;
	
	
	/** 
	 * Longitude_Seconds 
	 * Required: C 
	 */
	@CSVAnnotation(length = 6)
	private Double longitudeSeconds;
	
	
	/** 
	 * Provider_Physical_Address_1 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress1;
	
	
	/** 
	 * Provider_Physical_Address_2 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress2;
	
	
	/** 
	 * Provider_Physical_Address_Town 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddressTown;
	
	
	/** 
	 * Provider_Phys_Address_Postcode 
	 * Required: N 
	 */
	@CSVAnnotation(length = 4)
	private String providerPhysAddressPostcode;
	
	
	/** 
	 * Provider_Web_Address 
	 * Required: N 
	 */
	@CSVAnnotation(length = 50)
	private String providerWebAddress;


	 /**
	  * Date Stamp 
	  * Required: True
	  */
	 @CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
     private Date dateStamp  ;


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


	public Double getLatitudeDegree() {
		return latitudeDegree;
	}


	public void setLatitudeDegree(Double latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}


	public Double getLatitudeMinutes() {
		return latitudeMinutes;
	}


	public void setLatitudeMinutes(Double latitudeMinutes) {
		this.latitudeMinutes = latitudeMinutes;
	}


	public Double getLatitudeSeconds() {
		return latitudeSeconds;
	}


	public void setLatitudeSeconds(Double latitudeSeconds) {
		this.latitudeSeconds = latitudeSeconds;
	}


	public Double getLongitudeDegree() {
		return longitudeDegree;
	}


	public void setLongitudeDegree(Double longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}


	public Double getLongitudeMinutes() {
		return longitudeMinutes;
	}


	public void setLongitudeMinutes(Double longitudeMinutes) {
		this.longitudeMinutes = longitudeMinutes;
	}


	public Double getLongitudeSeconds() {
		return longitudeSeconds;
	}


	public void setLongitudeSeconds(Double longitudeSeconds) {
		this.longitudeSeconds = longitudeSeconds;
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
}