
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InternetAddressKeyChangedCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternetAddressKeyChangedCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InternetAddressType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="InternetAddressKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="AddressKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternetAddressKeyChangedCriteria", propOrder = {
    "internetAddressType",
    "internetAddressKeyId",
    "addressKeyId"
})
public class InternetAddressKeyChangedCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "InternetAddressType")
    protected BetweenRestrictionOfString internetAddressType;
    @XmlElement(name = "InternetAddressKeyId")
    protected BetweenRestrictionOfString internetAddressKeyId;
    @XmlElement(name = "AddressKeyId")
    protected BetweenRestrictionOfString addressKeyId;

    /**
     * Gets the value of the internetAddressType property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getInternetAddressType() {
        return internetAddressType;
    }

    /**
     * Sets the value of the internetAddressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setInternetAddressType(BetweenRestrictionOfString value) {
        this.internetAddressType = value;
    }

    /**
     * Gets the value of the internetAddressKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getInternetAddressKeyId() {
        return internetAddressKeyId;
    }

    /**
     * Sets the value of the internetAddressKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setInternetAddressKeyId(BetweenRestrictionOfString value) {
        this.internetAddressKeyId = value;
    }

    /**
     * Gets the value of the addressKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getAddressKeyId() {
        return addressKeyId;
    }

    /**
     * Sets the value of the addressKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setAddressKeyId(BetweenRestrictionOfString value) {
        this.addressKeyId = value;
    }

}
