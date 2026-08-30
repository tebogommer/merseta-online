package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Roles;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * ProcessRoles.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "process_roles")
public class ProcessRoles implements IDataEntity {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** Unique Id of ProcessRoles. */
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
	 * The hosting company process.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_process_id", nullable = true)
	private HostingCompanyProcess hostingCompanyProcess;

	/**
	 * The roles.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roles_id", nullable = true)
	private Roles roles;

	/** The role permission. */
	@Enumerated
	@Column(name = "role_permission", nullable = true)
	private UserPermissionEnum rolePermission;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "next_task_role", nullable = true)
	private Roles nextTaskRole;
	
	/**
	 * The order.
	 */
	@Column(name = "role_order")
	private Integer roleOrder;

	@Column(name = "number_of_days")
	private Integer numberOfDays;

	/** Note. */
	@Column(name = "note", columnDefinition = "LONGTEXT")
	private String note;

	/** Note. */
	@Column(name = "reject_message", columnDefinition = "LONGTEXT")
	private String rejectMessage;

	/**
	 * The workflow process.
	 */
	@Enumerated
	@Column(name = "company_user_type")
	private ConfigDocProcessEnum companyUserType;

	@Column(name = "target_class", columnDefinition = "LONGTEXT")
	private String targetClass;

	/** Note. */
	@Column(name = "target_method", columnDefinition = "LONGTEXT")
	private String targetMethod;

	/** The style. */
	@Transient
	private String style;

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
		ProcessRoles other = (ProcessRoles) obj;
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
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the hosting company process.
	 *
	 * @return the hosting company process
	 */
	public HostingCompanyProcess getHostingCompanyProcess() {
		return hostingCompanyProcess;
	}

	/**
	 * Sets the hosting company process.
	 *
	 * @param hostingCompanyProcess
	 *            the new hosting company process
	 */
	public void setHostingCompanyProcess(HostingCompanyProcess hostingCompanyProcess) {
		this.hostingCompanyProcess = hostingCompanyProcess;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public Roles getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	/**
	 * Gets the role order.
	 *
	 * @return the role order
	 */
	public Integer getRoleOrder() {
		return roleOrder;
	}

	/**
	 * Sets the role order.
	 *
	 * @param roleOrder
	 *            the new role order
	 */
	public void setRoleOrder(Integer roleOrder) {
		this.roleOrder = roleOrder;
	}

	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Sets the style.
	 *
	 * @param style
	 *            the new style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Gets the role permission.
	 *
	 * @return the role permission
	 */
	public UserPermissionEnum getRolePermission() {
		return rolePermission;
	}

	/**
	 * Sets the role permission.
	 *
	 * @param rolePermission
	 *            the new role permission
	 */
	public void setRolePermission(UserPermissionEnum rolePermission) {
		this.rolePermission = rolePermission;
	}

	/**
	 * Gets the company user type.
	 *
	 * @return the company user type
	 */
	public ConfigDocProcessEnum getCompanyUserType() {
		return companyUserType;
	}

	/**
	 * Sets the company user type.
	 *
	 * @param companyUserType
	 *            the new company user type
	 */
	public void setCompanyUserType(ConfigDocProcessEnum companyUserType) {
		this.companyUserType = companyUserType;
	}

	/**
	 * Gets the reject message.
	 *
	 * @return the reject message
	 */
	public String getRejectMessage() {
		return rejectMessage;
	}

	/**
	 * Sets the reject message.
	 *
	 * @param rejectMessage
	 *            the new reject message
	 */
	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}

	public Integer getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public Roles getNextTaskRole() {
		return nextTaskRole;
	}

	public void setNextTaskRole(Roles nextTaskRole) {
		this.nextTaskRole = nextTaskRole;
	}

}
