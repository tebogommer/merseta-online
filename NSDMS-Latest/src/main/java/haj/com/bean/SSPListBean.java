package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;

// TODO: Auto-generated Javadoc
/**
 * The Class AttachmentBean.
 */
public class SSPListBean implements Serializable {

	private String interventionType;
	private String ofoCode;
	private BigInteger pivitolApplicationTotal;
	private BigInteger pivitolApplicationTotalPWD;
	private String sspPriotiy;
	private BigInteger totalAllocation;

	/**
	 * @return the interventionType
	 */
	public String getInterventionType() {
		return interventionType;
	}

	/**
	 * @param interventionType
	 *            the interventionType to set
	 */
	public void setInterventionType(String interventionType) {
		this.interventionType = interventionType;
	}

	/**
	 * @return the ofoCode
	 */
	public String getOfoCode() {
		return ofoCode;
	}

	/**
	 * @param ofoCode
	 *            the ofoCode to set
	 */
	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	/**
	 * @return the pivitolApplicationTotal
	 */
	public BigInteger getPivitolApplicationTotal() {
		return pivitolApplicationTotal;
	}

	/**
	 * @param pivitolApplicationTotal
	 *            the pivitolApplicationTotal to set
	 */
	public void setPivitolApplicationTotal(BigInteger pivitolApplicationTotal) {
		this.pivitolApplicationTotal = pivitolApplicationTotal;
	}

	/**
	 * @return the pivitolApplicationTotalPWD
	 */
	public BigInteger getPivitolApplicationTotalPWD() {
		return pivitolApplicationTotalPWD;
	}

	/**
	 * @param pivitolApplicationTotalPWD
	 *            the pivitolApplicationTotalPWD to set
	 */
	public void setPivitolApplicationTotalPWD(BigInteger pivitolApplicationTotalPWD) {
		this.pivitolApplicationTotalPWD = pivitolApplicationTotalPWD;
	}

	/**
	 * @return the sspPriotiy
	 */
	public String getSspPriotiy() {
		return sspPriotiy;
	}

	/**
	 * @param sspPriotiy
	 *            the sspPriotiy to set
	 */
	public void setSspPriotiy(String sspPriotiy) {
		this.sspPriotiy = sspPriotiy;
	}

	/**
	 * @return the totalAllocation
	 */
	public BigInteger getTotalAllocation() {
		return totalAllocation;
	}

	/**
	 * @param totalAllocation
	 *            the totalAllocation to set
	 */
	public void setTotalAllocation(BigInteger totalAllocation) {
		this.totalAllocation = totalAllocation;
	}

}