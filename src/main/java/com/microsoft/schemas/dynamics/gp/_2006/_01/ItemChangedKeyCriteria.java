
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfItemType" minOccurs="0"/&gt;
 *         &lt;element name="ItemKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemChangedKeyCriteria", propOrder = {
    "itemType",
    "itemKeyId"
})
public class ItemChangedKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "ItemType")
    protected ListRestrictionOfNullableOfItemType itemType;
    @XmlElement(name = "ItemKeyId")
    protected BetweenRestrictionOfString itemKeyId;

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfItemType }
     *     
     */
    public ListRestrictionOfNullableOfItemType getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfItemType }
     *     
     */
    public void setItemType(ListRestrictionOfNullableOfItemType value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the itemKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getItemKeyId() {
        return itemKeyId;
    }

    /**
     * Sets the value of the itemKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setItemKeyId(BetweenRestrictionOfString value) {
        this.itemKeyId = value;
    }

}
