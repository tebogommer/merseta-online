
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemCriteriaBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemCriteriaBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ShortDescription" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="GenericDescription" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ItemClassId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemCriteriaBase", propOrder = {
    "itemId",
    "description",
    "shortDescription",
    "genericDescription",
    "itemClassId",
    "createdDate",
    "modifiedDate",
    "lastModifiedDate"
})
@XmlSeeAlso({
    ItemCriteria.class,
    InventoriedItemCriteria.class,
    FeeCriteria.class,
    ServiceCriteria.class
})
public abstract class ItemCriteriaBase
    extends Criteria
{

    @XmlElement(name = "ItemId")
    protected LikeRestrictionOfString itemId;
    @XmlElement(name = "Description")
    protected LikeRestrictionOfString description;
    @XmlElement(name = "ShortDescription")
    protected LikeRestrictionOfString shortDescription;
    @XmlElement(name = "GenericDescription")
    protected LikeRestrictionOfString genericDescription;
    @XmlElement(name = "ItemClassId")
    protected LikeRestrictionOfString itemClassId;
    @XmlElement(name = "CreatedDate")
    protected BetweenRestrictionOfNullableOfDateTime createdDate;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;

    /**
     * Gets the value of the itemId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getItemId() {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setItemId(LikeRestrictionOfString value) {
        this.itemId = value;
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
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setShortDescription(LikeRestrictionOfString value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the genericDescription property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getGenericDescription() {
        return genericDescription;
    }

    /**
     * Sets the value of the genericDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setGenericDescription(LikeRestrictionOfString value) {
        this.genericDescription = value;
    }

    /**
     * Gets the value of the itemClassId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getItemClassId() {
        return itemClassId;
    }

    /**
     * Sets the value of the itemClassId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setItemClassId(LikeRestrictionOfString value) {
        this.itemClassId = value;
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

}
