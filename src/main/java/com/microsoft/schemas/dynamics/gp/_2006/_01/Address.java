
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Address complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Address"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CountryRegion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Phone1" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="Phone2" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="Phone3" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="Fax" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PhoneNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address", propOrder = {
    "countryRegion",
    "phone1",
    "phone2",
    "phone3",
    "fax"
})
@XmlSeeAlso({
    ApplicantAddress.class,
    InternationalAddress.class
})
public class Address
    extends AddressBase
{

    @XmlElement(name = "CountryRegion")
    protected String countryRegion;
    @XmlElement(name = "Phone1")
    protected PhoneNumber phone1;
    @XmlElement(name = "Phone2")
    protected PhoneNumber phone2;
    @XmlElement(name = "Phone3")
    protected PhoneNumber phone3;
    @XmlElement(name = "Fax")
    protected PhoneNumber fax;

    /**
     * Gets the value of the countryRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryRegion() {
        return countryRegion;
    }

    /**
     * Sets the value of the countryRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryRegion(String value) {
        this.countryRegion = value;
    }

    /**
     * Gets the value of the phone1 property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getPhone1() {
        return phone1;
    }

    /**
     * Sets the value of the phone1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setPhone1(PhoneNumber value) {
        this.phone1 = value;
    }

    /**
     * Gets the value of the phone2 property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getPhone2() {
        return phone2;
    }

    /**
     * Sets the value of the phone2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setPhone2(PhoneNumber value) {
        this.phone2 = value;
    }

    /**
     * Gets the value of the phone3 property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getPhone3() {
        return phone3;
    }

    /**
     * Sets the value of the phone3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setPhone3(PhoneNumber value) {
        this.phone3 = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setFax(PhoneNumber value) {
        this.fax = value;
    }

}
