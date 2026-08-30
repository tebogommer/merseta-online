package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * NLRDFile24.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "nlrd_file_24")
public class NLRDFile24 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of NLRDFile24. */
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
	
	@Column(name = "learnership_id")
	private String learnershipId;
	
	@Column(name = "qualification_id")
	private BigInteger qualificationId;
	
	@Column(name = "unit_standard_id")
	private BigInteger unitStandardId;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqa_id")
	private String providerEtqaId;
	
	@Column(name = "provider_accreditation_num")
	private String providerAccreditationNum;
	
	@Column(name = "provider_accredit_assessor_ind")
	private String providerAccreditAssessorInd;
	
	@Column(name = "provider_accred_start_date")
	private Date providerAccredStartDate;
	
	@Column(name = "provider_accred_end_date")
	private Date providerAccredEndDate;
	
	@Column(name = "etqa_decision_number")
	private String etqaDecisionNumber;
	
	@Column(name = "provider_accred_status_code")
	private BigInteger providerAccredStatusCode;
	
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
		NLRDFile24 other = (NLRDFile24) obj;
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

	public String getLearnershipId() {
		return learnershipId;
	}

	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
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

	public BigInteger getProviderAccredStatusCode() {
		return providerAccredStatusCode;
	}

	public void setProviderAccredStatusCode(BigInteger providerAccredStatusCode) {
		this.providerAccredStatusCode = providerAccredStatusCode;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
