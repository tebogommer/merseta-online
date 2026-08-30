
package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantRecommendation.
 */
@Entity
@Table(name = "mandatory_grant_recommendation")
@AuditTable(value = "mandatory_grant_recommendation_history")
@Audited
public class MandatoryGrantRecommendation implements IDataEntity {

	/** Unique Id of MandatoryGrant. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mandatory_grant_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private MandatoryGrant mandatoryGrant;

	/** The amount. */
	@Column(name = "quantity")
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification qualification;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsSet skillsSet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standards_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandards;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_credit_bearing_intervation_title_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recommending_user_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Users recommendingUser;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reject_reasons_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private RejectReasons rejectReasons;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionType interventionType;

	public MandatoryGrantRecommendation() {
		super();
	}

	public MandatoryGrantRecommendation(MandatoryGrant mandatoryGrant, Users recommendingUser) {
		super();
		this.mandatoryGrant = mandatoryGrant;
		this.recommendingUser = recommendingUser;
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
		MandatoryGrantRecommendation other = (MandatoryGrantRecommendation) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Users getRecommendingUser() {
		return recommendingUser;
	}

	public void setRecommendingUser(Users recommendingUser) {
		this.recommendingUser = recommendingUser;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public RejectReasons getRejectReasons() {
		return rejectReasons;
	}

	public void setRejectReasons(RejectReasons rejectReasons) {
		this.rejectReasons = rejectReasons;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public UnitStandards getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(UnitStandards unitStandards) {
		this.unitStandards = unitStandards;
	}

	public NonCreditBearingIntervationTitle getNonCreditBearingIntervationTitle() {
		return nonCreditBearingIntervationTitle;
	}

	public void setNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle) {
		this.nonCreditBearingIntervationTitle = nonCreditBearingIntervationTitle;
	}
}
