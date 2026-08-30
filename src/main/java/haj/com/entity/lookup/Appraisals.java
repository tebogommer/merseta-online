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

import haj.com.entity.OfoCodes;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * bank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "appraisals")
public class Appraisals extends AbstractLookup {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "description", length = 500)
	private String description;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;
	
	@Transient
	private List<AppraisalCategoryCode> appraisalCategoryCode;

	@Transient
	private List<AppraisalCategories> appraisalListCategory;

	@Transient
	private List<AppraisalChecklist> appraisalChecklist;
	
	public Appraisals() {
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
		Appraisals other = (Appraisals) obj;
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

	public List<AppraisalCategories> getAppraisalListCategory() {
		return appraisalListCategory;
	}

	public void setAppraisalListCategory(List<AppraisalCategories> appraisalListCategory) {
		this.appraisalListCategory = appraisalListCategory;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public List<AppraisalChecklist> getAppraisalChecklist() {
		return appraisalChecklist;
	}

	public void setAppraisalChecklist(List<AppraisalChecklist> appraisalChecklist) {
		this.appraisalChecklist = appraisalChecklist;
	}

	public List<AppraisalCategoryCode> getAppraisalCategoryCode() {
		return appraisalCategoryCode;
	}

	public void setAppraisalCategoryCode(List<AppraisalCategoryCode> appraisalCategoryCode) {
		this.appraisalCategoryCode = appraisalCategoryCode;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
}
