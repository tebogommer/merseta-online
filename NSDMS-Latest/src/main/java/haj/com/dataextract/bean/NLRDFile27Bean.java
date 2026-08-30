package haj.com.dataextract.bean;



import java.math.BigInteger;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class NLRDFile27Bean  implements IDataEntity {
	
	
	/** 
	 * NLRD Files spec as at 18 May 2018
	 */
	
	/** 
	 * File 27 - NQF Designation registration    
	 */
	
	/**	
	 * Learnership_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 10)
	private String learnershipId;
	 
	/**	
	 * Qualification_Id	
	 * Required:	C
	 */	 
	@CSVAnnotation(length = 10)
	private Integer qualificationId;
	 
	/**	
	 * Unit_Standard_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 10)
	private Integer unitStandardId;
	 
	/**	
	 * Designation_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 5)
	private String designationId;
	 
	/**	
	 * Designation_Registration_Number	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 20)
	private String designationRegistrationNumber;
	 
	/**	
	 * Designation_ETQA_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String designationETQAId;
	 
	/**	
	 * NQF_Designation_Start_Date	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date nQFDesignationStartDate;
	 
	/**	
	 * NQF_Designation_End_Date	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date nQFDesignationEndDate;
	 
	/**	
	 * Etqa_Decision_Number	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;
	 
	/**	
	 * NQF_Desig_Status_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String nQFDesigStatusCode;
	 
	/**	
	 * Date_Stamp	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;


		public String getLearnershipId() {
			return learnershipId;
		}

		public void setLearnershipId(String learnershipId) {
			this.learnershipId = learnershipId;
		}

		public Integer getQualificationId() {
			return qualificationId;
		}

		public void setQualificationId(Integer qualificationId) {
			this.qualificationId = qualificationId;
		}

		public Integer getUnitStandardId() {
			return unitStandardId;
		}

		public void setUnitStandardId(Integer unitStandardId) {
			this.unitStandardId = unitStandardId;
		}

		public String getDesignationId() {
			return designationId;
		}

		public void setDesignationId(String designationId) {
			this.designationId = designationId;
		}

		public String getDesignationRegistrationNumber() {
			return designationRegistrationNumber;
		}

		public void setDesignationRegistrationNumber(String designationRegistrationNumber) {
			this.designationRegistrationNumber = designationRegistrationNumber;
		}

		public String getDesignationETQAId() {
			return designationETQAId;
		}

		public void setDesignationETQAId(String designationETQAId) {
			this.designationETQAId = designationETQAId;
		}

		public Date getnQFDesignationStartDate() {
			return nQFDesignationStartDate;
		}

		public void setnQFDesignationStartDate(Date nQFDesignationStartDate) {
			this.nQFDesignationStartDate = nQFDesignationStartDate;
		}

		public Date getnQFDesignationEndDate() {
			return nQFDesignationEndDate;
		}

		public void setnQFDesignationEndDate(Date nQFDesignationEndDate) {
			this.nQFDesignationEndDate = nQFDesignationEndDate;
		}

		public String getEtqaDecisionNumber() {
			return etqaDecisionNumber;
		}

		public void setEtqaDecisionNumber(String etqaDecisionNumber) {
			this.etqaDecisionNumber = etqaDecisionNumber;
		}

		public String getnQFDesigStatusCode() {
			return nQFDesigStatusCode;
		}

		public void setnQFDesigStatusCode(String nQFDesigStatusCode) {
			this.nQFDesigStatusCode = nQFDesigStatusCode;
		}

		public Date getDateStamp() {
			return dateStamp;
		}

		public void setDateStamp(Date dateStamp) {
			this.dateStamp = dateStamp;
		}

		

}


