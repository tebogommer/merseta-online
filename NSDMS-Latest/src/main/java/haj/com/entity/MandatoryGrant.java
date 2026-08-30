
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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.Training;
import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantBean.
 */
@Entity
@Table(name = "mandatory_grant")
@AuditTable(value = "mandatory_grant_history")
@Audited
public class MandatoryGrant implements IDataEntity {

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
	@JoinColumn(name = "wsp_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Wsp wsp;

	/** The amount. */
	@Column(name = "quantity")
	private Integer amount;

	@Column(name = "disabled_amount")
	@CSVAnnotation(name = "disabled_amount", className = Integer.class)
	private Integer disabledAmount;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_code_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private OfoCodes ofoCodes;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipality_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Municipality municipality;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Training training;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funding_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Funding funding;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionType interventionType;

	@Transient
	private InterventionType recinterventionType;

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

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_credit_bearing_intervation_title_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_levels_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqfLevels;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionLevel interventionLevel;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "etqa_id", nullable = true)
	private Etqa etqa;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private ProviderType providerType;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_delivery_method_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private TrainingDeliveryMethod trainingDeliveryMethod;

	/** Note. */
	@Column(name = "intervention_title", columnDefinition = "LONGTEXT")
	@CSVAnnotation(name = "intervention_title", className = String.class)
	private String interventionTitle;

	@Column(name = "estimated_cost")
	@CSVAnnotation(name = "estimated_cost", className = Double.class)
	private Double estimatedCost;

	/** The create date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	@CSVAnnotation(name = "start_date", className = Date.class, datePattern = "yyyy-MM-dd")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	@CSVAnnotation(name = "end_date", className = Date.class, datePattern = "yyyy-MM-dd")
	private Date endDate;

	@Enumerated
	@Column(name = "wsp_report")
	private WspReportEnum wspReport;

	/** The pivot non pivot. */
	@Enumerated
	@Column(name = "pivot_non_pivot")
	private PivotNonPivotEnum pivotNonPivot;

	@Column(name = "ofo_code", length = 11)
	@CSVAnnotation(name = "ofo_code", className = String.class)
	private String ofoCode;

	@Length(max = 10)
	@Column(name = "funding_code", length = 10)
	@CSVAnnotation(name = "funding_code", className = String.class)
	private String fundingCode;

	@Length(max = 10)
	@Column(name = "intervention_type_code", length = 10)
	@CSVAnnotation(name = "intervention_type_code", className = String.class)
	private String interventionTypeCode;

	@Column(name = "qualification_code")
	@CSVAnnotation(name = "qualification_code", className = Integer.class)
	private Integer qualificationCode;

	@Length(max = 10)
	@Column(name = "provider_type_code", length = 10)
	@CSVAnnotation(name = "provider_type_code", className = String.class)
	private String providerTypeCode;

	// Added for JIRA #1026
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disability_status_id", nullable = true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	private DisabilityStatus disability;

	@Length(max = 10)
	@Column(name = "training_delivery_method_code", length = 10)
	@CSVAnnotation(name = "training_delivery_method_code", className = String.class)
	private String trainingDeliveryMethodCode;

	/** The pivot non pivot. */
	@Enumerated
	@Column(name = "employment_status")
	private EmploymentStatusEnum employmentStatus;

	@Column(name = "employment_status_code")
	@CSVAnnotation(name = "employment_status_code", className = Integer.class)
	private Integer employmentStatusCode;

	@Column(name = "employee_age_18")
	@CSVAnnotation(name = "employee_age_18", className = Long.class)
	private Long employeeAge18;

	@Column(name = "employee_age_25")
	@CSVAnnotation(name = "employee_age_25", className = Long.class)
	private Long employeeAge25;

	@Column(name = "employee_age_35")
	@CSVAnnotation(name = "employee_age_35", className = Long.class)
	private Long employeeAge35;

