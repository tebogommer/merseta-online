package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.framework.IDataEntity;

/**
 * The Class sars_mg_retrun_years.
 */
@Entity
@Table(name = "sars_levy_scheme_year_returns")
@AuditTable(value = "sars_levy_scheme_year_returns_hist")
@Audited
public class SarsLevySchemeYearReturns implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SarsLevyDetailCalculation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of SarsLevyDetailCalculation. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** The scheme year for when the check must apply */
	@Column(name = "for_scheme_year", length = 19, nullable = false)
	private Integer forSchemeYear;
	
	/** Indicator if returns are allowed on Mandatory payments */
	@Column(name = "allow_returns_mandatory")
	private Boolean allowReturnsMandatory;
	
	/** Indicator if invoices are allowed on Mandatory payments */
	@Column(name = "allow_invoices_mandatory")
	private Boolean allowInvoicesMandatory;
	
	/** Indicator if returns are allowed on Discretionary payments */
	@Column(name = "allow_returns_discretionary")
	private Boolean allowReturnsDiscretionary;
	
	/** Indicator if invoices are allowed on Discretionary payments */
	@Column(name = "allow_invoices_discretionary")
	private Boolean allowInvoicesDiscretionary;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user_id", nullable = true)
	private Users lastActionUser;
    
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
		SarsLevySchemeYearReturns other = (SarsLevySchemeYearReturns) obj;
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

	public Integer getForSchemeYear() {
		return forSchemeYear;
	}

	public void setForSchemeYear(Integer forSchemeYear) {
		this.forSchemeYear = forSchemeYear;
	}

	public Boolean getAllowReturnsMandatory() {
		return allowReturnsMandatory;
	}

	public void setAllowReturnsMandatory(Boolean allowReturnsMandatory) {
		this.allowReturnsMandatory = allowReturnsMandatory;
	}

	public Boolean getAllowInvoicesMandatory() {
		return allowInvoicesMandatory;
	}

	public void setAllowInvoicesMandatory(Boolean allowInvoicesMandatory) {
		this.allowInvoicesMandatory = allowInvoicesMandatory;
	}

	public Boolean getAllowReturnsDiscretionary() {
		return allowReturnsDiscretionary;
	}

	public void setAllowReturnsDiscretionary(Boolean allowReturnsDiscretionary) {
		this.allowReturnsDiscretionary = allowReturnsDiscretionary;
	}

	public Boolean getAllowInvoicesDiscretionary() {
		return allowInvoicesDiscretionary;
	}

	public void setAllowInvoicesDiscretionary(Boolean allowInvoicesDiscretionary) {
		this.allowInvoicesDiscretionary = allowInvoicesDiscretionary;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}
	
}