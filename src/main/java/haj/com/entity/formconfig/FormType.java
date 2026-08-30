/*-------------------------------------------------------------------*
Programmer: wesley    		
Date: 08 Dec 2016                    
Project: RockCOP										
Package: com.techfinium.module.formconfig.entity
*-------------------------------------------------------------------*/
package haj.com.entity.formconfig;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

/**
 * The Class FormType.
 */
@Entity
@Table(name = "form_type")
public class FormType implements IDataEntity, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The points threshold. */
	@Column(name = "points_threshold")
	private Long pointsThreshold;

	/** The form title. */
	@Column(name = "form_title", columnDefinition = "LONGTEXT")
	private String formTitle;

	/** The form title. */
	@Column(name = "form_introduction", columnDefinition = "LONGTEXT")
	private String formIntroduction;

	/** The order pos. */
	@Column(name = "order_pos")
	private Integer orderPos;

	/** The users. */
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Users users;

	@JoinColumn(name = "company_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Company company;

	/** The active. */
	@Column(name = "active")
	private Boolean active;
	
	/** The form title. */
	@Column(name = "paper_number", columnDefinition = "LONGTEXT")
	private String paperNumber;
	
	/** The Qualification linked to the User. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;
	
	private ArrayList<FormTypeSections> formTypeSectionsList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public FormType clone() {
		FormType clone = null;
		try {
			clone = (FormType) super.clone();
			clone.setId(null);
			clone.setCreateDate(new java.util.Date());
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;
	}

	public FormType clone(Users users) {
		FormType clone = null;
		try {
			clone = (FormType) super.clone();
			clone.setUsers(users);
			clone.setCreateDate(new java.util.Date());
			clone.setId(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;
	}

	public FormType clone(Users users, Company company) {
		FormType clone = null;
		try {
			clone = (FormType) super.clone();
			clone.setCompany(company);
			clone.setUsers(users);
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
	 * Gets the points threshold.
	 *
	 * @return the points threshold
	 */
	public Long getPointsThreshold() {
		return pointsThreshold;
	}

	/**
	 * Sets the points threshold.
	 *
	 * @param pointsThreshold
	 *            the new points threshold
	 */
	public void setPointsThreshold(Long pointsThreshold) {
		this.pointsThreshold = pointsThreshold;
	}

	/**
	 * Gets the form title.
	 *
	 * @return the form title
	 */
	public String getFormTitle() {
		return formTitle;
	}

	/**
	 * Sets the form title.
	 *
	 * @param formTitle
	 *            the new form title
	 */
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
		FormType other = (FormType) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public String getFormIntroduction() {
		return formIntroduction;
	}

	public void setFormIntroduction(String formIntroduction) {
		this.formIntroduction = formIntroduction;
	}

	public String getPaperNumber() {
		return paperNumber;
	}

	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * @return the formTypeSectionsList
	 */
	public ArrayList<FormTypeSections> getFormTypeSectionsList() {
		return formTypeSectionsList;
	}

	/**
	 * @param formTypeSectionsList the formTypeSectionsList to set
	 */
	public void setFormTypeSectionsList(ArrayList<FormTypeSections> formTypeSectionsList) {
		this.formTypeSectionsList = formTypeSectionsList;
	}

}
