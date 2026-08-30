package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.Doc;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * Funding Funding_Id.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "modules")
public class Modules extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Funding. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "module_ref", length = 20)
	private String moduleRef;

	@Column(name = "module_title", length = 500)
	private String moduleTitle;

	@Column(name = "module_topic", length = 500)
	private String moduleTopic;

	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modules_category_id", nullable = true)
	private ModulesCategory modulesCategory;

	@Transient
	private List<ModulesUnitStandards> modulesUnitStandards;

	@Transient
	private List<Doc> docs;
	
	/*Total Courseware distribution request*/
	@Transient
	private Integer totalRequest;

	public Modules() {
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
		Modules other = (Modules) obj;
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

	public String getModuleRef() {
		return moduleRef;
	}

	public void setModuleRef(String moduleRef) {
		this.moduleRef = moduleRef;
	}

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public String getModuleTopic() {
		return moduleTopic;
	}

	public void setModuleTopic(String moduleTopic) {
		this.moduleTopic = moduleTopic;
	}

	public List<ModulesUnitStandards> getModulesUnitStandards() {
		return modulesUnitStandards;
	}

	public void setModulesUnitStandards(List<ModulesUnitStandards> modulesUnitStandards) {
		this.modulesUnitStandards = modulesUnitStandards;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public ModulesCategory getModulesCategory() {
		return modulesCategory;
	}

	public void setModulesCategory(ModulesCategory modulesCategory) {
		this.modulesCategory = modulesCategory;
	}

	public Integer getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(Integer totalRequest) {
		this.totalRequest = totalRequest;
	}

}
