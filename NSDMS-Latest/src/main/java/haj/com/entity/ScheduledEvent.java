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
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.ModerationEnum;
import haj.com.entity.enums.PercentageEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "scheduled_event")
@AuditTable(value = "scheduled_event_hist")
public class ScheduledEvent implements IDataEntity
{	
	

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
	
	/** title */
	@Column(name="title", columnDefinition="LONGTEXT") 
    private String title;
	
	/**Review from date time */
	@Column(name = "from_date_time", length = 19)
	private Date fromDateTime;
	
	@Column(name = "signoff_date", length = 19)
	private Date signOffDate;
	
	/**Review to date time */
	@Column(name = "to_date_time", length = 19)
	private Date toDateTime;
	
	/** The user. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Column(name = "company_moderation_done", columnDefinition = "BIT default false")
	private Boolean companyModerationDone;
	
	@Column(name = "learners_set_for_moderation", columnDefinition = "BIT default false")
	private Boolean learnerSetForModeration;
	
	@Enumerated
	@Column(name = "collection_type")
	private CollectionEnum collectionEnum;
	
	@Enumerated
	@Column(name = "percentage_enum")
	private PercentageEnum percentageEnum;
	
	/** title */
	@Column(name="recomendations", columnDefinition="LONGTEXT") 
    private String recomendations;
	/** title */
	@Column(name="concistency", columnDefinition="LONGTEXT") 
    private String concistency;
	/** title */
	@Column(name="support", columnDefinition="LONGTEXT") 
    private String support;
	/** title */
	@Column(name="internal_moderation", columnDefinition="LONGTEXT") 
    private String internalmoderation;
	/** title */
	@Column(name="appeals", columnDefinition="LONGTEXT") 
    private String appeals;
	
	@Enumerated
	@Column(name = "moderation_enum")
	private ModerationEnum moderationEnum;
	
	@Column(name="behavior", columnDefinition="LONGTEXT") 
    private String behavior;
	
	@Column(name="decision", columnDefinition="LONGTEXT") 
    private String decision;
	
	@Column(name="plan", columnDefinition="LONGTEXT") 
    private String plan;
	
	@Column(name="documentation", columnDefinition="LONGTEXT") 
    private String documentation;
	
	@Enumerated
	@Column(name = "qualification_type_selection_enum")
	QualificationTypeSelectionEnum qualificationTypeSelectionEnum;
	
	@Column(name = "collection_information")
	private String collectionInformation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_collection", length = 19)
	private Date dateOfCollection;
	
	public ScheduledEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScheduledEvent(Long id, Date createDate, String title, Date fromDateTime, Date toDateTime, Users user,
			ApprovalEnum status, List<Doc> docs, Long targetKey, String targetClass,
			ReviewCommitteeMeeting reviewCommitteeMeeting) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.title = title;
		this.fromDateTime = fromDateTime;
		this.toDateTime = toDateTime;
		this.user = user;
		this.status = status;
		this.docs = docs;
		this.targetKey = targetKey;
		this.targetClass = targetClass;
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}
	
	@Transient
	private List<Doc> docs;
	
	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	/** The ETQA Review Committee Schedule. */
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		ScheduledEvent other = (ScheduledEvent) obj;
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
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(Date fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public Date getToDateTime() {
		return toDateTime;
	}

	public void setToDateTime(Date toDateTime) {
		this.toDateTime = toDateTime;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
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

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Boolean getCompanyModerationDone() {
		return companyModerationDone;
	}

	public void setCompanyModerationDone(Boolean companyModerationDone) {
		this.companyModerationDone = companyModerationDone;
	}

	public CollectionEnum getCollectionEnum() {
		return collectionEnum;
	}

	public void setCollectionEnum(CollectionEnum collectionEnum) {
		this.collectionEnum = collectionEnum;
	}

	public PercentageEnum getPercentageEnum() {
		return percentageEnum;
	}

	public void setPercentageEnum(PercentageEnum percentageEnum) {
		this.percentageEnum = percentageEnum;
	}

	public String getRecomendations() {
		return recomendations;
	}

	public void setRecomendations(String recomendations) {
		this.recomendations = recomendations;
	}

	public String getConcistency() {
		return concistency;
	}

	public void setConcistency(String concistency) {
		this.concistency = concistency;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getInternalmoderation() {
		return internalmoderation;
	}

	public void setInternalmoderation(String internalmoderation) {
		this.internalmoderation = internalmoderation;
	}

	public String getAppeals() {
		return appeals;
	}

	public void setAppeals(String appeals) {
		this.appeals = appeals;
	}

	public ModerationEnum getModerationEnum() {
		return moderationEnum;
	}

	public void setModerationEnum(ModerationEnum moderationEnum) {
		this.moderationEnum = moderationEnum;
	}

	public String getBehavior() {
		return behavior;
	}

	public String getDecision() {
		return decision;
	}

	public String getPlan() {
		return plan;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public QualificationTypeSelectionEnum getQualificationTypeSelectionEnum() {
		return qualificationTypeSelectionEnum;
	}

	public void setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum qualificationTypeSelectionEnum) {
		this.qualificationTypeSelectionEnum = qualificationTypeSelectionEnum;
	}

	public String getCollectionInformation() {
		return collectionInformation;
	}

	public void setCollectionInformation(String collectionInformation) {
		this.collectionInformation = collectionInformation;
	}

	public Date getDateOfCollection() {
		return dateOfCollection;
	}

	public void setDateOfCollection(Date dateOfCollection) {
		this.dateOfCollection = dateOfCollection;
	}

	public Date getSignOffDate() {
		return signOffDate;
	}

	public void setSignOffDate(Date signOffDate) {
		this.signOffDate = signOffDate;
	}

	public Boolean getLearnerSetForModeration() {
		return learnerSetForModeration;
	}

	public void setLearnerSetForModeration(Boolean learnerSetForModeration) {
		this.learnerSetForModeration = learnerSetForModeration;
	}
}
