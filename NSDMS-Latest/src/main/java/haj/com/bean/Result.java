
package haj.com.bean;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class Result.
 */
@Generated("org.jsonschema2pojo")
public class Result {

    /** The postcode. */
    @SerializedName("postcode")
    @Expose
    private String postcode;
    
    /** The quality. */
    @SerializedName("quality")
    @Expose
    private Integer quality;
    
    /** The eastings. */
    @SerializedName("eastings")
    @Expose
    private Integer eastings;
    
    /** The northings. */
    @SerializedName("northings")
    @Expose
    private Integer northings;
    
    /** The country. */
    @SerializedName("country")
    @Expose
    private String country;
    
    /** The nhs ha. */
    @SerializedName("nhs_ha")
    @JsonProperty("nhs_ha")
    @Expose
    private String nhsHa;
    
    /** The longitude. */
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    
    /** The latitude. */
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    
    /** The parliamentary constituency. */
    @SerializedName("parliamentary_constituency")
    @JsonProperty("parliamentary_constituency")
    @Expose
    private String parliamentaryConstituency;
    
    /** The european electoral region. */
    @SerializedName("european_electoral_region")
    @JsonProperty("european_electoral_region")
    @Expose
    private String europeanElectoralRegion;
    
    /** The primary care trust. */
    @SerializedName("primary_care_trust")
    @JsonProperty("primary_care_trust")
    @Expose
    private String primaryCareTrust;
    
    /** The region. */
    @SerializedName("region")
    @Expose
    private String region;
    
    /** The lsoa. */
    @SerializedName("lsoa")
    @Expose
    private String lsoa;
    
    /** The msoa. */
    @SerializedName("msoa")
    @Expose
    private String msoa;
    
    /** The incode. */
    @SerializedName("incode")
    @Expose
    private String incode;
    
    /** The outcode. */
    @SerializedName("outcode")
    @Expose
    private String outcode;
    
    /** The admin district. */
    @SerializedName("admin_district")
    @JsonProperty("admin_district")
    @Expose
    private String adminDistrict;
    
    /** The parish. */
    @SerializedName("parish")
    @Expose
    private String parish;
    
    /** The admin county. */
    @SerializedName("admin_county")
    @JsonProperty("admin_county")
    @Expose
    private String adminCounty;
    
    /** The admin ward. */
    @SerializedName("admin_ward")
    @JsonProperty("admin_ward")
    @Expose
    private String adminWard;
    
    /** The ccg. */
    @SerializedName("ccg")
    @Expose
    private String ccg;
    
    /** The nuts. */
    @SerializedName("nuts")
    @Expose
    private String nuts;
    
    /** The codes. */
    @SerializedName("codes")
    @Expose
    private Codes codes;

    /**
     * Gets the postcode.
     *
     * @return     The postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the postcode.
     *
     * @param postcode     The postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Gets the quality.
     *
     * @return     The quality
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * Sets the quality.
     *
     * @param quality     The quality
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    /**
     * Gets the eastings.
     *
     * @return     The eastings
     */
    public Integer getEastings() {
        return eastings;
    }

    /**
     * Sets the eastings.
     *
     * @param eastings     The eastings
     */
    public void setEastings(Integer eastings) {
        this.eastings = eastings;
    }

    /**
     * Gets the northings.
     *
     * @return     The northings
     */
    public Integer getNorthings() {
        return northings;
    }

    /**
     * Sets the northings.
     *
     * @param northings     The northings
     */
    public void setNorthings(Integer northings) {
        this.northings = northings;
    }

    /**
     * Gets the country.
     *
     * @return     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the nhs ha.
     *
     * @return     The nhsHa
     */
    public String getNhsHa() {
        return nhsHa;
    }

    /**
     * Sets the nhs ha.
     *
     * @param nhsHa     The nhs_ha
     */
    public void setNhsHa(String nhsHa) {
        this.nhsHa = nhsHa;
    }

    /**
     * Gets the longitude.
     *
     * @return     The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     *
     * @param longitude     The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the latitude.
     *
     * @return     The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     *
     * @param latitude     The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the parliamentary constituency.
     *
     * @return     The parliamentaryConstituency
     */
    public String getParliamentaryConstituency() {
        return parliamentaryConstituency;
    }

    /**
     * Sets the parliamentary constituency.
     *
     * @param parliamentaryConstituency     The parliamentary_constituency
     */
    public void setParliamentaryConstituency(String parliamentaryConstituency) {
        this.parliamentaryConstituency = parliamentaryConstituency;
    }

    /**
     * Gets the european electoral region.
     *
     * @return     The europeanElectoralRegion
     */
    public String getEuropeanElectoralRegion() {
        return europeanElectoralRegion;
    }

