package haj.com.sars;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.Users;
import haj.com.framework.IDataEntity;


// TODO: Auto-generated Javadoc
/**
 * The Class SarsFiles.
 */
@Entity
@Table(name = "sars_files")
public class SarsFiles implements IDataEntity {
	
	public SarsFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

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


	/** The for month. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "for_month", length = 19)
	private Date forMonth;
   
	 /** The employers. */
 	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sarsFiles")
	 @Cascade({ CascadeType.DELETE})
	 private Set<SarsEmployerDetail> employers = new HashSet<SarsEmployerDetail>(0);
 	
	@Column(name = "loaded_to_bp", columnDefinition = "BIT default false")
 	private Boolean loadedToGP;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "load_gp_date", length = 19)
	private Date loadGPDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "loaded_to_gp_by_user_id")
	private Users loadedToGpByUser;
	
	@Column(name = "can_process_mg_payments", columnDefinition = "BIT default true")
 	private Boolean canProcessMgPayments;
	 
	 /** The levy summary. */
 	@Transient
	private SarsLevyDetails levySummary;
	 
	 /** The sars levies paid. */
 	@Transient
	private SarsLeviesPaid sarsLeviesPaid;
	 
	/** The discreptancy. */
	@Transient
    private BigDecimal discreptancy;
    
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
		SarsFiles other = (SarsFiles) obj;
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

	/**
	 * Gets the for month.
	 *
	 * @return the for month
	 */
	public Date getForMonth() {
		return forMonth;
	}

	/**
	 * Sets the for month.
	 *
	 * @param forMonth the new for month
	 */
	public void setForMonth(Date forMonth) {
		this.forMonth = forMonth;
	}

	/**
	 * Gets the employers.
	 *
	 * @return the employers
	 */
	public Set<SarsEmployerDetail> getEmployers() {
		return employers;
	}

	/**
	 * Sets the employers.
	 *
	 * @param employers the new employers
	 */
	public void setEmployers(Set<SarsEmployerDetail> employers) {
		this.employers = employers;
	}

	/**
	 * Gets the levy summary.
	 *
	 * @return the levy summary
	 */
	public SarsLevyDetails getLevySummary() {
		return levySummary;
	}

	/**
	 * Sets the levy summary.
	 *
	 * @param levySummary the new levy summary
	 */
	public void setLevySummary(SarsLevyDetails levySummary) {
		this.levySummary = levySummary;
	}



	/**
	 * Gets the sars levies paid.
	 *
	 * @return the sars levies paid
	 */
	public SarsLeviesPaid getSarsLeviesPaid() {
		return sarsLeviesPaid;
	}

	/**
	 * Sets the sars levies paid.
	 *
	 * @param sarsLeviesPaid the new sars levies paid
	 */
	public void setSarsLeviesPaid(SarsLeviesPaid sarsLeviesPaid) {
		this.sarsLeviesPaid = sarsLeviesPaid;
	}

	/**
	 * Gets the discreptancy.
	 *
	 * @return the discreptancy
	 */
	public BigDecimal getDiscreptancy() {
		return discreptancy;
	}

	/**
	 * Sets the discreptancy.
	 *
	 * @param discreptancy the new discreptancy
	 */
	public void setDiscreptancy(BigDecimal discreptancy) {
		this.discreptancy = discreptancy;
	}

	public Boolean getLoadedToGP() {
		return loadedToGP;
	}

	public void setLoadedToGP(Boolean loadedToGP) {
		this.loadedToGP = loadedToGP;
	}

	public Date getLoadGPDate() {
		return loadGPDate;
	}

	public void setLoadGPDate(Date loadGPDate) {
		this.loadGPDate = loadGPDate;
	}

	public Users getLoadedToGpByUser() {
		return loadedToGpByUser;
	}

	public void setLoadedToGpByUser(Users loadedToGpByUser) {
		this.loadedToGpByUser = loadedToGpByUser;
	}

	public Boolean getCanProcessMgPayments() {
		return canProcessMgPayments;
	}

	public void setCanProcessMgPayments(Boolean canProcessMgPayments) {
		this.canProcessMgPayments = canProcessMgPayments;
	}




}
