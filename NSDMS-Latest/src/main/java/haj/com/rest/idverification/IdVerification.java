
package haj.com.rest.idverification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdVerification {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("idProfile")
    @Expose
    private IdProfile idProfile;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IdProfile getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(IdProfile idProfile) {
        this.idProfile = idProfile;
    }

}
