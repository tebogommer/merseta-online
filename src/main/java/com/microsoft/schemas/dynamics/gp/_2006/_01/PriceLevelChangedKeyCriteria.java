
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PriceLevelChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PriceLevelChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PriceLevelKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceLevelChangedKeyCriteria", propOrder = {
    "priceLevelKeyId"
})
public class PriceLevelChangedKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "PriceLevelKeyId")
    protected BetweenRestrictionOfString priceLevelKeyId;

    /**
     * Gets the value of the priceLevelKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getPriceLevelKeyId() {
        return priceLevelKeyId;
    }

    /**
     * Sets the value of the priceLevelKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setPriceLevelKeyId(BetweenRestrictionOfString value) {
        this.priceLevelKeyId = value;
    }

}
