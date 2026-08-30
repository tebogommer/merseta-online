
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerCreditLimit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerCreditLimit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}CreditLimit"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Period" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PeriodAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerCreditLimit", propOrder = {
    "period",
    "periodAmount"
})
public class CustomerCreditLimit
    extends CreditLimit
{

    @XmlElement(name = "Period", required = true, type = Integer.class, nillable = true)
    protected Integer period;
    @XmlElement(name = "PeriodAmount")
    protected MoneyAmount periodAmount;

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPeriod(Integer value) {
        this.period = value;
    }

    /**
     * Gets the value of the periodAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriodAmount() {
        return periodAmount;
    }

    /**
     * Sets the value of the periodAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriodAmount(MoneyAmount value) {
        this.periodAmount = value;
    }

}
