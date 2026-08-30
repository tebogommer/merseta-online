
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeAddressCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeAddressCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeScope"/&gt;
 *         &lt;element name="EmployeeKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="EmployeeAddressKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PhoneNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="City" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PostalCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeAddressCriteria", propOrder = {
    "scope",
    "employeeKeyId",
    "employeeAddressKeyId",
    "phoneNumber",
    "address",
    "city",
    "state",
    "postalCode"
})
public class EmployeeAddressCriteria
    extends Criteria
{

    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected EmployeeScope scope;
    @XmlElement(name = "EmployeeKeyId")
    protected LikeRestrictionOfString employeeKeyId;
    @XmlElement(name = "EmployeeAddressKeyId")
    protected LikeRestrictionOfString employeeAddressKeyId;
    @XmlElement(name = "PhoneNumber")
    protected LikeRestrictionOfString phoneNumber;
    @XmlElement(name = "Address")
    protected LikeRestrictionOfString address;
    @XmlElement(name = "City")
    protected LikeRestrictionOfString city;
    @XmlElement(name = "State")
    protected LikeRestrictionOfString state;
    @XmlElement(name = "PostalCode")
    protected LikeRestrictionOfString postalCode;

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeScope }
     *     
     */
    public EmployeeScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeScope }
     *     
     */
    public void setScope(EmployeeScope value) {
        this.scope = value;
    }

    /**
     * Gets the value of the employeeKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getEmployeeKeyId() {
        return employeeKeyId;
    }

    /**
     * Sets the value of the employeeKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setEmployeeKeyId(LikeRestrictionOfString value) {
        this.employeeKeyId = value;
    }

    /**
     * Gets the value of the employeeAddressKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getEmployeeAddressKeyId() {
        return employeeAddressKeyId;
    }

    /**
     * Sets the value of the employeeAddressKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setEmployeeAddressKeyId(LikeRestrictionOfString value) {
        this.employeeAddressKeyId = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPhoneNumber(LikeRestrictionOfString value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setAddress(LikeRestrictionOfString value) {
        this.address = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCity(LikeRestrictionOfString value) {
        this.city = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setState(LikeRestrictionOfString value) {
        this.state = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPostalCode(LikeRestrictionOfString value) {
        this.postalCode = value;
    }

}
