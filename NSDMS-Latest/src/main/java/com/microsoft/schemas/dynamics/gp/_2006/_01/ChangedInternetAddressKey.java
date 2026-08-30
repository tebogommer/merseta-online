
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangedInternetAddressKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChangedInternetAddressKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedBusinessObjectGreatPlainsKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InternetAddressType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetAddressKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangedInternetAddressKey", propOrder = {
    "internetAddressType",
    "internetAddressKey",
    "addressKey"
})
public class ChangedInternetAddressKey
    extends ChangedBusinessObjectGreatPlainsKey
{

    @XmlElement(name = "InternetAddressType")
    protected String internetAddressType;
    @XmlElement(name = "InternetAddressKey")
    protected String internetAddressKey;
    @XmlElement(name = "AddressKey")
    protected AddressKey addressKey;

    /**
     * Gets the value of the internetAddressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetAddressType() {
        return internetAddressType;
    }

    /**
     * Sets the value of the internetAddressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetAddressType(String value) {
        this.internetAddressType = value;
    }

    /**
     * Gets the value of the internetAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetAddressKey() {
        return internetAddressKey;
    }

    /**
     * Sets the value of the internetAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetAddressKey(String value) {
        this.internetAddressKey = value;
    }

    /**
     * Gets the value of the addressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getAddressKey() {
        return addressKey;
    }

    /**
     * Sets the value of the addressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setAddressKey(AddressKey value) {
        this.addressKey = value;
    }

}
