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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.lookup.Language;
import haj.com.framework.IDataEntity;


@Entity
@Table(name = "users_language")
@AuditTable(value = "users_language_hist")
@Audited
public class UsersLanguage implements IDataEntity
{
	
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

	@JoinColumn(name="user_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private Users user;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name="language_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private Language language;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "speark_id", nullable = true)
	private YesNoLookup speark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "read_id", nullable = true)
	private YesNoLookup read;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "write_id", nullable = true)
	private YesNoLookup write;
	
	@Column(name="home_language",nullable= true)
	private Boolean homeLanguage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id", nullable = true)
	private Users updateUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	private Date updateDate;

	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

    
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
		UsersLanguage other = (UsersLanguage) obj;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public YesNoLookup getSpeark() {
		return speark;
	}

	public void setSpeark(YesNoLookup speark) {
		this.speark = speark;
	}

	public YesNoLookup getRead() {
		return read;
	}

	public void setRead(YesNoLookup read) {
		this.read = read;
	}

	public YesNoLookup getWrite() {
		return write;
	}

	public void setWrite(YesNoLookup write) {
		this.write = write;
	}

	public Boolean getHomeLanguage() {
		return homeLanguage;
	}

	public void setHomeLanguage(Boolean homeLanguage) {
		this.homeLanguage = homeLanguage;
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

	public Users getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Users updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
