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
 * SetmisFile502.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_502")
public class SetmisFile502 implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile502. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	@Column(name = "national_id")
	private String nationalId;

	@Column(name = "person_alternate_id")
	private String personAlternateId;
	
	@Column(name = "alternative_id_type")
	private String alternativeIdType;
	
	@Column(name = "non_nqf_intervention_code")
	private String nonNQFInterventionCode;
	
	@Column(name = "enrolment_status_id")
	private String enrolmentStatusId;
	
	@Column(name = "assessor_registration_number")
	private String assessorRegistrationNumber;
	
	@Column(name = "enrolment_type_id")
	private String enrolmentTypeId;
	
	@Column(name = "enrolment_status_date")
	private Date enrolmentStatusDate;
	
	@Column(name = "enrolment_date")
	private Date enrolmentDate;
	
	@Column(name = "part_of_id")
	private String partOfId;
	
	@Column(name = "qualification_id")
	private String qualificationId;
	
	@Column(name = "learnership_id")
	private String learnershipId;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqe_id")
	private String providerETQEId;
	
	@Column(name = "assessor_etqe_id")
	private String assessorETQEId;
	
	@Column(name = "enrolment_status_reason_id")
	private String enrolmentStatusReasonId;
	
	@Column(name = "most_recent_registration_date")
	private Date mostRecentRegistrationDate;
	
	@Column(name = "economic_status_id")
	private String economicStatusId;

	@Column(name = "funding_id")
	private String fundingId;
	
	@Column(name = "cumulative_spending")
	private String cumulativeSpending;
	
	@Column(name = "ofo_code")
	private String oFOCode;
	
	@Column(name = "sdl_no")
	private String sDLNo;
	
	@Column(name = "site_no")
	private String siteNo;
	
	@Column(name = "non_nqf_interv_etqe_id")
	private String nonNQFIntervETQEId;
	
	@Column(name = "urban_rural_id")
	private String urbanRuralId;
	
	@Column(name = "date_stamp")
	private Date dateStamp;
	
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
		SetmisFile502 other = (SetmisFile502) obj;
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

	public String getNonNQFInterventionCode() {
		return nonNQFInterventionCode;
	}

	public void setNonNQFInterventionCode(String nonNQFInterventionCode) {
		this.nonNQFInterventionCode = nonNQFInterventionCode;
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

	public Date getEnrolmentStatusDate() {
		return enrolmentStatusDate;
	}

	public void setEnrolmentStatusDate(Date enrolmentStatusDate) {
		this.enrolmentStatusDate = enrolmentStatusDate;
	}

	public Date getEnrolmentDate() {
		return enrolmentDate;
	}

	public void setEnrolmentDate(Date enrolmentDate) {
		this.enrolmentDate = enrolmentDate;
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

	public Date getMostRecentRegistrationDate() {
		return mostRecentRegistrationDate;
	}

	public void setMostRecentRegistrationDate(Date mostRecentRegistrationDate) {
		this.mostRecentRegistrationDate = mostRecentRegistrationDate;
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

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
