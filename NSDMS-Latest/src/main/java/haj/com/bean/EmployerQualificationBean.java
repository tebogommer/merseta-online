
package haj.com.bean;

import java.math.BigInteger;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployerQualificationBean.
 */
public class EmployerQualificationBean {

	private BigInteger targetKey;
	private BigInteger employerId;
	private String targetClass;

	public EmployerQualificationBean() {
		super();
	}

	public EmployerQualificationBean(BigInteger targetKey, BigInteger employerId, String targetClass) {
		super();
		this.targetKey = targetKey;
		this.employerId = employerId;
		this.targetClass = targetClass;
	}



	public BigInteger getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(BigInteger targetKey) {
		this.targetKey = targetKey;
	}

	public BigInteger getEmployerId() {
		return employerId;
	}

	public void setEmployerId(BigInteger employerId) {
		this.employerId = employerId;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	
}