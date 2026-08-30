
package haj.com.json;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class Current.
 */
public class Current implements Serializable {

	/** The last updated epoch. */
	@SerializedName("last_updated_epoch")
	@Expose
	private Integer lastUpdatedEpoch;

	/** The last updated. */
	@SerializedName("last_updated")
	@Expose
	private String lastUpdated;

	/** The temp C. */
	@SerializedName("temp_c")
	@Expose
	private Double tempC;

	/** The temp F. */
	@SerializedName("temp_f")
	@Expose
	private Double tempF;

	/** The is day. */
	@SerializedName("is_day")
	@Expose
	private Integer isDay;

	/** The condition. */
	@SerializedName("condition")
	@Expose
	private Condition condition;

	/** The wind mph. */
	@SerializedName("wind_mph")
	@Expose
	private Double windMph;

	/** The wind kph. */
	@SerializedName("wind_kph")
	@Expose
	private Double windKph;

	/** The wind degree. */
	@SerializedName("wind_degree")
	@Expose
	private Integer windDegree;

	/** The wind dir. */
	@SerializedName("wind_dir")
	@Expose
	private String windDir;

	/** The pressure mb. */
	@SerializedName("pressure_mb")
	@Expose
	private Double pressureMb;

	/** The pressure in. */
	@SerializedName("pressure_in")
	@Expose
	private Double pressureIn;

	/** The precip mm. */
	@SerializedName("precip_mm")
	@Expose
	private Double precipMm;

	/** The precip in. */
	@SerializedName("precip_in")
	@Expose
	private Double precipIn;

	/** The humidity. */
	@SerializedName("humidity")
	@Expose
	private Integer humidity;

	/** The cloud. */
	@SerializedName("cloud")
	@Expose
	private Integer cloud;

	/** The feelslike C. */
	@SerializedName("feelslike_c")
	@Expose
	private Double feelslikeC;

	/** The feelslike F. */
	@SerializedName("feelslike_f")
	@Expose
	private Double feelslikeF;

	/** The vis km. */
	@SerializedName("vis_km")
	@Expose
	private Double visKm;

	/** The vis miles. */
	@SerializedName("vis_miles")
	@Expose
	private Double visMiles;

	/**
	 * Gets the last updated epoch.
	 *
	 * @return the last updated epoch
	 */
	public Integer getLastUpdatedEpoch() {
		return lastUpdatedEpoch;
	}

	/**
	 * Sets the last updated epoch.
	 *
	 * @param lastUpdatedEpoch
	 *            the new last updated epoch
	 */
	public void setLastUpdatedEpoch(Integer lastUpdatedEpoch) {
		this.lastUpdatedEpoch = lastUpdatedEpoch;
	}

	/**
	 * Gets the last updated.
	 *
	 * @return the last updated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * Sets the last updated.
	 *
	 * @param lastUpdated
	 *            the new last updated
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * Gets the temp C.
	 *
	 * @return the temp C
	 */
	public Double getTempC() {
		return tempC;
	}

	/**
	 * Sets the temp C.
	 *
	 * @param tempC
	 *            the new temp C
	 */
	public void setTempC(Double tempC) {
		this.tempC = tempC;
	}

	/**
	 * Gets the temp F.
	 *
	 * @return the temp F
	 */
	public Double getTempF() {
		return tempF;
	}

	/**
	 * Sets the temp F.
	 *
	 * @param tempF
	 *            the new temp F
	 */
	public void setTempF(Double tempF) {
		this.tempF = tempF;
	}

	/**
	 * Gets the checks if is day.
	 *
	 * @return the checks if is day
	 */
	public Integer getIsDay() {
		return isDay;
	}

