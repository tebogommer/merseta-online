
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeAddress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InternationalAddress"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="County" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetAddresses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InternetAddresses" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeAddress", propOrder = {
    "key",
    "county",
    "internetAddresses",
    "deleteOnUpdate"
})
public class EmployeeAddress
    extends InternationalAddress
{

    @XmlElement(name = "Key")
    protected EmployeeAddressKey key;
    @XmlElement(name = "County")
    protected String county;
    @XmlElement(name = "InternetAddresses")
    protected InternetAddresses internetAddresses;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeAddressKey }
     *     
     */
    public EmployeeAddressKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeAddressKey }
     *     
     */
    public void setKey(EmployeeAddressKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the county property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounty(String value) {
        this.county = value;
    }

    /**
     * Gets the value of the internetAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link InternetAddresses }
     *     
     */
    public InternetAddresses getInternetAddresses() {
        return internetAddresses;
    }

    /**
     * Sets the value of the internetAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternetAddresses }
     *     
     */
    public void setInternetAddresses(InternetAddresses value) {
        this.internetAddresses = value;
    }

    /**
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
    }

}