	@Column(name = "employee_age_45")
	@CSVAnnotation(name = "employee_age_45", className = Long.class)
	private Long employeeAge45;

	@Column(name = "employee_age_55")
	@CSVAnnotation(name = "employee_age_55", className = Long.class)
	private Long employeeAge55;

	@Column(name = "employee_age_65")
	@CSVAnnotation(name = "employee_age_65", className = Long.class)
	private Long employeeAge65;

	@Column(name = "african_female")
	@CSVAnnotation(name = "african_female", className = Long.class)
	private Long africanFemale;

	@Column(name = "african_male")
	@CSVAnnotation(name = "african_male", className = Long.class)
	private Long africanMale;

	@Column(name = "coloured_female")
	@CSVAnnotation(name = "coloured_female", className = Long.class)
	private Long colouredFemale;

	@Column(name = "coloured_male")
	@CSVAnnotation(name = "coloured_male", className = Long.class)
	private Long colouredMale;

	@Column(name = "indian_female")
	@CSVAnnotation(name = "indian_female", className = Long.class)
	private Long indianFemale;

	@Column(name = "indian_male")
	@CSVAnnotation(name = "indian_male", className = Long.class)
	private Long indianMale;

	@Column(name = "white_female")
	@CSVAnnotation(name = "white_female", className = Long.class)
	private Long whiteFemale;

	@Column(name = "white_male")
	@CSVAnnotation(name = "white_male", className = Long.class)
	private Long whiteMale;

	@Column(name = "african_female_disability")
	@CSVAnnotation(name = "african_female_disability", className = Long.class)
	private Long africanFemaleDisability;

	@Column(name = "african_male_disability")
	@CSVAnnotation(name = "african_male_disability", className = Long.class)
	private Long africanMaleDisability;

	@Column(name = "coloured_female_disability")
	@CSVAnnotation(name = "coloured_female_disability", className = Long.class)
	private Long colouredFemaleDisability;

	@Column(name = "coloured_male_disability")
	@CSVAnnotation(name = "coloured_male_disability", className = Long.class)
	private Long colouredMaleDisability;

	@Column(name = "indian_female_disability")
	@CSVAnnotation(name = "indian_female_disability", className = Long.class)
	private Long indianFemaleDisability;

	@Column(name = "indian_male_disability")
	@CSVAnnotation(name = "indian_male_disability", className = Long.class)
	private Long indianMaleDisability;

	@Column(name = "white_female_disability")
	@CSVAnnotation(name = "white_female_disability", className = Long.class)
	private Long whiteFemaleDisability;

	@Column(name = "white_male_disability")
	@CSVAnnotation(name = "white_male_disability", className = Long.class)
	private Long whiteMaleDisability;

	@Column(name = "imported", columnDefinition = "BIT default true")
	private boolean imported;

	@Transient
	private List<MandatoryGrantRecommendation> grantRecommendations;

	@Transient
	private MandatoryGrantRecommendation grantRecommendation;

	@Transient
	private ApprovalEnum dgVerificationStatus;

	@Transient
	private DgVerification dgVerification;

	@Transient
	private Double estimatedCostAppliedFor;

	@Transient
	private Double estimatedCostRecommenedCrm;

	@Transient
	private WorkPlaceApproval workPlaceApproval;

	@Transient
	private Integer lastestRecommendLearnerAmount;

	@Transient
	private InterventionType interventionTypeReporting;

	@Transient
	private Qualification qualificationReporting;

	@Transient
	private SkillsSet skillsSetReporting;

	@Transient
	private SkillsProgram skillsProgramReporting;

	@Transient
	private NonCreditBearingIntervationTitle nonCreditBearingIntervationTitleReporting;

	@Transient
	private UnitStandards unitStandardsReporting;

	@Transient
	private String nqfLevelQualification;

	@Transient
	private List<MandatoryGrantWorkplace> workplaces;

	@Column(name = "import_error", columnDefinition = "BIT default false")
	private boolean importError;

