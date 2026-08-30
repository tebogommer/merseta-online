package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.lookup.UserResponsibility;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "users_responsibilities")
public class UsersResponsibilities implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_users_id", nullable = true)
	private CompanyUsers companyUsers;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_responsibility_id", nullable = true)
	private UserResponsibility userResponsibility;
	
	@Transient
	private Users sdfUser;

	public UsersResponsibilities() {
		super();
	}

	public UsersResponsibilities(CompanyUsers companyUsers, UserResponsibility userResponsibility) {
		super();
		this.companyUsers = companyUsers;
		this.userResponsibility = userResponsibility;
	}

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
		UsersResponsibilities other = (UsersResponsibilities) obj;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public CompanyUsers getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(CompanyUsers companyUsers) {
		this.companyUsers = companyUsers;
	}

	public UserResponsibility getUserResponsibility() {
		return userResponsibility;
	}

	public void setUserResponsibility(UserResponsibility userResponsibility) {
		this.userResponsibility = userResponsibility;
	}

	public Users getSdfUser() {
		return sdfUser;
	}

	public void setSdfUser(Users sdfUser) {
		this.sdfUser = sdfUser;
	}

}
