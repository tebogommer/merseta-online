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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

@Entity
@Table(name = "user_permissions")
public class UserPermissions implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	@Column(name = "provider_suspension", columnDefinition = "BIT default false")
	private Boolean providerSuspension;
	
	@Column(name = "provider_suspension_alter", columnDefinition = "BIT default false")
	private Boolean providerSuspensionAlter;
	
	@Column(name = "legacy_site_allocation", columnDefinition = "BIT default false")
	private Boolean legacySiteAllocation;
	
	@Column(name = "legacy_site_allocation_alter", columnDefinition = "BIT default false")
	private Boolean legacySiteAllocationAlter;
	
	/*
	 * Indexs
	 * CREATE INDEX user_permissions_user_id ON user_permissions (user_id);
	 */
		
	public UserPermissions() {
		super();
	}

	public UserPermissions(Users user) {
		super();
		this.user = user;
		providerSuspension = false;
		providerSuspensionAlter = false;
		legacySiteAllocation = false;
		legacySiteAllocationAlter = false;
	}

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
		UserPermissions other = (UserPermissions) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Boolean getProviderSuspension() {
		return providerSuspension;
	}

	public void setProviderSuspension(Boolean providerSuspension) {
		this.providerSuspension = providerSuspension;
	}

	public Boolean getProviderSuspensionAlter() {
		return providerSuspensionAlter;
	}

	public void setProviderSuspensionAlter(Boolean providerSuspensionAlter) {
		this.providerSuspensionAlter = providerSuspensionAlter;
	}

	public Boolean getLegacySiteAllocation() {
		return legacySiteAllocation;
	}

	public void setLegacySiteAllocation(Boolean legacySiteAllocation) {
		this.legacySiteAllocation = legacySiteAllocation;
	}

	public Boolean getLegacySiteAllocationAlter() {
		return legacySiteAllocationAlter;
	}

	public void setLegacySiteAllocationAlter(Boolean legacySiteAllocationAlter) {
		this.legacySiteAllocationAlter = legacySiteAllocationAlter;
	}
	
}