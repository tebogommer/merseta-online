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

import haj.com.framework.IDataEntity;
import haj.com.json.IPLocation;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "user_browser_information")
public class UserBrowserInformation implements IDataEntity {

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

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	private Users users;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tasks_id", nullable = true)
	private Tasks tasks;

	@Column(name = "user_agent", columnDefinition = "LONGTEXT")
	private String userAgent;

	@Column(name = "operating_system", columnDefinition = "LONGTEXT")
	private String operatingSystem;

	@Column(name = "browser_name", columnDefinition = "LONGTEXT")
	private String browserName;

	@Column(name = "ip_address", columnDefinition = "LONGTEXT")
	private String ipAddress;

	@Column(name = "full_url", columnDefinition = "LONGTEXT")
	private String fullURL;

	@Column(name = "referrer", columnDefinition = "LONGTEXT")
	private String referrer;

	@Column(name = "calling_class", columnDefinition = "LONGTEXT")
	private String callingClass;

	@Column(name = "calling_method", columnDefinition = "LONGTEXT")
	private String callingMethod;

	/** The latitude. */
	private Double latitude;

	/** The longitude. */
	private Double longitude;

	@Column(name = "ip_address_decoded", columnDefinition = "LONGTEXT")
	private String ipAddressDecoded;

	@Transient
	private IPLocation ipLocation;

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
		UserBrowserInformation other = (UserBrowserInformation) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public UserBrowserInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBrowserInformation(String userAgent, String operatingSystem, String browserName, String ipAddress, String fullURL, String referrer, Users users, Tasks tasks) {
		super();
		this.userAgent = userAgent;
		this.operatingSystem = operatingSystem;
		this.browserName = browserName;
		this.ipAddress = ipAddress;
		this.fullURL = fullURL;
		this.referrer = referrer;
		this.users = users;
		this.tasks = tasks;
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

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFullURL() {
		return fullURL;
	}

	public void setFullURL(String fullURL) {
		this.fullURL = fullURL;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public String getCallingClass() {
		return callingClass;
	}

	public void setCallingClass(String callingClass) {
		this.callingClass = callingClass;
	}

	public String getCallingMethod() {
		return callingMethod;
	}

	public void setCallingMethod(String callingMethod) {
		this.callingMethod = callingMethod;
	}

	public String getIpAddressDecoded() {
		return ipAddressDecoded;
	}

	public void setIpAddressDecoded(String ipAddressDecoded) {
		this.ipAddressDecoded = ipAddressDecoded;
	}

	public IPLocation getIpLocation() {
		return ipLocation;
	}

	public void setIpLocation(IPLocation ipLocation) {
		this.ipLocation = ipLocation;
	}

}
