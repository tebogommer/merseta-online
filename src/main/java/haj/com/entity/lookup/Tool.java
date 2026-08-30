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
 * bank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "tool")
public class Tool extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of bank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of tool. */
	@Column(name = "description", length = 500)
	private String description;
	
	/** Tool Category. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tool_category_id")
	private ToolCategory toolCategory;
	
	/** The Ratio. */
	@Column(name = "ratio_id")
	private Ratio ratio;

	/**
	 * Instantiates a new bank.
	 */
	public Tool() {
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
		Tool other = (Tool) obj;
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
	 * @param description            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public ToolCategory getToolCategory() {
		return toolCategory;
	}

	public void setToolCategory(ToolCategory toolCategory) {
		this.toolCategory = toolCategory;
	}

	public Ratio getRatio() {
		return ratio;
	}

	public void setRatio(Ratio ratio) {
		this.ratio = ratio;
	}

}
