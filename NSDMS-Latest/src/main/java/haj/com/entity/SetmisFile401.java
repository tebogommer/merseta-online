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

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * SetmisFile401.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_401")
public class SetmisFile401 implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile401. */
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
	
	@Column(name = "designation_id")
	private String designationId;
	
	@Column(name = "designation_registration_number")
	private String designationRegistrationNumber;
	
	@Column(name = "designation_etqe_id")
	private String designationETQEId;
	
	@Column(name = "designation_start_date")
	private Date designationStartDate;
	
	@Column(name = "designation_end_date")
	private Date designationEndDate;
	
	@Column(name = "designation_structure_status_id")
	private String designationStructureStatusId;
	
	@Column(name = "etqe_decision_number")
	private String eTQEDecisionNumber;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqe_id")
	private String providerETQEId;
	
	@Column(name = "filler_01")
	private String filler01;

	@Column(name = "filler_02")
	private String filler02;

	@Column(name = "filler_03")
	private String filler03;
	
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
		SetmisFile401 other = (SetmisFile401) obj;
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

	public String getDesignationETQEId() {
		return designationETQEId;
	}

	public void setDesignationETQEId(String designationETQEId) {
		this.designationETQEId = designationETQEId;
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

	public String getDesignationStructureStatusId() {
		return designationStructureStatusId;
	}

	public void setDesignationStructureStatusId(String designationStructureStatusId) {
		this.designationStructureStatusId = designationStructureStatusId;
	}

	public String geteTQEDecisionNumber() {
		return eTQEDecisionNumber;
	}

	public void seteTQEDecisionNumber(String eTQEDecisionNumber) {
		this.eTQEDecisionNumber = eTQEDecisionNumber;
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

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
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

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
