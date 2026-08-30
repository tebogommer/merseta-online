
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemClassCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemClassCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemClassId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfItemType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemClassCriteria", propOrder = {
    "itemClassId",
    "description",
    "type"
})
public class ItemClassCriteria
    extends Criteria
{

    @XmlElement(name = "ItemClassId")
    protected LikeRestrictionOfString itemClassId;
    @XmlElement(name = "Description")
    protected LikeRestrictionOfString description;
    @XmlElement(name = "Type")
    protected ListRestrictionOfNullableOfItemType type;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfItemType }
     *     
     */
    public ListRestrictionOfNullableOfItemType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfItemType }
     *     
     */
    public void setType(ListRestrictionOfNullableOfItemType value) {
        this.type = value;
    }

}
