
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PricingDetailKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PricingDetailKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PricingKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PricingKey" minOccurs="0"/&gt;
 *         &lt;element name="ToQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricingDetailKey", propOrder = {
    "pricingKey",
    "toQuantity"
})
public class PricingDetailKey
    extends ReferenceKey
{

    @XmlElement(name = "PricingKey")
    protected PricingKey pricingKey;
    @XmlElement(name = "ToQuantity")
    protected Quantity toQuantity;

    /**
     * Gets the value of the pricingKey property.
     * 
     * @return
     *     possible object is
     *     {@link PricingKey }
     *     
     */
    public PricingKey getPricingKey() {
        return pricingKey;
    }

    /**
     * Sets the value of the pricingKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PricingKey }
     *     
     */
    public void setPricingKey(PricingKey value) {
        this.pricingKey = value;
    }

    /**
     * Gets the value of the toQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getToQuantity() {
        return toQuantity;
    }

    /**
     * Sets the value of the toQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setToQuantity(Quantity value) {
        this.toQuantity = value;
    }

}
