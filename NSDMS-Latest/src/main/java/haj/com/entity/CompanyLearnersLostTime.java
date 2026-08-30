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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsers.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learners_lost_time")
@AuditTable(value = "company_learners_lost_time_hist")
@Audited
public class CompanyLearnersLostTime implements IDataEntity {

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
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;

	@Enumerated
	@Column(name = "lost_time_reason")
	private LostTimeReason lostTimeReason;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	/**The date the lost time started*/
	@Column(name = "lost_time_start_date", length = 19)
	private Date lostTimeStartDate ;
	
	/**The date the lost time ended */
	@Column(name = "lost_time_end_date", length = 19)
	private Date lostTimeEndDate ;
	
	/**The date the lost time ended */
	@Column(name = "new_completion_date", length = 19)
	private Date newCompletionDate;
	
	@Column(name = "first_approval_date", length = 19)
	private Date firstApprovalDate;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	/**The days Extended */
	@Column(name = "days_extended")
	private Integer daysExtended;
	
	/** User created lost time. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id",nullable = true)
	private Users createUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approval_user_id",nullable = true)
	private Users approvalUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	/* Designated Trade Info */
	@JoinColumn(name = "designated_trade_extension", nullable = true)
	private Boolean designatedTradeExtension;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designated_trade_id", nullable = true)
	private DesignatedTrade designatedTrade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designated_trade_level_id", nullable = true)
	private DesignatedTradeLevel designatedTradeLevel;
	
	@Column(name = "weeks_assigned")
	private Integer weeksAssigned;
	
	@Column(name = "orginal_end_date", length = 19)
	private Date orginalEndDate;
	
	@Column(name = "calculated_end_date", length = 19)
	private Date calculatedEndDate;
	
	@Enumerated
	@Column(name = "signoff_by_enum")
	private SignoffByEnum signoffByEnum;
	
	@Enumerated
	@Column(name = "created_by_enum")
	private CreatedByEnum createdByEnum;
	
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
		CompanyLearnersLostTime other = (CompanyLearnersLostTime) obj;
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

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public LostTimeReason getLostTimeReason() {
		return lostTimeReason;
	}

	public void setLostTimeReason(LostTimeReason lostTimeReason) {
		this.lostTimeReason = lostTimeReason;
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

	public Date getLostTimeStartDate() {
		return lostTimeStartDate;
	}

	public void setLostTimeStartDate(Date lostTimeStartDate) {
		this.lostTimeStartDate = lostTimeStartDate;
	}

	public Date getLostTimeEndDate() {
		return lostTimeEndDate;
	}

	public void setLostTimeEndDate(Date lostTimeEndDate) {
		this.lostTimeEndDate = lostTimeEndDate;
	}

	public Date getNewCompletionDate() {
		return newCompletionDate;
	}

	public void setNewCompletionDate(Date newCompletionDate) {
		this.newCompletionDate = newCompletionDate;
	}

	public Integer getDaysExtended() {
		return daysExtended;
	}

	public void setDaysExtended(Integer daysExtended) {
		this.daysExtended = daysExtended;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Date getFirstApprovalDate() {
		return firstApprovalDate;
	}

	public void setFirstApprovalDate(Date firstApprovalDate) {
		this.firstApprovalDate = firstApprovalDate;
	}

	public Users getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(Users approvalUser) {
		this.approvalUser = approvalUser;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public DesignatedTrade getDesignatedTrade() {
		return designatedTrade;
	}

	public void setDesignatedTrade(DesignatedTrade designatedTrade) {
		this.designatedTrade = designatedTrade;
	}

	public DesignatedTradeLevel getDesignatedTradeLevel() {
		return designatedTradeLevel;
	}

	public void setDesignatedTradeLevel(DesignatedTradeLevel designatedTradeLevel) {
		this.designatedTradeLevel = designatedTradeLevel;
	}

	public Integer getWeeksAssigned() {
		return weeksAssigned;
	}

	public void setWeeksAssigned(Integer weeksAssigned) {
		this.weeksAssigned = weeksAssigned;
	}

	public Date getOrginalEndDate() {
		return orginalEndDate;
	}

	public void setOrginalEndDate(Date orginalEndDate) {
		this.orginalEndDate = orginalEndDate;
	}

	public Date getCalculatedEndDate() {
		return calculatedEndDate;
	}

	public void setCalculatedEndDate(Date calculatedEndDate) {
		this.calculatedEndDate = calculatedEndDate;
	}

	public Boolean getDesignatedTradeExtension() {
		return designatedTradeExtension;
	}

	public void setDesignatedTradeExtension(Boolean designatedTradeExtension) {
		this.designatedTradeExtension = designatedTradeExtension;
	}

	public SignoffByEnum getSignoffByEnum() {
		return signoffByEnum;
	}

	public CreatedByEnum getCreatedByEnum() {
		return createdByEnum;
	}

	public void setSignoffByEnum(SignoffByEnum signoffByEnum) {
		this.signoffByEnum = signoffByEnum;
	}

	public void setCreatedByEnum(CreatedByEnum createdByEnum) {
		this.createdByEnum = createdByEnum;
	}
}