	/**
	 * Sets the checks if is day.
	 *
	 * @param isDay
	 *            the new checks if is day
	 */
	public void setIsDay(Integer isDay) {
		this.isDay = isDay;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * Sets the condition.
	 *
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * Gets the wind mph.
	 *
	 * @return the wind mph
	 */
	public Double getWindMph() {
		return windMph;
	}

	/**
	 * Sets the wind mph.
	 *
	 * @param windMph
	 *            the new wind mph
	 */
	public void setWindMph(Double windMph) {
		this.windMph = windMph;
	}

	/**
	 * Gets the wind kph.
	 *
	 * @return the wind kph
	 */
	public Double getWindKph() {
		return windKph;
	}

	/**
	 * Sets the wind kph.
	 *
	 * @param windKph
	 *            the new wind kph
	 */
	public void setWindKph(Double windKph) {
		this.windKph = windKph;
	}

	/**
	 * Gets the wind degree.
	 *
	 * @return the wind degree
	 */
	public Integer getWindDegree() {
		return windDegree;
	}

	/**
	 * Sets the wind degree.
	 *
	 * @param windDegree
	 *            the new wind degree
	 */
	public void setWindDegree(Integer windDegree) {
		this.windDegree = windDegree;
	}

	/**
	 * Gets the wind dir.
	 *
	 * @return the wind dir
	 */
	public String getWindDir() {
		return windDir;
	}

	/**
	 * Sets the wind dir.
	 *
	 * @param windDir
	 *            the new wind dir
	 */
	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	/**
	 * Gets the pressure mb.
	 *
	 * @return the pressure mb
	 */
	public Double getPressureMb() {
		return pressureMb;
	}

	/**
	 * Sets the pressure mb.
	 *
	 * @param pressureMb
	 *            the new pressure mb
	 */
	public void setPressureMb(Double pressureMb) {
		this.pressureMb = pressureMb;
	}

	/**
	 * Gets the pressure in.
	 *
	 * @return the pressure in
	 */
	public Double getPressureIn() {
		return pressureIn;
	}

	/**
	 * Sets the pressure in.
	 *
	 * @param pressureIn
	 *            the new pressure in
	 */
	public void setPressureIn(Double pressureIn) {
		this.pressureIn = pressureIn;
	}

	/**
	 * Gets the precip mm.
	 *
	 * @return the precip mm
	 */
	public Double getPrecipMm() {
		return precipMm;
	}

	/**
	 * Sets the precip mm.
	 *
	 * @param precipMm
	 *            the new precip mm
	 */
	public void setPrecipMm(Double precipMm) {
		this.precipMm = precipMm;
	}

	/**
	 * Gets the precip in.
	 *
	 * @return the precip in
	 */
	public Double getPrecipIn() {
		return precipIn;
	}

	/**
	 * Sets the precip in.
	 *
	 * @param precipIn
	 *            the new precip in
	 */
	public void setPrecipIn(Double precipIn) {
		this.precipIn = precipIn;
	}

	/**
	 * Gets the humidity.
	 *
	 * @return the humidity
	 */
	public Integer getHumidity() {
		return humidity;
	}

	/**
	 * Sets the humidity.
	 *
	 * @param humidity
	 *            the new humidity
	 */
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	/**
	 * Gets the cloud.
	 *
	 * @return the cloud
	 */
	public Integer getCloud() {
		return cloud;
	}

	/**
	 * Sets the cloud.
	 *
	 * @param cloud
	 *            the new cloud
	 */
	public void setCloud(Integer cloud) {
		this.cloud = cloud;
	}

	/**
	 * Gets the feelslike C.
	 *
	 * @return the feelslike C
	 */
	public Double getFeelslikeC() {
		return feelslikeC;
	}

	/**
	 * Sets the feelslike C.
	 *
	 * @param feelslikeC
	 *            the new feelslike C
	 */
	public void setFeelslikeC(Double feelslikeC) {
		this.feelslikeC = feelslikeC;
	}

	/**
	 * Gets the feelslike F.
	 *
	 * @return the feelslike F
	 */
	public Double getFeelslikeF() {
		return feelslikeF;
	}

	/**
	 * Sets the feelslike F.
	 *
	 * @param feelslikeF
	 *            the new feelslike F
	 */
	public void setFeelslikeF(Double feelslikeF) {
		this.feelslikeF = feelslikeF;
	}

	/**
	 * Gets the vis km.
	 *
	 * @return the vis km
	 */
	public Double getVisKm() {
		return visKm;
	}

	/**
	 * Sets the vis km.
	 *
	 * @param visKm
	 *            the new vis km
	 */
	public void setVisKm(Double visKm) {
		this.visKm = visKm;
	}

	/**
	 * Gets the vis miles.
	 *
	 * @return the vis miles
	 */
	public Double getVisMiles() {
		return visMiles;
	}

	/**
	 * Sets the vis miles.
	 *
	 * @param visMiles
	 *            the new vis miles
	 */
	public void setVisMiles(Double visMiles) {
		this.visMiles = visMiles;
	}

}
