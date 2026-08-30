package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Etqa;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;

@Entity
@Table(name = "inter_seta_transfer")
@ExportValidation(message = "Invalid Inter Seta Transfer Profile")
public class InterSetaTransfer implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "action_date", length = 19)
	private Date actionDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_etqa_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Etqa currentEtqa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "new_etqa_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Etqa newEtqa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	@SETMISFieldValidation(process = true, fieldName = "users", fieldValue = "NOT_NULL")
	private Users users;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;

	@Column(name = "scoped_within_seta", columnDefinition = "BIT default false")
	private Boolean scopedWithinSETA;

	@Column(name = "core_business_changed", columnDefinition = "BIT default false")
	private Boolean coreBusinessChanged;

	@Column(name = "more_than_one_seta", columnDefinition = "BIT default false")
	private Boolean moreThanOneSETA;

	@Column(name = "additional_comment", length = 500)
	private String additionalComment;

	@Column(name = "guid", length = 500)
	private String guid;

	@Column(name = "approval_status")
	private ApprovalEnum approval;

	public InterSetaTransfer() {
		super();
		this.guid = UUID.randomUUID().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterSetaTransfer other = (InterSetaTransfer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Etqa getCurrentEtqa() {
		return currentEtqa;
	}

	public void setCurrentEtqa(Etqa currentEtqa) {
		this.currentEtqa = currentEtqa;
	}

	public Etqa getNewEtqa() {
		return newEtqa;
	}

	public void setNewEtqa(Etqa newEtqa) {
		this.newEtqa = newEtqa;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getScopedWithinSETA() {
		return scopedWithinSETA;
	}

	public void setScopedWithinSETA(Boolean scopedWithinSETA) {
		this.scopedWithinSETA = scopedWithinSETA;
	}

	public Boolean getCoreBusinessChanged() {
		return coreBusinessChanged;
	}

	public void setCoreBusinessChanged(Boolean coreBusinessChanged) {
		this.coreBusinessChanged = coreBusinessChanged;
	}

	public Boolean getMoreThanOneSETA() {
		return moreThanOneSETA;
	}

	public void setMoreThanOneSETA(Boolean moreThanOneSETA) {
		this.moreThanOneSETA = moreThanOneSETA;
	}

	public String getAdditionalComment() {
		return additionalComment;
	}

	public void setAdditionalComment(String additionalComment) {
		this.additionalComment = additionalComment;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public ApprovalEnum getApproval() {
		return approval;
	}

	public void setApproval(ApprovalEnum approval) {
		this.approval = approval;
	}

}
