package haj.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HAJProperties.
 */
@Entity
@Table(name = "employee_pages")
public class EmployeePages implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The e property id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/** The e property. */
	@Column(name = "page", length = 100, nullable = false)
	private String page;

	/**
	 * Instantiates a new HAJ properties.
	 */
	public EmployeePages() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EmployeePages other = (EmployeePages) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}
