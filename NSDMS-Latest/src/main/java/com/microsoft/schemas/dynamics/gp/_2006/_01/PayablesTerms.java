
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesTerms complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesTerms"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Terms"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DiscountTakenAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DiscountAmountAppliedTaken" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesTerms", propOrder = {
    "discountTakenAmount",
    "discountAmountAppliedTaken"
})
public class PayablesTerms
    extends Terms
{

    @XmlElement(name = "DiscountTakenAmount")
    protected MoneyAmount discountTakenAmount;
    @XmlElement(name = "DiscountAmountAppliedTaken")
    protected MoneyAmount discountAmountAppliedTaken;

    /**
     * Gets the value of the discountTakenAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountTakenAmount() {
        return discountTakenAmount;
    }

    /**
     * Sets the value of the discountTakenAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountTakenAmount(MoneyAmount value) {
        this.discountTakenAmount = value;
    }

    /**
     * Gets the value of the discountAmountAppliedTaken property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountAmountAppliedTaken() {
        return discountAmountAppliedTaken;
    }

    /**
     * Sets the value of the discountAmountAppliedTaken property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountAmountAppliedTaken(MoneyAmount value) {
        this.discountAmountAppliedTaken = value;
    }

}
