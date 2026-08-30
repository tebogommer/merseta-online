
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
import org.hibernate.validator.constraints.Length;

import haj.com.annotations.CSVAnnotation;
import haj.com.constants.HAJConstants;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.Training;
import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantBean.
 */
@Entity
@Table(name = "mandatory_grant_workplace")
public class MandatoryGrantWorkplace implements IDataEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@JoinColumn(name = "mandatory_grant_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private MandatoryGrant mandatoryGrant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sites_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Sites sites;
	
	@Column(name = "amount")
	private Integer amount;

	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MandatoryGrantWorkplace other = (MandatoryGrantWorkplace) obj;
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

	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public Sites getSites() {
		return sites;
	}

	public void setSites(Sites sites) {
		this.sites = sites;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
