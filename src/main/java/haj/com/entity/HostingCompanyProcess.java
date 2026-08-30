package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * HostingCompanyProcess.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "hosting_company_process")
public class HostingCompanyProcess implements IDataEntity {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** Unique Id of HostingCompanyProcess. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/**
	 * The hosting company.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_id", nullable = true)
	private HostingCompany hostingCompany;
	

	/**
	 * The next process.
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "next_process_id", nullable = true)
	private HostingCompanyProcess nextProcess;


	/**
	 * The workflow process.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "workflow_process")
	private ConfigDocProcessEnum workflowProcess;

	/** Note. */
	@Column(name = "note", length=300)
	private String note;
	
	/**
	 * The process roles.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hostingCompanyProcess")
	private Set<ProcessRoles> processRoles = new HashSet<ProcessRoles>(0);

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HostingCompanyProcess other = (HostingCompanyProcess) obj;
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
	 * @param id            the id to set
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
	 * @param createDate            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany
	 *            the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
	}

	/**
	 * Gets the workflow process.
	 *
	 * @return the workflow process
	 */
	public ConfigDocProcessEnum getWorkflowProcess() {
		return workflowProcess;
	}

	/**
	 * Sets the workflow process.
	 *
	 * @param workflowProcess
	 *            the new workflow process
	 */
	public void setWorkflowProcess(ConfigDocProcessEnum workflowProcess) {
		this.workflowProcess = workflowProcess;
	}

	/**
	 * Gets the process roles.
	 *
	 * @return the process roles
	 */
	public Set<ProcessRoles> getProcessRoles() {
		return processRoles;
	}

	/**
	 * Sets the process roles.
	 *
	 * @param processRoles
	 *            the new process roles
	 */
	public void setProcessRoles(Set<ProcessRoles> processRoles) {
		this.processRoles = processRoles;
	}

	/**
	 * Gets the next process.
	 *
	 * @return the next process
	 */
	public HostingCompanyProcess getNextProcess() {
		return nextProcess;
	}

	/**
	 * Sets the next process.
	 *
	 * @param nextProcess
	 *            the new next process
	 */
	public void setNextProcess(HostingCompanyProcess nextProcess) {
		this.nextProcess = nextProcess;
	}

}
