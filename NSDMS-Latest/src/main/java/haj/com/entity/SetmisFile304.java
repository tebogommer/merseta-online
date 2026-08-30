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
 * SetmisFile304.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_304")
public class SetmisFile304 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile304. */
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
	
	@Column(name = "non_nqf_interv_code")
	private String nonNQFIntervCode;
	
	@Column(name = "non_nqf_interv_name")
	private String nonNQFIntervName;
	
	@Column(name = "filler_01")
	private String filler01;
	
	@Column(name = "sub_field_id")
	private String subfieldId;
	
	@Column(name = "filler_02")
	private String filler02;
	
	@Column(name = "non_nqf_interv_reg_start_date")
	private Date nonNQFIntervRegStartDate;
	
	@Column(name = "non_nqf_interv_reg_end_date")
	private Date nonNQFIntervRegEndDate;
	
	@Column(name = "filler_03")
	private String filler03;
	
	@Column(name = "non_nqf_interv_etqe_id")
	private String nonNQFIntervETQEId;
	
	@Column(name = "non_nqf_interv_status_id")
	private String nonNQFIntervStatusId;
	
	@Column(name = "non_nqf_interv_credit")
	private String nonNQFIntervCredit;
	
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
		SetmisFile304 other = (SetmisFile304) obj;
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

	public String getNonNQFIntervCode() {
		return nonNQFIntervCode;
	}

	public void setNonNQFIntervCode(String nonNQFIntervCode) {
		this.nonNQFIntervCode = nonNQFIntervCode;
	}

	public String getNonNQFIntervName() {
		return nonNQFIntervName;
	}

	public void setNonNQFIntervName(String nonNQFIntervName) {
		this.nonNQFIntervName = nonNQFIntervName;
	}

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}

	public String getSubfieldId() {
		return subfieldId;
	}

	public void setSubfieldId(String subfieldId) {
		this.subfieldId = subfieldId;
	}

	public String getFiller02() {
		return filler02;
	}

	public void setFiller02(String filler02) {
		this.filler02 = filler02;
	}

	public Date getNonNQFIntervRegStartDate() {
		return nonNQFIntervRegStartDate;
	}

	public void setNonNQFIntervRegStartDate(Date nonNQFIntervRegStartDate) {
		this.nonNQFIntervRegStartDate = nonNQFIntervRegStartDate;
	}

	public Date getNonNQFIntervRegEndDate() {
		return nonNQFIntervRegEndDate;
	}

	public void setNonNQFIntervRegEndDate(Date nonNQFIntervRegEndDate) {
		this.nonNQFIntervRegEndDate = nonNQFIntervRegEndDate;
	}

	public String getFiller03() {
		return filler03;
	}

	public void setFiller03(String filler03) {
		this.filler03 = filler03;
	}

	public String getNonNQFIntervETQEId() {
		return nonNQFIntervETQEId;
	}

	public void setNonNQFIntervETQEId(String nonNQFIntervETQEId) {
		this.nonNQFIntervETQEId = nonNQFIntervETQEId;
	}

	public String getNonNQFIntervStatusId() {
		return nonNQFIntervStatusId;
	}

	public void setNonNQFIntervStatusId(String nonNQFIntervStatusId) {
		this.nonNQFIntervStatusId = nonNQFIntervStatusId;
	}

	public String getNonNQFIntervCredit() {
		return nonNQFIntervCredit;
	}

	public void setNonNQFIntervCredit(String nonNQFIntervCredit) {
		this.nonNQFIntervCredit = nonNQFIntervCredit;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getLearningProgrammeTypeId() {
		return learningProgrammeTypeId;
	}

	public void setLearningProgrammeTypeId(String learningProgrammeTypeId) {
		this.learningProgrammeTypeId = learningProgrammeTypeId;
	}
}
