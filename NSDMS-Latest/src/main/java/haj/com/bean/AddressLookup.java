
package haj.com.bean;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressLookup.
 */
@Generated("org.jsonschema2pojo")
public class AddressLookup {

    /** The status. */
    @SerializedName("status")
    @Expose
    private Integer status;
    
    /** The result. */
    @SerializedName("result")
    @Expose
    private Result result;

    /**
     * Gets the status.
     *
     * @return     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets the result.
     *
     * @return     The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result     The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddressLookup [status=" + status + ", result=" + result + "]";
	}
    
    

}
