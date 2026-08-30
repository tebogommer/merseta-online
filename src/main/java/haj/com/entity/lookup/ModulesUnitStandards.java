package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * Funding Funding_Id.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "modules_unit_standards")
public class ModulesUnitStandards extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Funding. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modules_id", nullable = false)
	private Modules modules;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standards_id", nullable = false)
	private UnitStandards unitStandards;

	public ModulesUnitStandards() {
		super();
	}

	public ModulesUnitStandards(Modules modules, UnitStandards unitStandards) {
		super();
		this.modules = modules;
		this.unitStandards = unitStandards;
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
		ModulesUnitStandards other = (ModulesUnitStandards) obj;
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

	public Modules getModules() {
		return modules;
	}

	public void setModules(Modules modules) {
		this.modules = modules;
	}

	public UnitStandards getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(UnitStandards unitStandards) {
		this.unitStandards = unitStandards;
	}

}
