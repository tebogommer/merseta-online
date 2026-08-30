package haj.com.entity.formconfig;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import haj.com.entity.enums.SectionEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class FormTypeSections.
 */
@Entity
@Table(name = "form_type_sections")
public class FormTypeSections implements IDataEntity, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "section_desc", columnDefinition = "LONGTEXT")
	private String sectionDesc;

	@Column(name = "section_title", columnDefinition = "LONGTEXT")
	private String sectionTitle;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_template_id")
	private FormType parentTemplate;

	@Column(name = "order_pos")
	private Integer orderPos;

	@Enumerated
	@Column(name = "section_type")
	private SectionEnum sectionType;

	@Transient
	private List<FormSectionQuestions> formSectionQuestions;

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
		FormTypeSections other = (FormTypeSections) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public FormTypeSections clone() {
		FormTypeSections clone = null;
		try {
			clone = (FormTypeSections) super.clone();
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	public FormTypeSections clone(FormType parent) {
		FormTypeSections clone = null;
		try {
			clone = (FormTypeSections) super.clone();
			clone.setParentTemplate(parent);
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the section desc.
	 *
	 * @return the section desc
	 */
	public String getSectionDesc() {
		return sectionDesc;
	}

	/**
	 * Sets the section desc.
	 *
	 * @param sectionDesc
	 *            the new section desc
	 */
	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Sets the section title.
	 *
	 * @param sectionTitle
	 *            the new section title
	 */
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	/**
	 * Gets the parent template.
	 *
	 * @return the parent template
	 */
	public FormType getParentTemplate() {
		return parentTemplate;
	}

	/**
	 * Sets the parent template.
	 *
	 * @param parentTemplate
	 *            the new parent template
	 */
	public void setParentTemplate(FormType parentTemplate) {
		this.parentTemplate = parentTemplate;
	}

	/**
	 * Gets the order pos.
	 *
	 * @return the order pos
	 */
	public Integer getOrderPos() {
		return orderPos;
	}

	/**
	 * Sets the order pos.
	 *
	 * @param orderPos
	 *            the new order pos
	 */
	public void setOrderPos(Integer orderPos) {
		this.orderPos = orderPos;
	}

	public SectionEnum getSectionType() {
		return sectionType;
	}

	public void setSectionType(SectionEnum sectionType) {
		this.sectionType = sectionType;
	}

	public List<FormSectionQuestions> getFormSectionQuestions() {
		return formSectionQuestions;
	}

	public void setFormSectionQuestions(List<FormSectionQuestions> formSectionQuestions) {
		this.formSectionQuestions = formSectionQuestions;
	}

}
