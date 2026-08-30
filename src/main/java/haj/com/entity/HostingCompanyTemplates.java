package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import haj.com.entity.enums.TemplateTypeEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyTemplates.
 */
@Entity
@Table(name = "hosting_company_templates")
public class HostingCompanyTemplates implements IDataEntity, Cloneable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/** The title. */
	@Column(name="title", columnDefinition="LONGTEXT")
	private String title;
	
	/** The user. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="uid", nullable=false)
    private Users user;
	
	/** The hosting company. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hosting_company_id", nullable=false)
	private HostingCompany hostingCompany;

    /** The parent template. */
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="template_id",nullable=true)
    private HostingCompanyTemplates parentTemplate;
    
    /** The template type. */
    @Enumerated
    @Column(name="template_type")
    private TemplateTypeEnum templateType;

    /** The create date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", length=19)
    private Date createDate;
  
    /** The unique id. */
    @Column(name="unique_id", length=50)
    private String uniqueId;
	
    /** The original template. */
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orig_template_id",nullable=true)
    private HostingCompanyTemplates originalTemplate;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
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
		HostingCompanyTemplates other = (HostingCompanyTemplates) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}


	/**
	 * Gets the parent template.
	 *
	 * @return the parent template
	 */
	public HostingCompanyTemplates getParentTemplate() {
		return parentTemplate;
	}

	/**
	 * Sets the parent template.
	 *
	 * @param parentTemplate the new parent template
	 */
	public void setParentTemplate(HostingCompanyTemplates parentTemplate) {
		this.parentTemplate = parentTemplate;
	}

	/**
	 * Gets the template type.
	 *
	 * @return the template type
	 */
	public TemplateTypeEnum getTemplateType() {
		return templateType;
	}

	/**
	 * Sets the template type.
	 *
	 * @param templateType the new template type
	 */
	public void setTemplateType(TemplateTypeEnum templateType) {
		this.templateType = templateType;
	}

	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
	}

	/**
	 * Gets the unique id.
	 *
	 * @return the unique id
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the unique id.
	 *
	 * @param uniqueId the new unique id
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	/**
	 * Hs compare to.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean hsCompareTo(HostingCompanyTemplates o) {
		boolean equal = false;
		if (this.id.equals(o.getOriginalTemplate().getId())) {
			equal = true;
		}
		return equal;
	}

	/**
	 * Hs compare to old.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean hsCompareToOld(HostingCompanyTemplates o) {
		boolean equal = false;

		if (this.getOriginalTemplate()!=null && this.getOriginalTemplate().equals(o)) {
			equal = true;
		}
		return equal;
	}
	
	/**
	 * Gets the original template.
	 *
	 * @return the original template
	 */
	public HostingCompanyTemplates getOriginalTemplate() {
		return originalTemplate;
	}

	/**
	 * Sets the original template.
	 *
	 * @param originalTemplate the new original template
	 */
	public void setOriginalTemplate(HostingCompanyTemplates originalTemplate) {
		this.originalTemplate = originalTemplate;
	}
	
}
