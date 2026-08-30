package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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

import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.entity.enums.RelationTypeEnum;
import haj.com.framework.AbstractLookup;

/**
 * ProviderAccreditAssessor.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "stakeholder_relations")
public class StakeholderRelations extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of ProviderAccreditAssessor. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of ProviderAccreditAssessor. */
	@Column(name = "description", length = 500)
	private String description;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@Enumerated(EnumType.STRING)
	@Column(name = "relation_type_enum")
	private RelationTypeEnum relationTypeEnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_submitted", length = 19)
	private Date dateSubmitted;
	
	@Column(name = "stakeholder_relations_flat_id")
	private Long parentLink;
	
	@Column(name = "additional_comment", length = 550, nullable = true)
	private String additionalComment;
	
	@Transient
	private List<StakeholderRelationsSurvey> stakeholderRelationsSurveyList;
	
	@Transient
	private List<Doc> docAssignedList;
	

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
		StakeholderRelations other = (StakeholderRelations) obj;
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
	 * @return the relationTypeEnum
	 */
	public RelationTypeEnum getRelationTypeEnum() {
		return relationTypeEnum;
	}

	/**
	 * @param relationTypeEnum
	 *            the relationTypeEnum to set
	 */
	public void setRelationTypeEnum(RelationTypeEnum relationTypeEnum) {
		this.relationTypeEnum = relationTypeEnum;
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

	public List<StakeholderRelationsSurvey> getStakeholderRelationsSurveyList() {
		return stakeholderRelationsSurveyList;
	}

	public void setStakeholderRelationsSurveyList(List<StakeholderRelationsSurvey> stakeholderRelationsSurveyList) {
		this.stakeholderRelationsSurveyList = stakeholderRelationsSurveyList;
	}

	public List<Doc> getDocAssignedList() {
		return docAssignedList;
	}

	public void setDocAssignedList(List<Doc> docAssignedList) {
		this.docAssignedList = docAssignedList;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public Long getParentLink() {
		return parentLink;
	}

	public void setParentLink(Long parentLink) {
		this.parentLink = parentLink;
	}

	public String getAdditionalComment() {
		return additionalComment;
	}

	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}

}
