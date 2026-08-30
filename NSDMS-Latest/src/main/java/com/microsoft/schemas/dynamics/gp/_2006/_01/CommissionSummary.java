
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommissionSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommissionSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CostOfSales" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalCommissionsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CommissionedSales" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="NoncommissionedSales" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommissionSummary", propOrder = {
    "costOfSales",
    "totalCommissionsAmount",
    "commissionedSales",
    "noncommissionedSales"
})
public class CommissionSummary
    extends BusinessObject
{

    @XmlElement(name = "CostOfSales")
    protected MoneyAmount costOfSales;
    @XmlElement(name = "TotalCommissionsAmount")
    protected MoneyAmount totalCommissionsAmount;
    @XmlElement(name = "CommissionedSales")
    protected MoneyAmount commissionedSales;
    @XmlElement(name = "NoncommissionedSales")
    protected MoneyAmount noncommissionedSales;

    /**
     * Gets the value of the costOfSales property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCostOfSales() {
        return costOfSales;
    }

    /**
     * Sets the value of the costOfSales property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCostOfSales(MoneyAmount value) {
        this.costOfSales = value;
    }

    /**
     * Gets the value of the totalCommissionsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCommissionsAmount() {
        return totalCommissionsAmount;
    }

    /**
     * Sets the value of the totalCommissionsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCommissionsAmount(MoneyAmount value) {
        this.totalCommissionsAmount = value;
    }

    /**
     * Gets the value of the commissionedSales property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommissionedSales() {
        return commissionedSales;
    }

    /**
     * Sets the value of the commissionedSales property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommissionedSales(MoneyAmount value) {
        this.commissionedSales = value;
    }

    /**
     * Gets the value of the noncommissionedSales property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getNoncommissionedSales() {
        return noncommissionedSales;
    }

    /**
     * Sets the value of the noncommissionedSales property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setNoncommissionedSales(MoneyAmount value) {
        this.noncommissionedSales = value;
    }

}
