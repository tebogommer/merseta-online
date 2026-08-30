
package haj.com.bean;

import haj.com.entity.enums.AgeGroupEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantBean.
 */
public class GrantBean {

	/** The gender. */
	private Gender gender;
	
	/** The equity. */
	private Equity equity;
	
	/** The disability status. */
	private DisabilityStatus disabilityStatus;
	
	/** The age group. */
	private AgeGroupEnum ageGroup;
	
	/** The pivot non pivot. */
	private PivotNonPivotEnum pivotNonPivot;
	
	/** The amount. */
	private Integer amount;

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the equity.
	 *
	 * @return the equity
	 */
	public Equity getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 *
	 * @param equity the new equity
	 */
	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	/**
	 * Gets the age group.
	 *
	 * @return the age group
	 */
	public AgeGroupEnum getAgeGroup() {
		return ageGroup;
	}

	/**
	 * Sets the age group.
	 *
	 * @param ageGroup the new age group
	 */
	public void setAgeGroup(AgeGroupEnum ageGroup) {
		this.ageGroup = ageGroup;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * Gets the pivot non pivot.
	 *
	 * @return the pivot non pivot
	 */
	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	/**
	 * Sets the pivot non pivot.
	 *
	 * @param pivotNonPivot the new pivot non pivot
	 */
	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
	}

	/**
	 * Gets the disability status.
	 *
	 * @return the disability status
	 */
	public DisabilityStatus getDisabilityStatus() {
		return disabilityStatus;
	}

	/**
	 * Sets the disability status.
	 *
	 * @param disabilityStatus the new disability status
	 */
	public void setDisabilityStatus(DisabilityStatus disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}
}