	@Column(name = "import_errors", columnDefinition = "LONGTEXT")
	private String importErrors;

	public MandatoryGrant() {
		super();
	}

	public MandatoryGrant(Wsp wsp, WspReportEnum wspReport) {
		super();
		this.wsp = wsp;
		this.wspReport = wspReport;
	}

	/*
	 * Version 1 constructor for generation
	 */
	public MandatoryGrant(long amount, InterventionType interventionType, OfoCodes ofoCode, Wsp wsp, YesNoLookup nqfAligned, Qualification qualification, SkillsProgram skillsProgram, SkillsSet skillsSet, InterventionLevel interventionLevel, NqfLevels nqfLevels, ProviderType providerType, long disabledAmount) {
		super();
		this.amount = Long.valueOf(amount).intValue();
		this.interventionType = interventionType;
		this.ofoCodes = ofoCode;
		this.wsp = wsp;
		this.nqfAligned = nqfAligned;
		this.qualification = qualification;
		this.skillsProgram = skillsProgram;
		this.skillsSet = skillsSet;
		this.interventionLevel = interventionLevel;
		this.nqfLevels = nqfLevels;
		this.providerType = providerType;
		this.disabledAmount = Long.valueOf(disabledAmount).intValue();
	}

	/*
	 * Version 2 constructor for generation
	 */
	public MandatoryGrant(long amount, InterventionType interventionType, OfoCodes ofoCode, Wsp wsp, YesNoLookup nqfAligned, Qualification qualification, SkillsProgram skillsProgram, SkillsSet skillsSet, NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle, InterventionLevel interventionLevel, NqfLevels nqfLevels, ProviderType providerType, long disabledAmount) {
		super();
		this.amount = Long.valueOf(amount).intValue();
		this.interventionType = interventionType;
		this.ofoCodes = ofoCode;
		this.wsp = wsp;
		this.nqfAligned = nqfAligned;
		this.qualification = qualification;
		this.skillsProgram = skillsProgram;
		this.skillsSet = skillsSet;
		this.nonCreditBearingIntervationTitle = nonCreditBearingIntervationTitle;
		this.interventionLevel = interventionLevel;
		this.nqfLevels = nqfLevels;
		this.providerType = providerType;
		this.disabledAmount = Long.valueOf(disabledAmount).intValue();
	}

	/*
	 * Version 3 constructor for generation
	 */
	public MandatoryGrant(long amount, InterventionType interventionType, OfoCodes ofoCode, Wsp wsp, YesNoLookup nqfAligned, Qualification qualification, SkillsProgram skillsProgram, SkillsSet skillsSet, NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle, UnitStandards unitStandard, InterventionLevel interventionLevel, NqfLevels nqfLevels, ProviderType providerType, long disabledAmount) {
		super();
		this.amount = Long.valueOf(amount).intValue();
		this.interventionType = interventionType;
		this.ofoCodes = ofoCode;
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
		this.disabledAmount = Long.valueOf(disabledAmount).intValue();
	}

