
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MoneyProfitCalculation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MoneyProfitCalculation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitCalculation"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoneyProfitCalculation", propOrder = {
    "profitAmount"
})
@XmlSeeAlso({
    ProfitFixed.class,
    ProfitVariable.class,
    TotalProfit.class,
    BillingRate.class
})
public abstract class MoneyProfitCalculation
    extends ProfitCalculation
{

    @XmlElement(name = "ProfitAmount")
    protected MoneyAmount profitAmount;

    /**
     * Gets the value of the profitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProfitAmount() {
        return profitAmount;
    }

    /**
     * Sets the value of the profitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProfitAmount(MoneyAmount value) {
        this.profitAmount = value;
    }

}
