
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="IsOnHold" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ClassId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CorporateAccountId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PhoneNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerCriteria", propOrder = {
    "id",
    "name",
    "isActive",
    "isOnHold",
    "salespersonId",
    "classId",
    "corporateAccountId",
    "state",
    "phoneNumber",
    "createdDate",
    "modifiedDate",
    "lastModifiedDate",
    "scope"
})
public class CustomerCriteria
    extends Criteria
{

    @XmlElement(name = "Id")
    protected BetweenRestrictionOfString id;
    @XmlElement(name = "Name")
    protected LikeRestrictionOfString name;
    @XmlElement(name = "IsActive")
    protected RestrictionOfNullableOfBoolean isActive;
    @XmlElement(name = "IsOnHold")
    protected RestrictionOfNullableOfBoolean isOnHold;
    @XmlElement(name = "SalespersonId")
    protected LikeRestrictionOfString salespersonId;
    @XmlElement(name = "ClassId")
    protected LikeRestrictionOfString classId;
    @XmlElement(name = "CorporateAccountId")
    protected LikeRestrictionOfString corporateAccountId;
    @XmlElement(name = "State")
    protected LikeRestrictionOfString state;
    @XmlElement(name = "PhoneNumber")
    protected LikeRestrictionOfString phoneNumber;
    @XmlElement(name = "CreatedDate")
    protected BetweenRestrictionOfNullableOfDateTime createdDate;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected CustomerScope scope;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setId(BetweenRestrictionOfString value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setName(LikeRestrictionOfString value) {
        this.name = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsActive(RestrictionOfNullableOfBoolean value) {
        this.isActive = value;
    }

    /**
     * Gets the value of the isOnHold property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsOnHold() {
        return isOnHold;
    }

    /**
     * Sets the value of the isOnHold property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsOnHold(RestrictionOfNullableOfBoolean value) {
        this.isOnHold = value;
    }

    /**
     * Gets the value of the salespersonId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSalespersonId() {
        return salespersonId;
    }

    /**
     * Sets the value of the salespersonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSalespersonId(LikeRestrictionOfString value) {
        this.salespersonId = value;
    }

    /**
     * Gets the value of the classId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getClassId() {
        return classId;
    }

    /**
     * Sets the value of the classId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setClassId(LikeRestrictionOfString value) {
        this.classId = value;
    }

    /**
     * Gets the value of the corporateAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCorporateAccountId() {
        return corporateAccountId;
    }

    /**
     * Sets the value of the corporateAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCorporateAccountId(LikeRestrictionOfString value) {
        this.corporateAccountId = value;
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
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setCreatedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setModifiedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setLastModifiedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerScope }
     *     
     */
    public CustomerScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerScope }
     *     
     */
    public void setScope(CustomerScope value) {
        this.scope = value;
    }

}
