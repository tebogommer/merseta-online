
package haj.com.bean;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class Codes.
 */
@Generated("org.jsonschema2pojo")
public class Codes {

    /** The admin district. */
    @SerializedName("admin_district")
    @Expose
    private String adminDistrict;
    
    /** The admin county. */
    @SerializedName("admin_county")
    @Expose
    private String adminCounty;
    
    /** The admin ward. */
    @SerializedName("admin_ward")
    @Expose
    private String adminWard;
    
    /** The parish. */
    @SerializedName("parish")
    @Expose
    private String parish;
    
    /** The ccg. */
    @SerializedName("ccg")
    @Expose
    private String ccg;
    
    /** The nuts. */
    @SerializedName("nuts")
    @Expose
    private String nuts;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Codes [adminDistrict=" + adminDistrict + ", adminCounty=" + adminCounty + ", adminWard=" + adminWard
				+ ", parish=" + parish + ", ccg=" + ccg + ", nuts=" + nuts + "]";
	}
    
    

}
