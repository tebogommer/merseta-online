package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.UUID;

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

import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.lookup.Roles;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Wsp.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "signoff")
@AuditTable(value = "signoff_hist")
@Audited
public class Signoff implements IDataEntity {

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
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mg_verification_id", nullable = true)
	private MgVerification mgVerification;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_verification_id", nullable = true)
	private DgVerification dgVerification;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_dispute_id", nullable = true)
	private WspDispute wspDispute;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "temp_signoff_id", nullable = true)
	private TempSignoff tempSignoff;
	
	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

	/** The accept. */
	@Column(name = "accept")
	private Boolean accept;

	@Column(name = "completed")
	private Boolean completed;
	
	@Column(name = "one_time_password", length = 1000)
	private String oneTimePassword;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_sign_off_user_changed", length = 19, nullable = true)
	private Date dateSignOffUserChanged;  
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "change_user_id", nullable = true)
	private Users changeUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date", length = 19, nullable = true)
	private Date expiryDate;

	/** The dispute. */
	@Column(name = "dispute")
	private Boolean dispute;

	@Column(name = "sign_off_title", columnDefinition = "LONGTEXT")
	private String signOffTitle;

	@Column(name = "guid", length = 100, nullable = true)
	private String guid;
	
	@Column(name = "lastest_signoff")
	private Boolean lastestSignoff;
	
	@Column(name = "external_user_signoff")
	private Boolean externalUserSignoff;
	
	@Column(name = "can_change_sign_off_user")
	private Boolean canChangeSignOffUser;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = true)
	private Roles role;
	
	@Enumerated
	@Column(name = "signoff_by_enum")
	private SignoffByEnum signoffByEnum;
	
	@Transient
	private String disignation;
	
	@Transient
	private String status;

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
		Signoff other = (Signoff) obj;
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
	public Signoff(Wsp wsp, Users user) {
		this();
		this.wsp = wsp;
		this.user = user;
	}

	public Signoff(MgVerification mgVerification, Users user, String signOffTitle) {
		this(user, signOffTitle);
		this.mgVerification = mgVerification;
	}

	public Signoff(DgVerification dgVerification, Users user, String signOffTitle) {
		this(user, signOffTitle);
		this.dgVerification = dgVerification;
	}

	public Signoff(WspDispute wspDispute, Users user, String signOffTitle) {
		this(user, signOffTitle);
		this.wspDispute = wspDispute;
	}

	public Signoff(WspDispute wspDispute, TempSignoff tempSignoff, String signOffTitle) {
		this();
		this.tempSignoff = tempSignoff;
		this.signOffTitle = signOffTitle;
		this.wspDispute = wspDispute;
	}

	public Signoff(Users user, String signOffTitle) {
		this();
		this.user = user;
		this.signOffTitle = signOffTitle;
	}
	
	public Signoff(Users user, String targetClass, String signOffTitle) {
		this(user, signOffTitle);
		this.targetClass = targetClass;
	}

	public Signoff(Users user, Long targetKey, String targetClass, String signOffTitle, String oneTimePassword, Date expiryDate) {
		super();
		this.user = user;
		this.targetKey = targetKey;
		this.targetClass = targetClass;
		this.signOffTitle = signOffTitle;
		this.oneTimePassword = oneTimePassword;
		this.guid = UUID.randomUUID().toString();
		this.expiryDate = expiryDate;
	}
	
	public Signoff(Users user, MgVerification mgVerification, String signOffTitle, String oneTimePassword, Date expiryDate) {
		super();
		this.user = user;
		this.mgVerification = mgVerification;
		this.signOffTitle = signOffTitle;
		this.oneTimePassword = oneTimePassword;
		this.guid = UUID.randomUUID().toString();
		this.expiryDate = expiryDate;
	}

	/**
	 * Instantiates a new wsp signoff.
	 */
	public Signoff() {
		super();
		this.guid = UUID.randomUUID().toString();
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

	public MgVerification getMgVerification() {
		return mgVerification;
	}

	public void setMgVerification(MgVerification mgVerification) {
		this.mgVerification = mgVerification;
	}

	public String getSignOffTitle() {
		return signOffTitle;
	}

	public void setSignOffTitle(String signOffTitle) {
		this.signOffTitle = signOffTitle;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public WspDispute getWspDispute() {
		return wspDispute;
	}

	public void setWspDispute(WspDispute wspDispute) {
		this.wspDispute = wspDispute;
	}

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public TempSignoff getTempSignoff() {
		return tempSignoff;
	}

	public void setTempSignoff(TempSignoff tempSignoff) {
		this.tempSignoff = tempSignoff;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getSignOffDate() {
		return signOffDate;
	}

	public void setSignOffDate(Date signOffDate) {
		this.signOffDate = signOffDate;
	}
	
	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public String getDisignation() {
		return disignation;
	}

	public void setDisignation(String disignation) {
		this.disignation = disignation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getDateSignOffUserChanged() {
		return dateSignOffUserChanged;
	}

	public void setDateSignOffUserChanged(Date dateSignOffUserChanged) {
		this.dateSignOffUserChanged = dateSignOffUserChanged;
	}

	public Users getChangeUser() {
		return changeUser;
	}

	public void setChangeUser(Users changeUser) {
		this.changeUser = changeUser;
	}

	public Boolean getLastestSignoff() {
		return lastestSignoff;
	}

	public void setLastestSignoff(Boolean lastestSignoff) {
		this.lastestSignoff = lastestSignoff;
	}

	public Boolean getExternalUserSignoff() {
		return externalUserSignoff;
	}

	public void setExternalUserSignoff(Boolean externalUserSignoff) {
		this.externalUserSignoff = externalUserSignoff;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Boolean getCanChangeSignOffUser() {
		return canChangeSignOffUser;
	}

	public void setCanChangeSignOffUser(Boolean canChangeSignOffUser) {
		this.canChangeSignOffUser = canChangeSignOffUser;
	}

	public SignoffByEnum getSignoffByEnum() {
		return signoffByEnum;
	}

	public void setSignoffByEnum(SignoffByEnum signoffByEnum) {
		this.signoffByEnum = signoffByEnum;
	}
}
