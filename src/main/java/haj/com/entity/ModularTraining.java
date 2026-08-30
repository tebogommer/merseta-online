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
 * The Class EmployeesTraining.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "modular_training")
public class ModularTraining implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	
	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "module", length = 100)
	private String module;
	
	@Column(name = "mentor", length = 100)
	private String mentor;

	@Column(name = "apprentice", length = 100)
	private String apprentice;

	@Column(name = "mentor1", length = 100)
	private String mentor1;

	@Column(name = "apprentice1", length = 100)
	private String apprentice1;

	
	

		
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
		ModularTraining other = (ModularTraining) obj;
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
	 * @param id            the id to set
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
	 * @param createDate            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMentor() {
		return mentor;
	}

	public void setMentor(String mentor) {
		this.mentor = mentor;
	}

	public String getApprentice() {
		return apprentice;
	}

	public void setApprentice(String apprentice) {
		this.apprentice = apprentice;
	}

	public String getMentor1() {
		return mentor1;
	}

	public void setMentor1(String mentor1) {
		this.mentor1 = mentor1;
	}

	public String getApprentice1() {
		return apprentice1;
	}

	public void setApprentice1(String apprentice1) {
		this.apprentice1 = apprentice1;
	}
}
