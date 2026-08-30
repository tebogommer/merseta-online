package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.Users;
import haj.com.entity.enums.RatingEnum;
import haj.com.entity.enums.RelationTypeEnum;
import haj.com.framework.AbstractLookup;

/**
 * ProviderAccreditAssessor.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "stakeholder_relations_survey")
public class StakeholderRelationsSurvey extends AbstractLookup {

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
	@JoinColumn(name = "stakeholder_relations_id", nullable = true)
	private StakeholderRelations stakeholderRelations;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	// ratingEnum
	@Enumerated(EnumType.STRING)
	@Column(name = "rating_enum")
	private RatingEnum ratingEnum;
	
	/** The manager selection */
	@Column(name = "toa_large_extent")
	private Boolean toaLargeExtent;
	/** The manager selection */
	@Column(name = "toa_limited_extent")
	private Boolean toaLimitedExtent;
	/** The manager selection */
	@Column(name = "nuetral")
	private Boolean nuetral;
	/** The manager selection */
	@Column(name = "not_really")
	private Boolean notReally;
	/** The manager selection */
	@Column(name = "not_at_all")
	private Boolean notAtAll;

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
		StakeholderRelationsSurvey other = (StakeholderRelationsSurvey) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the stakeholderRelations
	 */
	public StakeholderRelations getStakeholderRelations() {
		return stakeholderRelations;
	}

	/**
	 * @param stakeholderRelations
	 *            the stakeholderRelations to set
	 */
	public void setStakeholderRelations(StakeholderRelations stakeholderRelations) {
		this.stakeholderRelations = stakeholderRelations;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the ratingEnum
	 */
	public RatingEnum getRatingEnum() {
		return ratingEnum;
	}

	/**
	 * @param ratingEnum
	 *            the ratingEnum to set
	 */
	public void setRatingEnum(RatingEnum ratingEnum) {
		this.ratingEnum = ratingEnum;
	}

	public Boolean getToaLargeExtent() {
		return toaLargeExtent;
	}

	public void setToaLargeExtent(Boolean toaLargeExtent) {
		this.toaLargeExtent = toaLargeExtent;
	}

	public Boolean getToaLimitedExtent() {
		return toaLimitedExtent;
	}

	public void setToaLimitedExtent(Boolean toaLimitedExtent) {
		this.toaLimitedExtent = toaLimitedExtent;
	}

	public Boolean getNuetral() {
		return nuetral;
	}

	public void setNuetral(Boolean nuetral) {
		this.nuetral = nuetral;
	}

	public Boolean getNotReally() {
		return notReally;
	}

	public void setNotReally(Boolean notReally) {
		this.notReally = notReally;
	}

	public Boolean getNotAtAll() {
		return notAtAll;
	}

	public void setNotAtAll(Boolean notAtAll) {
		this.notAtAll = notAtAll;
	}
}
