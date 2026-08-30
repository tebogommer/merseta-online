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
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "file506")
public class File506 implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	/** Note. */
	@Column(name="note", columnDefinition="LONGTEXT")
    private String note;

	@Column(name="National_Id", columnDefinition="LONGTEXT") private String NationalId;
	@Column(name="Provider", columnDefinition="LONGTEXT") private String Provider;
	@Column(name="Provider_ETQE", columnDefinition="LONGTEXT") private String ProviderETQE;
	@Column(name="POPI_Act_Status", columnDefinition="LONGTEXT") private String POPIActStatus;
	@Column(name="POPI_Act_Status_Date", columnDefinition="LONGTEXT") private String POPIActStatusDate;
	@Column(name="Qualification_Id", columnDefinition="LONGTEXT") private String QualificationId;
	@Column(name="Enrolment_Status", columnDefinition="LONGTEXT") private String EnrolmentStatus;
	@Column(name="Enrolment_Type", columnDefinition="LONGTEXT") private String EnrolmentType;
	@Column(name="Enrolment_Status_Date", columnDefinition="LONGTEXT") private String EnrolmentStatusDate;
	@Column(name="Enrolment_Date", columnDefinition="LONGTEXT") private String EnrolmentDate;
	@Column(name="Economic_Status", columnDefinition="LONGTEXT") private String EconomicStatus;
	@Column(name="SDL_No", columnDefinition="LONGTEXT") private String SDLNo;
	@Column(name="Funding_Id", columnDefinition="LONGTEXT") private String FundingId;
	@Column(name="OFO_Code", columnDefinition="LONGTEXT") private String OFOCode;
	@Column(name="Urban_Rural", columnDefinition="LONGTEXT") private String UrbanRural;
	@Column(name="LastUpDate", columnDefinition="LONGTEXT") private String LastUpDate;

    
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
		File506 other = (File506) obj;
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

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}



}
