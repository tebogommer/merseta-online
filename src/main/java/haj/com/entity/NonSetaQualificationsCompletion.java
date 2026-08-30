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
import haj.com.entity.enums.CompetenceEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "non_seta_qualifications_completion")
public class NonSetaQualificationsCompletion implements IDataEntity {

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
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_id", nullable = true)
	private Company trainingProvider;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "summative_assessment_report_id", nullable = true)
	private SummativeAssessmentReport SummativeAssessmentReport;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "moderation_date", length = 19)
	private Date moderationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "assessment_date", length = 19)
	private Date assessmentDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "achievement_date", length = 19)
	private Date achievementDate;
	
	@Enumerated
	@Column(name = "competence_enum")
	private CompetenceEnum competenceEnum;

	@Transient
	private List<Doc> docs;

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
		NonSetaQualificationsCompletion other = (NonSetaQualificationsCompletion) obj;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getTrainingProvider() {
		return trainingProvider;
	}

	public void setTrainingProvider(Company trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public SummativeAssessmentReport getSummativeAssessmentReport() {
		return SummativeAssessmentReport;
	}

	public void setSummativeAssessmentReport(SummativeAssessmentReport summativeAssessmentReport) {
		SummativeAssessmentReport = summativeAssessmentReport;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Date getModerationDate() {
		return moderationDate;
	}

	public void setModerationDate(Date moderationDate) {
		this.moderationDate = moderationDate;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public Date getAchievementDate() {
		return achievementDate;
	}

	public void setAchievementDate(Date achievementDate) {
		this.achievementDate = achievementDate;
	}

	public CompetenceEnum getCompetenceEnum() {
		return competenceEnum;
	}

	public void setCompetenceEnum(CompetenceEnum competenceEnum) {
		this.competenceEnum = competenceEnum;
	}
}
