package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * The Class CounterBean.
 * Used for counts in native sql quiries
 */
public class CounterBean implements Serializable {

	private BigInteger counter;

	public BigInteger getCounter() {
		return counter;
	}

	public void setCounter(BigInteger counter) {
		this.counter = counter;
	}
	
	
}
