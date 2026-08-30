package haj.com.dataextract.bean;



import java.math.BigInteger;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile200Bean  implements IDataEntity {

	/** * SDL_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** * Site_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String siteNo;
	/** * SETA_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private BigInteger sETAId;
	/** * SIC_Code * Required: N */
	@CSVAnnotation(length = 10)
	private String sICCode;
	/** * Employer_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String employerRegistrationNumber;
	/** * Employer_Company_Name * Required: Y */
	@CSVAnnotation(length = 70)
	private String employerCompanyName;
	/** * Employer_Trading_Name * Required: Y */
	@CSVAnnotation(length = 70)
	private String employerTradingName;
	/** * Employer_Postal_Address_1 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPostalAddress1;
	/** * Employer_Postal_Address_2 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPostalAddress2;
	/** * Employer_Postal_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPostalAddress3;
	/** * Employer_Postal_Address_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String providerPostalAddressCode;
	/** * Employer_Physical_Address_1 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress1;
	/** * Employer_Physical_Address_2 * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress2;
	/** * Employer_Physical_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String providerPhysicalAddress3;
	/** * Employer_Physical_Address_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String providerPhysicalAddressCode;
	/** * Employer_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerPhoneNumber;
	/** * Employer_Fax_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerFaxNumber;
	/** * Employer_Contact_Name * Required: Y */
	@CSVAnnotation(length = 50)
	private String providerContactName;
	/** * Employer_Contact_Email_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String providerContactEmailAddress;
	/** * Employer_Contact_Phone_Number * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerContactPhoneNumber;
	/** * Employer_Contact_Cell_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String providerContactCellNumber;
	/** * Employer_Approval_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private BigInteger employerApprovalStatusId;
	/** * Employer_Approval_Status_Start_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date employerApprovalStatusStartDate;
	/** * Employer_Approval_Status_End_Date * Required: Y */
	@CSVAnnotation(length = 8)
	private String employerApprovalStatusEndDate;
	/** * Employer_Approval_Status_Num * Required: N */
	@CSVAnnotation(length = 20)
	private String employerApprovalStatusNum;
	/** * Province_Code * Required: Y */
	@CSVAnnotation(length = 2)
	private String provinceCode;
	/** * Country_Code * Required: Y */
	@CSVAnnotation(length = 4)
	private String countryCode;
	/** * Latitude_Degree * Required: N */
	@CSVAnnotation(length = 3)
	private String latitudeDegree;
	/** * Latitude_Minutes * Required: N */
	@CSVAnnotation(length = 2)
	private String latitudeMinutes;
	/** * Latitude_Seconds * Required: N */
	@CSVAnnotation(length = 6)
	private String latitudeSeconds;
	/** * Longitude_Degree * Required: N */
	@CSVAnnotation(length = 2)
	private String longitudeDegree;
	/** * Longitude_Minutes * Required: N */
	@CSVAnnotation(length = 2)
	private String longitudeMinutes;
	/** * Longitude_Seconds * Required: N */
	@CSVAnnotation(length = 6)
	private String longitudeSeconds;
	/** * Main_SDL_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String mainSDLNo;
	/** * Filler01 * Required: */
	@CSVAnnotation(length = 20)
	private String filler01;
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 4)
	private String filler02;
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;

	

		public SETMISFile200Bean() {
			super();
			// TODO Auto-generated constructor stub
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

		public BigInteger getsETAId() {
			return sETAId;
		}

		public void setsETAId(BigInteger sETAId) {
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

		public Date getEmployerApprovalStatusStartDate() {
			return employerApprovalStatusStartDate;
		}

		public void setEmployerApprovalStatusStartDate(Date employerApprovalStatusStartDate) {
			this.employerApprovalStatusStartDate = employerApprovalStatusStartDate;
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

		public BigInteger getEmployerApprovalStatusId() {
			return employerApprovalStatusId;
		}

		public void setEmployerApprovalStatusId(BigInteger employerApprovalStatusId) {
			this.employerApprovalStatusId = employerApprovalStatusId;
		}

		public String getEmployerApprovalStatusEndDate() {
			return employerApprovalStatusEndDate;
		}

		public void setEmployerApprovalStatusEndDate(String employerApprovalStatusEndDate) {
			this.employerApprovalStatusEndDate = employerApprovalStatusEndDate;
		}

}

		


