
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PricingChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PricingChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PriceLevelKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="UofMKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PricingChangedKeyCriteria", propOrder = {
    "itemKeyId",
    "currencyKeyId",
    "priceLevelKeyId",
    "uofMKeyId"
})
public class PricingChangedKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "ItemKeyId")
    protected BetweenRestrictionOfString itemKeyId;
    @XmlElement(name = "CurrencyKeyId")
    protected BetweenRestrictionOfString currencyKeyId;
    @XmlElement(name = "PriceLevelKeyId")
    protected BetweenRestrictionOfString priceLevelKeyId;
    @XmlElement(name = "UofMKeyId")
    protected BetweenRestrictionOfString uofMKeyId;

    /**
     * Gets the value of the itemKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getItemKeyId() {
        return itemKeyId;
    }

    /**
     * Sets the value of the itemKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setItemKeyId(BetweenRestrictionOfString value) {
        this.itemKeyId = value;
    }

    /**
     * Gets the value of the currencyKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getCurrencyKeyId() {
        return currencyKeyId;
    }

    /**
     * Sets the value of the currencyKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setCurrencyKeyId(BetweenRestrictionOfString value) {
        this.currencyKeyId = value;
    }

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

    /**
     * Gets the value of the uofMKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getUofMKeyId() {
        return uofMKeyId;
    }

    /**
     * Sets the value of the uofMKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setUofMKeyId(BetweenRestrictionOfString value) {
        this.uofMKeyId = value;
    }

}
