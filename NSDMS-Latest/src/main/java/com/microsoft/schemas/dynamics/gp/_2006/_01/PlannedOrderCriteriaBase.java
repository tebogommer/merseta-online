
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlannedOrderCriteriaBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlannedOrderCriteriaBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PlannedOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="LocationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DueDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ReleaseDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="VendorKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedOrderCriteriaBase", propOrder = {
    "plannedOrderKey",
    "itemKey",
    "locationKey",
    "dueDate",
    "releaseDate",
    "vendorKeyId"
})
@XmlSeeAlso({
    PlannedOrderCriteria.class,
    VendorPlannedOrderCriteria.class
})
public class PlannedOrderCriteriaBase
    extends Criteria
{

    @XmlElement(name = "PlannedOrderKey")
    protected LikeRestrictionOfString plannedOrderKey;
    @XmlElement(name = "ItemKey")
    protected LikeRestrictionOfString itemKey;
    @XmlElement(name = "LocationKey")
    protected LikeRestrictionOfString locationKey;
    @XmlElement(name = "DueDate")
    protected BetweenRestrictionOfNullableOfDateTime dueDate;
    @XmlElement(name = "ReleaseDate")
    protected BetweenRestrictionOfNullableOfDateTime releaseDate;
    @XmlElement(name = "VendorKeyId")
    protected LikeRestrictionOfString vendorKeyId;

    /**
     * Gets the value of the plannedOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPlannedOrderKey() {
        return plannedOrderKey;
    }

    /**
     * Sets the value of the plannedOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPlannedOrderKey(LikeRestrictionOfString value) {
        this.plannedOrderKey = value;
    }

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setItemKey(LikeRestrictionOfString value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the locationKey property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getLocationKey() {
        return locationKey;
    }

    /**
     * Sets the value of the locationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setLocationKey(LikeRestrictionOfString value) {
        this.locationKey = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setDueDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the releaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setReleaseDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.releaseDate = value;
    }

    /**
     * Gets the value of the vendorKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getVendorKeyId() {
        return vendorKeyId;
    }

    /**
     * Sets the value of the vendorKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setVendorKeyId(LikeRestrictionOfString value) {
        this.vendorKeyId = value;
    }

}