    /**
     * Sets the european electoral region.
     *
     * @param europeanElectoralRegion     The european_electoral_region
     */
    public void setEuropeanElectoralRegion(String europeanElectoralRegion) {
        this.europeanElectoralRegion = europeanElectoralRegion;
    }

    /**
     * Gets the primary care trust.
     *
     * @return     The primaryCareTrust
     */
    public String getPrimaryCareTrust() {
        return primaryCareTrust;
    }

    /**
     * Sets the primary care trust.
     *
     * @param primaryCareTrust     The primary_care_trust
     */
    public void setPrimaryCareTrust(String primaryCareTrust) {
        this.primaryCareTrust = primaryCareTrust;
    }

    /**
     * Gets the region.
     *
     * @return     The region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region.
     *
     * @param region     The region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the lsoa.
     *
     * @return     The lsoa
     */
    public String getLsoa() {
        return lsoa;
    }

    /**
     * Sets the lsoa.
     *
     * @param lsoa     The lsoa
     */
    public void setLsoa(String lsoa) {
        this.lsoa = lsoa;
    }

    /**
     * Gets the msoa.
     *
     * @return     The msoa
     */
    public String getMsoa() {
        return msoa;
    }

    /**
     * Sets the msoa.
     *
     * @param msoa     The msoa
     */
    public void setMsoa(String msoa) {
        this.msoa = msoa;
    }

    /**
     * Gets the incode.
     *
     * @return     The incode
     */
    public String getIncode() {
        return incode;
    }

    /**
     * Sets the incode.
     *
     * @param incode     The incode
     */
    public void setIncode(String incode) {
        this.incode = incode;
    }

    /**
     * Gets the outcode.
     *
     * @return     The outcode
     */
    public String getOutcode() {
        return outcode;
    }

    /**
     * Sets the outcode.
     *
     * @param outcode     The outcode
     */
    public void setOutcode(String outcode) {
        this.outcode = outcode;
    }

    /**
     * Gets the admin district.
     *
     * @return     The adminDistrict
     */
    public String getAdminDistrict() {
        return adminDistrict;
    }

    /**
     * Sets the admin district.
     *
     * @param adminDistrict     The admin_district
     */
    public void setAdminDistrict(String adminDistrict) {
        this.adminDistrict = adminDistrict;
    }

    /**
     * Gets the parish.
     *
     * @return     The parish
     */
    public String getParish() {
        return parish;
    }

    /**
     * Sets the parish.
     *
     * @param parish     The parish
     */
    public void setParish(String parish) {
        this.parish = parish;
    }

    /**
     * Gets the admin county.
     *
     * @return     The adminCounty
     */
    public String getAdminCounty() {
        return adminCounty;
    }

    /**
     * Sets the admin county.
     *
     * @param adminCounty     The admin_county
     */
    public void setAdminCounty(String adminCounty) {
        this.adminCounty = adminCounty;
    }

    /**
     * Gets the admin ward.
     *
     * @return     The adminWard
     */
    public String getAdminWard() {
        return adminWard;
    }

    /**
     * Sets the admin ward.
     *
     * @param adminWard     The admin_ward
     */
    public void setAdminWard(String adminWard) {
        this.adminWard = adminWard;
    }

    /**
     * Gets the ccg.
     *
     * @return     The ccg
     */
    public String getCcg() {
        return ccg;
    }

    /**
     * Sets the ccg.
     *
     * @param ccg     The ccg
     */
    public void setCcg(String ccg) {
        this.ccg = ccg;
    }

    /**
     * Gets the nuts.
     *
     * @return     The nuts
     */
    public String getNuts() {
        return nuts;
    }

    /**
     * Sets the nuts.
     *
     * @param nuts     The nuts
     */
    public void setNuts(String nuts) {
        this.nuts = nuts;
    }

    /**
     * Gets the codes.
     *
     * @return     The codes
     */
    public Codes getCodes() {
        return codes;
    }

    /**
     * Sets the codes.
     *
     * @param codes     The codes
     */
    public void setCodes(Codes codes) {
        this.codes = codes;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Result [postcode=" + postcode + ", quality=" + quality + ", eastings=" + eastings + ", northings="
				+ northings + ", country=" + country + ", nhsHa=" + nhsHa + ", longitude=" + longitude + ", latitude="
				+ latitude + ", parliamentaryConstituency=" + parliamentaryConstituency + ", europeanElectoralRegion="
				+ europeanElectoralRegion + ", primaryCareTrust=" + primaryCareTrust + ", region=" + region + ", lsoa="
				+ lsoa + ", msoa=" + msoa + ", incode=" + incode + ", outcode=" + outcode + ", adminDistrict="
				+ adminDistrict + ", parish=" + parish + ", adminCounty=" + adminCounty + ", adminWard=" + adminWard
				+ ", ccg=" + ccg + ", nuts=" + nuts + ", codes=" + codes + "]";
	}

    
}