	/*
	 * Version 4 constructor for generation
	 */
	public MandatoryGrant(long amount, InterventionType interventionType, OfoCodes ofoCode, Wsp wsp, YesNoLookup nqfAligned, Qualification qualification, SkillsProgram skillsProgram, SkillsSet skillsSet, NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle, UnitStandards unitStandard, InterventionLevel interventionLevel, NqfLevels nqfLevels, ProviderType providerType, Date startDate, Date endDate, long disabledAmount) {
		super();
		this.amount = Long.valueOf(amount).intValue();
		this.interventionType = interventionType;
		this.ofoCodes = ofoCode;
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
		final int prime  = 31;
		int       result = 1;
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
		MandatoryGrant other = (MandatoryGrant) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the amount.
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	public double getGrantAmount() {
		double basic    = (interventionType.getBasicGrantAmount() == 0) ? 0 : interventionType.getBasicGrantAmount() * getNumberDisabled();
		double disabled = (interventionType.getDisabilityGrantAmount() == null) ? 0 : interventionType.getDisabilityGrantAmount() * (amount - getNumberDisabled());
		return basic + disabled;
	}

	/**
	 * Sets the amount.
	 * @param amount
	 * the new amount
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * Gets the pivot non pivot.
	 * @return the pivot non pivot
	 */
	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	/**
	 * Sets the pivot non pivot.
	 * @param pivotNonPivot
	 * the new pivot non pivot
	 */
	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Long getEmployeeAge18() {
		return employeeAge18;
	}

	public void setEmployeeAge18(Long employeeAge18) {
		this.employeeAge18 = employeeAge18;
	}

	public Long getEmployeeAge25() {
		return employeeAge25;
	}

	public void setEmployeeAge25(Long employeeAge25) {
		this.employeeAge25 = employeeAge25;
	}

	public Long getEmployeeAge35() {
		return employeeAge35;
	}

	public void setEmployeeAge35(Long employeeAge35) {
		this.employeeAge35 = employeeAge35;
	}

	public Long getEmployeeAge45() {
		return employeeAge45;
	}

	public void setEmployeeAge45(Long employeeAge45) {
		this.employeeAge45 = employeeAge45;
	}

	public Long getEmployeeAge55() {
		return employeeAge55;
	}

	public void setEmployeeAge55(Long employeeAge55) {
		this.employeeAge55 = employeeAge55;
	}

	public Long getEmployeeAge65() {
		return employeeAge65;
	}

	public void setEmployeeAge65(Long employeeAge65) {
		this.employeeAge65 = employeeAge65;
	}

	public Long getAfricanFemale() {
		return africanFemale;
	}

	public void setAfricanFemale(Long africanFemale) {
		this.africanFemale = africanFemale;
	}

	public Long getAfricanMale() {
		return africanMale;
	}

	public void setAfricanMale(Long africanMale) {
		this.africanMale = africanMale;
	}

	public Long getColouredFemale() {
		return colouredFemale;
	}

	public void setColouredFemale(Long colouredFemale) {
		this.colouredFemale = colouredFemale;
	}

	public Long getColouredMale() {
		return colouredMale;
	}

	public void setColouredMale(Long colouredMale) {
		this.colouredMale = colouredMale;
	}

	public Long getIndianFemale() {
		return indianFemale;
	}

	public void setIndianFemale(Long indianFemale) {
		this.indianFemale = indianFemale;
	}

	public Long getIndianMale() {
		return indianMale;
	}

	public void setIndianMale(Long indianMale) {
		this.indianMale = indianMale;
	}

	public Long getWhiteFemale() {
		return whiteFemale;
	}

	public void setWhiteFemale(Long whiteFemale) {
		this.whiteFemale = whiteFemale;
	}

	public Long getWhiteMale() {
		return whiteMale;
	}

	public void setWhiteMale(Long whiteMale) {
		this.whiteMale = whiteMale;
	}

	public Long getAfricanFemaleDisability() {
		return africanFemaleDisability;
	}

	public void setAfricanFemaleDisability(Long africanFemaleDisability) {
		this.africanFemaleDisability = africanFemaleDisability;
	}

	public Long getAfricanMaleDisability() {
		return africanMaleDisability;
	}

	public void setAfricanMaleDisability(Long africanMaleDisability) {
		this.africanMaleDisability = africanMaleDisability;
	}

	public Long getColouredFemaleDisability() {
		return colouredFemaleDisability;
	}

	public void setColouredFemaleDisability(Long colouredFemaleDisability) {
		this.colouredFemaleDisability = colouredFemaleDisability;
	}

	public Long getColouredMaleDisability() {
		return colouredMaleDisability;
	}

	public void setColouredMaleDisability(Long colouredMaleDisability) {
		this.colouredMaleDisability = colouredMaleDisability;
	}

	public Long getIndianFemaleDisability() {
		return indianFemaleDisability;
	}

	public void setIndianFemaleDisability(Long indianFemaleDisability) {
		this.indianFemaleDisability = indianFemaleDisability;
	}

	public Long getIndianMaleDisability() {
		return indianMaleDisability;
	}

	public void setIndianMaleDisability(Long indianMaleDisability) {
		this.indianMaleDisability = indianMaleDisability;
	}

	public Long getWhiteFemaleDisability() {
		return whiteFemaleDisability;
	}

	public void setWhiteFemaleDisability(Long whiteFemaleDisability) {
		this.whiteFemaleDisability = whiteFemaleDisability;
	}

	public Long getWhiteMaleDisability() {
		return whiteMaleDisability;
	}

	public void setWhiteMaleDisability(Long whiteMaleDisability) {
		this.whiteMaleDisability = whiteMaleDisability;
	}

	public Long getNumberAge() {
		if (amount != null && amount > 0) {
			long total = amount;
			total -= (employeeAge18 != null) ? employeeAge18 : 0;
			total -= (employeeAge25 != null) ? employeeAge25 : 0;
			total -= (employeeAge35 != null) ? employeeAge35 : 0;
			total -= (employeeAge45 != null) ? employeeAge45 : 0;
			total -= (employeeAge55 != null) ? employeeAge55 : 0;
			total -= (employeeAge65 != null) ? employeeAge65 : 0;
			return total;
		}
		return 0l;
	}

	public Integer getNumberAgesTotal() {

		Long total = 0l;
		total += (employeeAge18 != null) ? employeeAge18 : 0;
		total += (employeeAge25 != null) ? employeeAge25 : 0;
		total += (employeeAge35 != null) ? employeeAge35 : 0;
		total += (employeeAge45 != null) ? employeeAge45 : 0;
		total += (employeeAge55 != null) ? employeeAge55 : 0;
		total += (employeeAge65 != null) ? employeeAge65 : 0;
		return total.intValue();

	}

	public void zeroAge() {
		employeeAge18 = null;
		employeeAge25 = null;
		employeeAge35 = null;
		employeeAge45 = null;
		employeeAge55 = null;
		employeeAge65 = null;
	}

	public Long getNumberGender() {
		if (amount != null && amount > 0) {
			long total = amount;
			total -= (whiteFemale != null) ? whiteFemale : 0;
			total -= (colouredFemale != null) ? colouredFemale : 0;
			total -= (africanFemale != null) ? africanFemale : 0;
			total -= (indianFemale != null) ? indianFemale : 0;
			total -= (whiteMale != null) ? whiteMale : 0;
			total -= (colouredMale != null) ? colouredMale : 0;
			total -= (africanMale != null) ? africanMale : 0;
			total -= (indianMale != null) ? indianMale : 0;
			return total;
		}
		return 0l;
	}

	public void zeroGenderFemale() {
		whiteFemale = null;
		colouredFemale = null;
		africanFemale = null;
		indianFemale = null;
		whiteMaleDisability = null;
		colouredMaleDisability = null;
		africanMaleDisability = null;
		indianMaleDisability = null;
	}

	public void zeroGendeMale() {
		whiteMale = null;
		colouredMale = null;
		africanMale = null;
		indianMale = null;
	}

	public void zeroGendeMaleAndFemale() {
		whiteMale = null;
		colouredMale = null;
		africanMale = null;
		indianMale = null;
		whiteFemale = null;
		colouredFemale = null;
		africanFemale = null;
		indianFemale = null;
	}

	public Long getNumberDisabled() {
		if (disabledAmount != null && disabledAmount > 0) {
			long total = disabledAmount;
			total -= (whiteFemaleDisability != null) ? whiteFemaleDisability : 0;
			total -= (colouredFemaleDisability != null) ? colouredFemaleDisability : 0;
			total -= (africanFemaleDisability != null) ? africanFemaleDisability : 0;
			total -= (indianFemaleDisability != null) ? indianFemaleDisability : 0;
			total -= (whiteMaleDisability != null) ? whiteMaleDisability : 0;
			total -= (colouredMaleDisability != null) ? colouredMaleDisability : 0;
			total -= (africanMaleDisability != null) ? africanMaleDisability : 0;
			total -= (indianMaleDisability != null) ? indianMaleDisability : 0;
			return total;
		}
		return 0l;
	}

	public void zeroDisabled() {
		whiteFemaleDisability = null;
		colouredFemaleDisability = null;
		africanFemaleDisability = null;
		indianFemaleDisability = null;
		whiteMaleDisability = null;
		colouredMaleDisability = null;
		africanMaleDisability = null;
		indianMaleDisability = null;
	}

	public WspReportEnum getWspReport() {
		return wspReport;
	}

	public void setWspReport(WspReportEnum wspReport) {
		this.wspReport = wspReport;
	}

	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public Municipality getMunicipality() {
		return municipality;
	}

	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Funding getFunding() {
		return funding;
	}

	public void setFunding(Funding funding) {
		this.funding = funding;
	}

	public YesNoLookup getNqfAligned() {
		return nqfAligned;
	}

	public void setNqfAligned(YesNoLookup nqfAligned) {
		this.nqfAligned = nqfAligned;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
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

	public Etqa getEtqa() {
		return etqa;
	}

	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

	public ProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
	}

	public Double getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(Double estimatedCost) {
		this.estimatedCost = estimatedCost;
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

	public int getDuration() {
		if (startDate == null || endDate == null) {
			return 0;
		}
		return GenericUtility.getDaysBetweenDatesExcludeWeekends(startDate, endDate);
	}

	public String getInterventionTitle() {
		return interventionTitle;
	}

	public void setInterventionTitle(String interventionTitle) {
		this.interventionTitle = interventionTitle;
	}

	public TrainingDeliveryMethod getTrainingDeliveryMethod() {
		return trainingDeliveryMethod;
	}

	public void setTrainingDeliveryMethod(TrainingDeliveryMethod trainingDeliveryMethod) {
		this.trainingDeliveryMethod = trainingDeliveryMethod;
	}

	public List<MandatoryGrantRecommendation> getGrantRecommendations() {
		return grantRecommendations;
	}

	public void setGrantRecommendations(List<MandatoryGrantRecommendation> grantRecommendations) {
		this.grantRecommendations = grantRecommendations;
	}

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public Integer getDisabledAmount() {
		return disabledAmount;
	}

	public void setDisabledAmount(Integer disabledAmount) {
		this.disabledAmount = disabledAmount;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getFundingCode() {
		return fundingCode;
	}

	public void setFundingCode(String fundingCode) {
		this.fundingCode = fundingCode;
	}

	public String getInterventionTypeCode() {
		return interventionTypeCode;
	}

	public void setInterventionTypeCode(String interventionTypeCode) {
		this.interventionTypeCode = interventionTypeCode;
	}

	public Integer getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(Integer qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public String getProviderTypeCode() {
		return providerTypeCode;
	}

	public void setProviderTypeCode(String providerTypeCode) {
		this.providerTypeCode = providerTypeCode;
	}

	// Added for JIRA #1026
	public DisabilityStatus getDisability() {
		return disability;
	}

	// Added for JIRA #1026
	public void setDisability(DisabilityStatus disability) {
		this.disability = disability;
	}

	public String getTrainingDeliveryMethodCode() {
		return trainingDeliveryMethodCode;
	}

	public void setTrainingDeliveryMethodCode(String trainingDeliveryMethodCode) {
		this.trainingDeliveryMethodCode = trainingDeliveryMethodCode;
	}

	public Integer getEmploymentStatusCode() {
		return employmentStatusCode;
	}

	public void setEmploymentStatusCode(Integer employmentStatusCode) {
		this.employmentStatusCode = employmentStatusCode;
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public boolean isImportError() {
		return importError;
	}

	public void setImportError(boolean importError) {
		this.importError = importError;
	}

	public String getImportErrors() {
		if (importErrors == null) importErrors = "";
		return importErrors;
	}

	public void setImportErrors(String importErrors) {
		this.importErrors = importErrors;
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

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public ApprovalEnum getDgVerificationStatus() {
		return dgVerificationStatus;
	}

	public void setDgVerificationStatus(ApprovalEnum dgVerificationStatus) {
		this.dgVerificationStatus = dgVerificationStatus;
	}

	public Integer getLastestRecommendLearnerAmount() {
		return lastestRecommendLearnerAmount;
	}

	public void setLastestRecommendLearnerAmount(Integer lastestRecommendLearnerAmount) {
		this.lastestRecommendLearnerAmount = lastestRecommendLearnerAmount;
	}

	public Double getEstimatedCostAppliedFor() {
		return estimatedCostAppliedFor;
	}

	public void setEstimatedCostAppliedFor(Double estimatedCostAppliedFor) {
		this.estimatedCostAppliedFor = estimatedCostAppliedFor;
	}

	public Double getEstimatedCostRecommenedCrm() {
		return estimatedCostRecommenedCrm;
	}

	public void setEstimatedCostRecommenedCrm(Double estimatedCostRecommenedCrm) {
		this.estimatedCostRecommenedCrm = estimatedCostRecommenedCrm;
	}

	public InterventionType getInterventionTypeReporting() {
		return interventionTypeReporting;
	}

	public void setInterventionTypeReporting(InterventionType interventionTypeReporting) {
		this.interventionTypeReporting = interventionTypeReporting;
	}

	public Qualification getQualificationReporting() {
		return qualificationReporting;
	}

	public void setQualificationReporting(Qualification qualificationReporting) {
		this.qualificationReporting = qualificationReporting;
	}

	public String getNqfLevelQualification() {
		return nqfLevelQualification;
	}

	public void setNqfLevelQualification(String nqfLevelQualification) {
		this.nqfLevelQualification = nqfLevelQualification;
	}

	public MandatoryGrantRecommendation getGrantRecommendation() {
		return grantRecommendation;
	}

	public void setGrantRecommendation(MandatoryGrantRecommendation grantRecommendation) {
		this.grantRecommendation = grantRecommendation;
	}

	/**
	 * @return the recinterventionType
	 */
	public InterventionType getRecinterventionType() {
		return recinterventionType;
	}

	/**
	 * @param recinterventionType
	 * the recinterventionType to set
	 */
	public void setRecinterventionType(InterventionType recinterventionType) {
		this.recinterventionType = recinterventionType;
	}

	public SkillsSet getSkillsSetReporting() {
		return skillsSetReporting;
	}

	public void setSkillsSetReporting(SkillsSet skillsSetReporting) {
		this.skillsSetReporting = skillsSetReporting;
	}

	public SkillsProgram getSkillsProgramReporting() {
		return skillsProgramReporting;
	}

	public void setSkillsProgramReporting(SkillsProgram skillsProgramReporting) {
		this.skillsProgramReporting = skillsProgramReporting;
	}

	public NonCreditBearingIntervationTitle getNonCreditBearingIntervationTitleReporting() {
		return nonCreditBearingIntervationTitleReporting;
	}

	public void setNonCreditBearingIntervationTitleReporting(NonCreditBearingIntervationTitle nonCreditBearingIntervationTitleReporting) {
		this.nonCreditBearingIntervationTitleReporting = nonCreditBearingIntervationTitleReporting;
	}

	public UnitStandards getUnitStandardsReporting() {
		return unitStandardsReporting;
	}

	public void setUnitStandardsReporting(UnitStandards unitStandardsReporting) {
		this.unitStandardsReporting = unitStandardsReporting;
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

	public List<MandatoryGrantWorkplace> getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(List<MandatoryGrantWorkplace> workplaces) {
		this.workplaces = workplaces;
	}

}
