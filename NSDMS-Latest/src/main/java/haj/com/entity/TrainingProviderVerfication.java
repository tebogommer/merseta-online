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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CeritificateCollectionEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsers.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "training_provider_verfication")
@AuditTable(value = "training_provider_verfication_hist")
@Audited
public class TrainingProviderVerfication implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_id", nullable = true)
	private Company trainingProvider;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Enumerated
	@Column(name = "verification_status")
	private ApprovalEnum verificationStatus;
	
	//@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	@Enumerated
	@Column(name = "collection_type")
	private CollectionEnum collectionEnum;
	
	@Column(name = "date_for_visit_provided")
	private Boolean dateForVisitProvided;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "review_date", length = 19)
	private Date reviewDate;
	
	/** The form user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_user_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Users reviewUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_provider_verfication_parent_id", nullable = true)
	private TrainingProviderVerfication trainingProviderVerficationParent;
	
	@Column(name = "for_moderation", columnDefinition = "BIT default false")
	private Boolean forModeration;
	
	@Column(name = "assessment_done", columnDefinition = "BIT default false")
	private Boolean assessmentDone;
	
	@Column(name = "learner_moderation_done", columnDefinition = "BIT default false")
	private Boolean learnerModerationDone;
	
	@Column(name = "company_moderation_done", columnDefinition = "BIT default false")
	private Boolean companyModerationDone;
	
	@Column(name = "downloads", columnDefinition = "integer default 0")
    private Integer downloads;	
	
	@Column(name = "certificate_number", length = 30, nullable = true)
	private String certificateNumber;
	
	@Column(name = "serial_number", length = 30, nullable = true)
	private String serialNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cetificate_generated_date", length = 19)
	private Date cetificateGeneratedDate;
	
	@Column(name = "cetificate_generated", columnDefinition = "BIT default false")
	private Boolean cetificateGenerated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "collection_date", length = 19)
	private Date collectionDate;

	@Enumerated
	@Column(name = "ceritificate_collection_enum")
	private CeritificateCollectionEnum ceritificateCollectionEnum;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scheduled_event_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private ScheduledEvent scheduledEvent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "moderation_date", length = 19)
	private Date moderationDate;
	
	@Column(name = "created_by_merseta", columnDefinition = "BIT default false")
	private Boolean createdByMerseta;
	
	@Enumerated
	@Column(name = "generate_add_enum")
	private GenerateAddEnum generateAddEnum;
	
	@Column(name = "created_final_approval", columnDefinition = "BIT default false")
	private Boolean createdFinalApproval;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private Doc doc;
	
	@Transient
	private Doc documents;
	
	@Transient
	List<AuditorMonitorReview> auditorMonitorReviews;

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
		TrainingProviderVerfication other = (TrainingProviderVerfication) obj;
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

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
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

	public Company getTrainingProvider() {
		return trainingProvider;
	}

	public void setTrainingProvider(Company trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public CollectionEnum getCollectionEnum() {
		return collectionEnum;
	}

	public void setCollectionEnum(CollectionEnum collectionEnum) {
		this.collectionEnum = collectionEnum;
	}

	public Boolean getDateForVisitProvided() {
		return dateForVisitProvided;
	}

	public void setDateForVisitProvided(Boolean dateForVisitProvided) {
		this.dateForVisitProvided = dateForVisitProvided;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Users getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(Users reviewUser) {
		this.reviewUser = reviewUser;
	}

	public TrainingProviderVerfication getTrainingProviderVerficationParent() {
		return trainingProviderVerficationParent;
	}

	public void setTrainingProviderVerficationParent(TrainingProviderVerfication trainingProviderVerficationParent) {
		this.trainingProviderVerficationParent = trainingProviderVerficationParent;
	}

	public Boolean getForModeration() {
		return forModeration;
	}

	public void setForModeration(Boolean forModeration) {
		this.forModeration = forModeration;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Boolean getAssessmentDone() {
		return assessmentDone;
	}

	public void setAssessmentDone(Boolean assessmentDone) {
		this.assessmentDone = assessmentDone;
	}

	public Boolean getLearnerModerationDone() {
		return learnerModerationDone;
	}

	public void setLearnerModerationDone(Boolean learnerModerationDone) {
		this.learnerModerationDone = learnerModerationDone;
	}

	public Boolean getCompanyModerationDone() {
		return companyModerationDone;
	}

	public void setCompanyModerationDone(Boolean companyModerationDone) {
		this.companyModerationDone = companyModerationDone;
	}

	public Doc getDocuments() {
		return documents;
	}

	public void setDocuments(Doc documents) {
		this.documents = documents;
	}

	public Integer getDownloads() {
		return downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public Date getCetificateGeneratedDate() {
		return cetificateGeneratedDate;
	}

	public void setCetificateGeneratedDate(Date cetificateGeneratedDate) {
		this.cetificateGeneratedDate = cetificateGeneratedDate;
	}

	public Boolean getCetificateGenerated() {
		return cetificateGenerated;
	}

	public void setCetificateGenerated(Boolean cetificateGenerated) {
		this.cetificateGenerated = cetificateGenerated;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public CeritificateCollectionEnum getCeritificateCollectionEnum() {
		return ceritificateCollectionEnum;
	}

	public void setCeritificateCollectionEnum(CeritificateCollectionEnum ceritificateCollectionEnum) {
		this.ceritificateCollectionEnum = ceritificateCollectionEnum;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public Date getModerationDate() {
		return moderationDate;
	}

	public void setModerationDate(Date moderationDate) {
		this.moderationDate = moderationDate;
	}

	public ApprovalEnum getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(ApprovalEnum verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public Boolean getCreatedByMerseta() {
		return createdByMerseta;
	}

	public void setCreatedByMerseta(Boolean createdByMerseta) {
		this.createdByMerseta = createdByMerseta;
	}

	public GenerateAddEnum getGenerateAddEnum() {
		return generateAddEnum;
	}

	public void setGenerateAddEnum(GenerateAddEnum generateAddEnum) {
		this.generateAddEnum = generateAddEnum;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviews() {
		return auditorMonitorReviews;
	}

	public void setAuditorMonitorReviews(List<AuditorMonitorReview> auditorMonitorReviews) {
		this.auditorMonitorReviews = auditorMonitorReviews;
	}

	public Boolean getCreatedFinalApproval() {
		return createdFinalApproval;
	}

	public void setCreatedFinalApproval(Boolean createdFinalApproval) {
		this.createdFinalApproval = createdFinalApproval;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}		
}
