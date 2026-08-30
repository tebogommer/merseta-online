package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile23BeanCourseLegacyVersionTwo implements IDataEntity {

	/**
	 * NLRD Files 
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 23 NLRD Course (Legacy)
	 * 
	 * @author Arno
	 */

	/** File 23 - Course (Legacy) */

	/** Course_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String courseCode;

	/** Course_Name * Required: Y */
	@CSVAnnotation(length = 200)
	private String courseName;

	/** NQF_Level_Id * Required: Y */
	@CSVAnnotation(length = 8)
	private String nQFLevelId;

	/** Subdomain_Id * Required: Y */
	@CSVAnnotation(length = 8)
	private String subdomainId;

	/** Abet_Band_Id * Required: Y */
	@CSVAnnotation(length = 8)
	private String abetBandId;

	/** Course_Registration_Start_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date courseRegistrationStartDate;

	/** Course_Registration_End_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date courseRegistrationEndDate;

	/** Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/** Provider_Etqa_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerEtqaId;

	/** Date Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	
	/*
	 * SUBSTRING('', 1, 20) as courseCode,
	 * SUBSTRING('', 1, 200) as courseName,
	 * SUBSTRING('', 1, 8) as nQFLevelId,
	 * SUBSTRING('', 1, 8) as subdomainId,
	 * SUBSTRING('', 1, 8) as abetBandId,
	 * '' as courseRegistrationStartDate,
	 * '' as courseRegistrationEndDate,
	 * SUBSTRING('', 1, 20) as providerCode,
	 * SUBSTRING('', 1, 10) as providerEtqaId,
	 * '' as dateStamp
	 */
	
	public NLRDFile23BeanCourseLegacyVersionTwo() {
		super();
	}

	public NLRDFile23BeanCourseLegacyVersionTwo(String courseCode, String courseName, String nQFLevelId, String subdomainId, String abetBandId, Date courseRegistrationStartDate, Date courseRegistrationEndDate, String providerCode, String providerEtqaId, Date dateStamp) {
		super();

		this.courseCode = courseCode;
		this.courseName = courseName;
		this.nQFLevelId = nQFLevelId;
		this.subdomainId = subdomainId;
		this.abetBandId = abetBandId;
		this.courseRegistrationStartDate = courseRegistrationStartDate;
		this.courseRegistrationEndDate = courseRegistrationEndDate;
		this.providerCode = providerCode;
		this.providerEtqaId = providerEtqaId;
		this.dateStamp = dateStamp;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public Date getCourseRegistrationStartDate() {
		return courseRegistrationStartDate;
	}

	public void setCourseRegistrationStartDate(Date courseRegistrationStartDate) {
		this.courseRegistrationStartDate = courseRegistrationStartDate;
	}

	public Date getCourseRegistrationEndDate() {
		return courseRegistrationEndDate;
	}

	public void setCourseRegistrationEndDate(Date courseRegistrationEndDate) {
		this.courseRegistrationEndDate = courseRegistrationEndDate;
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