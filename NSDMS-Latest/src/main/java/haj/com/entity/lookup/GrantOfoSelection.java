package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * GrantOfoSelection.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "grant_ofo_selection")
public class GrantOfoSelection extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of AbetBand. */
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "grant_year")
	private Integer grantYear;
	
	@Column(name = "ofo_wsp_selection_year")
	private Integer ofoWspSelectionYear;
	
	@Column(name = "ofo_atr_selection_year")
	private Integer ofoAtrSelectionYear;

	/**
	 * Instantiates a new GrantOfoSelection.
	 */
	public GrantOfoSelection() {
		super();
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
		GrantOfoSelection other = (GrantOfoSelection) obj;
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

	public Integer getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}

	public Integer getOfoWspSelectionYear() {
		return ofoWspSelectionYear;
	}

	public void setOfoWspSelectionYear(Integer ofoWspSelectionYear) {
		this.ofoWspSelectionYear = ofoWspSelectionYear;
	}

	public Integer getOfoAtrSelectionYear() {
		return ofoAtrSelectionYear;
	}

	public void setOfoAtrSelectionYear(Integer ofoAtrSelectionYear) {
		this.ofoAtrSelectionYear = ofoAtrSelectionYear;
	}

}
