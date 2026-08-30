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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;


@Entity
@Table(name = "levy_detail_mg_payments_alter_audit")
public class LevyDetailMgPaymentsAlterAudit implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of GpBankAccountAlterAudit. */
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
	@JoinColumn(name = "user_id", nullable = true)
	private Users users;
	
	@Column(name = "mg_can_process")
	private Boolean mgCanProcess;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_file_id", nullable = true)
	private SarsFiles sarsFile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_levy_details_id", nullable = true)
	private SarsLevyDetails sarsLevyDetails;

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
		LevyDetailMgPaymentsAlterAudit other = (LevyDetailMgPaymentsAlterAudit) obj;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Boolean getMgCanProcess() {
		return mgCanProcess;
	}

	public void setMgCanProcess(Boolean mgCanProcess) {
		this.mgCanProcess = mgCanProcess;
	}

	public SarsFiles getSarsFile() {
		return sarsFile;
	}

	public void setSarsFile(SarsFiles sarsFile) {
		this.sarsFile = sarsFile;
	}

	public SarsLevyDetails getSarsLevyDetails() {
		return sarsLevyDetails;
	}

	public void setSarsLevyDetails(SarsLevyDetails sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}

}