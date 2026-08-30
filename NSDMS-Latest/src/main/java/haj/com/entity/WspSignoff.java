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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.lookup.SDFType;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Wsp.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_signoff")
@AuditTable(value = "wsp_signoff_hist")
@Audited
public class WspSignoff implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sign_off_date", length = 19)
	private Date signOffDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** The user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sdf_type_id", nullable = true)
	private SDFType sdfType;

	/** The accept. */
	@Column(name = "accept")
	private Boolean accept;

	/** The dispute. */
	@Column(name = "dispute")
	private Boolean dispute;

	@Transient
	private ExtensionRequest extensionRequest;
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
		WspSignoff other = (WspSignoff) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new wsp signoff.
	 *
	 * @param wsp
	 *            the wsp
	 * @param user
	 *            the user
	 */
	public WspSignoff(Wsp wsp, Users user) {
		super();
		this.wsp = wsp;
		this.user = user;
	}

	/**
	 * Instantiates a new wsp signoff.
	 */
	public WspSignoff() {
		super();
		// TODO Auto-generated constructor stub
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the accept.
	 *
	 * @return the accept
	 */
	public Boolean getAccept() {
		return accept;
	}

	/**
	 * Sets the accept.
	 *
	 * @param accept
	 *            the new accept
	 */
	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	/**
	 * Gets the dispute.
	 *
	 * @return the dispute
	 */
	public Boolean getDispute() {
		return dispute;
	}

	/**
	 * Sets the dispute.
	 *
	 * @param dispute
	 *            the new dispute
	 */
	public void setDispute(Boolean dispute) {
		this.dispute = dispute;
	}

	public SDFType getSdfType() {
		return sdfType;
	}

	public void setSdfType(SDFType sdfType) {
		this.sdfType = sdfType;
	}

	public Date getSignOffDate() {
		return signOffDate;
	}

	public void setSignOffDate(Date signOffDate) {
		this.signOffDate = signOffDate;
	}

	public ExtensionRequest getExtensionRequest() {
		return extensionRequest;
	}

	public void setExtensionRequest(ExtensionRequest extensionRequest) {
		this.extensionRequest = extensionRequest;
	}
}
