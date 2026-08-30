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
 * SetmisFile500.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_500_extracted")
public class SetmisFile500Extracted implements IDataEntity
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
	
	/** * Learnership_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "learnershipid")
	private String learnershipId;
	
	/** * Enrolment_Status_Id * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "enrolmentstatusid")
	private String enrolmentStatusId;
	
	/** * Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	@Column(name = "assessorregistrationnumber")
	private String assessorRegistrationNumber;
	
	/** * Enrolment_Status_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "enrolmentstatusdatestring")
	private String enrolmentStatusDateString;
	//private Date enrolmentStatusDate;
	
	/** * Enrolment_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "enrolmentdate")
	private String enrolmentDate;
	//private Date enrolmentDate;
	
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;
	
	/** * Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "provideretqeid")
	private String providerETQEId;
	
	/** * Assessor_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "assessoretqeid")
	private String assessorETQEId;
	
	/** * Enrolment_Status_Reason_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "enrolmentstatusreasonid")
	private String enrolmentStatusReasonId;
	
	/** * Most_Recent_Registration_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "mostrecentregistrationdatestring")
	private String mostRecentRegistrationDateString;
	//private Date mostRecentRegistrationDate;
	
	/** * Certificate_Number * Required: N */
	@CSVAnnotation(length = 30)
	@Column(name = "certificatenumber")
	private String certificateNumber;
	
	/** * Economic_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "economicstatusid")
	private String economicStatusId;
	
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "fundingid")
	private String fundingId;
	
	/** * Cumulative_Spend * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "cumulativespend")
	private String cumulativeSpend;
	
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	@Column(name = "ofocode")
	private String oFOCode;
	
	/** * SDL_No * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "sdlno")
	private String sDLNo;
	
	/** * Site_No * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "siteno")
	private String siteNo;
	
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
		SetmisFile500Extracted other = (SetmisFile500Extracted) obj;
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

	public String getPersonAlternateId() {
		return personAlternateId;
	}

	public void setPersonAlternateId(String personAlternateId) {
		this.personAlternateId = personAlternateId;
	}

	public String getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}

	public String getLearnershipId() {
		return learnershipId;
	}

	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
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

	public String getEnrolmentStatusReasonId() {
		return enrolmentStatusReasonId;
	}

	public void setEnrolmentStatusReasonId(String enrolmentStatusReasonId) {
		this.enrolmentStatusReasonId = enrolmentStatusReasonId;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getEconomicStatusId() {
		return economicStatusId;
	}

	public void setEconomicStatusId(String economicStatusId) {
		this.economicStatusId = economicStatusId;
	}

	public String getFundingId() {
		return fundingId;
	}

	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
	}

	public String getCumulativeSpend() {
		return cumulativeSpend;
	}

	public void setCumulativeSpend(String cumulativeSpend) {
		this.cumulativeSpend = cumulativeSpend;
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

	public String getUrbanRuralId() {
		return urbanRuralId;
	}

	public void setUrbanRuralId(String urbanRuralId) {
		this.urbanRuralId = urbanRuralId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEnrolmentStatusDateString() {
		return enrolmentStatusDateString;
	}

	public void setEnrolmentStatusDateString(String enrolmentStatusDateString) {
		this.enrolmentStatusDateString = enrolmentStatusDateString;
	}

	public String getEnrolmentDate() {
		return enrolmentDate;
	}

	public void setEnrolmentDate(String enrolmentDate) {
		this.enrolmentDate = enrolmentDate;
	}

	public String getMostRecentRegistrationDateString() {
		return mostRecentRegistrationDateString;
	}

	public void setMostRecentRegistrationDateString(String mostRecentRegistrationDateString) {
		this.mostRecentRegistrationDateString = mostRecentRegistrationDateString;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}

	
}
