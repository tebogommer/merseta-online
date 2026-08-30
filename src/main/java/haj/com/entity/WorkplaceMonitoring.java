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
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.entity.lookup.PurposeOfSiteVisitSupport;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "workplace_monitoring")
public class WorkplaceMonitoring implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of SiteVisit. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users users;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "visit_date", length = 19)
	private Date visitDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Enumerated
	@Column(name = "recieve_toolbox")
	private YesNoEnum recieveToolbox;

	@Enumerated
	@Column(name = "exposed_training_requirements")
	private YesNoEnum exposedTrainingRequirements;

	@Enumerated
	@Column(name = "satisfied_training")
	private YesNoEnum satisfiedTraining;

	@Enumerated
	@Column(name = "bariers_relting_to_training")
	private YesNoEnum bariersReltingToTraining;

	@Enumerated
	@Column(name = "qualified_mentor")
	private YesNoEnum qualifiedMentor;

	@Enumerated
	@Column(name = "access_training_provider")
	private YesNoEnum accessTrainingProvider;

	@Enumerated
	@Column(name = "possession_n1_Certificate")
	private YesNoEnum possessionN1Certificate;

	@Enumerated
	@Column(name = "prescribed_bargaining_council_wages")
	private YesNoEnum prescribedBargainingCouncilWages;

	@Enumerated
	@Column(name = "required_ppe")
	private YesNoEnum requiredPPE;

	@Enumerated
	@Column(name = "indicated_per_relevant_contract")
	private YesNoEnum indicatedPerRelevantContract;

	@Enumerated
	@Column(name = "extend_any_contract")
	private YesNoEnum extendAnyContract;
	
	@Column(name = "ignore_tranche_payments")
	private Boolean ignoreTranchePayments;
	
	@Transient
	private List<Doc> docs;

	public WorkplaceMonitoring() {
		super();
	}

	public WorkplaceMonitoring(Company company) {
		super();
		this.company = company;
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
		WorkplaceMonitoring other = (WorkplaceMonitoring) obj;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
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

	public YesNoEnum getRecieveToolbox() {
		return recieveToolbox;
	}

	public void setRecieveToolbox(YesNoEnum recieveToolbox) {
		this.recieveToolbox = recieveToolbox;
	}

	public YesNoEnum getExposedTrainingRequirements() {
		return exposedTrainingRequirements;
	}

	public void setExposedTrainingRequirements(YesNoEnum exposedTrainingRequirements) {
		this.exposedTrainingRequirements = exposedTrainingRequirements;
	}

	public YesNoEnum getSatisfiedTraining() {
		return satisfiedTraining;
	}

	public void setSatisfiedTraining(YesNoEnum satisfiedTraining) {
		this.satisfiedTraining = satisfiedTraining;
	}

	public YesNoEnum getBariersReltingToTraining() {
		return bariersReltingToTraining;
	}

	public void setBariersReltingToTraining(YesNoEnum bariersReltingToTraining) {
		this.bariersReltingToTraining = bariersReltingToTraining;
	}

	public YesNoEnum getQualifiedMentor() {
		return qualifiedMentor;
	}

	public void setQualifiedMentor(YesNoEnum qualifiedMentor) {
		this.qualifiedMentor = qualifiedMentor;
	}

	public YesNoEnum getAccessTrainingProvider() {
		return accessTrainingProvider;
	}

	public void setAccessTrainingProvider(YesNoEnum accessTrainingProvider) {
		this.accessTrainingProvider = accessTrainingProvider;
	}

	public YesNoEnum getPossessionN1Certificate() {
		return possessionN1Certificate;
	}

	public void setPossessionN1Certificate(YesNoEnum possessionN1Certificate) {
		this.possessionN1Certificate = possessionN1Certificate;
	}

	public YesNoEnum getPrescribedBargainingCouncilWages() {
		return prescribedBargainingCouncilWages;
	}

	public void setPrescribedBargainingCouncilWages(YesNoEnum prescribedBargainingCouncilWages) {
		this.prescribedBargainingCouncilWages = prescribedBargainingCouncilWages;
	}

	public YesNoEnum getRequiredPPE() {
		return requiredPPE;
	}

	public void setRequiredPPE(YesNoEnum requiredPPE) {
		this.requiredPPE = requiredPPE;
	}

	public YesNoEnum getIndicatedPerRelevantContract() {
		return indicatedPerRelevantContract;
	}

	public void setIndicatedPerRelevantContract(YesNoEnum indicatedPerRelevantContract) {
		this.indicatedPerRelevantContract = indicatedPerRelevantContract;
	}

	public YesNoEnum getExtendAnyContract() {
		return extendAnyContract;
	}

	public void setExtendAnyContract(YesNoEnum extendAnyContract) {
		this.extendAnyContract = extendAnyContract;
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

	public Boolean getIgnoreTranchePayments() {
		return ignoreTranchePayments;
	}

	public void setIgnoreTranchePayments(Boolean ignoreTranchePayments) {
		this.ignoreTranchePayments = ignoreTranchePayments;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

}
