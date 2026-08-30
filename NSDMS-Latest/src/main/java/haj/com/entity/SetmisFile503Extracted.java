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

// TODO: Auto-generated Javadoc
/**
 * SetmisFile503.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_503_extracted")
public class SetmisFile503Extracted implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile503. */
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

	/** * National_Id * Required: Y */
	@CSVAnnotation(length = 15)
	@Column(name = "nationalid")
	private String nationalId;
	
	/** * Person_Alternate_Id * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "personalternateid")
	private String personAlternateId;
	
	/** * Alternative_Id_Type * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "alternativeidtype")
	private String alternativeIdType;
	
	/** * Unit_Standard_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "unitstandardidstring")
	private String unitStandardIdString;
	//private Integer unitStandardId;
	
	/** * Enrolment_Status_Id * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "enrolmentstatusid")
	private String enrolmentStatusId;
	
	/** * Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	@Column(name = "assessorregistrationnumber")
	private String assessorRegistrationNumber;
	
	/** * Enrolment_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "enrolmenttypeid")
	private String enrolmentTypeId;
	
	/** * Enrolment_Status_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "enrolmentstatusdatestring")
	private String enrolmentStatusDateString;
	//private Date enrolmentStatusDate;
	
	/** * Enrolment_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "enrolmentdatestring")
	private String enrolmentDateString;
	//private Date enrolmentDate;
	
	/** * Filler01 * Required: */
	@CSVAnnotation(length = 3)
	@Column(name = "filler01")
	private String filler01;
	
	/** * Part_Of * Required: */
	@CSVAnnotation(length = 2)
	@Column(name = "partof")
	private String partOf;
	
	/** * Qualification_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "qualificationidstring")
	private String qualificationIdString;
	//private Integer qualificationId;
	
	/** * Learnership_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "learnershipid")
	private String learnershipId;
	
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;
	
	/** * Provide_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "provideretqeid")
	private String providerETQEId;
	
	/** * Assessor_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "assessoretqeid")
	private String assessorETQEId;
	
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 20)
	@Column(name = "filler02")
	private String filler02;
	
	/** * Filler03 * Required: */
	@CSVAnnotation(length = 20)
	@Column(name = "filler03")
	private String filler03;
	
	/** * Enrolment_Status_Reason_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "enrolmentstatusreasonid")
	private String enrolmentStatusReasonId;
	
	/** * Most_Recent_Registration_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "mostrecentregistrationdatestring")
	private String mostRecentRegistrationDateString;
	//private Date mostRecentRegistrationDate;
	
	/** * Filler04 * Required: */
	@CSVAnnotation(length = 10)
	@Column(name = "filler04")
	private String filler04;
	
	/** * Filler05 * Required: */
	@CSVAnnotation(length = 10)
	@Column(name = "filler05")
	private String filler05;
	
	/** * Filler06 * Required: */
	@CSVAnnotation(length = 10)
	@Column(name = "filler06")
	private String filler06;
	
	/** * Economic_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "economicstatusid")
	private String economicStatusId;
	
	/** * Filler07 * Required: */
	@CSVAnnotation(length = 10)
	@Column(name = "filler07")
	private String filler07;
	
	/** * Filler08 * Required: */
	@CSVAnnotation(length = 1)
	@Column(name = "filler08")
	private String filler08;
	
	/** * Filler09 * Required: */
	@CSVAnnotation(length = 10)
	@Column(name = "filler09")
	private String filler09;
	
	/** * Cumulative_Spend * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "cumulativespend")
	private String cumulativeSpend;
	
	/** * Certificate_Number * Required: N */
	@CSVAnnotation(length = 30)
	@Column(name = "certificatenumber")
	private String certificateNumber;
	
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "fundingid")
	private String fundingId;
	
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	@Column(name = "ofocode")
	private String oFOCode;
	
	/** * SDL_No * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "sdlno")
	private String sDLNo;
	
	/** * Site_No * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "siteno")
	private String siteNo;
	
	/** * Non_NQF_Interv_Code * Required: N */
	@CSVAnnotation(length = 20)
	@Column(name = "nonnqfintervcode")
	private String nonNQFIntervCode;
	
	/** * Non_NQF_Interv_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "nonnqfintervetqeid")
	private String nonNQFIntervETQEId;
	
	/** * Urban_Rural_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "urbanruralid")
	private String urbanRuralId;
	
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "datestampstring")
	private String dateStampString;
	//private Date dateStamp;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		SetmisFile503Extracted other = (SetmisFile503Extracted) obj;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}

	public String getUnitStandardIdString() {
		return unitStandardIdString;
	}

	public void setUnitStandardIdString(String unitStandardIdString) {
		this.unitStandardIdString = unitStandardIdString;
	}

	public String getEnrolmentStatusId() {
		return enrolmentStatusId;
	}

	public void setEnrolmentStatusId(String enrolmentStatusId) {
		this.enrolmentStatusId = enrolmentStatusId;
	}

	public String getAssessorRegistrationNumber() {
		return assessorRegistrationNumber;
	}

	public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
		this.assessorRegistrationNumber = assessorRegistrationNumber;
	}

	public String getEnrolmentTypeId() {
		return enrolmentTypeId;
	}

	public void setEnrolmentTypeId(String enrolmentTypeId) {
		this.enrolmentTypeId = enrolmentTypeId;
	}

	public String getEnrolmentStatusDateString() {
		return enrolmentStatusDateString;
	}

	public void setEnrolmentStatusDateString(String enrolmentStatusDateString) {
		this.enrolmentStatusDateString = enrolmentStatusDateString;
	}

	public String getEnrolmentDateString() {
		return enrolmentDateString;
	}

	public void setEnrolmentDateString(String enrolmentDateString) {
		this.enrolmentDateString = enrolmentDateString;
	}

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}

	public String getPartOf() {
		return partOf;
	}

	public void setPartOf(String partOf) {
		this.partOf = partOf;
	}

	public String getQualificationIdString() {
		return qualificationIdString;
	}

	public void setQualificationIdString(String qualificationIdString) {
		this.qualificationIdString = qualificationIdString;
	}

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

	public String getProviderETQEId() {
		return providerETQEId;
	}

	public void setProviderETQEId(String providerETQEId) {
		this.providerETQEId = providerETQEId;
	}

	public String getAssessorETQEId() {
		return assessorETQEId;
	}

	public void setAssessorETQEId(String assessorETQEId) {
		this.assessorETQEId = assessorETQEId;
	}

	public String getFiller02() {
		return filler02;
	}

	public void setFiller02(String filler02) {
		this.filler02 = filler02;
	}

	public String getFiller03() {
		return filler03;
	}

	public void setFiller03(String filler03) {
		this.filler03 = filler03;
	}

	public String getEnrolmentStatusReasonId() {
		return enrolmentStatusReasonId;
	}

	public void setEnrolmentStatusReasonId(String enrolmentStatusReasonId) {
		this.enrolmentStatusReasonId = enrolmentStatusReasonId;
	}

	public String getMostRecentRegistrationDateString() {
		return mostRecentRegistrationDateString;
	}

	public void setMostRecentRegistrationDateString(String mostRecentRegistrationDateString) {
		this.mostRecentRegistrationDateString = mostRecentRegistrationDateString;
	}

	public String getFiller04() {
		return filler04;
	}

	public void setFiller04(String filler04) {
		this.filler04 = filler04;
	}

	public String getFiller05() {
		return filler05;
	}

	public void setFiller05(String filler05) {
		this.filler05 = filler05;
	}

	public String getFiller06() {
		return filler06;
	}

	public void setFiller06(String filler06) {
		this.filler06 = filler06;
	}

	public String getEconomicStatusId() {
		return economicStatusId;
	}

	public void setEconomicStatusId(String economicStatusId) {
		this.economicStatusId = economicStatusId;
	}

	public String getFiller07() {
		return filler07;
	}

	public void setFiller07(String filler07) {
		this.filler07 = filler07;
	}

	public String getFiller08() {
		return filler08;
	}

	public void setFiller08(String filler08) {
		this.filler08 = filler08;
	}

	public String getFiller09() {
		return filler09;
	}

	public void setFiller09(String filler09) {
		this.filler09 = filler09;
	}

	public String getCumulativeSpend() {
		return cumulativeSpend;
	}

	public void setCumulativeSpend(String cumulativeSpend) {
		this.cumulativeSpend = cumulativeSpend;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getFundingId() {
		return fundingId;
	}

	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
	}

	public String getoFOCode() {
		return oFOCode;
	}

	public void setoFOCode(String oFOCode) {
		this.oFOCode = oFOCode;
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

	public String getNonNQFIntervCode() {
		return nonNQFIntervCode;
	}

	public void setNonNQFIntervCode(String nonNQFIntervCode) {
		this.nonNQFIntervCode = nonNQFIntervCode;
	}

	public String getNonNQFIntervETQEId() {
		return nonNQFIntervETQEId;
	}

	public void setNonNQFIntervETQEId(String nonNQFIntervETQEId) {
		this.nonNQFIntervETQEId = nonNQFIntervETQEId;
	}

	public String getUrbanRuralId() {
		return urbanRuralId;
	}

	public void setUrbanRuralId(String urbanRuralId) {
		this.urbanRuralId = urbanRuralId;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}

	
}
