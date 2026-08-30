
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CustomerSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsOnHold" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CustomerClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerClassKey" minOccurs="0"/&gt;
 *         &lt;element name="CorporateAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PhoneNumbers" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPhoneNumber" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerSummary", propOrder = {
    "key",
    "name",
    "isActive",
    "isOnHold",
    "customerClassKey",
    "corporateAccountKey",
    "salespersonKey",
    "state",
    "phoneNumbers",
    "createdDate",
    "modifiedDate",
    "lastModifiedDate"
})
public class CustomerSummary {

    @XmlElement(name = "Key")
    protected CustomerKey key;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "IsActive")
    protected boolean isActive;
    @XmlElement(name = "IsOnHold")
    protected boolean isOnHold;
    @XmlElement(name = "CustomerClassKey")
    protected CustomerClassKey customerClassKey;
    @XmlElement(name = "CorporateAccountKey")
    protected CustomerKey corporateAccountKey;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "PhoneNumbers")
    protected ArrayOfPhoneNumber phoneNumbers;
    @XmlElement(name = "CreatedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "LastModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setKey(CustomerKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     */
    public void setIsActive(boolean value) {
        this.isActive = value;
    }

    /**
     * Gets the value of the isOnHold property.
     * 
     */
    public boolean isIsOnHold() {
        return isOnHold;
    }

    /**
     * Sets the value of the isOnHold property.
     * 
     */
    public void setIsOnHold(boolean value) {
        this.isOnHold = value;
    }

    /**
     * Gets the value of the customerClassKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerClassKey }
     *     
     */
    public CustomerClassKey getCustomerClassKey() {
        return customerClassKey;
    }

    /**
     * Sets the value of the customerClassKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerClassKey }
     *     
     */
    public void setCustomerClassKey(CustomerClassKey value) {
        this.customerClassKey = value;
    }

    /**
     * Gets the value of the corporateAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCorporateAccountKey() {
        return corporateAccountKey;
    }

    /**
     * Sets the value of the corporateAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCorporateAccountKey(CustomerKey value) {
        this.corporateAccountKey = value;
    }

    /**
     * Gets the value of the salespersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalespersonKey() {
        return salespersonKey;
    }

    /**
     * Sets the value of the salespersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalespersonKey(SalespersonKey value) {
        this.salespersonKey = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the phoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPhoneNumber }
     *     
     */
    public ArrayOfPhoneNumber getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Sets the value of the phoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPhoneNumber }
     *     
     */
    public void setPhoneNumbers(ArrayOfPhoneNumber value) {
        this.phoneNumbers = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

}
