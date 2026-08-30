
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeAndMaterial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeAndMaterial"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Profit"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="None" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}NoProfit" minOccurs="0"/&gt;
 *           &lt;element name="BillingRate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BillingRate" minOccurs="0"/&gt;
 *           &lt;element name="MarkupPercentage" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MarkupPercentage" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeAndMaterial", propOrder = {
    "none",
    "billingRate",
    "markupPercentage"
})
public class TimeAndMaterial
    extends Profit
{

    @XmlElement(name = "None")
    protected NoProfit none;
    @XmlElement(name = "BillingRate")
    protected BillingRate billingRate;
    @XmlElement(name = "MarkupPercentage")
    protected MarkupPercentage markupPercentage;

    /**
     * Gets the value of the none property.
     * 
     * @return
     *     possible object is
     *     {@link NoProfit }
     *     
     */
    public NoProfit getNone() {
        return none;
    }

    /**
     * Sets the value of the none property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoProfit }
     *     
     */
    public void setNone(NoProfit value) {
        this.none = value;
    }

    /**
     * Gets the value of the billingRate property.
     * 
     * @return
     *     possible object is
     *     {@link BillingRate }
     *     
     */
    public BillingRate getBillingRate() {
        return billingRate;
    }

    /**
     * Sets the value of the billingRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingRate }
     *     
     */
    public void setBillingRate(BillingRate value) {
        this.billingRate = value;
    }

    /**
     * Gets the value of the markupPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link MarkupPercentage }
     *     
     */
    public MarkupPercentage getMarkupPercentage() {
        return markupPercentage;
    }

    /**
     * Sets the value of the markupPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkupPercentage }
     *     
     */
    public void setMarkupPercentage(MarkupPercentage value) {
        this.markupPercentage = value;
    }

}
