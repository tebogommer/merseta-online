package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class NLRDFile22Bean  implements IDataEntity {
	
	
	/** NLRD Files spec as at 18 May 2018
	 */
	
	/** File 22 - Qualification/Degree (Legacy)  
	 */
	
	/**
	 * Qualification_Code 
	 * Required: Y
	 */
	@CSVAnnotation(length = 20)
	private String qualificationCode;

	/**
	 * Qualification_Name 
	 * Required: Y
	 */
	@CSVAnnotation(length = 100)
	private String qualificationName;

	/**
	 * Qualification_Type_Id 
	 * Required: Y
	 */
	@CSVAnnotation(length = 15)
	private String qualificationTypeId;

	/**
	 * NQF_Level_Id 
	 * Required: Y
	 */
	@CSVAnnotation(length = 8)
	private String nQFLevelId;

	/**
	 * Subdomain_Id 
	 * Required: Y
	 */
	@CSVAnnotation(length = 8)
	private String subdomainId;

	/**
	 * Abet_Band_Id 
	 * Required: Y
	 */
	@CSVAnnotation(length = 8)
	private String abetBandId;

	/**
	 * Qual_Registration_Start_Date 
	 * Required: Y
	 */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date qualRegistrationStartDate;

	/**
	 * Qual_Registration_End_Date 
	 * Required: Y
	 */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date qualRegistrationEndDate;

	/**
	 * Provider_Code 
	 * Required: Y
	 */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/**
	 * Provider_Etqa_Id 
	 * Required: Y
	 */
	@CSVAnnotation(length = 10)
	private String providerEtqaId;

	/**
	 * Date Stamp 
	 * Required: True
	 */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;

	public NLRDFile22Bean(String qualificationCode, String qualificationName,
			String nQFLevelId, String subdomainId, String abetBandId, Date qualRegistrationStartDate,
			Date qualRegistrationEndDate, String providerCode, String providerEtqaId, Date dateStamp) {
		super();

		this.qualificationCode = qualificationCode;
		this.qualificationName = qualificationName;
		//this.qualificationTypeId = qualificationTypeId;
		this.nQFLevelId = nQFLevelId;
		this.subdomainId = subdomainId;
		this.abetBandId = abetBandId;
		this.qualRegistrationStartDate = qualRegistrationStartDate;
		this.qualRegistrationEndDate = qualRegistrationEndDate;
		this.providerCode = providerCode;
		this.providerEtqaId = providerEtqaId;
		this.dateStamp = dateStamp;
	}

		public NLRDFile22Bean() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getQualificationCode() {
			return qualificationCode;
		}

		public void setQualificationCode(String qualificationCode) {
			this.qualificationCode = qualificationCode;
		}

		public String getQualificationName() {
			return qualificationName;
		}

		public void setQualificationName(String qualificationName) {
			this.qualificationName = qualificationName;
		}

		public String getQualificationTypeId() {
			return qualificationTypeId;
		}

		public void setQualificationTypeId(String qualificationTypeId) {
			this.qualificationTypeId = qualificationTypeId;
		}

		public String getnQFLevelId() {
			return nQFLevelId;
		}

		public void setnQFLevelId(String nQFLevelId) {
			this.nQFLevelId = nQFLevelId;
		}

		public String getSubdomainId() {
			return subdomainId;
		}

		public void setSubdomainId(String subdomainId) {
			this.subdomainId = subdomainId;
		}

		public String getAbetBandId() {
			return abetBandId;
		}

		public void setAbetBandId(String abetBandId) {
			this.abetBandId = abetBandId;
		}

		public Date getQualRegistrationStartDate() {
			return qualRegistrationStartDate;
		}

		public void setQualRegistrationStartDate(Date qualRegistrationStartDate) {
			this.qualRegistrationStartDate = qualRegistrationStartDate;
		}

		public Date getQualRegistrationEndDate() {
			return qualRegistrationEndDate;
		}

		public void setQualRegistrationEndDate(Date qualRegistrationEndDate) {
			this.qualRegistrationEndDate = qualRegistrationEndDate;
		}

		public String getProviderCode() {
			return providerCode;
		}

		public void setProviderCode(String providerCode) {
			this.providerCode = providerCode;
		}

		public String getProviderEtqaId() {
			return providerEtqaId;
		}

		public void setProviderEtqaId(String providerEtqaId) {
			this.providerEtqaId = providerEtqaId;
		}

		public Date getDateStamp() {
			return dateStamp;
		}

		public void setDateStamp(Date dateStamp) {
			this.dateStamp = dateStamp;
		}


}


