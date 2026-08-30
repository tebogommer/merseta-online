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
 * NLRDFile26.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "nlrd_file_26")
public class NLRDFile26 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of NLRDFile26. */
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
	private BigInteger alternativeIdType;
	
	@Column(name = "designation_id")
	private String designationId;
	
	@Column(name = "designation_registration_number")
	private String designationRegistrationNumber;
	
	@Column(name = "designation_etqa_id")
	private String designationETQAId;
	
	@Column(name = "designation_start_date")
	private Date designationStartDate;
	
	@Column(name = "designation_end_date")
	private Date designationEndDate;
	
	@Column(name = "structure_status_id")
	private String structureStatusId;
	
	@Column(name = "etqa_decision_number")
	private String etqaDecisionNumber;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqa_id")
	private String providerETQAID;
	
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
		NLRDFile26 other = (NLRDFile26) obj;
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

	public BigInteger getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(BigInteger alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
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

	public Date getDesignationStartDate() {
		return designationStartDate;
	}

	public void setDesignationStartDate(Date designationStartDate) {
		this.designationStartDate = designationStartDate;
	}

	public Date getDesignationEndDate() {
		return designationEndDate;
	}

	public void setDesignationEndDate(Date designationEndDate) {
		this.designationEndDate = designationEndDate;
	}

	public String getStructureStatusId() {
		return structureStatusId;
	}

	public void setStructureStatusId(String structureStatusId) {
		this.structureStatusId = structureStatusId;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderETQAID() {
		return providerETQAID;
	}

	public void setProviderETQAID(String providerETQAID) {
		this.providerETQAID = providerETQAID;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
