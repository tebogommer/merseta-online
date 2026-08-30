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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.QCDTemplateTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qualifications_curriculum_development")
public class QualificationsCurriculumDevelopment implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of BankingDetails. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "new_development_1", columnDefinition="LONGTEXT")
	private String newDevelopment1;
	
	@Column(name = "new_development_2", columnDefinition="LONGTEXT")
	private String newDevelopment2;

	@Column(name = "new_development_3", columnDefinition="LONGTEXT")
	private String newDevelopment3;
	
	@Column(name = "re_alignment_qualification_id_list", columnDefinition="LONGTEXT")
	private String reAlignmentQualificationIdList;
	
	@Column(name = "review_qualification_id_list", columnDefinition="LONGTEXT")
	private String reviewQualificationIdList;
	
	@Column(name = "ndp_checked")
	private Boolean nationalDevelopmentPlanChecked;
	
	@Column(name = "ndp_checked_evidence", columnDefinition="LONGTEXT")
	private String nationalDevelopmentPlanCheckedEvidence;
	
	@Column(name = "ngp_checked")
	private Boolean newGrowthPlanChecked;
	
	@Column(name = "ngp_checked_evidence")
	private String newGrowthPlanCheckedEvidence;
	
	@Column(name = "ipap_checked")
	private Boolean industrialPolicyActionPlanChecked;
	
	@Column(name = "ipap_checked_evidence")
	private String industrialPolicyActionPlanCheckedEvidence;
	
	@Column(name = "edisip_checked")
	private Boolean economicDriversInStrategicInfrastructureProjectsChecked;	

	@Column(name = "edisip_checked_evidence")
	private String economicDriversInStrategicInfrastructureProjectsCheckedEvidence;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Column(name = "purpose_qualification", columnDefinition="LONGTEXT")
	private String purposeQualification;
	
	@Column(name = "role_qualification", columnDefinition="LONGTEXT")
	private String roleQualification;
	
	@Column(name = "primary_service_occupation", columnDefinition="LONGTEXT")
	private String primaryServiceForOccupation;
	
	@Column(name = "users_service", columnDefinition="LONGTEXT")
	private String usersOfService;
	
	@Column(name = "demand", columnDefinition="LONGTEXT")
	private String demand;
	
	@Column(name = "current_training", columnDefinition="LONGTEXT")
	private String currentTraining;
	
	@Column(name = "key_stakeholders", columnDefinition="LONGTEXT")
	private String keyStakeholders;
	
	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submit_date", length = 19)
	private Date submitDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "upload_date", length = 19)
	private Date uploadDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user", nullable = true)
	private Users createUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submit_user", nullable = true)
	private Users submitUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "upload_user", nullable = true)
	private Users uploadUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approve_user", nullable = true)
	private Users approveUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<Doc> oldDocs;
	
	@Transient
	private List<Qualification> reAlignmentQualificationList;

	@Transient
	private List<Qualification> reviewQualificationList;
	
	@Enumerated
	@Column(name = "template_type_enum", length = 100)
	private QCDTemplateTypeEnum templateType;
	
	@Transient
	private  Qualification qualification;
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
		QualificationsCurriculumDevelopment other = (QualificationsCurriculumDevelopment) obj;
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

	public String getNewDevelopment1() {
		return newDevelopment1;
	}

	public void setNewDevelopment1(String newDevelopment1) {
		this.newDevelopment1 = newDevelopment1;
	}

	public String getNewDevelopment2() {
		return newDevelopment2;
	}

	public void setNewDevelopment2(String newDevelopment2) {
		this.newDevelopment2 = newDevelopment2;
	}

	public String getNewDevelopment3() {
		return newDevelopment3;
	}

	public void setNewDevelopment3(String newDevelopment3) {
		this.newDevelopment3 = newDevelopment3;
	}

	public String getReAlignmentQualificationIdList() {
		return reAlignmentQualificationIdList;
	}

	public void setReAlignmentQualificationIdList(String reAlignmentQualificationIdList) {
		this.reAlignmentQualificationIdList = reAlignmentQualificationIdList;
	}

	public String getReviewQualificationIdList() {
		return reviewQualificationIdList;
	}

	public void setReviewQualificationIdList(String reviewQualificationIdList) {
		this.reviewQualificationIdList = reviewQualificationIdList;
	}

	public Boolean getNationalDevelopmentPlanChecked() {
		return nationalDevelopmentPlanChecked;
	}

	public void setNationalDevelopmentPlanChecked(Boolean nationalDevelopmentPlanChecked) {
		this.nationalDevelopmentPlanChecked = nationalDevelopmentPlanChecked;
	}

	public Boolean getNewGrowthPlanChecked() {
		return newGrowthPlanChecked;
	}

	public void setNewGrowthPlanChecked(Boolean newGrowthPlanChecked) {
		this.newGrowthPlanChecked = newGrowthPlanChecked;
	}

	public Boolean getIndustrialPolicyActionPlanChecked() {
		return industrialPolicyActionPlanChecked;
	}

	public void setIndustrialPolicyActionPlanChecked(Boolean industrialPolicyActionPlanChecked) {
		this.industrialPolicyActionPlanChecked = industrialPolicyActionPlanChecked;
	}

	public Boolean getEconomicDriversInStrategicInfrastructureProjectsChecked() {
		return economicDriversInStrategicInfrastructureProjectsChecked;
	}

	public void setEconomicDriversInStrategicInfrastructureProjectsChecked(
			Boolean economicDriversInStrategicInfrastructureProjectsChecked) {
		this.economicDriversInStrategicInfrastructureProjectsChecked = economicDriversInStrategicInfrastructureProjectsChecked;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public String getPurposeQualification() {
		return purposeQualification;
	}

	public void setPurposeQualification(String purposeQualification) {
		this.purposeQualification = purposeQualification;
	}

	public String getRoleQualification() {
		return roleQualification;
	}

	public void setRoleQualification(String roleQualification) {
		this.roleQualification = roleQualification;
	}

	public String getPrimaryServiceForOccupation() {
		return primaryServiceForOccupation;
	}

	public void setPrimaryServiceForOccupation(String primaryServiceForOccupation) {
		this.primaryServiceForOccupation = primaryServiceForOccupation;
	}

	public String getUsersOfService() {
		return usersOfService;
	}

	public void setUsersOfService(String usersOfService) {
		this.usersOfService = usersOfService;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getCurrentTraining() {
		return currentTraining;
	}

	public void setCurrentTraining(String currentTraining) {
		this.currentTraining = currentTraining;
	}

	public String getKeyStakeholders() {
		return keyStakeholders;
	}

	public void setKeyStakeholders(String keyStakeholders) {
		this.keyStakeholders = keyStakeholders;
	}
	
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public List<Doc> getOldDocs() {
		return oldDocs;
	}

	public void setOldDocs(List<Doc> oldDocs) {
		this.oldDocs = oldDocs;
	}

	public List<Qualification> getReAlignmentQualificationList() {
		return reAlignmentQualificationList;
	}

	public void setReAlignmentQualificationList(List<Qualification> reAlignmentQualificationList) {
		this.reAlignmentQualificationList = reAlignmentQualificationList;
	}

	public List<Qualification> getReviewQualificationList() {
		return reviewQualificationList;
	}

	public void setReviewQualificationList(List<Qualification> reviewQualificationList) {
		this.reviewQualificationList = reviewQualificationList;
	}

	public String getNewGrowthPlanCheckedEvidence() {
		return newGrowthPlanCheckedEvidence;
	}

	public void setNewGrowthPlanCheckedEvidence(String newGrowthPlanCheckedEvidence) {
		this.newGrowthPlanCheckedEvidence = newGrowthPlanCheckedEvidence;
	}

	public String getIndustrialPolicyActionPlanCheckedEvidence() {
		return industrialPolicyActionPlanCheckedEvidence;
	}

	public void setIndustrialPolicyActionPlanCheckedEvidence(String industrialPolicyActionPlanCheckedEvidence) {
		this.industrialPolicyActionPlanCheckedEvidence = industrialPolicyActionPlanCheckedEvidence;
	}

	public String getEconomicDriversInStrategicInfrastructureProjectsCheckedEvidence() {
		return economicDriversInStrategicInfrastructureProjectsCheckedEvidence;
	}

	public void setEconomicDriversInStrategicInfrastructureProjectsCheckedEvidence(
			String economicDriversInStrategicInfrastructureProjectsCheckedEvidence) {
		this.economicDriversInStrategicInfrastructureProjectsCheckedEvidence = economicDriversInStrategicInfrastructureProjectsCheckedEvidence;
	}
	
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public void setNationalDevelopmentPlanCheckedEvidence(String nationalDevelopmentPlanCheckedEvidence) {
		this.nationalDevelopmentPlanCheckedEvidence = nationalDevelopmentPlanCheckedEvidence;
	}

	public String getNationalDevelopmentPlanCheckedEvidence() {
		return nationalDevelopmentPlanCheckedEvidence;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Users getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(Users submitUser) {
		this.submitUser = submitUser;
	}

	public Users getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(Users uploadUser) {
		this.uploadUser = uploadUser;
	}

	public Users getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public QCDTemplateTypeEnum getTemplateType() {
		return templateType;
	}

	public void setTemplateType(QCDTemplateTypeEnum templateType) {
		this.templateType = templateType;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	
	
}
