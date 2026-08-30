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
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.lookup.DeviationReason;
import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "mg_verification_details")
public class MgVerificationDetails implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reset_date", length = 19)
	private Date resetDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Column(name = "final_fin")
	private Long finalFin;

	/** Note. */
	@Column(name = "note", columnDefinition = "LONGTEXT")
	private String note;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mandatory_grant_detail_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private MandatoryGrantDetail mandatoryGrantDetail;
	
	@Column(name = "no_planned_learners")
	private long noPlannedLearners;

	@Column(name = "no_learners_started")
	private Integer noLearnersStarted;
	
	@Column(name = "no_learners_withdrawn")
	private Integer noLearnersWithdrawn;
	
	@Column(name = "no_learners_completed")
	private Integer noLearnersCompleted;
	
	@Column(name = "action_plan", columnDefinition = "LONGTEXT")
	private String actionPlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deviation_reason_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private DeviationReason deviationReason;
	
	@Column(name = "evidance_required", columnDefinition = "BIT default false")
	private Boolean evidanceRequired;
	
	/* New Fields to replicate DG verification but for MG verification  Start*/
	/** The amount. */
	@Column(name = "quantity")
	private Integer amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionType interventionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_code_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private OfoCodes ofoCodes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Wsp wsp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_aligned_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private YesNoLookup nqfAligned;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification qualification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsSet skillsSet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_credit_bearing_intervation_title_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionLevel interventionLevel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_levels_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqfLevels;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private ProviderType providerType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	private Date endDate;
	
	@Column(name = "disabled_amount")
	private Integer disabledAmount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enrolment_status", nullable = true)
	@Fetch(FetchMode.JOIN)
	private EnrolmentStatus enrolmentStatus;
	
	@Enumerated
	@Column(name = "employment_status")
	private EmploymentStatusEnum employmentStatus;
	/* New Fields to replicate DG verification but for MG verification  END*/
	
	@Transient
	private Doc doc;
	
	@Transient
	private List<Doc> docs;

	public MgVerificationDetails() {
		super();
	}
	
	public MgVerificationDetails(MandatoryGrantDetail mandatoryGrantDetail, Long finalFin,long noPlannedLearners, Boolean evidanceRequired) {
		this.mandatoryGrantDetail = mandatoryGrantDetail;
		this.finalFin=finalFin;
		this.noPlannedLearners = noPlannedLearners;
		this.evidanceRequired = evidanceRequired;
	}
	
	public MgVerificationDetails(long amount, InterventionType interventionType, OfoCodes ofoCode, EnrolmentStatus enrolmentStatus, EmploymentStatusEnum employmentStatus, Wsp wsp, YesNoLookup nqfAligned, Qualification qualification, SkillsProgram skillsProgram, SkillsSet skillsSet, NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle, UnitStandards unitStandard, InterventionLevel interventionLevel, NqfLevels nqfLevels, ProviderType providerType, Date startDate, Date endDate, long disabledAmount) {
		super();
		this.amount = Long.valueOf(amount).intValue();
		this.interventionType = interventionType;
		this.ofoCodes = ofoCode;
		this.enrolmentStatus = enrolmentStatus;
		this.employmentStatus = employmentStatus;
		this.wsp = wsp;
		this.nqfAligned = nqfAligned;
		this.qualification = qualification;
		this.skillsProgram = skillsProgram;
		this.skillsSet = skillsSet;
		this.nonCreditBearingIntervationTitle = nonCreditBearingIntervationTitle;
		this.unitStandard = unitStandard;
		this.interventionLevel = interventionLevel;
		this.nqfLevels = nqfLevels;
		this.providerType = providerType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.disabledAmount = Long.valueOf(disabledAmount).intValue();
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MgVerificationDetails other = (MgVerificationDetails) obj;
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
	 * @param id
	 *            the id to set
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
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getFinalFin() {
		return finalFin;
	}

	public void setFinalFin(Long finalFin) {
		this.finalFin = finalFin;
	}

	public MandatoryGrantDetail getMandatoryGrantDetail() {
		return mandatoryGrantDetail;
	}

	public void setMandatoryGrantDetail(MandatoryGrantDetail mandatoryGrantDetail) {
		mandatoryGrantDetail = mandatoryGrantDetail;
	}

	public long getNoPlannedLearners() {
		return noPlannedLearners;
	}

	public void setNoPlannedLearners(long noPlannedLearners) {
		this.noPlannedLearners = noPlannedLearners;
	}

	public Integer getNoLearnersStarted() {
		return noLearnersStarted;
	}

	public void setNoLearnersStarted(Integer noLearnersStarted) {
		this.noLearnersStarted = noLearnersStarted;
	}

	public Integer getNoLearnersWithdrawn() {
		return noLearnersWithdrawn;
	}

	public void setNoLearnersWithdrawn(Integer noLearnersWithdrawn) {
		this.noLearnersWithdrawn = noLearnersWithdrawn;
	}

	public Integer getNoLearnersCompleted() {
		return noLearnersCompleted;
	}

	public void setNoLearnersCompleted(Integer noLearnersCompleted) {
		this.noLearnersCompleted = noLearnersCompleted;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public DeviationReason getDeviationReason() {
		return deviationReason;
	}

	public void setDeviationReason(DeviationReason deviationReason) {
		this.deviationReason = deviationReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Boolean getEvidanceRequired() {
		return evidanceRequired;
	}

	public void setEvidanceRequired(Boolean evidanceRequired) {
		this.evidanceRequired = evidanceRequired;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public YesNoLookup getNqfAligned() {
		return nqfAligned;
	}

	public void setNqfAligned(YesNoLookup nqfAligned) {
		this.nqfAligned = nqfAligned;
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

	public NonCreditBearingIntervationTitle getNonCreditBearingIntervationTitle() {
		return nonCreditBearingIntervationTitle;
	}

	public void setNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle) {
		this.nonCreditBearingIntervationTitle = nonCreditBearingIntervationTitle;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public InterventionLevel getInterventionLevel() {
		return interventionLevel;
	}

	public void setInterventionLevel(InterventionLevel interventionLevel) {
		this.interventionLevel = interventionLevel;
	}

	public NqfLevels getNqfLevels() {
		return nqfLevels;
	}

	public void setNqfLevels(NqfLevels nqfLevels) {
		this.nqfLevels = nqfLevels;
	}

	public ProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getDisabledAmount() {
		return disabledAmount;
	}

	public void setDisabledAmount(Integer disabledAmount) {
		this.disabledAmount = disabledAmount;
	}

	public EnrolmentStatus getEnrolmentStatus() {
		return enrolmentStatus;
	}

	public void setEnrolmentStatus(EnrolmentStatus enrolmentStatus) {
		this.enrolmentStatus = enrolmentStatus;
	}

	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
}
