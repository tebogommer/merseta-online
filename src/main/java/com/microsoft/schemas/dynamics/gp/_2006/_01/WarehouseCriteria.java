
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WarehouseCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WarehouseCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WarehouseId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="AddressLine1" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="City" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PostalCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CountryRegion" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WarehouseCriteria", propOrder = {
    "warehouseId",
    "description",
    "addressLine1",
    "city",
    "state",
    "postalCode",
    "countryRegion"
})
public class WarehouseCriteria
    extends Criteria
{

    @XmlElement(name = "WarehouseId")
    protected LikeRestrictionOfString warehouseId;
    @XmlElement(name = "Description")
    protected LikeRestrictionOfString description;
    @XmlElement(name = "AddressLine1")
    protected LikeRestrictionOfString addressLine1;
    @XmlElement(name = "City")
    protected LikeRestrictionOfString city;
    @XmlElement(name = "State")
    protected LikeRestrictionOfString state;
    @XmlElement(name = "PostalCode")
    protected LikeRestrictionOfString postalCode;
    @XmlElement(name = "CountryRegion")
    protected LikeRestrictionOfString countryRegion;

    /**
     * Gets the value of the warehouseId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets the value of the warehouseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setWarehouseId(LikeRestrictionOfString value) {
        this.warehouseId = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDescription(LikeRestrictionOfString value) {
        this.description = value;
    }

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setAddressLine1(LikeRestrictionOfString value) {
        this.addressLine1 = value;
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

    /**
     * Gets the value of the countryRegion property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCountryRegion() {
        return countryRegion;
    }

    /**
     * Sets the value of the countryRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCountryRegion(LikeRestrictionOfString value) {
        this.countryRegion = value;
    }

}
