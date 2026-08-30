package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity is the unit standards 
 * 
 * UnitStandards.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "unit_standards")
public class UnitStandards_backup implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of UnitStandards. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** title or description of the unit standard. */
	@Column(name = "title", columnDefinition = "LONGTEXT")
	private String title;

	/** Code for the unit standard. */
	@Column(name = "code", columnDefinition = "LONGTEXT")
	private String code;

	/** Link of the NQF level to the unit standard. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_level_id")
	private NqfLevels nqfLevel;
	
	/** Link of the Abet Band to the unit standard. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "abet_band_id")
	private AbetBand abetBand;
	
	/** The credits. */
	private Integer credits;
	
	/**
	 * Default Constructor.
	 *
	 * @param title the title
	 * @param code the code
	 * @param nqfLevel the nqf level
	 */

	
	/**
	 * Constructor using title, code and NQF Level fields
	 * 
	 * @param title
	 * @param code
	 * @param nqfLevel
	 */
	public UnitStandards_backup(String title, String code, NqfLevels nqfLevel) {
		super();
		this.title = title;
		this.code = code;
		this.nqfLevel = nqfLevel;
	}

	/**
	 * Instantiates a new unit standards backup.
	 */
	public UnitStandards_backup() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		UnitStandards_backup other = (UnitStandards_backup) obj;
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
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the title.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the code.
	 *
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the nqf level.
	 *
	 * @return nqfLevel
	 */
	public NqfLevels getNqfLevel() {
		return nqfLevel;
	}

	/**
	 * Sets the nqf level.
	 *
	 * @param nqfLevel the new nqf level
	 */
	public void setNqfLevel(NqfLevels nqfLevel) {
		this.nqfLevel = nqfLevel;
	}

	/**
	 * Gets the abet band.
	 *
	 * @return abetBand
	 */
	public AbetBand getAbetBand() {
		return abetBand;
	}

	/**
	 * Sets the abet band.
	 *
	 * @param abetBand the new abet band
	 */
	public void setAbetBand(AbetBand abetBand) {
		this.abetBand = abetBand;
	}

	/**
	 * Gets the credits.
	 *
	 * @return the credits
	 */
	public Integer getCredits() {
		return credits;
	}

	/**
	 * Sets the credits.
	 *
	 * @param credits the new credits
	 */
	public void setCredits(Integer credits) {
		this.credits = credits;
	}

}
