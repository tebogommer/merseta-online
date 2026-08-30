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

import com.fasterxml.jackson.annotation.JsonIgnore;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Images.
 */
@Entity
@Table(name = "images")
public class Images implements IDataEntity {
	
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

	/** The security pic. */
	@Column(name = "security_pic", nullable = true, columnDefinition = "LONGBLOB")
	private byte[] securityPic;

	/** The users. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = true)
	private Users users;
	
	/** The hosting company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_id", insertable = true, updatable = true, nullable = true)
	private HostingCompany hostingCompany;

	/** The extension. */
	@Column(name = "extension", nullable = true, length = 20)
	private String extension;

	/** The original fname. */
	@Column(name = "original_fname", nullable = true, length = 500)
	private String originalFname;

	/** The content type. */
	@Column(name = "content_type", nullable = true, length = 200)
	private String contentType;

	/** The new fname. */
	@Column(name = "new_fname", nullable = true, length = 200)
	private String newFname;

	/** The active. */
	private Boolean active;

	/** The latitude. */
	@Column(name = "latitude", nullable = true)
	private Double latitude;

	/** The longitude. */
	@Column(name = "longitude", nullable = true)
	private Double longitude;

	/** The small file name. */
	@Transient
	private String smallFileName;

	
	/** The bug report. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bug_report_id", insertable = true, updatable = true, nullable = true)
	private BugReport bugReport;
	
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
		Images other = (Images) obj;
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
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@JsonIgnore
	public Users getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(Users users) {
		this.users = users;
	}
	
	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	@JsonIgnore
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	@JsonIgnore
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the security pic.
	 *
	 * @return the security pic
	 */
	@JsonIgnore
	public byte[] getSecurityPic() {
		return securityPic;
	}

	/**
	 * Sets the security pic.
	 *
	 * @param securityPic the new security pic
	 */
	public void setSecurityPic(byte[] securityPic) {
		this.securityPic = securityPic;
	}

	/**
	 * Gets the extension.
	 *
	 * @return the extension
	 */
	@JsonIgnore
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the extension.
	 *
	 * @param extension the new extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Gets the original fname.
	 *
	 * @return the original fname
	 */
	@JsonIgnore
	public String getOriginalFname() {
		return originalFname;
	}

	/**
	 * Sets the original fname.
	 *
	 * @param originalFname the new original fname
	 */
	public void setOriginalFname(String originalFname) {
		this.originalFname = originalFname;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	@JsonIgnore
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the new fname.
	 *
	 * @return the new fname
	 */
	public String getNewFname() {
		return newFname;
	}

	/**
	 * Sets the new fname.
	 *
	 * @param newFname the new new fname
	 */
	public void setNewFname(String newFname) {
		this.newFname = newFname;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	@JsonIgnore
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	@JsonIgnore
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the small file name.
	 *
	 * @return the small file name
	 */
	@JsonIgnore
	public String getSmallFileName() {
		if (newFname != null && !newFname.isEmpty()) {
			try {
			 smallFileName = newFname.substring(0, newFname.indexOf('.')) + "_small." + extension;
			} catch(Exception e) {}
		}
		return smallFileName;
	}

	/**
	 * Sets the small file name.
	 *
	 * @param smallFileName the new small file name
	 */
	public void setSmallFileName(String smallFileName) {
		this.smallFileName = smallFileName;
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
	 * @param hostingCompany the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
	}

	/**
	 * Gets the bug report.
	 *
	 * @return the bug report
	 */
	public BugReport getBugReport() {
		return bugReport;
	}

	/**
	 * Sets the bug report.
	 *
	 * @param bugReport the new bug report
	 */
	public void setBugReport(BugReport bugReport) {
		this.bugReport = bugReport;
	}

}
