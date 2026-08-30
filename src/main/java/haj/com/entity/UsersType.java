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

import org.hibernate.annotations.CreationTimestamp;

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * UsersType.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "users_type")
public class UsersType implements IDataEntity {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** Unique Id of UsersType. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of UsersType. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/**
	 * The users.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users users;

	/**
	 * The type of user.
	 */
	@Enumerated
	@Column(name = "type_of_user")
	private ConfigDocProcessEnum typeOfUser;

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
		UsersType other = (UsersType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new users type.
	 */
	public UsersType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new users type.
	 *
	 * @param users
	 *            the users
	 * @param typeOfUser
	 *            the type of user
	 */
	public UsersType(Users users, ConfigDocProcessEnum typeOfUser) {
		super();
		this.users = users;
		this.typeOfUser = typeOfUser;
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
	 * Gets the users.
	 *
	 * @return the users
	 */
	public Users getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users
	 *            the new users
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * Gets the type of user.
	 *
	 * @return the type of user
	 */
	public ConfigDocProcessEnum getTypeOfUser() {
		return typeOfUser;
	}

	/**
	 * Sets the type of user.
	 *
	 * @param typeOfUser
	 *            the new type of user
	 */
	public void setTypeOfUser(ConfigDocProcessEnum typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

}
