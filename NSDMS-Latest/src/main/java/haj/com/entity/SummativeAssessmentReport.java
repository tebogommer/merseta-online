package haj.com.entity;

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

import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "summative_assessment_report")
public class SummativeAssessmentReport implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_seta_qualifications_completion_id", nullable = true)
	private NonSetaQualificationsCompletion nonSetaQualificationsCompletion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_verfication_id", nullable = true)
	private TrainingProviderVerfication trainingProviderVerfication;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	private Users users;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	private InterventionType interventionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_aligned_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private YesNoLookup nqfAligned;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsSet skillsSet;

	/** The pivot non pivot. */
	@Enumerated
	@Column(name = "pivot_non_pivot")
	private PivotNonPivotEnum pivotNonPivot;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_levels_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqfLevels;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionLevel interventionLevel;

	@Transient
	private List<SummativeAssessmentReportUnitStandards> unitStandards;

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
		SummativeAssessmentReport other = (SummativeAssessmentReport) obj;
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

	public NonSetaQualificationsCompletion getNonSetaQualificationsCompletion() {
		return nonSetaQualificationsCompletion;
	}

	public void setNonSetaQualificationsCompletion(NonSetaQualificationsCompletion nonSetaQualificationsCompletion) {
		this.nonSetaQualificationsCompletion = nonSetaQualificationsCompletion;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public YesNoLookup getNqfAligned() {
		return nqfAligned;
	}

	public void setNqfAligned(YesNoLookup nqfAligned) {
		this.nqfAligned = nqfAligned;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
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

	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
	}

	public NqfLevels getNqfLevels() {
		return nqfLevels;
	}

	public void setNqfLevels(NqfLevels nqfLevels) {
		this.nqfLevels = nqfLevels;
	}

	public InterventionLevel getInterventionLevel() {
		return interventionLevel;
	}

	public void setInterventionLevel(InterventionLevel interventionLevel) {
		this.interventionLevel = interventionLevel;
	}

	public TrainingProviderVerfication getTrainingProviderVerfication() {
		return trainingProviderVerfication;
	}

	public void setTrainingProviderVerfication(TrainingProviderVerfication trainingProviderVerfication) {
		this.trainingProviderVerfication = trainingProviderVerfication;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	@Transient
	public Integer getQualificationID() {
		Qualification qual = null;
		if (companyLearners != null) qual = companyLearners.getQualification() != null ? companyLearners.getQualification() : null;
		else qual = qualification != null ? qualification : null;
		return qual != null ? qual.getCode() : null;

	}

	@Transient
	public Long getSkillID() {
		Long id = null;
		if (companyLearners != null) id = companyLearners.getSkillsProgram() != null ? companyLearners.getSkillsProgram().getId() : companyLearners.getSkillsSet() != null ? companyLearners.getSkillsSet().getId() : null;
		else id = skillsProgram != null ? skillsProgram.getId() : skillsSet != null ? skillsSet.getId() : null;
		return id;

	}

	@Transient
	public Long getInterventionTypeId() {
		Long id = null;
		if (companyLearners != null) id = companyLearners.getInterventionType().getId();
		else id = interventionType.getId();
		return id;
	}

	public List<SummativeAssessmentReportUnitStandards> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<SummativeAssessmentReportUnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

}
