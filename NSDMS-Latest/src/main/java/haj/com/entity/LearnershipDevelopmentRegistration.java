package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.NewExistingLearnershipEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.ApplicantType;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.InterventionTitle;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsIdentification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "learnership_development_registration")
public class LearnershipDevelopmentRegistration implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of LearnershipDevelopmentRegistration. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "user_id", nullable = true)
	private Users users;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "chamber_id", nullable = true)
	private Chamber chamber;
	
	@Column(name = "guid", length = 100, nullable = true)
	@Size(min = 1, max = 100, message = "Guid can't be more than 100 characters")
	private String guid;
	
	
	/** Feilds for checklist info Start */
	@Column(name = "designation", columnDefinition = "LONGTEXT")
	private String designation;
	
	@Column(name = "department", columnDefinition = "LONGTEXT")
	private String department;
	
	@Column(name = "learner_number", columnDefinition = "LONGTEXT")
	private String learnerNumber;	
	
	@Column(name = "professional_body_name ", columnDefinition = "LONGTEXT")
	private String professionalBodyName;
	
	@Column(name = "industry_body_or_representative_body_name", columnDefinition = "LONGTEXT")
	private String industryBodyOrRepresentativeBodyName ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "applicant_type", nullable = true)
	private ApplicantType applicantType;
	
	@Enumerated()
	@Column(name = "new_learnership")
	private NewExistingLearnershipEnum newLearnership;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_learnership_id", nullable = true)
	private LearnershipDevelopmentRegistration previousLearnership;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "skills_identification_id", nullable = true)
	private SkillsIdentification skillsIdentification;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionType interventionType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification qualification;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsSet skillsSet;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandard;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;
	
	@Enumerated
	@Column(name = "pivot_non_pivot")
	private PivotNonPivotEnum pivotNonPivot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_aligned_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private YesNoLookup nqfAligned;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "Intervention_title_id", nullable = true)
	private InterventionTitle interventionTitle;
	
	/** The code. */
	@Length(max = 20)
	@Column(name = "registration_number", length = 20)
	private String registrationNumber;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;
	
	/** Fields for checklist info End */

	@Enumerated()
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Transient
	private List<Doc> docs;

	
	
	public LearnershipDevelopmentRegistration() {
		super();
		this.guid = UUID.randomUUID().toString();
	}

	public LearnershipDevelopmentRegistration(Company company, Users users) {
		super();
		this.company = company;
		this.users = users;
		this.guid = UUID.randomUUID().toString();
		this.approvalEnum = ApprovalEnum.WaitingForManager;
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
		LearnershipDevelopmentRegistration other = (LearnershipDevelopmentRegistration) obj;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public NewExistingLearnershipEnum getNewLearnership() {
		return newLearnership;
	}

	public void setNewLearnership(NewExistingLearnershipEnum newLearnership) {
		this.newLearnership = newLearnership;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LearnershipDevelopmentRegistration getPreviousLearnership() {
		return previousLearnership;
	}

	public void setPreviousLearnership(LearnershipDevelopmentRegistration previousLearnership) {
		this.previousLearnership = previousLearnership;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public SkillsIdentification getSkillsIdentification() {
		return skillsIdentification;
	}

	public void setSkillsIdentification(SkillsIdentification skillsIdentification) {
		this.skillsIdentification = skillsIdentification;
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

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
	}

	public YesNoLookup getNqfAligned() {
		return nqfAligned;
	}

	public void setNqfAligned(YesNoLookup nqfAligned) {
		this.nqfAligned = nqfAligned;
	}

	public InterventionTitle getInterventionTitle() {
		return interventionTitle;
	}

	public void setInterventionTitle(InterventionTitle interventionTitle) {
		this.interventionTitle = interventionTitle;
	}

	public ApplicantType getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(ApplicantType applicantType) {
		this.applicantType = applicantType;
	}

	public String getProfessionalBodyName() {
		return professionalBodyName;
	}

	public void setProfessionalBodyName(String professionalBodyName) {
		this.professionalBodyName = professionalBodyName;
	}

	public String getIndustryBodyOrRepresentativeBodyName() {
		return industryBodyOrRepresentativeBodyName;
	}

	public void setIndustryBodyOrRepresentativeBodyName(String industryBodyOrRepresentativeBodyName) {
		this.industryBodyOrRepresentativeBodyName = industryBodyOrRepresentativeBodyName;
	}

	public Chamber getChamber() {
		return chamber;
	}

	public void setChamber(Chamber chamber) {
		this.chamber = chamber;
	}

	public String getLearnerNumber() {
		return learnerNumber;
	}

	public void setLearnerNumber(String learnerNumber) {
		this.learnerNumber = learnerNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}
	
}
