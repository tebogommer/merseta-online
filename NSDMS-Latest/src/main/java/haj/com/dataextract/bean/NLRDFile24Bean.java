package haj.com.dataextract.bean;



import java.math.BigInteger;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class NLRDFile24Bean  implements IDataEntity {
	
	
	/** 
	 * NLRD Files spec as at 18 May 2018
	 */
	
	/** 
	 * File 24 - Provider Accreditation  
	 */
	
	/**	
	 * Learnership_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length =	10) 
	private String learnershipId;

	/**	
	 * Qualification_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length =	10) 
	private BigInteger qualificationId;

	/**	
	 * Unit_Standard_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length =	10) 
	private BigInteger unitStandardId;

	/**	
	 * Provider_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 20)
	private String providerCode;

	/**	
	 * Provider_Etqa_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String providerEtqaId;

	/**	
	 * Provider_Accreditation_Num	
	 * Required:	N	
	 */	
	@CSVAnnotation(length = 20)
	private String providerAccreditationNum;

	/**	
	 * Provider_Accredit_Assessor_Ind	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 1)
	private String providerAccreditAssessorInd;

	/**	
	 * Provider_Accred_Start_Date	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccredStartDate;

	/**	
	 * Provider_Accred_End_Date	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccredEndDate;

	/**	
	 * Etqa_Decision_Number 	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;

	/**	
	 * Provider_Accred_Status_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private BigInteger providerAccredStatusCode;

	/**
	 * Date Stamp 
	 * Required: True
	 */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;


		public String getLearnershipId() {
			return learnershipId;
		}

		public void setLearnershipId(String learnershipId) {
			this.learnershipId = learnershipId;
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

		public String getProviderAccreditationNum() {
			return providerAccreditationNum;
		}

		public void setProviderAccreditationNum(String providerAccreditationNum) {
			this.providerAccreditationNum = providerAccreditationNum;
		}

		public String getProviderAccreditAssessorInd() {
			return providerAccreditAssessorInd;
		}

		public void setProviderAccreditAssessorInd(String providerAccreditAssessorInd) {
			this.providerAccreditAssessorInd = providerAccreditAssessorInd;
		}

		public Date getProviderAccredStartDate() {
			return providerAccredStartDate;
		}

		public void setProviderAccredStartDate(Date providerAccredStartDate) {
			this.providerAccredStartDate = providerAccredStartDate;
		}

		public Date getProviderAccredEndDate() {
			return providerAccredEndDate;
		}

		public void setProviderAccredEndDate(Date providerAccredEndDate) {
			this.providerAccredEndDate = providerAccredEndDate;
		}

		public String getEtqaDecisionNumber() {
			return etqaDecisionNumber;
		}

		public void setEtqaDecisionNumber(String etqaDecisionNumber) {
			this.etqaDecisionNumber = etqaDecisionNumber;
		}

		public Date getDateStamp() {
			return dateStamp;
		}

		public void setDateStamp(Date dateStamp) {
			this.dateStamp = dateStamp;
		}

		public BigInteger getQualificationId() {
			return qualificationId;
		}

		public void setQualificationId(BigInteger qualificationId) {
			this.qualificationId = qualificationId;
		}

		public BigInteger getUnitStandardId() {
			return unitStandardId;
		}

		public void setUnitStandardId(BigInteger unitStandardId) {
			this.unitStandardId = unitStandardId;
		}

		public BigInteger getProviderAccredStatusCode() {
			return providerAccredStatusCode;
		}

		public void setProviderAccredStatusCode(BigInteger providerAccredStatusCode) {
			this.providerAccredStatusCode = providerAccredStatusCode;
		}

}


