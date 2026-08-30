package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * DateChangeReasons.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "date_change_reasons")
public class DateChangeReasons extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of RejectReasons. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of DateChangeReasons. */
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "for_process", length = 500)
	private ConfigDocProcessEnum forProcess;
	
	@Column(name = "for_manager_selection")
	private Boolean forManagerSelection;

	/**
	 * Instantiates a new Date Change Reasons.
	 */
	public DateChangeReasons() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DateChangeReasons other = (DateChangeReasons) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the for process.
	 *
	 * @return the for process
	 */
	public ConfigDocProcessEnum getForProcess() {
		return forProcess;
	}

	/**
	 * Sets the for process.
	 *
	 * @param forProcess
	 *            the new for process
	 */
	public void setForProcess(ConfigDocProcessEnum forProcess) {
		this.forProcess = forProcess;
	}

	public Boolean getForManagerSelection() {
		return forManagerSelection;
	}

	public void setForManagerSelection(Boolean forManagerSelection) {
		this.forManagerSelection = forManagerSelection;
	}

}
