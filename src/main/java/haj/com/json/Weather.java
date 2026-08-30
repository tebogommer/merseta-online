
package haj.com.json;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class Weather.
 */
public class Weather implements Serializable{

    /** The location. */
    @SerializedName("location")
    @Expose
    private Location location;
    
    /** The current. */
    @SerializedName("current")
    @Expose
    private Current current;

    /**
     * Gets the location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the current.
     *
     * @return the current
     */
    public Current getCurrent() {
        return current;
    }

    /**
     * Sets the current.
     *
     * @param current the new current
     */
    public void setCurrent(Current current) {
        this.current = current;
    }

}
