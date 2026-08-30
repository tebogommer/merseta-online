
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.RsaCitizenTypeEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.EmploymentType;
import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Nationality;
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

@Entity
@Table(name = "mandatory_grant_detail", uniqueConstraints = @UniqueConstraint(columnNames = { "id_number", "ofo_code_id", "intervention_type_id", "wsp_report", "skills_set_id", "skills_program_id ", "qualification_id", "unit_standard_id" }))
public class MandatoryGrantDetail implements IDataEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The first name. */
	@Column(name = "first_name", length = 100, nullable = true)
	@CSVAnnotation(name = "first_name", className = String.class)
	private String firstName;

	/** The last name. */
	@Column(name = "last_name", length = 100, nullable = true)
	@CSVAnnotation(name = "last_name", className = String.class)
	private String lastName;

	@Column(name = "id_type")
	@Enumerated
	private IdPassportEnum idType;

	@Column(name = "id_type_code")
	@CSVAnnotation(name = "id_type", className = Integer.class)
	private Integer idTypeCode;

	/** The rsa ID number. */
	@Column(name = "id_number", length = 13, nullable = true)
	@CSVAnnotation(name = "id_number", className = String.class)
	private String idNumber;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Wsp wsp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_code_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private OfoCodes ofoCodes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipality_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Municipality municipality;
	
	@Column(name = "municipality_code")
	@CSVAnnotation(name = "municipality_code", className = String.class)
	private String municipalityCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Training training;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funding_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Funding funding;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionType interventionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_aligned_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private YesNoLookup nqfAligned;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification qualification;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_levels_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqfLevels;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionLevel interventionLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etqa_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Etqa etqa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private ProviderType providerType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_delivery_method_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private TrainingDeliveryMethod trainingDeliveryMethod;

	@Column(name = "intervention_title", columnDefinition = "LONGTEXT")
	@CSVAnnotation(name = "intervention_title", className = String.class)
	private String interventionTitle;

	@Column(name = "estimated_cost")
	@CSVAnnotation(name = "estimated_cost", className = Double.class)
	private Double estimatedCost;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	@CSVAnnotation(name = "start_date", className = Date.class, datePattern = "dd-MM-yyyy")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	@CSVAnnotation(name = "end_date", className = Date.class, datePattern = "dd-MM-yyyy")
	private Date endDate;

	@Column(name = "date_of_birth", nullable = true)
	@CSVAnnotation(name = "date_of_birth", className = Date.class, datePattern = "dd-MM-yyyy")
	private Date dateOfBirth;

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

	@Column(name = "specialisation_code", length = 16)
	@CSVAnnotation(name = "specialisation_code", className = String.class)
	private String specialisationCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Gender gender;

	@Column(name = "gender_code", length = 11)
	@CSVAnnotation(name = "gender_code", className = String.class)
	private String genderCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equity_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Equity equity;

	@Column(name = "equity_code", length = 11)
	@CSVAnnotation(name = "equity_code", className = String.class)
	private String equityCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationality_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Nationality nationality;

	@Column(name = "nationality_code", length = 11)
	@CSVAnnotation(name = "nationality_code", className = String.class)
	private String nationalityCode;

	@Column(name = "enrolment_status_code", length = 11)
	@CSVAnnotation(name = "enrolment_status_code", className = String.class)
	private String enrolmentStatusCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enrolment_status", nullable = true)
	@Fetch(FetchMode.JOIN)
	private EnrolmentStatus enrolmentStatus;

	@Column(name = "funding_code", length = 10)
	@CSVAnnotation(name = "funding_code", className = String.class)
	private String fundingCode;

	@Column(name = "intervention_type_code", length = 10)
	@CSVAnnotation(name = "intervention_type_code", className = String.class)
	private String interventionTypeCode;

	@Column(name = "qualification_code")
	@CSVAnnotation(name = "qualification_code", className = Integer.class)
	private Integer qualificationCode;

	@Column(name = "provider_type_code", length = 10)
	@CSVAnnotation(name = "provider_type_code", className = String.class)
	private String providerTypeCode;

	@Column(name = "training_delivery_method_code", length = 10)
	@CSVAnnotation(name = "training_delivery_method_code", className = String.class)
	private String trainingDeliveryMethodCode;

	/** The pivot non pivot. */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employment_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	// @Column(name = "employment_status")
	private EmploymentType employmentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disability_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private DisabilityStatus disability;

	@Column(name = "disability", length = 100)
	@CSVAnnotation(name = "disability", className = String.class)
	private String disabilityCode;

	@Enumerated
	@Column(name = "employment_status")
	private EmploymentStatusEnum employmentStatus;

	@Column(name = "employment_type_code")
	@CSVAnnotation(name = "employment_type_code", className = String.class)
	private String employmentTypeCode;

	@Column(name = "skills_program_code")
	@CSVAnnotation(name = "skills_program_code", className = String.class)
	private String skillsProgramCode;

	@Column(name = "skills_set_code")
	@CSVAnnotation(name = "skills_set_code", className = String.class)
	private String skillsSetCode;

	@Column(name = "imported", columnDefinition = "BIT default true")
	private boolean imported;

	@Transient
	private List<MandatoryGrantRecommendation> grantRecommendations;

	@Transient
	private WorkPlaceApproval workPlaceApproval;

	@Column(name = "import_error", columnDefinition = "BIT default false")
	private boolean importError;

	@Column(name = "import_errors", columnDefinition = "LONGTEXT")
	private String importErrors;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "non_credit_bearing_intervation_title_id", nullable = true)
    @Fetch(FetchMode.JOIN)
    private NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle;
	
	@Column(name = "non_credit_bearing_intervation_title_code")
	@CSVAnnotation(name = "non_credit_bearing_intervation_title_code", className = String.class)
	private String nonCreditBearingIntervationTitleCode;
	
	@Enumerated
	@Column(name = "rsa_citizen_type")
	private RsaCitizenTypeEnum rsaCitizenTypeEnum;

	public MandatoryGrantDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MandatoryGrantDetail(Wsp wsp, WspReportEnum wspReport) {
		super();
		this.wsp = wsp;
		this.wspReport = wspReport;
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
		MandatoryGrantDetail other = (MandatoryGrantDetail) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the pivot non pivot.
	 *
	 * @return the pivot non pivot
	 */
	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	/**
	 * Sets the pivot non pivot.
	 *
	 * @param pivotNonPivot
	 *            the new pivot non pivot
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

	public WspReportEnum getWspReport() {
		return wspReport;
	}

	public void setWspReport(WspReportEnum wspReport) {
		this.wspReport = wspReport;
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

	public String getTrainingDeliveryMethodCode() {
		return trainingDeliveryMethodCode;
	}

	public void setTrainingDeliveryMethodCode(String trainingDeliveryMethodCode) {
		this.trainingDeliveryMethodCode = trainingDeliveryMethodCode;
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

	public String getErrorS() {
		return importErrors.replaceAll("<li>", "").replaceAll("</li>", ",");
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public IdPassportEnum getIdType() {
		return idType;
	}

	public void setIdType(IdPassportEnum idType) {
		this.idType = idType;
	}

	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getEmploymentTypeCode() {
		return employmentTypeCode;
	}

	public void setEmploymentTypeCode(String employmentTypeCode) {
		this.employmentTypeCode = employmentTypeCode;
	}

	public String getSkillsProgramCode() {
		return skillsProgramCode;
	}

	public void setSkillsProgramCode(String skillsProgramCode) {
		this.skillsProgramCode = skillsProgramCode;
	}

	public String getSkillsSetCode() {
		return skillsSetCode;
	}

	public void setSkillsSetCode(String skillsSetCode) {
		this.skillsSetCode = skillsSetCode;
	}

	public String getDisabilityCode() {
		return disabilityCode;
	}

	public void setDisabilityCode(String disabilityCode) {
		this.disabilityCode = disabilityCode;
	}

	public DisabilityStatus getDisability() {
		return disability;
	}

	public void setDisability(DisabilityStatus disability) {
		this.disability = disability;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getIdTypeCode() {
		return idTypeCode;
	}

	public void setIdTypeCode(Integer idTypeCode) {
		this.idTypeCode = idTypeCode;
	}

	// New
	public String getSpecialisationCode() {
		return specialisationCode;
	}

	public void setSpecialisationCode(String specialisationCode) {
		this.specialisationCode = specialisationCode;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getEquityCode() {
		return equityCode;
	}

	public void setEquityCode(String equityCode) {
		this.equityCode = equityCode;
	}

	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public String getEnrolmentStatusCode() {
		return enrolmentStatusCode;
	}

	public void setEnrolmentStatusCode(String enrolmentStatusCode) {
		this.enrolmentStatusCode = enrolmentStatusCode;
	}

	public EnrolmentStatus getEnrolmentStatus() {
		return enrolmentStatus;
	}

	public void setEnrolmentStatus(EnrolmentStatus enrolmentStatus) {
		this.enrolmentStatus = enrolmentStatus;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Equity getEquity() {
		return equity;
	}

	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public NonCreditBearingIntervationTitle getNonCreditBearingIntervationTitle() {
		return nonCreditBearingIntervationTitle;
	}

	public void setNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle) {
		this.nonCreditBearingIntervationTitle = nonCreditBearingIntervationTitle;
	}

	public RsaCitizenTypeEnum getRsaCitizenTypeEnum() {
		return rsaCitizenTypeEnum;
	}

	public void setRsaCitizenTypeEnum(RsaCitizenTypeEnum rsaCitizenTypeEnum) {
		this.rsaCitizenTypeEnum = rsaCitizenTypeEnum;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getNonCreditBearingIntervationTitleCode() {
		return nonCreditBearingIntervationTitleCode;
	}

	public void setNonCreditBearingIntervationTitleCode(String nonCreditBearingIntervationTitleCode) {
		this.nonCreditBearingIntervationTitleCode = nonCreditBearingIntervationTitleCode;
	}

}
