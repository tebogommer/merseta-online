package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * SetmisFile501.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_501")
public class SetmisFile501 implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile501. */
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
	
	@Column(name = "qualification_id")
	private String qualificationId;
	
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
	
	@Column(name = "filler_01")
	private String filler01;
	
	@Column(name = "part_of_id")
	private String partOfId;
	
	@Column(name = "learnership_id")
	private String learnershipId;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqe_id")
	private String providerETQEId;
	
	@Column(name = "assessor_etqe_id")
	private String assessorETQEId;
	
	@Column(name = "filler_02")
	private String filler02;
	
	@Column(name = "enrolment_status_reason_id")
	private String enrolmentStatusReasonId;
	
	@Column(name = "most_recent_registration_date")
	private Date mostRecentRegistrationDate;
	
	@Column(name = "certificate_number")
	private String certificateNumber;
	
	@Column(name = "filler_03")
	private String filler03;
	
	@Column(name = "filler_04")
	private String filler04;
	
	@Column(name = "filler_05")
	private String filler05;
	
	@Column(name = "filler_06")
	private String filler06;
	
	@Column(name = "economic_status_id")
	private String economicStatusId;
	
	@Column(name = "filler_07")
	private String filler07;
	
	@Column(name = "sdl_no")
	private String sDLNo;
	
	@Column(name = "filler_08")
	private String filler08;
	
	@Column(name = "filler_09")
	private String filler09;
	
	@Column(name = "filler_10")
	private String filler10;
	
	@Column(name = "filler_11")
	private String filler11;
	
	@Column(name = "site_no")
	private BigInteger siteNo;
	
	@Column(name = "practical_provider_code")
	private String practicalProviderCode;
	
	@Column(name = "practical_provider_etqe_id")
	private String practicalProviderETQEId;
	
	@Column(name = "funding_id")
	private String fundingId;
	
	@Column(name = "cumulative_spending")
	private String cumulativeSpending;
	
	@Column(name = "ofo_code")
	private String oFOCode;
	
	@Column(name = "urban_rural_id")
	private String urbanRuralId;
	
	@Column(name = "learning_programme_type_id")
	private String learningProgrammeTypeId;
	
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
		SetmisFile501 other = (SetmisFile501) obj;
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

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
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

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}

	public String getPartOfId() {
		return partOfId;
	}

	public void setPartOfId(String partOfId) {
		this.partOfId = partOfId;
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

	public String getFiller02() {
		return filler02;
	}

	public void setFiller02(String filler02) {
		this.filler02 = filler02;
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

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getFiller03() {
		return filler03;
	}

	public void setFiller03(String filler03) {
		this.filler03 = filler03;
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

	public String getsDLNo() {
		return sDLNo;
	}

	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
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

	public String getFiller10() {
		return filler10;
	}

	public void setFiller10(String filler10) {
		this.filler10 = filler10;
	}

	public String getFiller11() {
		return filler11;
	}

	public void setFiller11(String filler11) {
		this.filler11 = filler11;
	}

	public String getPracticalProviderCode() {
		return practicalProviderCode;
	}

	public void setPracticalProviderCode(String practicalProviderCode) {
		this.practicalProviderCode = practicalProviderCode;
	}

	public String getPracticalProviderETQEId() {
		return practicalProviderETQEId;
	}

	public void setPracticalProviderETQEId(String practicalProviderETQEId) {
		this.practicalProviderETQEId = practicalProviderETQEId;
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

	public String getUrbanRuralId() {
		return urbanRuralId;
	}

	public void setUrbanRuralId(String urbanRuralId) {
		this.urbanRuralId = urbanRuralId;
	}

	public String getLearningProgrammeTypeId() {
		return learningProgrammeTypeId;
	}

	public void setLearningProgrammeTypeId(String learningProgrammeTypeId) {
		this.learningProgrammeTypeId = learningProgrammeTypeId;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getAssessorETQEId() {
		return assessorETQEId;
	}

	public void setAssessorETQEId(String assessorETQEId) {
		this.assessorETQEId = assessorETQEId;
	}

	public BigInteger getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(BigInteger siteNo) {
		this.siteNo = siteNo;
	}
}
