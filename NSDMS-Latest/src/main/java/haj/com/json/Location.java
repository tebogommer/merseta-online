
package haj.com.json;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 */
public class Location implements Serializable {

	/** The name. */
	@SerializedName("name")
	@Expose
	private String name;

	/** The region. */
	@SerializedName("region")
	@Expose
	private String region;

	/** The country. */
	@SerializedName("country")
	@Expose
	private String country;

	/** The lat. */
	@SerializedName("lat")
	@Expose
	private Double lat;

	/** The lon. */
	@SerializedName("lon")
	@Expose
	private Double lon;

	/** The tz id. */
	@SerializedName("tz_id")
	@Expose
	private String tzId;

	/** The localtime epoch. */
	@SerializedName("localtime_epoch")
	@Expose
	private Integer localtimeEpoch;

	/** The localtime. */
	@SerializedName("localtime")
	@Expose
	private String localtime;

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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region
	 *            the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the lat.
	 *
	 * @return the lat
	 */
	public Double getLat() {
		return lat;
	}

	/**
	 * Sets the lat.
	 *
	 * @param lat
	 *            the new lat
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * Gets the lon.
	 *
	 * @return the lon
	 */
	public Double getLon() {
		return lon;
	}

	/**
	 * Sets the lon.
	 *
	 * @param lon
	 *            the new lon
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}

	/**
	 * Gets the tz id.
	 *
	 * @return the tz id
	 */
	public String getTzId() {
		return tzId;
	}

	/**
	 * Sets the tz id.
	 *
	 * @param tzId
	 *            the new tz id
	 */
	public void setTzId(String tzId) {
		this.tzId = tzId;
	}

	/**
	 * Gets the localtime epoch.
	 *
	 * @return the localtime epoch
	 */
	public Integer getLocaltimeEpoch() {
		return localtimeEpoch;
	}

	/**
	 * Sets the localtime epoch.
	 *
	 * @param localtimeEpoch
	 *            the new localtime epoch
	 */
	public void setLocaltimeEpoch(Integer localtimeEpoch) {
		this.localtimeEpoch = localtimeEpoch;
	}

	/**
	 * Gets the localtime.
	 *
	 * @return the localtime
	 */
	public String getLocaltime() {
		return localtime;
	}

	/**
	 * Sets the localtime.
	 *
	 * @param localtime
	 *            the new localtime
	 */
	public void setLocaltime(String localtime) {
		this.localtime = localtime;
	}

}
