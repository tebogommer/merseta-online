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

import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "project_implementation_plan_learners")
@AuditTable(value = "project_implementation_plan_learners_hist")
@Audited
public class ProjectImplementationPlanLearners implements IDataEntity {

	/** Unique Id of ProjectImplementationPlanLearners. */
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
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_allocation_id", nullable = true)
	private DgAllocation dgAllocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_implementation_plan_id", nullable = true)
	private ProjectImplementationPlan projectImplementationPlan;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_link", nullable = true)
	private InterventionType interventionTypeLink;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;
	
	@Enumerated
	@Column(name = "next_tranch_payment")
	private TrancheEnum nextTranchPayment;
	
	@Column(name = "all_payments_completed", columnDefinition = "BIT default false")
	private Boolean allPaymentsCompleted;
	
	@Column(name = "learner_number")
	private Integer learnerNumber;

	@Transient
	private List<Doc> docs;

	public ProjectImplementationPlanLearners() {
		super();
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ProjectImplementationPlanLearners other = (ProjectImplementationPlanLearners) obj;
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

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public DgAllocation getDgAllocation() {
		return dgAllocation;
	}

	public void setDgAllocation(DgAllocation dgAllocation) {
		this.dgAllocation = dgAllocation;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public ProjectImplementationPlan getProjectImplementationPlan() {
		return projectImplementationPlan;
	}

	public void setProjectImplementationPlan(ProjectImplementationPlan projectImplementationPlan) {
		this.projectImplementationPlan = projectImplementationPlan;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public Boolean getAllPaymentsCompleted() {
		return allPaymentsCompleted;
	}

	public void setAllPaymentsCompleted(Boolean allPaymentsCompleted) {
		this.allPaymentsCompleted = allPaymentsCompleted;
	}

	public InterventionType getInterventionTypeLink() {
		return interventionTypeLink;
	}

	public void setInterventionTypeLink(InterventionType interventionTypeLink) {
		this.interventionTypeLink = interventionTypeLink;
	}

	public TrancheEnum getNextTranchPayment() {
		return nextTranchPayment;
	}

	public void setNextTranchPayment(TrancheEnum nextTranchPayment) {
		this.nextTranchPayment = nextTranchPayment;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public Integer getLearnerNumber() {
		return learnerNumber;
	}

	public void setLearnerNumber(Integer learnerNumber) {
		this.learnerNumber = learnerNumber;
	}
	
}