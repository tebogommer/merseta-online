
package haj.com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import haj.com.sars.SarsFiles;


// TODO: Auto-generated Javadoc
/**
 * The Class LeviesPaidBean.
 */
public class LeviesPaidBean implements Serializable {
	
	/** The scheme year. */
	private Integer schemeYear;
	
	/** The detail. */
	private List<SarsFiles> detail;
	
	/** The tot levy. */
	private BigDecimal totLevy; 
	
	/** The tot CB amount. */
	private BigDecimal totCBAmount; 
	
	/** The diff. */
	private BigDecimal diff;
	
	/**
	 * Gets the scheme year.
	 *
	 * @return the scheme year
	 */
	public Integer getSchemeYear() {
		return schemeYear;
	}
	
	/**
	 * Sets the scheme year.
	 *
	 * @param schemeYear the new scheme year
	 */
	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}
	
	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public List<SarsFiles> getDetail() {
		return detail;
	}
	
	/**
	 * Sets the detail.
	 *
	 * @param detail the new detail
	 */
	public void setDetail(List<SarsFiles> detail) {
		this.detail = detail;
	}
	
	/**
	 * Gets the tot levy.
	 *
	 * @return the tot levy
	 */
	public BigDecimal getTotLevy() {
		return totLevy;
	}
	
	/**
	 * Sets the tot levy.
	 *
	 * @param totLevy the new tot levy
	 */
	public void setTotLevy(BigDecimal totLevy) {
		this.totLevy = totLevy;
	}
	
	/**
	 * Gets the tot CB amount.
	 *
	 * @return the tot CB amount
	 */
	public BigDecimal getTotCBAmount() {
		return totCBAmount;
	}
	
	/**
	 * Sets the tot CB amount.
	 *
	 * @param totCBAmount the new tot CB amount
	 */
	public void setTotCBAmount(BigDecimal totCBAmount) {
		this.totCBAmount = totCBAmount;
	}
	
	/**
	 * Gets the diff.
	 *
	 * @return the diff
	 */
	public BigDecimal getDiff() {
		return diff;
	}
	
	/**
	 * Sets the diff.
	 *
	 * @param diff the new diff
	 */
	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}

	
}