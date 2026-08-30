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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ProgressType;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsers.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learners_progress")
@AuditTable(value = "company_learners_progress_hist")
@Audited
public class CompanyLearnersProgress implements IDataEntity {

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

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designated_trade_level_id", nullable = true)
	private DesignatedTradeLevel designatedTradeLevel;

	@Enumerated(EnumType.STRING)
	@Column(name = "progress_type")
	private ProgressType progressType;

	@Column(name = "credits_needed")
	private Integer creditsNeeded;

	@Column(name = "credits_completed")
	private Integer creditsCompleted;

	@Transient
	private List<Doc> docs;

	public CompanyLearnersProgress() {
		super();
	}

	public CompanyLearnersProgress(CompanyLearners companyLearners, ProgressType progressType) {
		super();
		this.companyLearners = companyLearners;
		this.progressType = progressType;
	}

	public CompanyLearnersProgress(CompanyLearners companyLearners, DesignatedTradeLevel designatedTradeLevel) {
		super();
		this.companyLearners = companyLearners;
		this.designatedTradeLevel = designatedTradeLevel;
	}

	public CompanyLearnersProgress(CompanyLearners companyLearners, DesignatedTradeLevel designatedTradeLevel, Integer creditsNeeded) {
		this(companyLearners, designatedTradeLevel);
		this.creditsNeeded = creditsNeeded;
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
		CompanyLearnersProgress other = (CompanyLearnersProgress) obj;
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

	public ProgressType getProgressType() {
		return progressType;
	}

	public void setProgressType(ProgressType progressType) {
		this.progressType = progressType;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Integer getCreditsNeeded() {
		return creditsNeeded;
	}

	public void setCreditsNeeded(Integer creditsNeeded) {
		this.creditsNeeded = creditsNeeded;
	}

	public Integer getCreditsCompleted() {
		return creditsCompleted;
	}

	public void setCreditsCompleted(Integer creditsCompleted) {
		this.creditsCompleted = creditsCompleted;
	}

	public DesignatedTradeLevel getDesignatedTradeLevel() {
		return designatedTradeLevel;
	}

	public void setDesignatedTradeLevel(DesignatedTradeLevel designatedTradeLevel) {
		this.designatedTradeLevel = designatedTradeLevel;
	}
}
