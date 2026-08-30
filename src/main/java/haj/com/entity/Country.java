package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import haj.com.framework.IDataEntity;


// TODO: Auto-generated Javadoc
/**
 * Country.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "country")
public class Country implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Country. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", length=19)
    private Date createDate;
    
    /** Name of the Country.
     *	Country name is required
     *	Country name can't be more than 100 characters
     *
     */
    
    @NotEmpty(message="Enter country name")
    @Size(min=1, max=100,message="Country name can't be more than 100 characters")
    @Column(name = "name", length = 100)
	private String name;
    
    /** Code of the Country.
     *  Country code is required
     *  Length of field must be exactly 2 characters
     *  May not start with space 
     *  Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'-
     *  Field must contain a valid Country_Code
     *  */
    @NotEmpty(message="Enter country code")
    @Size(min=2, max=2, message="Country code must be exatly 2 characters")
    @Column(name = "code", length = 2)
	private String code;
    
    /** Foreign System Identification. */
    @Column(name = "fs_id", unique = true, length = 10, nullable=true)
	private Long fsid;
    
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
		Country other = (Country) obj;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the fsid.
	 *
	 * @return the fsid
	 */
	public Long getFsid() {
		return fsid;
	}

	/**
	 * Sets the fsid.
	 *
	 * @param fsid the fsid to set
	 */
	public void setFsid(Long fsid) {
		this.fsid = fsid;
	}



}
