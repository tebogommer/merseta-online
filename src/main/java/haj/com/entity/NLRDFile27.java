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
 * NLRDFile27.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "nlrd_file_27")
public class NLRDFile27 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of NLRDFile27. */
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
	private Integer qualificationId;
	
	@Column(name = "unit_standard_id")
	private Integer unitStandardId;
	
	@Column(name = "designation_id")
	private String designationId;
	
	@Column(name = "designation_registration_number")
	private String designationRegistrationNumber;
	
	@Column(name = "designation_etqa_id")
	private String designationETQAId;
	
	@Column(name = "nqf_designation_start_date")
	private Date nQFDesignationStartDate;
	
	@Column(name = "nqf_designation_end_date")
	private Date nQFDesignationEndDate;
	
	@Column(name = "etqa_decision_number")
	private String etqaDecisionNumber;
	
	@Column(name = "nqf_desig_status_code")
	private String nQFDesigStatusCode;
	
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
		NLRDFile27 other = (NLRDFile27) obj;
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

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public Integer getUnitStandardId() {
		return unitStandardId;
	}

	public void setUnitStandardId(Integer unitStandardId) {
		this.unitStandardId = unitStandardId;
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

	public Date getnQFDesignationStartDate() {
		return nQFDesignationStartDate;
	}

	public void setnQFDesignationStartDate(Date nQFDesignationStartDate) {
		this.nQFDesignationStartDate = nQFDesignationStartDate;
	}

	public Date getnQFDesignationEndDate() {
		return nQFDesignationEndDate;
	}

	public void setnQFDesignationEndDate(Date nQFDesignationEndDate) {
		this.nQFDesignationEndDate = nQFDesignationEndDate;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public String getnQFDesigStatusCode() {
		return nQFDesigStatusCode;
	}

	public void setnQFDesigStatusCode(String nQFDesigStatusCode) {
		this.nQFDesigStatusCode = nQFDesigStatusCode;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
