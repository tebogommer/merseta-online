
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceEquipmentSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEquipmentSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="AddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="StatusKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceStatusKey" minOccurs="0"/&gt;
 *         &lt;element name="InstallDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEquipmentSummary", propOrder = {
    "key",
    "customerKey",
    "addressKey",
    "statusKey",
    "installDate"
})
public class ServiceEquipmentSummary {

    @XmlElement(name = "Key")
    protected ServiceEquipmentKey key;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "AddressKey")
    protected AddressKey addressKey;
    @XmlElement(name = "StatusKey")
    protected ServiceStatusKey statusKey;
    @XmlElement(name = "InstallDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar installDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public ServiceEquipmentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public void setKey(ServiceEquipmentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the customerKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    /**
     * Sets the value of the customerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerKey(CustomerKey value) {
        this.customerKey = value;
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

    /**
     * Gets the value of the statusKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStatusKey }
     *     
     */
    public ServiceStatusKey getStatusKey() {
        return statusKey;
    }

    /**
     * Sets the value of the statusKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStatusKey }
     *     
     */
    public void setStatusKey(ServiceStatusKey value) {
        this.statusKey = value;
    }

    /**
     * Gets the value of the installDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInstallDate() {
        return installDate;
    }

    /**
     * Sets the value of the installDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInstallDate(XMLGregorianCalendar value) {
        this.installDate = value;
    }

}
