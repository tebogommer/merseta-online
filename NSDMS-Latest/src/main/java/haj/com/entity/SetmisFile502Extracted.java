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
@Table(name = "setmis_file_502_extracted")
public class SetmisFile502Extracted implements IDataEntity
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
	
	/** * Person_Alternative_Id * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "personalternateid")
	private String personAlternateId;
	
	/** * Aternative_Id_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "alternativeidtype")
	private String alternativeIdType;
	
	/** * Non_NQF_Intervention_Code * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "nonnqfintervcode")
	private String NonNQFIntervCode;
	
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
	
	/** * Part_Of_Id * Required: Y */
	@CSVAnnotation(length = 2)
	@Column(name = "partofid")
	private String partOfId;
	
	/** * Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "qualificationid")
	private String qualificationId;
	
	/** * Learnership_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "learnershipid")
	private String learnershipId;
	
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
	
	/** * Economic_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "economicstatusid")
	private String economicStatusId;
	
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "fundingid")
	private String fundingId;
	
	/** * Cumulative_Spending * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "cumulativespending")
	private String cumulativeSpending;
	
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
	
	/** * Non_NQF_Interv_ETQE_Id * Required: Y */
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
		SetmisFile502Extracted other = (SetmisFile502Extracted) obj;
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

	public String getNonNQFIntervCode() {
		return NonNQFIntervCode;
	}

	public void setNonNQFIntervCode(String nonNQFIntervCode) {
		NonNQFIntervCode = nonNQFIntervCode;
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

	public String getPartOfId() {
		return partOfId;
	}

	public void setPartOfId(String partOfId) {
		this.partOfId = partOfId;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
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

	public String getCumulativeSpending() {
		return cumulativeSpending;
	}

	public void setCumulativeSpending(String cumulativeSpending) {
		this.cumulativeSpending = cumulativeSpending;
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
