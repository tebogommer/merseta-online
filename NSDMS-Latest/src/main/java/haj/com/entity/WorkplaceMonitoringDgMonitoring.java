package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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
import haj.com.entity.enums.ContractStatusEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "workplace_monitoring_dg_monitoring")
@AuditTable(value = "workplace_monitoring_dg_monitoring_hist")
@Audited
public class WorkplaceMonitoringDgMonitoring implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user", nullable = true)
	private Users lastActionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;

	@Enumerated(EnumType.STRING)
	@Column(name = "moa_type")
	private MoaTypeEnum moaType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "contract_status_enum")
	private ContractStatusEnum contractStatusEnum;
	
	@Enumerated
	@Column(name = "active_contract_status")
	private ApprovalEnum activeContractStatus;
	
	@Column(name = "can_termiate_moa", columnDefinition = "BIT default false")
	private Boolean canTermiateMoa;
	
	@Enumerated
	@Column(name = "keep_contract_active")
	private YesNoEnum keepContractActive;
	
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkplaceMonitoringDgMonitoring other = (WorkplaceMonitoringDgMonitoring) obj;
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

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public MoaTypeEnum getMoaType() {
		return moaType;
	}

	public void setMoaType(MoaTypeEnum moaType) {
		this.moaType = moaType;
	}

	public ContractStatusEnum getContractStatusEnum() {
		return contractStatusEnum;
	}

	public void setContractStatusEnum(ContractStatusEnum contractStatusEnum) {
		this.contractStatusEnum = contractStatusEnum;
	}

	public ApprovalEnum getActiveContractStatus() {
		return activeContractStatus;
	}

	public void setActiveContractStatus(ApprovalEnum activeContractStatus) {
		this.activeContractStatus = activeContractStatus;
	}

	public Boolean getCanTermiateMoa() {
		return canTermiateMoa;
	}

	public void setCanTermiateMoa(Boolean canTermiateMoa) {
		this.canTermiateMoa = canTermiateMoa;
	}

	public YesNoEnum getKeepContractActive() {
		return keepContractActive;
	}

	public void setKeepContractActive(YesNoEnum keepContractActive) {
		this.keepContractActive = keepContractActive;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
	
}