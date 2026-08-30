
package haj.com.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Codes.
 */
public class ResultsBean {

	private String refNo;
	
	private String registeredNameOfEntity;
	
	private Integer schemeYear;
	
	private Double totalMDA;
	
	private Double totalPI;
	
	private Double totalAmount;
	
	public ResultsBean() {
		super();
	}

	/* getters and Setters */
	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getRegisteredNameOfEntity() {
		return registeredNameOfEntity;
	}

	public void setRegisteredNameOfEntity(String registeredNameOfEntity) {
		this.registeredNameOfEntity = registeredNameOfEntity;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	public Double getTotalMDA() {
		return totalMDA;
	}

	public void setTotalMDA(Double totalMDA) {
		this.totalMDA = totalMDA;
	}

	public Double getTotalPI() {
		return totalPI;
	}

	public void setTotalPI(Double totalPI) {
		this.totalPI = totalPI;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalMDAMultipled() {
		if (totalMDA != null && !totalMDA.equals(0.00)) {
			return (totalMDA * -1);
		}else {
			return 0.00;
		}
	}

	public Double getTotalPIMultipled() {
		if (totalPI != null && !totalPI.equals(0.00)) {
			return (totalPI * -1);
		}else {
			return 0.00;
		}
	}

	public Double getTotalAmountMultipled() {
		if (totalAmount != null && !totalAmount.equals(0.00)) {
			return (totalAmount * -1);
		}else {
			return 0.00;
		}
	}
	
}
