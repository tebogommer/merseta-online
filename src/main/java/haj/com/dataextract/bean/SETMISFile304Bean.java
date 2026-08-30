package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile304Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 304 - Non NQF Intervention    
	 */
	
	
	/** * Non_NQF_Interv_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String nonNQFIntervCode;
	/** * Non_NQF_Interv_Name * Required: Y */
	@CSVAnnotation(length = 200)
	private String nonNQFIntervName;
	/** * Filler01 * Required: */
	@CSVAnnotation(length = 8)
	private String filler01;
	/** * Subfield_Id * Required: Y */
	@CSVAnnotation(length = 8)
	private String subfieldId;
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 8)
	private String filler02;
	/** * Non_NQF_Interv_Reg_Start_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date nonNQFIntervRegStartDate;
	/** * Non_NQF_Interv_Reg_End_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date nonNQFIntervRegEndDate;
	/** * Filler03 * Required: */
	@CSVAnnotation(length = 20)
	private String filler03;
	/** * Non_NQF_Interv_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String nonNQFIntervETQEId;
	/** * Non_NQF_Interv_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String nonNQFIntervStatusId;
	/** * Non_NQF_Interv_Credit * Required: Y */
	@CSVAnnotation(length = 10)
	private String nonNQFIntervCredit;
	/** * LearningProgrammeTypeId * Required: Y */
	@CSVAnnotation(length = 10)
	private String learningProgrammeTypeId;
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;


		public String getNonNQFIntervCode() {
			return nonNQFIntervCode;
		}

		public void setNonNQFIntervCode(String nonNQFIntervCode) {
			this.nonNQFIntervCode = nonNQFIntervCode;
		}

		public String getNonNQFIntervName() {
			return nonNQFIntervName;
		}

		public void setNonNQFIntervName(String nonNQFIntervName) {
			this.nonNQFIntervName = nonNQFIntervName;
		}

		public String getFiller01() {
			return filler01;
		}

		public void setFiller01(String filler01) {
			this.filler01 = filler01;
		}

		public String getSubfieldId() {
			return subfieldId;
		}

		public void setSubfieldId(String subfieldId) {
			this.subfieldId = subfieldId;
		}

		public String getFiller02() {
			return filler02;
		}

		public void setFiller02(String filler02) {
			this.filler02 = filler02;
		}

		public Date getNonNQFIntervRegStartDate() {
			return nonNQFIntervRegStartDate;
		}

		public void setNonNQFIntervRegStartDate(Date nonNQFIntervRegStartDate) {
			this.nonNQFIntervRegStartDate = nonNQFIntervRegStartDate;
		}

		public Date getNonNQFIntervRegEndDate() {
			return nonNQFIntervRegEndDate;
		}

		public void setNonNQFIntervRegEndDate(Date nonNQFIntervRegEndDate) {
			this.nonNQFIntervRegEndDate = nonNQFIntervRegEndDate;
		}

		public String getFiller03() {
			return filler03;
		}

		public void setFiller03(String filler03) {
			this.filler03 = filler03;
		}

		public String getNonNQFIntervETQEId() {
			return nonNQFIntervETQEId;
		}

		public void setNonNQFIntervETQEId(String nonNQFIntervETQEId) {
			this.nonNQFIntervETQEId = nonNQFIntervETQEId;
		}

		public String getNonNQFIntervStatusId() {
			return nonNQFIntervStatusId;
		}

		public void setNonNQFIntervStatusId(String nonNQFIntervStatusId) {
			this.nonNQFIntervStatusId = nonNQFIntervStatusId;
		}

		public String getNonNQFIntervCredit() {
			return nonNQFIntervCredit;
		}

		public void setNonNQFIntervCredit(String nonNQFIntervCredit) {
			this.nonNQFIntervCredit = nonNQFIntervCredit;
		}

		public Date getDateStamp() {
			return dateStamp;
		}

		public void setDateStamp(Date dateStamp) {
			this.dateStamp = dateStamp;
		}

		public String getLearningProgrammeTypeId() {
			return learningProgrammeTypeId;
		}

		public void setLearningProgrammeTypeId(String learningProgrammeTypeId) {
			this.learningProgrammeTypeId = learningProgrammeTypeId;
		}

		
		
}


