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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;


@Entity
@Table(name = "legacy_provider_application_alteration_audit")
public class LegacyProviderApplicationAlterationAudit implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of LegacyProviderApplicationAlterationAudit. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_provider_accreditation_id", nullable = true)
	private LegacyProviderAccreditation legacyProviderAccreditation;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_provider_application_alteration_audit_id", nullable = true)
	private LegacyProviderApplicationAlterationAudit legacyProviderApplicationAlterationAudit;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users users;
	
	@Enumerated
	@Column(name = "qualification_type_selection")
	private QualificationTypeSelectionEnum qualificationTypeSelectionEnum;
	
	@Column(name = "ref_number")
	private Integer refNumber;
	
	@Column(name = "reason_for_alteration")
	private String reasonForAlteration;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id", nullable = true)
	private SkillsProgram skillsProgram;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id", nullable = true)
	private SkillsSet skillsSet;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id", nullable = true)
	private UnitStandards unitStandard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learnership_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Learnership learnership;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LegacyProviderApplicationAlterationAudit other = (LegacyProviderApplicationAlterationAudit) obj;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getReasonForAlteration() {
		return reasonForAlteration;
	}

	public void setReasonForAlteration(String reasonForAlteration) {
		this.reasonForAlteration = reasonForAlteration;
	}

	public LegacyProviderAccreditation getLegacyProviderAccreditation() {
		return legacyProviderAccreditation;
	}

	public void setLegacyProviderAccreditation(LegacyProviderAccreditation legacyProviderAccreditation) {
		this.legacyProviderAccreditation = legacyProviderAccreditation;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public QualificationTypeSelectionEnum getQualificationTypeSelectionEnum() {
		return qualificationTypeSelectionEnum;
	}

	public void setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum qualificationTypeSelectionEnum) {
		this.qualificationTypeSelectionEnum = qualificationTypeSelectionEnum;
	}

	public Integer getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(Integer refNumber) {
		this.refNumber = refNumber;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	public LegacyProviderApplicationAlterationAudit getLegacyProviderApplicationAlterationAudit() {
		return legacyProviderApplicationAlterationAudit;
	}

	public void setLegacyProviderApplicationAlterationAudit(
			LegacyProviderApplicationAlterationAudit legacyProviderApplicationAlterationAudit) {
		this.legacyProviderApplicationAlterationAudit = legacyProviderApplicationAlterationAudit;
	}

}