package haj.com.rest.idverification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdVerificationRealTime {

	@SerializedName("Status")
	@Expose
	private String          status;
	@SerializedName("realTimeResults")
	@Expose
	private RealTimeResults realTimeResults;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RealTimeResults getRealTimeResults() {
		return realTimeResults;
	}

	public void setRealTimeResults(RealTimeResults realTimeResults) {
		this.realTimeResults = realTimeResults;
	}

}
